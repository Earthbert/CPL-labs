package ast;

import java.util.*;

import antlr.*;
import antlr.CPLangParser.BlockContext;
import antlr.CPLangParser.FloatContext;
import antlr.CPLangParser.ForContext;
import antlr.CPLangParser.FormalContext;
import antlr.CPLangParser.FuncDefContext;
import antlr.CPLangParser.RelationalContext;
import antlr.CPLangParser.UnaryMinusContext;

public class ASTConstructionVisitor extends CPLangParserBaseVisitor<ASTNode> {
    @Override
    public ASTNode visitId(CPLangParser.IdContext ctx) {
        return new Id(ctx.ID().getSymbol());
    }

    @Override
    public ASTNode visitInt(CPLangParser.IntContext ctx) {
        return new IntLiteral(ctx.INT().getSymbol());
    }

    @Override
    public ASTNode visitIf(CPLangParser.IfContext ctx) {
        return new If((Expression) visit(ctx.cond),
                (Expression) visit(ctx.thenBranch),
                (Expression) visit(ctx.elseBranch),
                ctx.start);
    }

    @Override
    public ASTNode visitProg(CPLangParser.ProgContext ctx) {
        List<ASTNode> elements = new ArrayList<>();
        for (var element : ctx.children)
            if ((element instanceof CPLangParser.ExprContext) 
                || (element instanceof CPLangParser.VarDefContext)
                || (element instanceof CPLangParser.FuncDefContext))
            elements.add(visit(element));
        return new Prog(elements);
    }

    @Override
    public ASTNode visitAssign(CPLangParser.AssignContext ctx) {
        return new Assign(new Id(ctx.name), (Expression) visit(ctx.expr()), ctx.start);
    }

    @Override
    public ASTNode visitVarDef(CPLangParser.VarDefContext ctx) {
        return new VarDef(new Type(ctx.TYPE().getSymbol()), new Id(ctx.name), ctx.expr() != null ? (Expression) visit(ctx.expr()) : null, ctx.start);
    }

    @Override
    public ASTNode visitArithmetic(CPLangParser.ArithmeticContext ctx) {
        return new Arithmetic((Expression) visit(ctx.left), (Expression) visit(ctx.right), ctx.op.getText(), ctx.start);
    }

    @Override
    public ASTNode visitCall(CPLangParser.CallContext ctx) {
        List<Expression> args = new ArrayList<>();
        for (var arg : ctx.expr())
            args.add((Expression) visit(arg));
        return new Call(new Id(ctx.name), args, ctx.start);
    }

    @Override
    public ASTNode visitLocal(CPLangParser.LocalContext ctx) {
        return new Local(new Type(ctx.type), new Id(ctx.name), ctx.init != null ? (Expression) visit(ctx.init) : null, ctx.start);
    }

    @Override
    public ASTNode visitFuncDef(FuncDefContext ctx) {
        return new FuncDef(new Type(ctx.type), new Id(ctx.name), ctx.formal().stream().map(f -> (Formal) visit(f)).toList(), (Block) visit(ctx.body), ctx.start);
    }

    @Override
    public ASTNode visitBlock(BlockContext ctx) {
        List<ASTNode> elements = new ArrayList<>();
        for (var element : ctx.children)
            if ((element instanceof CPLangParser.LocalContext) 
                || (element instanceof CPLangParser.ExprContext))
            elements.add(visit(element));

        return new Block(elements, ctx.start);
    }

    @Override
    public ASTNode visitFormal(FormalContext ctx) {
        return new Formal(new Type(ctx.type), new Id(ctx.name), ctx.start);
    }

    @Override
    public ASTNode visitFloat(FloatContext ctx) {
        return new Float(ctx.FLOAT().getSymbol());
    }

    @Override
    public ASTNode visitFor(ForContext ctx) {
        return new For((Expression) visit(ctx.init), (Expression) visit(ctx.cond), (Expression) visit(ctx.step), (Block) visit(ctx.body), ctx.start);
    }

    @Override
    public ASTNode visitRelational(RelationalContext ctx) {
        return new Comparison((Expression) visit(ctx.left), (Expression) visit(ctx.right), ctx.op.getText(), ctx.start);
    }

    @Override
    public ASTNode visitUnaryMinus(UnaryMinusContext ctx) {
        return new UnaryMinus((Expression) visit(ctx.expr()), ctx.start);
    }
}