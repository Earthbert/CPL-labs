public interface ASTVisitor<T> {
    T visit(Id id);
    T visit(Int intt);
    T visit(If iff);
    // TODO2: Adăugați metode pentru fiecare clasă definită anterior
}
