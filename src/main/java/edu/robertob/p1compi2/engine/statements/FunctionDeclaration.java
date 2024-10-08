package edu.robertob.p1compi2.engine.statements;

import edu.robertob.p1compi2.engine.base.Statement;
import edu.robertob.p1compi2.engine.structs.*;

import java.util.HashMap;
import java.util.LinkedList;

public class FunctionDeclaration extends Statement {
    private String id;
    private LinkedList<HashMap> params;
    private LinkedList<Statement> header;
    private LinkedList<Statement> methodBody;
    private String returnTypeName;
    private int returnTypeId;
    private int originalReturnTypeId;

    private final String[] reservedMethodNames = {
            "writeln",
            "readln"
    };

    public boolean isReservedMethod() {
        for (String reservedMethodName : reservedMethodNames) {
            if (reservedMethodName.equals(this.id)) {
                return true;
            }
        }
        return false;
    }


    public FunctionDeclaration(int typeId, int line, int column, String id, LinkedList<HashMap> params, LinkedList<Statement> header, LinkedList<Statement> methodBody, String returnTypeName, int returnTypeId) {
        super(typeId, line, column);
        this.id = id;
        this.params = params;
        this.header = header;
        this.methodBody = methodBody;
        this.returnTypeName = returnTypeName;
        this.returnTypeId = returnTypeId;
    }

    @Override
    public Object execute(Tree tree, SymbolTable table, TypesTable typesTable) {
        if (returnTypeName != null){
            var typeId = typesTable.getType(returnTypeName);
            this.returnTypeId = typeId.id;
            this.originalReturnTypeId = typeId.baseTypeId;
            this.typeId = this.returnTypeId;
        } else {
            this.typeId = this.returnTypeId;
//            this.originalReturnTypeId = -1;
        }

        //execute header first
        for (Statement i : header) {
            if (i != null) {
                var result = i.execute(tree, table, typesTable);
                if (result instanceof PError) {
                    tree.addError((PError) result);
                    return result;
                }
                if (result != null) {
                    return result;
                }
            }
        }

        for (int i = 0; i < methodBody.size(); i++) {
            if (methodBody.get(i) != null) {
                var result = methodBody.get(i).execute(tree, table, typesTable);
                if (result instanceof PError) {
                    tree.addError((PError) result);
                    return result;
                }
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LinkedList<HashMap> getParams() {
        return params;
    }

    public void setParams(LinkedList<HashMap> params) {
        this.params = params;
    }

    public LinkedList<Statement> getHeader() {
        return header;
    }

    public void setHeader(LinkedList<Statement> header) {
        this.header = header;
    }

    public LinkedList<Statement> getMethodBody() {
        return methodBody;
    }

    public void setMethodBody(LinkedList<Statement> methodBody) {
        this.methodBody = methodBody;
    }
}
