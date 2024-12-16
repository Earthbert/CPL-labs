package semantic;

import org.antlr.v4.runtime.misc.NotNull;
import parser.ASTNode;
import parser.ASTVisitor;

public class DefinitionPassVisitor implements ASTVisitor<Void> {

    Scope currentScope = null;

    @Override
    public Void visit(ASTNode.Id id) {
        var symbol = currentScope.lookup(id.getToken().getText());

        id.setScope(currentScope);

        // Semnalăm eroare dacă nu există.
        if (symbol == null) {
            ASTVisitor.error(id.getToken(), id.getToken().getText() + " undefined");
            return null;
        }

        // Atașăm simbolul nodului din arbore.
        id.setSymbol((IdSymbol) symbol);

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
        assign.id.accept(this);
        assign.expr.accept(this);
        return null;
    }

    @Override
    public Void visit(ASTNode.Relational relational) {
        relational.left.accept(this);
        relational.right.accept(this);
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
        var id = call.id;
        for (var arg : call.args) {
            arg.accept(this);
        }
        id.setScope(currentScope);
        return null;
    }

    @Override
    public Void visit(ASTNode.Type type) {
        return null;
    }

    @Override
    public Void visit(ASTNode.FormalDef formalDef) {

        return processVarStructure(formalDef.id, formalDef.type, null, false);
    }

    @Override
    public Void visit(ASTNode.LocalVarDef localVarDef) {

        return processVarStructure(localVarDef.id, localVarDef.type, localVarDef.initValue, false);
    }

    @Override
    public Void visit(ASTNode.GlobalVarDef globalVarDef) {

        return processVarStructure(globalVarDef.id, globalVarDef.type, globalVarDef.initValue, true);
    }

    @Override
    public Void visit(ASTNode.FuncDef funcDef) {
        var id = funcDef.id;
        var type = funcDef.type;
        var functionSymbol = new FunctionSymbol(currentScope, id.getToken().getText());

        // Verificăm faptul că o funcție cu același nume nu a mai fost
        // definită până acum.
        if (!currentScope.add(functionSymbol)) {
            ASTVisitor.error(id.getToken(), id.getToken().getText() + " function redefined");
            return null;
        }
        currentScope = functionSymbol;

        id.setSymbol(functionSymbol);
        id.setScope(currentScope);

        // TODO 1: Reținem informația de tip în simbolul nou creat.

        // Căutăm tipul funcției.
        Symbol typeSymbol = currentScope.lookup(type.getToken().getText());

        // Semnalăm eroare dacă nu există.
        // NOTE: Această verificare nu este strict necesară pentru CPLang, deoarece din definiția token-ului
        // CPLangParser.TYPE se garantează că avem un tip valid.
        if (typeSymbol == null) {
            ASTVisitor.error(type.getToken(), "Type " + type.getToken().getText() + " is not defined");
            return null;
        }

        if (!(typeSymbol instanceof TypeSymbol)) {
            ASTVisitor.error(type.getToken(), type.getToken().getText() + " is not a type");
            return null;
        }

        // Reținem informația de tip în cadrul simbolului aferent funcției.
        functionSymbol.setType((TypeSymbol) typeSymbol);

        for (var formal : funcDef.formalDefs) {
            formal.accept(this);
        }

        funcDef.body.accept(this);

        currentScope = currentScope.getParent();

        return null;
    }

    @Override
    public Void visit(ASTNode.Block block) {
        currentScope = new DefaultScope(currentScope);

        for (var stmt : block.stmts)
            stmt.accept(this);

        currentScope = currentScope.getParent();

        return null;

    }

    @Override
    public Void visit(ASTNode.Program program) {
        // Domeniul de vizibilitate global conține inițial doar numele
        // tipurilor.
        currentScope = new DefaultScope(null);
        currentScope.add(TypeSymbol.INT);
        currentScope.add(TypeSymbol.FLOAT);
        currentScope.add(TypeSymbol.BOOL);

        for (var stmt : program.stmts) {
            stmt.accept(this);
        }
        return null;
    }

    Void processVarStructure(ASTNode.Id id, ASTNode.Type type, ASTNode.Expression initValue, boolean globalFlag) {

        var symbol = new IdSymbol(id.getToken().getText());
        symbol.isGlobal = globalFlag;

        // Semnalăm eroare dacă există deja variabila în scope-ul curent.
        if (!currentScope.add(symbol)) {
            ASTVisitor.error(id.getToken(), id.getToken().getText() + " redefined");
            return null;
        }

        // Atașăm simbolul nodului din arbore.
        id.setSymbol(symbol);

        // TODO 1: Reținem informația de tip în simbolul nou creat.

        // Căutăm tipul variabilei.
        Symbol typeSymbol = currentScope.lookup(type.getToken().getText());

        // Semnalăm eroare dacă nu există.
        // NOTE: Această verificare nu este strict necesară pentru CPLang, deoarece din definiția token-ului
        // CPLangParser.TYPE se garantează că avem un tip valid.
        if (typeSymbol == null) {
            ASTVisitor.error(type.getToken(), "Type " + type.getToken().getText() + " is not defined");
            return null;
        }

        if (!(typeSymbol instanceof TypeSymbol)) {
            ASTVisitor.error(type.getToken(), type.getToken().getText() + " is not a type");
            return null;
        }

        // Reținem informația de tip în cadrul simbolului aferent
        // variabilei
        symbol.setType((TypeSymbol) typeSymbol);

        if (initValue != null) {
            initValue.accept(this);
        }

        return null;
    }
}