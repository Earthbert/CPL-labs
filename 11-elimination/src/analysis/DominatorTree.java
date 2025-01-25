package analysis;

import irgen.BasicBlock;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import semantic.Symbol;

/**
 * Arborele de dominare conține blocuri drept noduri, iar părintele unui bloc este dominatorul său imediat.
 * Este utilizat în construcția formei SSA.
 *
 * Dominatorul imediat al unui bloc B este cel mai apropiat dominator al lui B, diferit de B însuși.
 * Blocul de intrare în CFG este rădăcina arborelui de dominare (domină întregul CFG) și nu are un dominator imediat.
 * Arborele de dominare surprinde compact mulțimea de dominatori ai unui bloc B, urmând calea de la B către rădăcină
 * (blocul de intrare).
 *
 * Frontiera de dominanță a unui bloc D se referă intuitiv la mulțimea *primelor* blocuri care părăsesc sfera
 * de dominanță (adică mulțimea blocurilor dominate) a blocului D. Cu alte cuvinte, se află la *exact o muchie* distanță
 * de cele mai depărtate blocuri dominate de D. Formal, un bloc B aparține frontierei de dominanță a blocului D dacă:
 *
 * 1. D domină un predecesor al lui B (D poate fi predecesorul însuși, datorită reflexivității dominanței)
 * 2. D NU îl domină *strict* pe B (ceea ce înseamnă că D poate fi B).
 *
 * Condiția (2) asigură că D se poate afla în propria frontieră de dominanță, fapt important la construcția formei SSA.
 * Dacă impuneam ca D să nu îl domine deloc (nestrict) pe B, D nu ar fi putut aparține propriei frontiere de dominanță.
 */
public class DominatorTree {
    /**
     * Este necesară analiza prealabilă a dominatorilor fiecărui bloc
     */
    DominatorAnalysis dominatorAnalysis;

    /**
     * Codificare a arborelui de dominare ca map care asociază fiecărui bloc mulțimea de copii
     */
    public Map<BasicBlock, Set<BasicBlock>> childrenSets = new IdentityHashMap<>();

    /**
     * Codificare redundantă a arborelui de copii ca map care asociază fiecărui bloc părintele său (dominatorul imediat).
     * În funcție de situație, poate fi utilă o codificare sau cealaltă.
     */
    public Map<BasicBlock, BasicBlock> immediateDominators = new IdentityHashMap<>();

    /**
     * Frontierele de dominanță ale blocurilor, fiecare fiind reprezentată ca o mulțime de blocuri
     */
    public Map<BasicBlock, Set<BasicBlock>> dominanceFrontiers = new IdentityHashMap<>();

    static STGroupFile group = new STGroupFile("irgen/cfg.stg");

    public DominatorTree(DominatorAnalysis dominatorAnalysis) {
        this.dominatorAnalysis = dominatorAnalysis;
        var blocks = dominatorAnalysis.cfg.blocks;

        // Este utilă stocarea copiilor unui nod în post-ordine inversă
        var rpoNumbering = new ReversePostOrderNumbering(dominatorAnalysis.cfg);
        rpoNumbering.computeRpoNumbers();
        var rpoComparator = rpoNumbering.getRpoComparator();

        // Se inițializează mulțimile de copii și frontierele de dominanță cu mulțimi vide
        for (var block : blocks) {
            childrenSets.put(block, new TreeSet<>(rpoComparator));
            dominanceFrontiers.put(block, new HashSet<>());
        }

        // Se determină dominatorul imediat al fiecărui bloc diferit de cel de intrare prin apelul metodei cu același
        // nume, și se stabilesc legăturile bidirecționale dintre părinți și copii
        for (var block : blocks.subList(1, blocks.size())) {
            var immediateDominator = computeImmediateDominator(block, dominatorAnalysis.blockInInfos.get(block));
            immediateDominators.put(block, immediateDominator);
            childrenSets.get(immediateDominator).add(block);
        }

        // Se determină frontierele de dominanță ale fiecărui bloc. Ideea este următoarea:
        // În primul rând, un bloc cu un singur predecesor nu poate să părăsească sfera de dominanță a blocului care
        // îi domină unicul predecesor (dominatorul poate fi unicul predecesor însuși, datorită reflexivității
        // dominanței), și prin urmare nu se va regăsi în vreo frontieră de dominanță. Deci candidații pentru
        // introducerea în frontiere de dominanță vor fi doar blocurile cu cel puțin 2 predecesori (join points),
        // astfel încât să existe posibilitatea ca predecesori distincți să manifeste influențe diferite, fără ca
        // vreunul dintre ei să domine efectiv blocul curent.
        //
        // Concentrându-ne acum doar pe blocurile care satisfac această proprietate (au multipli predecesori), putem
        // observa suplimentar următoarele două aspecte:
        //
        // 1. Dacă un predecesor din CFG al blocului curent B este dominatorul său imediat, ID(B) (immediate dominator),
        //    atunci B NU APARȚINE frontierei de dominanță a lui ID(B) și nici a altui dominator de mai sus.
        //    Justificarea este că proprietatea (2) din definiția formală a frontierei de dominanță (vezi comentariile
        //    clasei) impune ca blocul în frontiera de dominanță a căruia B ar fi prezent trebuie să nu domine strict B,
        //    dar atât ID(B), cât și ceilalți dominatori de mai sus ai lui B domină strict B.
        // 2. Dacă un predecesor P din CFG al blocului curent B nu este dominatorul său imediat, P != ID(B),
        //    atunci B APARȚINE frontierei de dominanță a lui P, precum și frontierelor de dominanță ale dominatorilor
        //    lui P, dar până la ID(B), exclusiv (vezi observația (1) de deasupra). Ceilalți dominatori ai lui P se pot
        //    determina mergând în sus în arborele de dominanță și oprindu-ne la întâlnirea lui ID(B). Justificarea este
        //    că între P și ID(B) nu există alți dominatori ai lui B, din moment ce ID(B) este cel imediat, și atunci
        //    proprietatea (2) din definiția formală a frontierei de dominanță (vezi comentariile clasei) este
        //    respectată.
        //
        // Pornind de la cele 2 observații, secvența scurtă de mai jos ia în calcul toate blocurile cu cel puțin 2
        // predecesori (join points), B, și le adaugă la frontierele de dominanță ale predecesorilor săi din CFG și ale
        // dominatorilor acestora, mergând în sus în arborele de dominanță, dar până la întâlnirea lui ID(B).
        for (var block : blocks)
            // Ne uităm doar la join points
            if (block.predecessors.size() > 1)
                // Parcurgem predecesorii din CFG ai blocului curent
                for (var predecessor : block.predecessors) {
                    var temp = predecessor;
                    // Adăugăm blocul curent în frontierele de dominanță ale blocurilor intermediare din arborele
                    // de dominanță, dintre predecesor și dominatorul imediat al blocului curent
                    while (temp != immediateDominators.get(block)) {
                        dominanceFrontiers.get(temp).add(block);
                        temp = immediateDominators.get(temp);
                    }
                }
    }

    /**
     * Determină dominatorul imediat al unui bloc utilizând mulțimea tuturor dominatorilor săi, determinată printr-o
     * analiză prealabilă.
     *
     * @param  block      blocul pentru care se dorește determinarea dominatorului imediat
     * @param  dominators lista tuturor dominatorilor săi
     * @return            dominatorul imediat
     */
    protected BasicBlock computeImmediateDominator(BasicBlock block, Set<BasicBlock> dominators) {
        // Se pornește de la primul predecesor din CFG al blocului de interes și se urmează repetat primul predecesor
        // până la întâlnirea primului dominator al blocului de interes. Acela va fi și dominatorul imediat.
        // În locul primului predecesor putea fi ales oricare altul, întrucât un dominator al blocului de interes
        // domină prin definiție toți predecesorii săi.
        var crtBlock = block.predecessors.getFirst();

        while (! dominators.contains(crtBlock))
            crtBlock = crtBlock.predecessors.getFirst();

        return crtBlock;
    }

    @Override
    public String toString() {
        return dominatorAnalysis.cfg.blocks.stream()
                .map(block ->
                        block.getName() + ": "
                        + "IDOM = " + Optional.ofNullable(immediateDominators.get(block))
                                .map(Symbol::getName)
                                .orElse("-")
                        + ", frontier = " + dominanceFrontiers.get(block).stream()
                                .map(Symbol::getName)
                                .collect(Collectors.toSet()))
                .collect(Collectors.joining("\n"));
    }

    /**
     * Desenează arborele de dominanță în format DOT.
     *
     * @return un StringTemplate cu reprezentarea
     */
    public ST toDOT() {
        var graph = group.getInstanceOf("cfg");

        for (var block : dominatorAnalysis.cfg.blocks) {
            var blockTemplate = group.getInstanceOf("block")
                    .add("name", block.getName())
                    .add("instructions", block.toString().replace("\n\t", "\\l") + "\\l");

            graph.add("blocks", blockTemplate);

            for (var child : childrenSets.get(block)) {
                var edge = group.getInstanceOf("edge")
                        .add("from", block.getName())
                        .add("to", child.getName());

                graph.add("edges", edge);
            }
        }

        return graph;
    }

    /**
     * Scrie reprezentarea DOT a CFG-ului într-un fișier
     *
     * @param  fileName    numele fișierului
     * @throws IOException -
     */
    public void toDOTFile(String fileName) throws IOException {
        Files.writeString(Path.of(fileName), toDOT().render());
    }
}
