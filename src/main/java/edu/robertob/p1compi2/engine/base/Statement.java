package edu.robertob.p1compi2.engine.base;

import edu.robertob.p1compi2.engine.structs.SymbolTable;
import edu.robertob.p1compi2.engine.structs.Tree;
import edu.robertob.p1compi2.engine.structs.TypesTable;

public abstract class Statement {
    public int typeId;
    public int line;
    public int column;
    public SymbolTable scopeTable;

    public Statement(int typeId, int line, int column) {
        this.typeId = typeId;
        this.line = line;
        this.column = column;
    }

    public abstract Object execute(Tree tree, SymbolTable table, TypesTable typesTable);

//    public abstract String generateAstDotFormat(Tree tree, String previousContent);

//    public Types getType(){
//        return this.type;
//    }

    public SymbolTable getScopeTable() {
        return scopeTable;
    }

    public void setScopeTable(SymbolTable scopeTable) {
        this.scopeTable = scopeTable;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}
