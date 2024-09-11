package edu.robertob.p1compi2.engine.expressions;

import edu.robertob.p1compi2.engine.base.Statement;
import edu.robertob.p1compi2.engine.structs.PError;
import edu.robertob.p1compi2.engine.structs.SymbolTable;
import edu.robertob.p1compi2.engine.structs.Tree;
import edu.robertob.p1compi2.engine.structs.TypesTable;

public class SymbolAccess extends Statement {
    private String id;

    public SymbolAccess(String id, int line, int col) {
        super(0, line, col);
        this.id = id;
    }

    @Override
    public Object execute(Tree tree, SymbolTable table, TypesTable typesTable) {
        var value = table.getSymbol(this.id);
        if (value == null) {
            tree.addError(new PError("Semantica", "Variable/Constante: " + this.id + " no existe", this.line, this.column));
            return new PError("Semantica", "Variable/Constante: " + this.id + " no existe", this.line, this.column);
        }
        this.typeId = value.getTypeId();
//        System.out.println("SymbolAccess: " + value.getValue());
//        System.out.println("SymbolAccess: " + value.getTypeId());
        return value.getValue();
    }
}
