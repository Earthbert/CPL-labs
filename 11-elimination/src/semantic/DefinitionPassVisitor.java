package semantic;

import parser.ASTNode;
import parser.ASTVisitor;

public class DefinitionPassVisitor implements ASTVisitor<Void> {

    Scope currentScope = null;

    @Override
    public Void visit(final ASTNode.Id id) {
        final var symbol = this.currentScope.lookup(id.getToken().getText());

        id.setScope(this.currentScope);

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
    public Void visit(final ASTNode.IntLiteral intLiteral) {
        return null;
    }

    @Override
    public Void visit(final ASTNode.If anIf) {
        anIf.cond.accept(this);
        anIf.thenBranch.accept(this);
        anIf.elseBranch.accept(this);
        return null;
    }

    @Override
    public Void visit(final ASTNode.For aFor) {
        aFor.init.accept(this);
        aFor.cond.accept(this);
        aFor.step.accept(this);
        aFor.body.accept(this);

        return null;
    }

    @Override
    public Void visit(final ASTNode.FloatLiteral floatLiteral) {
        return null;
    }

    @Override
    public Void visit(final ASTNode.BoolLiteral boolLiteral) {
        return null;
    }

    @Override
    public Void visit(final ASTNode.Assign assign) {
        assign.id.accept(this);
        assign.expr.accept(this);
        return null;
    }

    @Override
    public Void visit(final ASTNode.Relational relational) {
        relational.left.accept(this);
        relational.right.accept(this);
        return null;
    }

    @Override
    public Void visit(final ASTNode.Arithmetic arithmetic) {
        arithmetic.left.accept(this);
        arithmetic.right.accept(this);
        return null;
    }

    @Override
    public Void visit(final ASTNode.UnaryMinus unaryMinus) {
        unaryMinus.expr.accept(this);
        return null;
    }

    @Override
    public Void visit(final ASTNode.Call call) {
        final var id = call.id;
        for (final var arg : call.args) {
            arg.accept(this);
        }
        id.setScope(this.currentScope);
        return null;
    }

    @Override
    public Void visit(final ASTNode.Type type) {
        return null;
    }

    @Override
    public Void visit(final ASTNode.FormalDef formalDef) {

        return this.processVarStructure(formalDef.id, formalDef.type, null, false);
    }

    @Override
    public Void visit(final ASTNode.LocalVarDef localVarDef) {

        return this.processVarStructure(localVarDef.id, localVarDef.type, localVarDef.initValue, false);
    }

    @Override
    public Void visit(final ASTNode.GlobalVarDef globalVarDef) {

        return this.processVarStructure(globalVarDef.id, globalVarDef.type, globalVarDef.initValue, true);
    }

    @Override
    public Void visit(final ASTNode.FuncDef funcDef) {
        final var id = funcDef.id;
        final var type = funcDef.type;
        final var functionSymbol = new FunctionSymbol(this.currentScope, id.getToken().getText());

        // Verificăm faptul că o funcție cu același nume nu a mai fost
        // definită până acum.
        if (!this.currentScope.add(functionSymbol)) {
            ASTVisitor.error(id.getToken(), id.getToken().getText() + " function redefined");
            return null;
        }
        this.currentScope = functionSymbol;

        id.setSymbol(functionSymbol);
        id.setScope(this.currentScope);

        // TODO 1: Reținem informația de tip în simbolul nou creat.

        // Căutăm tipul funcției.
        final Symbol typeSymbol = this.currentScope.lookup(type.getToken().getText());

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

        for (final var formal : funcDef.formalDefs) {
            formal.accept(this);
        }

        funcDef.body.accept(this);

        this.currentScope = this.currentScope.getParent();

        return null;
    }

    @Override
    public Void visit(final ASTNode.Block block) {
        this.currentScope = new DefaultScope(this.currentScope);

        for (final var stmt : block.stmts)
            stmt.accept(this);

        this.currentScope = this.currentScope.getParent();

        return null;

    }

    @Override
    public Void visit(final ASTNode.Program program) {
        // Domeniul de vizibilitate global conține inițial doar numele
        // tipurilor.
        this.currentScope = new DefaultScope(null);
        this.currentScope.add(TypeSymbol.INT);
        this.currentScope.add(TypeSymbol.FLOAT);
        this.currentScope.add(TypeSymbol.BOOL);

        final var printInt = new FunctionSymbol(this.currentScope, "print_int");
        final var n = new IdSymbol("n");
        n.setType(TypeSymbol.INT);
        printInt.add(n);
        this.currentScope.add(printInt);

        for (final var stmt : program.stmts) {
            stmt.accept(this);
        }
        return null;
    }

    Void processVarStructure(final ASTNode.Id id, final ASTNode.Type type, final ASTNode.Expression initValue, final boolean globalFlag) {

        final var symbol = new IdSymbol(id.getToken().getText());
        symbol.isGlobal = globalFlag;

        // Semnalăm eroare dacă există deja variabila în scope-ul curent.
        if (!this.currentScope.add(symbol)) {
            ASTVisitor.error(id.getToken(), id.getToken().getText() + " redefined");
            return null;
        }

        // Atașăm simbolul nodului din arbore.
        id.setSymbol(symbol);

        // TODO 1: Reținem informația de tip în simbolul nou creat.

        // Căutăm tipul variabilei.
        final Symbol typeSymbol = this.currentScope.lookup(type.getToken().getText());

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