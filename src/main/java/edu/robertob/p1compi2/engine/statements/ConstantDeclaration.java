package edu.robertob.p1compi2.engine.statements;

import edu.robertob.p1compi2.engine.base.Statement;
import edu.robertob.p1compi2.engine.structs.*;

public class ConstantDeclaration extends Statement {
    private String id;
    private Statement value;

    public ConstantDeclaration(int typeId, int line, int column, String id, Statement value) {
        super(typeId, line, column);
        this.id = id;
        this.value = value;
    }

//    @Override
//    public Object execute(Tree tree, SymbolTable table, TypesTable typesTable) {
//        // add the constant to the symbol table
//        var symbolForTable = new SymbolConstant(this.typeId, this.id, this.value);
//        table.addSymbol(symbolForTable);
//
//        return null;
//    }

    @Override
    public Object execute(Tree tree, SymbolTable table, TypesTable typesTable) {
        this.typeId = value.typeId;

        var result = this.value.execute(tree, table, typesTable);

        if (result instanceof PError) return result;

        // add the constant to the symbol table
        var symbolForTable = new SymbolVariable(this.typeId, true, this.id, result, this.line, this.column);
        boolean added = table.setSymbol(symbolForTable);
        if (!added) {
            var error = new PError("Semantica", "Constante " + this.id + " ya está declarada", this.line, this.column);
            tree.addError(error);
            return error;
        }

        return null;
    }
}
