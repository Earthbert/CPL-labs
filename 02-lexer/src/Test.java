import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.antlr.v4.runtime.*;
import org.apache.commons.lang3.StringUtils;

public class Test {

    static final boolean PRINT = true;
    static final boolean CLEAN_PRINT = true;

    public static void main(String[] args) throws IOException {
        var input = CharStreams.fromFileName("manual.txt");

        var lexer = new CPLangLexer(input);
        var tokenStream = new CommonTokenStream(lexer);

        tokenStream.fill();
        List<Token> tokens = tokenStream.getTokens();
        for (var token : tokens) {
            if (!PRINT)
                continue;

            if (CLEAN_PRINT) {
                var text = token.getText();
                var type = CPLangLexer.VOCABULARY.getSymbolicName(token.getType());
                System.out.println(String.format("%-15s -> %s", text, type));
            } else
                System.out.println(((CommonToken) token).toString(lexer));
        }

        checkTokens(tokens, lexer);
    }

    private static void checkTokens(List<Token> tokens, CPLangLexer lexer) throws IOException {
        try (BufferedReader refStream = new BufferedReader(new FileReader("reference.txt"))) {

            for (var token : tokens) {
                final String ref = refStream.readLine();
                final String text = ((CommonToken) token).toString(lexer);
                final String diff = StringUtils.difference(ref, text);

                if (!diff.isEmpty()) {
                    System.out.println("Difference found:");
                    System.out.println("Reference: " + ref);
                    System.out.println("Actual:    " + text);
                    System.out.println("Diff:      " + diff);
                }
            }
        }
    }
}
