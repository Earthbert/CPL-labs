package irgen;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import lexer.CPLangLexer;
import parser.ASTNode;
import parser.ASTVisitor;
import semantic.FunctionSymbol;
import semantic.IdSymbol;
import semantic.IntSymbol;
import semantic.Symbol;

/**
 * Visitor al AST-ului care generează codul intermediar, organizat în mai multe CFG-uri: unul pentru corpul principal
 * al programului (mainCfg) și câte unul pentru fiecare funcție, reținut în simbolul aferent (FunctionSymbol).
 *
 * Funcțiile visit întorc tipul Symbol. Astfel, fiecare funcție visit generează codul intermediar aferent
 * unei construcții de limbaj CPLang în CFG-ul curent, și, în cazul expresiilor, întorc un simbol care reprezintă
 * rezultatul evaluării acelei expresii. Acel simbol poate fi:
 *
 * - un IntSymbol, în cazul literalilor întregi (e.g. 3)
 * - un IdSymbol aferent unei variabile din codul sursă (e.g. Int a)
 * - un IdSymbol aferent unui temporar modificabil generat de compilator (e.g. if1)
 * - un Instruction aferent unei instrucțiuni pure, identificată cu un temporar nemodificabil generat de compilator
 *   (e.g. add = ADD x y)
 */
public class IRGenVisitor implements ASTVisitor<Symbol> {
	/**
	 * CFG-ul corpului principal al programului
	 */
	public CFG mainCfg = new CFG();

	/**
	 * CFG-ul curent, care poate corespunde corpului principal (inițial, dar și pe parcurs) sau funcției curente în care
	 * ne aflăm
	 */
	CFG currentCfg = this.mainCfg;

	/**
	 * Mecanism pentru acces rapid la simbolurile funcțiilor și CFG-urile aferente
	 */
	public Map<String, FunctionSymbol> functionSymbols = new LinkedHashMap<>();

	@Override
	public Symbol visit(final ASTNode.Program program) {
		for (final ASTNode e : program.stmts)
			e.accept(this);

		return null;
	}

	/**
	 * Pentru un literal întreg se întoarce instanța unică de IntSymbol aferentă, fără generarea de instrucțiuni
	 * propriu-zise de cod intermediar
	 *
	 * @param  intLiteral nodul de AST
	 * @return      instanța de IntSymbol
	 */
	@Override
	public Symbol visit(final ASTNode.IntLiteral intLiteral) {
		return IntSymbol.get(Integer.parseInt(intLiteral.getToken().getText()));
	}

	/**
	 * Pentru o referire la o variabilă din codul sursă, se întoarce direct instanța de IdSymbol corespunzătoare,
	 * fără generarea de instrucțiuni propriu-zise de cod intermediar
	 *
	 * @param  id nodul de AST
	 * @return    instanța de IdSymbol
	 */
	@Override
	public Symbol visit(final ASTNode.Id id) {
		return id.getSymbol();
	}

	@Override
	public Symbol visit(final ASTNode.FloatLiteral floaty) {
		return null;
	}

	@Override
	public Symbol visit(final ASTNode.BoolLiteral boolLiteral) {
		return null;
	}

	@Override
	public Symbol visit(final ASTNode.UnaryMinus uMinus) {
		return null;
	}

	/**
	 * Pentru operațiile aritmetice binare, se generează o instrucțiune pură, cu rezultatul depus într-un temporar
	 * nemodificabil, identificat cu instrucțiunea, se adaugă instrucțiunea la CFG-ul curent și se întoarce ca rezultat.
	 *
	 * @param  arithmetic nodul de AST
	 * @return            instanța de Instruction
	 */
	@Override
	public Symbol visit(final ASTNode.Arithmetic arithmetic) {
		final var iType = switch (arithmetic.getToken().getType()) {
			case CPLangLexer.PLUS  -> InstructionType.ADD;
			case CPLangLexer.MINUS -> InstructionType.SUB;
			case CPLangLexer.MULT  -> InstructionType.MUL;
			case CPLangLexer.DIV   -> InstructionType.DIV;
			default                -> throw new UnsupportedOperationException();
		};

		final var instr = new Instruction(
				iType,
				iType.toString().toLowerCase(),
				arithmetic.left.accept(this),
				arithmetic.right.accept(this));
		this.currentCfg.addInstruction(instr);

		return instr;
	}

	@Override
	public Symbol visit(final ASTNode.Assign assign) {
		return this.visitInitAssign(assign.id.getSymbol(), assign.expr);
	}

	@Override
	public Symbol visit(final ASTNode.GlobalVarDef globalVarDef) {
		this.visitInitAssign(globalVarDef.id.getSymbol(), globalVarDef.initValue);
		return null;
	}

	/**
	 * La întâlnirea unei definiții de funcție, se demarează construcția unui nou CFG, memorat în simbolul funcției
	 * respective.
	 *
	 * @param  funcDef nodul de AST
	 * @return         null
	 */
	@Override
	public Symbol visit(final ASTNode.FuncDef funcDef) {
		// Se obține simbolul funcției
		final var funcSymbol = (FunctionSymbol)funcDef.id.getSymbol();
		this.functionSymbols.put(funcSymbol.getName(), funcSymbol);

		// Se realizează legătura dintre un nou CFG și simbolul funcției
		this.currentCfg = new CFG(funcSymbol);

		// Se generează cod pentru corpul funcției și valoarea acestuia devine operand pentru instrucțiunea RET
		final var retInstr = new Instruction(InstructionType.RET, (String) null, funcDef.body.accept(this));
		this.currentCfg.addInstruction(retInstr);

		// La finalul definiției, se revine în corpul principal al programului
		this.currentCfg = this.mainCfg;

		return null;
	}

	/**
	 * La întâlnirea unei expresii if, este necesară generarea unui temporar modificabil pentru valoarea expresiei,
	 * și generarea a trei blocuri noi, pentru ramurile then și else, și pentru blocul final care îi urmează if-ului.
	 *
	 * @param  iff nodul de AST
	 * @return     temporarul modificabil care reprezintă valoarea expresiei if
	 */
	@Override
	public Symbol visit(final ASTNode.If iff) {
		// Se generează un temporar modificabil care surprinde valoarea expresiei
		final var ifValue = new IdSymbol("if").applyNextVersion();

		// Se generează cod pentru evaluarea condiției
		final var condValue = iff.cond.accept(this);

		// Se generează cele 3 blocuri noi
		final var thenBlock = new BasicBlock("then");
		final var elseBlock = new BasicBlock("else");
		final var endBlock = new BasicBlock("ifEnd");

		// Se generează o instrucțiune de salt condiționat către blocurile then și else, în funcție de valoarea condiției
		this.currentCfg.addInstruction(new Instruction(InstructionType.BR, (String) null, condValue, thenBlock, elseBlock));

		// Se trece la blocul then
		this.currentCfg.addBlock(thenBlock);
		this.currentCfg.setCurrentBlock(thenBlock);

		// Se generează cod pentru ramura then în blocul then, iar valoarea ramurii este copiată în temporarul
		// modificabil. În final se adaugă un salt necondiționat către blocul final
		this.visitInitAssign(ifValue, iff.thenBranch);
		this.currentCfg.addInstruction(new Instruction(InstructionType.BR, (String) null, endBlock));

		// Se trece la blocul else
		this.currentCfg.addBlock(elseBlock);
		this.currentCfg.setCurrentBlock(elseBlock);

		// Se generează cod pentru ramura else în blocul else, iar valoarea ramurii este copiată în temporarul
		// modificabil. În final se adaugă un salt necondiționat către blocul final
		this.visitInitAssign(ifValue, iff.elseBranch);
		this.currentCfg.addInstruction(new Instruction(InstructionType.BR, (String) null, endBlock));

		// Se trece la blocul final, pentru ca următoarele instrucțiuni, aferente altor construcții din AST să fie
		// depuse în acesta
		this.currentCfg.addBlock(endBlock);
		this.currentCfg.setCurrentBlock(endBlock);

		// Valoarea expresiei if este în temporarul modificabil
		return ifValue;
	}

	/**
	 * La întâlnirea unei bucle for se generează două blocuri noi, primul, pentru corpul buclei și al doilea,
	 * care îi urmează buclei. Este singura construcție care introduce cicluri în CFG.
	 *
	 * @param  forr nodul de AST
	 * @return      null, considerăm că bucla nu întoarce ceva util
	 */
	@Override
	public Symbol visit(final ASTNode.For forr) {
		// Se generează cod pentru expresia de inițializare a buclei în blocul curent
		forr.init.accept(this);

		// Se generează cod pentru testarea inițială a condiției
		final var condValue = forr.cond.accept(this);

		// Se generează cele 2 blocuri noi
		final var bodyBlock = new BasicBlock("forBody");
		final var endBlock = new BasicBlock("forEnd");

		// Se generează un salt condiționat fie către corpul buclei, fie către blocul final (0 iterații), în funcție
		// de valoarea condiției
		this.currentCfg.addInstruction(new Instruction(InstructionType.BR, (String) null, condValue, bodyBlock, endBlock));

		// Trecem la corpul buclei
		this.currentCfg.addBlock(bodyBlock);
		this.currentCfg.setCurrentBlock(bodyBlock);

		// Generăm cod pentru corpul propriu-zis și pentru expresia de actualizare (de obicei, a variabilei de inducție,
		// e.g. i = i + 1)
		forr.body.accept(this);
		forr.step.accept(this);

		// Generăm cod din nou pentru testarea condiției
		final var innerCondValue = forr.cond.accept(this);

		// Generăm o instrucțiune de salt condiționat fie înapoi către corpul buclei, fie către blocul final,
		// în funcție de valoarea condiției
		this.currentCfg.addInstruction(new Instruction(InstructionType.BR, (String) null, innerCondValue, bodyBlock, endBlock));

		// Se trece la blocul final, pentru ca următoarele instrucțiuni, aferente altor construcții din AST să fie
		// depuse în acesta
		this.currentCfg.addBlock(endBlock);
		this.currentCfg.setCurrentBlock(endBlock);

		// TODO Ce ar putea întoarce un for?
		return IntSymbol.get(0);
	}

	/**
	 * Un apel de funcție generează o instrucțiune CALL, ai cărei operanzi sunt simbolul de funcție și valorile
	 * parametrilor actuali
	 *
	 * @param  call nodul de AST
	 * @return      instrucțiunea CALL
	 */
	@Override
	public Symbol visit(final ASTNode.Call call) {
		final var argSymbols = new ArrayList<Symbol>();
		// Primul operand este chiar simbolul funcției apelate
		argSymbols.add(call.id.getSymbol());

		// Ceilalți operanzi sunt valorile parametrilor actuali
		call.args.forEach(arg -> argSymbols.add(arg.accept(this)));

		// Se generează o instrucțiune CALL cu operanzii de mai sus
		final var callInstr = new Instruction(InstructionType.CALL,
				"call",
				argSymbols.toArray(new Symbol[argSymbols.size()]));
		this.currentCfg.addInstruction(callInstr);

		return callInstr;
	}

	@Override
	public Symbol visit(final ASTNode.Relational rel) {
		final var instructionType = switch (rel.getToken().getText()) {
			case "==" -> InstructionType.CMP_EQ;
			case "<" -> InstructionType.CMP_LT;
			case "<=" -> InstructionType.CMP_LE;
			default -> throw new UnsupportedOperationException();
		};

		final var relInstr = new Instruction(instructionType,
				"rel",
				rel.left.accept(this),
				rel.right.accept(this));
		this.currentCfg.addInstruction(relInstr);

		return relInstr;
	}

	@Override
	public Symbol visit(final ASTNode.Type type) {
		return null;
	}

	@Override
	public Symbol visit(final ASTNode.FormalDef formalDef) {
		return null;
	}

	/**
	 * Definiție de variabilă locală, după extinderea CPLang
	 *
	 * @param  localVarDef nodul de AST
	 * @return       null
	 */
	@Override
	public Symbol visit(final ASTNode.LocalVarDef localVarDef) {
		// Fiecare variabilă locală trebuie redenumită, pentru a evita confuzia de nume a unor variabile cu același
		// nume din scope-uri imbricate, care ar apărea ca aceeași variabilă în forma liniară a codului intermediar!
		this.visitInitAssign(localVarDef.id.getSymbol().applyNextVersion(), localVarDef.initValue);
		return null;
	}

	/**
	 * Valoarea unui bloc este valoarea ultimei expresii
	 *
	 * @param  block nodul de AST
	 * @return       valoarea ultimei expresii
	 */
	@Override
	public Symbol visit(final ASTNode.Block block) {
		Symbol value = null;

		for (final var stmt : block.stmts)
			value = stmt.accept(this);

		return value;
	}

	/**
	 * Funcție generală petru vizitarea definițiilor de variabilă globală sau locală cu inițializare, și a atribuirilor.
	 * Generează întotdeauna o instrucțiune distructivă, ce utilizează simbolul variabilei către care se realizează
	 * atribuirea drept rezultat modificabil, și o adaugă la CFG-ul curent.
	 *
	 * @param  id   simbolul aferent variabilei din codul sursă către care se realizează atribuirea
	 * @param  expr expresia de inițializare/atribuire ca nod de AST
	 * @return      rezultatul modificabil al instrucțiunii distructive
	 */
	protected Symbol visitInitAssign(final IdSymbol id, final ASTNode.Expression expr) {
		// Dacă nu există expresie de inițializare/atribuire, inițializăm cu 0
		// (presupunem că toți operanzii sunt Int)
		if (expr == null) {
			final var copyInstr = new Instruction(
					InstructionType.COPY,
					id,
					IntSymbol.get(0));
			this.currentCfg.addInstruction(copyInstr);

			return null;
		}

		// Determinăm rezultatul expresiei de inițializare
		final var value = expr.accept(this);

		// Dacă a fost necesară producerea unei instrucțiuni pure pentru evaluarea expresiei, o transformăm
		// într-o variantă distructivă, care modifică direct variabila de interes pentru noi, ignorând temporarul
		// generat anterior
		if (value instanceof Instruction)
			((Instruction) value).toDestructive(id);
		// Dacă rezultatul expresiei este deja prezent într-un simbol extern, generăm o instrucțiune distructivă
		// de copiere către variabila de interes pentru noi
		else {
			final var copyInstr = new Instruction(
					InstructionType.COPY,
					id,
					value);
			this.currentCfg.addInstruction(copyInstr);
		}

		// În ambele cazuri, întoarcem rezultatul operațiilor distructive. Acest lucru este necesar dacă vom folosi
		// valoarea atribuirii într-o altă expresie.
		return id;
	}
}
