package edu.robertob.p1compi2.engine.expressions;

import edu.robertob.p1compi2.engine.base.Statement;
import edu.robertob.p1compi2.engine.structs.*;
import edu.robertob.p1compi2.engine.utils.TypeUtils;

public class ArrayAccess extends Statement {
    private String id;
    private Statement indexToAccess;

    public ArrayAccess(String id, Statement indexToAccess, int line, int column) {
        super(TypesTable.DefaultTypes.VOID.id, line, column);
        this.id = id;
        this.indexToAccess = indexToAccess;
    }

    @Override
    public Object execute(Tree tree, SymbolTable symbolTable, TypesTable typesTable) {
        var symbol = symbolTable.getSymbol(id);
        if (symbol == null) {
            tree.addError(new PError("Semantica", "Variable " + id + " no existe", this.line, this.column));
            return new PError("Semantica", "Variable " + id + " no existe", this.line, this.column);
        }

        if (!(symbol.getValue() instanceof ArrayValue)) {
            tree.addError(new PError("Semantica", "Variable: " + this.id + " no es un vector", this.line, this.column));
            return new PError("Semantica", "Variable: " + this.id + " no es un vector", this.line, this.column);
        }

        ArrayValue vector = (ArrayValue) symbol.getValue();
        var indexValue = this.indexToAccess.execute(tree, symbolTable, typesTable);
        if (indexValue instanceof PError) {
            return indexValue;
        }
        if (!(indexValue instanceof Integer)) {
            tree.addError(new PError("Semantica", "Indice del vector a accesar debe ser entero", this.line, this.column));
            return new PError("Semantica", "Indice del vector a accesar debe ser entero", this.line, this.column);
        }

        int index = (Integer) indexValue;
        if (!vector.isValidIndex(index)) {
            tree.addError(new PError("Semantica", "Indice de acceso al vector " + id + " fuera de rango", this.line, this.column));
            return new PError("Semantica", "Indice de acceso al vector " + id + " fuera de rango", this.line, this.column);
        }

        System.out.println(vector.get(index)+" <- vector");
//        this.typeId = symbol.getTypeId();
        this.typeId = TypeUtils.recursivelyResolveBaseType(symbol.getTypeId(), typesTable);
        return vector.get(index);
    }
}
