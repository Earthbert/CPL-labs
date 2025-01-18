package analysis;

import irgen.BasicBlock;
import irgen.CFG;
import irgen.Instruction;

import java.util.*;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * Clasă de bază pentru reprezentarea analizelor globale (la nivelul unui întreg CFG).
 *
 * Din cauza complexității CFG-urilor, optimizările globale presupun în general două etape, mai întâi, de analiză,
 * și de-abia apoi, de transformare. Scopul unei analize globale este de a adnota fiecare sau anumite puncte din CFG
 * cu câte o informație având o natură specifică analizei, care va fi valorificată de etapa a doua, de transformare.
 *
 * Analizele globale se bazează pe algoritmi iterativi de punct fix, care propagă repetat informația prin CFG, până când
 * nu mai survine nicio modificare între două iterații succesive. Matematic, se poate demonstra că, în anumite condiții,
 * punctul fix este unic, independent de ordinea de propagare a informației în CFG. Totuși, anumite ordini de propagare
 * reduc semnificativ numărul necesar de iterații.
 *
 * Analizele globale pot fi:
 *
 * - forward/top-down, dinspre blocul de intrare în CFG către blocul de ieșire, urmând sensul muchiilor
 * - backwards/bottom-up, dinspre blocul de ieșire către cel de intrare, în sensul opus al muchiilor.
 *
 * Scopul acestei clase este de a expune un framework general pentru realizarea de analize globale, atât top-down,
 * cât și bottom-up, izolând algoritmii universali de propagare a informației de specificul fiecărei analize concrete.
 * Astfel, implementarea unei anumite analize se reduce la subclasarea acestei clase și la implementarea exclusivă
 * a logicii particulare analizei, fără a fi necesară reimplementarea mecanismului de propagare a informației.
 *
 * Aspectele caracteristice unei analize concrete, care trebuie implementate de subclase, sunt următoarele:
 *
 * 1. Tipul informației cu care este adnotat CFG-ul (e.g. mulțime de simboluri), surprins de parametrul de tip, I,
 *    al clasei.
 * 2. Informația inițială cu care sunt adnotate blocul de pornire (metoda getStartBlockInitialInfo()),
 *    respectiv celelalte blocuri (metoda getOtherBlocksInitialInfo())
 * 3. Funcția de transfer al informației printr-o instrucțiune (metoda transfer(Instruction, ...)) și/sau
 *    printr-un întreg bloc (metoda transfer(BasicBlock, ...))
 * 4. Funcția de agregare a informațiilor blocurilor vecine pentru a determina informația blocului curent
 *    (metoda merge())
 *
 * @param <I> tipul informației cu care este adnotat CFG-ul
 */
public abstract class DataflowAnalysis<I> {
    /**
     * CFG-ul analizat și adnotat
     */
    public CFG cfg;

    // Nu vor exista instanțe duplicat pentru același basic block, deci accesarea colecției se poate face
    // pe baza referinței (IdentityHashMap)

    /**
     * Informațiile la intrarea (in) în fiecare bloc
     */
    public Map<BasicBlock, I> blockInInfos = new IdentityHashMap<>();

    /**
     * Informațiile la ieșirea (out) din fiecare bloc
     */
    protected Map<BasicBlock, I> blockOutInfos = new IdentityHashMap<>();

    /**
     * Informațiile aferente fiecărei instrucțiuni din CFG. Pentru analizele forward, informația corespunde intrării
     * (in) în instrucțiune. Pentru analizele backwards, informația corespunde ieșirii (out) din instrucțiune.
     */
    protected Map<Instruction, I> instructionInfos = new IdentityHashMap<>();

    public DataflowAnalysis(CFG cfg) {
        this.cfg = cfg;
    }

    /**
     * Determină informația cu care se adnotează inițial blocul de pornire
     *
     * @return informația inițială a blocului de pornire
     */
    protected abstract I getStartBlockInitialInfo();

    /**
     * Determină informația cu care se adnotează inițial celelalte blocuri
     *
     * @return informația inițială a celorlalte blocuri
     */
    protected abstract I getOtherBlocksInitialInfo();

    /**
     * Funcția de transfer la nivelul unui întreg bloc. Poate primi o implementare implicită pe baza funcției
     * de transfer la nivel de instrucțiune.
     *
     * @param block             blocul prin care se realizează transferul informației
     * @param preInfo           informația anterioară transferului
     * @param orderInstructions funcție de ordonare a instrucțiunilor din bloc, în funcție de tipul analizei
     *                          (forward/backwards)
     * @return                  informația ulterioară transferului
     */
    protected I transfer(
            BasicBlock block,
            I preInfo,
            UnaryOperator<List<Instruction>> orderInstructions
    ) {
        I currentInfo = preInfo;

        // Parcurgem fiecare instrucțiune, în ordinea dată de funcția de ordonare
        for (var instruction : orderInstructions.apply(block.instructions)) {
            // Adnotăm instrucțiunea curentă
            instructionInfos.put(instruction, currentInfo);
            // Apoi realizăm transferul informației prin ea
            currentInfo = transfer(instruction, currentInfo);
        }

        // Informația finală din bloc este cea aferentă ultimei instrucțiuni analizate
        return currentInfo;
    }

    /**
     * Funcția de transfer la nivel de instrucțiune. Implementarea implicită propagă informația fără a o actualiza,
     * corespunzând unei funcții identitate.
     *
     * @param instruction instrucțiunea prin care se transferă informația
     * @param preInfo     informația anterioară transferului
     * @return            informația ulterioară transferului
     */
    protected I transfer(Instruction instruction, I preInfo) {
        return preInfo;
    }

    /**
     * Funcția de agregare a informației ulterioare transferului, venite de la vecini, pentru a determina informația
     * anterioară transferului pentru blocul curent. Acumulatorul furnizat ca prim parametru trebuie modificat pentru
     * a încorpora informația următorului vecin.
     *
     * @param accumInfo acumulatorul modificabil, care a agregat informația unei părți a vecinilor
     * @param nextInfo  informația următorului vecin
     */
    protected abstract void merge(I accumInfo, I nextInfo);

    /**
     * Funcție de ajustare a informației rezultate din agregarea informației vecinilor, utilizând informația din blocul
     * curent. Întrucât este rar folosită, implementarea implicită este vidă.
     *
     * @param block      blocul curent
     * @param mergedInfo informația agregată a tuturor vecinilor blocului curent
     */
    protected void finishInfo(BasicBlock block, I mergedInfo) {}

    /**
     * Verifică dacă informația s-a schimbat de la o iterație la alta. Este utilizată pentru încheierea algoritmului
     * iterativ de propagare.
     *
     * @param  info1 informația anterioară
     * @param  info2 informația curentă
     * @return      true dacă informația s-a modificat
     */
    protected boolean hasInfoChanged(I info1, I info2) {
        return ! info1.equals(info2);
    }

    /**
     * Modalitatea de reprezentare a informației sub formă de șir de caractere, pentru a înlesni afișarea tuturor
     * adnotărilor CFG-ului. Implicit, se invocă toString().
     *
     * @param  info informația
     * @return reprezentarea informației ca șir de caractere
     */
    protected String infoToString(I info) {
        return info.toString();
    }

    /**
     * Mecanismul universal de propagare a informației. Întrucât, în funcție de tipul analizei (forward/backwards),
     * blocul inițial, informația anterioară/ulterioară transferului, vecinii blocului curent, și ordinea de parcurgere
     * a instrucțiunilor dintr-un bloc au semnificații diferite, ele sunt primite ca parametri.
     *
     * @param startBlock        blocul de pornire a analizei
     * @param otherBlocks       celelalte blocuri, mai puțin cel de pornire
     * @param blockPreInfos     map cu informații ale blocurilor, anterioare transferului
     * @param blockPostInfos    map cu informații ale blocurilor, ulterioare transferului
     * @param getNeighbors      funcție care determină vecinii unui bloc
     * @param orderInstructions funcție de ordonare a instrucțiunilor din bloc
     * @see   #runForward()
     * @see   #runBackwards()
     */
    protected void run(
            BasicBlock startBlock,
            List<BasicBlock> otherBlocks,
            Map<BasicBlock, I> blockPreInfos,
            Map<BasicBlock, I> blockPostInfos,
            Function<BasicBlock, List<BasicBlock>> getNeighbors,
            UnaryOperator<List<Instruction>> orderInstructions
    ) {
        var startInfo = getStartBlockInitialInfo();
        var otherInfo = getOtherBlocksInitialInfo();

        // Pentru blocul de pornire, informația anterioară transferului este furnizată de metoda
        // getStartBlockInitialInfo(), iar cea ulterioară transferului se obține apelând funcția de transfer la nivel
        // de bloc asupra celei anterioare
        blockPreInfos.put(startBlock, startInfo);
        blockPostInfos.put(startBlock, transfer(startBlock, startInfo, orderInstructions));

        // Toate celelalte blocuri sunt adnotate atât la intrare, cât și la ieșire, cu informația furnizată de metoda
        // getOtherBlocksInitialInfo()
        for (var otherBlock : otherBlocks) {
            blockPreInfos.put(otherBlock, otherInfo);
            blockPostInfos.put(otherBlock, otherInfo);
        }

        // Algoritmul iterativ de punct fix, care se oprește odată ce changed rămâne false la finalul unei iterații
        boolean changed;
        do {
            changed = false;
            // Într-o iterație a algoritmului, se parcurg toate nodurile diferite de cel de pornire, întrucât
            // informațiile acestuia nu se pot modifica de la o iterație la alta
            for (var block : otherBlocks) {
                // Informația anterioară transferului pentru blocul curent se obține agregând informația ulterioară
                // transferului pentru vecinii săi
                I preInfo = getNeighbors.apply(block).stream()
                        .map(blockPostInfos::get)
                        .collect(this::getOtherBlocksInitialInfo, this::merge, this::merge);
                // Dacă este nevoie, se ajustează suplimentar informația obținută prin agregare
                finishInfo(block, preInfo);
                // Se adnotează blocul curent cu informația anterioară transferului
                blockPreInfos.put(block, preInfo);

                // Informația ulterioară transferului se obține aplicând funcția de transfer la nivelul blocului curent
                I postInfo = transfer(block, preInfo, orderInstructions);
                // Se adnotează blocul curent cu informația ulterioară transferului
                I oldPostInfo = blockPostInfos.get(block);
                // Se verifică dacă informația ulterioară transferului s-a modificat față de cea anterioară. În caz
                // afirmativ, este necesară o nouă iterație, pentru actualizarea informației blocurilor care primesc
                // noua informație de la blocul curent.
                if (hasInfoChanged(oldPostInfo, postInfo)) {
                    changed = true;
                    blockPostInfos.put(block, postInfo);
                }
            }
        } while (changed);
    }

    /**
     * Funcția de propagare a informației pentru analizele forward.
     */
    public void runForward() {
        var blocks = cfg.blocks;
        // Un număr redus de iterații se obține ordonând celelalte blocuri (parametrul 2 al metodei run()) conform
        // unei traversări reverse post-order a CFG-ului. Aici este păstrată ordinea preexistentă din CFG pentru
        // simplitate, dar acest lucru nu afectează corectitudinea.
        run(
                blocks.getFirst(),                // Blocul de pornire este cel de intrare
                blocks.subList(1, blocks.size()), // Celelalte blocuri îl exclud pe cel de pornire
                blockInInfos,                     // Informația anterioară transferurilor este la intrarea în blocuri (in)
                blockOutInfos,                    // Informația ulterioară transferurilor este la ieșirea din blocuri (out)
                block -> block.predecessors,      // Vecinii de la care blocul curent primește informație sunt predecesorii
                UnaryOperator.identity()          // Ordinea de parcurgere a instrucțiunilor din bloc este cea naturală
        );
    }

    /**
     * Funcția de propagare a informației pentru analizele backwards
     */
    public void runBackwards() {
        var blocks = cfg.blocks;
        // Un număr redus de iterații se obține ordonând celelalte blocuri (parametrul 2 al metodei run()) conform
        // unei traversări reverse post-order a CFG-ului cu muchiile inversate. Aici este păstrată ordinea preexistentă
        // din CFG pentru simplitate, dar acest lucru nu afectează corectitudinea.
        run(
                blocks.getLast(),                     // Blocul de pornire este cel de ieșire
                blocks.subList(0, blocks.size() - 1), // Celelalte blocuri îl exclud pe cel de pornire
                blockOutInfos,                        // Informația anterioară transferurilor este la ieșirea din blocuri (out)
                blockInInfos,                         // Informația ulterioară transferurilor este la intrarea în blocuri (in)
                block -> block.successors,            // Vecinii de la care blocul curent primește informație sunt succesorii
                List::reversed                        // Ordinea de parcurgere a instrucțiunilor din bloc este inversată
        );
    }

    /**
     * Reprezentarea analizei sub formă de șir de caractere include reprezentarea CFG-ului, alături de adnotările
     * informaționale din fiecare punct
     *
     * @return reprezentarea ca șir
     */
    @Override
    public String toString() {
        return cfg.blocks.stream()
                .map(block ->
                        String.format(
                                "%-34s %s\n\t",
                                block.getName() + ": ",
                                infoToString(blockInInfos.get(block)) + " (in)")
                        + block.instructions.stream()
                                .map(instruction -> String.format(
                                        "%-30s %s",
                                        instruction,
                                        Optional.ofNullable(instructionInfos.get(instruction))
                                                .map(this::infoToString)
                                                .orElse("")))
                                .collect(Collectors.joining("\n\t"))
                        + String.format(
                                "\n\t%-30s %s",
                                "",
                                infoToString(blockOutInfos.get(block)) + " (out)"))
                .collect(Collectors.joining("\n"));
    }
}
