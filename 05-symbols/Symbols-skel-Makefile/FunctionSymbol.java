import java.util.*;

// O functie este atât simbol, cât și domeniu de vizibilitate pentru parametrii
// săi formali.

/*
 TODO 1: Implementați clasa FunctionSymbol, suprascriind metodele din interfață
        și adăugându-i un nume.
 */
public class FunctionSymbol extends IdSymbol implements Scope {
    public FunctionSymbol(String name) {
        super(name);
    }

    @Override
    public boolean add(Symbol s) {
        return false;
    }

    @Override
    public Symbol lookup(String s) {
        return null;
    }

    @Override
    public Scope getParent() {
        return null;
    }
}