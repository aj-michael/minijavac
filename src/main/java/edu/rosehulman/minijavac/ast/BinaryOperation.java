package edu.rosehulman.minijavac.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.rosehulman.minijavac.generator.ConstantPool;
import edu.rosehulman.minijavac.typechecker.Scope;
import edu.rosehulman.minijavac.typechecker.Type;

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
            Type leftType = left.getType(scope);
            Type rightType = right.getType(scope);
            if (leftType.isPrimitiveType() ^ rightType.isPrimitiveType()) {
                errors.add("The operand types, " + leftType + " and " + rightType + ", are not compatible for equality comparison");
            }
        } else {
            if (!left.getType(scope).isA(operator.operandType, scope)) {
                errors.add("Left argument of type " + left.getType(scope) + " does not match expected type " +
                        operator.operandType + " for operator " + operator.name());
            }
            errors.addAll(left.typecheck(scope));
            if (!right.getType(scope).isA(operator.operandType, scope)) {
                errors.add("Right argument of type " + right.getType(scope) + " does not match expected type " +
                        operator.operandType + " for operator " + operator.name());
            }
            errors.addAll(right.typecheck(scope));
        }
        return errors;
    }

    @Override
    public Type getType(Scope scope) {
        return operator.returnType;
    }

    @Override
    public List<Byte> generateCode(ConstantPool cp, Map<String, Integer> variables) {
        ArrayList<Byte> bytes = new ArrayList<>();
        bytes.addAll(left.generateCode(cp, variables));
        bytes.addAll(right.generateCode(cp, variables));
        bytes.add(operator.byteCode);
        return bytes;
    }

    public enum BinaryOperator {
        PLUS(Type.INT, Type.INT, (byte) 96),
        MINUS(Type.INT, Type.INT, (byte) 100),
        MULTIPLY(Type.INT, Type.INT, (byte) 104),
        DIVIDE(Type.INT, Type.INT, (byte) 108),
        LT(Type.INT, Type.BOOLEAN, (byte) 0), // TODO find me an opcode
        LTE(Type.INT, Type.BOOLEAN, (byte) 0),
        GT(Type.INT, Type.BOOLEAN, (byte) 0),
        GTE(Type.INT, Type.BOOLEAN, (byte) 0),
        AND(Type.BOOLEAN, Type.BOOLEAN, (byte) 0),
        OR(Type.BOOLEAN, Type.BOOLEAN, (byte) 0),
        EQ(null, Type.BOOLEAN, (byte) 0),
        NEQ(null, Type.BOOLEAN, (byte) 0);

        Type operandType;
        Type returnType;
        byte byteCode;

        BinaryOperator(Type operandType, Type returnType, byte byteCode) {
            this.operandType = operandType;
            this.returnType = returnType;
            this.byteCode = byteCode;
        }
    }

    public BinaryOperation(BinaryOperator operator, Expression left, Expression right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }
}
