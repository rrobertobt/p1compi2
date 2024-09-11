package edu.robertob.p1compi2.engine.expressions;

import edu.robertob.p1compi2.engine.base.ArithmeticOperators;
import edu.robertob.p1compi2.engine.base.Statement;
import edu.robertob.p1compi2.engine.structs.PError;
import edu.robertob.p1compi2.engine.structs.SymbolTable;
import edu.robertob.p1compi2.engine.structs.Tree;
import edu.robertob.p1compi2.engine.structs.TypesTable;

public class Arithmetic extends Statement {
    private Statement leftOperand;
    private Statement rightOperand;
    private ArithmeticOperators operator;

    // in case of unary operators
    private Statement uniqueOperand;

    // constructor to handle negation (ex: -6)
    public Arithmetic(Statement uniqueOperand, ArithmeticOperators operator, int line, int column) {
        super(0, line, column);
        this.uniqueOperand = uniqueOperand;
        this.operator = operator;
    }

    // constructor to handle any other operation
    public Arithmetic(Statement leftOperand, Statement rightOperand, ArithmeticOperators operator, int line, int column) {
        super(0, line, column);
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.operator = operator;
    }

    public Object execute(Tree tree, SymbolTable symbolTable, TypesTable typesTable) {
        Object leftOperandValue = null, rightOperandValue = null, uniqueOperandValue = null;
        // error checking
        if (this.uniqueOperand != null){
            uniqueOperandValue = this.uniqueOperand.execute(tree, symbolTable, typesTable);
            if (uniqueOperandValue instanceof PError) return uniqueOperandValue;
        } else {
            leftOperandValue = this.leftOperand.execute(tree, symbolTable, typesTable);
            if (leftOperandValue instanceof PError) return leftOperandValue;

            rightOperandValue = this.rightOperand.execute(tree, symbolTable, typesTable);
            if (rightOperandValue instanceof PError) return rightOperandValue;
        }


        return switch (this.operator) {
            case SUM -> add(leftOperandValue, rightOperandValue, typesTable);
            case SUB -> subtract(leftOperandValue, rightOperandValue, typesTable);
            case MUL -> multiply(leftOperandValue, rightOperandValue, typesTable);
            case DIV -> divide(leftOperandValue, rightOperandValue, typesTable);
            case MOD -> modulo(leftOperandValue, rightOperandValue, typesTable);
            case NEG -> negate(uniqueOperandValue, typesTable);

        };
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

    public Object add(Object leftOperand, Object rightOperand, TypesTable typesTable) {
        //before continuing, check if the types are primitive and not aliases
        TypeHelper types = resolveTypesId(this.leftOperand.getTypeId(), this.rightOperand.getTypeId(), typesTable);

        switch (types.getLeftTypeId()) {
            case TypesTable.DefaultIds.INTEGER -> {
                switch (types.getRightTypeId()) {
                    case TypesTable.DefaultIds.INTEGER -> {
                        this.typeId = TypesTable.DefaultIds.INTEGER;
                        return (int) leftOperand + (int) rightOperand;
                    }
                    case TypesTable.DefaultIds.REAL -> {
                        this.typeId = TypesTable.DefaultIds.REAL;
                        return (int) leftOperand + (double) rightOperand;
                    }
                    case TypesTable.DefaultIds.STRING -> {
                        this.typeId = TypesTable.DefaultIds.STRING;
                        return leftOperand.toString() + rightOperand.toString();
                    }
                    default -> {
                        return new PError("Semantica", "No se puede sumar " + typesTable.getType(types.getLeftTypeId()).name + " con " + typesTable.getType(types.getRightTypeId()).name, this.line, this.column);
                    }
                }
            }
            case TypesTable.DefaultIds.REAL -> {
                switch (types.getRightTypeId()) {
                    case TypesTable.DefaultIds.INTEGER -> {
                        this.typeId = TypesTable.DefaultIds.REAL;
                        return Double.parseDouble(leftOperand.toString()) + Integer.parseInt(rightOperand.toString());
                    }
                    case TypesTable.DefaultIds.REAL -> {
                        this.typeId = TypesTable.DefaultIds.REAL;
                        return (double) leftOperand + (double) rightOperand;
                    }
                    case TypesTable.DefaultIds.STRING -> {
                        this.typeId = TypesTable.DefaultIds.STRING;
                        return leftOperand.toString() + rightOperand.toString();
                    }
                    default -> {
                        return new PError("Semantica", "No se puede sumar " + typesTable.getType(types.getLeftTypeId()).name + " con " + typesTable.getType(types.getRightTypeId()).name, this.line, this.column);
                    }
                }
            }
            case TypesTable.DefaultIds.STRING -> {
                switch (types.getRightTypeId()) {
                    case TypesTable.DefaultIds.INTEGER, TypesTable.DefaultIds.REAL,
                         TypesTable.DefaultIds.STRING -> {
                        this.typeId = TypesTable.DefaultIds.STRING;
                        return leftOperand.toString() + rightOperand.toString();
                    }
                    case TypesTable.DefaultIds.CHARACTER -> {
                        this.typeId = TypesTable.DefaultIds.STRING;
                        return leftOperand.toString() + (char) rightOperand;
                    }
                    default -> {
                        return new PError("Semantica", "No se puede sumar " + typesTable.getType(types.getLeftTypeId()).name + " con " + typesTable.getType(types.getRightTypeId()).name, this.line, this.column);
                    }
                }
            }

            default -> {
                return new PError("Semantica", "Operación invalida de suma", this.line, this.column);
            }
        }
    }

    public Object subtract(Object leftOperand, Object rightOperand, TypesTable typesTable) {
        TypeHelper types = resolveTypesId(this.leftOperand.getTypeId(), this.rightOperand.getTypeId(), typesTable);

        switch (types.getLeftTypeId()) {
            case TypesTable.DefaultIds.INTEGER -> {
                switch (types.getRightTypeId()) {
                    case TypesTable.DefaultIds.INTEGER -> {
                        this.typeId = TypesTable.DefaultIds.INTEGER;
                        return (int) leftOperand - (int) rightOperand;
                    }
                    case TypesTable.DefaultIds.REAL -> {
                        this.typeId = TypesTable.DefaultIds.REAL;
                        return (int) leftOperand - Double.parseDouble(rightOperand.toString());
                    }
                    default -> {
                        return new PError("Semantica", "No se puede restar " + typesTable.getType(types.getLeftTypeId()).name + " con " + typesTable.getType(types.getRightTypeId()).name, this.line, this.column);
                    }
                }
            }
            case TypesTable.DefaultIds.REAL -> {
                switch (types.getRightTypeId()) {
                    case TypesTable.DefaultIds.INTEGER -> {
                        this.typeId = TypesTable.DefaultIds.REAL;
                        return (double) leftOperand - (int) rightOperand;
                    }
                    case TypesTable.DefaultIds.REAL -> {
                        this.typeId = TypesTable.DefaultIds.REAL;
                        return (double) leftOperand - (double) rightOperand;
                    }
                    default -> {
                        return new PError("Semantica", "No se puede restar " + typesTable.getType(types.getLeftTypeId()).name + " con " + typesTable.getType(types.getRightTypeId()).name, this.line, this.column);
                    }
                }
            }
            default -> {
                return new PError("Semantica", "Operación invalida de resta", this.line, this.column);
            }
        }
    }

    public Object multiply(Object leftOperand, Object rightOperand, TypesTable typesTable) {
        TypeHelper types = resolveTypesId(this.leftOperand.getTypeId(), this.rightOperand.getTypeId(), typesTable);

        switch (types.getLeftTypeId()) {
            case TypesTable.DefaultIds.INTEGER -> {
                switch (types.getRightTypeId()) {
                    case TypesTable.DefaultIds.INTEGER -> {
                        this.typeId = TypesTable.DefaultIds.INTEGER;
                        return (int) leftOperand * (int) rightOperand;
                    }
                    case TypesTable.DefaultIds.REAL -> {
                        this.typeId = TypesTable.DefaultIds.REAL;
                        return (int) leftOperand * (double) rightOperand;
                    }
                    default -> {
                        return new PError("Semantica", "No se puede multiplicar " + typesTable.getType(types.getLeftTypeId()).name + " con " + typesTable.getType(types.getRightTypeId()).name, this.line, this.column);
                    }
                }
            }
            case TypesTable.DefaultIds.REAL -> {
                switch (types.getRightTypeId()) {
                    case TypesTable.DefaultIds.INTEGER -> {
                        this.typeId = TypesTable.DefaultIds.REAL;
                        return Double.parseDouble(leftOperand.toString()) * Integer.parseInt(rightOperand.toString());
                    }
                    case TypesTable.DefaultIds.REAL -> {
                        this.typeId = TypesTable.DefaultIds.REAL;
                        return (double) leftOperand * (double) rightOperand;
                    }
                    default -> {
                        return new PError("Semantica", "No se puede multiplicar " + typesTable.getType(types.getLeftTypeId()).name + " con " + typesTable.getType(types.getRightTypeId()).name, this.line, this.column);
                    }
                }
            }
            default -> {
                return new PError("Semantica", "Operación invalida de multiplicación", this.line, this.column);
            }
        }
    }

    public Object divide(Object leftOperand , Object rightOperand, TypesTable typesTable) {
        TypeHelper types = resolveTypesId(this.leftOperand.getTypeId(), this.rightOperand.getTypeId(), typesTable);

        switch (types.getLeftTypeId()) {
            case TypesTable.DefaultIds.INTEGER -> {
                switch (types.getRightTypeId()) {
                    case TypesTable.DefaultIds.INTEGER -> {
                        this.typeId = TypesTable.DefaultIds.REAL;
                        return (double) (int) leftOperand / (int) rightOperand;
                    }
                    case TypesTable.DefaultIds.REAL -> {
                        this.typeId = TypesTable.DefaultIds.REAL;
                        return (int) leftOperand / (double) rightOperand;
                    }
                    default -> {
                        return new PError("Semantica", "No se puede dividir " + typesTable.getType(types.getLeftTypeId()).name + " con " + typesTable.getType(types.getRightTypeId()).name, this.line, this.column);
                    }
                }
            }
            case TypesTable.DefaultIds.REAL -> {
                switch (types.getRightTypeId()) {
                    case TypesTable.DefaultIds.INTEGER -> {
                        this.typeId = TypesTable.DefaultIds.REAL;
                        return (double) leftOperand / (int) rightOperand;
                    }
                    case TypesTable.DefaultIds.REAL -> {
                        this.typeId = TypesTable.DefaultIds.REAL;
                        return (double) leftOperand / (double) rightOperand;
                    }
                    default -> {
                        return new PError("Semantica", "No se puede dividir " + typesTable.getType(types.getLeftTypeId()).name + " con " + typesTable.getType(types.getRightTypeId()).name, this.line, this.column);
                    }
                }
            }
            default -> {
                return new PError("Semantica", "Operación invalida de división", this.line, this.column);
            }
        }
    }

    public Object modulo(Object leftOperand, Object rightOperand, TypesTable typesTable) {
        TypeHelper types = resolveTypesId(this.leftOperand.getTypeId(), this.rightOperand.getTypeId(), typesTable);

        switch (types.getLeftTypeId()) {
            case TypesTable.DefaultIds.INTEGER -> {
                switch (types.getRightTypeId()) {
                    case TypesTable.DefaultIds.INTEGER -> {
                        this.typeId = TypesTable.DefaultIds.INTEGER;
                        return (int) leftOperand % (int) rightOperand;
                    }
                    default -> {
                        return new PError("Semantica", "No se puede aplicar modulo a " + typesTable.getType(types.getLeftTypeId()).name + " con " + typesTable.getType(types.getRightTypeId()).name, this.line, this.column);
                    }
                }
            }
            default -> {
                return new PError("Semantica", "Operación invalida de módulo", this.line, this.column);
            }
        }
    }

    public Object negate(Object operand, TypesTable typesTable) {
        // first resolve the type in case it is an alias
        if (!typesTable.isPrimitiveType(typesTable.getType(this.uniqueOperand.getTypeId()))) {
            this.uniqueOperand.setTypeId(typesTable.getParentType(typesTable.getType(this.uniqueOperand.getTypeId())).id);
        }

        switch (this.uniqueOperand.getTypeId()) {
            case TypesTable.DefaultIds.INTEGER -> {
                this.typeId = TypesTable.DefaultIds.INTEGER;
                return -(int) operand;
            }
            case TypesTable.DefaultIds.REAL -> {
                this.typeId = TypesTable.DefaultIds.REAL;
                return -(double) operand;
            }
            default -> {
                return new PError("Semantica", "Operación invalida de negación", this.line, this.column);
            }
        }
    }
}
