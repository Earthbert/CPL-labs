import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.antlr.v4.runtime.*;
import antlr.*;
import antlr.CPLangLexer;
import antlr.CPLangParser;
import ast.ASTConstructionVisitor;
import ast.PrintVisitor;

public class Test {

    public static void main(String[] args) throws IOException {
        System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream("output.txt")), true));

        var input = CharStreams.fromFileName("manual.txt");

        var lexer = new CPLangLexer(input);
        var tokenStream = new CommonTokenStream(lexer);

        /*
        tokenStream.fill();
        List<Token> tokens = tokenStream.getTokens();
        for (var token : tokens) {
            var text = token.getText();
            var type = lexer.CPLangLexer.VOCABULARY.getSymbolicName(token.getType());

            System.out.println(text + " : " + type);
        }
        */

        var parser = new CPLangParser(tokenStream);
        // schimbati regula de start in "prog" pentru a testa un program CPLang complet
        var tree = parser.prog();

        // Visitor-ul de mai jos parcurge arborele de derivare și construiește
        // un arbore de sintaxă abstractă (AST).
        var astConstructionVisitor = new ASTConstructionVisitor();

        // ast este AST-ul proaspăt construit pe baza arborelui de derivare.
        var ast = astConstructionVisitor.visit(tree);

        // Acest visitor parcurge AST-ul generat mai sus.
        // ATENȚIE! Avem de-a face cu două categorii de visitori:
        // (1) Cei pentru arborele de derivare, care extind
        //     CPLangParserBaseVisitor<T> și
        // (2) Cei pentru AST, care extind ASTVisitor<T>.
        // Aveți grijă să nu îi confundați!
        var printVisitor = new PrintVisitor();

        // TODO 5: Creati un program CPLang care sa cuprinda cat mai multe
        // constructii definite in laboratorul de astazi. Scrieti codul CPLang
        // intr-un fisier txt si modificati fisierul de input de la inceputul
        // metodei 'main'

        ast.accept(printVisitor);
    }


}
