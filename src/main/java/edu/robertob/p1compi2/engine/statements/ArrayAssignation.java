package edu.robertob.p1compi2.engine.statements;

import edu.robertob.p1compi2.engine.base.Statement;
import edu.robertob.p1compi2.engine.structs.*;

import java.util.Objects;

public class ArrayAssignation extends Statement {
    private String id;
    private Statement indexToAssign;
    private Statement newValue;

    public ArrayAssignation(String id, Statement indexToAssign, Statement newValue, int line, int column) {
        super(TypesTable.DefaultTypes.VOID.id, line, column);
        this.id = id;
        this.indexToAssign = indexToAssign;
        this.newValue = newValue;
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
        var indexValue = this.indexToAssign.execute(tree, symbolTable, typesTable);
        if (indexValue instanceof PError) {
//            tree.addError((PError) indexValue);
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

        var newValueResult = this.newValue.execute(tree, symbolTable, typesTable);
        if (newValueResult instanceof PError) {
            tree.addError((PError) newValueResult);
            return newValueResult;
        }


        System.out.println(newValue.getClass());
        System.out.println(newValue.getTypeId());
        int originalTypeId = symbol.getOriginalTypeId();
        if (originalTypeId == typesTable.getType(newValue.getTypeId()).id) {
            vector.set(index, newValueResult);
        } else {
            var err = new PError(
                    "Semantica",
//                    "Mala asignacion de tipo en vector",
                    "No se puede asignar el valor de tipo " +
                            typesTable.getType(newValue.getTypeId()).name +
                            "(" +
                            (Objects.equals(typesTable.getType(newValue.getTypeId()).name, "void")  || Objects.equals(typesTable.getType(newValue.getTypeId()).name, "void") ?
                                    "" :
                                    typesTable.getType(typesTable.getType(newValue.getTypeId()).id).name
                            ) +
                             ")" +
                            " a un vector de tipo " +
                            typesTable.getType(symbol.getTypeId()).name +
                            "(" +
                            (Objects.equals(typesTable.getType(symbol.getOriginalTypeId()).name, "void") ?
                                    "" :
                                    typesTable.getType(symbol.getOriginalTypeId()).name
                            ) + ")",
                    this.line,
                    this.column
            );
//
            tree.addError(err);
            System.out.println(typesTable.getType(symbol.getOriginalTypeId()).name);
            return err;
        }



        return null;
    }

}
