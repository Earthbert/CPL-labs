// Generated from D:/UPB/CPL/Idea/AST-skel\CPLangParser.g4 by ANTLR 4.10.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CPLangParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.10.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		IF=1, THEN=2, ELSE=3, FI=4, BOOL=5, TYPE=6, ID=7, INT=8, FLOAT=9, STRING=10, 
		SEMI=11, COMMA=12, ASSIGN=13, LPAREN=14, RPAREN=15, LBRACE=16, RBRACE=17, 
		PLUS=18, MINUS=19, MULT=20, DIV=21, EQUAL=22, LT=23, LE=24, LINE_COMMENT=25, 
		BLOCK_COMMENT=26, WS=27;
	public static final int
		RULE_prog = 0, RULE_definition = 1, RULE_formal = 2, RULE_expr = 3;
	private static String[] makeRuleNames() {
		return new String[] {
			"prog", "definition", "formal", "expr"
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
			setState(16);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << IF) | (1L << BOOL) | (1L << TYPE) | (1L << ID) | (1L << INT) | (1L << FLOAT) | (1L << LPAREN) | (1L << MINUS))) != 0)) {
				{
				{
				setState(10);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case TYPE:
					{
					setState(8);
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
					setState(9);
					expr(0);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(12);
				match(SEMI);
				}
				}
				setState(18);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(19);
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
	public static class VarDefContext extends DefinitionContext {
		public Token type;
		public Token name;
		public ExprContext init;
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
	public static class FuncDefContext extends DefinitionContext {
		public Token type;
		public Token name;
		public FormalContext formal;
		public List<FormalContext> formals = new ArrayList<FormalContext>();
		public ExprContext body;
		public TerminalNode LPAREN() { return getToken(CPLangParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(CPLangParser.RPAREN, 0); }
		public TerminalNode LBRACE() { return getToken(CPLangParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(CPLangParser.RBRACE, 0); }
		public TerminalNode TYPE() { return getToken(CPLangParser.TYPE, 0); }
		public TerminalNode ID() { return getToken(CPLangParser.ID, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public List<FormalContext> formal() {
			return getRuleContexts(FormalContext.class);
		}
		public FormalContext formal(int i) {
			return getRuleContext(FormalContext.class,i);
		}
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
				setState(21);
				((VarDefContext)_localctx).type = match(TYPE);
				setState(22);
				((VarDefContext)_localctx).name = match(ID);
				setState(25);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ASSIGN) {
					{
					setState(23);
					match(ASSIGN);
					setState(24);
					((VarDefContext)_localctx).init = expr(0);
					}
				}

				}
				break;
			case 2:
				_localctx = new FuncDefContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(27);
				((FuncDefContext)_localctx).type = match(TYPE);
				setState(28);
				((FuncDefContext)_localctx).name = match(ID);
				setState(29);
				match(LPAREN);
				setState(38);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==TYPE) {
					{
					setState(30);
					((FuncDefContext)_localctx).formal = formal();
					((FuncDefContext)_localctx).formals.add(((FuncDefContext)_localctx).formal);
					setState(35);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(31);
						match(COMMA);
						setState(32);
						((FuncDefContext)_localctx).formal = formal();
						((FuncDefContext)_localctx).formals.add(((FuncDefContext)_localctx).formal);
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
				((FuncDefContext)_localctx).body = expr(0);
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

	public static class FormalContext extends ParserRuleContext {
		public Token type;
		public Token name;
		public TerminalNode TYPE() { return getToken(CPLangParser.TYPE, 0); }
		public TerminalNode ID() { return getToken(CPLangParser.ID, 0); }
		public FormalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).enterFormal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).exitFormal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPLangParserVisitor ) return ((CPLangParserVisitor<? extends T>)visitor).visitFormal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FormalContext formal() throws RecognitionException {
		FormalContext _localctx = new FormalContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_formal);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(47);
			((FormalContext)_localctx).type = match(TYPE);
			setState(48);
			((FormalContext)_localctx).name = match(ID);
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
	public static class CallContext extends ExprContext {
		public Token name;
		public ExprContext expr;
		public List<ExprContext> args = new ArrayList<ExprContext>();
		public TerminalNode LPAREN() { return getToken(CPLangParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(CPLangParser.RPAREN, 0); }
		public TerminalNode ID() { return getToken(CPLangParser.ID, 0); }
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
	public static class ParenContext extends ExprContext {
		public ExprContext e;
		public TerminalNode LPAREN() { return getToken(CPLangParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(CPLangParser.RPAREN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ParenContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).enterParen(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).exitParen(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPLangParserVisitor ) return ((CPLangParserVisitor<? extends T>)visitor).visitParen(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PlusMinusContext extends ExprContext {
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
		public PlusMinusContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).enterPlusMinus(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).exitPlusMinus(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPLangParserVisitor ) return ((CPLangParserVisitor<? extends T>)visitor).visitPlusMinus(this);
			else return visitor.visitChildren(this);
		}
	}
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
	public static class MultDivContext extends ExprContext {
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
		public MultDivContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).enterMultDiv(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).exitMultDiv(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPLangParserVisitor ) return ((CPLangParserVisitor<? extends T>)visitor).visitMultDiv(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UnaryMinusContext extends ExprContext {
		public ExprContext e;
		public TerminalNode MINUS() { return getToken(CPLangParser.MINUS, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public UnaryMinusContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).enterUnaryMinus(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).exitUnaryMinus(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPLangParserVisitor ) return ((CPLangParserVisitor<? extends T>)visitor).visitUnaryMinus(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RelationalContext extends ExprContext {
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
		public RelationalContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).enterRelational(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CPLangParserListener ) ((CPLangParserListener)listener).exitRelational(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CPLangParserVisitor ) return ((CPLangParserVisitor<? extends T>)visitor).visitRelational(this);
			else return visitor.visitChildren(this);
		}
	}
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
	public static class AssignContext extends ExprContext {
		public Token name;
		public ExprContext e;
		public TerminalNode ASSIGN() { return getToken(CPLangParser.ASSIGN, 0); }
		public TerminalNode ID() { return getToken(CPLangParser.ID, 0); }
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
		int _startState = 6;
		enterRecursionRule(_localctx, 6, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(85);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				{
				_localctx = new CallContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(51);
				((CallContext)_localctx).name = match(ID);
				setState(52);
				match(LPAREN);
				setState(61);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << IF) | (1L << BOOL) | (1L << ID) | (1L << INT) | (1L << FLOAT) | (1L << LPAREN) | (1L << MINUS))) != 0)) {
					{
					setState(53);
					((CallContext)_localctx).expr = expr(0);
					((CallContext)_localctx).args.add(((CallContext)_localctx).expr);
					setState(58);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(54);
						match(COMMA);
						setState(55);
						((CallContext)_localctx).expr = expr(0);
						((CallContext)_localctx).args.add(((CallContext)_localctx).expr);
						}
						}
						setState(60);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(63);
				match(RPAREN);
				}
				break;
			case 2:
				{
				_localctx = new UnaryMinusContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(64);
				match(MINUS);
				setState(65);
				((UnaryMinusContext)_localctx).e = expr(11);
				}
				break;
			case 3:
				{
				_localctx = new IfContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(66);
				match(IF);
				setState(67);
				((IfContext)_localctx).cond = expr(0);
				setState(68);
				match(THEN);
				setState(69);
				((IfContext)_localctx).thenBranch = expr(0);
				setState(70);
				match(ELSE);
				setState(71);
				((IfContext)_localctx).elseBranch = expr(0);
				setState(72);
				match(FI);
				}
				break;
			case 4:
				{
				_localctx = new AssignContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(74);
				((AssignContext)_localctx).name = match(ID);
				setState(75);
				match(ASSIGN);
				setState(76);
				((AssignContext)_localctx).e = expr(6);
				}
				break;
			case 5:
				{
				_localctx = new ParenContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(77);
				match(LPAREN);
				setState(78);
				((ParenContext)_localctx).e = expr(0);
				setState(79);
				match(RPAREN);
				}
				break;
			case 6:
				{
				_localctx = new IdContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(81);
				match(ID);
				}
				break;
			case 7:
				{
				_localctx = new IntContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(82);
				match(INT);
				}
				break;
			case 8:
				{
				_localctx = new FloatContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(83);
				match(FLOAT);
				}
				break;
			case 9:
				{
				_localctx = new BoolContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(84);
				match(BOOL);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(98);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(96);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
					case 1:
						{
						_localctx = new MultDivContext(new ExprContext(_parentctx, _parentState));
						((MultDivContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(87);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(88);
						((MultDivContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==MULT || _la==DIV) ) {
							((MultDivContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(89);
						((MultDivContext)_localctx).right = expr(11);
						}
						break;
					case 2:
						{
						_localctx = new PlusMinusContext(new ExprContext(_parentctx, _parentState));
						((PlusMinusContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(90);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(91);
						((PlusMinusContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==PLUS || _la==MINUS) ) {
							((PlusMinusContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(92);
						((PlusMinusContext)_localctx).right = expr(10);
						}
						break;
					case 3:
						{
						_localctx = new RelationalContext(new ExprContext(_parentctx, _parentState));
						((RelationalContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(93);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(94);
						((RelationalContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EQUAL) | (1L << LT) | (1L << LE))) != 0)) ) {
							((RelationalContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(95);
						((RelationalContext)_localctx).right = expr(9);
						}
						break;
					}
					} 
				}
				setState(100);
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
		case 3:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 10);
		case 1:
			return precpred(_ctx, 9);
		case 2:
			return precpred(_ctx, 8);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u001bf\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0001\u0000\u0001\u0000\u0003"+
		"\u0000\u000b\b\u0000\u0001\u0000\u0001\u0000\u0005\u0000\u000f\b\u0000"+
		"\n\u0000\f\u0000\u0012\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0003\u0001\u001a\b\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0005\u0001\"\b"+
		"\u0001\n\u0001\f\u0001%\t\u0001\u0003\u0001\'\b\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u0001.\b\u0001\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0005\u00039\b\u0003\n\u0003\f\u0003<\t"+
		"\u0003\u0003\u0003>\b\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0003\u0003V\b\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0005"+
		"\u0003a\b\u0003\n\u0003\f\u0003d\t\u0003\u0001\u0003\u0000\u0001\u0006"+
		"\u0004\u0000\u0002\u0004\u0006\u0000\u0003\u0001\u0000\u0014\u0015\u0001"+
		"\u0000\u0012\u0013\u0001\u0000\u0016\u0018t\u0000\u0010\u0001\u0000\u0000"+
		"\u0000\u0002-\u0001\u0000\u0000\u0000\u0004/\u0001\u0000\u0000\u0000\u0006"+
		"U\u0001\u0000\u0000\u0000\b\u000b\u0003\u0002\u0001\u0000\t\u000b\u0003"+
		"\u0006\u0003\u0000\n\b\u0001\u0000\u0000\u0000\n\t\u0001\u0000\u0000\u0000"+
		"\u000b\f\u0001\u0000\u0000\u0000\f\r\u0005\u000b\u0000\u0000\r\u000f\u0001"+
		"\u0000\u0000\u0000\u000e\n\u0001\u0000\u0000\u0000\u000f\u0012\u0001\u0000"+
		"\u0000\u0000\u0010\u000e\u0001\u0000\u0000\u0000\u0010\u0011\u0001\u0000"+
		"\u0000\u0000\u0011\u0013\u0001\u0000\u0000\u0000\u0012\u0010\u0001\u0000"+
		"\u0000\u0000\u0013\u0014\u0005\u0000\u0000\u0001\u0014\u0001\u0001\u0000"+
		"\u0000\u0000\u0015\u0016\u0005\u0006\u0000\u0000\u0016\u0019\u0005\u0007"+
		"\u0000\u0000\u0017\u0018\u0005\r\u0000\u0000\u0018\u001a\u0003\u0006\u0003"+
		"\u0000\u0019\u0017\u0001\u0000\u0000\u0000\u0019\u001a\u0001\u0000\u0000"+
		"\u0000\u001a.\u0001\u0000\u0000\u0000\u001b\u001c\u0005\u0006\u0000\u0000"+
		"\u001c\u001d\u0005\u0007\u0000\u0000\u001d&\u0005\u000e\u0000\u0000\u001e"+
		"#\u0003\u0004\u0002\u0000\u001f \u0005\f\u0000\u0000 \"\u0003\u0004\u0002"+
		"\u0000!\u001f\u0001\u0000\u0000\u0000\"%\u0001\u0000\u0000\u0000#!\u0001"+
		"\u0000\u0000\u0000#$\u0001\u0000\u0000\u0000$\'\u0001\u0000\u0000\u0000"+
		"%#\u0001\u0000\u0000\u0000&\u001e\u0001\u0000\u0000\u0000&\'\u0001\u0000"+
		"\u0000\u0000\'(\u0001\u0000\u0000\u0000()\u0005\u000f\u0000\u0000)*\u0005"+
		"\u0010\u0000\u0000*+\u0003\u0006\u0003\u0000+,\u0005\u0011\u0000\u0000"+
		",.\u0001\u0000\u0000\u0000-\u0015\u0001\u0000\u0000\u0000-\u001b\u0001"+
		"\u0000\u0000\u0000.\u0003\u0001\u0000\u0000\u0000/0\u0005\u0006\u0000"+
		"\u000001\u0005\u0007\u0000\u00001\u0005\u0001\u0000\u0000\u000023\u0006"+
		"\u0003\uffff\uffff\u000034\u0005\u0007\u0000\u00004=\u0005\u000e\u0000"+
		"\u00005:\u0003\u0006\u0003\u000067\u0005\f\u0000\u000079\u0003\u0006\u0003"+
		"\u000086\u0001\u0000\u0000\u00009<\u0001\u0000\u0000\u0000:8\u0001\u0000"+
		"\u0000\u0000:;\u0001\u0000\u0000\u0000;>\u0001\u0000\u0000\u0000<:\u0001"+
		"\u0000\u0000\u0000=5\u0001\u0000\u0000\u0000=>\u0001\u0000\u0000\u0000"+
		">?\u0001\u0000\u0000\u0000?V\u0005\u000f\u0000\u0000@A\u0005\u0013\u0000"+
		"\u0000AV\u0003\u0006\u0003\u000bBC\u0005\u0001\u0000\u0000CD\u0003\u0006"+
		"\u0003\u0000DE\u0005\u0002\u0000\u0000EF\u0003\u0006\u0003\u0000FG\u0005"+
		"\u0003\u0000\u0000GH\u0003\u0006\u0003\u0000HI\u0005\u0004\u0000\u0000"+
		"IV\u0001\u0000\u0000\u0000JK\u0005\u0007\u0000\u0000KL\u0005\r\u0000\u0000"+
		"LV\u0003\u0006\u0003\u0006MN\u0005\u000e\u0000\u0000NO\u0003\u0006\u0003"+
		"\u0000OP\u0005\u000f\u0000\u0000PV\u0001\u0000\u0000\u0000QV\u0005\u0007"+
		"\u0000\u0000RV\u0005\b\u0000\u0000SV\u0005\t\u0000\u0000TV\u0005\u0005"+
		"\u0000\u0000U2\u0001\u0000\u0000\u0000U@\u0001\u0000\u0000\u0000UB\u0001"+
		"\u0000\u0000\u0000UJ\u0001\u0000\u0000\u0000UM\u0001\u0000\u0000\u0000"+
		"UQ\u0001\u0000\u0000\u0000UR\u0001\u0000\u0000\u0000US\u0001\u0000\u0000"+
		"\u0000UT\u0001\u0000\u0000\u0000Vb\u0001\u0000\u0000\u0000WX\n\n\u0000"+
		"\u0000XY\u0007\u0000\u0000\u0000Ya\u0003\u0006\u0003\u000bZ[\n\t\u0000"+
		"\u0000[\\\u0007\u0001\u0000\u0000\\a\u0003\u0006\u0003\n]^\n\b\u0000\u0000"+
		"^_\u0007\u0002\u0000\u0000_a\u0003\u0006\u0003\t`W\u0001\u0000\u0000\u0000"+
		"`Z\u0001\u0000\u0000\u0000`]\u0001\u0000\u0000\u0000ad\u0001\u0000\u0000"+
		"\u0000b`\u0001\u0000\u0000\u0000bc\u0001\u0000\u0000\u0000c\u0007\u0001"+
		"\u0000\u0000\u0000db\u0001\u0000\u0000\u0000\u000b\n\u0010\u0019#&-:="+
		"U`b";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}