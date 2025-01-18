package parser;

public class PrintVisitor implements ASTVisitor<Void> {
    int indent = 0;

    void printIndent(String str) {
        for (int i = 0; i < indent; i++)
            System.out.print("\t");
        System.out.println(str);
    }

    @Override
    public Void visit(ASTNode.Id id) {
        printIndent("ID " + id.token.getText());
        return null;
    }

    @Override
    public Void visit(ASTNode.IntLiteral intLiteral) {
        printIndent("INT " + intLiteral.token.getText());
        return null;
    }

    @Override
    public Void visit(ASTNode.If anIf) {
        printIndent("If");
        indent++;
        anIf.cond.accept(this);
        anIf.thenBranch.accept(this);
        anIf.elseBranch.accept(this);
        indent--;
        return null;
    }

    @Override
    public Void visit(ASTNode.For aFor) {
        printIndent("For");
        indent++;
        aFor.init.accept(this);
        aFor.cond.accept(this);
        aFor.step.accept(this);
        aFor.body.accept(this);
        indent--;
        return null;
    }

    @Override
    public Void visit(ASTNode.FloatLiteral floatLiteral) {
        printIndent("FLOAT " + floatLiteral.token.getText());
        return null;
    }

    @Override
    public Void visit(ASTNode.BoolLiteral boolLiteral) {
        printIndent("BOOL " + boolLiteral.token.getText());
        return null;
    }

    @Override
    public Void visit(ASTNode.Assign assign) {
        printIndent("Assign");
        indent++;
        assign.id.accept(this);
        assign.expr.accept(this);
        indent--;
        return null;
    }

    @Override
    public Void visit(ASTNode.Relational relational) {
        printIndent(relational.getToken().getText());
        indent++;
        relational.left.accept(this);
        relational.right.accept(this);
        indent--;
        return null;
    }

    @Override
    public Void visit(ASTNode.Arithmetic arithmetic) {
        printIndent(arithmetic.getToken().getText());
        indent++;
        arithmetic.left.accept(this);
        arithmetic.right.accept(this);
        indent--;
        return null;
    }

    @Override
    public Void visit(ASTNode.UnaryMinus unaryMinus) {
        printIndent("-");
        indent++;
        unaryMinus.expr.accept(this);
        indent--;
        return null;
    }

    @Override
    public Void visit(ASTNode.Call call) {
        printIndent("Call");
        indent++;
        call.id.accept(this);
        call.args.forEach(a -> a.accept(this));
        indent--;
        return null;
    }

    @Override
    public Void visit(ASTNode.Type type) {
        printIndent("TYPE " + type.token.getText());
        return null;
    }

    @Override
    public Void visit(ASTNode.FormalDef formalDef) {
        printIndent("Formal");
        indent++;
        formalDef.type.accept(this);
        formalDef.id.accept(this);
        indent--;

        return null;
    }

    @Override
    public Void visit(ASTNode.LocalVarDef localVarDef) {
        printIndent("LocalVarDef");
        indent++;
        localVarDef.type.accept(this);
        localVarDef.id.accept(this);
        if (localVarDef.initValue != null)
            localVarDef.initValue.accept(this);
        indent--;

        return null;
    }

    @Override
    public Void visit(ASTNode.GlobalVarDef globalVarDef) {
        printIndent("GlobalVarDef");
        indent++;
        globalVarDef.type.accept(this);
        globalVarDef.id.accept(this);
        if (globalVarDef.initValue != null)
            globalVarDef.initValue.accept(this);
        indent--;
        return null;
    }

    @Override
    public Void visit(ASTNode.FuncDef funcDef) {
        printIndent("FuncDef");
        indent++;
        funcDef.type.accept(this);
        funcDef.id.accept(this);
        funcDef.formalDefs.forEach(f -> f.accept(this));
        funcDef.body.accept(this);
        indent--;

        return null;
    }

    @Override
    public Void visit(ASTNode.Block block) {
        printIndent("Block");
        indent++;
        block.stmts.forEach(s -> s.accept(this));
        indent--;
        return null;
    }

    @Override
    public Void visit(ASTNode.Program program) {
        program.stmts.forEach(s -> s.accept(this));

        return null;
    }
}
