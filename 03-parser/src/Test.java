import java.io.IOException;
import java.util.*;
import java.io.File;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import antlr.*;
import antlr.CPLangParser.AddSubContext;
import antlr.CPLangParser.FuncDefContext;
import antlr.CPLangParser.IntContext;
import antlr.CPLangParser.MulDivContext;
import antlr.CPLangParser.ParensContext;

public class Test {

	/* Variable pentru contorizarea definițiilor de variabile și funcții */
	public static int varDefs = 0;
	public static int funcDefs = 0;

	public static CPLangParser readFromFile(String filename)
			throws IOException {
		var input = CharStreams.fromFileName(filename);

		var lexer = new CPLangLexer(input);
		var tokenStream = new CommonTokenStream(lexer);

		/*
		 * Decomentează pentru a afișa mulțimea de tokeni generată
		 * după analiza lexicală pe fișierul de intrare.
		 **/

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

		return new CPLangParser(tokenStream);
	}

	public static void task12() throws IOException {
		/*
		 * Fișierul manual.txt conține un program CPLang.
		 * Fișierul reference1-2.txt poate fi folosit ca referință.
		 **/
		var parser = readFromFile("manual.txt");

		/* TODO 1 înlocuiește expr cu regula de start din gramatică */
		var tree = parser.prog();

		System.out.println(tree.toStringTree(parser));

		// Interfața CPLangParserListener conține, pentru fiecare alternativă
		// etichetată, câte o pereche de metode enter/exit. Spre exemplu,
		// pentru eticheta if, avem perechea de metode enterIf(IfContext)
		// și exitIf(IfContext). Clasa CPLangParserBaseListener oferă
		// implementări goale ale acestor metode, astfel încât noi să putem
		// supradefini doar metodele de interes.
		//
		// Listenerii au avantajul că parcurgerea arborelui de derivare este
		// realizată automat, pe baza unui walker, ca mai jos. Dezavantajul
		// constă în faptul că este parcurs întregul arbore de derivare, chiar
		// dacă pe noi ne intesează doar anumite noduri particulare.
		var listener = new CPLangParserBaseListener() {
			/*
			 * TODO 2
			 * Suprascrie metodele din Listener pentru a contoriza numărul
			 * de definiții de variabile, atât globale cât și ca parametri
			 * formali ai unor funcții și, separat, numărul
			 * de definiții de funcții.
			 * !!! Variabilele folosite pentru contori sunt varDefs și funcDefs.
			 *
			 * HINT: Uită-te în clasa CPLangParserBaseListener
			 * la metodele exit<ETICHETA>, unde eticheta corespunde unei
			 * reguli din gramatică. Trebuie să suprascrii cele 3 metode.
			 */

			@Override
			public void exitVarDef(CPLangParser.VarDefContext ctx) {
				varDefs++;
			};

			@Override
			public void exitFuncDef(FuncDefContext ctx) {
				funcDefs++;
				varDefs += ctx.ID().size() - 1;
			};
		};
		// Un walker realizează o parcurgere în adâncime a arborelui de
		// derivare, invocând la momentul potrivit metodele enter/exit.
		var walker = new ParseTreeWalker();
		walker.walk(listener, tree);

		/*
		 * După parcurgerea arborelui de derivare,
		 * afișăm contorii pentru definiții.
		 */
		System.out.println("Definitii de variable: " + varDefs);
		System.out.println("Definitii de functii: " + funcDefs);
	}

	public static void task3() throws IOException {
		/*
		 * Fișierul input3.txt conține un program CPLang restricționat,
		 * fără definiții, fără referiri la variabile sau funcții.
		 * Conține o serie de instrucțiuni aritmetice, doar pentru literali
		 * întregi.
		 * Fișierul reference3.txt continue output-ul corespunzător.
		 **/
		var parser = readFromFile("input3.txt");

		/* TODO 3 înlocuiește expr cu regula de start */
		var tree = parser.prog();

		Scanner s = new Scanner(new File("reference3.txt"));
		ArrayList<Integer> reference = new ArrayList<Integer>();
		while (s.hasNextLine()) {
			reference.add(Integer.parseInt(s.nextLine()));
		}
		s.close();

		/*
		 * Interfața CPLangParserVisitor conține câte o metodă pentru
		 * fiecare alternativă etichetată. Spre exemplu, pentru eticheta mul,
		 * avem metoda T visitMul(MulContext).
		 * Clasa CPLangParserBaseVisitor<T> oferă implementări implicite
		 * ale acestor metode, a.i noi să putem supradefini doar metodele
		 * de interes.
		 *
		 * De remarcat că, spre deosebire de listeners, metodele de vizitare
		 * pot întoarce și o valoare utilă, care poate fi interpretată
		 * recursiv. Acest lucru, alături de faptul că putem vizita doar
		 * nodurile de interes pentru noi, constituie avantajul vizitatorilor.
		 * Dezavantajul constă în faptul că este necesară vizitarea explicită
		 * a copiilor, mai ales când trebuie să parcurgem întregul arbore.
		 *
		 * Pentru acest task, fiecare metodă visit va întoarce un Integer.
		 * 
		 * TODO 3
		 * Pentru acest task, programele CPLang vor conține doar
		 * expresii aritmetice cu literali numerici, fără definiții sau
		 * referiri la variabile!!!
		 * Pentru rezolvare, trebuie suprascrise metodele visit, doar pentru
		 * etichetele corespunzatoare literalilor si operatiilor aritmetice.
		 * Ignoram restul.
		 **/
		var visitor = new CPLangParserBaseVisitor<Integer>() {
			@Override
			public Integer visitProg(CPLangParser.ProgContext ctx) {
				int index = 0;
				for (var expr : ctx.expr()) {
					Integer res = visit(expr);
					System.out.println(expr.getText() + " = " + res);
					var ref_res = reference.get(index++);
					if (res != ref_res) {
						System.err.println("Your result : " + res +
								" is different than " + ref_res);
					}
				}

				return 0;
			}

			@Override
			public Integer visitInt(IntContext ctx) {
				return Integer.valueOf(ctx.getText());
			}

			@Override
			public Integer visitAddSub(AddSubContext ctx) {
				if (ctx.op.getText().equals("+"))
					return visit(ctx.left) + visit(ctx.right);
				else
					return visit(ctx.left) - visit(ctx.right);
			}

			@Override
			public Integer visitMulDiv(MulDivContext ctx) {
				if (ctx.op.getText().equals("*"))
					return visit(ctx.left) * visit(ctx.right);
				else
					return visit(ctx.left) / visit(ctx.right);
			}

			@Override
			public Integer visitParens(ParensContext ctx) {
				return visit(ctx.inner);
			}
		};

		visitor.visit(tree);
	}

	public static void main(String[] args) throws IOException {
		task12();
		task3();
	}
};
