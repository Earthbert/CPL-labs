package analysis;

import irgen.BasicBlock;
import irgen.CFG;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Etichetează blocurile din CFG cu numerele de ordine aferente unei parcurgeri în post-ordine inversă (engl.
 * reverse post-order). Dacă graful este aciclic, corespunde sortării topologice.
 */
public class ReversePostOrderNumbering {
    protected CFG cfg;

    public Map<BasicBlock, Integer> rpoNumbers = new HashMap<>();

    protected Set<BasicBlock> visited = new HashSet<>();

    protected int number;

    public ReversePostOrderNumbering(CFG cfg) {
        this.cfg = cfg;
    }

    /**
     * Determină numerotarea în post-ordine inversă pentru întregul CFG.
     */
    public void computeRpoNumbers() {
        // Reinițializare structuri
        rpoNumbers.clear();
        visited.clear();
        number = cfg.blocks.size() - 1;

        computeRpoNumbers(cfg.blocks.getFirst());
    }

    /**
     * Determină numerotarea în post-ordine inversă pornind de la un anumit nod
     *
     * @param block nodul de pornire
     */
    protected void computeRpoNumbers(BasicBlock block) {
        visited.add(block);

        for (var successor : block.successors)
            // Evitare cicluri
            if (! visited.contains(successor))
                computeRpoNumbers(successor);

        // Se realizează o parcurge standard în post-ordine, dar blocurile se numerotează descrescător, pentru a obține
        // inversarea
        rpoNumbers.put(block, number--);
    }

    /**
     * Construiește un comparator de blocuri pe baza numerelor de post-ordine inversă
     *
     * @return comparatorul
     */
    public Comparator<BasicBlock> getRpoComparator() {
        return Comparator.comparing(rpoNumbers::get);
    }

    @Override
    public String toString() {
        return rpoNumbers.entrySet().stream()
                .map(entry -> entry.getKey().getDisplayName() + ": " + entry.getValue())
                .collect(Collectors.joining("\n"));
    }
}
