package edu.robertob.p1compi2.engine.expressions;

import edu.robertob.p1compi2.engine.base.Statement;
import edu.robertob.p1compi2.engine.statements.FunctionDeclaration;
import edu.robertob.p1compi2.engine.statements.ProcedureDeclaration;
import edu.robertob.p1compi2.engine.statements.VariableDeclaration;
import edu.robertob.p1compi2.engine.structs.PError;
import edu.robertob.p1compi2.engine.structs.SymbolTable;
import edu.robertob.p1compi2.engine.structs.Tree;
import edu.robertob.p1compi2.engine.structs.TypesTable;

import java.util.LinkedList;

public class MethodCall extends Statement {
    private String methodName;
    private LinkedList<Statement> parameters;

    private FunctionDeclaration functionToCall;
    private ProcedureDeclaration procedureToCall;

    public MethodCall(String methodName, LinkedList<Statement> parameters, int line, int column) {
        super(TypesTable.DefaultIds.VOID, line, column);
        this.methodName = methodName;
        this.parameters = parameters;
    }

    @Override
    public Object execute(Tree tree, SymbolTable symbolTable, TypesTable typesTable){
        functionToCall = tree.getFunction(this.methodName);
        procedureToCall = tree.getProcedure(this.methodName);

        if (functionToCall == null && procedureToCall == null) {
            var err = new PError("Semantica", "Función/Procedimiento " + this.methodName + " no existe", this.line, this.column);
            tree.addError(err);
            return err;
        }

        if (functionToCall != null) {
            this.typeId = functionToCall.getTypeId();
            var callTable = new SymbolTable(tree.getGlobalTable());
            callTable.setName("FUNC"+this.methodName+"@"+this.line+"@"+this.column);
            tree.getGlobalTable().getChildren().add(callTable);

            // check parameters
            if (functionToCall.getParams().size() != this.parameters.size()) {
                var err = new PError("Semantica", "Se esperaban " + functionToCall.getParams().size() + " parametros, pero se recibieron " + this.parameters.size(), this.line, this.column);
                tree.addError(err);
                return err;
            }

            for (int i = 0; i < this.parameters.size(); i++) {
                var mParamId = (String) functionToCall.getParams().get(i).get("id");
                var asList = new LinkedList<String>();
                asList.add(mParamId);
                var mParamType = (int) functionToCall.getParams().get(i).get("type");
                System.out.println("mParamType: " + mParamType);
                var passedValue = this.parameters.get(i);

                // declare parameter as if it was a variable, it is like a temporary variable but with the same scope as the method and without value set
                var paramDeclaration = new VariableDeclaration(
                        mParamType,
                        this.line,
                        this.column,
                        asList,
                        null,
                        false,
                        false,
                        false,
                        0,
                        0,
                        null,
                        mParamType
                );
                var paramResult = paramDeclaration.execute(tree, callTable, typesTable);

                // execute parameter to get its value with the current scope, not the method scope
                // since we can't set the value of the parameter in the declaration, because it could be a method call and we need to execute it first
                if (paramResult instanceof PError) return paramResult;
                var paramValueResult = passedValue.execute(tree, symbolTable, typesTable);
                System.out.println("passedValue: " + passedValue.getTypeId());

                if (paramValueResult instanceof PError) return paramValueResult;
                var createdVar = callTable.getSymbol(mParamId);
                System.out.println("createdVar: " + createdVar.getTypeId());

                if ((createdVar != null) && createdVar.getTypeId() != passedValue.getTypeId()) {
                    var err = new PError("Semantica", "Tipo de argumento: " + passedValue.getTypeId() + " no coincide con el tipo de parametro esperado: " + createdVar.getOriginalTypeId(), this.line, this.column);
                    tree.addError(err);
                    return err;
                }

                createdVar.setValue(paramValueResult);
            }

            var mResult = functionToCall.execute(tree, callTable, typesTable);
            if (mResult instanceof PError) return mResult;
            if (functionToCall.getTypeId() != TypesTable.DefaultIds.VOID) {
                this.typeId = functionToCall.getTypeId();
                return mResult;
            }

        } else if (procedureToCall != null) {
            this.typeId = procedureToCall.getTypeId();
            var callTable = new SymbolTable(tree.getGlobalTable());
            callTable.setName("FUNC"+this.methodName+"@"+this.line+"@"+this.column);
            tree.getGlobalTable().getChildren().add(callTable);

            // check parameters
            if (procedureToCall.getParams().size() != this.parameters.size()) {
                var err = new PError("Semantica", "Se esperaban " + procedureToCall.getParams().size() + " parametros, pero se recibieron " + this.parameters.size(), this.line, this.column);
                tree.addError(err);
                return err;
            }

            for (int i = 0; i < this.parameters.size(); i++) {
                var mParamId = (String) procedureToCall.getParams().get(i).get("id");
                var asList = new LinkedList<String>();
                asList.add(mParamId);
                var mParamType = (int) procedureToCall.getParams().get(i).get("type");
                System.out.println("mParamType: " + mParamType);
                var passedValue = this.parameters.get(i);

                // declare parameter as if it was a variable, it is like a temporary variable but with the same scope as the method and without value set
                var paramDeclaration = new VariableDeclaration(
                        mParamType,
                        this.line,
                        this.column,
                        asList,
                        null,
                        false,
                        false,
                        false,
                        0,
                        0,
                        null,
                        mParamType
                );
                var paramResult = paramDeclaration.execute(tree, callTable, typesTable);

                // execute parameter to get its value with the current scope, not the method scope
                // since we can't set the value of the parameter in the declaration, because it could be a method call and we need to execute it first
                if (paramResult instanceof PError) return paramResult;
                var paramValueResult = passedValue.execute(tree, symbolTable, typesTable);
                System.out.println("passedValue: " + passedValue.getTypeId());

                if (paramValueResult instanceof PError) return paramValueResult;
                var createdVar = callTable.getSymbol(mParamId);
                System.out.println("createdVar: " + createdVar.getTypeId());

                if ((createdVar != null) && createdVar.getTypeId() != passedValue.getTypeId()) {
                    var err = new PError("Semantica", "Tipo de argumento: " + passedValue.getTypeId() + " no coincide con el tipo de parametro esperado: " + createdVar.getOriginalTypeId(), this.line, this.column);
                    tree.addError(err);
                    return err;
                }

                createdVar.setValue(paramValueResult);
            }

            var mResult = procedureToCall.execute(tree, callTable, typesTable);
            if (mResult instanceof PError) return mResult;
            if (procedureToCall.getTypeId() != TypesTable.DefaultIds.VOID) {
                this.typeId = procedureToCall.getTypeId();
                return mResult;
            }
        }

        return null;
    }
}
