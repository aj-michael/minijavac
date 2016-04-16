package edu.rosehulman.minijavac.ast;

import edu.rosehulman.minijavac.typechecker.Scope;

import java.util.ArrayList;
import java.util.List;

public class VariableInvocation implements LiteralExpression {
    public final String name;

    public VariableInvocation(String name) {
        this.name = name;
    }

    @Override
    public List<String> typecheck(Scope scope) {
        List<String> errors = new ArrayList<>();
        if(scope.containsVariable(name)) {
            errors.add("No variable named " + name + " exists in the current scope.");
        }
        return errors;
    }

    @Override
    public String getType(Scope scope) {
        return scope.getVariableType(name);
    }
}
