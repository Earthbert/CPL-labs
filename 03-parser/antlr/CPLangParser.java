// Generated from c:/Users/earthbert/University/CPL/Labs/03-parser/CPLangParser.g4 by ANTLR 4.13.1
package antlr;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class CPLangParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		IF=1, THEN=2, ELSE=3, FI=4, BOOL=5, TYPE=6, ID=7, INT=8, FLOAT=9, STRING=10, 
		SEMI=11, COMMA=12, ASSIGN=13, LPAREN=14, RPAREN=15, LBRACE=16, RBRACE=17, 
		PLUS=18, MINUS=19, MULT=20, DIV=21, EQUAL=22, LT=23, LE=24, LINE_COMMENT=25, 
		BLOCK_COMMENT=26, WS=27;
	public static final int
		RULE_prog = 0, RULE_definition = 1, RULE_expr = 2;
	private static String[] makeRuleNames() {
		return new String[] {
			"prog", "definition", "expr"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'if'", "'then'", "'else'", "'fi'", null, null, null, null, null, 
			null, "';'", "','", "'='", "'('", "')'", "'{'", "'}'", "'+'", "'-'", 
			"'*'", "'/'", "'=='", "'<'", "'<='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "IF", "THEN", "ELSE", "FI", "BOOL", "TYPE", "ID", "INT", "FLOAT", 
			"STRING", "SEMI", "COMMA", "ASSIGN", "LPAREN", "RPAREN", "LBRACE", "RBRACE", 
			"PLUS", "MINUS", "MULT", "DIV", "EQUAL", "LT", "LE", "LINE_COMMENT", 
			"BLOCK_COMMENT", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "CPLangParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public CPLangParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(CPLangParser.EOF, 0); }
		public List<TerminalNode> SEMI() { return getTokens(CPLangParser.SEMI); }
		public TerminalNode SEMI(int i) {
			return getToken(CPLangParser.SEMI, i);
		}
		public List<DefinitionContext> definition() {
			return getRuleContexts(DefinitionContext.class);
		}
		public DefinitionContext definition(int i) {
			return getRuleContext(DefinitionContext.class,i);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).enterProg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).exitProg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPLangParserVisitor ) return ((CPLangParserVisitor<? extends T>)visitor).visitProg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(14);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 541666L) != 0)) {
				{
				{
				setState(8);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case TYPE:
					{
					setState(6);
					definition();
					}
					break;
				case IF:
				case BOOL:
				case ID:
				case INT:
				case FLOAT:
				case LPAREN:
				case MINUS:
					{
					setState(7);
					expr(0);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(10);
				match(SEMI);
				}
				}
				setState(16);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(17);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DefinitionContext extends ParserRuleContext {
		public DefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_definition; }
	 
		public DefinitionContext() { }
		public void copyFrom(DefinitionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class VarDefContext extends DefinitionContext {
		public TerminalNode TYPE() { return getToken(CPLangParser.TYPE, 0); }
		public TerminalNode ID() { return getToken(CPLangParser.ID, 0); }
		public TerminalNode ASSIGN() { return getToken(CPLangParser.ASSIGN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public VarDefContext(DefinitionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).enterVarDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).exitVarDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPLangParserVisitor ) return ((CPLangParserVisitor<? extends T>)visitor).visitVarDef(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FuncDefContext extends DefinitionContext {
		public List<TerminalNode> TYPE() { return getTokens(CPLangParser.TYPE); }
		public TerminalNode TYPE(int i) {
			return getToken(CPLangParser.TYPE, i);
		}
		public List<TerminalNode> ID() { return getTokens(CPLangParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(CPLangParser.ID, i);
		}
		public TerminalNode LPAREN() { return getToken(CPLangParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(CPLangParser.RPAREN, 0); }
		public TerminalNode LBRACE() { return getToken(CPLangParser.LBRACE, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RBRACE() { return getToken(CPLangParser.RBRACE, 0); }
		public List<TerminalNode> COMMA() { return getTokens(CPLangParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(CPLangParser.COMMA, i);
		}
		public FuncDefContext(DefinitionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).enterFuncDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).exitFuncDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPLangParserVisitor ) return ((CPLangParserVisitor<? extends T>)visitor).visitFuncDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefinitionContext definition() throws RecognitionException {
		DefinitionContext _localctx = new DefinitionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_definition);
		int _la;
		try {
			setState(45);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				_localctx = new VarDefContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(19);
				match(TYPE);
				setState(20);
				match(ID);
				setState(23);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ASSIGN) {
					{
					setState(21);
					match(ASSIGN);
					setState(22);
					expr(0);
					}
				}

				}
				break;
			case 2:
				_localctx = new FuncDefContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(25);
				match(TYPE);
				setState(26);
				match(ID);
				setState(27);
				match(LPAREN);
				setState(38);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==TYPE) {
					{
					setState(28);
					match(TYPE);
					setState(29);
					match(ID);
					setState(35);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(30);
						match(COMMA);
						setState(31);
						match(TYPE);
						setState(32);
						match(ID);
						}
						}
						setState(37);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(40);
				match(RPAREN);
				setState(41);
				match(LBRACE);
				setState(42);
				expr(0);
				setState(43);
				match(RBRACE);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExprContext extends ParserRuleContext {
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	 
		public ExprContext() { }
		public void copyFrom(ExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CallContext extends ExprContext {
		public TerminalNode ID() { return getToken(CPLangParser.ID, 0); }
		public TerminalNode LPAREN() { return getToken(CPLangParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(CPLangParser.RPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(CPLangParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(CPLangParser.COMMA, i);
		}
		public CallContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).enterCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).exitCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPLangParserVisitor ) return ((CPLangParserVisitor<? extends T>)visitor).visitCall(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ParensContext extends ExprContext {
		public ExprContext inner;
		public TerminalNode LPAREN() { return getToken(CPLangParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(CPLangParser.RPAREN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ParensContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).enterParens(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).exitParens(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPLangParserVisitor ) return ((CPLangParserVisitor<? extends T>)visitor).visitParens(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BoolContext extends ExprContext {
		public TerminalNode BOOL() { return getToken(CPLangParser.BOOL, 0); }
		public BoolContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).enterBool(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).exitBool(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPLangParserVisitor ) return ((CPLangParserVisitor<? extends T>)visitor).visitBool(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AddSubContext extends ExprContext {
		public ExprContext left;
		public Token op;
		public ExprContext right;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode PLUS() { return getToken(CPLangParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(CPLangParser.MINUS, 0); }
		public AddSubContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).enterAddSub(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).exitAddSub(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPLangParserVisitor ) return ((CPLangParserVisitor<? extends T>)visitor).visitAddSub(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IdContext extends ExprContext {
		public TerminalNode ID() { return getToken(CPLangParser.ID, 0); }
		public IdContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).enterId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).exitId(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPLangParserVisitor ) return ((CPLangParserVisitor<? extends T>)visitor).visitId(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class UnaryContext extends ExprContext {
		public TerminalNode MINUS() { return getToken(CPLangParser.MINUS, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public UnaryContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).enterUnary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).exitUnary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPLangParserVisitor ) return ((CPLangParserVisitor<? extends T>)visitor).visitUnary(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FloatContext extends ExprContext {
		public TerminalNode FLOAT() { return getToken(CPLangParser.FLOAT, 0); }
		public FloatContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).enterFloat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).exitFloat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPLangParserVisitor ) return ((CPLangParserVisitor<? extends T>)visitor).visitFloat(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RelOpContext extends ExprContext {
		public ExprContext left;
		public Token op;
		public ExprContext right;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode LT() { return getToken(CPLangParser.LT, 0); }
		public TerminalNode LE() { return getToken(CPLangParser.LE, 0); }
		public TerminalNode EQUAL() { return getToken(CPLangParser.EQUAL, 0); }
		public RelOpContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).enterRelOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).exitRelOp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPLangParserVisitor ) return ((CPLangParserVisitor<? extends T>)visitor).visitRelOp(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IfContext extends ExprContext {
		public ExprContext cond;
		public ExprContext thenBranch;
		public ExprContext elseBranch;
		public TerminalNode IF() { return getToken(CPLangParser.IF, 0); }
		public TerminalNode THEN() { return getToken(CPLangParser.THEN, 0); }
		public TerminalNode ELSE() { return getToken(CPLangParser.ELSE, 0); }
		public TerminalNode FI() { return getToken(CPLangParser.FI, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public IfContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).enterIf(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).exitIf(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPLangParserVisitor ) return ((CPLangParserVisitor<? extends T>)visitor).visitIf(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IntContext extends ExprContext {
		public TerminalNode INT() { return getToken(CPLangParser.INT, 0); }
		public IntContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).enterInt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).exitInt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPLangParserVisitor ) return ((CPLangParserVisitor<? extends T>)visitor).visitInt(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MulDivContext extends ExprContext {
		public ExprContext left;
		public Token op;
		public ExprContext right;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode MULT() { return getToken(CPLangParser.MULT, 0); }
		public TerminalNode DIV() { return getToken(CPLangParser.DIV, 0); }
		public MulDivContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).enterMulDiv(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).exitMulDiv(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPLangParserVisitor ) return ((CPLangParserVisitor<? extends T>)visitor).visitMulDiv(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AssignContext extends ExprContext {
		public ExprContext value;
		public TerminalNode ID() { return getToken(CPLangParser.ID, 0); }
		public TerminalNode ASSIGN() { return getToken(CPLangParser.ASSIGN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public AssignContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).enterAssign(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).exitAssign(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPLangParserVisitor ) return ((CPLangParserVisitor<? extends T>)visitor).visitAssign(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 4;
		enterRecursionRule(_localctx, 4, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(82);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				{
				_localctx = new IfContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(48);
				match(IF);
				setState(49);
				((IfContext)_localctx).cond = expr(0);
				setState(50);
				match(THEN);
				setState(51);
				((IfContext)_localctx).thenBranch = expr(0);
				setState(52);
				match(ELSE);
				setState(53);
				((IfContext)_localctx).elseBranch = expr(0);
				setState(54);
				match(FI);
				}
				break;
			case 2:
				{
				_localctx = new IdContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(56);
				match(ID);
				}
				break;
			case 3:
				{
				_localctx = new IntContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(57);
				match(INT);
				}
				break;
			case 4:
				{
				_localctx = new FloatContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(58);
				match(FLOAT);
				}
				break;
			case 5:
				{
				_localctx = new BoolContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(59);
				match(BOOL);
				}
				break;
			case 6:
				{
				_localctx = new UnaryContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(60);
				match(MINUS);
				setState(61);
				expr(7);
				}
				break;
			case 7:
				{
				_localctx = new CallContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(62);
				match(ID);
				setState(63);
				match(LPAREN);
				setState(72);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 541602L) != 0)) {
					{
					setState(64);
					expr(0);
					setState(69);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(65);
						match(COMMA);
						setState(66);
						expr(0);
						}
						}
						setState(71);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(74);
				match(RPAREN);
				}
				break;
			case 8:
				{
				_localctx = new AssignContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(75);
				match(ID);
				setState(76);
				match(ASSIGN);
				setState(77);
				((AssignContext)_localctx).value = expr(2);
				}
				break;
			case 9:
				{
				_localctx = new ParensContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(78);
				match(LPAREN);
				setState(79);
				((ParensContext)_localctx).inner = expr(0);
				setState(80);
				match(RPAREN);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(95);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(93);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
					case 1:
						{
						_localctx = new MulDivContext(new ExprContext(_parentctx, _parentState));
						((MulDivContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(84);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(85);
						((MulDivContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==MULT || _la==DIV) ) {
							((MulDivContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(86);
						((MulDivContext)_localctx).right = expr(6);
						}
						break;
					case 2:
						{
						_localctx = new AddSubContext(new ExprContext(_parentctx, _parentState));
						((AddSubContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(87);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(88);
						((AddSubContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==PLUS || _la==MINUS) ) {
							((AddSubContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(89);
						((AddSubContext)_localctx).right = expr(5);
						}
						break;
					case 3:
						{
						_localctx = new RelOpContext(new ExprContext(_parentctx, _parentState));
						((RelOpContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(90);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(91);
						((RelOpContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 29360128L) != 0)) ) {
							((RelOpContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(92);
						((RelOpContext)_localctx).right = expr(4);
						}
						break;
					}
					} 
				}
				setState(97);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 2:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 5);
		case 1:
			return precpred(_ctx, 4);
		case 2:
			return precpred(_ctx, 3);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u001bc\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0001\u0000\u0001\u0000\u0003\u0000\t\b\u0000\u0001"+
		"\u0000\u0001\u0000\u0005\u0000\r\b\u0000\n\u0000\f\u0000\u0010\t\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0003\u0001\u0018\b\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0005\u0001\"\b\u0001"+
		"\n\u0001\f\u0001%\t\u0001\u0003\u0001\'\b\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u0001.\b\u0001\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0005\u0002D\b\u0002\n\u0002\f\u0002G\t\u0002\u0003\u0002"+
		"I\b\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u0002S\b\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0005\u0002^\b\u0002\n\u0002\f\u0002a\t\u0002"+
		"\u0001\u0002\u0000\u0001\u0004\u0003\u0000\u0002\u0004\u0000\u0003\u0001"+
		"\u0000\u0014\u0015\u0001\u0000\u0012\u0013\u0001\u0000\u0016\u0018r\u0000"+
		"\u000e\u0001\u0000\u0000\u0000\u0002-\u0001\u0000\u0000\u0000\u0004R\u0001"+
		"\u0000\u0000\u0000\u0006\t\u0003\u0002\u0001\u0000\u0007\t\u0003\u0004"+
		"\u0002\u0000\b\u0006\u0001\u0000\u0000\u0000\b\u0007\u0001\u0000\u0000"+
		"\u0000\t\n\u0001\u0000\u0000\u0000\n\u000b\u0005\u000b\u0000\u0000\u000b"+
		"\r\u0001\u0000\u0000\u0000\f\b\u0001\u0000\u0000\u0000\r\u0010\u0001\u0000"+
		"\u0000\u0000\u000e\f\u0001\u0000\u0000\u0000\u000e\u000f\u0001\u0000\u0000"+
		"\u0000\u000f\u0011\u0001\u0000\u0000\u0000\u0010\u000e\u0001\u0000\u0000"+
		"\u0000\u0011\u0012\u0005\u0000\u0000\u0001\u0012\u0001\u0001\u0000\u0000"+
		"\u0000\u0013\u0014\u0005\u0006\u0000\u0000\u0014\u0017\u0005\u0007\u0000"+
		"\u0000\u0015\u0016\u0005\r\u0000\u0000\u0016\u0018\u0003\u0004\u0002\u0000"+
		"\u0017\u0015\u0001\u0000\u0000\u0000\u0017\u0018\u0001\u0000\u0000\u0000"+
		"\u0018.\u0001\u0000\u0000\u0000\u0019\u001a\u0005\u0006\u0000\u0000\u001a"+
		"\u001b\u0005\u0007\u0000\u0000\u001b&\u0005\u000e\u0000\u0000\u001c\u001d"+
		"\u0005\u0006\u0000\u0000\u001d#\u0005\u0007\u0000\u0000\u001e\u001f\u0005"+
		"\f\u0000\u0000\u001f \u0005\u0006\u0000\u0000 \"\u0005\u0007\u0000\u0000"+
		"!\u001e\u0001\u0000\u0000\u0000\"%\u0001\u0000\u0000\u0000#!\u0001\u0000"+
		"\u0000\u0000#$\u0001\u0000\u0000\u0000$\'\u0001\u0000\u0000\u0000%#\u0001"+
		"\u0000\u0000\u0000&\u001c\u0001\u0000\u0000\u0000&\'\u0001\u0000\u0000"+
		"\u0000\'(\u0001\u0000\u0000\u0000()\u0005\u000f\u0000\u0000)*\u0005\u0010"+
		"\u0000\u0000*+\u0003\u0004\u0002\u0000+,\u0005\u0011\u0000\u0000,.\u0001"+
		"\u0000\u0000\u0000-\u0013\u0001\u0000\u0000\u0000-\u0019\u0001\u0000\u0000"+
		"\u0000.\u0003\u0001\u0000\u0000\u0000/0\u0006\u0002\uffff\uffff\u0000"+
		"01\u0005\u0001\u0000\u000012\u0003\u0004\u0002\u000023\u0005\u0002\u0000"+
		"\u000034\u0003\u0004\u0002\u000045\u0005\u0003\u0000\u000056\u0003\u0004"+
		"\u0002\u000067\u0005\u0004\u0000\u00007S\u0001\u0000\u0000\u00008S\u0005"+
		"\u0007\u0000\u00009S\u0005\b\u0000\u0000:S\u0005\t\u0000\u0000;S\u0005"+
		"\u0005\u0000\u0000<=\u0005\u0013\u0000\u0000=S\u0003\u0004\u0002\u0007"+
		">?\u0005\u0007\u0000\u0000?H\u0005\u000e\u0000\u0000@E\u0003\u0004\u0002"+
		"\u0000AB\u0005\f\u0000\u0000BD\u0003\u0004\u0002\u0000CA\u0001\u0000\u0000"+
		"\u0000DG\u0001\u0000\u0000\u0000EC\u0001\u0000\u0000\u0000EF\u0001\u0000"+
		"\u0000\u0000FI\u0001\u0000\u0000\u0000GE\u0001\u0000\u0000\u0000H@\u0001"+
		"\u0000\u0000\u0000HI\u0001\u0000\u0000\u0000IJ\u0001\u0000\u0000\u0000"+
		"JS\u0005\u000f\u0000\u0000KL\u0005\u0007\u0000\u0000LM\u0005\r\u0000\u0000"+
		"MS\u0003\u0004\u0002\u0002NO\u0005\u000e\u0000\u0000OP\u0003\u0004\u0002"+
		"\u0000PQ\u0005\u000f\u0000\u0000QS\u0001\u0000\u0000\u0000R/\u0001\u0000"+
		"\u0000\u0000R8\u0001\u0000\u0000\u0000R9\u0001\u0000\u0000\u0000R:\u0001"+
		"\u0000\u0000\u0000R;\u0001\u0000\u0000\u0000R<\u0001\u0000\u0000\u0000"+
		"R>\u0001\u0000\u0000\u0000RK\u0001\u0000\u0000\u0000RN\u0001\u0000\u0000"+
		"\u0000S_\u0001\u0000\u0000\u0000TU\n\u0005\u0000\u0000UV\u0007\u0000\u0000"+
		"\u0000V^\u0003\u0004\u0002\u0006WX\n\u0004\u0000\u0000XY\u0007\u0001\u0000"+
		"\u0000Y^\u0003\u0004\u0002\u0005Z[\n\u0003\u0000\u0000[\\\u0007\u0002"+
		"\u0000\u0000\\^\u0003\u0004\u0002\u0004]T\u0001\u0000\u0000\u0000]W\u0001"+
		"\u0000\u0000\u0000]Z\u0001\u0000\u0000\u0000^a\u0001\u0000\u0000\u0000"+
		"_]\u0001\u0000\u0000\u0000_`\u0001\u0000\u0000\u0000`\u0005\u0001\u0000"+
		"\u0000\u0000a_\u0001\u0000\u0000\u0000\u000b\b\u000e\u0017#&-EHR]_";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}