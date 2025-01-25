package optim;

import java.util.ArrayList;
import java.util.Collections;

import analysis.ReversePostOrderNumbering;
import irgen.BasicBlock;
import irgen.CFG;

/**
 * Eliminarea fluxului de control inutil
 */
public class UselessControlFlowElimination {
    protected CFG cfg;

    public UselessControlFlowElimination(final CFG cfg) {
        this.cfg = cfg;
    }

    /**
     * Verifică dacă blocul curent poate fi combinat cu alt bloc. Acest lucru este posibil numai dacă blocul curent
     * are un unic succesor, iar acesta are doar blocul curent ca unic predecesor. Dacă proprietatea anterioară este
     * satisfăcută, blocul curent preia instrucțiunile și legăturile succesorului.
     *
     * Pentru simplitate, eliminarea blocului curent în cazul în care este gol (conține doar un salt) poate fi
     * conceptualizată ca un caz particular de combinare a blocurilor.
     *
     * @param  block blocul de analizat
     * @return true, dacă transformarea a fost aplicată
     */
    protected boolean combineBlocks(final BasicBlock block) {
        // Ultima instrucțiune din bloc
        final var branch = block.instructions.getLast();

        // TODO Dacă ultima instrucțiune din bloc nu este un salt necondiționat, abandonăm
        if (!branch.isUnconditionalBranch())
            return false;

        // Ținta saltului necondiționat
        final var target = (BasicBlock) branch.operands.get(0);

        // TODO Dacă ținta are multipli predecesori, abandonăm
        if (target.predecessors.size() > 1)
            return false;

        // În acest punct, combinarea poate începe

        // TODO Saltul de la finalul blocului curent este eliminat
        block.instructions.removeLast();

        // TODO Instrucțiunile din blocul țintă sunt copiate în blocul curent
        block.instructions = new ArrayList<>(block.instructions);
        block.instructions.addAll(target.instructions);

        // TODO Succesorii blocului țintă își înlocuiesc în listele lor de predecesori blocul țintă cu blocul curent
        target.successors.forEach(s -> s.predecessors.replaceAll(p -> p == target ? block : p));

        // TODO Blocul curent preia succesorii blocului țintă, și numai pe ei
        block.successors = new ArrayList<>(target.successors);

        // TODO Blocul țintă rămâne fără predecesori
        target.predecessors.clear();

        return true;
    }

    /**
     * Rulează o iterație pe întregul CFG.
     *
     * @return true, dacă a putut fi aplicată vreo transformare asupra unui bloc
     */
    public boolean runIteration() {
        // Sortăm blocurile conform unei parcurgeri în postordine.
        final var rpoNumbering = new ReversePostOrderNumbering(this.cfg);
        rpoNumbering.computeRpoNumbers();

        // Nu operăm direct pe lista blocurilor din CFG, deoarece ar putea conține blocuri devenite neexecutabile
        // după iterațiie anterioare. Blocurile întâlnite în urma parcurgerii în post-ordine se garantează a fi
        // executabile.
        final var sortedBlocks = new ArrayList<>(rpoNumbering.rpoNumbers.keySet());
        Collections.sort(sortedBlocks, rpoNumbering.getRpoComparator().reversed());

        var changed = false;

        for (final var block : sortedBlocks)
            changed |= this.combineBlocks(block);

        return changed;
    }

    /**
     * Rulează multiple iterații până nu se mai schimbă nimic
     */
    public void run() {
        while (this.runIteration()) {}
    }
}
