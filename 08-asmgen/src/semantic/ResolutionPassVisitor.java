package semantic;

import java.util.List;

import parser.ASTNode;
import parser.ASTVisitor;

public class ResolutionPassVisitor implements ASTVisitor<TypeSymbol> {
    @Override
    public TypeSymbol visit(final ASTNode.Program prog) {
        for (final var stmt : prog.stmts) {
            stmt.accept(this);
        }
        return null;
    }

    @Override
    public TypeSymbol visit(final ASTNode.Id id) {
        // Verificăm dacă într-adevăr avem de-a face cu o variabilă
        // sau cu o funcție, al doilea caz constituind eroare.
        // Puteți folosi instanceof.
        final var symbol = id.getScope().lookup(id.getToken().getText());

        if (symbol instanceof FunctionSymbol) {
            ASTVisitor.error(id.getToken(), id.getToken().getText() + " is not a variable");
            return null;
        }

        // TODO 2: Întoarcem informația de tip salvată deja în simbol încă de la
        // definirea variabilei.
        return ((IdSymbol) symbol).getType();
    }

    @Override
    public TypeSymbol visit(final ASTNode.GlobalVarDef varDef) {

        return this.processVarStructure(varDef.id, varDef.initValue);
    }

    @Override
    public TypeSymbol visit(final ASTNode.LocalVarDef varDef) {

        return this.processVarStructure(varDef.id, varDef.initValue);
    }

    @Override
    public TypeSymbol visit(final ASTNode.FuncDef funcDef) {
        final var returnType = funcDef.id.getSymbol().getType();
        final var bodyType = funcDef.body.accept(this);

        // TODO 6: Verificăm dacă tipul de retur declarat este compatibil
        // cu cel al corpului.
        if (returnType != bodyType) {
            ASTVisitor.error(funcDef.body.getToken(), "Return type does not match body type");
        }

        // Tipul unei definiții ca instrucțiune în sine nu este relevant.
        return null;
    }

    @Override
    public TypeSymbol visit(final ASTNode.Block block) {
        TypeSymbol lastType = null;
        for (final var stmt : block.stmts) {
            lastType = stmt.accept(this);
        }
        // tipul unui bloc este tipul ultimei expresii din acesta
        return lastType;
    }

    @Override
    public TypeSymbol visit(final ASTNode.Call call) {
        // Verificăm dacă funcția există în scope. Nu am putut face
        // asta în prima trecere din cauza a forward references.
        //
        // De asemenea, verificăm că Id-ul pe care se face apelul de funcție
        // este, într-adevăr, o funcție și nu o variabilă.
        //
        // Hint: pentru a obține scope-ul, putem folosi call.id.getScope(),
        // setat la trecerea anterioară.
        final var id = call.id;
        final var symbol = id.getScope().lookup(id.getToken().getText());

        if (symbol == null) {
            ASTVisitor.error(id.getToken(), id.getToken().getText() + " function undefined");
            return null;
        }

        if (!(symbol instanceof final FunctionSymbol functionSymbol)) {
            ASTVisitor.error(id.getToken(), id.getToken().getText() + " is not a function");
            return null;
        }

        id.setSymbol(functionSymbol);

        // TODO 7: Verificați dacă numărul parametrilor actuali coincide
        // cu cel al parametrilor formali, și că tipurile sunt compatibile.

        final var formals = functionSymbol.getFormals();

        final var params = call.args.stream().map(arg -> arg.accept(this)).toList();

        if (formals.size() != params.size()) {
            ASTVisitor.error(call.getToken(), call.id.getToken().getText() + " applied to wrong number of arguments");
            return null;
        }

        final var formalTypes = formals.values().stream().map(sym -> ((IdSymbol) sym).getType()).toList();

        for (int i = 0; i < params.size(); i++) {
            if (!formalTypes.get(i).equals(params.get(i))) {
                ASTVisitor.error(call.args.get(i).getToken(),
                        "Argument " + (i + 1) + " of " + call.id.getToken().getText() + " has wrong type");
            }
        }

        return functionSymbol.getType();
    }

    @Override
    public TypeSymbol visit(final ASTNode.Assign assign) {
        final var idType = assign.id.accept(this);
        final var exprType = assign.expr.accept(this);

        // TODO 6: Verificăm dacă expresia cu care se realizează atribuirea
        // are tipul potrivit cu cel declarat pentru variabilă.
        if (!idType.equals(exprType)) {
            ASTVisitor.error(assign.expr.getToken(), "Assignment with incompatible types");
        }

        return exprType;
    }

    @Override
    public TypeSymbol visit(final ASTNode.If iff) {
        final var condType = iff.cond.accept(this);
        final var thenType = iff.thenBranch.accept(this);
        final var elseType = iff.elseBranch.accept(this);

        // TODO 4: Verificați tipurile celor 3 componente, afișați eroare
        // dacă este cazul, și precizați tipul expresiei.

        if (thenType == null || elseType == null) {
            ASTVisitor.error(iff.getToken(), "No return type in if branches");
        }

        if (!TypeSymbol.BOOL.equals(condType)) {
            ASTVisitor.error(iff.cond.getToken(), "Condition of if expression has type other than Bool");
        }

        if (thenType == TypeSymbol.FLOAT || elseType == TypeSymbol.FLOAT) {
            return TypeSymbol.FLOAT;
        }

        if (!thenType.equals(elseType)) {
            ASTVisitor.error(iff.getToken(), "Branches of if expression have incompatible types");
        }

        return thenType;
    }

    @Override
    public TypeSymbol visit(final ASTNode.For aFor) {
        aFor.init.accept(this);

        final var condType = aFor.cond.accept(this);
        // TODO 5: Verificați tipul conditiei si afișați eroare dacă este cazul
        if (!TypeSymbol.BOOL.equals(condType)) {
            ASTVisitor.error(aFor.cond.getToken(), "Stop condition of for expression has type other than Bool");
        }

        aFor.step.accept(this);
        aFor.body.accept(this);

        // Prin convenție, valoarea buclei for este 0.
        return TypeSymbol.INT;
    }

    @Override
    public TypeSymbol visit(final ASTNode.Type type) {
        return null;
    }

    @Override
    public TypeSymbol visit(final ASTNode.FormalDef formal) {
        return formal.id.getSymbol().getType();
    }

    // Operații aritmetice si relationale.
    @Override
    public TypeSymbol visit(final ASTNode.UnaryMinus uMinus) {
        final var exprType = uMinus.expr.accept(this);

        // TODO 3: Verificăm tipul operandului, afișăm eroare dacă e cazul,
        // și întoarcem tipul expresiei.
        final List<TypeSymbol> expected = List.of(TypeSymbol.INT, TypeSymbol.FLOAT);
        if (!expected.contains(exprType)) {
            ASTVisitor.error(uMinus.getToken(), "Incompatible type for unary minus");
        }

        return exprType;
    }

    @Override
    public TypeSymbol visit(final ASTNode.Arithmetic op) {
        // TODO 3: Verificăm tipurile operanzilor, afișăm eroare dacă e cazul,
        // și întoarcem tipul expresiei.
        // Puteți afla felul operației din relational.getToken().getType(),
        // pe care îl puteți compara cu CPLangLexer.EQUAL etc.
        final var left = op.left.accept(this);
        final var right = op.right.accept(this);

        final List<TypeSymbol> expected = List.of(TypeSymbol.INT, TypeSymbol.FLOAT);
        if (!expected.contains(left)) {
            ASTVisitor.error(op.getToken(), "Left operand of " + op.getToken().getText() + " has type " + left);
        }

        if (!expected.contains(right)) {
            ASTVisitor.error(op.getToken(), "Right operand of " + op.getToken().getText() + " has type " + right);
        }

        op.leftType = left;
        op.rightType = right;

        return left == TypeSymbol.FLOAT || right == TypeSymbol.FLOAT ? TypeSymbol.FLOAT : TypeSymbol.INT;
    }

    @Override
    public TypeSymbol visit(final ASTNode.Relational op) {
        // TODO 3: Verificăm tipurile operanzilor, afișăm eroare dacă e cazul,
        // și întoarcem tipul expresiei.
        // Puteți afla felul operației din relational.getToken().getType(),
        // pe care îl puteți compara cu CPLangLexer.EQUAL etc.
        final var leftType = op.left.accept(this);
        final var rightType = op.right.accept(this);

        final List<TypeSymbol> expected = List.of(TypeSymbol.INT, TypeSymbol.FLOAT);
        if (!expected.contains(leftType)) {
            ASTVisitor.error(op.getToken(),
                    "Left operand of " + op.getToken().getText() + " has type " + leftType);
        }

        if (!expected.contains(rightType)) {
            ASTVisitor.error(op.getToken(),
                    "Right operand of " + op.getToken().getText() + " has type " + rightType);
        }

        op.leftType = leftType;
        op.rightType = rightType;

        return TypeSymbol.BOOL;
    }

    // Tipurile de bază
    @Override
    public TypeSymbol visit(final ASTNode.IntLiteral intt) {
        // TODO 2: Întoarcem tipul corect.
        return TypeSymbol.INT;
    }

    @Override
    public TypeSymbol visit(final ASTNode.BoolLiteral bool) {
        // TODO 2: Întoarcem tipul corect.
        return TypeSymbol.BOOL;
    }

    @Override
    public TypeSymbol visit(final ASTNode.FloatLiteral floatNum) {
        // TODO 2: Întoarcem tipul corect.
        return TypeSymbol.FLOAT;
    }

    TypeSymbol processVarStructure(final ASTNode.Id id, final ASTNode.Expression initValue) {

        final TypeSymbol declaredType = id.getSymbol().getType();
        if (initValue != null) {

            final TypeSymbol initType = initValue.accept(this);

            // TODO 6: Verificăm dacă expresia de inițializare are tipul potrivit
            // cu cel declarat pentru variabilă.
            if (!declaredType.equals(initType)) {
                ASTVisitor.error(initValue.getToken(),
                        "Type of initialization expression does not match variable type");
            }
        }

        // Tipul unei definiții ca instrucțiune în sine nu este relevant.
        return null;
    }
}
