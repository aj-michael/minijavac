package edu.rosehulman.minijavac.ast;

import java.util.ArrayList;
import java.util.List;

import edu.rosehulman.minijavac.typechecker.Scope;
import edu.rosehulman.minijavac.typechecker.Type;

public class LiteralValue implements LiteralExpression {
    public final Type type;
    public final Object value;

    public LiteralValue(String type, Object value) {
        this.type = new Type(type);
        this.value = value;
    }

    @Override
    public List<String> typecheck(Scope scope) {
        List<String> errors = new ArrayList<>();
        return errors;
    }

    @Override
    public Type getType(Scope scope) {
        return type;
    }
}