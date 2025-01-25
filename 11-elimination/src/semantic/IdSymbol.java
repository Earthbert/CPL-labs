package semantic;

import java.util.HashMap;
import java.util.Map;

/**
 * Simbol cu nume (variabilă, funcție etc.).
 *
 * Reține asocieri la nivelul întregului program între nume și ultima versiune utilizată pentru acel nume.
 * Acestea au scopul de a evita conflictele dintre simbolurile cu același nume, întrucât separația asigurată
 * de domeniile de vizibilitate diferite la nivelul codului sursă și al analizei semantice nu mai este valabilă
 * la nivelul codului intermediar.
 *
 * De exemplu, în secvența CPLang:
 *
 * Int a = 1;
 * { Int a = 2; a; }
 * a + 3;
 *
 * a din a + 3 se referă la a = 1, și acest lucru transpare din gestiunea adecvată a domeniilor de vizibilitate
 * în cadrul analizei semantice. Însă, secvența aferentă de cod intermediar este una liniară, de forma:
 *
 * a <- 1
 * a1 <- 2
 * add = a + 3
 *
 * și în absența versiunilor diferite al numelui a, ar apărea conflicte, cel puțin la nivel vizual.
 */
public class IdSymbol extends Symbol {
    protected static Map<String, Integer> versions = new HashMap<>();

    // Fiecare identificator posedă un tip.
    protected TypeSymbol type;
    
    public boolean isGlobal;
    
    public IdSymbol(String name) {
        super(name);
    }
    
    public void setType(TypeSymbol type) {
        this.type = type;
    }
    
    public TypeSymbol getType() {
        return type;
    }

    @Override
    public String getDisplayName() {
        return (isGlobal ? "@" : "") + name;
    }

    /**
     * Adaugă o nouă versiune la sfârșitul numelui acestui simbol. Dacă simbolul desemnează un nume SSA, se utilizează
     * și caracterul '.' între nume și noua versiune (e.g. a.0). Pentru numele non-SSA, versiunea 0 nu se mai adaugă,
     * pentru simplitate.
     *
     * @param  isSSAName true dacă este vorba de un nume SSA
     * @return           noul nume, cu versiunea adăugată
     */
    public IdSymbol applyNextVersion(boolean isSSAName) {
        if (name == null)
            return this;

        if (isSSAName)
            name += ".";
        var version = versions.get(name);

        if (version == null)
            version = 0;
        else
            version++;

        versions.put(name, version);

        if (isSSAName || version > 0)
            name += version;

        return this;
    }

    public IdSymbol applyNextVersion() {
        return applyNextVersion(false);
    }
}