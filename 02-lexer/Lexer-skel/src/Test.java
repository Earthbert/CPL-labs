import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;


public class Test {

  public static void main(String[] args) throws IOException {
    CPLangLexer lexer = new CPLangLexer(CharStreams.fromFileName("program.txt"));
    CommonTokenStream tokenStream = new CommonTokenStream(lexer);

    tokenStream.fill();

    // Decomentați această secvență în locul celei de mai jos pentru o afișare mai curată
    /*
    tokenStream.getTokens().forEach(token -> System.out.println(
        token.getText() + " : " + CPLangLexer.VOCABULARY.getSymbolicName(token.getType())));
    */

    tokenStream.getTokens()
        .forEach(token -> System.out.println(((CommonToken) token).toString(lexer)));
  }
}
