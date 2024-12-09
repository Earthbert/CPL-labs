package asmgen;

import java.util.Stack;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import parser.ASTNode;
import parser.ASTNode.BinaryOp;
import parser.ASTVisitor;
import semantic.TypeSymbol;

public class AsmGenVisitor implements ASTVisitor<ST> {
    static STGroupFile templates = new STGroupFile("asmgen/asmgen.stg");
    Integer ifCount = 0;
    Integer forCount = 0;
    Integer printBoolCount = 0;

    Stack<Integer> scopeStack = new Stack<>();
    Integer mainLocals = 0;
    Integer currentFunctionLocals = 0;
    Boolean inFunction = false;

    ST mainSection; // filled directly (through visitor returns)
    ST dataSection; // filled collaterally ("global" access)
    ST funcSection; // filled collaterally ("global" access)

    @Override
    public ST visit(final ASTNode.IntLiteral val) {
        return templates.getInstanceOf("literal")
                .add("value", val.getToken().getText());
    }

    @Override
    public ST visit(final ASTNode.FloatLiteral val) {
        return templates.getInstanceOf("fliteral")
                .add("value", val.getToken().getText());
    }

    @Override
    public ST visit(final ASTNode.BoolLiteral val) {
        return templates.getInstanceOf("literal")
                .add("value", "true".equals(val.getToken().getText()) ? 1 : 0);
    }

    @Override
    public ST visit(final ASTNode.UnaryMinus uMinus) {
        return templates.getInstanceOf("uMinus")
                .add("e", uMinus.expr.accept(this));
    }

    @Override
    public ST visit(final ASTNode.Arithmetic expr) {
        return this.visitBinaryOp(expr);
    }

    @Override
    public ST visit(final ASTNode.Relational expr) {
        return this.visitBinaryOp(expr);
    }

    private ST visitBinaryOp(final BinaryOp expr) {

        if (expr.leftType == TypeSymbol.FLOAT || expr.rightType == TypeSymbol.FLOAT) {
            return this.visitBinaryOpFloat(expr);
        }

        final var op = switch (expr.getToken().getText()) {
            case "+" -> "add";
            case "-" -> "sub";
            case "*" -> "mul";
            case "/" -> "div";
            case "==" -> "seq";
            case "<" -> "slt";
            case "<=" -> "sle";
            default -> "";
        };

        final var st = templates.getInstanceOf("binaryOp");
        st.add("e1", expr.left.accept(this))
                .add("e2", expr.right.accept(this))
                .add("op", op)
                .add("dStr", expr.debugStr);

        return st;
    }

    private ST visitBinaryOpFloat(final BinaryOp expr) {
        final var op = switch (expr.getToken().getText()) {
            case "+" -> "add.s";
            case "-" -> "sub.s";
            case "*" -> "mul.s";
            case "/" -> "div.s";
            case "==" -> "c.eq.s";
            case "<" -> "c.lt.s";
            case "<=" -> "c.le.s";
            default -> "";
        };

        final var st = templates.getInstanceOf("fbinaryOp");
        st.add("e1", expr.left.accept(this))
                .add("e2", expr.right.accept(this))
                .add("op", op)
                .add("dStr", expr.debugStr)
                .add("c1", expr.leftType == TypeSymbol.INT)
                .add("c2", expr.rightType == TypeSymbol.INT);

        return st;
    }

    @Override
    public ST visit(final ASTNode.If iff) {
        return templates.getInstanceOf("iff")
                .add("cond", iff.cond.accept(this))
                .add("thenBranch", iff.thenBranch.accept(this))
                .add("elseBranch", iff.elseBranch.accept(this))
                .add("idx", this.ifCount++);
    }

    @Override
    public ST visit(final ASTNode.Call call) {

        return switch (call.getToken().getText()) {
            case "print_int" -> templates.getInstanceOf("syscall_print_int")
                    .add("expr", call.args.get(0).accept(this));
            case "print_float" -> templates.getInstanceOf("syscall_print_float")
                    .add("expr", call.args.get(0).accept(this));
            case "print_bool" -> templates.getInstanceOf("print_bool")
                    .add("expr", call.args.get(0).accept(this))
                    .add("i", this.printBoolCount++);
            case "read_int" -> templates.getInstanceOf("syscall_read_int");
            case "read_float" -> templates.getInstanceOf("syscall_read_float");
            case "exit" -> templates.getInstanceOf("syscall_exit");
            default -> templates.getInstanceOf("call")
                    .add("name", call.id.getSymbol().getName())
                    .add("params", call.args.stream().map(a -> a.accept(this)).toList().reversed());
        };
    }

    @Override
    public ST visit(final ASTNode.GlobalVarDef varDef) {
        final var name = varDef.id.getSymbol().getName();
        final TypeSymbol type = varDef.id.getSymbol().getType();

        if (type == TypeSymbol.FLOAT) {
            this.dataSection.add("e", "\t" + name + ": .float 0.0");
        } else {
            this.dataSection.add("e", "\t" + name + ": .word 0");
        }

        return varDef.initValue != null ? templates.getInstanceOf("assignGlobal")
                .add("name", name)
                .add("expr", varDef.initValue.accept(this))
                : null;
    }

    @Override
    public ST visit(final ASTNode.LocalVarDef localVarDef) {
        localVarDef.id.getSymbol().offset = this.scopeStack.peek();
        this.scopeStack.push(this.scopeStack.pop() - 4);

        if (localVarDef.initValue != null) {
            return templates.getInstanceOf("assignLocal")
                    .add("offset", localVarDef.id.getSymbol().offset)
                    .add("expr", localVarDef.initValue.accept(this));
        }

        return null;
    }

    @Override
    public ST visit(final ASTNode.Assign assign) {

        if (assign.id.getSymbol().isGlobal) {
            return templates.getInstanceOf("assignGlobal")
                    .add("name", assign.id.getSymbol().getName())
                    .add("expr", assign.expr.accept(this));
        } else {
            return templates.getInstanceOf("assignLocal")
                    .add("offset", assign.id.getSymbol().offset)
                    .add("expr", assign.expr.accept(this));
        }
    }

    @Override
    public ST visit(final ASTNode.Block block) {

        this.scopeStack.push(this.scopeStack.peek());

        final ST bodyST = templates.getInstanceOf("sequence");
        for (final var stmt : block.stmts)
            bodyST.add("e", stmt.accept(this));

        if (this.inFunction)
            this.currentFunctionLocals = Math.min(this.currentFunctionLocals, this.scopeStack.pop() + 4);
        else
            this.mainLocals = Math.min(this.mainLocals, this.scopeStack.pop() + 4);

        return bodyST;
    }

    @Override
    public ST visit(final ASTNode.FuncDef funcDef) {
        this.inFunction = true;

        this.scopeStack.push(this.scopeStack.peek());
        this.currentFunctionLocals = 0;

        for (int i = 0; i < funcDef.formalDefs.size(); i++) {
            final var formal = funcDef.formalDefs.get(i);
            formal.id.getSymbol().offset = 8 + i * 4;
        }

        final ST bodyST = funcDef.body.accept(this);

        this.currentFunctionLocals = Math.min(this.currentFunctionLocals, this.scopeStack.pop() + 4);

        final ST funcST = templates.getInstanceOf("function");
        funcST.add("name", funcDef.id.getSymbol().getName());
        funcST.add("body", bodyST);
        funcST.add("locals_size", this.currentFunctionLocals != 0 ? this.currentFunctionLocals : null);
        funcST.add("params_size", funcDef.formalDefs.size() * 4);

        this.funcSection.add("e", funcST);

        this.inFunction = false;
        return null;
    }

    @Override
    public ST visit(final ASTNode.FormalDef formal) {
        return null;
    }

    @Override
    public ST visit(final ASTNode.Id id) {
        if (id.getSymbol().isGlobal)
            return templates.getInstanceOf("idGlobal")
                    .add("name", id.getSymbol().getName());
        else
            return templates.getInstanceOf("idLocal")
                    .add("offset", id.getSymbol().offset);
    }

    @Override
    public ST visit(final ASTNode.For aFor) {
        return templates.getInstanceOf("for")
                .add("init", aFor.init.accept(this))
                .add("cond", aFor.cond.accept(this))
                .add("inc", aFor.step.accept(this))
                .add("body", aFor.body.accept(this))
                .add("idx", this.forCount++);
    }

    @Override
    public ST visit(final ASTNode.Type type) {
        return null;
    }

    @Override
    public ST visit(final ASTNode.Program program) {
        this.dataSection = templates.getInstanceOf("sequenceSpaced");
        this.funcSection = templates.getInstanceOf("sequenceSpaced");
        this.mainSection = templates.getInstanceOf("sequence");

        this.scopeStack.push(-4);

        for (final ASTNode e : program.stmts)
            this.mainSection.add("e", e.accept(this));

        final ST mainST = templates.getInstanceOf("main");
        mainST.add("locals_size", this.mainLocals != 0 ? this.mainLocals : null);
        mainST.add("body", this.mainSection);

        // assembly-ing it all together. HA! get it?
        final var programST = templates.getInstanceOf("program");
        programST.add("data", this.dataSection);
        programST.add("textFuncs", this.funcSection);
        programST.add("textMain", mainST);

        this.dataSection.add("e", "\ttrue: .asciiz \"true\"");
        this.dataSection.add("e", "\tfalse: .asciiz \"false\"");
        this.dataSection.add("e", "\tnewline: .asciiz \"\\n\"");

        return programST;
    }
}
