package semantic;

import java.util.LinkedHashMap;
import java.util.Map;

// O functie este atât simbol, cât și domeniu de vizibilitate pentru parametrii
// săi formali.

// TODO 1: Implementați clasa FunctionSymbol, suprascriind metodele din interfață
// și adăugându-i un nume.
public class FunctionSymbol extends IdSymbol implements Scope {

    private Map<String, Symbol> symbols = new LinkedHashMap<>();

    private Scope parent;

    public FunctionSymbol(Scope parent, String name) {
        super(name);
        this.parent = parent;
    }

    @Override
    public boolean add(Symbol sym) {
        if (symbols.containsKey(sym.getName()))
            return false;

        symbols.put(sym.getName(), sym);
        return true;
    }

    @Override
    public Symbol lookup(String s) {
        if (symbols.containsKey(s))
            return symbols.get(s);

        return parent != null ? parent.lookup(s) : null;
    }

    @Override
    public Scope getParent() {
        return parent;
    }
}