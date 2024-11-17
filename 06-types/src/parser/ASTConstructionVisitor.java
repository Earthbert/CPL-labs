package parser;
import java.util.*;

public class ASTConstructionVisitor extends CPLangParserBaseVisitor<ASTNode> {
    @Override
    public ASTNode visitId(CPLangParser.IdContext ctx) {
        return new ASTNode.Id(ctx.ID().getSymbol());
    }

    @Override
    public ASTNode visitInt(CPLangParser.IntContext ctx) {
        return new ASTNode.IntLiteral(ctx.INT().getSymbol());
    }

    @Override
    public ASTNode visitIf(CPLangParser.IfContext ctx) {
        return new ASTNode.If((ASTNode.Expression)visit(ctx.cond),
                (ASTNode.Expression)visit(ctx.thenBranch),
                (ASTNode.Expression)visit(ctx.elseBranch),
                ctx.start);
    }

    @Override
    public ASTNode visitFor(CPLangParser.ForContext ctx) {
        return new ASTNode.For(
                (ASTNode.Expression)visit(ctx.init),
                (ASTNode.Expression)visit(ctx.cond),
                (ASTNode.Expression)visit(ctx.step),
                (ASTNode.Expression)visit(ctx.body),
                ctx.start);
    }

    @Override
    public ASTNode visitFloat(CPLangParser.FloatContext ctx) {
        return new ASTNode.FloatLiteral(ctx.FLOAT().getSymbol());
    }

    @Override
    public ASTNode visitBool(CPLangParser.BoolContext ctx) {
        return new ASTNode.BoolLiteral(ctx.BOOL().getSymbol());
    }

    @Override
    public ASTNode visitAssign(CPLangParser.AssignContext ctx) {
        return new ASTNode.Assign(new ASTNode.Id(ctx.name),
                (ASTNode.Expression)visit(ctx.e),
                ctx.ASSIGN().getSymbol());
    }

    @Override
    public ASTNode visitRelational(CPLangParser.RelationalContext ctx) {
        return new ASTNode.Relational((ASTNode.Expression)visit(ctx.left),
                (ASTNode.Expression)visit(ctx.right),
                ctx.op);
    }

    @Override
    public ASTNode visitArithmetic(CPLangParser.ArithmeticContext ctx) {
        return new ASTNode.Arithmetic((ASTNode.Expression)visit(ctx.left),
                (ASTNode.Expression)visit(ctx.right),
                ctx.op);
    }

    @Override
    public ASTNode visitUnaryMinus(CPLangParser.UnaryMinusContext ctx) {
        return new ASTNode.UnaryMinus((ASTNode.Expression)visit(ctx.e),
                ctx.MINUS().getSymbol());
    }

    @Override
    public ASTNode visitParen(CPLangParser.ParenContext ctx) {
        return visit(ctx.e);
    }

    @Override
    public ASTNode visitCall(CPLangParser.CallContext ctx) {
        LinkedList<ASTNode.Expression> args = new LinkedList<>();
        for (var child : ctx.args) {
            args.add((ASTNode.Expression)visit(child));
        }

        return new ASTNode.Call(new ASTNode.Id(ctx.name),
                args,
                ctx.start);
    }

    @Override
    public ASTNode visitFormal(CPLangParser.FormalContext ctx) {
        return new ASTNode.FormalDef(new ASTNode.Type(ctx.type), new ASTNode.Id(ctx.name), ctx.start);
    }

    @Override
    public ASTNode visitVarDef(CPLangParser.VarDefContext ctx) {
        if (ctx.init != null)
            return new ASTNode.GlobalVarDef(
                    new ASTNode.Type(ctx.type),
                    new ASTNode.Id(ctx.name),
                    (ASTNode.Expression)visit(ctx.init),
                    ctx.start
            );
        else
            return new ASTNode.GlobalVarDef(
                    new ASTNode.Type(ctx.type),
                    new ASTNode.Id(ctx.name),
                    null,
                    ctx.start
            );
    }

    @Override
    public ASTNode visitFuncDef(CPLangParser.FuncDefContext ctx) {
        LinkedList<ASTNode.FormalDef> formalDefs = new LinkedList<>();
        for (var formal : ctx.formals)
            formalDefs.add((ASTNode.FormalDef)visit(formal));

        return new ASTNode.FuncDef(
                new ASTNode.Type(ctx.type),
                new ASTNode.Id(ctx.name),
                formalDefs,
                (ASTNode.Block)visit(ctx.body),
                ctx.start
        );
    }

    @Override
    public ASTNode visitLocal(CPLangParser.LocalContext ctx) {
        ASTNode.Expression initExpr = null;

        if (ctx.init != null)
            initExpr = (ASTNode.Expression)visit(ctx.init);

        return new ASTNode.LocalVarDef(
                new ASTNode.Type(ctx.type),
                new ASTNode.Id(ctx.name),
                initExpr,
                ctx.start
        );
    }

    @Override
    public ASTNode visitBlock(CPLangParser.BlockContext ctx) {
        LinkedList<ASTNode> stmts = new LinkedList<>();
        for (var child : ctx.children) {
            ASTNode stmt = visit(child);
            if (stmt != null)
                stmt.debugStr = child.getText();

            if (stmt != null) {
                stmts.add(stmt);
            }
        }

        return new ASTNode.Block(stmts, ctx.start);
    }

    @Override
    public ASTNode visitProg(CPLangParser.ProgContext ctx) {
        LinkedList<ASTNode> stmts = new LinkedList<>();
        for (var child : ctx.children) {
            ASTNode stmt = visit(child);
            if (stmt != null)
                stmt.debugStr = child.getText();

            if (stmt != null) {
                stmts.add(stmt);
            }
        }

        return new ASTNode.Program(stmts, ctx.start);
    }
}
