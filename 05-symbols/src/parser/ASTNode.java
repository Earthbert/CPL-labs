package parser;
import semantic.*;
import org.antlr.v4.runtime.Token;
import java.util.*;

// Rădăcina ierarhiei de clase reprezentând nodurile arborelui de sintaxă
// abstractă (AST). Singura metodă permite primirea unui visitor.
public abstract class ASTNode {
    // Reținem un token descriptiv al nodului, pentru a putea afișa ulterior
    // informații legate de linia și coloana eventualelor erori semantice.
    protected Token token;
    public String debugStr = null;		// used in codegen

    ASTNode(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return null;
    }

    // Orice expresie.
    public static abstract class Expression extends ASTNode {
        Expression(Token token) {
            super(token);
        }
    }

    // Identificatori
    public static class Id extends Expression {
        public IdSymbol symbol;
        public Scope scope;

        Id(Token token) {
            super(token);
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }

        public IdSymbol getSymbol() {
            return symbol;
        }

        public void setSymbol(IdSymbol symbol) {
            this.symbol = symbol;
        }

        public Scope getScope() {
            return scope;
        }

        public void setScope(Scope scope) {
            this.scope = scope;
        }

    }

    // Literali întregi
    public static class IntLiteral extends Expression {
        IntLiteral(Token token) {
            super(token);
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    // Construcția if.
    public static class If extends Expression {
        // Sunt necesare trei câmpuri pentru cele trei componente ale expresiei.
        public Expression cond;
        public Expression thenBranch;
        public Expression elseBranch;

        If(Expression cond,
           Expression thenBranch,
           Expression elseBranch,
           Token start) {
            super(start);
            this.cond = cond;
            this.thenBranch = thenBranch;
            this.elseBranch = elseBranch;
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    // Construcția for.
    public static class For extends Expression {
        public Expression init;
        public Expression cond;
        public Expression step;
        public Expression body;

        For(Expression init,
            Expression cond,
            Expression step,
            Expression body,
            Token start) {
            super(start);
            this.init = init;
            this.cond = cond;
            this.step = step;
            this.body = body;
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    public static class FloatLiteral extends Expression {
        FloatLiteral(Token token) {
            super(token);
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    public static class BoolLiteral extends Expression {
        BoolLiteral(Token token) {
            super(token);
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    // Token-ul pentru un Assign va fi '='
    public static class Assign extends Expression {
        public Id id;
        public Expression expr;

        Assign(Id id, Expression expr, Token token) {
            super(token);
            this.id = id;
            this.expr = expr;
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    // Pentru un Relational avem 'op=(LT | LE | EQUAL)' ca reprezentare
    public static class Relational extends Expression {
        public Expression left;
        public Expression right;

        Relational(Expression left, Expression right, Token op) {
            super(op);
            this.left = left;
            this.right = right;
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    public static class Arithmetic extends Expression {
        public Expression left;
        public Expression right;

        Arithmetic(Expression left, Expression right, Token op) {
            super(op);
            this.left = left;
            this.right = right;
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    // Pentru UnaryMinus, operatorul va fi '-'
    public static class UnaryMinus extends Expression {
        public Expression expr;

        UnaryMinus(Expression expr, Token op) {
            super(op);
            this.expr = expr;
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    // Apelul unei functii poate avea oricate argumente. Le vom salva intr-o lista
    public static class Call extends Expression {
        public Id id;
        public LinkedList<Expression> args;

        Call(Id id, LinkedList<Expression> args, Token start) {
            super(start);
            this.id = id;
            this.args = args;
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    public static abstract class Definition extends ASTNode {
        Definition(Token token) {
            super(token);
        }
    }

    public static class Type extends ASTNode {
        Type(Token token) {
            super(token);
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    // Parametrul formal din definita unei functii
    public static class FormalDef extends Definition {
        public Type type;
        public Id id;

        FormalDef(Type type, Id id, Token token) {
            super(token);
            this.type = type;
            this.id = id;
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    // Definiție de variabilă locală
    public static class LocalVarDef extends Definition {
        public Type type;
        public Id id;
        public Expression initValue;

        LocalVarDef(Type type, Id id, Expression initValue, Token token) {
            super(token);
            this.type = type;
            this.id = id;
            this.initValue = initValue;
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    // Initializarea poate sa lipseasca
    public static class GlobalVarDef extends Definition {
        public Type type;
        public Id id;
        public Expression initValue;

        GlobalVarDef(Type type, Id id, Expression initValue, Token token) {
            super(token);
            this.type = type;
            this.id = id;
            this.initValue = initValue;
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    // Definirea unei functii
    public static class FuncDef extends Definition {
        public Type type;
        public Id id;
        public LinkedList<FormalDef> formalDefs;
        public Block body;

        FuncDef(Type type, Id id, LinkedList<FormalDef> formalDefs, Block body, Token token) {
            super(token);
            this.type = type;
            this.id = id;
            this.formalDefs = formalDefs;
            this.body = body;
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    public static class Block extends Expression {
        public LinkedList<ASTNode> stmts;

        Block(LinkedList<ASTNode> stmts, Token token) {
            super(token);
            this.stmts = stmts;
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    public static class Program extends ASTNode {
        public LinkedList<ASTNode> stmts;

        Program(LinkedList<ASTNode> stmts, Token token) {
            super(token);
            this.stmts = stmts;
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }
}


