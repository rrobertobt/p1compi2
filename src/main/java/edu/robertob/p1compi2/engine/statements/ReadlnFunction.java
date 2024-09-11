package edu.robertob.p1compi2.engine.statements;

import edu.robertob.p1compi2.engine.base.Statement;
import edu.robertob.p1compi2.engine.structs.PError;
import edu.robertob.p1compi2.engine.structs.SymbolTable;
import edu.robertob.p1compi2.engine.structs.Tree;
import edu.robertob.p1compi2.engine.structs.TypesTable;

import java.util.LinkedList;

public class ReadlnFunction extends Statement {
    private LinkedList<Statement> values;
    public ReadlnFunction(int line, int column, LinkedList<Statement> values) {
        super(TypesTable.DefaultIds.VOID, line, column);
        this.values = values;
    }

    @Override
    public Object execute(Tree tree, SymbolTable table, TypesTable typesTable) {
        for (var value : values) {
            var result = value.execute(tree, table, typesTable);
            if (result instanceof PError) {
                tree.addError((PError) result);
                return result;
            }
            System.out.print(result);
        }
        return null;
    }
}
