package analysis;

import irgen.BasicBlock;
import irgen.CFG;
import irgen.Instruction;
import semantic.IdSymbol;
import semantic.Symbol;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Analiză clasică backwards pentru determinarea numelor live în fiecare punct din CFG, fără a necesita forma SSA.
 * De fapt, este utilizată pentru construcția eficientă a formei SSA.
 *
 * Pentru a ține cont de mai multe nume simultan, informația pentru adnotare este o mulțime de nume (identificate
 * sau nu cu instrucțiunile care le produc).
 */
public class LivenessAnalysis extends DataflowAnalysis<Set<IdSymbol>> {

    public LivenessAnalysis(CFG cfg) {
        super(cfg);
    }

    /**
     * La ieșirea din blocul de ieșire, nu există inițial niciun nume live
     *
     * @return mulțimea vidă
     */
    @Override
    protected Set<IdSymbol> getStartBlockInitialInfo() {
        return Set.of();
    }

    /**
     * Informația inițială a celorlalte blocuri este și ea vidă
     *
     * @return mulțimea vidă
     */
    @Override
    protected Set<IdSymbol> getOtherBlocksInitialInfo() {
        return new HashSet<>();
    }

    /**
     * Funcția de transfer la nivel de instrucțiune operează dinspre ieșirea spre intrarea în aceasta. Rezultatul
     * instrucțiunii este omorât iar operanzii devin live. Ordinea aceasta de actualizare a informației este importantă
     * în situația în care rezultatul este și operand!
     *
     * @param  instruction instrucțiunea prin care se transferă informația
     * @param  outInfo     informația la ieșirea din instrucțiune
     * @return             informația la intrarea în instrucțiune
     */
    @Override
    protected Set<IdSymbol> transfer(Instruction instruction, Set<IdSymbol> outInfo) {
        // Avem nevoie de o instanță nouă pentru informația de la intrare, pentru că nu o putem modifica pe cea
        // de la ieșire
        var inInfo = new HashSet<>(outInfo);

        // Întâi trebuie omorât rezultatul
        inInfo.remove(instruction.getResult());
        // Și de-abia apoi înviați operanzii.
        // Dacă procedăm invers, e posibil ca un operand să rămână în mod eronat dead deasupra instrucțiunii.
        instruction.operands.stream()
                .filter(operand -> (operand instanceof IdSymbol) && !(operand instanceof BasicBlock))
                .forEach(operand -> inInfo.add((IdSymbol)operand));

        return inInfo;
    }

    /**
     * Funcția de agregare a informației de la intrarea succesorilor consideră că un nume este live la ieșirea
     * din blocul curent dacă este live în cel puțin o mulțime.
     *
     * @param accumInfo acumulatorul modificabil, care a agregat informația unei părți a succesorilor
     * @param succInfo  informația următorului succesor
     */
    @Override
    protected void merge(Set<IdSymbol> accumInfo, Set<IdSymbol> succInfo) {
        // Aplicăm reuniune de mulțimi
        accumInfo.addAll(succInfo);
    }

    @Override
    protected String infoToString(Set<IdSymbol> info) {
        return info.stream()
                .map(Symbol::getDisplayName)
                .collect(Collectors.toSet())
                .toString();
    }
}
