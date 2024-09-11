package edu.robertob.p1compi2.engine.statements;

import edu.robertob.p1compi2.engine.base.Statement;
import edu.robertob.p1compi2.engine.structs.*;

import java.util.LinkedList;

public class WhileLoop extends Statement {
    private Statement condition;
    private LinkedList<Statement> whileBody;

    public WhileLoop(Statement condition, LinkedList<Statement> whileBody, int line, int column) {
        super(0, line, column);
        this.condition = condition;
        this.whileBody = whileBody;
    }

    @Override
    public Object execute(Tree tree, SymbolTable table, TypesTable typesTable) {
        var conditionResult = condition.execute(tree, table, typesTable);
//        if (conditionResult instanceof PError) return conditionResult;

        if (condition.getTypeId() != TypesTable.DefaultIds.BOOLEAN){
//            return new PError("Semantica", "La condición de un bloque while debe ser de tipo booleano", line, column);
            tree.addError(new PError("Semantica", "La condición de un bloque while debe ser de tipo booleano", line, column));
        }

//        while((boolean) conditionResult){
            for (var instruction : whileBody) {
                var result = instruction.execute(tree, table, typesTable);
//                if (result instanceof PError) return result;
                if (result instanceof PError) tree.addError((PError) result);
//                if (result instanceof Break) {
//                    return null; // Exit the loop
//                }

                // Check for continue
//                if (result instanceof Continue) {
//                    break; // Exit the current iteration and evaluate the condition again
//                }

            }
//            conditionResult = condition.execute(tree, table, typesTable);
//            if (conditionResult instanceof PError) return conditionResult;
//        }

        return null;
    }
}
