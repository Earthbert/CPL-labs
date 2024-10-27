package ast;

import org.antlr.v4.runtime.Token;
import java.util.*;

// Rădăcina ierarhiei de clase reprezentând nodurile arborelui de sintaxă
// abstractă (AST). Singura metodă permite primirea unui visitor.
public abstract class ASTNode {
    // Reținem un token descriptiv al nodului, pentru a putea afișa ulterior
    // informații legate de linia și coloana eventualelor erori semantice.
    protected Token token;
    public String debugStr = null; // used in codegen

    ASTNode(Token token) {
        this.token = token;
    }

    Token getToken() {
        return token;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return null;
    }
}

// Orice expresie.
abstract class Expression extends ASTNode {
    Expression(Token token) {
        super(token);
    }
}

// Identificatori
class Id extends Expression {
    Id(Token token) {
        super(token);
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

// Literali întregi
class IntLiteral extends Expression {
    IntLiteral(Token token) {
        super(token);
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

// Construcția if.
class If extends Expression {
    // Sunt necesare trei câmpuri pentru cele trei componente ale expresiei.
    Expression cond;
    Expression thenBranch;
    Expression elseBranch;

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

class Prog extends ASTNode {
    List<ASTNode> elements;

    Prog(List<ASTNode> elements) {
        super(null);
        this.elements = elements;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class Assign extends Expression {
    Id id;
    Expression expr;

    Assign(Id id, Expression expr, Token start) {
        super(start);
        this.id = id;
        this.expr = expr;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class VarDef extends ASTNode {
    Type type;
    Id id;
    Expression expr;

    VarDef(Type type, Id id, Expression expr, Token start) {
        super(start);
        this.id = id;
        this.type = type;
        this.expr = expr;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class FuncDef extends ASTNode {
    Type type;
    Id id;
    List<Formal> args;
    Block body;

    FuncDef(Type type, Id id, List<Formal> args, Block body, Token start) {
        super(start);
        this.type = type;
        this.id = id;
        this.args = args;
        this.body = body;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class Block extends ASTNode {
    List<ASTNode> elements;

    Block(List<ASTNode> elements, Token start) {
        super(start);
        this.elements = elements;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class Type extends ASTNode {
    String name;

    Type(Token start) {
        super(start);
        this.name = start.getText();
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class Local extends ASTNode {
    Type type;
    Id id;
    Expression value;

    Local(Type type, Id id, Expression value, Token start) {
        super(start);
        this.type = type;
        this.id = id;
        this.value = value;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class Call extends Expression {
    Id id;
    List<Expression> args;

    Call(Id id, List<Expression> args, Token start) {
        super(start);
        this.id = id;
        this.args = args;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class Arithmetic extends Expression {
    Expression left;
    Expression right;
    String op;

    Arithmetic(Expression left, Expression right, String op, Token start) {
        super(start);
        this.left = left;
        this.right = right;
        this.op = op;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class Formal extends ASTNode {
    Type type;
    Id id;

    Formal(Type type, Id id, Token start) {
        super(start);
        this.type = type;
        this.id = id;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class Float extends Expression {
    float value;

    Float(Token token) {
        super(token);
        this.value = java.lang.Float.parseFloat(token.getText());
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class For extends ASTNode {
    Expression init;
    Expression cond;
    Expression update;
    Block body;

    For(Expression init, Expression cond, Expression update, Block body, Token start) {
        super(start);
        this.init = init;
        this.cond = cond;
        this.update = update;
        this.body = body;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class Comparison extends Expression {
    Expression left;
    Expression right;
    String op;

    Comparison(Expression left, Expression right, String op, Token start) {
        super(start);
        this.left = left;
        this.right = right;
        this.op = op;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class UnaryMinus extends Expression {
    Expression expr;

    UnaryMinus(Expression expr, Token start) {
        super(start);
        this.expr = expr;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
