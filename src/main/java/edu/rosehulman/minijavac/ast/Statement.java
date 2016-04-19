package edu.rosehulman.minijavac.ast;

import edu.rosehulman.minijavac.typechecker.Scope;

import java.util.List;

public interface Statement {
    List<String> typecheck(Scope scope);
}