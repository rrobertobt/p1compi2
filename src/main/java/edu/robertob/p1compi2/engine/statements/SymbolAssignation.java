package edu.robertob.p1compi2.engine.statements;

import edu.robertob.p1compi2.engine.base.Statement;
import edu.robertob.p1compi2.engine.structs.*;

public class SymbolAssignation extends Statement {
    private String id;
    private Statement newValue;

    public SymbolAssignation(String id, Statement newValue, int line, int column) {
        super(-1, line, column);
        this.id = id;
        this.newValue = newValue;
    }

    @Override
    public Object execute(Tree tree, SymbolTable table, TypesTable typesTable) {
        SymbolVariable symbol = table.getSymbol(id);
        if (symbol == null) {
            return new PError("Semantica", "Variable " + id + " no existe", this.line, this.column);
        }

        Object result = this.newValue.execute(tree, table, typesTable);

        if (result instanceof PError) return result;



        // edge cases:
        // if the symbol is boolean, it can accept 0 as false and any other value as true so we convert it to boolean
        if (symbol.getTypeId() == TypesTable.DefaultIds.BOOLEAN || (typesTable.getType(symbol.getTypeId()).parentTypeId == TypesTable.DefaultIds.BOOLEAN)) {
            if (result instanceof Integer) {
                result = (int) result != 0;
                this.newValue.setTypeId(TypesTable.DefaultIds.BOOLEAN);
            }
        }

        // check first if the symbol type has a parent type and the new value has the same parent type
        // example: type mystringtype is string; var a: mystringtype = "hello";
        if (typesTable.getType(symbol.getTypeId()).parentTypeId != -1) {
            if (typesTable.getType(symbol.getTypeId()).parentTypeId != this.newValue.getTypeId())
                return new PError("Semantica", "No se puede asignar el valor de tipo " + typesTable.getType(this.newValue.getTypeId()).name + " a una variable de tipo " + typesTable.getType(symbol.getTypeId()).name, this.line, this.column);
        } else if (symbol.getTypeId() != this.newValue.getTypeId())
            return new PError("Semantica", "No se puede asignar el valor de tipo " + typesTable.getType(this.newValue.getTypeId()).name + " a una variable de tipo " + typesTable.getType(symbol.getTypeId()).name, this.line, this.column);

        if(symbol.isConstant())
            return new PError("Semantica", "Variable constante " + id + " no puede ser re-asignada", this.line, this.column);

        symbol.setValue(result);
        return null;
    }
}
