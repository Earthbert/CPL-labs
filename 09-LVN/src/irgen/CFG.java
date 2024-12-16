package irgen;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;
import semantic.FunctionSymbol;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Graf al fluxului de control (control-flow graph).
 *
 * Reprezintă un graf orientat, în care nodurile sunt basic block-uri, și există muchie orientată între două blocuri
 * dacă fluxul de control poate ajunge de la ultima instrucțiune a primului bloc la prima instrucțiune a celui
 * de-al doilea.
 *
 * Șabloanele interesante dintr-un CFG sunt:
 *
 * - multifurcațiile, când fluxul de control diverge pe mai multe căi (e.g. ramurile unui if sau case)
 * - join points, când fluxul de control converge într-un singur bloc (e.g. după ramurile unui if sau case)
 * - ciclurile (e.g. buclă for).
 */
public class CFG {
    static STGroupFile group = new STGroupFile("irgen/cfg.stg");

    /**
     * Simbolul funcției căreia îi corespunde acest CFG, sau null
     */
    FunctionSymbol functionSymbol;

    /**
     * Listă ordonată de blocuri. Muchiile sunt implicite în reprezentarea blocurilor înselor (succesori și predecesori).
     */
    public List<BasicBlock> blocks = new ArrayList<>();

    /**
     * Blocul curent la care se adaugă următoarea instrucțiune. Inițial este blocul de intrare în graf.
     */
    BasicBlock currentBlock = new BasicBlock("entry");

    /**
     * Stabilește legături bidirecționale între CFG și simbolul de funcție căreia îi corespunde, dacă aceasta există.
     * Inițial, CFG-ul conține doar blocul de intrare.
     *
     * @param functionSymbol simbolul funcției căreia îi corespunde acest CFG, sau null
     */
    public CFG(FunctionSymbol functionSymbol) {
        if (functionSymbol != null) {
            this.functionSymbol = functionSymbol;
            functionSymbol.cfg = this;
        }

        blocks.add(currentBlock);
    }

    public CFG() {
        this(null);
    }

    public void addBlock(BasicBlock block) {
        blocks.add(block);
    }

    public void setCurrentBlock(BasicBlock block) {
        currentBlock = block;
    }

    /**
     * Adaugă o instrucțiune la blocul curent
     *
     * @param instruction noua instrucțiune
     */
    public void addInstruction(Instruction instruction) {
        currentBlock.addInstruction(instruction);
    }

    @Override
    public String toString() {
        return blocks.stream()
                .map(BasicBlock::toString)
                .collect(Collectors.joining("\n"));
    }

    /**
     * Desenează CFG-ul curent în format DOT.
     *
     * @return un StringTemplate cu reprezentarea
     */
    public ST toDOT() {
        var graph = group.getInstanceOf("cfg");

        for (var block : blocks) {
            var blockTemplate = group.getInstanceOf("block")
                    .add("name", block.getName())
                    .add("instructions", block.toString().replace("\n\t", "\\l") + "\\l");

            graph.add("blocks", blockTemplate);

            if (block.successors.size() == 1) {
                var edge = group.getInstanceOf("edge")
                        .add("from", block.getName())
                        .add("to", block.successors.get(0).getName());

                graph.add("edges", edge);
            } else if (block.successors.size() == 2) {
                blockTemplate.add("binary", true);

                var edge = group.getInstanceOf("edge")
                        .add("from", block.getName())
                        .add("to", block.successors.get(0).getName())
                        .add("color", "green4")
                        .add("anchor", "t");

                graph.add("edges", edge);

                edge = group.getInstanceOf("edge")
                        .add("from", block.getName())
                        .add("to", block.successors.get(1).getName())
                        .add("color", "red")
                        .add("anchor", "f");

                graph.add("edges", edge);
            }
        }

        return graph;
    }

    /**
     * Scrie reprezentarea DOT a CFG-ului într-un fișier
     *
     * @param  fileName    numele fișierului
     * @throws IOException -
     */
    public void toDOTFile(String fileName) throws IOException {
        Files.writeString(Path.of(fileName), toDOT().render());
    }
}
