package edu.robertob.p1compi2.engine.structs;

import edu.robertob.p1compi2.engine.base.Statement;

public class Continue extends Statement {
    public Continue(int line, int column) {
        super(0, line, column);
    }

    @Override
    public Object execute(Tree tree, SymbolTable table, TypesTable typesTable) {
        return this;
    }
}
