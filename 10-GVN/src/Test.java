import java.io.IOException;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import analysis.DominatorAnalysis;
import analysis.DominatorTree;
import analysis.LivenessAnalysis;
import irgen.IRGenVisitor;
import irgen.SSATransformer;
import optim.GlobalValueNumbering;
import optim.UnreachableCodeElimination;
import parser.ASTConstructionVisitor;
import parser.CPLangParser;
import semantic.DefinitionPassVisitor;
import semantic.ResolutionPassVisitor;

public class Test {

    public static void main(final String[] args) throws IOException {

        final var input = CharStreams.fromFileName("gvn.txt");

        final var lexer = new lexer.CPLangLexer(input);
        final var tokenStream = new CommonTokenStream(lexer);

        /*
        tokenStream.fill();
        List<Token> tokens = tokenStream.getTokens();
        for (var token : tokens) {
            var text = token.getText();
            var type = CPLangLexer.VOCABULARY.getSymbolicName(token.getType());

            System.out.println(text + " : " + type);
        }
        */

        final var parser = new CPLangParser(tokenStream);
        final var tree = parser.prog();
//        System.out.println(tree.toStringTree(parser));

        // Visitor-ul de mai jos parcurge arborele de derivare și construiește
        // un arbore de sintaxă abstractă (AST).
        final var astConstructionVisitor = new ASTConstructionVisitor();

        // ast este AST-ul proaspăt construit pe baza arborelui de derivare.
        final var ast = astConstructionVisitor.visit(tree);

        // În vederea gestiunii referirilor anticipate, utilizăm două treceri,
        // una de definire a simbolurilor, și cealaltă, de rezolvare.
        final var definitionPassVisitor = new DefinitionPassVisitor();

        // A doua trecere, pentru rezolvarea simbolurilor în raport cu domeniile
        // de vizibilitate memorate în prima trecere. Observați că, în această
        // trecere, nu mai este necesară gestiunea domeniului curent,
        // ca în prima trecere.
        final var resolutionPassVisitor = new ResolutionPassVisitor();

        ast.accept(definitionPassVisitor);
        ast.accept(resolutionPassVisitor);

        // Generarea de cod intermediar
        final var irGenVisitor = new IRGenVisitor();
        ast.accept(irGenVisitor);

        // CFG pentru corpul principal al programului
        final var mainCfg = irGenVisitor.mainCfg;
        mainCfg.toDOTFile("main.dot");

        // CFG-uri pentru fiecare funcție
        for (final var functionSymbol : irGenVisitor.functionSymbols.values())
            functionSymbol.cfg.toDOTFile(functionSymbol.getName() + ".dot");

        // CFG-ul funcției "f"
        final var fCfg = irGenVisitor.functionSymbols.get("f").cfg;

//        // Analiza globală a constantelor
//        var fGlobalConstantAnalysis = new GlobalConstantAnalysis(fCfg);
//        fGlobalConstantAnalysis.runForward();
//        System.out.println(fGlobalConstantAnalysis);

        // Analiza duratei de viață a valorilor
        final var fLivenessAnalysis = new LivenessAnalysis(fCfg);
        fLivenessAnalysis.runBackwards();
//        System.out.println(fLivenessAnalysis);

        // Analiza de dominanță
        final var fDominatorAnalysis = new DominatorAnalysis(fCfg);
        fDominatorAnalysis.runForward();
//        System.out.println(fDominatorAnalysis);

        // Arborele de dominanță
        final var fDominatorTree = new DominatorTree(fDominatorAnalysis);
//        System.out.println(fDominatorTree);
//        fDominatorTree.toDOTFile("f-DT.dot");

        // Transformarea în forma SSA
        final var fSSATransformer = new SSATransformer(fDominatorTree, fLivenessAnalysis);
        fSSATransformer.insertPhiInstructions();
        fSSATransformer.rename();

        fCfg.toDOTFile("f-SSA.dot");

        String name = "f-SSA";
        

        // TODO Continuați cu alte runde ale celor două optimizări de mai jos
        for (int i = 1; i <= 3; i++) {
            // Numerotarea globală a valorilor
            final var globalValueNumbering = new GlobalValueNumbering(fCfg);
            globalValueNumbering.propagate();

            name += "-GVN";
            fCfg.toDOTFile(name + ".dot");

            // Eliminarea codului neexecutabil
            final var unreachableCodeElimination = new UnreachableCodeElimination(fCfg);
            unreachableCodeElimination.processConditionalBranches();
            unreachableCodeElimination.computeReachableBlocks();
            unreachableCodeElimination.cleanup();

            name += "-UCE";
            fCfg.toDOTFile(name + ".dot");
        }
    }



}
