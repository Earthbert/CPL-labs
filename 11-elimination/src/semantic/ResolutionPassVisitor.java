package semantic;

import org.antlr.v4.runtime.Token;

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
        return id.symbol.getType();
    }

    @Override
    public TypeSymbol visit(final ASTNode.GlobalVarDef varDef) {

        return this.processVarStructure(varDef.id, varDef.initValue);
    }


    @Override
    public TypeSymbol visit(final ASTNode.LocalVarDef varDef) {

        return this.processVarStructure(varDef.id, varDef.initValue);
    }

    boolean valuesCompatible(final TypeSymbol decalred, final TypeSymbol actual)
    {
        if (decalred == TypeSymbol.BOOL && actual != TypeSymbol.BOOL)
            return false;
        if (decalred != TypeSymbol.BOOL && actual == TypeSymbol.BOOL)
            return false;
        if (decalred == TypeSymbol.INT && actual != TypeSymbol.INT)
            return false;
        return true;
    }

    @Override
    public TypeSymbol visit(final ASTNode.FuncDef funcDef) {
        final var returnType = funcDef.id.getSymbol().getType();
        final var bodyType = funcDef.body.accept(this);

        // TODO 6: Verificăm dacă tipul de retur declarat este compatibil
        // cu cel al corpului.

        if (bodyType != null && !this.valuesCompatible(returnType, bodyType)) { // int = float
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

        if (formals.size() != call.args.size()) {
            ASTVisitor.error(call.getToken(),
                    call.id.getToken().getText() +
                    " applied to wrong number of arguments");
        } else {
            var index = 0;
            final var formalIterator = formals.values().iterator();
            final var actualIterator = call.args.iterator();

            while (formalIterator.hasNext()) {
                index++;

                final var formal = formalIterator.next();
                final var actual = actualIterator.next();

                final var formalType = ((IdSymbol)formal).getType();
                final var actualType = actual.accept(this);

                if (actualType == null) {
                    continue;
                }

                if (!this.valuesCompatible(formalType, actualType)) {
                    ASTVisitor.error(actual.getToken(), "Argument " + index + " of " + call.id.getToken().getText() + " has wrong type");
                }
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

        if (exprType != null && !this.valuesCompatible(idType, exprType)) {
            ASTVisitor.error(assign.expr.getToken(), "Assignment with incompatible types");
        }

        return null;
    }

    @Override
    public TypeSymbol visit(final ASTNode.If iff) {
        final var condType = iff.cond.accept(this);
        final var thenType = iff.thenBranch.accept(this);
        final var elseType = iff.elseBranch.accept(this);

        // TODO 4: Verificați tipurile celor 3 componente, afișați eroare
        // dacă este cazul, și precizați tipul expresiei.

        if (condType != null && condType != TypeSymbol.BOOL) {
            ASTVisitor.error(iff.cond.getToken(), "Condition of if expression has type other than Bool");
        }

        if (thenType == null || elseType == null) {
            return null;
        }

        if ((thenType == TypeSymbol.BOOL && elseType != TypeSymbol.BOOL) ||
            (elseType == TypeSymbol.BOOL && thenType != TypeSymbol.BOOL)) {
            ASTVisitor.error(iff.getToken(), "Branches of if expression have incompatible types");
            return null;
        }

        if (thenType == TypeSymbol.FLOAT || elseType == TypeSymbol.FLOAT) {
            return TypeSymbol.FLOAT;
        }

        return thenType;
    }

    @Override
    public TypeSymbol visit(final ASTNode.For aFor) {
        aFor.init.accept(this);

        final var condType = aFor.cond.accept(this);
        // TODO 5: Verificați tipul conditiei si afișați eroare dacă este cazul
        if (condType != null && condType != TypeSymbol.BOOL) {
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

        if (exprType == TypeSymbol.BOOL) {
            ASTVisitor.error(uMinus.getToken(), "Unary minus applied to Bool");
            return null;
        }

        return exprType;
    }

    @Override
    public TypeSymbol visit(final ASTNode.Arithmetic op) {
        // TODO 3: Verificăm tipurile operanzilor, afișăm eroare dacă e cazul,
        // și întoarcem tipul expresiei.
        // Puteți afla felul operației din relational.getToken().getType(),
        // pe care îl puteți compara cu CPLangLexer.EQUAL etc.

        return this.checkBinaryOpTypes(op.getToken(), op.left, op.right);

    }

    @Override
    public TypeSymbol visit(final ASTNode.Relational op) {
        // TODO 3: Verificăm tipurile operanzilor, afișăm eroare dacă e cazul,
        // și întoarcem tipul expresiei.
        // Puteți afla felul operației din relational.getToken().getType(),
        // pe care îl puteți compara cu CPLangLexer.EQUAL etc.

        this.checkBinaryOpTypes(op.getToken(), op.left, op.right);
        return TypeSymbol.BOOL;
    }

    TypeSymbol checkBinaryOpTypes(final Token token, final ASTNode.Expression e1, final ASTNode.Expression e2) {
        final var type1 = e1.accept(this);
        final var type2 = e2.accept(this);


        if (type1 == TypeSymbol.BOOL) {
            ASTVisitor.error(token, "Left operand of " + token.getText() + " has type Bool");
        }
        if (type2 == TypeSymbol.BOOL) {
            ASTVisitor.error(token, "Right operand of " + token.getText() + " has type Bool");
        }

        if (type1 == null || type2 == null || type1 == TypeSymbol.BOOL || type2 == TypeSymbol.BOOL)
            return null;

        if (type1 == TypeSymbol.FLOAT || type2 == TypeSymbol.FLOAT)
            return TypeSymbol.FLOAT;

        return TypeSymbol.INT;
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

            if (initType == null)
                return null;

            if (declaredType != null && !this.valuesCompatible(declaredType, initType)) {
                ASTVisitor.error(initValue.getToken(),
                        "Type of initialization expression does not match variable " + "type");
            }
        }

        // Tipul unei definiții ca instrucțiune în sine nu este relevant.
        return null;
    }

}
