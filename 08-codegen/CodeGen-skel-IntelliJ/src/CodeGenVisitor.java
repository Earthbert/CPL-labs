import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

public class CodeGenVisitor implements ASTVisitor<ST>{
	static STGroupFile templates = new STGroupFile("cgen.stg");
	
	ST mainSection;	// filled directly (through visitor returns)
	ST dataSection; // filled collaterally ("global" access)
	ST funcSection; // filled collaterally ("global" access)

	/* 
	 * Plain numbers
	 * TODO 1:
	 */

    @Override
    public ST visit(Int val) {
        return templates.getInstanceOf("literal")
                .add("value", val.getToken().getText());
    }
    
    @Override
    public ST visit(FloatNum val) {
        return null;
    }

    @Override
    public ST visit(Bool val) {
        return null;
    }
    
    /* 
     * Unary operations
     * TODO 1:
     */
    
	@Override
	public ST visit(UnaryMinus uMinus) {
		return null;
	}
    
	/* 
	 * Binary operations
	 * TODO 2:
	 */
    
    @Override
    public ST visit(Plus expr) {
    	var st = templates.getInstanceOf("plus");
    	st.add("e1", expr.left.accept(this))
    		.add("e2",  expr.right.accept(this))
    		.add("dStr", expr.debugStr);
    	
    	return st;
    }
    
    @Override
    public ST visit(Minus expr) {
    	return null;
    }
    
    @Override
    public ST visit(Mult expr) {
    	return null;
    }
    
    @Override
    public ST visit(Div expr) {
    	return null;
    }
	
	@Override
	public ST visit(Relational expr) {
		return null;
	}

    /*
     * Control structures
     * TODO 3:
     */
    
    @Override
	public ST visit(If iff) {
		return null;
	}

	@Override
	public ST visit(Call call) {
		return null;
	}

    /*
     * Definitions & assignments
     * TODO 4&5:
     */

	@Override
	public ST visit(Assign assign) {
		// TODO 4: generare cod pentru main()
		return null;
	}

	@Override
	public ST visit(VarDef varDef) {
		// TODO 4: generare cod pentru main() și etichetă în .data
		return null;
	}

	@Override
	public ST visit(FuncDef funcDef) {
		// TODO 5: generare cod pentru funcSection. Fără cod în main()!
		return null;
	}

	/*
	 * META
	 */
	
	@Override
	public ST visit(Id id) {
		// TODO 5
		return null;
	}

	@Override
	public ST visit(Formal formal) {
		// TODO 5
		return null;
	}
	
	@Override
	public ST visit(Type type) {
		return null;
	}

	@Override
	public ST visit(Program program) {
		dataSection = templates.getInstanceOf("sequenceSpaced");
		funcSection = templates.getInstanceOf("sequenceSpaced");
		mainSection = templates.getInstanceOf("sequence");
		
		for (ASTNode e : program.stmts)
			mainSection.add("e", e.accept(this));
		
		//assembly-ing it all together. HA! get it?
		var programST = templates.getInstanceOf("program");
		programST.add("data", dataSection);
		programST.add("textFuncs", funcSection);
		programST.add("textMain", mainSection);
		
		return programST;
	}

}
