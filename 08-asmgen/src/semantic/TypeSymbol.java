package semantic;

public class TypeSymbol extends Symbol {
    public TypeSymbol(String name) {
        super(name);
    }

    // Symboluri aferente tipurilor, definite global
    public static final TypeSymbol INT = new TypeSymbol("Int");
    public static final TypeSymbol FLOAT = new TypeSymbol("Float");
    public static final TypeSymbol BOOL = new TypeSymbol("Bool");

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof TypeSymbol) {
            return name.equals(((TypeSymbol) obj).name);
        }
        return false;
    }
}
