package semantic;

public class IdSymbol extends Symbol {
    // Fiecare identificator posedă un tip.
    protected TypeSymbol type;

    public boolean isGlobal;

    public Integer offset;  // relative to fp

    public IdSymbol(final String name) {
        super(name);
    }

    public IdSymbol(final String name, final TypeSymbol type) {
        super(name);
        this.type = type;
    }

    public void setType(final TypeSymbol type) {
        this.type = type;
    }

    public TypeSymbol getType() {
        return this.type;
    }
}