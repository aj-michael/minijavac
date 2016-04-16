package edu.rosehulman.minijavac.ast;

import com.google.common.collect.ImmutableList;
import edu.rosehulman.minijavac.typechecker.Scope;

import java.util.ArrayList;
import java.util.List;

public class PrintlnStatement implements Statement {
    public final Expression expression;

    public PrintlnStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public List<Statement> getSubstatements() {
        return ImmutableList.of();
    }

    @Override
    public List<String> typecheck(Scope scope) {
        List<String> errors = new ArrayList<>();
        return errors;
    }
}
