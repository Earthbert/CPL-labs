package optim;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import irgen.BasicBlock;
import irgen.InstructionType;
import semantic.IdSymbol;
import semantic.IntSymbol;
import semantic.Symbol;

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
    public void process(final BasicBlock block) {
        // Parcurgem fiecare instrucțiune din bloc
        for (final var instruction : block.instructions) {
            // Instrucțiune de copiere (dinspre o altă variabilă sau o constantă)
            if (instruction.iType == InstructionType.COPY) {
                // Unicul operand
                final var operand = instruction.operands.getFirst();

                // TODO Obținem din tabel informația despre unicul operand
                final var valueInfo = this.getValueInfo(operand, operand);

                // TODO Rescriem operandul cu reprezentarea sa canonică
                instruction.operands.set(0, valueInfo.canonical());

                // TODO Conectăm în tabel rezultatul la operand.
                // Utilizați metoda getResult() a clasei Instruction
                this.getValueInfo(instruction.getResult(), valueInfo.canonical());
            }
            // Operație aritmetică binară
            else if (Arrays.asList(InstructionType.ADD, InstructionType.SUB, InstructionType.MUL, InstructionType.DIV)
                    .contains(instruction.iType)) {
                // Cei doi operanzi
                final var leftOperand = instruction.operands.get(0);
                final var rightOperand = instruction.operands.get(1);

                // TODO Obținem din tabel informațiile celor doi operanzi
                var leftValueInfo = this.getValueInfo(leftOperand, leftOperand);
                var rightValueInfo = this.getValueInfo(rightOperand, rightOperand);

                // TODO Dacă operația este comutativă, ordonăm crescător numerele celor doi
                // operanzi
                if (Arrays.asList(InstructionType.ADD, InstructionType.MUL)
                        .contains(instruction.iType) && leftValueInfo.number() > rightValueInfo.number()) {
                    final var tmp = leftValueInfo;
                    leftValueInfo = rightValueInfo;
                    rightValueInfo = tmp;
                }

                // TODO Construim cheia formată din operație, numărul operandului stâng, numărul
                // operandului drept
                // Exemplu: ADD #0 #1
                // Dacă expresia a fost deja evaluată, numele canonic este cel stocat în tabel.
                // Altfel, este rezultatul curent.
                final String opString = instruction.iType.toString() +
                        " #" + leftValueInfo.number() +
                        " #" + rightValueInfo.number();

                ValueInfo keyInfo = null;
                // Both operands are constants
                if (leftValueInfo.canonical() instanceof final IntSymbol leftIntSymbol &&
                        rightValueInfo.canonical() instanceof final IntSymbol rightIntSymbol) {

                    final Integer result = switch (instruction.iType) {
                        case InstructionType.ADD -> leftIntSymbol.value + rightIntSymbol.value;
                        case InstructionType.SUB -> leftIntSymbol.value - rightIntSymbol.value;
                        case InstructionType.MUL -> leftIntSymbol.value * rightIntSymbol.value;
                        case InstructionType.DIV -> leftIntSymbol.value / rightIntSymbol.value;
                        default -> null;
                    };

                    final var resultValueInfo = this.getValueInfo(IntSymbol.get(result), IntSymbol.get(result));
                    this.getValueInfo(opString, resultValueInfo.canonical());
                    keyInfo = this.getValueInfo(instruction.getResult(), resultValueInfo.canonical());
                } else if (leftValueInfo.canonical() instanceof final IntSymbol leftIntSymbol &&
                        leftIntSymbol.value == 0 && (instruction.iType == InstructionType.ADD
                                || instruction.iType == InstructionType.SUB)) {
                    // Left operand is 0 in ADD or SUB
                    keyInfo = this.getValueInfo(instruction.getResult(),
                            this.getValueInfo(opString, rightValueInfo.canonical()).canonical());
                } else if (rightValueInfo.canonical() instanceof final IntSymbol rightIntSymbol &&
                        rightIntSymbol.value == 0 && (instruction.iType == InstructionType.ADD
                                || instruction.iType == InstructionType.SUB)) {
                    // Right operand is 0 in ADD or SUB
                    keyInfo = this.getValueInfo(instruction.getResult(),
                            this.getValueInfo(opString, leftValueInfo.canonical()).canonical());
                } else if (leftValueInfo.canonical() instanceof final IntSymbol leftIntSymbol &&
                        leftIntSymbol.value == 1 && (instruction.iType == InstructionType.MUL
                                || instruction.iType == InstructionType.DIV)) {
                    // Left operand is 1 in MUL or DIV
                    keyInfo = this.getValueInfo(instruction.getResult(),
                            this.getValueInfo(opString, rightValueInfo.canonical()).canonical());
                } else if (rightValueInfo.canonical() instanceof final IntSymbol rightIntSymbol &&
                        rightIntSymbol.value == 1 && (instruction.iType == InstructionType.MUL
                                || instruction.iType == InstructionType.DIV)) {
                    // Right operand is 1 in MUL or DIV
                    keyInfo = this.getValueInfo(instruction.getResult(),
                            this.getValueInfo(opString, leftValueInfo.canonical()).canonical());
                } else if (leftValueInfo.canonical() instanceof final IntSymbol leftIntSymbol &&
                        leftIntSymbol.value == 0 && (instruction.iType == InstructionType.MUL)) {
                    // Left operand is 0 in MUL
                    keyInfo = this.getValueInfo(instruction.getResult(),
                            this.getValueInfo(opString, IntSymbol.get(0)).canonical());
                } else if (rightValueInfo.canonical() instanceof final IntSymbol rightIntSymbol &&
                        rightIntSymbol.value == 0 && (instruction.iType == InstructionType.MUL)) {
                    // Right operand is 0 in MUL
                    keyInfo = this.getValueInfo(instruction.getResult(),
                            this.getValueInfo(opString, IntSymbol.get(0)).canonical());
                } else {
                    keyInfo = this.getValueInfo(instruction.getResult(),
                            this.getValueInfo(opString, instruction.getResult()).canonical());
                }

                if (!keyInfo.canonical().equals(instruction.getResult())) {
                    instruction.iType = InstructionType.COPY;
                    instruction.operands = List.of(keyInfo.canonical());
                }
            }
        }

    }

    /**
     * Determină reprezentarea unei valori abstracte, pe baza cheii. Dacă cheia
     * există deja, intrarea corespunzătoare
     * din tabel este întoarsă. Altfel, este creată o nouă intrare pe baza cheii,
     * utilizând al doilea parametru
     * pentru reprezentarea canonică și un număr nou.
     *
     * @param value     cheia de căutare din tabel
     * @param canonical reprezentarea canonică a valorii
     * @return intrarea din tabel (existentă sau nouă)
     */
    private ValueInfo getValueInfo(final Object value, final Symbol canonical) {
        var valueInfo = this.valueInfos.get(value);

        if (valueInfo == null) {
            final var opValueInfo = this.valueInfos.values().stream().filter(v -> v.canonical().equals(canonical))
                    .findFirst().map(v -> new ValueInfo(v.number(), canonical));
            if (opValueInfo.isPresent()) {
                valueInfo = opValueInfo.get();
            } else {
                valueInfo = new ValueInfo(this.valueNumber++, canonical);
            }
            this.valueInfos.put(value, valueInfo);
        }

        return valueInfo;
    }

    /**
     * Reprezentarea unei valori abstracte
     *
     * @param number    numărul valorii
     * @param canonical reprezentarea canonică a valorii (constantă sau variabilă)
     */
    record ValueInfo(Integer number, Symbol canonical) {
    }

    /**
     * Reprezentarea sub formă de șir a analizei conține tabelul cu 3 coloane
     * populat pe parcurs
     *
     * @return reprezentarea ca șir
     */
    @Override
    public String toString() {
        return String.format("%-4s %-12s %s\n", "#", "key", "canonical")
                + this.valueInfos.entrySet().stream()
                        .map(entry -> String.format(
                                "%-4s %-12s %s",
                                entry.getValue().number,
                                entry.getKey() instanceof final IdSymbol symbol ? symbol.getDisplayName()
                                        : entry.getKey(),
                                entry.getValue().canonical.getDisplayName()))
                        .collect(Collectors.joining("\n"));
    }
}
