package edu.robertob.p1compi2.engine.statements;

import edu.robertob.p1compi2.engine.base.Statement;
import edu.robertob.p1compi2.engine.structs.*;

import java.util.Objects;

public class SymbolAssignation extends Statement {
    private String id;
    private Statement newValue;

    public SymbolAssignation(String id, Statement newValue, int line, int column) {
        super(0, line, column);
        this.id = id;
        this.newValue = newValue;
    }

    @Override
    public Object execute(Tree tree, SymbolTable table, TypesTable typesTable) {
        SymbolVariable symbol = table.getSymbol(id);
        if (symbol == null) {
            var error = new PError("Semantica", "Variable " + id + " no existe", this.line, this.column);
            tree.addError(error);
            return error;
        }

        Object result = this.newValue.execute(tree, table, typesTable);

        if (result instanceof PError) {
            tree.addError((PError) result);
            return result;
        }

        // edge cases:
        // if the symbol is boolean, it can accept 0 as false and any other value as true so we convert it to boolean
        if (symbol.getTypeId() == TypesTable.DefaultIds.BOOLEAN || (typesTable.getType(symbol.getTypeId()).parentTypeId == TypesTable.DefaultIds.BOOLEAN)) {
            if (result instanceof Integer) {
                result = (int) result != 0;
                this.newValue.setTypeId(TypesTable.DefaultIds.BOOLEAN);
            }
        }

        // in case it's a subrange, we need to check if the value is within the range
        if (symbol.isRange()) System.out.println("SymbolAssignation: symbol is range");

        if (symbol.getTypeId() != this.newValue.getTypeId()) {
            var err = new PError("Semantica", "No se puede asignar el valor de tipo " + typesTable.getType(this.newValue.getTypeId()).name + " a una variable de tipo " + typesTable.getType(symbol.getTypeId()).name + "(" + (Objects.equals(typesTable.getType(symbol.getOriginalTypeId()).name, "void") ? "" : typesTable.getType(symbol.getOriginalTypeId()).name) + ")", this.line, this.column);
            tree.addError(err);
            return err;
        }

        if(symbol.isConstant()) {
            var error = new PError("Semantica", "Variable constante " + id + " no puede ser re-asignada", this.line, this.column);
            tree.addError(error);
            return error;
        }


        symbol.setValue(result);
        return null;
    }
}
