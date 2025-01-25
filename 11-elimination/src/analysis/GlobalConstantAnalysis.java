package analysis;

import irgen.CFG;
import irgen.Instruction;
import irgen.InstructionType;
import semantic.Symbol;
import semantic.IdSymbol;
import semantic.IntSymbol;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Analiză clasică forward, pentru determinarea numelor constante în diverse puncte din CFG, fără a necesita forma SSA.
 *
 * Pentru a ține cont de mai multe nume simultan, informația pentru adnotare este un map, în care cheile sunt nume
 * (identificate sau nu cu instrucțiunile care le produc).
 *
 * În curs, se utilizează 3 categorii de valori posibile pentru un nume (_|_, c, T) (bottom, constantă concretă, top).
 * Se alege următoarea reprezentare a valorilor din map pentru aceste 3 categorii, utilizând Optional cu IntSymbol drept
 * conținut:
 *
 * - _|_ este reprezentat prin absența cheii din map, și prin urmare nu afectează funcția de agregare
 * - constanta concretă c este reprezentată prin Optional[c]
 * - T este reprezentat prin Optional[] (vid).
 */
public class GlobalConstantAnalysis extends DataflowAnalysis<Map<IdSymbol, Optional<IntSymbol>>> {

    public GlobalConstantAnalysis(CFG cfg) {
        super(cfg);
    }

    /**
     * La intrarea în blocul de intrare în CFG, informația inițială este despre valorile necunoscute (T)
     * ale parametrilor formali.
     *
     * @return map-ul cu valorile T ale parametrilor formali
     */
    @Override
    protected Map<IdSymbol, Optional<IntSymbol>> getStartBlockInitialInfo() {
        var functionSymbol = cfg.functionSymbol;

        // Dacă nu există un simbol de funcție asociat CFG-ului, informația de intrare în blocul de intrare este
        // un map vid
        if (functionSymbol == null)
            return Map.of();

        // Altfel, se adaugă informația de valoare necunoscută pentru parametrii formali ai funcției
        return functionSymbol.symbols.values().stream()
                .collect(Collectors.toMap(
                        param -> (IdSymbol) param,
                        param -> Optional.empty()));
    }

    /**
     * Informația inițială a celorlalte blocuri este un map vid
     *
     * @return map-ul vid
     */
    @Override
    protected Map<IdSymbol, Optional<IntSymbol>> getOtherBlocksInitialInfo() {
        return new HashMap<>();
    }

    /**
     * Funcția de transfer la nivel de instrucțiune consideră că o instrucțiune care definește un nume face ca acel
     * nume să devină constant dacă instrucțiunea este de copiere a unui literal întreg. Dacă instrucțiunea este
     * mai complexă, se consideră valoarea T. Evident, se ignoră instrucțiunile care nu produc rezultat.
     *
     * @param  instruction instrucțiunea prin care se transferă informația
     * @param  inInfo      informația la intrarea în instrucțiune
     * @return             informația la ieșirea din instrucțiune
     */
    @Override
    protected Map<IdSymbol, Optional<IntSymbol>> transfer(Instruction instruction, Map<IdSymbol, Optional<IntSymbol>> inInfo) {
        // Ignorăm instrucțiunile care nu produc rezultat
        if (! instruction.hasResult())
            return inInfo;

        // Avem nevoie de o instanță nouă pentru informația de la ieșire, pentru că nu o putem modifica pe cea
        // de la intrare
        var outInfo = new HashMap<>(inInfo);

        // Rezultatul instrucțiunii devine constant dacă instrucțiunea este de copiere a unui literal întreg.
        // Altfel, capătă un statut neconstant (T).
        outInfo.put(
                instruction.getResult(),
                (instruction.iType == InstructionType.COPY && instruction.operands.getFirst() instanceof IntSymbol value)
                        ? Optional.of(value)
                        : Optional.empty()
        );

        return outInfo;
    }

    /**
     * Funcția de agregare a informației de la ieșirea predecesorilor consideră că un nume este constant la intrarea
     * în blocul curent dacă este constant în fiecare map și are aceeași valoare. Altfel, devine T.
     *
     * @param accumInfo acumulatorul modificabil, care a agregat informația unei părți a predecesorilor
     * @param predInfo  informația următorului predecesor
     */
    @Override
    protected void merge(Map<IdSymbol, Optional<IntSymbol>> accumInfo, Map<IdSymbol, Optional<IntSymbol>> predInfo) {
        // Se pornește de la intrările din map-ul acumulator (parametrul 4 al lui toMap()), și se adaugă intrările
        // din map-ul următorului predecesor, gestionând corespunzător conflictele de chei (parametrul 3 al lui toMap()).
        predInfo.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (o1, o2) -> (o1.equals(o2) ? o1 : Optional.empty()),
                        () -> accumInfo
                ));
    }

    /**
     * Reprezentarea informației sub formă de șir de caractere formatează valorile Optional.empty() ca T.
     *
     * @param  info informația
     * @return      șirul
     */
    @Override
    protected String infoToString(Map<IdSymbol, Optional<IntSymbol>> info) {
        return info.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().getDisplayName(),
                        entry -> entry.getValue().map(Symbol::getName).orElse("T")))
                .toString();
    }
}
