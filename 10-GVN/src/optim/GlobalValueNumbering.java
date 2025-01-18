package optim;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import analysis.DominatorAnalysis;
import analysis.DominatorTree;
import irgen.BasicBlock;
import irgen.CFG;
import irgen.InstructionType;
import semantic.IdSymbol;
import semantic.IntSymbol;
import semantic.Symbol;

/**
 * Extensia strategiei Value Numbering la nivelul unui întreg CFG, deja
 * transformat în forma SSA.
 *
 * Principiul este de a asocia o valoare abstractă, caracterizată de un anumit
 * număr (value number), tuturor expresiilor
 * care calculează aceeași valoare concretă (eventual necunoscută) pentru toate
 * execuțiile posibile ale CFG-ului.
 * Fiecare valoare abstractă are asociată o reprezentare canonică (nume sau
 * constantă), ce reține valoarea concretă.
 * Reprezentările canonice sunt utilizate pentru rescrierea instrucțiunilor în
 * scopul reutilizării valorilor deja
 * calculate. Strategia permite implementare unei plaje largi de optimizări:
 * common subexpression elimination,
 * cu valorificarea informației de comutativitate, constant folding, algebraic
 * simplification, constant/copy propagation.
 *
 * Utilizează un tabel, în care cheile sunt nume, expresii complexe sau
 * constante, iar valorile sunt perechi
 * (value number, reprezentare canonică). Cheile pentru expresii complexe se
 * construiesc utilizând numele operatorului
 * și value numbers ale operanzilor, pentru decuplarea de reprezentarea lor
 * textuală și obținerea unei flexibilități
 * sporite. De exemplu, pentru expresia ADD a b, presupunând că a și b au value
 * numbers #0, respectiv #1 (a nu se
 * confunda cu literali întregi), cheia este ADD #0 #1.
 *
 * Modalitatea de propagare a analizei în CFG mimează pașii (1)-(5) descriși
 * pentru etapa (2), de redenumire,
 * a transformării în forma SSA. Vezi comentariile metodei rename(BasicBlock)
 * din clasa SSATransformer.
 */
public class GlobalValueNumbering {
    /**
     * Value number curent
     */
    int valueNumber = 0;

    /**
     * Reprezentarea tabelului
     */
    Map<Object, ValueInfo> valueInfos = new LinkedHashMap<>();

    CFG cfg;

    DominatorTree dominatorTree;

    public GlobalValueNumbering(final CFG cfg) {
        this.cfg = cfg;

        // Dacă CFG-ul corespunde unei funcții, parametrii formali trebuie adăugați în
        // tabel la început pentru a fi
        // accesibili într-o manieră uniformă pe toate căile CFG-ului
        final var functionSymbol = cfg.functionSymbol;
        if (functionSymbol != null)
            functionSymbol.symbols.values().forEach(param -> this.getValueInfo(param, param));
    }

    /**
     * Propagarea începe cu blocul de intrare în CFG și continuă recursiv conform
     * unei parcurgeri în preordine
     * a arborelui de dominanță.
     */
    public void propagate() {
        final var dominatorAnalysis = new DominatorAnalysis(this.cfg);
        dominatorAnalysis.runForward();

        this.dominatorTree = new DominatorTree(dominatorAnalysis);
        this.valueNumber = 0;

        this.propagate(this.cfg.blocks.getFirst());
    }

    /**
     * Modalitatea de propagare a analizei în CFG mimează pașii (1)-(5) descriși
     * pentru etapa (2), de redenumire,
     * a transformării în forma SSA. Vezi comentariile metodei rename(BasicBlock)
     * din clasa SSATransformer.
     *
     * @param block blocul curent
     */
    protected void propagate(final BasicBlock block) {
        // La intrarea într-un bloc, se salvează conținutul curent al tabelului,
        // întrucât noile intrări vor fi
        // înlăturate (vezi explicațiile de la finalul metodei)
        final var savedValueInfos = new LinkedHashMap<>(this.valueInfos);

        // Analog pașilor (1)-(2) menționați mai sus: Prelucrarea instrucțiunilor
        // blocului curent, ϕ și de alte tipuri
        this.process(block);

        // Analog pasului (3) menționat mai sus: Popularea argumentelor instrucțiunilor
        // ϕ din fiecare succesor din CFG
        // al blocului curent
        for (final var successor : block.successors) {
            for (final var instruction : successor.instructions) {
                if (instruction.iType != InstructionType.PHI)
                    continue;

                final var predecessorIndex = successor.predecessors.indexOf(block);
                instruction.operands.set(
                        predecessorIndex,
                        this.getValueInfo(instruction.operands.get(predecessorIndex), null).canonical);
            }
        }

        // Analog pasului (4) menționat mai sus: Propagarea recursivă către copiii din
        // arborele de dominanță,
        // i.e. către blocurile dominate direct (parcurgere în preordine, pentru că am
        // prelucrat deja blocul curent)
        this.dominatorTree.childrenSets.get(block).forEach(this::propagate);

        // Analog pasului (5): Eliminarea din tabel a intrărilor introduse în cadrul
        // acestui subarbore de dominanță
        this.valueInfos = savedValueInfos;
    }

    /**
     * Procesează instrucțiunile din blocul curent. Față de metoda process din clasa
     * ValueNumbering discutată
     * în laboratorul 9, se adaugă un tratament special al instrucțiunilor ϕ, astfel
     * încât, dacă toți operanzii valoare
     * ai acesteia coincid, instrucțiunea ϕ poate fi înlocuită cu una de copiere a
     * unui operand. De exemplu,
     * a = ϕ b b bloc0 bloc1 poate fi înlocuită cu a = COPY b.
     *
     * @param block blocul curent
     */
    protected void process(final BasicBlock block) {
        // Parcurgem fiecare instrucțiune din bloc
        for (final var instruction : block.instructions) {
            // Instrucțiune ϕ
            if (instruction.iType == InstructionType.PHI) {
                // TODO Verificăm dacă toți operanzii valoare ai instrucțiunii ϕ sunt identici.
                // Ne uităm la prima
                // jumătate a operanzilor, întrucât cealaltă jumătate sunt blocuri.
                // Completați condiția if-ului.
                if (instruction.operands.get(0) == instruction.operands.get(1)) {
                    instruction.operands = Arrays.asList(instruction.operands.get(0));
                    instruction.iType = InstructionType.COPY;
                } else {
                    final int numOperands = instruction.operands.size();
                    // TODO Construim cheia
                    final var key = InstructionType.PHI
                            + instruction.operands.subList(0, numOperands / 2)
                                    .stream().map(operand -> this.getValueInfo(operand, operand).number.toString())
                                    .collect(Collectors.joining(" #", " #", ""))
                            + instruction.operands.subList(numOperands / 2, numOperands)
                                    .stream().map(Symbol::getDisplayName)
                                    .collect(Collectors.joining(" ", " ", ""));

                    // TODO Dacă expresia a fost deja evaluată, numele canonic este cel stocat în
                    // tabel.
                    // Altfel, numele canonic este numele curent.
                    final var keyInfo = this.getValueInfo(key, instruction);

                    // TODO keyInfo constituie o intrare anterioară din tabel (expresia a fost
                    // evaluată anterior)
                    // Completați condiția if-ului
                    if (keyInfo.canonical != instruction) {
                        // TODO Schimbăm tipul instrucțiunii ca fiind de copiere și refacem lista de operanzi, care
                        //  conține acum o singură intrare, cu rezultatul calculat anterior
                        instruction.iType = InstructionType.COPY;
                        instruction.operands = Arrays.asList(keyInfo.canonical);
                    }

                    // TODO Indiferent dacă expresia a fost evaluată anterior sau nu, rezultatul
                    // operației trebuie
                    // să aibă aceeași informație cu cheia expresiei
                    this.valueInfos.put(instruction, keyInfo);
                }
            }
            // Instrucțiune de copiere, care acoperă și cazul în care instrucțiunea ϕ de mai
            // sus a fost transformată
            // în copiere
            if (instruction.iType == InstructionType.COPY) {
                // Obținem informația despre unicul operand
                final var operand = instruction.operands.getFirst();
                final var operandInfo = this.getValueInfo(operand, operand);

                // Rescriem operandul cu reprezentarea sa canonică
                instruction.operands = Arrays.asList(operandInfo.canonical);

                // Conectăm rezultatul la operand
                this.valueInfos.put(instruction, operandInfo);
            }
            // Operație aritmetică binară sau relațională
            else if (Arrays.asList(InstructionType.ADD, InstructionType.SUB, InstructionType.MUL, InstructionType.DIV,
                    InstructionType.CMP_EQ, InstructionType.CMP_LT, InstructionType.CMP_LE)
                    .contains(instruction.iType)) {
                // Cei 2 operanzi
                final var leftOperand = instruction.operands.get(0);
                final var rightOperand = instruction.operands.get(1);

                // Informațiile aferente celor 2 operanzi
                final var leftInfo = this.getValueInfo(leftOperand, leftOperand);
                final var rightInfo = this.getValueInfo(rightOperand, rightOperand);

                // Dacă operația este comutativă, ordonăm crescător numerele celor 2 operanzi
                final var operandNumbers = new int[] { leftInfo.number, rightInfo.number };
                if (instruction.isCommutative())
                    Arrays.sort(operandNumbers);

                // Construim cheia formată din operație, numărul operandului stâng, numărul
                // operandului drept
                final var key = instruction.iType + " #" + operandNumbers[0] + " #" + operandNumbers[1];

                // Dacă expresia a fost deja evaluată, numele canonic este cel stocat în tabel.
                // Altfel, este numele curent.
                final var keyInfo = this.getValueInfo(key, instruction);

                // Common subexpression elimination: keyInfo constituie o intrare anterioară din
                // tabel
                // (expresia a fost evaluată anterior)
                if (keyInfo.canonical != instruction) {
                    // Schimbăm tipul instrucțiunii ca fiind de copiere și refacem lista de
                    // operanzi, care conține acum
                    // o singură intrare, cu rezultatul calculat anterior
                    instruction.iType = InstructionType.COPY;
                    instruction.operands = Arrays.asList(keyInfo.canonical);
                }
                // Expresia nu a fost evaluată anterior: keyInfo constituie o intrare nouă
                else {
                    // Constant folding
                    if (leftInfo.canonical instanceof final IntSymbol left
                            && rightInfo.canonical instanceof final IntSymbol right) {
                        // Vom suprascrie intrarea proaspăt creată pentru cheie, deci putem scădea
                        // numărul curent,
                        // pentru a evita numerele lipsă din tabel
                        this.valueNumber--;

                        final var resultValue = switch (instruction.iType) {
                            case ADD -> left.value + right.value;
                            case SUB -> left.value - right.value;
                            case MUL -> left.value * right.value;
                            case DIV -> left.value / right.value;
                            case CMP_EQ -> left.value == right.value ? 1 : 0;
                            case CMP_LT -> left.value < right.value ? 1 : 0;
                            case CMP_LE -> left.value <= right.value ? 1 : 0;
                            default -> throw new UnsupportedOperationException();
                        };

                        // Stocăm rezultatul constant în tabel
                        final var result = IntSymbol.get(resultValue);
                        final var resultInfo = this.getValueInfo(result, result);

                        // Schimbăm tipul instrucțiunii ca fiind de copiere și refacem lista de
                        // operanzi,
                        // care conține acum o singură intrare, cu rezultatul constant
                        instruction.iType = InstructionType.COPY;
                        instruction.operands = Arrays.asList(result);

                        // Conectăm cheia la operand
                        this.valueInfos.put(key, resultInfo);
                    }
                    // Algebraic simplification: 0 + x = x
                    else if (instruction.iType == InstructionType.ADD
                            && leftInfo.canonical instanceof final IntSymbol literal
                            && literal.value == 0) {
                        // Vom suprascrie intrarea proaspăt creată pentru cheie, deci putem scădea
                        // numărul curent,
                        // pentru a evita numerele lipsă din tabel
                        this.valueNumber--;

                        // Schimbăm tipul instrucțiunii ca fiind de copiere și refacem lista de
                        // operanzi,
                        // care conține acum o singură intrare, cu celălalt operand
                        instruction.iType = InstructionType.COPY;
                        instruction.operands = Arrays.asList(rightInfo.canonical);

                        // Conectăm cheia la operandul rămas
                        this.valueInfos.put(key, rightInfo);
                    }
                }

                // Indiferent dacă expresia a fost evaluată anterior sau nu, rezultatul
                // operației trebuie
                // să aibă aceeași informație cu cheia expresiei
                this.valueInfos.put(instruction, this.getValueInfo(key, null));
            }

            // Indiferent de tipul instrucțiunii, putem încerca să propagăm copii/constante
            // pt operanzi,
            // în cazul în care nu s-au putut realiza prelucrările mai specifice de mai sus
            instruction.operands = instruction.operands.stream()
                    .map(operand -> operand instanceof BasicBlock ? operand
                            : this.getValueInfo(operand, operand).canonical)
                    .collect(Collectors.toList());
        }
    }

    /**
     * Determină reprezentarea unei valori abstracte, pe baza cheii. Dacă cheia
     * există deja, intrarea corespunzătoare
     * din tabel este întoarsă. Altfel, este creată o nouă intrare pe baza cheii,
     * utilizând al doilea parametru
     * pentru reprezentarea canonică și un număr nou.
     *
     * @param key       cheia de căutare din tabel
     * @param canonical reprezentarea canonică a valorii, utilizată pentru intrări
     *                  noi
     * @return intrarea din tabel (existentă sau nouă)
     */
    private ValueInfo getValueInfo(final Object key, final Symbol canonical) {
        // Verificăm existența unei intrări pentru cheia curentă
        var valueInfo = this.valueInfos.get(key);

        // Dacă nu exista, o adăugăm, generăm următorul value number și stocăm
        // reprezentarea canonică furnizată
        if (valueInfo == null) {
            valueInfo = new ValueInfo(this.valueNumber++, canonical);
            this.valueInfos.put(key, valueInfo);
        }

        return valueInfo;
    }

    /**
     * Pereche (value number, reprezentare canonică), aferentă unei chei din tabel
     *
     * @param number    value number
     * @param canonical reprezentare canonică
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
        return String.format("%-4s %-30s %s\n", "#", "key", "canonical")
                + this.valueInfos.entrySet().stream()
                        .map(entry -> String.format(
                                "%-4s %-30s %s",
                                entry.getValue().number,
                                entry.getKey() instanceof final IdSymbol symbol ? symbol.getDisplayName()
                                        : entry.getKey(),
                                entry.getValue().canonical.getDisplayName()))
                        .collect(Collectors.joining("\n"));
    }
}
