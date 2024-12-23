// Generated from c:/Users/earthbert/University/CPL/Labs/02-lexer/CPLangLexer.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class CPLangLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		IF=1, THEN=2, ELSE=3, FI=4, FOR=5, DO=6, TRUE=7, FALSE=8, COLON=9, SEMICOLON=10, 
		OPEN_PAR=11, CLOSE_PAR=12, COMMA=13, DOT=14, OPEN_BRACE=15, CLOSE_BRACE=16, 
		OPEN_BRACKET=17, CLOSE_BRACKET=18, INT=19, ID=20, TYPE=21, FLOAT=22, ASSIGN=23, 
		PLUS=24, MINUS=25, TIMES=26, DIV=27, MOD=28, POW=29, EQ=30, NEQ=31, LT=32, 
		LE=33, GT=34, GE=35, STRING=36, BLOCK_COMMENT=37, LINE_COMMENT=38, WS=39;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"IF", "THEN", "ELSE", "FI", "FOR", "DO", "TRUE", "FALSE", "COLON", "SEMICOLON", 
			"OPEN_PAR", "CLOSE_PAR", "COMMA", "DOT", "OPEN_BRACE", "CLOSE_BRACE", 
			"OPEN_BRACKET", "CLOSE_BRACKET", "DIGIT", "INT", "LETTER", "LOWER", "UPPER", 
			"ID", "TYPE", "DIGITS", "MOBILE", "FRACTION", "EXPONENT", "FLOAT", "ASSIGN", 
			"PLUS", "MINUS", "TIMES", "DIV", "MOD", "POW", "EQ", "NEQ", "LT", "LE", 
			"GT", "GE", "STRING", "BLOCK_COMMENT", "LINE_COMMENT", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'if'", "'then'", "'else'", "'fi'", "'for'", "'do'", "'true'", 
			"'false'", "':'", "';'", "'('", "')'", "','", "'.'", "'{'", "'}'", "'['", 
			"']'", null, null, null, null, "'='", "'+'", "'-'", "'*'", "'/'", "'%'", 
			"'^'", "'=='", "'!='", "'<'", "'<='", "'>'", "'>='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "IF", "THEN", "ELSE", "FI", "FOR", "DO", "TRUE", "FALSE", "COLON", 
			"SEMICOLON", "OPEN_PAR", "CLOSE_PAR", "COMMA", "DOT", "OPEN_BRACE", "CLOSE_BRACE", 
			"OPEN_BRACKET", "CLOSE_BRACKET", "INT", "ID", "TYPE", "FLOAT", "ASSIGN", 
			"PLUS", "MINUS", "TIMES", "DIV", "MOD", "POW", "EQ", "NEQ", "LT", "LE", 
			"GT", "GE", "STRING", "BLOCK_COMMENT", "LINE_COMMENT", "WS"
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


	public CPLangLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "CPLangLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 43:
			STRING_action((RuleContext)_localctx, actionIndex);
			break;
		case 44:
			BLOCK_COMMENT_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void STRING_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:
			 System.out.println("there are no strings in CPLang, but shhh.."); 
			break;
		}
	}
	private void BLOCK_COMMENT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 1:
			 System.err.println("End of block comment not allowed"); 
			break;
		case 2:
			 System.err.println("Unclosed block comment");
			break;
		}
	}

	public static final String _serializedATN =
		"\u0004\u0000\'\u012c\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002"+
		"\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002"+
		"\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002"+
		"\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002"+
		"\u001b\u0007\u001b\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002"+
		"\u001e\u0007\u001e\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007"+
		"!\u0002\"\u0007\"\u0002#\u0007#\u0002$\u0007$\u0002%\u0007%\u0002&\u0007"+
		"&\u0002\'\u0007\'\u0002(\u0007(\u0002)\u0007)\u0002*\u0007*\u0002+\u0007"+
		"+\u0002,\u0007,\u0002-\u0007-\u0002.\u0007.\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\b\u0001\b\u0001\t\u0001\t\u0001\n\u0001\n\u0001"+
		"\u000b\u0001\u000b\u0001\f\u0001\f\u0001\r\u0001\r\u0001\u000e\u0001\u000e"+
		"\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011"+
		"\u0001\u0012\u0001\u0012\u0001\u0013\u0004\u0013\u0099\b\u0013\u000b\u0013"+
		"\f\u0013\u009a\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015\u0001\u0016"+
		"\u0001\u0016\u0001\u0017\u0001\u0017\u0003\u0017\u00a5\b\u0017\u0001\u0017"+
		"\u0001\u0017\u0001\u0017\u0005\u0017\u00aa\b\u0017\n\u0017\f\u0017\u00ad"+
		"\t\u0017\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0005\u0018\u00b3"+
		"\b\u0018\n\u0018\f\u0018\u00b6\t\u0018\u0001\u0019\u0004\u0019\u00b9\b"+
		"\u0019\u000b\u0019\f\u0019\u00ba\u0001\u001a\u0001\u001a\u0001\u001a\u0001"+
		"\u001a\u0001\u001a\u0003\u001a\u00c2\b\u001a\u0001\u001b\u0001\u001b\u0003"+
		"\u001b\u00c6\b\u001b\u0003\u001b\u00c8\b\u001b\u0001\u001c\u0001\u001c"+
		"\u0003\u001c\u00cc\b\u001c\u0001\u001c\u0003\u001c\u00cf\b\u001c\u0001"+
		"\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0003\u001d\u00d5\b\u001d\u0001"+
		"\u001d\u0001\u001d\u0001\u001e\u0001\u001e\u0001\u001f\u0001\u001f\u0001"+
		" \u0001 \u0001!\u0001!\u0001\"\u0001\"\u0001#\u0001#\u0001$\u0001$\u0001"+
		"%\u0001%\u0001%\u0001&\u0001&\u0001&\u0001\'\u0001\'\u0001(\u0001(\u0001"+
		"(\u0001)\u0001)\u0001*\u0001*\u0001*\u0001+\u0001+\u0001+\u0001+\u0005"+
		"+\u00fb\b+\n+\f+\u00fe\t+\u0001+\u0001+\u0001+\u0001,\u0001,\u0001,\u0001"+
		",\u0001,\u0001,\u0001,\u0001,\u0001,\u0005,\u010c\b,\n,\f,\u010f\t,\u0001"+
		",\u0001,\u0001,\u0001,\u0003,\u0115\b,\u0003,\u0117\b,\u0001,\u0001,\u0001"+
		"-\u0001-\u0001-\u0001-\u0005-\u011f\b-\n-\f-\u0122\t-\u0001-\u0001-\u0001"+
		".\u0004.\u0127\b.\u000b.\f.\u0128\u0001.\u0001.\u0002\u00fc\u010d\u0000"+
		"/\u0001\u0001\u0003\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006"+
		"\r\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b\u0017\f\u0019\r\u001b\u000e"+
		"\u001d\u000f\u001f\u0010!\u0011#\u0012%\u0000\'\u0013)\u0000+\u0000-\u0000"+
		"/\u00141\u00153\u00005\u00007\u00009\u0000;\u0016=\u0017?\u0018A\u0019"+
		"C\u001aE\u001bG\u001cI\u001dK\u001eM\u001fO Q!S\"U#W$Y%[&]\'\u0001\u0000"+
		"\u0007\u0001\u000009\u0002\u0000AZaz\u0001\u0000az\u0001\u0000AZ\u0002"+
		"\u0000++--\u0002\u0000\n\n\r\r\u0003\u0000\t\n\r\r  \u013a\u0000\u0001"+
		"\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0005"+
		"\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001"+
		"\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000"+
		"\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0000\u0011\u0001\u0000"+
		"\u0000\u0000\u0000\u0013\u0001\u0000\u0000\u0000\u0000\u0015\u0001\u0000"+
		"\u0000\u0000\u0000\u0017\u0001\u0000\u0000\u0000\u0000\u0019\u0001\u0000"+
		"\u0000\u0000\u0000\u001b\u0001\u0000\u0000\u0000\u0000\u001d\u0001\u0000"+
		"\u0000\u0000\u0000\u001f\u0001\u0000\u0000\u0000\u0000!\u0001\u0000\u0000"+
		"\u0000\u0000#\u0001\u0000\u0000\u0000\u0000\'\u0001\u0000\u0000\u0000"+
		"\u0000/\u0001\u0000\u0000\u0000\u00001\u0001\u0000\u0000\u0000\u0000;"+
		"\u0001\u0000\u0000\u0000\u0000=\u0001\u0000\u0000\u0000\u0000?\u0001\u0000"+
		"\u0000\u0000\u0000A\u0001\u0000\u0000\u0000\u0000C\u0001\u0000\u0000\u0000"+
		"\u0000E\u0001\u0000\u0000\u0000\u0000G\u0001\u0000\u0000\u0000\u0000I"+
		"\u0001\u0000\u0000\u0000\u0000K\u0001\u0000\u0000\u0000\u0000M\u0001\u0000"+
		"\u0000\u0000\u0000O\u0001\u0000\u0000\u0000\u0000Q\u0001\u0000\u0000\u0000"+
		"\u0000S\u0001\u0000\u0000\u0000\u0000U\u0001\u0000\u0000\u0000\u0000W"+
		"\u0001\u0000\u0000\u0000\u0000Y\u0001\u0000\u0000\u0000\u0000[\u0001\u0000"+
		"\u0000\u0000\u0000]\u0001\u0000\u0000\u0000\u0001_\u0001\u0000\u0000\u0000"+
		"\u0003b\u0001\u0000\u0000\u0000\u0005g\u0001\u0000\u0000\u0000\u0007l"+
		"\u0001\u0000\u0000\u0000\to\u0001\u0000\u0000\u0000\u000bs\u0001\u0000"+
		"\u0000\u0000\rv\u0001\u0000\u0000\u0000\u000f{\u0001\u0000\u0000\u0000"+
		"\u0011\u0081\u0001\u0000\u0000\u0000\u0013\u0083\u0001\u0000\u0000\u0000"+
		"\u0015\u0085\u0001\u0000\u0000\u0000\u0017\u0087\u0001\u0000\u0000\u0000"+
		"\u0019\u0089\u0001\u0000\u0000\u0000\u001b\u008b\u0001\u0000\u0000\u0000"+
		"\u001d\u008d\u0001\u0000\u0000\u0000\u001f\u008f\u0001\u0000\u0000\u0000"+
		"!\u0091\u0001\u0000\u0000\u0000#\u0093\u0001\u0000\u0000\u0000%\u0095"+
		"\u0001\u0000\u0000\u0000\'\u0098\u0001\u0000\u0000\u0000)\u009c\u0001"+
		"\u0000\u0000\u0000+\u009e\u0001\u0000\u0000\u0000-\u00a0\u0001\u0000\u0000"+
		"\u0000/\u00a4\u0001\u0000\u0000\u00001\u00ae\u0001\u0000\u0000\u00003"+
		"\u00b8\u0001\u0000\u0000\u00005\u00c1\u0001\u0000\u0000\u00007\u00c7\u0001"+
		"\u0000\u0000\u00009\u00ce\u0001\u0000\u0000\u0000;\u00d4\u0001\u0000\u0000"+
		"\u0000=\u00d8\u0001\u0000\u0000\u0000?\u00da\u0001\u0000\u0000\u0000A"+
		"\u00dc\u0001\u0000\u0000\u0000C\u00de\u0001\u0000\u0000\u0000E\u00e0\u0001"+
		"\u0000\u0000\u0000G\u00e2\u0001\u0000\u0000\u0000I\u00e4\u0001\u0000\u0000"+
		"\u0000K\u00e6\u0001\u0000\u0000\u0000M\u00e9\u0001\u0000\u0000\u0000O"+
		"\u00ec\u0001\u0000\u0000\u0000Q\u00ee\u0001\u0000\u0000\u0000S\u00f1\u0001"+
		"\u0000\u0000\u0000U\u00f3\u0001\u0000\u0000\u0000W\u00f6\u0001\u0000\u0000"+
		"\u0000Y\u0116\u0001\u0000\u0000\u0000[\u011a\u0001\u0000\u0000\u0000]"+
		"\u0126\u0001\u0000\u0000\u0000_`\u0005i\u0000\u0000`a\u0005f\u0000\u0000"+
		"a\u0002\u0001\u0000\u0000\u0000bc\u0005t\u0000\u0000cd\u0005h\u0000\u0000"+
		"de\u0005e\u0000\u0000ef\u0005n\u0000\u0000f\u0004\u0001\u0000\u0000\u0000"+
		"gh\u0005e\u0000\u0000hi\u0005l\u0000\u0000ij\u0005s\u0000\u0000jk\u0005"+
		"e\u0000\u0000k\u0006\u0001\u0000\u0000\u0000lm\u0005f\u0000\u0000mn\u0005"+
		"i\u0000\u0000n\b\u0001\u0000\u0000\u0000op\u0005f\u0000\u0000pq\u0005"+
		"o\u0000\u0000qr\u0005r\u0000\u0000r\n\u0001\u0000\u0000\u0000st\u0005"+
		"d\u0000\u0000tu\u0005o\u0000\u0000u\f\u0001\u0000\u0000\u0000vw\u0005"+
		"t\u0000\u0000wx\u0005r\u0000\u0000xy\u0005u\u0000\u0000yz\u0005e\u0000"+
		"\u0000z\u000e\u0001\u0000\u0000\u0000{|\u0005f\u0000\u0000|}\u0005a\u0000"+
		"\u0000}~\u0005l\u0000\u0000~\u007f\u0005s\u0000\u0000\u007f\u0080\u0005"+
		"e\u0000\u0000\u0080\u0010\u0001\u0000\u0000\u0000\u0081\u0082\u0005:\u0000"+
		"\u0000\u0082\u0012\u0001\u0000\u0000\u0000\u0083\u0084\u0005;\u0000\u0000"+
		"\u0084\u0014\u0001\u0000\u0000\u0000\u0085\u0086\u0005(\u0000\u0000\u0086"+
		"\u0016\u0001\u0000\u0000\u0000\u0087\u0088\u0005)\u0000\u0000\u0088\u0018"+
		"\u0001\u0000\u0000\u0000\u0089\u008a\u0005,\u0000\u0000\u008a\u001a\u0001"+
		"\u0000\u0000\u0000\u008b\u008c\u0005.\u0000\u0000\u008c\u001c\u0001\u0000"+
		"\u0000\u0000\u008d\u008e\u0005{\u0000\u0000\u008e\u001e\u0001\u0000\u0000"+
		"\u0000\u008f\u0090\u0005}\u0000\u0000\u0090 \u0001\u0000\u0000\u0000\u0091"+
		"\u0092\u0005[\u0000\u0000\u0092\"\u0001\u0000\u0000\u0000\u0093\u0094"+
		"\u0005]\u0000\u0000\u0094$\u0001\u0000\u0000\u0000\u0095\u0096\u0007\u0000"+
		"\u0000\u0000\u0096&\u0001\u0000\u0000\u0000\u0097\u0099\u0003%\u0012\u0000"+
		"\u0098\u0097\u0001\u0000\u0000\u0000\u0099\u009a\u0001\u0000\u0000\u0000"+
		"\u009a\u0098\u0001\u0000\u0000\u0000\u009a\u009b\u0001\u0000\u0000\u0000"+
		"\u009b(\u0001\u0000\u0000\u0000\u009c\u009d\u0007\u0001\u0000\u0000\u009d"+
		"*\u0001\u0000\u0000\u0000\u009e\u009f\u0007\u0002\u0000\u0000\u009f,\u0001"+
		"\u0000\u0000\u0000\u00a0\u00a1\u0007\u0003\u0000\u0000\u00a1.\u0001\u0000"+
		"\u0000\u0000\u00a2\u00a5\u0003+\u0015\u0000\u00a3\u00a5\u0005_\u0000\u0000"+
		"\u00a4\u00a2\u0001\u0000\u0000\u0000\u00a4\u00a3\u0001\u0000\u0000\u0000"+
		"\u00a5\u00ab\u0001\u0000\u0000\u0000\u00a6\u00aa\u0003)\u0014\u0000\u00a7"+
		"\u00aa\u0005_\u0000\u0000\u00a8\u00aa\u0003%\u0012\u0000\u00a9\u00a6\u0001"+
		"\u0000\u0000\u0000\u00a9\u00a7\u0001\u0000\u0000\u0000\u00a9\u00a8\u0001"+
		"\u0000\u0000\u0000\u00aa\u00ad\u0001\u0000\u0000\u0000\u00ab\u00a9\u0001"+
		"\u0000\u0000\u0000\u00ab\u00ac\u0001\u0000\u0000\u0000\u00ac0\u0001\u0000"+
		"\u0000\u0000\u00ad\u00ab\u0001\u0000\u0000\u0000\u00ae\u00b4\u0003-\u0016"+
		"\u0000\u00af\u00b3\u0003)\u0014\u0000\u00b0\u00b3\u0005_\u0000\u0000\u00b1"+
		"\u00b3\u0003%\u0012\u0000\u00b2\u00af\u0001\u0000\u0000\u0000\u00b2\u00b0"+
		"\u0001\u0000\u0000\u0000\u00b2\u00b1\u0001\u0000\u0000\u0000\u00b3\u00b6"+
		"\u0001\u0000\u0000\u0000\u00b4\u00b2\u0001\u0000\u0000\u0000\u00b4\u00b5"+
		"\u0001\u0000\u0000\u0000\u00b52\u0001\u0000\u0000\u0000\u00b6\u00b4\u0001"+
		"\u0000\u0000\u0000\u00b7\u00b9\u0003%\u0012\u0000\u00b8\u00b7\u0001\u0000"+
		"\u0000\u0000\u00b9\u00ba\u0001\u0000\u0000\u0000\u00ba\u00b8\u0001\u0000"+
		"\u0000\u0000\u00ba\u00bb\u0001\u0000\u0000\u0000\u00bb4\u0001\u0000\u0000"+
		"\u0000\u00bc\u00bd\u0005.\u0000\u0000\u00bd\u00c2\u00033\u0019\u0000\u00be"+
		"\u00bf\u00033\u0019\u0000\u00bf\u00c0\u0005.\u0000\u0000\u00c0\u00c2\u0001"+
		"\u0000\u0000\u0000\u00c1\u00bc\u0001\u0000\u0000\u0000\u00c1\u00be\u0001"+
		"\u0000\u0000\u0000\u00c26\u0001\u0000\u0000\u0000\u00c3\u00c5\u0005.\u0000"+
		"\u0000\u00c4\u00c6\u00033\u0019\u0000\u00c5\u00c4\u0001\u0000\u0000\u0000"+
		"\u00c5\u00c6\u0001\u0000\u0000\u0000\u00c6\u00c8\u0001\u0000\u0000\u0000"+
		"\u00c7\u00c3\u0001\u0000\u0000\u0000\u00c7\u00c8\u0001\u0000\u0000\u0000"+
		"\u00c88\u0001\u0000\u0000\u0000\u00c9\u00cb\u0005e\u0000\u0000\u00ca\u00cc"+
		"\u0007\u0004\u0000\u0000\u00cb\u00ca\u0001\u0000\u0000\u0000\u00cb\u00cc"+
		"\u0001\u0000\u0000\u0000\u00cc\u00cd\u0001\u0000\u0000\u0000\u00cd\u00cf"+
		"\u00033\u0019\u0000\u00ce\u00c9\u0001\u0000\u0000\u0000\u00ce\u00cf\u0001"+
		"\u0000\u0000\u0000\u00cf:\u0001\u0000\u0000\u0000\u00d0\u00d1\u00033\u0019"+
		"\u0000\u00d1\u00d2\u00037\u001b\u0000\u00d2\u00d5\u0001\u0000\u0000\u0000"+
		"\u00d3\u00d5\u00035\u001a\u0000\u00d4\u00d0\u0001\u0000\u0000\u0000\u00d4"+
		"\u00d3\u0001\u0000\u0000\u0000\u00d5\u00d6\u0001\u0000\u0000\u0000\u00d6"+
		"\u00d7\u00039\u001c\u0000\u00d7<\u0001\u0000\u0000\u0000\u00d8\u00d9\u0005"+
		"=\u0000\u0000\u00d9>\u0001\u0000\u0000\u0000\u00da\u00db\u0005+\u0000"+
		"\u0000\u00db@\u0001\u0000\u0000\u0000\u00dc\u00dd\u0005-\u0000\u0000\u00dd"+
		"B\u0001\u0000\u0000\u0000\u00de\u00df\u0005*\u0000\u0000\u00dfD\u0001"+
		"\u0000\u0000\u0000\u00e0\u00e1\u0005/\u0000\u0000\u00e1F\u0001\u0000\u0000"+
		"\u0000\u00e2\u00e3\u0005%\u0000\u0000\u00e3H\u0001\u0000\u0000\u0000\u00e4"+
		"\u00e5\u0005^\u0000\u0000\u00e5J\u0001\u0000\u0000\u0000\u00e6\u00e7\u0005"+
		"=\u0000\u0000\u00e7\u00e8\u0005=\u0000\u0000\u00e8L\u0001\u0000\u0000"+
		"\u0000\u00e9\u00ea\u0005!\u0000\u0000\u00ea\u00eb\u0005=\u0000\u0000\u00eb"+
		"N\u0001\u0000\u0000\u0000\u00ec\u00ed\u0005<\u0000\u0000\u00edP\u0001"+
		"\u0000\u0000\u0000\u00ee\u00ef\u0005<\u0000\u0000\u00ef\u00f0\u0005=\u0000"+
		"\u0000\u00f0R\u0001\u0000\u0000\u0000\u00f1\u00f2\u0005>\u0000\u0000\u00f2"+
		"T\u0001\u0000\u0000\u0000\u00f3\u00f4\u0005>\u0000\u0000\u00f4\u00f5\u0005"+
		"=\u0000\u0000\u00f5V\u0001\u0000\u0000\u0000\u00f6\u00fc\u0005\"\u0000"+
		"\u0000\u00f7\u00f8\u0005\\\u0000\u0000\u00f8\u00fb\u0005\"\u0000\u0000"+
		"\u00f9\u00fb\t\u0000\u0000\u0000\u00fa\u00f7\u0001\u0000\u0000\u0000\u00fa"+
		"\u00f9\u0001\u0000\u0000\u0000\u00fb\u00fe\u0001\u0000\u0000\u0000\u00fc"+
		"\u00fd\u0001\u0000\u0000\u0000\u00fc\u00fa\u0001\u0000\u0000\u0000\u00fd"+
		"\u00ff\u0001\u0000\u0000\u0000\u00fe\u00fc\u0001\u0000\u0000\u0000\u00ff"+
		"\u0100\u0005\"\u0000\u0000\u0100\u0101\u0006+\u0000\u0000\u0101X\u0001"+
		"\u0000\u0000\u0000\u0102\u0103\u0005*\u0000\u0000\u0103\u0104\u0005/\u0000"+
		"\u0000\u0104\u0105\u0001\u0000\u0000\u0000\u0105\u0117\u0006,\u0001\u0000"+
		"\u0106\u0107\u0005/\u0000\u0000\u0107\u0108\u0005*\u0000\u0000\u0108\u010d"+
		"\u0001\u0000\u0000\u0000\u0109\u010c\u0003Y,\u0000\u010a\u010c\t\u0000"+
		"\u0000\u0000\u010b\u0109\u0001\u0000\u0000\u0000\u010b\u010a\u0001\u0000"+
		"\u0000\u0000\u010c\u010f\u0001\u0000\u0000\u0000\u010d\u010e\u0001\u0000"+
		"\u0000\u0000\u010d\u010b\u0001\u0000\u0000\u0000\u010e\u0114\u0001\u0000"+
		"\u0000\u0000\u010f\u010d\u0001\u0000\u0000\u0000\u0110\u0111\u0005*\u0000"+
		"\u0000\u0111\u0115\u0005/\u0000\u0000\u0112\u0113\u0005\u0000\u0000\u0001"+
		"\u0113\u0115\u0006,\u0002\u0000\u0114\u0110\u0001\u0000\u0000\u0000\u0114"+
		"\u0112\u0001\u0000\u0000\u0000\u0115\u0117\u0001\u0000\u0000\u0000\u0116"+
		"\u0102\u0001\u0000\u0000\u0000\u0116\u0106\u0001\u0000\u0000\u0000\u0117"+
		"\u0118\u0001\u0000\u0000\u0000\u0118\u0119\u0006,\u0003\u0000\u0119Z\u0001"+
		"\u0000\u0000\u0000\u011a\u011b\u0005/\u0000\u0000\u011b\u011c\u0005/\u0000"+
		"\u0000\u011c\u0120\u0001\u0000\u0000\u0000\u011d\u011f\b\u0005\u0000\u0000"+
		"\u011e\u011d\u0001\u0000\u0000\u0000\u011f\u0122\u0001\u0000\u0000\u0000"+
		"\u0120\u011e\u0001\u0000\u0000\u0000\u0120\u0121\u0001\u0000\u0000\u0000"+
		"\u0121\u0123\u0001\u0000\u0000\u0000\u0122\u0120\u0001\u0000\u0000\u0000"+
		"\u0123\u0124\u0006-\u0003\u0000\u0124\\\u0001\u0000\u0000\u0000\u0125"+
		"\u0127\u0007\u0006\u0000\u0000\u0126\u0125\u0001\u0000\u0000\u0000\u0127"+
		"\u0128\u0001\u0000\u0000\u0000\u0128\u0126\u0001\u0000\u0000\u0000\u0128"+
		"\u0129\u0001\u0000\u0000\u0000\u0129\u012a\u0001\u0000\u0000\u0000\u012a"+
		"\u012b\u0006.\u0003\u0000\u012b^\u0001\u0000\u0000\u0000\u0016\u0000\u009a"+
		"\u00a4\u00a9\u00ab\u00b2\u00b4\u00ba\u00c1\u00c5\u00c7\u00cb\u00ce\u00d4"+
		"\u00fa\u00fc\u010b\u010d\u0114\u0116\u0120\u0128\u0004\u0001+\u0000\u0001"+
		",\u0001\u0001,\u0002\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}