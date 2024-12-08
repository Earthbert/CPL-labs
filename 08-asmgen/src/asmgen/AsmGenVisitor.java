package asmgen;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import parser.ASTNode;
import parser.ASTNode.TwoOp;
import parser.ASTVisitor;
import semantic.TypeSymbol;

public class AsmGenVisitor implements ASTVisitor<ST> {
	static STGroupFile templates = new STGroupFile("asmgen/asmgen.stg");
	Integer idx = 0;

	ST mainSection; // filled directly (through visitor returns)
	ST dataSection; // filled collaterally ("global" access)
	ST funcSection; // filled collaterally ("global" access)

	/*
	 * TODO 1: Plain numbers
	 */

	@Override
	public ST visit(final ASTNode.IntLiteral val) {
		return templates.getInstanceOf("literal")
				.add("value", val.getToken().getText());
	}

	@Override
	public ST visit(final ASTNode.FloatLiteral val) {
		return templates.getInstanceOf("fliteral")
				.add("value", val.getToken().getText());
	}

	@Override
	public ST visit(final ASTNode.BoolLiteral val) {
		return templates.getInstanceOf("literal")
				.add("value", "true".equals(val.getToken().getText()) ? 1 : 0);
	}

	/*
	 * TODO 1: Unary operations
	 */

	@Override
	public ST visit(final ASTNode.UnaryMinus uMinus) {
		return templates.getInstanceOf("uMinus")
				.add("e", uMinus.expr.accept(this));
	}

	/*
	 * TODO 2: Binary operations
	 */

	@Override
	public ST visit(final ASTNode.Arithmetic expr) {
		return this.visit2Op(expr);
	}

	@Override
	public ST visit(final ASTNode.Relational expr) {
		return this.visit2Op(expr);
	}

	private ST visit2Op(final TwoOp expr) {

		if (expr.leftType == TypeSymbol.FLOAT || expr.rightType == TypeSymbol.FLOAT) {
			return this.visit2OpFloat(expr);
		}

		final var op = switch (expr.getToken().getText()) {
			case "+" -> "add";
			case "-" -> "sub";
			case "*" -> "mul";
			case "/" -> "div";
			case "==" -> "seq";
			case "<" -> "slt";
			case "<=" -> "sle";
			default -> "";
		};

		final var st = templates.getInstanceOf("binaryOp");
		st.add("e1", expr.left.accept(this))
				.add("e2", expr.right.accept(this))
				.add("op", op)
				.add("dStr", expr.debugStr);

		return st;
	}

	private ST visit2OpFloat(final TwoOp expr) {
		final var op = switch (expr.getToken().getText()) {
			case "+" -> "add.s";
			case "-" -> "sub.s";
			case "*" -> "mul.s";
			case "/" -> "div.s";
			case "==" -> "c.eq.s";
			case "<" -> "c.lt.s";
			case "<=" -> "c.le.s";
			default -> "";
		};

		final var st = templates.getInstanceOf("fbinaryOp");
		st.add("e1", expr.left.accept(this))
				.add("e2", expr.right.accept(this))
				.add("op", op)
				.add("dStr", expr.debugStr)
				.add("c1", expr.leftType == TypeSymbol.INT)
				.add("c2", expr.rightType == TypeSymbol.INT);

		return st;
	}

	/*
	 * TODO 3: Control structures
	 */

	@Override
	public ST visit(final ASTNode.If iff) {
		return templates.getInstanceOf("iff")
				.add("cond", iff.cond.accept(this))
				.add("thenBranch", iff.thenBranch.accept(this))
				.add("elseBranch", iff.elseBranch.accept(this))
				.add("idx", this.idx++);
	}

	@Override
	public ST visit(final ASTNode.Call call) {
		
		return null;
		// return templates.getInstanceOf("call")
		// 		.add("name", call.id.getSymbol().getName())
		// 		.add("params", call.args.stream().map(a -> a.accept(this)).toList().reversed());
	}

	/*
	 * TODO 4: Variable definitions, assignments and blocks
	 */

	@Override
	public ST visit(final ASTNode.GlobalVarDef varDef) {
		// TODO 4: generare cod pentru main și etichetă în .data
		return null;
	}

	@Override
	public ST visit(final ASTNode.LocalVarDef localVarDef) {
		// TODO 4: generare cod în blocul curent
		return null;
	}

	@Override
	public ST visit(final ASTNode.Assign assign) {
		return null;
	}

	/*
	 * TODO 4: Blocks
	 */

	@Override
	public ST visit(final ASTNode.Block block) {
		return null;
	}

	/*
	 * TODO 5: Function definitions
	 */

	@Override
	public ST visit(final ASTNode.FuncDef funcDef) {
		// TODO 5: generare cod pentru funcSection. Fără cod în main()!
		return null;
	}

	@Override
	public ST visit(final ASTNode.FormalDef formal) {
		// TODO 5
		return null;
	}

	/*
	 * TODO 5: Variable references
	 */

	@Override
	public ST visit(final ASTNode.Id id) {
		// TODO 5
		return null;
	}

	/*
	 * TODO 6: For
	 */

	@Override
	public ST visit(final ASTNode.For aFor) {
		return null;
	}

	@Override
	public ST visit(final ASTNode.Type type) {
		return null;
	}

	@Override
	public ST visit(final ASTNode.Program program) {
		this.dataSection = templates.getInstanceOf("sequenceSpaced");
		this.funcSection = templates.getInstanceOf("sequenceSpaced");
		this.mainSection = templates.getInstanceOf("sequence");

		for (final ASTNode e : program.stmts)
			this.mainSection.add("e", e.accept(this));

		// assembly-ing it all together. HA! get it?
		final var programST = templates.getInstanceOf("program");
		programST.add("data", this.dataSection);
		programST.add("textFuncs", this.funcSection);
		programST.add("textMain", this.mainSection);

		return programST;
	}

}
