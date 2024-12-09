package asmgen;

import lexer.CPLangLexer;
import parser.ASTNode.*;
import parser.ASTVisitor;

public class NTAnalizator implements ASTVisitor<Integer> {

    @Override
    public Integer visit(final Id id) {
        return 0;
    }

    @Override
    public Integer visit(final IntLiteral intLiteral) {
        return 0;
    }

    @Override
    public Integer visit(final If anIf) {
        Integer result = anIf.cond.accept(this);
        result = Math.max(result, anIf.thenBranch.accept(this));
        if (anIf.elseBranch != null) {
            result = Math.max(result, anIf.elseBranch.accept(this));
        }
        return result;
    }

    @Override
    public Integer visit(final For aFor) {
        Integer result = aFor.init.accept(this);
        result = Math.max(result, aFor.cond.accept(this));
        result = Math.max(result, aFor.step.accept(this));
        result = Math.max(result, aFor.body.accept(this));
        return result;
    }

    @Override
    public Integer visit(final FloatLiteral floatLiteral) {
        return 0;
    }

    @Override
    public Integer visit(final BoolLiteral boolLiteral) {
        return 0;
    }

    @Override
    public Integer visit(final Assign assign) {
        return assign.expr.accept(this);
    }

    @Override
    public Integer visit(final Relational relational) {
        return Math.max(relational.left.accept(this), relational.right.accept(this) + 1);
    }

    @Override
    public Integer visit(final Arithmetic arithmetic) {
        final Integer left = arithmetic.left.accept(this);
        final Integer right = arithmetic.right.accept(this);
        if ((arithmetic.getToken().getType() == CPLangLexer.PLUS
                || arithmetic.getToken().getType() == CPLangLexer.MULT) && left < right) {
            final Expression temp = arithmetic.left;
            arithmetic.left = arithmetic.right;
            arithmetic.right = temp;
        }
        return Math.max(arithmetic.left.accept(this), arithmetic.right.accept(this) + 1);
    }

    @Override
    public Integer visit(final UnaryMinus unaryMinus) {
        return unaryMinus.expr.accept(this);
    }

    @Override
    public Integer visit(final Call call) {
        return call.args.stream().mapToInt(node -> node.accept(this)).max().orElse(0);
    }

    @Override
    public Integer visit(final Type type) {
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Integer visit(final FormalDef formalDef) {
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Integer visit(final LocalVarDef localVarDef) {
        return localVarDef.initValue != null ? localVarDef.initValue.accept(this) : 0;
    }

    @Override
    public Integer visit(final GlobalVarDef globalVarDef) {
        return globalVarDef.initValue != null ? globalVarDef.initValue.accept(this) : 0;
    }

    @Override
    public Integer visit(final FuncDef funcDef) {
        funcDef.tempLocations = funcDef.body.accept(this);
        return 0;
    }

    @Override
    public Integer visit(final Block block) {
        return block.stmts.stream().mapToInt(node -> node.accept(this)).max().orElse(0);
    }

    @Override
    public Integer visit(final Program program) {
        for (final var node : program.stmts) {
            program.mainTempLocations = Math.max(node.accept(this), program.mainTempLocations);
        }

        return null;
    }

}
