package optim;

import irgen.*;
import semantic.*;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ValueNumbering {
    int valueNumber = 0;

    /**
     * Tabelul valorilor
     */
    Map<Object, ValueInfo> valueInfos = new LinkedHashMap<>();

    /**
     * Prelucrează un singur basic bloc
     *
     * @param block blocul de prelucrat
     */
    public void process(BasicBlock block) {
        // Parcurgem fiecare instrucțiune din bloc
        for (var instruction : block.instructions) {
            // Instrucțiune de copiere (dinspre o altă variabilă sau o constantă)
            if (instruction.iType == InstructionType.COPY) {
                // TODO Obținem din tabel informația despre unicul operand
                var operand = instruction.operands.getFirst();
                var operandInfo = getValueInfo(operand, operand);

                // TODO Rescriem operandul cu reprezentarea sa canonică
                instruction.operands = Arrays.asList(operandInfo.canonical);

                // TODO Conectăm în tabel rezultatul la operand.
                //  Utilizați metoda getResult() a clasei Instruction
                valueInfos.put(instruction.getResult(), operandInfo);
            }
            // Operație aritmetică binară
            else if (Arrays.asList(InstructionType.ADD, InstructionType.SUB, InstructionType.MUL, InstructionType.DIV)
                    .contains(instruction.iType)) {
                // Cei doi operanzi
                var leftOperand = instruction.operands.get(0);
                var rightOperand = instruction.operands.get(1);

                // TODO Obținem din tabel informațiile celor doi operanzi
                var leftInfo = getValueInfo(leftOperand, leftOperand);
                var rightInfo = getValueInfo(rightOperand, rightOperand);

                // TODO Dacă operația este comutativă, ordonăm crescător numerele celor doi operanzi
                var operandNumbers = new int[] { leftInfo.number, rightInfo.number };
                if (instruction.isCommutative())
                    Arrays.sort(operandNumbers);

                // TODO Construim cheia formată din operație, numărul operandului stâng, numărul operandului drept
                //  Exemplu: ADD #0 #1
                //  Dacă expresia a fost deja evaluată, numele canonic este cel stocat în tabel.
                //  Altfel, este rezultatul curent.
                var key = instruction.iType + " #" + operandNumbers[0] + " #" + operandNumbers[1];
                var keyInfo = getValueInfo(key, instruction.getResult());

                // TODO Common subexpression elimination: keyInfo constituie o intrare anterioară din tabel
                //  (expresia a fost evaluată anterior)
                if (keyInfo.canonical != instruction.getResult()) {
                    // TODO Schimbăm tipul instrucțiunii ca fiind de copiere și refacem lista de operanzi, care conține
                    //  acum o singură intrare, cu rezultatul calculat anterior
                    instruction.iType = InstructionType.COPY;
                    instruction.operands = Arrays.asList(keyInfo.canonical);
                }
                // TODO Expresia nu a fost evaluată anterior: keyInfo constituie o intrare nouă
                else {
                    // TODO Constant folding: ambii operanzi sunt literali întregi
                    if (leftInfo.canonical instanceof IntSymbol left && rightInfo.canonical instanceof IntSymbol right) {
                        // Vom suprascrie intrarea proaspăt creată pentru cheie, deci putem scădea numărul curent,
                        // pentru a evita numerele lipsă din tabel
                        valueNumber--;

                        // TODO Evaluăm instrucțiunea
                        var resultValue = switch (instruction.iType) {
                            case ADD -> left.value + right.value;
                            case SUB -> left.value - right.value;
                            case MUL -> left.value * right.value;
                            case DIV -> left.value / right.value;
                            default -> throw new UnsupportedOperationException();
                        };

                        // TODO Stocăm rezultatul constant în tabel
                        var result = IntSymbol.get(resultValue);
                        var resultInfo = getValueInfo(result, result);

                        // TODO Schimbăm tipul instrucțiunii ca fiind de copiere și refacem lista de operanzi,
                        //  care conține acum o singură intrare, cu rezultatul constant
                        instruction.iType = InstructionType.COPY;
                        instruction.operands = Arrays.asList(result);

                        // TODO Conectăm în tabel cheia la operandul constant
                        valueInfos.put(key, resultInfo);
                    }
                    // TODO Algebraic simplification: 0 + x = x
                    else if (instruction.iType == InstructionType.ADD
                            && leftInfo.canonical instanceof IntSymbol literal
                            && literal.value == 0) {
                        // Vom suprascrie intrarea proaspăt creată pentru cheie, deci putem scădea numărul curent,
                        // pentru a evita numerele lipsă din tabel
                        valueNumber--;

                        // TODO Schimbăm tipul instrucțiunii ca fiind de copiere și refacem lista de operanzi,
                        //  care conține acum o singură intrare, cu celălalt operand
                        instruction.iType = InstructionType.COPY;
                        instruction.operands = Arrays.asList(rightInfo.canonical);

                        // TODO Conectăm cheia la operandul rămas
                        valueInfos.put(key, rightInfo);
                    }
                }

                // TODO Indiferent dacă expresia a fost evaluată anterior sau nu, rezultatul operației trebuie
                //  să aibă aceeași informație cu cheia expresiei
                valueInfos.put(instruction.getResult(), getValueInfo(key, null));
            }

            // TODO Indiferent de tipul instrucțiunii, putem încerca să propagăm copii/constante pt operanzi,
            //  în cazul în care nu s-au putut realiza prelucrările mai specifice de mai sus
            instruction.operands = instruction.operands.stream()
                    .map(operand -> getValueInfo(operand, operand).canonical)
                    .collect(Collectors.toList());
        }
    }

    /**
     * Determină reprezentarea unei valori abstracte, pe baza cheii. Dacă cheia există deja, intrarea corespunzătoare
     * din tabel este întoarsă. Altfel, este creată o nouă intrare pe baza cheii, utilizând al doilea parametru
     * pentru reprezentarea canonică și un număr nou.
     *
     * @param value     cheia de căutare din tabel
     * @param canonical reprezentarea canonică a valorii
     * @return          intrarea din tabel (existentă sau nouă)
     */
    private ValueInfo getValueInfo(Object value, Symbol canonical) {
        var valueInfo = valueInfos.get(value);

        if (valueInfo == null) {
            valueInfo = new ValueInfo(valueNumber++, canonical);
            valueInfos.put(value, valueInfo);
        }

        return valueInfo;
    }

    /**
     * Reprezentarea unei valori abstracte
     *
     * @param number    numărul valorii
     * @param canonical reprezentarea canonică a valorii (constantă sau variabilă)
     */
    record ValueInfo(Integer number, Symbol canonical) {}

    /**
     * Reprezentarea sub formă de șir a analizei conține tabelul cu 3 coloane populat pe parcurs
     *
     * @return reprezentarea ca șir
     */
    @Override
    public String toString() {
        return String.format("%-4s %-12s %s\n", "#", "key", "canonical")
                + valueInfos.entrySet().stream()
                .map(entry -> String.format(
                        "%-4s %-12s %s",
                        entry.getValue().number,
                        entry.getKey() instanceof IdSymbol symbol ? symbol.getDisplayName() : entry.getKey(),
                        entry.getValue().canonical.getDisplayName()))
                .collect(Collectors.joining("\n"));
    }
}
