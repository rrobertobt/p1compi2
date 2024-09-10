package edu.robertob.p1compi2.engine.expressions;

import edu.robertob.p1compi2.engine.base.LogicalOperators;
import edu.robertob.p1compi2.engine.base.Statement;
import edu.robertob.p1compi2.engine.structs.PError;
import edu.robertob.p1compi2.engine.structs.SymbolTable;
import edu.robertob.p1compi2.engine.structs.Tree;
import edu.robertob.p1compi2.engine.structs.TypesTable;

public class Logical extends Statement {
    private Statement leftOperand;
    private Statement rightOperand;
    private Statement uniqueOperand;
    private LogicalOperators operator;

    public Logical(Statement leftOperand, Statement rightOperand, LogicalOperators operator, int line, int column) {
        super(TypesTable.DefaultTypes.BOOLEAN.id, line, column);
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.operator = operator;
    }

    public Logical(Statement uniqueOperand, LogicalOperators operator, int line, int column) {
        super(TypesTable.DefaultTypes.BOOLEAN.id, line, column);
        this.uniqueOperand = uniqueOperand;
        this.operator = operator;
    }

    @Override
    public Object execute(Tree tree, SymbolTable table, TypesTable typesTable) {
        Object unique = null, left = null, right = null;
        int leftTypeId, rightTypeId, uniqueTypeId;
        if (uniqueOperand != null) {
            unique = uniqueOperand.execute(tree, table, typesTable);
            if (unique instanceof PError) return unique;
            uniqueTypeId = uniqueOperand.getTypeId();
            if (uniqueTypeId != TypesTable.DefaultTypes.BOOLEAN.id) {
                return new PError("Semantico", "No se puede operar " + typesTable.getType(uniqueTypeId).name + " con " + typesTable.getType(TypesTable.DefaultTypes.BOOLEAN.id).name, this.line, this.column);
            }
        } else {
            left = leftOperand.execute(tree, table, typesTable);
            if (left instanceof PError) return left;

            right = rightOperand.execute(tree, table, typesTable);
            if (right instanceof PError) return right;

            leftTypeId = leftOperand.getTypeId();
            rightTypeId = rightOperand.getTypeId();
            if (leftTypeId != TypesTable.DefaultTypes.BOOLEAN.id || rightTypeId != TypesTable.DefaultTypes.BOOLEAN.id) {
                return new PError("Semantico", "No se puede operar " + typesTable.getType(leftTypeId).name + " con " + typesTable.getType(rightTypeId).name, this.line, this.column);
            }

        }

        switch (operator) {
            case AND:
                // Always evaluate both sides
                return ((boolean) left) & ((boolean) right); // Single '&' for non-short-circuit AND
            case ANDTHEN:
                // Short-circuit AND; Java's '&&' handles short-circuiting
                return ((boolean) left) && ((boolean) right);
            case OR:
                // Always evaluate both sides
                return ((boolean) left) | ((boolean) right); // Single '|' for non-short-circuit OR
            case ORELSE:
                // Short-circuit OR; Java's '||' handles short-circuiting
                return ((boolean) left) || ((boolean) right);
            case NOT:
                // NOT operation only needs one operand
                return !((boolean) unique);
            default:
                return new PError("Semantico", "Operador lógico no reconocido", this.line, this.column);
        }

    }
}
