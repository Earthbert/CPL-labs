package irgen;

import analysis.DominatorTree;
import analysis.LivenessAnalysis;
import semantic.IdSymbol;
import semantic.Symbol;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Transformarea unui CFG în forma SSA, utilizând arborele de dominanță și informația de liveness.
 *
 * Forma SSA (static single-assignment) a codului intermediar garantează două proprietăți:
 *
 * 1. Fiecare instrucțiune care produce un rezultat definește un nume unic, i.e. este pură (de aici ideea
 *    de identificare a unei instrucțiuni cu rezultatul său)
 * 2. Fiecare utilizare a unui nume referă o unică definiție, nu potențiale multiple definiții ale aceluiași nume
 *    care ar putea fi vizibile pe diferite căi din CFG.
 *
 *  Pentru obținerea formei SSA, este necesar un algoritm de transformare a unui CFG oarecare. La nivelul unui singur
 *  bloc, acest lucru este banal, întrucât presupune incrementarea versiunii unui nume de fiecare dată când acesta este
 *  redefinit, și redenumirea utilizărilor acestuia cu cea mai recentă versiune vizibilă. Lucrurile se complică
 *  în momentul în care într-un bloc intră multiple căi, ce pot defini același nume. Se pune întrebarea care este
 *  versiunea acelui nume la intrarea în blocul respectiv. Soluția este ca, pentru fiecare nume live în blocuri
 *  multiple, să se introducă la începutul blocurilor unde sunt accesibile multiple versiuni ale acelui nume,
 *  o instrucțiune ϕ (phi) care agregă aceste versiuni într-o nouă versiune a numelui. Numele live la nivelul
 *  unui singur bloc nu necesită instrucțiuni ϕ.
 *
 *  De exemplu, dacă la intrarea într-un bloc, sunt vizibile două versiuni ale numelui a, și anume
 *  a.0 și a.1, atunci la intrarea în blocul respectiv, se adaugă instrucțiunea a.2 = ϕ a.0 a.1 bloc0 bloc1. Aceasta
 *  afirmă că, dacă fluxul de control vine dinspre bloc0, atunci a.2 îi corespunde lui a.0, iar dacă fluxul de control
 *  vine dinspre bloc1, a.2 îi corespunde lui a.1. Nu este obligatoriu ca a.0 și a.1 să fi fost definite efectiv
 *  în bloc0, respectiv bloc1. În continuare, în blocul curent, utilizările lui a pot fi redenumite în a.2.
 *
 *  Următoarea întrebare este cum determinăm blocurile unde este necesară introducerea instrucțiunilor ϕ? O abordare
 *  naivă introduce instrucțiuni ϕ pentru fiecare nume live în multiple blocuri, la începutul fiecărui join point (dacă
 *  un bloc are un singur predecesor, instrucțiunile ϕ ar fi inutile). Această variantă ar conduce la un număr uriaș
 *  de instrucțiuni ϕ, care ar îngreuna inspecția vizuală a CFG-ului și desfășurarea algoritmilor de optimizare.
 *
 *  Abordarea eficientă se bazează pe dominanță și liveness. Intuiția este următoarea:
 *
 *  1. Din perspectiva fluxului de control, dacă un bloc D conține o definiție a numelui a, în niciun bloc dominat
 *     de el (toate căile către acel bloc trec prin D) nu pot fi vizibile definiții anterioare din CFG ale numelui a.
 *     Cel mult, ar putea fi vizibile definiții de sub cea din D. Prin urmare definiția din D nu se află în competiție
 *     cu alte definiții de deasupra. Pericolul apare în punctul în care D își pierde influența, și anume în blocurile
 *     din frontiera de dominanță a lui D. Aici, pot fi vizibile atât definiția lui a din D, cât și alte definiții
 *     ale lui a de pe alte căi paralele. Prin urmare, pentru fiecare nume definit în D este necesară introducerea
 *     unei instrucțiuni ϕ la începutul fiecărui bloc din frontiera sa de dominanță. Din moment ce blocul din frontieră
 *     capătă o definiție a acelui nume, în forma instrucțiunii ϕ, este posibil ca el însuși să declanșeze introducerea
 *     de noi instrucțiuni ϕ în frontiera sa de dominanță ș.a.m.d.
 *  2. Din perspectiva fluxului de date, o instrucțiune ϕ este necesară la intrarea într-un bloc doar dacă numele
 *     aferent este live la intrarea în acel bloc. Dacă numele nu este utilizat, instrucțiunea ϕ este inutilă.
 *
 *  Algoritmul de transformare urmează 2 etape:
 *
 *  1. Introducerea instrucțiunilor ϕ (metoda insertPhiInstructions())
 *  2. Redenumirea definițiilor și utilizărilor numelor (metoda rename())
 *
 *  CFG-ul original conține un amestec de instrucțiuni pure și distructive. Transformarea se concentrează exclusiv
 *  pe cele distructive, astfel încât în final CFG-ul să conțină doar instrucțiuni pure.
 */
public class SSATransformer {
    DominatorTree dominatorTree;

    LivenessAnalysis livenessAnalysis;

    /**
     * În cadrul etapei (1), de introducere a instrucțiunilor ϕ, se reține, pentru fiecare nume definit distructiv,
     * mulțimea blocurilor care îl definesc cel puțin o dată.
     */
    Map<IdSymbol, Set<BasicBlock>> definitionBlockSets = new IdentityHashMap<>();

    /**
     * În cadrul etapei (2), de redenumire, se reține, pentru fiecare nume definit distructiv, o stivă de versiuni
     * generate până în momentul curent. Vârful stivei conține cea mai recentă versiune vizibilă în punctul curent
     * din CFG. Algoritmul de redenumire va și elimina elemente din stivă, pe măsură ce revine de pe o cale din CFG.
     */
    Map<IdSymbol, Deque<IdSymbol>> ssaNameStacks = new IdentityHashMap<>();

    /**
     * Populează definitionBlockSets.
     *
     * @param dominatorTree    arborele de dominanță
     * @param livenessAnalysis analiza de liveness deja desfășurată
     */
    public SSATransformer(DominatorTree dominatorTree, LivenessAnalysis livenessAnalysis) {
        this.dominatorTree = dominatorTree;
        this.livenessAnalysis = livenessAnalysis;

        // Parcurgem blocurile din CFG și instrucțiunile din fiecare bloc
        for (var block : livenessAnalysis.cfg.blocks)
            for (var instruction : block.instructions)
                // Ne interesează exclusiv instrucțiunile distructive
                if (instruction.isDestructive()) {
                    // Adăugăm blocul curent la mulțimea de blocuri care definesc distructiv numele curent
                    definitionBlockSets.computeIfAbsent(instruction.getResult(), k -> new HashSet<>()).add(block);
                    // Inițializăm stiva aferentă numelui curent ca fiind vidă
                    ssaNameStacks.computeIfAbsent(instruction.getResult(), k -> new ArrayDeque<>());
                }
    }

    /**
     * Etapa 1: Introducerea instrucțiunile ϕ unde este necesar.
     *
     * Introducerea se realizează separat pentru fiecare nume definit distructiv în parte. Astfel, pentru fiecare nume,
     * se creează o coadă cu blocurile care îl definesc. Într-un pas, se extrage un bloc din coadă și se inserează
     * câte o instrucțiune ϕ aferentă numelui curent în fiecare bloc din frontiera de dominanță a blocului curent,
     * DACĂ acest lucru nu s-a întâmplat deja (nu dorim să introducem de mai multe ori instrucțiuni ϕ pentru același
     * nume în același bloc). Blocurile care tocmai au căpătat o instrucțiune ϕ sunt la rândul lor introduse în coadă,
     * pentru că noua definiție poate genera ea însăși instrucțiuni ϕ în alte blocuri. Se continuă pentru numele curent
     * până la golirea cozii.
     *
     * Există în algoritmul de mai sus 2 surse de redundanță, care pot fi evitate:
     *
     * 1. Un bloc nu ar trebui să ajungă mai mult de o dată (e.g. prin reintroduceri) în coada aferentă numelui curent.
     *    Astfel, se pot reține blocurile care au fost deja în coadă (mulțimea listed de mai jos).
     * 2. Un bloc poate aparține frontierelor de dominanță ale mai multor blocuri și are riscul de a căpăta mai multe
     *    instrucțiuni ϕ pentru același nume. Pentru a evita verificarea instrucțiune cu instrucțiune a prezenței
     *    unei instrucțiuni ϕ pentru numele curent într-un bloc, se rețin blocurile care au căpătat deja o instrucțiune
     *    ϕ pentru numele curent (mulțimea phiInserted de mai jos).
     */
    public void insertPhiInstructions() {
        // Se parcurge fiecare nume definit distructiv, împreună cu mulțimea de blocuri care îl definesc
        definitionBlockSets.forEach((symbol, definitionBlockSet) -> {
            // Coada aferentă numelui curent conține inițial blocurile care îl definesc
            var queue = new ArrayDeque<BasicBlock>(definitionBlockSet);
            // Mulțimea de blocuri care au ajuns în coada aferentă numelui curent conține inițial blocurile care
            // îl definesc
            var listed = new HashSet<BasicBlock>(definitionBlockSet);
            // Inițial niciun bloc nu a căpătat o instrucțiune ϕ pentru numele curent
            var phiInserted = new HashSet<BasicBlock>();

            // Se repetă până la epuizarea cozii
            while (! queue.isEmpty()) {
                // Se extrage primul bloc cu definiri din coadă
                var definitionBlock = queue.removeFirst();

                // Se parcurg toate blocurile din frontiera de dominanță a blocului curent
                for (var frontierBlock : dominatorTree.dominanceFrontiers.get(definitionBlock)) {
                    // Blocul din frontieră necesită o instrucțiune ϕ dacă:
                    // 1. Nu posedă deja una pentru numele curent
                    var needsPhi = ! phiInserted.contains(frontierBlock);
                    // 2. Numele este live la intrarea în bloc
                    var isLive = livenessAnalysis.blockInInfos.get(frontierBlock).contains(symbol);
                    // Dacă ambele condiții sunt satisfăcute
                    if (needsPhi && isLive) {
                        // Se generează o instrucțiune ϕ, care are ca număr de operanzi dublul numărului de predecesori
                        // ai blocul din frontieră. Prima jumătate este alocată pentru versiunile agregate ale numelui
                        // curent, iar a doua jumătate, pentru blocurile care furnizează versiunile respective.
                        var numPredecessors = frontierBlock.predecessors.size();
                        var operands = new Symbol[numPredecessors * 2];
                        // Valorile inițiale ale operanzilor sunt provizorii, urmând a fi cosmetizate în etapa (2),
                        // de redenumire
                        Arrays.fill(operands, 0, numPredecessors, symbol);
                        Arrays.fill(operands, numPredecessors, 2 * numPredecessors, frontierBlock);
                        // Instrucțiunea ϕ este inițial tot distructivă, urmând a deveni pură în etapa (2),
                        // de redenumire
                        var phiInstruction = new Instruction(InstructionType.PHI, symbol, operands);
                        // Instrucțiunile ϕ sunt adăugate implicit la începutul blocului, nu la sfârșit
                        frontierBlock.addInstruction(phiInstruction);
                        // Blocul de frontieră este acum marcat ca posedând o instrucțiune ϕ pentru numele curent
                        phiInserted.add(frontierBlock);

                        // Dacă blocul de frontieră nu fost până acum în coadă, este adăugat, pentru că noua
                        // instrucțiune ϕ este o nouă definiție a numelui curent, și deci poate declanșa introducerea
                        // de noi instrucțiuni ϕ în alte blocuri
                        if (! listed.contains(frontierBlock)) {
                            queue.addLast(frontierBlock);
                            listed.add(frontierBlock);
                        }
                    }
                }
            }
        });
    }

    /**
     * Etapa 2: Redenumirea definițiilor și utilizărilor, printr-o parcurgere în preordine a arborelui de dominanță
     */
    public void rename() {
        // Se pornește cu blocul de intrare în CFG și se operează recursiv (vezi următoarea metodă)
        rename(livenessAnalysis.cfg.blocks.getFirst());
    }

    /**
     * În cadrul unui bloc, se realizează următoarele:
     *
     * 1. La întâlnirea unei instrucțiuni ϕ, numele definit capătă o nouă versiune, și astfel instrucțiunea devine pură.
     *    Noul nume este adăugat în stiva aferentă numelui pentru care s-a introdus instrucțiunea ϕ (metoda
     *    pushSSAName()), astfel încât utilizările următoare să poată fi redenumite cu cea mai recentă versiune.
     * 2. La întâlnirea unei instrucțiuni oarecare, se rescriu operanzii săi pentru a reflecta cele mai recente versiuni
     *    vizibile la nivelul acelei instrucțiuni (metoda getLatestVersion()). De asemenea, dacă instrucțiunea este
     *    distructivă, numele definit capătă o nouă versiune, și astfel instrucțiunea devine pură. Noul nume este
     *    adăugat în stiva aferentă numelui original (metoda pushSSAName()), astfel încât utilizările imediate
     *    să poată fi redenumite cu cea mai recentă versiune. Ordinea prelucrării este importantă (mai întâi operanzii,
     *    și apoi rezultatul), pentru că altfel riscăm să rescriem un operand cu numele care de-abia urmează a fi
     *    introdus. Nu este necesar să prelucrăm numele introduse de instrucțiuni deja pure.
     * 3. Pentru fiecare succesor al blocului curent din CFG, se populează parametrii instrucțiunilor ϕ de la început,
     *    dar doar pe pozițiile aferente blocului din care venim, în raport cu succesorul. De exemplu, dacă blocul
     *    curent este primul dintre cei 2 predecesori ai succesorului, vom popula doar operanzii 0 și 2, corespunzători
     *    ultimei versiuni a numelui, respectiv blocului curent. Operanzii 1 și 3 vor fi populați din cel de-al doilea
     *    predecesor al succesorului.
     * 4. Pentru fiecare copil al blocului curent din arborele de dominanță (blocurile dominate direct de blocul curent),
     *    se aplică recursiv metoda.
     * 5. La încheierea subarborelui de dominanță a blocului curent, se elimină din stive ultimele versiuni introduse,
     *    pentru a prelucra corect frații blocului curent din arborele de dominanță, care nu văd versiunile introduse
     *    în subarborele blocului curent.
     *
     * În cazul în care blocul curent definește de mai multe ori același nume, există multiple adăugări în stiva
     * aferentă numelui (pentru fiecare nouă definire) în pașii (1)-(2) de mai sus, și tot atâtea eliminări din stivă
     * în pasul (5). Pentru a evita operațiile repetate cu stiva pentru același nume, și a limita la cel mult o adăugare
     * și o eliminare per nume, doar prima definire a unui nume în blocul curent este adăugată pe o nouă poziție
     * în stivă, celelalte redefiniri suprascriind poziția existentă. Mulțimea renamedSymbols de mai jos reține
     * numele definite în blocul curent, astfel încât pasul (5) să o poată parcurge și realiza exact câte o eliminare
     * din fiecare dintre stivele aferente acestor nume.
     *
     * @param block blocul curent
     */
    protected void rename(BasicBlock block) {
        // Mulțimea de nume redefinite în blocul curent
        var renamedSymbols = new HashSet<IdSymbol>();

        // Se parcurge fiecare instrucțiune din bloc
        for (var instruction : block.instructions) {
            // Pasul (1): Redenumirea rezultatului unei instrucțiuni ϕ și transformarea ei în variantă pură,
            // cu introducerea în stiva aferentă numelui a noii versiuni
            if (instruction.iType == InstructionType.PHI) {
                // toPure() întoarce vechiul rezultat modificabil, din varianta distructivă a instrucțiunii
                var mutableResult = instruction.toPure();
                // Stocarea noii versiuni în stivă
                pushSSAName(mutableResult, instruction, renamedSymbols);
            }
            // Pasul (2): Prelucrarea celorlalte instrucțiuni
            else {
                // Fiecare operand este rescris cu cea mai recentă versiune a sa, din vârful stivei aferente
                instruction.operands = instruction.operands.stream()
                        .map(this::getLatestVersion)
                        .collect(Collectors.toList());

                // Instrucțiunile distructive sunt transformate în variante pure
                if (instruction.isDestructive()) {
                    var mutableResult = instruction.toPure();
                    pushSSAName(mutableResult, instruction, renamedSymbols);
                }
            }
        }

        // Pasul (3): Popularea argumentelor instrucțiunilor ϕ din fiecare succesor din CFG al blocului curent
        for (var successor : block.successors) {
            for (var instruction : successor.instructions) {
                if (instruction.iType != InstructionType.PHI)
                    break;

                // Se determină operanzii instrucțiunilor ϕ pe care acest bloc are dreptul să îi modifice, în funcție
                // de numărul de ordine pe care îl are printre predecesorii succesorului
                var predecessorIndex = successor.predecessors.indexOf(block);
                instruction.operands.set(predecessorIndex, getLatestVersion(instruction.operands.get(predecessorIndex)));
                instruction.operands.set(predecessorIndex + successor.predecessors.size(), block);
            }
        }

        // Pasul (4): Redenumirea recursivă a copiilor din arborele de dominanță, i.e. a blocurilor dominate direct
        // (parcurgere în preordine, pentru că am prelucrat deja blocul curent)
        dominatorTree.childrenSets.get(block).forEach(this::rename);

        // Pasul (5): Eliminarea din stive a versiunilor introduse în cadrul acestui subarbore de dominanță
        for (var renamedSymbol : renamedSymbols)
            ssaNameStacks.get(renamedSymbol).removeFirst();

//        System.out.println("removed " + renamedSymbols + ": " + ssaNameStacks.entrySet().stream()
//                .collect(Collectors.toMap(
//                        Map.Entry::getKey,
//                        e -> e.getValue().stream()
//                                .map(Symbol::getName)
//                                .collect(Collectors.toList()))));
    }

    /**
     * Actualizează stiva aferentă unui nume cu cea mai recentă versiune a sa
     *
     * @param symbol         numele pentru care se generează o nouă versiune
     * @param instruction    instrucțiunea pură care introduce noua versiune
     * @param renamedSymbols mulțimea de nume redefinite în blocul curent
     */
    protected void pushSSAName(IdSymbol symbol, Instruction instruction, Set<IdSymbol> renamedSymbols) {
        // Se obține stiva corespunzătoare
        var ssaNameStack = ssaNameStacks.get(symbol);

        // Dacă există deja o definiție a numelui în blocul curent, adăugăm noua intrare în locul celei anterioare;
        // altfel, adăugăm intrarea deasupra (vezi îmbunătățirea explicată în comentariile metodei rename(BasicBlock))
        if (renamedSymbols.contains(symbol))
            ssaNameStack.removeFirst();
        else
            renamedSymbols.add(symbol);

        ssaNameStack.addFirst(instruction);

//        System.out.println("add " + symbol + ": " + ssaNameStacks.entrySet().stream()
//                .collect(Collectors.toMap(
//                        Map.Entry::getKey,
//                        e -> e.getValue().stream()
//                                .map(Symbol::getName)
//                                .collect(Collectors.toList()))));
    }

    /**
     * Determină cea mai recentă versiune a unui nume.
     *
     * @param  symbol numele de interes
     * @return        cea mai recentă versiune
     */
    protected Symbol getLatestVersion(Symbol symbol) {
        // Ne interesează doar simbolurile cu tipul dinamic IdSymbol, care pot constitui rezultate modificabile
        // ale instrucțiunilor distructive. Rezultatele instrucțiunilor pure și literalii întregi rămân neschimbați.
        if (symbol.getClass() != IdSymbol.class)
            return symbol;

        var idSymbol = (IdSymbol) symbol;

        // Pentru parametrii formali și pentru variabilele locale neinițializate, nu există instrucțiuni propriu-zise
        // în CFG care să fi condus la generarea măcar a unei versiuni SSA pentru numele curent, și accesarea vârfului
        // unei stive vide sau absente ar genera excepție. Prin urmare, este introdusă manual o versiune în astfel
        // de cazuri.
        var ssaNameStack = ssaNameStacks.compute(
                idSymbol,
                (k, stack) -> {
                    if (stack == null)
                        stack = new ArrayDeque<>();
                    if (stack.isEmpty())
                        //stack.addFirst(new IdSymbol(symbol.getName()).applyNextVersion(true));
                        stack.addFirst(idSymbol);
                    return stack;
                }
        );

        // Se utilizează numele din vârful stivei.
        return ssaNameStack.getFirst();
    }
}

