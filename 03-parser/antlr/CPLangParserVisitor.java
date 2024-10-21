// Generated from c:/Users/earthbert/University/CPL/Labs/03-parser/CPLangParser.g4 by ANTLR 4.13.1
package antlr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CPLangParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CPLangParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link CPLangParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(CPLangParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPLangParser#varDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDef(CPLangParser.VarDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPLangParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(CPLangParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPLangParser#funcDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncDef(CPLangParser.FuncDefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parens}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParens(CPLangParser.ParensContext ctx);
	/**
	 * Visit a parse tree produced by the {@code bool}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBool(CPLangParser.BoolContext ctx);
	/**
	 * Visit a parse tree produced by the {@code for}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFor(CPLangParser.ForContext ctx);
	/**
	 * Visit a parse tree produced by the {@code addSub}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddSub(CPLangParser.AddSubContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unary}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnary(CPLangParser.UnaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code float}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloat(CPLangParser.FloatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code relOp}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelOp(CPLangParser.RelOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code int}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInt(CPLangParser.IntContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mulDiv}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulDiv(CPLangParser.MulDivContext ctx);
	/**
	 * Visit a parse tree produced by the {@code call}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCall(CPLangParser.CallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code id}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId(CPLangParser.IdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code if}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf(CPLangParser.IfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assign}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign(CPLangParser.AssignContext ctx);
}