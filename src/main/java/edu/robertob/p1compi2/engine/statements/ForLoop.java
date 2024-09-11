package edu.robertob.p1compi2.engine.statements;

import edu.robertob.p1compi2.engine.base.Statement;
import edu.robertob.p1compi2.engine.structs.*;

import java.util.LinkedList;

public class ForLoop extends Statement {
    private String idToUse;
    private Statement start;
    private Statement end;
    private LinkedList<Statement> body;

    public ForLoop(int line, int column, String idToUse, Statement start, Statement end, LinkedList<Statement> body) {
        super(TypesTable.DefaultIds.VOID, line, column);
        this.idToUse = idToUse;
        this.start = start;
        this.end = end;
        this.body = body;
    }

    @Override
    public Object execute(Tree tree, SymbolTable table, TypesTable typesTable) {
        SymbolVariable sv = table.getSymbol(idToUse);
        if (sv == null) {
            tree.addError(new PError("Semantico", "La variable " + idToUse + " no ha sido declarada", line, column));
            return new PError("Semantico", "La variable " + idToUse + " no ha sido declarada", line, column);
        }

        var init = start.execute(tree, table, typesTable);
        var endValue = end.execute(tree, table, typesTable);

        // check types
        if (sv.getTypeId() != TypesTable.DefaultIds.INTEGER || start.getTypeId() != TypesTable.DefaultIds.INTEGER || end.getTypeId() != TypesTable.DefaultIds.INTEGER) {
            tree.addError(new PError("Semantico", "Los valores de inicio y fin deben ser de tipo entero", line, column));
            return new PError("Semantico", "Los valores de inicio y fin deben ser de tipo entero", line, column);
        } else if ((int) init >= (int) endValue) {
            tree.addError(new PError("Semantico", "El valor de inicio debe ser menor al valor de fin", line, column));
            return new PError("Semantico", "El valor de inicio debe ser menor al valor de fin", line, column);
        }

        for (Statement s : body) {
            var result = s.execute(tree, table, typesTable);
//            if (result instanceof PError) {
//                tree.addError((PError) result);
//                return result;
//            }
        }

        return null;
    }
}
