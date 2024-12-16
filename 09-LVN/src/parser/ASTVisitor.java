package parser;
import org.antlr.v4.runtime.*;
public interface ASTVisitor<T> {
    T visit(ASTNode.Id id);

    T visit(ASTNode.IntLiteral intLiteral);

    T visit(ASTNode.If anIf);

    T visit(ASTNode.For aFor);

    T visit(ASTNode.FloatLiteral floatLiteral);

    T visit(ASTNode.BoolLiteral boolLiteral);

    T visit(ASTNode.Assign assign);

    T visit(ASTNode.Relational relational);

    T visit(ASTNode.Arithmetic arithmetic);

    T visit(ASTNode.UnaryMinus unaryMinus);

    T visit(ASTNode.Call call);

    T visit(ASTNode.Type type);

    T visit(ASTNode.FormalDef formalDef);

    T visit(ASTNode.LocalVarDef localVarDef);

    T visit(ASTNode.GlobalVarDef globalVarDef);

    T visit(ASTNode.FuncDef funcDef);

    T visit(ASTNode.Block block);

    T visit(ASTNode.Program program);

    static void error(Token token, String message) {
        System.err.println("line " + token.getLine()
                + ":" + (token.getCharPositionInLine() + 1)
                + ", " + message);
    }

}
