import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import asmgen.AsmGenVisitor;
import asmgen.NTAnalizator;
import lexer.CPLangLexer;
import parser.ASTConstructionVisitor;
import parser.CPLangParser;
import semantic.DefinitionPassVisitor;
import semantic.ResolutionPassVisitor;

public class Test {

    public static void main(final String[] args) throws IOException {

        final var input = CharStreams.fromFileName("asmgen.txt");

        final var lexer = new CPLangLexer(input);
        final var tokenStream = new CommonTokenStream(lexer);

        /*
         * tokenStream.fill();
         * List<Token> tokens = tokenStream.getTokens();
         * for (var token : tokens) {
         * var text = token.getText();
         * var type = CPLangLexer.VOCABULARY.getSymbolicName(token.getType());
         * 
         * System.out.println(text + " : " + type);
         * }
         */

        final var parser = new CPLangParser(tokenStream);
        final var tree = parser.prog();
        // System.out.println(tree.toStringTree(parser));

        // Construcția AST-ului din arborele de derivare
        final var astConstructionVisitor = new ASTConstructionVisitor();
        final var ast = astConstructionVisitor.visit(tree);

        // Analiză semantică, trecerea 1: definire simboluri și domenii de vizibilitate
        final var definitionPassVisitor = new DefinitionPassVisitor();
        ast.accept(definitionPassVisitor);

        // Analiză semantică, trecerea 2: verificare simboluri
        final var resolutionPassVisitor = new ResolutionPassVisitor();
        ast.accept(resolutionPassVisitor);

        // Generarea de cod
        ast.accept(new NTAnalizator());
        final var t = ast.accept(new AsmGenVisitor());

        final File file = new File("output.s");
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(t.render());
        }
    }

}
