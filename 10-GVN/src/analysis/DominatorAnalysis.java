package analysis;

import irgen.BasicBlock;
import irgen.CFG;
import irgen.Instruction;

import semantic.Symbol;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * Analiză clasică forward pentru determinarea mulțimii de dominatori ai fiecărui bloc. Este utilizată pentru
 * construcția eficientă a formei SSA.
 *
 * Un bloc D domină un bloc B dacă toate căile de la blocul de intrare din CFG către B trec prin D. Fiecare bloc
 * se domină pe sine. Un bloc D domină *strict* un alt bloc B, dacă îl domină fără a fi egal cu el (D != B).
 *
 * Informația pentru adnotare este o mulțime de blocuri.
 */
public class DominatorAnalysis extends DataflowAnalysis<Set<BasicBlock>> {

    public DominatorAnalysis(CFG cfg) {
        super(cfg);
    }

    /**
     * La intrarea în blocul de intrare în CFG, informația inițială conține doar blocul de intrare însuși,
     * care se domină pe sine.
     *
     * @return mulțimea singleton cu blocul de intrare
     */
    @Override
    protected Set<BasicBlock> getStartBlockInitialInfo() {
        return Set.of(cfg.blocks.getFirst());
    }

    /**
     * Informația inițială a celorlalte blocuri este mulțimea tuturor blocurilor din CFG
     *
     * @return mulțimea tuturor blocurilor din CFG
     */
    @Override
    protected Set<BasicBlock> getOtherBlocksInitialInfo() {
        return new HashSet<>(cfg.blocks);
    }

    /**
     * Întrucât informația de dominare nu depinde de instrucțiunile din fiecare bloc, ci exclusiv de structura CFG-ului,
     * este mai avantajos pentru această analiză să supradefinim direct funcția de transfer la nivelul unui bloc,
     * care nu face decât să propage informația de la intrarea blocului spre ieșirea acestuia.
     *
     * @param block             blocul prin care se realizează transferul informației
     * @param inInfo            informația la intrarea în bloc
     * @param orderInstructions funcție de ordonare a instrucțiunilor din bloc, în funcție de tipul analizei
     *                          (forward/backwards)
     * @return                  informația la ieșirea din bloc, identică cu cea de la intrare
     */
    @Override
    protected Set<BasicBlock> transfer(
            BasicBlock block,
            Set<BasicBlock> inInfo,
            UnaryOperator<List<Instruction>> orderInstructions
    ) {
        return inInfo;
    }

    /**
     * Funcția de agregare a informației de la ieșirea predecesorilor consideră că un bloc domină blocul curent
     * dacă domină fiecare predecesor al său.
     *
     * @param accumInfo acumulatorul modificabil, care a agregat informația unei părți a predecesorilor
     * @param predInfo  informația următorului predecesor
     */
    @Override
    protected void merge(Set<BasicBlock> accumInfo, Set<BasicBlock> predInfo) {
        // Utilizăm intersecție de mulțimi
        accumInfo.retainAll(predInfo);
    }

    /**
     * După agregarea informației de la ieșirea predecesorilor, este necesară adăugarea blocului curent ca propriul
     * său dominator
     *
     * @param block      blocul curent
     * @param mergedInfo informația agregată a tuturor predecesorilor
     */
    @Override
    protected void finishInfo(BasicBlock block, Set<BasicBlock> mergedInfo) {
        mergedInfo.add(block);
    }

    @Override
    protected String infoToString(Set<BasicBlock> info) {
        return info.stream()
                .map(Symbol::getDisplayName)
                .collect(Collectors.toSet())
                .toString();
    }
}
