package irgen;

import semantic.IdSymbol;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Basic block, care constituie un nod al CFG-ului. Include o listă ordonată de instrucțiuni.
 *
 * Fiecare basic block este caracterizat de eticheta sa la care se pot realiza salturi, motiv pentru care extinde
 * IdSymbol.
 */
public class BasicBlock extends IdSymbol {
    /**
     * Lista ordonată de instrucțiuni
     */
    public List<Instruction> instructions = new ArrayList<>();

    /**
     * Lista ordonată de succesori ai blocului curent.
     */
    public List<BasicBlock> successors = new ArrayList<>();

    /**
     * Lista ordonată de predecesori ai blocului curent.
     */
    public List<BasicBlock> predecessors = new ArrayList<>();

    /**
     * Toate blocurile sunt etichetate
     *
     * @param name eticheta
     */
    public BasicBlock(String name) {
        super(name);
        applyNextVersion();
    }

    /**
     * Adaugă o nouă instrucțiune la acest bloc. Instrucțiunile de tipul PHI (la transformarea în forma SSA) sunt
     * adăugate la începutul blocului. Toate celelalte instrucțiuni sunt adăugate la final. Dacă instrucțiunea este
     * de salt (BR), se actualizează succesorii blocului curent, pe baza operanzilor instrucțiunii de salt, iar blocul
     * curent devine predecesor pentru toți succesorii lui.
     *
     * @param instruction instrucțiunea de adăugat
     */
    public void addInstruction(Instruction instruction) {
        // Instrucțiunile PHI sunt adăugate la începutul blocului
        if (instruction.iType == InstructionType.PHI) {
            instructions.addFirst(instruction);
            return;
        }

        // Toate celelalte instrucțiuni sunt adăugate la final
        instructions.add(instruction);

        // În cazul unei instrucțiuni de salt, se actualizează lista de succesori.
        if (instruction.iType == InstructionType.BR) {
            // condiționat: BR condiție bloc1 bloc2
            if (instruction.operands.size() == 3) {
                addSuccessor((BasicBlock) instruction.operands.get(1));
                addSuccessor((BasicBlock) instruction.operands.get(2));
            }
            // necondiționat: BR bloc0
            else
                addSuccessor((BasicBlock) instruction.operands.get(0));
        }
    }

    /**
     * Asigură legăturile bidirecționale dintre blocuri: blocul curent capătă un succesor, iar succesorul, blocul curent
     * ca predecesor.
     *
     * @param successor blocul succesor
     */
    public void addSuccessor(BasicBlock successor) {
        successors.add(successor);
        successor.predecessors.add(this);
    }

    @Override
    public String toString() {
        return name + ":\n\t" + instructions.stream()
                .map(Instruction::toString)
                .collect(Collectors.joining("\n\t"));
    }
}
