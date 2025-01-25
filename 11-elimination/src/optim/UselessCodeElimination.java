package optim;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

import analysis.DominatorAnalysis;
import analysis.DominatorTree;
import irgen.BasicBlock;
import irgen.CFG;
import irgen.Instruction;
import irgen.InstructionType;

/**
 * Eliminarea codului inutil
 */
public class UselessCodeElimination {
    protected CFG cfg;

    /**
     * Instrucțiunile marcate ca utile
     */
    protected Set<Instruction> usefulInstructions = new HashSet<>();

    /**
     * Blocurile marcate ca utile, pe baza instrucțiunilor pe utile care le conțin.
     * Înlesnește determinarea facilă a
     * primului postdominator util al unui bloc
     */
    protected Set<BasicBlock> usefulBlocks = new HashSet<>();

    /**
     * Coada de instrucțiuni ce urmează a fi procesate prin prisma utilității
     */
    protected Deque<Instruction> instructionQueue = new ArrayDeque<>();

    /**
     * Arborele de postdominare
     */
    protected DominatorTree postDominatorTree;

    public UselessCodeElimination(final CFG cfg) {
        this.cfg = cfg;
    }

    /**
     * Determină instrucțiunile utile din CFG
     */
    public void computeUsefulInstructions() {
        this.usefulInstructions.clear();
        this.usefulBlocks.clear();
        this.instructionQueue.clear();

        // Arborele de postdominare este arborele de dominare al CFG-ului inversat (cu
        // arcele inversate)
        this.cfg.reverse();

        final var postDominatorAnalysis = new DominatorAnalysis(this.cfg);
        postDominatorAnalysis.runForward();

        this.postDominatorTree = new DominatorTree(postDominatorAnalysis);

        this.cfg.reverse();

        // TODO Identificăm toate instrucțiunile critice din CFG (RET și CALL)
        for (final var block : this.cfg.blocks)
            for (final var instruction : block.instructions) {
                // TODO Vedeți funcția markUsefulInstruction(Instruction) de mai jos
                if (instruction.iType == InstructionType.RET || instruction.iType == InstructionType.CALL)
                    this.markUsefulInstruction(instruction);
            }

        // Până la golirea cozii
        while (!this.instructionQueue.isEmpty()) {
            // Extragem o instrucțiune
            final var instruction = this.instructionQueue.removeFirst();

            // Parcurgem operanzii
            for (final var operand : instruction.operands) {
                // TODO Dacă operandul curent corespunde unei alte instrucțiuni, o marcăm pe
                // aceasta ca utilă
                if (operand instanceof final Instruction i) {
                    this.markUsefulInstruction(i);
                }

                // TODO Pentru instrucțiunile ϕ, ne interesează și blocurile referite, pentru a
                // marca salturile de la
                // finalului acestora drept utile
                if (instruction.iType == InstructionType.PHI) {
                    if (operand instanceof final BasicBlock b) {
                        this.markUsefulInstruction(b.instructions.getLast());
                    }
                }
            }

            // TODO În final, parcurgem blocurile din frontiera de postdominanță a blocului
            // care conține
            // instrucțiunea și marcăm salturile de la finalul acestora drept utile
            this.postDominatorTree.dominanceFrontiers.get(instruction.containingBlock)
                    .forEach(b -> this.markUsefulInstruction(b.instructions.getLast()));
        }
    }

    /**
     * Simplifică CFG-ul, eliminând sau rescriind instrucțiunile inutile
     */
    public void cleanup() {
        // Parcurgem toate blocurile
        for (final var block : this.cfg.blocks)
            // Parcurgem instrucțiunile din blocul curent. Utilizăm un iterator pentru a
            // putea șterge instrucțiuni pe
            // parcursul iterării.
            for (final var it = block.instructions.iterator(); it.hasNext();) {
                final var instruction = it.next();

                // Dacă instrucțiunea este utilă, nu ne atingem de ea
                if (this.usefulInstructions.contains(instruction))
                    continue;

                // TODO Un salt condiționat inutil trebuie rescris ca unul necondiționat către
                // cel mai apropiat
                // postdominator util
                if (instruction.isConditionalBranch()) {
                    var postDominator = block;

                    // TODO Determinăm cel mai apropiat postdominator util, consultând arborele de
                    // postdominanță
                    do {
                        postDominator = this.postDominatorTree.immediateDominators.get(postDominator);
                    } while (!this.usefulBlocks.contains(postDominator));

                    // TODO Îl stabilim pe acesta drept unic succesor al blocului curent.
                    // Vedeți metoda BasicBlock.setUniqueSuccesor(BasicBlock).
                    block.setUniqueSuccessor(postDominator);
                } else if (!instruction.isUnconditionalBranch()) {
                    // TODO Orice altă instrucțiune inutilă diferită de un salt este eliminată
                    it.remove();
                }
            }
    }

    /**
     * Dacă instrucțiunea nu a fost deja marcată drept utilă, marchează
     * instrucțiunea și blocul care o conține.
     * De asemenea, introduce instrucțiunea în coada de procesare.
     *
     * @param instruction instrucțiunea utilă
     */
    protected void markUsefulInstruction(final Instruction instruction) {
        if (this.usefulInstructions.contains(instruction))
            return;

        this.usefulInstructions.add(instruction);
        this.usefulBlocks.add(instruction.containingBlock);

        this.instructionQueue.add(instruction);
    }
}
