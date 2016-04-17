package edu.rosehulman.minijavac.ast;

import java.util.ArrayList;
import java.util.List;

import edu.rosehulman.minijavac.typechecker.Scope;

public class IfStatement implements Statement {
    public final Expression condition;
    public final Statement trueStatement;
    public final Statement falseStatement;

    public IfStatement(Expression condition, Statement trueStatement, Statement falseStatement) {
        this.condition = condition;
        this.trueStatement = trueStatement;
        this.falseStatement = falseStatement;
    }

    @Override
    public List<String> typecheck(Scope scope) {
        List<String> errors = new ArrayList<>();

        String conditionType = condition.getType(scope);
        if (!conditionType.equals("boolean")) {
            errors.add("Condition for if statement is of type " + conditionType + " instead of boolean");
        }

        errors.addAll(condition.typecheck(scope));
        errors.addAll(trueStatement.typecheck(scope));
        errors.addAll(falseStatement.typecheck(scope));
        return errors;
    }
}
