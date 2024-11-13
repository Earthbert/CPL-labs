package semantic;

import parser.ASTNode;
import parser.ASTVisitor;

public class ResolutionPassVisitor implements ASTVisitor<Void> {

    @Override
    public Void visit(ASTNode.Id id) {
        return null;
    }

    @Override
    public Void visit(ASTNode.IntLiteral intLiteral) {
        return null;
    }

    @Override
    public Void visit(ASTNode.If anIf) {
        anIf.cond.accept(this);
        anIf.thenBranch.accept(this);
        anIf.elseBranch.accept(this);
        return null;
    }

    @Override
    public Void visit(ASTNode.For aFor) {
        aFor.init.accept(this);
        aFor.cond.accept(this);
        aFor.step.accept(this);
        aFor.body.accept(this);
        return null;
    }

    @Override
    public Void visit(ASTNode.FloatLiteral floatLiteral) {
        return null;
    }

    @Override
    public Void visit(ASTNode.BoolLiteral boolLiteral) {
        return null;
    }

    @Override
    public Void visit(ASTNode.Assign assign) {
        // TODO 3: Verificăm dacă membrul din stânga este într-adevăr
        // o variabilă sau este o funcție, fiind eroare. Puteți folosi instanceof.

        Scope scopy = assign.id.getScope();

        Symbol idSymbol = scopy.lookup(assign.id.getToken().getText());

        if (idSymbol == null) {
            ASTVisitor.error(assign.id.getToken(), assign.id.getToken().getText() + " undefined");
            return null;
        }

        if (idSymbol instanceof FunctionSymbol) {
            ASTVisitor.error(assign.id.getToken(), assign.id.getToken().getText() + " is not a variable");
            return null;
        }

        assign.id.accept(this);
        assign.expr.accept(this);
        return null;
    }

    @Override
    public Void visit(ASTNode.Relational relational) {
        return null;
    }

    @Override
    public Void visit(ASTNode.Arithmetic arithmetic) {
        arithmetic.left.accept(this);
        arithmetic.right.accept(this);
        return null;
    }

    @Override
    public Void visit(ASTNode.UnaryMinus unaryMinus) {
        unaryMinus.expr.accept(this);
        return null;
    }

    @Override
    public Void visit(ASTNode.Call call) {
        // TODO 3: Verificăm dacă funcția există în scope. Nu am putut face
        // asta în prima trecere din cauza a forward declarations.
        //
        // De asemenea, verificăm că Id-ul pe care se face apelul de funcție
        // este, într-adevăr, o funcție și nu o variabilă.
        //
        // Hint: pentru a obține scope-ul, putem folosi call.id.getScope(),
        // setat la trecerea anterioară.
        Scope scopy = call.id.getScope();

        Symbol idSymbol = scopy.lookup(call.id.getToken().getText());

        if (idSymbol == null) {
            ASTVisitor.error(call.id.getToken(), call.id.getToken().getText() + " function undefined");
            return null;
        }

        if (!(idSymbol instanceof FunctionSymbol)) {
            ASTVisitor.error(call.id.getToken(), call.id.getToken().getText() + " is not a function");
            return null;
        }

        for (var arg : call.args) {
            arg.accept(this);
        }

        return null;
    }

    @Override
    public Void visit(ASTNode.Type type) {
        return null;
    }

    @Override
    public Void visit(ASTNode.FormalDef formalDef) {
        formalDef.id.accept(this);
        return null;
    }

    @Override
    public Void visit(ASTNode.LocalVarDef localVarDef) {
        processVarStructure(localVarDef);
        return null;
    }

    @Override
    public Void visit(ASTNode.GlobalVarDef globalVarDef) {
        processVarStructure(globalVarDef);
        return null;
    }

    @Override
    public Void visit(ASTNode.FuncDef funcDef) {
        return null;
    }

    @Override
    public Void visit(ASTNode.Block block) {
        for (var stmt : block.stmts) {
            stmt.accept(this);
        }
        return null;
    }

    @Override
    public Void visit(ASTNode.Program program) {
        for (var stmt : program.stmts) {
            stmt.accept(this);
        }
        return null;
    }

    Void processVarStructure(ASTNode var) {
        if (var instanceof ASTNode.GlobalVarDef) {
            if (((ASTNode.GlobalVarDef) var).initValue != null) {
                ((ASTNode.GlobalVarDef) var).initValue.accept(this);
            }
        } else if (var instanceof ASTNode.LocalVarDef) {
            if (((ASTNode.LocalVarDef) var).initValue != null) {
                ((ASTNode.LocalVarDef) var).initValue.accept(this);
            }
        }
        return null;
    }
}
