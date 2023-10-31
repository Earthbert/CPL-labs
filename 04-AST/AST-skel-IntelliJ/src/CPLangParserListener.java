// Generated from D:/UPB/CPL/Idea/AST-skel\CPLangParser.g4 by ANTLR 4.10.1
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
	 * Enter a parse tree produced by {@link CPLangParser#formal}.
	 * @param ctx the parse tree
	 */
	void enterFormal(CPLangParser.FormalContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPLangParser#formal}.
	 * @param ctx the parse tree
	 */
	void exitFormal(CPLangParser.FormalContext ctx);
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
	 * Enter a parse tree produced by the {@code paren}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParen(CPLangParser.ParenContext ctx);
	/**
	 * Exit a parse tree produced by the {@code paren}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParen(CPLangParser.ParenContext ctx);
	/**
	 * Enter a parse tree produced by the {@code plusMinus}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterPlusMinus(CPLangParser.PlusMinusContext ctx);
	/**
	 * Exit a parse tree produced by the {@code plusMinus}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitPlusMinus(CPLangParser.PlusMinusContext ctx);
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
	 * Enter a parse tree produced by the {@code multDiv}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMultDiv(CPLangParser.MultDivContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multDiv}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMultDiv(CPLangParser.MultDivContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaryMinus}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterUnaryMinus(CPLangParser.UnaryMinusContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryMinus}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitUnaryMinus(CPLangParser.UnaryMinusContext ctx);
	/**
	 * Enter a parse tree produced by the {@code relational}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterRelational(CPLangParser.RelationalContext ctx);
	/**
	 * Exit a parse tree produced by the {@code relational}
	 * labeled alternative in {@link CPLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitRelational(CPLangParser.RelationalContext ctx);
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