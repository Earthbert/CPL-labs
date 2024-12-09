package parser;

import java.util.LinkedList;

import org.antlr.v4.runtime.Token;

import semantic.IdSymbol;
import semantic.Scope;
import semantic.TypeSymbol;

// Rădăcina ierarhiei de clase reprezentând nodurile arborelui de sintaxă
// abstractă (AST). Singura metodă permite primirea unui visitor.
public abstract class ASTNode {
    // Reținem un token descriptiv al nodului, pentru a putea afișa ulterior
    // informații legate de linia și coloana eventualelor erori semantice.
    protected Token token;
    public String debugStr = null; // used in codegen

    ASTNode(final Token token) {
        this.token = token;
    }

    public Token getToken() {
        return this.token;
    }

    public <T> T accept(final ASTVisitor<T> visitor) {
        return null;
    }

    // Orice expresie.
    public static abstract class Expression extends ASTNode {
        Expression(final Token token) {
            super(token);
        }
    }

    // Identificatori
    public static class Id extends Expression {
        public IdSymbol symbol;
        public Scope scope;

        Id(final Token token) {
            super(token);
        }

        @Override
        public <T> T accept(final ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }

        public IdSymbol getSymbol() {
            return this.symbol;
        }

        public void setSymbol(final IdSymbol symbol) {
            this.symbol = symbol;
        }

        public Scope getScope() {
            return this.scope;
        }

        public void setScope(final Scope scope) {
            this.scope = scope;
        }

    }

    // Literali întregi
    public static class IntLiteral extends Expression {
        IntLiteral(final Token token) {
            super(token);
        }

        @Override
        public <T> T accept(final ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    // Construcția if.
    public static class If extends Expression {
        // Sunt necesare trei câmpuri pentru cele trei componente ale expresiei.
        public Expression cond;
        public Expression thenBranch;
        public Expression elseBranch;

        If(final Expression cond,
                final Expression thenBranch,
                final Expression elseBranch,
                final Token start) {
            super(start);
            this.cond = cond;
            this.thenBranch = thenBranch;
            this.elseBranch = elseBranch;
        }

        @Override
        public <T> T accept(final ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    // Construcția for.
    public static class For extends Expression {
        public Expression init;
        public Expression cond;
        public Expression step;
        public Expression body;

        For(final Expression init,
                final Expression cond,
                final Expression step,
                final Expression body,
                final Token start) {
            super(start);
            this.init = init;
            this.cond = cond;
            this.step = step;
            this.body = body;
        }

        @Override
        public <T> T accept(final ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    public static class FloatLiteral extends Expression {
        FloatLiteral(final Token token) {
            super(token);
        }

        @Override
        public <T> T accept(final ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    public static class BoolLiteral extends Expression {
        BoolLiteral(final Token token) {
            super(token);
        }

        @Override
        public <T> T accept(final ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    // Token-ul pentru un Assign va fi '='
    public static class Assign extends Expression {
        public Id id;
        public Expression expr;

        Assign(final Id id, final Expression expr, final Token token) {
            super(token);
            this.id = id;
            this.expr = expr;
        }

        @Override
        public <T> T accept(final ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    public static class BinaryOp extends Expression {
        public Expression left;
        public Expression right;

        public TypeSymbol leftType;
        public TypeSymbol rightType;

        BinaryOp(final Expression left, final Expression right, final Token op) {
            super(op);
            this.left = left;
            this.right = right;
        }
    }

    // Pentru un Relational avem 'op=(LT | LE | EQUAL)' ca reprezentare
    public static class Relational extends BinaryOp {

        Relational(final Expression left, final Expression right, final Token op) {
            super(left, right, op);
        }

        @Override
        public <T> T accept(final ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    public static class Arithmetic extends BinaryOp {

        Arithmetic(final Expression left, final Expression right, final Token op) {
            super(left, right, op);
        }

        @Override
        public <T> T accept(final ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    // Pentru UnaryMinus, operatorul va fi '-'
    public static class UnaryMinus extends Expression {
        public Expression expr;

        UnaryMinus(final Expression expr, final Token op) {
            super(op);
            this.expr = expr;
        }

        @Override
        public <T> T accept(final ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    // Apelul unei functii poate avea oricate argumente. Le vom salva intr-o lista
    public static class Call extends Expression {
        public Id id;
        public LinkedList<Expression> args;

        Call(final Id id, final LinkedList<Expression> args, final Token start) {
            super(start);
            this.id = id;
            this.args = args;
        }

        @Override
        public <T> T accept(final ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    public static abstract class Definition extends ASTNode {
        Definition(final Token token) {
            super(token);
        }
    }

    public static class Type extends ASTNode {
        Type(final Token token) {
            super(token);
        }

        @Override
        public <T> T accept(final ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    // Parametrul formal din definita unei functii
    public static class FormalDef extends Definition {
        public Type type;
        public Id id;

        FormalDef(final Type type, final Id id, final Token token) {
            super(token);
            this.type = type;
            this.id = id;
        }

        @Override
        public <T> T accept(final ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    // Definiție de variabilă locală
    public static class LocalVarDef extends Definition {
        public Type type;
        public Id id;
        public Expression initValue;

        LocalVarDef(final Type type, final Id id, final Expression initValue, final Token token) {
            super(token);
            this.type = type;
            this.id = id;
            this.initValue = initValue;
        }

        @Override
        public <T> T accept(final ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    // Initializarea poate sa lipseasca
    public static class GlobalVarDef extends Definition {
        public Type type;
        public Id id;
        public Expression initValue;

        GlobalVarDef(final Type type, final Id id, final Expression initValue, final Token token) {
            super(token);
            this.type = type;
            this.id = id;
            this.initValue = initValue;
        }

        @Override
        public <T> T accept(final ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    // Definirea unei functii
    public static class FuncDef extends Definition {
        public Type type;
        public Id id;
        public LinkedList<FormalDef> formalDefs;
        public Block body;

        FuncDef(final Type type, final Id id, final LinkedList<FormalDef> formalDefs, final Block body,
                final Token token) {
            super(token);
            this.type = type;
            this.id = id;
            this.formalDefs = formalDefs;
            this.body = body;
        }

        @Override
        public <T> T accept(final ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    public static class Block extends Expression {
        public LinkedList<ASTNode> stmts;

        Block(final LinkedList<ASTNode> stmts, final Token token) {
            super(token);
            this.stmts = stmts;
        }

        @Override
        public <T> T accept(final ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    public static class Program extends ASTNode {
        public LinkedList<ASTNode> stmts;

        Program(final LinkedList<ASTNode> stmts, final Token token) {
            super(token);
            this.stmts = stmts;
        }

        @Override
        public <T> T accept(final ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }
}
