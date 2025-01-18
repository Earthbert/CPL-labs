package irgen;

import semantic.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Instrucțiune de cod intermediar.
 *
 * Instrucțiunile pot produce valori (e.g. calcul unei sume (ADD), apel de funcție cu valoare întoarsă (CALL) etc.)
 * sau nu (e.g. instrucțiune de salt (BR)). Instrucțiunile care produc valori pot fi pure, definind un nume unic,
 * sau distructive, putând modifica același nume. În cazul celor pure, NUMELE SE IDENTIFICĂ CU INSTRUCȚIUNEA CARE ÎL
 * PRODUCE. Acesta este motivul pentru care (poate contraintuitiv) Instruction moștenește IdSymbol.
 *
 * Exemple:
 * add = ADD x y  (pură)
 * BR then        (nu produce un nume)
 * a <- COPY b    (distructivă)
 * a <- ADD c d   (distructivă)
 *
 * Principial, instrucțuni distructive se generează pentru atribuirile explicite către variabile din codul sursă
 * (e.g. Int a = 3;), și pentru numele temporare generate de compilator care pot lua valori diferite în puncte diferite
 * (vezi visit(If) din IRGenVisitor, unde trebuie generat un temporar care să stocheze posibilele valori ale expresiei
 * if în funcție de ramura aleasă). Pentru toate celelalte operații (e.g. aritmetice) se generează instrucțiuni pure,
 * care introduc un nume unic, cu care se identifică (numele add și instrucțiunea add = ADD x y sunt același lucru).
 */
public class Instruction extends IdSymbol {

    /**
     * Tipul instrucțiunii
     */
    public InstructionType iType;

    /**
     * Lista de operanzi
     */
    public List<Symbol> operands;

    /**
     * În cazul instrucțiunilor distructive, reține rezultatul modificat (care nu se identifică cu instrucțiunea, cum se
     * întâmplă la cele pure); altfel, este null.
     */
    IdSymbol mutableResult;

    /**
     * Creează o instrucțiune pură. Dacă numele rezultatului nu este null, versiunea acestuia se incrementează
     * și rezultatul se identifică cu instrucțiunea. Dacă numele rezultatului este null, instrucțiunea nu produce
     * rezultat, ca în cazul celor de salt (BR) sau de precizare a valorii întoarse de o funcție (RET).
     *
     * @param iType    tipul instrucțiunii
     * @param name     numele rezultatului, cu care instrucțiunea se identifică; posibil null
     * @param operands lista de operanzi
     */
    public Instruction(InstructionType iType, String name, Symbol... operands) {
        super(name);
        applyNextVersion();
        this.iType = iType;
        this.operands = Arrays.asList(operands);
    }

    /**
     * Creează o instrucțiune distructivă, care modifică simbolul extern furnizat ca rezultat.
     *
     * @param iType         tipul instrucțiunii
     * @param mutableResult simbolul extern utilizat ca rezultat
     * @param operands      lista de operanzi
     */
    public Instruction(InstructionType iType, IdSymbol mutableResult, Symbol... operands) {
        this(iType, (String) null, operands);
        toDestructive(mutableResult);
    }


    /**
     * Transformă instrucțiunea curentă într-o variantă distructivă, care utilizează un simbol extern ca rezultat.
     *
     * @param  mutableResult simbolul extern utilizat ca rezultat
     * @return               instanța curentă, în urma transformării
     */
    public Instruction toDestructive(IdSymbol mutableResult) {
        if (mutableResult == null)
            throw new NullPointerException();

        this.mutableResult = mutableResult;
        this.name = mutableResult.getName();
        return this;
    }

    /**
     * Transformă instrucțiunea curentă într-o variantă pură, obținută prin incrementarea versiunii numelui curent.
     * Este folosită în cadrul transformării în forma SSA.
     *
     * @return simbolul extern modificat de această instrucțiune, dacă instrucțiunea este în prezent distructivă,
     *         sau null, altfel
     */
    public IdSymbol toPure() {
        if (! isDestructive())
            return null;

        var oldMutableResult = mutableResult;
        mutableResult = null;
        applyNextVersion(true);

        return oldMutableResult;
    }

    /**
     * Verifică dacă instrucțiunea curentă produce un rezultat.
     *
     * @return true, dacă produce
     */
    public boolean hasResult() {
        return name != null;
    }

    /**
     * Verifică dacă instrucțiunea curentă este distructivă.
     *
     * @return true, dacă este
     */
    public boolean isDestructive() {
        return mutableResult != null;
    }

    /**
     * Verifică dacă instrucțiunea curentă este comutativă (adunare, înmulțire). Utilizată de value numbering.
     *
     * @return true, dacă este
     */
    public boolean isCommutative() {
        return Arrays.asList(InstructionType.ADD, InstructionType.MUL).contains(iType);
    }

    /**
     * Verifică dacă instrucțiunea curentă reprezintă un salt condiționat.
     *
     * @return true, în caz afirmativ
     */
    public boolean isConditionalBranch() {
        return iType == InstructionType.BR && operands.size() == 3;
    }

    /**
     * Verifică dacă instrucțiunea curentă reprezintă un salt necondiționat.
     *
     * @return true, în caz afirmativ
     */
    public boolean isUnconditionalBranch() {
        return iType == InstructionType.BR && operands.size() == 1;
    }

    /**
     * Întoarce rezultatul instrucțiunii curente, dacă ea produce într-adevăr unul. Dacă instrucțiunea este distructivă,
     * rezultatul este simbolul extern modificat. Dacă instrucțiunea este pură, rezultatul este instrucțiunea însăși,
     * întrucât se identifică cu rezultatul.
     *
     * @return rezultatul instrucțiunii, sau null
     */
    public IdSymbol getResult() {
        // Unele instrucțiuni nu produc rezultat
        if (! hasResult())
            return null;

        // Instrucțiunile distructive folosesc mutableResult drept rezultat
        if (isDestructive())
            return mutableResult;

        // Celelalte instrucțiuni se identifică cu rezultatul
        return this;
    }

    /**
     * Instrucțiunile pure sunt reprezentate utilizând "rezultat = ...". Cele distructive, prin "rezultat ← ...".
     *
     * @return reprezentarea sub formă de șir
     */
    @Override
    public String toString() {
        var str = (hasResult() ? getDisplayName() + (isDestructive() ? " ← " : " = ") : "") + iType + " ";
        str += operands.stream()
                .map(Symbol::getDisplayName)
                .collect(Collectors.joining(" "));
        return str;
    }
}

