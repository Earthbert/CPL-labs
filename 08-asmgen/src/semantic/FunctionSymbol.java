package semantic;

import java.util.LinkedHashMap;
import java.util.Map;

// O functie este atât simbol, cât și domeniu de vizibilitate pentru parametrii
// săi formali.

public class FunctionSymbol extends IdSymbol implements Scope {
    protected Scope parent;
    // LinkedHashMap reține ordinea adăugării.
    protected Map<String, Symbol> symbols = new LinkedHashMap<>();



    public FunctionSymbol(final Scope parent, final String name) {
        super(name);
        this.parent = parent;
        this.isGlobal = true;
    }

    @Override
    public boolean add(final Symbol sym) {
        // Ne asigurăm că simbolul nu există deja în domeniul de vizibilitate
        // curent.
        if (this.symbols.containsKey(sym.getName())) return false;

        this.symbols.put(sym.getName(), sym);

        return true;
    }

    @Override
    public Symbol lookup(final String s) {
        final var sym = this.symbols.get(s);

        if (sym != null) return sym;

        // Dacă nu găsim simbolul în domeniul de vizibilitate curent, îl căutăm
        // în domeniul de deasupra.
        if (this.parent != null) return this.parent.lookup(s);

        return null;
    }

    @Override
    public Scope getParent() {
        return this.parent;
    }

    public Map<String, Symbol> getFormals() {
        return this.symbols;
    }
}