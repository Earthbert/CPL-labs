package ast;

public class PrintVisitor implements ASTVisitor<Void> {
    int indent = 0;

    @Override
    public Void visit(Id id) {
        printIndent("ID " + id.token.getText());
        return null;
    }

    @Override
    public Void visit(IntLiteral intLiteral) {
        printIndent("INT " + intLiteral.token.getText());
        return null;
    }

    @Override
    public Void visit(If iff) {
        printIndent("If");
        indent++;
        iff.cond.accept(this);
        iff.thenBranch.accept(this);
        iff.elseBranch.accept(this);
        indent--;
        return null;
    }

    void printIndent(String str) {
        for (int i = 0; i < indent; i++)
            System.out.print("\t");
        System.out.println(str);
    }

    @Override
    public Void visit(Prog prog) {
        prog.elements.forEach(e -> e.accept(this));
        return null;
    }

    @Override
    public Void visit(Assign assign) {
        printIndent("Assign");
        indent++;
        assign.id.accept(this);
        assign.expr.accept(this);
        indent--;
        return null;
    }

    @Override
    public Void visit(VarDef varDef) {
        printIndent("GlobalVarDef");
        indent++;
        varDef.type.accept(this);
        varDef.id.accept(this);
        if (varDef.expr != null)
            varDef.expr.accept(this);
        indent--;
        return null;
    }

    @Override
    public Void visit(Arithmetic arithmetic) {
        printIndent(arithmetic.op);
        indent++;
        arithmetic.left.accept(this);
        arithmetic.right.accept(this);
        indent--;
        return null;
    }

    @Override
    public Void visit(Call call) {
        printIndent("Call");
        indent++;
        call.id.accept(this);
        call.args.forEach(e -> e.accept(this));
        indent--;
        return null;
    }

    @Override
    public Void visit(Local local) {
        printIndent("LocalVarDef");
        indent++;
        local.type.accept(this);
        local.id.accept(this);
        if (local.value != null)
            local.value.accept(this);
        indent--;
        return null;
    }

    @Override
    public Void visit(Block block) {
        printIndent("Block");
        indent++;
        block.elements.forEach(e -> e.accept(this));
        indent--;
        return null;
    }

    @Override
    public Void visit(FuncDef funcDef) {
        printIndent("FuncDef");
        indent++;
        funcDef.type.accept(this);
        funcDef.id.accept(this);
        funcDef.args.forEach(e -> e.accept(this));
        funcDef.body.accept(this);
        indent--;
        return null;
    }

    @Override
    public Void visit(Type type) {
        printIndent("TYPE " + type.name);
        return null;
    }

    @Override
    public Void visit(Formal formal) {
        printIndent("Formal");
        indent++;
        formal.type.accept(this);
        formal.id.accept(this);
        indent--;
        return null;
    }

    @Override
    public Void visit(Float floaty) {
        printIndent("FLOAT " + floaty.value);
        return null;
    }

    @Override
    public Void visit(For for1) {
        printIndent("For");
        indent++;
        for1.init.accept(this);
        for1.cond.accept(this);
        for1.update.accept(this);
        for1.body.accept(this);
        indent--;
        return null;
    }

    @Override
    public Void visit(Comparison comparison) {
        printIndent(comparison.op);
        indent++;
        comparison.left.accept(this);
        comparison.right.accept(this);
        indent--;
        return null;
    }

    @Override
    public Void visit(UnaryMinus unaryMinus) {
        printIndent("-");
        indent++;
        unaryMinus.expr.accept(this);
        indent--;
        return null;
    }
}