package edu.rosehulman.minijavac.ast;

import java.util.ArrayList;
import java.util.List;

import edu.rosehulman.minijavac.typechecker.Scope;

public class BinaryOperation implements Expression {
    public final BinaryOperator operator;
    public final Expression left;
    public final Expression right;

    @Override
    public List<String> typecheck(Scope scope) {
        List<String> errors = new ArrayList<>();
        if (operator == BinaryOperator.EQ || operator == BinaryOperator.NEQ) {
            errors.addAll(left.typecheck(scope));
            errors.addAll(right.typecheck(scope));
            String leftType = left.getType(scope);
            String rightType = right.getType(scope);
            if (!left.equals(right)) {
                errors.add("The operand types, " + leftType + " and " + rightType + ", are not compatible for equality comparison");
            }
        } else {
            if (!left.getType(scope).equals(operator.operandType)) {
                errors.add("Left argument of type " + left.getType(scope) + " does not match expected type " +
                        operator.operandType + " for operator " + operator.name());
            }
            errors.addAll(left.typecheck(scope));
            if (!right.getType(scope).equals(operator.operandType)) {
                errors.add("Right argument of type " + right.getType(scope) + " does not match expected type " +
                        operator.operandType + " for operator " + operator.name());
            }
            errors.addAll(right.typecheck(scope));
        }
        return errors;
    }

    @Override
    public String getType(Scope scope) {
        return operator.returnType;
    }

    public enum BinaryOperator {
        PLUS("int", "int"),
        MINUS("int", "int"),
        MULTIPLY("int", "int"),
        DIVIDE("int", "int"),
        LT("int", "boolean"),
        LTE("int", "boolean"),
        GT("int", "boolean"),
        GTE("int", "boolean"),
        AND("boolean", "boolean"),
        OR("boolean", "boolean"),
        EQ("", "boolean"),
        NEQ("", "boolean");

        String operandType;
        String returnType;

        BinaryOperator(String operandType, String returnType) {
            this.operandType = operandType;
            this.returnType = returnType;
        }
    }

    public BinaryOperation(BinaryOperator operator, Expression left, Expression right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }
}
