package edu.robertob.p1compi2.engine.statements;

import edu.robertob.p1compi2.engine.base.Statement;
import edu.robertob.p1compi2.engine.structs.*;

import java.util.LinkedList;

public class IfStatement extends Statement {

    private Statement condition;
    private LinkedList<ConditionBlock> conditionBlocks;
    private LinkedList<Statement> elseBody;

    public IfStatement(LinkedList<ConditionBlock> conditionBlocks, int line, int column) {
        super(0, line, column);
        this.conditionBlocks = conditionBlocks;
    }

    public IfStatement(LinkedList<ConditionBlock> conditionBlocks, LinkedList<Statement> elseBody, int line, int column) {
        super(0, line, column);
        this.conditionBlocks = conditionBlocks;
        this.elseBody = elseBody;
    }

    @Override
    public Object execute(Tree tree, SymbolTable table, TypesTable typesTable) {
        for (ConditionBlock block : conditionBlocks) {
            var resultCondition = block.getCondition().execute(tree, table, typesTable);
            if (resultCondition instanceof PError) return resultCondition;

            if (block.getCondition().getTypeId() != TypesTable.DefaultIds.BOOLEAN) {
                return new PError("Semantica", "La condición de un bloque if debe ser de tipo booleano", line, column);
            }
                for (var instruction : block.getBody()) {
                    var result = instruction.execute(tree, table, typesTable);
                    if (result instanceof PError || result instanceof Break || result instanceof Continue)
                        return result;
//                    if (result instanceof ReturnIns) return result;
                }
                return null;
//            }
        }

        if (elseBody != null) {
            for (var instruction : elseBody) {
                var result = instruction.execute(tree, table, typesTable);
                if (result instanceof PError || result instanceof Break || result instanceof Continue) return result;
//                if (result instanceof ReturnIns) return result;
            }
        }

        return null;
    }

    public static class ConditionBlock {
        private Statement condition;
        private LinkedList<Statement> body;

        public ConditionBlock(Statement condition, LinkedList<Statement> body) {
            this.condition = condition;
            this.body = body;
        }


        public Statement getCondition() {
            return condition;
        }

        public LinkedList<Statement> getBody() {
            return body;
        }
    }

    public static LinkedList<ConditionBlock> buildConditionBlockList(Statement ifCondition, LinkedList<Statement> ifBody, LinkedList<ConditionBlock> elseIfBlocks) {
        LinkedList<ConditionBlock> conditionBlocks = new LinkedList<>();
        conditionBlocks.add(new ConditionBlock(ifCondition, ifBody));
        conditionBlocks.addAll(elseIfBlocks);
        return conditionBlocks;
    }
}
