package optim;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import irgen.BasicBlock;
import irgen.CFG;
import irgen.InstructionType;
import semantic.IntSymbol;

/**
 * Eliminarea codului neexecutabil
 */
public class UnreachableCodeElimination {
    protected CFG cfg;

    public Set<BasicBlock> reachableBlocks = new HashSet<>();

    public UnreachableCodeElimination(final CFG cfg) {
        this.cfg = cfg;
    }

    /**
     * Verifică dacă salturile condiționate de la finalul blocurilor au condiții
     * constante. În caz afirmativ, rescrie
     * saltul cu unul necondiționat către unicul succesor valabil, înlătură ceilalți
     * succesori ai blocului curent,
     * și înlătură blocul curent dintre predecesorii succesorilor eliminați.
     */
    public void processConditionalBranches() {
        // Parcurgem toate blocurile
        for (final var block : this.cfg.blocks) {
            // TODO Ne interesează doar dacă ultima instrucțiune este salt condiționat: BR
            // conditie blocAdev blocFals
            final var branch = block.instructions.getLast();
            if (block.instructions.isEmpty() || !branch.isConditionalBranch())
                continue;

            // TODO Verificăm dacă condiția saltului (primul operand) este o constantă
            // întreagă (0/1)
            if ((branch.operands.getFirst() instanceof final IntSymbol intCondition)) {

                // TODO Determinăm blocul pe care îl păstrăm drept țintă și rescriem ca salt
                // necondiționat: BR bloc
                branch.iType = InstructionType.BR;
                final int successorIndex = intCondition.value == 1 ? 1 : 2;
                branch.operands = List.of(branch.operands.get(successorIndex));

                // TODO Parcurgem succesorii blocului curent și îi eliminăm pe cei diferiți de
                // țintă. De asemenea, eliminăm
                // blocul curent din listele de predecesori ai succesorilor înlăturați.
                block.successors.removeIf(successor -> successor != branch.operands.getFirst());
            }

        }
    }

    /**
     * Determină blocurile executabile, pornind de la blocul de intrare din CFG
     */
    public void computeReachableBlocks() {
        this.reachableBlocks.clear();
        this.computeReachableBlocks(this.cfg.blocks.getFirst());
    }

    /**
     * Determină blocurile executabile pornind de la un bloc fixat
     *
     * @param block blocul de plecare
     */
    protected void computeReachableBlocks(final BasicBlock block) {
        // TODO Marcăm blocul curent ca executabil
        this.reachableBlocks.add(block);

        // TODO Determinăm blocurile accesibile din blocul curent, evitând ciclurile
        for (final var successor : block.successors)
            if (!this.reachableBlocks.contains(successor))
                this.computeReachableBlocks(successor);
    }

    /**
     * Simplifică CFG-ul în funcție de blocurile marcate ca neexecutabile. Acestea
     * sunt înlăturate cu totul, inclusiv
     * din listele de predecesori ale blocurilor executabile care le succed. În
     * final, instrucțiunile ϕ din blocurile
     * executabile în care mai intră un singur predecesor (în urma înlăturării
     * predecesorilor neexecutabili) sunt
     * înlocuite cu instrucțiuni de copiere a versiunii furnizate de predecesorul
     * rămas.
     */
    public void cleanup() {
        // TODO Păstrăm în CFG doar blocurile executabile
        this.cfg.blocks.removeIf(block -> !this.reachableBlocks.contains(block));

        // Parcurgem blocurile executabile, singurele rămase în CFG, după implementarea
        // punctului anterior
        for (final var block : this.cfg.blocks) {
            // TODO Dacă blocul curent are predecesori neexecutabili, îi înlăturăm din lista
            // de predecesori
            block.predecessors.removeIf(predecessor -> !this.reachableBlocks.contains(predecessor));

            // TODO Pentru blocurile cu un singur predecesor, verificăm dacă există
            // instrucțiuni ϕ, de pe vremea când
            // blocul avea mai mulți predecesori, și le înlocuim cu instrucțiuni de copiere
            // a singurului operand valid
            if (block.predecessors.size() == 1)
                for (final var instruction : block.instructions) {
                    if (instruction.iType == InstructionType.PHI) {
                        final int keptOperandIndex = instruction.operands.indexOf(block.predecessors.getFirst());
                        instruction.iType = InstructionType.COPY;
                        instruction.operands = List.of(instruction.operands.get(keptOperandIndex - 2));
                    }
                }
        }
    }
}
