// Generated from c:/Users/earthbert/University/CPL/Labs/03-parser/CPLangParser.g4 by ANTLR 4.13.1
package antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CPLangParser}.
 */
public interface CPLangParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CPLangParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(CPLangParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPLangParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(CPLangParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by the {@code varDef}
	 * labeled alternative in {@link CPLangParser#definition}.
	 * @param ctx the parse tree
	 */
	void enterVarDef(CPLangParser.VarDefContext ctx);
	/**
	 * Exit a parse tree produced by the {@code varDef}
	 * labeled alternative in {@link CPLangParser#definition}.
	 * @param ctx the parse tree
	 */
	void exitVarDef(CPLangParser.VarDefContext ctx);
	/**
	 * Enter a parse tree produced by the {@code funcDef}
	 * labeled alternative in {@link CPLangParser#definition}.
	 * @param ctx the parse tree
	 */
	void enterFuncDef(CPLangParser.FuncDefContext ctx);
	/**
	 * Exit a parse tree produced by the {@code funcDef}
	 * labeled alternative in {@link CPLangParser#definition}.
	 * @param ctx the parse tree
	 */
	void exitFuncDef(CPLangParser.FuncDefContext ctx);
	/**
	 * Enter a parse tree produced by the {@code call}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterCall(CPLangParser.CallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code call}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitCall(CPLangParser.CallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parens}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParens(CPLangParser.ParensContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parens}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParens(CPLangParser.ParensContext ctx);
	/**
	 * Enter a parse tree produced by the {@code bool}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBool(CPLangParser.BoolContext ctx);
	/**
	 * Exit a parse tree produced by the {@code bool}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBool(CPLangParser.BoolContext ctx);
	/**
	 * Enter a parse tree produced by the {@code addSub}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAddSub(CPLangParser.AddSubContext ctx);
	/**
	 * Exit a parse tree produced by the {@code addSub}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAddSub(CPLangParser.AddSubContext ctx);
	/**
	 * Enter a parse tree produced by the {@code id}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterId(CPLangParser.IdContext ctx);
	/**
	 * Exit a parse tree produced by the {@code id}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitId(CPLangParser.IdContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unary}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterUnary(CPLangParser.UnaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unary}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitUnary(CPLangParser.UnaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code float}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterFloat(CPLangParser.FloatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code float}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitFloat(CPLangParser.FloatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code relOp}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterRelOp(CPLangParser.RelOpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code relOp}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitRelOp(CPLangParser.RelOpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code if}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIf(CPLangParser.IfContext ctx);
	/**
	 * Exit a parse tree produced by the {@code if}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIf(CPLangParser.IfContext ctx);
	/**
	 * Enter a parse tree produced by the {@code int}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterInt(CPLangParser.IntContext ctx);
	/**
	 * Exit a parse tree produced by the {@code int}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitInt(CPLangParser.IntContext ctx);
	/**
	 * Enter a parse tree produced by the {@code mulDiv}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMulDiv(CPLangParser.MulDivContext ctx);
	/**
	 * Exit a parse tree produced by the {@code mulDiv}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMulDiv(CPLangParser.MulDivContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assign}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAssign(CPLangParser.AssignContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assign}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAssign(CPLangParser.AssignContext ctx);
}