// Generated from c:/Users/earthbert/University/CPL/Labs/06-types/src/lexer/CPLangLexer.g4 by ANTLR 4.13.1

    package lexer;

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
		IF=1, THEN=2, ELSE=3, FI=4, BOOL=5, TYPE=6, FOR=7, DO=8, ID=9, INT=10, 
		FLOAT=11, STRING=12, SEMI=13, COMMA=14, ASSIGN=15, LPAREN=16, RPAREN=17, 
		LBRACE=18, RBRACE=19, PLUS=20, MINUS=21, MULT=22, DIV=23, EQUAL=24, LT=25, 
		LE=26, LINE_COMMENT=27, BLOCK_COMMENT=28, WS=29;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"IF", "THEN", "ELSE", "FI", "BOOL", "TYPE", "FOR", "DO", "LETTER", "ID", 
			"DIGIT", "INT", "DIGITS", "FRACTION", "EXPONENT", "FLOAT", "STRING", 
			"SEMI", "COMMA", "ASSIGN", "LPAREN", "RPAREN", "LBRACE", "RBRACE", "PLUS", 
			"MINUS", "MULT", "DIV", "EQUAL", "LT", "LE", "NEW_LINE", "LINE_COMMENT", 
			"BLOCK_COMMENT", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'if'", "'then'", "'else'", "'fi'", null, null, "'for'", "'do'", 
			null, null, null, null, "';'", "','", "'='", "'('", "')'", "'{'", "'}'", 
			"'+'", "'-'", "'*'", "'/'", "'=='", "'<'", "'<='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "IF", "THEN", "ELSE", "FI", "BOOL", "TYPE", "FOR", "DO", "ID", 
			"INT", "FLOAT", "STRING", "SEMI", "COMMA", "ASSIGN", "LPAREN", "RPAREN", 
			"LBRACE", "RBRACE", "PLUS", "MINUS", "MULT", "DIV", "EQUAL", "LT", "LE", 
			"LINE_COMMENT", "BLOCK_COMMENT", "WS"
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
		case 16:
			STRING_action((RuleContext)_localctx, actionIndex);
			break;
		case 33:
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
			 System.err.println("EOF in comment"); 
			break;
		}
	}

	public static final String _serializedATN =
		"\u0004\u0000\u001d\u00fe\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011"+
		"\u0002\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014"+
		"\u0002\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017"+
		"\u0002\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a"+
		"\u0002\u001b\u0007\u001b\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d"+
		"\u0002\u001e\u0007\u001e\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!"+
		"\u0007!\u0002\"\u0007\"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004a\b\u0004\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u0005"+
		"o\b\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\t\u0001\t\u0003\t|\b\t"+
		"\u0001\t\u0001\t\u0001\t\u0005\t\u0081\b\t\n\t\f\t\u0084\t\t\u0001\n\u0001"+
		"\n\u0001\u000b\u0004\u000b\u0089\b\u000b\u000b\u000b\f\u000b\u008a\u0001"+
		"\f\u0004\f\u008e\b\f\u000b\f\f\f\u008f\u0001\r\u0001\r\u0003\r\u0094\b"+
		"\r\u0003\r\u0096\b\r\u0001\u000e\u0001\u000e\u0003\u000e\u009a\b\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0003\u000f\u00a3\b\u000f\u0001\u000f\u0003\u000f\u00a6\b"+
		"\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0005\u0010\u00ac"+
		"\b\u0010\n\u0010\f\u0010\u00af\t\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013"+
		"\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015\u0001\u0016\u0001\u0016"+
		"\u0001\u0017\u0001\u0017\u0001\u0018\u0001\u0018\u0001\u0019\u0001\u0019"+
		"\u0001\u001a\u0001\u001a\u0001\u001b\u0001\u001b\u0001\u001c\u0001\u001c"+
		"\u0001\u001c\u0001\u001d\u0001\u001d\u0001\u001e\u0001\u001e\u0001\u001e"+
		"\u0001\u001f\u0003\u001f\u00d3\b\u001f\u0001\u001f\u0001\u001f\u0001 "+
		"\u0001 \u0001 \u0001 \u0005 \u00db\b \n \f \u00de\t \u0001 \u0001 \u0003"+
		" \u00e2\b \u0001 \u0001 \u0001!\u0001!\u0001!\u0001!\u0001!\u0005!\u00eb"+
		"\b!\n!\f!\u00ee\t!\u0001!\u0001!\u0001!\u0001!\u0003!\u00f4\b!\u0001!"+
		"\u0001!\u0001\"\u0004\"\u00f9\b\"\u000b\"\f\"\u00fa\u0001\"\u0001\"\u0003"+
		"\u00ad\u00dc\u00ec\u0000#\u0001\u0001\u0003\u0002\u0005\u0003\u0007\u0004"+
		"\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\u0000\u0013\t\u0015\u0000\u0017"+
		"\n\u0019\u0000\u001b\u0000\u001d\u0000\u001f\u000b!\f#\r%\u000e\'\u000f"+
		")\u0010+\u0011-\u0012/\u00131\u00143\u00155\u00167\u00179\u0018;\u0019"+
		"=\u001a?\u0000A\u001bC\u001cE\u001d\u0001\u0000\u0004\u0002\u0000AZaz"+
		"\u0001\u000009\u0002\u0000++--\u0003\u0000\t\n\r\r  \u010e\u0000\u0001"+
		"\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0005"+
		"\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001"+
		"\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000"+
		"\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000"+
		"\u0000\u0000\u0000\u0017\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000"+
		"\u0000\u0000\u0000!\u0001\u0000\u0000\u0000\u0000#\u0001\u0000\u0000\u0000"+
		"\u0000%\u0001\u0000\u0000\u0000\u0000\'\u0001\u0000\u0000\u0000\u0000"+
		")\u0001\u0000\u0000\u0000\u0000+\u0001\u0000\u0000\u0000\u0000-\u0001"+
		"\u0000\u0000\u0000\u0000/\u0001\u0000\u0000\u0000\u00001\u0001\u0000\u0000"+
		"\u0000\u00003\u0001\u0000\u0000\u0000\u00005\u0001\u0000\u0000\u0000\u0000"+
		"7\u0001\u0000\u0000\u0000\u00009\u0001\u0000\u0000\u0000\u0000;\u0001"+
		"\u0000\u0000\u0000\u0000=\u0001\u0000\u0000\u0000\u0000A\u0001\u0000\u0000"+
		"\u0000\u0000C\u0001\u0000\u0000\u0000\u0000E\u0001\u0000\u0000\u0000\u0001"+
		"G\u0001\u0000\u0000\u0000\u0003J\u0001\u0000\u0000\u0000\u0005O\u0001"+
		"\u0000\u0000\u0000\u0007T\u0001\u0000\u0000\u0000\t`\u0001\u0000\u0000"+
		"\u0000\u000bn\u0001\u0000\u0000\u0000\rp\u0001\u0000\u0000\u0000\u000f"+
		"t\u0001\u0000\u0000\u0000\u0011w\u0001\u0000\u0000\u0000\u0013{\u0001"+
		"\u0000\u0000\u0000\u0015\u0085\u0001\u0000\u0000\u0000\u0017\u0088\u0001"+
		"\u0000\u0000\u0000\u0019\u008d\u0001\u0000\u0000\u0000\u001b\u0095\u0001"+
		"\u0000\u0000\u0000\u001d\u0097\u0001\u0000\u0000\u0000\u001f\u00a2\u0001"+
		"\u0000\u0000\u0000!\u00a7\u0001\u0000\u0000\u0000#\u00b3\u0001\u0000\u0000"+
		"\u0000%\u00b5\u0001\u0000\u0000\u0000\'\u00b7\u0001\u0000\u0000\u0000"+
		")\u00b9\u0001\u0000\u0000\u0000+\u00bb\u0001\u0000\u0000\u0000-\u00bd"+
		"\u0001\u0000\u0000\u0000/\u00bf\u0001\u0000\u0000\u00001\u00c1\u0001\u0000"+
		"\u0000\u00003\u00c3\u0001\u0000\u0000\u00005\u00c5\u0001\u0000\u0000\u0000"+
		"7\u00c7\u0001\u0000\u0000\u00009\u00c9\u0001\u0000\u0000\u0000;\u00cc"+
		"\u0001\u0000\u0000\u0000=\u00ce\u0001\u0000\u0000\u0000?\u00d2\u0001\u0000"+
		"\u0000\u0000A\u00d6\u0001\u0000\u0000\u0000C\u00e5\u0001\u0000\u0000\u0000"+
		"E\u00f8\u0001\u0000\u0000\u0000GH\u0005i\u0000\u0000HI\u0005f\u0000\u0000"+
		"I\u0002\u0001\u0000\u0000\u0000JK\u0005t\u0000\u0000KL\u0005h\u0000\u0000"+
		"LM\u0005e\u0000\u0000MN\u0005n\u0000\u0000N\u0004\u0001\u0000\u0000\u0000"+
		"OP\u0005e\u0000\u0000PQ\u0005l\u0000\u0000QR\u0005s\u0000\u0000RS\u0005"+
		"e\u0000\u0000S\u0006\u0001\u0000\u0000\u0000TU\u0005f\u0000\u0000UV\u0005"+
		"i\u0000\u0000V\b\u0001\u0000\u0000\u0000WX\u0005t\u0000\u0000XY\u0005"+
		"r\u0000\u0000YZ\u0005u\u0000\u0000Za\u0005e\u0000\u0000[\\\u0005f\u0000"+
		"\u0000\\]\u0005a\u0000\u0000]^\u0005l\u0000\u0000^_\u0005s\u0000\u0000"+
		"_a\u0005e\u0000\u0000`W\u0001\u0000\u0000\u0000`[\u0001\u0000\u0000\u0000"+
		"a\n\u0001\u0000\u0000\u0000bc\u0005I\u0000\u0000cd\u0005n\u0000\u0000"+
		"do\u0005t\u0000\u0000ef\u0005F\u0000\u0000fg\u0005l\u0000\u0000gh\u0005"+
		"o\u0000\u0000hi\u0005a\u0000\u0000io\u0005t\u0000\u0000jk\u0005B\u0000"+
		"\u0000kl\u0005o\u0000\u0000lm\u0005o\u0000\u0000mo\u0005l\u0000\u0000"+
		"nb\u0001\u0000\u0000\u0000ne\u0001\u0000\u0000\u0000nj\u0001\u0000\u0000"+
		"\u0000o\f\u0001\u0000\u0000\u0000pq\u0005f\u0000\u0000qr\u0005o\u0000"+
		"\u0000rs\u0005r\u0000\u0000s\u000e\u0001\u0000\u0000\u0000tu\u0005d\u0000"+
		"\u0000uv\u0005o\u0000\u0000v\u0010\u0001\u0000\u0000\u0000wx\u0007\u0000"+
		"\u0000\u0000x\u0012\u0001\u0000\u0000\u0000y|\u0003\u0011\b\u0000z|\u0005"+
		"_\u0000\u0000{y\u0001\u0000\u0000\u0000{z\u0001\u0000\u0000\u0000|\u0082"+
		"\u0001\u0000\u0000\u0000}\u0081\u0003\u0011\b\u0000~\u0081\u0005_\u0000"+
		"\u0000\u007f\u0081\u0003\u0015\n\u0000\u0080}\u0001\u0000\u0000\u0000"+
		"\u0080~\u0001\u0000\u0000\u0000\u0080\u007f\u0001\u0000\u0000\u0000\u0081"+
		"\u0084\u0001\u0000\u0000\u0000\u0082\u0080\u0001\u0000\u0000\u0000\u0082"+
		"\u0083\u0001\u0000\u0000\u0000\u0083\u0014\u0001\u0000\u0000\u0000\u0084"+
		"\u0082\u0001\u0000\u0000\u0000\u0085\u0086\u0007\u0001\u0000\u0000\u0086"+
		"\u0016\u0001\u0000\u0000\u0000\u0087\u0089\u0003\u0015\n\u0000\u0088\u0087"+
		"\u0001\u0000\u0000\u0000\u0089\u008a\u0001\u0000\u0000\u0000\u008a\u0088"+
		"\u0001\u0000\u0000\u0000\u008a\u008b\u0001\u0000\u0000\u0000\u008b\u0018"+
		"\u0001\u0000\u0000\u0000\u008c\u008e\u0003\u0015\n\u0000\u008d\u008c\u0001"+
		"\u0000\u0000\u0000\u008e\u008f\u0001\u0000\u0000\u0000\u008f\u008d\u0001"+
		"\u0000\u0000\u0000\u008f\u0090\u0001\u0000\u0000\u0000\u0090\u001a\u0001"+
		"\u0000\u0000\u0000\u0091\u0093\u0005.\u0000\u0000\u0092\u0094\u0003\u0019"+
		"\f\u0000\u0093\u0092\u0001\u0000\u0000\u0000\u0093\u0094\u0001\u0000\u0000"+
		"\u0000\u0094\u0096\u0001\u0000\u0000\u0000\u0095\u0091\u0001\u0000\u0000"+
		"\u0000\u0095\u0096\u0001\u0000\u0000\u0000\u0096\u001c\u0001\u0000\u0000"+
		"\u0000\u0097\u0099\u0005e\u0000\u0000\u0098\u009a\u0007\u0002\u0000\u0000"+
		"\u0099\u0098\u0001\u0000\u0000\u0000\u0099\u009a\u0001\u0000\u0000\u0000"+
		"\u009a\u009b\u0001\u0000\u0000\u0000\u009b\u009c\u0003\u0019\f\u0000\u009c"+
		"\u001e\u0001\u0000\u0000\u0000\u009d\u009e\u0003\u0019\f\u0000\u009e\u009f"+
		"\u0003\u001b\r\u0000\u009f\u00a3\u0001\u0000\u0000\u0000\u00a0\u00a1\u0005"+
		".\u0000\u0000\u00a1\u00a3\u0003\u0019\f\u0000\u00a2\u009d\u0001\u0000"+
		"\u0000\u0000\u00a2\u00a0\u0001\u0000\u0000\u0000\u00a3\u00a5\u0001\u0000"+
		"\u0000\u0000\u00a4\u00a6\u0003\u001d\u000e\u0000\u00a5\u00a4\u0001\u0000"+
		"\u0000\u0000\u00a5\u00a6\u0001\u0000\u0000\u0000\u00a6 \u0001\u0000\u0000"+
		"\u0000\u00a7\u00ad\u0005\"\u0000\u0000\u00a8\u00a9\u0005\\\u0000\u0000"+
		"\u00a9\u00ac\u0005\"\u0000\u0000\u00aa\u00ac\t\u0000\u0000\u0000\u00ab"+
		"\u00a8\u0001\u0000\u0000\u0000\u00ab\u00aa\u0001\u0000\u0000\u0000\u00ac"+
		"\u00af\u0001\u0000\u0000\u0000\u00ad\u00ae\u0001\u0000\u0000\u0000\u00ad"+
		"\u00ab\u0001\u0000\u0000\u0000\u00ae\u00b0\u0001\u0000\u0000\u0000\u00af"+
		"\u00ad\u0001\u0000\u0000\u0000\u00b0\u00b1\u0005\"\u0000\u0000\u00b1\u00b2"+
		"\u0006\u0010\u0000\u0000\u00b2\"\u0001\u0000\u0000\u0000\u00b3\u00b4\u0005"+
		";\u0000\u0000\u00b4$\u0001\u0000\u0000\u0000\u00b5\u00b6\u0005,\u0000"+
		"\u0000\u00b6&\u0001\u0000\u0000\u0000\u00b7\u00b8\u0005=\u0000\u0000\u00b8"+
		"(\u0001\u0000\u0000\u0000\u00b9\u00ba\u0005(\u0000\u0000\u00ba*\u0001"+
		"\u0000\u0000\u0000\u00bb\u00bc\u0005)\u0000\u0000\u00bc,\u0001\u0000\u0000"+
		"\u0000\u00bd\u00be\u0005{\u0000\u0000\u00be.\u0001\u0000\u0000\u0000\u00bf"+
		"\u00c0\u0005}\u0000\u0000\u00c00\u0001\u0000\u0000\u0000\u00c1\u00c2\u0005"+
		"+\u0000\u0000\u00c22\u0001\u0000\u0000\u0000\u00c3\u00c4\u0005-\u0000"+
		"\u0000\u00c44\u0001\u0000\u0000\u0000\u00c5\u00c6\u0005*\u0000\u0000\u00c6"+
		"6\u0001\u0000\u0000\u0000\u00c7\u00c8\u0005/\u0000\u0000\u00c88\u0001"+
		"\u0000\u0000\u0000\u00c9\u00ca\u0005=\u0000\u0000\u00ca\u00cb\u0005=\u0000"+
		"\u0000\u00cb:\u0001\u0000\u0000\u0000\u00cc\u00cd\u0005<\u0000\u0000\u00cd"+
		"<\u0001\u0000\u0000\u0000\u00ce\u00cf\u0005<\u0000\u0000\u00cf\u00d0\u0005"+
		"=\u0000\u0000\u00d0>\u0001\u0000\u0000\u0000\u00d1\u00d3\u0005\r\u0000"+
		"\u0000\u00d2\u00d1\u0001\u0000\u0000\u0000\u00d2\u00d3\u0001\u0000\u0000"+
		"\u0000\u00d3\u00d4\u0001\u0000\u0000\u0000\u00d4\u00d5\u0005\n\u0000\u0000"+
		"\u00d5@\u0001\u0000\u0000\u0000\u00d6\u00d7\u0005/\u0000\u0000\u00d7\u00d8"+
		"\u0005/\u0000\u0000\u00d8\u00dc\u0001\u0000\u0000\u0000\u00d9\u00db\t"+
		"\u0000\u0000\u0000\u00da\u00d9\u0001\u0000\u0000\u0000\u00db\u00de\u0001"+
		"\u0000\u0000\u0000\u00dc\u00dd\u0001\u0000\u0000\u0000\u00dc\u00da\u0001"+
		"\u0000\u0000\u0000\u00dd\u00e1\u0001\u0000\u0000\u0000\u00de\u00dc\u0001"+
		"\u0000\u0000\u0000\u00df\u00e2\u0003?\u001f\u0000\u00e0\u00e2\u0005\u0000"+
		"\u0000\u0001\u00e1\u00df\u0001\u0000\u0000\u0000\u00e1\u00e0\u0001\u0000"+
		"\u0000\u0000\u00e2\u00e3\u0001\u0000\u0000\u0000\u00e3\u00e4\u0006 \u0001"+
		"\u0000\u00e4B\u0001\u0000\u0000\u0000\u00e5\u00e6\u0005/\u0000\u0000\u00e6"+
		"\u00e7\u0005*\u0000\u0000\u00e7\u00ec\u0001\u0000\u0000\u0000\u00e8\u00eb"+
		"\u0003C!\u0000\u00e9\u00eb\t\u0000\u0000\u0000\u00ea\u00e8\u0001\u0000"+
		"\u0000\u0000\u00ea\u00e9\u0001\u0000\u0000\u0000\u00eb\u00ee\u0001\u0000"+
		"\u0000\u0000\u00ec\u00ed\u0001\u0000\u0000\u0000\u00ec\u00ea\u0001\u0000"+
		"\u0000\u0000\u00ed\u00f3\u0001\u0000\u0000\u0000\u00ee\u00ec\u0001\u0000"+
		"\u0000\u0000\u00ef\u00f0\u0005*\u0000\u0000\u00f0\u00f4\u0005/\u0000\u0000"+
		"\u00f1\u00f2\u0005\u0000\u0000\u0001\u00f2\u00f4\u0006!\u0002\u0000\u00f3"+
		"\u00ef\u0001\u0000\u0000\u0000\u00f3\u00f1\u0001\u0000\u0000\u0000\u00f4"+
		"\u00f5\u0001\u0000\u0000\u0000\u00f5\u00f6\u0006!\u0001\u0000\u00f6D\u0001"+
		"\u0000\u0000\u0000\u00f7\u00f9\u0007\u0003\u0000\u0000\u00f8\u00f7\u0001"+
		"\u0000\u0000\u0000\u00f9\u00fa\u0001\u0000\u0000\u0000\u00fa\u00f8\u0001"+
		"\u0000\u0000\u0000\u00fa\u00fb\u0001\u0000\u0000\u0000\u00fb\u00fc\u0001"+
		"\u0000\u0000\u0000\u00fc\u00fd\u0006\"\u0001\u0000\u00fdF\u0001\u0000"+
		"\u0000\u0000\u0016\u0000`n{\u0080\u0082\u008a\u008f\u0093\u0095\u0099"+
		"\u00a2\u00a5\u00ab\u00ad\u00d2\u00dc\u00e1\u00ea\u00ec\u00f3\u00fa\u0003"+
		"\u0001\u0010\u0000\u0006\u0000\u0000\u0001!\u0001";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}