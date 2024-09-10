package edu.robertob.p1compi2.engine.expressions;

import edu.robertob.p1compi2.engine.base.ComparisonOperators;
import edu.robertob.p1compi2.engine.base.Statement;
import edu.robertob.p1compi2.engine.structs.PError;
import edu.robertob.p1compi2.engine.structs.SymbolTable;
import edu.robertob.p1compi2.engine.structs.Tree;
import edu.robertob.p1compi2.engine.structs.TypesTable;

public class Comparison extends Statement {
    private Statement leftCondition;
    private Statement rightCondition;
    private ComparisonOperators operator;

    public Comparison(Statement leftCondition, Statement rightCondition, ComparisonOperators operator, int line, int column) {
        super(TypesTable.DefaultTypes.BOOLEAN.id, line, column);
        this.leftCondition = leftCondition;
        this.rightCondition = rightCondition;
        this.operator = operator;
    }

    @Override
    public Object execute(Tree tree, SymbolTable table, TypesTable typesTable) {
        Object left = leftCondition.execute(tree, table, typesTable);
        if (left instanceof PError) return left;

        Object right = rightCondition.execute(tree, table, typesTable);
        if (right instanceof PError) return right;

        TypeHelper typeHelper = resolveTypesId(leftCondition.getTypeId(), rightCondition.getTypeId(), typesTable);

        switch (typeHelper.getLeftTypeId()) {
            case TypesTable.DefaultIds.INTEGER:
                switch (typeHelper.getRightTypeId()) {
                    case TypesTable.DefaultIds.INTEGER:
                        switch (operator) {
                            case EQUALS:
                                return (int) left == (int) right;
                            case NOT_EQUALS:
                                return (int) left != (int) right;
                            case GREATER:
                                return (int) left > (int) right;
                            case LESS:
                                return (int) left < (int) right;
                            case GREATER_EQUALS:
                                return (int) left >= (int) right;
                            case LESS_EQUALS:
                                return (int) left <= (int) right;
                            default:
                                return new PError("Semántico", "Operador relacional inválido", line, column);
                        }
                    case TypesTable.DefaultIds.REAL:
//                        return (int) left == (double) right;
                        switch (operator) {
                            case EQUALS:
                                return (int) left == (double) right;
                            case NOT_EQUALS:
                                return (int) left != (double) right;
                            case GREATER:
                                return (int) left > (double) right;
                            case LESS:
                                return (int) left < (double) right;
                            case GREATER_EQUALS:
                                return (int) left >= (double) right;
                            case LESS_EQUALS:
                                return (int) left <= (double) right;
                            default:
                                return new PError("Semántico", "Operador relacional inválido", line, column);
                        }
                    case TypesTable.DefaultIds.CHARACTER:
                        //return (int) left == (int)(char) right);
                        switch (operator) {
                            case EQUALS:
                                return (int) left == (int)(char)right;
                            case NOT_EQUALS:
                                return (int) left != (int)(char)right;
                            case GREATER:
                                return (int) left > (int)(char)right;
                            case LESS:
                                return (int) left < (int)(char)right;
                            case GREATER_EQUALS:
                                return (int) left >= (int)(char)right;
                            case LESS_EQUALS:
                                return (int) left <= (int)(char)right;
                            default:
                                return new PError("Semántico", "Operador relacional inválido", line, column);
                        }
                    default:
                        return new PError("Semántico", "No es posible comparar los tipos: " + typesTable.getType(typeHelper.getLeftTypeId()).name + " con " + typesTable.getType(typeHelper.getRightTypeId()).name, line, column);
                }
            case TypesTable.DefaultIds.REAL:
                switch (typeHelper.getRightTypeId()) {
                    case TypesTable.DefaultIds.INTEGER:
//                        return (double) left == (int) right;
                        switch (operator) {
                            case EQUALS:
                                return (double) left == (int) right;
                            case NOT_EQUALS:
                                return (double) left != (int) right;
                            case GREATER:
                                return (double) left > (int) right;
                            case LESS:
                                return (double) left < (int) right;
                            case GREATER_EQUALS:
                                return (double) left >= (int) right;
                            case LESS_EQUALS:
                                return (double) left <= (int) right;
                            default:
                                return new PError("Semántico", "Operador relacional inválido", line, column);
                        }
                    case TypesTable.DefaultIds.REAL:
//                        return (double) left == (double) right;
                        switch (operator) {
                            case EQUALS:
                                return (double) left == (double) right;
                            case NOT_EQUALS:
                                return (double) left != (double) right;
                            case GREATER:
                                return (double) left > (double) right;
                            case LESS:
                                return (double) left < (double) right;
                            case GREATER_EQUALS:
                                return (double) left >= (double) right;
                            case LESS_EQUALS:
                                return (double) left <= (double) right;
                            default:
                                return new PError("Semántico", "Operador relacional inválido", line, column);
                        }
                    case TypesTable.DefaultIds.CHARACTER:
//                        return (double) left == Character.getNumericValue((char) right);
                        switch (operator) {
                            case EQUALS:
                                return (double) left == (int)(char)right;
                            case NOT_EQUALS:
                                return (double) left != (int)(char)right;
                            case GREATER:
                                return (double) left > (int)(char)right;
                            case LESS:
                                return (double) left < (int)(char)right;
                            case GREATER_EQUALS:
                                return (double) left >= (int)(char)right;
                            case LESS_EQUALS:
                                return (double) left <= (int)(char)right;
                            default:
                                return new PError("Semántico", "Operador relacional inválido", line, column);
                        }
                    default:
                        return new PError("Semántico", "No es posible comparar los tipos: " + typesTable.getType(typeHelper.getLeftTypeId()).name + " con " + typesTable.getType(typeHelper.getRightTypeId()).name, line, column);
                }
            case TypesTable.DefaultIds.BOOLEAN:
                switch (operator) {
                    case EQUALS:
                        return left.equals(right);
                    case NOT_EQUALS:
                        return !left.equals(right);
                    case GREATER: {
                        var leftValue = (boolean) left ? 1 : 0;
                        var rightValue = (boolean) right ? 1 : 0;
                        return leftValue > rightValue;
                    }
                    case LESS: {
                        var leftValue = (boolean) left ? 1 : 0;
                        var rightValue = (boolean) right ? 1 : 0;
                        return leftValue < rightValue;
                    }
                    case GREATER_EQUALS: {
                        var leftValue = (boolean) left ? 1 : 0;
                        var rightValue = (boolean) right ? 1 : 0;
                        return leftValue >= rightValue;
                    }
                    case LESS_EQUALS: {
                        var leftValue = (boolean) left ? 1 : 0;
                        var rightValue = (boolean) right ? 1 : 0;
                        return leftValue <= rightValue;
                    }
                    default:
                        return new PError("Semántico", "Operador relacional inválido", line, column);
                }
            case TypesTable.DefaultIds.CHARACTER:
                switch (typeHelper.getRightTypeId()) {
                    case TypesTable.DefaultIds.INTEGER:
                        switch (operator) {
                            case EQUALS:
                                return (int)(char)left == (int) right;
                            case NOT_EQUALS:
                                return (int)(char)left != (int) right;
                            case GREATER:
                                return (int)(char)left > (int) right;
                            case LESS:
                                return (int)(char)left < (int) right;
                            case GREATER_EQUALS:
                                return (int)(char)left >= (int) right;
                            case LESS_EQUALS:
                                return (int)(char)left <= (int) right;
                            default:
                                return new PError("Semántico", "Operador relacional inválido", line, column);
                        }
                    case TypesTable.DefaultIds.REAL:
//                        return (char) left == (double) right;
                        switch (operator) {
                            case EQUALS:
                                return (int)(char)left == (double) right;
                            case NOT_EQUALS:
                                return (int)(char)left != (double) right;
                            case GREATER:
                                return (int)(char)left > (double) right;
                            case LESS:
                                return (int)(char)left < (double) right;
                            case GREATER_EQUALS:
                                return (int)(char)left >= (double) right;
                            case LESS_EQUALS:
                                return (int)(char)left <= (double) right;
                            default:
                                return new PError("Semántico", "Operador relacional inválido", line, column);
                        }
                    case TypesTable.DefaultIds.CHARACTER:
//                        return (char) left == (char) right;
                        switch (operator) {
                            case EQUALS:
                                return (int)(char)left == (int)(char)right;
                            case NOT_EQUALS:
                                return (int)(char)left != (int)(char)right;
                            case GREATER:
                                return (int)(char)left > (int)(char)right;
                            case LESS:
                                return (int)(char)left < (int)(char)right;
                            case GREATER_EQUALS:
                                return (int)(char)left >= (int)(char)right;
                            case LESS_EQUALS:
                                return (int)(char)left <= (int)(char)right;
                            default:
                                return new PError("Semántico", "Operador relacional inválido", line, column);
                        }
                    default:
                        return new PError("Semántico", "No es posible comparar los tipos: " + typesTable.getType(typeHelper.getLeftTypeId()).name + " con " + typesTable.getType(typeHelper.getRightTypeId()).name, line, column);
                }
            case TypesTable.DefaultIds.STRING:
                switch (operator) {
                    case EQUALS:
                        return left.equals(right);
                    case NOT_EQUALS:
                        return !left.equals(right);
                    default:
                        return new PError("Semántico", "Operador relacional inválido", line, column);
                }
            default:
                return new PError("Semántico", "No es posible comparar los tipos: " + typesTable.getType(typeHelper.getLeftTypeId()).name + " con " + typesTable.getType(typeHelper.getRightTypeId()).name, line, column);
        }


    }

    private class TypeHelper {
        int leftTypeId;
        int rightTypeId;

        public TypeHelper(int leftTypeId, int rightTypeId) {
            this.leftTypeId = leftTypeId;
            this.rightTypeId = rightTypeId;
        }

        public int getLeftTypeId() {
            return leftTypeId;
        }

        public int getRightTypeId() {
            return rightTypeId;
        }
    }

    private TypeHelper resolveTypesId(int leftTypeId, int rightTypeId, TypesTable typesTable) {
        TypesTable.TypeTableEntry leftOpType = typesTable.getType(leftTypeId);
        TypesTable.TypeTableEntry rightOpType = typesTable.getType(rightTypeId);
        if (!typesTable.isPrimitiveType(typesTable.getType(leftTypeId))) {
            leftOpType = typesTable.getParentType(leftOpType);
            leftTypeId = leftOpType.id;
        }
        if (!typesTable.isPrimitiveType(typesTable.getType(rightTypeId))) {
            rightOpType = typesTable.getParentType(rightOpType);
            rightTypeId = rightOpType.id;
        }
        return new TypeHelper(leftTypeId, rightTypeId);
    }
}
