package semantic;

public interface Scope {
    // Adaugă un simbol în domeniul de vizibilitate curent.
    boolean add(Symbol s);

    // Caută un simbol în domeniul de vizibilitate curent sau în cele superioare.
    Symbol lookup(String s);

    // Întoarce domeniul de vizibilitate de deasupra.
    Scope getParent();
}
