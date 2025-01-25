package semantic;

import java.util.*;

/**
 * Simbol aferent unui literal întreg.
 *
 * Asigură câte un simbol unic pentru toate utilizările unui anumit literal întreg dintr-un program. De exemplu, toate
 * aparițiile literalului 3 corespund unei singure instanțe de IntSymbol.
 */
public class IntSymbol extends Symbol {

    public int value;
    protected IntSymbol(int value) {
        super(String.valueOf(value));
        this.value = value;
    }

    protected static Map<Integer, IntSymbol> intSymbols = new HashMap<>();

    public static IntSymbol get(int value) {
        var intSymbol = intSymbols.get(value);

        if (intSymbol == null) {
            intSymbol = new IntSymbol(value);
            intSymbols.put(value, intSymbol);
        }

        return intSymbol;
    }
}
