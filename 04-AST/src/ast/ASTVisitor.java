package ast;
import org.antlr.v4.runtime.*;

public interface ASTVisitor<T> {
    T visit(Id id);
    T visit(IntLiteral intLiteral);
    T visit(If iff);
    T visit(Prog prog);
    T visit(Assign assign);
    T visit(VarDef varDef);
    T visit(Arithmetic arithmetic);
    T visit(Call call);
    T visit(Local local);
    T visit(Block block);
    T visit(FuncDef funcDef);
    T visit(Type type);
    T visit(Formal formal);
    T visit(Float float1);
	T visit(For for1);
    T visit(Comparison comparison);
	T visit(UnaryMinus unaryMinus);
}
