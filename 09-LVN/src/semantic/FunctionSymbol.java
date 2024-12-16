package semantic;

import irgen.CFG;

import java.util.*;
import java.util.stream.Collectors;

// O functie este atât simbol, cât și domeniu de vizibilitate pentru parametrii
// săi formali.

public class FunctionSymbol extends IdSymbol implements Scope {
 
    // LinkedHashMap reține ordinea adăugării.
    protected Map<String, Symbol> symbols = new LinkedHashMap<>();
    
    protected Scope parent;

    /**
     * Fiecare funcție își conține CFG-ul
     */
    public CFG cfg;
    
    public FunctionSymbol(Scope parent, String name) {
        super(name);
        this.parent = parent;
        this.isGlobal = true;
    }

    @Override
    public boolean add(Symbol sym) {
        // Ne asigurăm că simbolul nu există deja în domeniul de vizibilitate
        // curent.
        if (symbols.containsKey(sym.getName()))
            return false;
        
        symbols.put(sym.getName(), sym);
        
        return true;
    }

    @Override
    public Symbol lookup(String s) {
        var sym = symbols.get(s);
        
        if (sym != null)
            return sym;
        
        // Dacă nu găsim simbolul în domeniul de vizibilitate curent, îl căutăm
        // în domeniul de deasupra.
        if (parent != null)
            return parent.lookup(s);
        
        return null;
    }

    @Override
    public Scope getParent() {
        return parent;
    }
    
    public Map<String, Symbol> getFormals() {
        return symbols;
    }

    @Override
    public String toString() {
        var str = getDisplayName();
        str += "(" + symbols.values().stream()
                .map(Symbol::getDisplayName)
                .collect(Collectors.joining(", "))
                + ")";

        if (cfg != null)
            str += " {\n" + cfg + "\n}";

        return str;
    }
}