package edu.robertob.p1compi2.engine.expressions;

import edu.robertob.p1compi2.engine.base.Statement;
import edu.robertob.p1compi2.engine.structs.SymbolTable;
import edu.robertob.p1compi2.engine.structs.Tree;
import edu.robertob.p1compi2.engine.structs.TypesTable;

public class Primitive extends Statement {
    private Object value;

    public Primitive( int line, int column, int typeId, Object value) {
        super(typeId, line, column);
        this.value = value;
    }

    @Override
    public Object execute(Tree tree, SymbolTable table, TypesTable typesTable) {
        return this.value;
    }
}
