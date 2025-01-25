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
     * Adaugă o nouă instrucțiune la acest bloc, care la rândul său este marcat drept bloc conținător pentru
     * instrucțiune. Instrucțiunile PHI (la transformarea în forma SSA) sunt adăugate la începutul blocului. Toate
     * celelalte instrucțiuni sunt adăugate la final. Dacă instrucțiunea este de salt (BR), se actualizează
     * succesorii blocului curent, pe baza operanzilor instrucțiunii de salt, iar blocul curent devine predecesor
     * pentru toți succesorii lui.
     *
     * @param instruction instrucțiunea de adăugat
     */
    public void addInstruction(Instruction instruction) {
        // Blocul curent conține instrucțiunea
        instruction.containingBlock = this;

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

    /**
     * Stabilește un unic succesor al blocului curent, care obligatoriu trebuia să se încheie cu un salt. Elimină
     * ceilalți succesori, și blocul curent ca predecesor ai succesorilor eliminați.
     *
     * @param uniqueSuccessor
     */
    public void setUniqueSuccessor(BasicBlock uniqueSuccessor) {
        // Ultima instrucțiune din bloc trebuie să fie de salt
        var branch = instructions.getLast();

        if (branch.iType != InstructionType.BR)
            throw new IllegalStateException("Block does not end in a branch");

        // Actualizăm operanzii instrucțiunii de salt pentru a indica unicul succesor
        branch.operands = List.of(uniqueSuccessor);

        // Parcurgem succesorii blocului curent și îi eliminăm pe cei diferiți de unicul succesor păstrat.
        // De asemenea, eliminăm blocul curent ca predecesor ai succesorilor înlăturați.
        for (var it = successors.iterator(); it.hasNext(); ) {
            var successor = it.next();

            if (successor != uniqueSuccessor) {
                it.remove();
                successor.predecessors.remove(this);
            }
        }

        // Dacă lista de succesori devine vidă, înseamnă că unicul succesor nu se găsea printre cei vechi, și trebuie
        // adăugat acum
        if (successors.isEmpty())
            addSuccessor(uniqueSuccessor);
    }

    @Override
    public String toString() {
        return name + ":\n\t" + instructions.stream()
                .map(Instruction::toString)
                .collect(Collectors.joining("\n\t"));
    }
}
