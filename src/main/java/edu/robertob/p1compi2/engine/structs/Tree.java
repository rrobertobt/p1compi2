package edu.robertob.p1compi2.engine.structs;

import edu.robertob.p1compi2.engine.base.Statement;
import edu.robertob.p1compi2.engine.statements.FunctionDeclaration;
import edu.robertob.p1compi2.engine.statements.ProcedureDeclaration;

import java.util.LinkedList;

public class Tree {
    private LinkedList<Statement> statements;
    private SymbolTable globalTable;
    public LinkedList<PError> errors;
    private LinkedList<Statement> functions;
    private LinkedList<Statement> procedures;

    public Tree(LinkedList<Statement> instructions) {
        this.statements = instructions;
        this.globalTable = new SymbolTable("");
        this.errors = new LinkedList<>();
//        this.structs = new HashMap<>();
        this.functions = new LinkedList<>();
        this.procedures = new LinkedList<>();
    }

    public boolean addFunction(FunctionDeclaration function) {
        // check if function already exists
        for (Statement i : this.functions) {
            if (i instanceof FunctionDeclaration f) {
                if (f.getId().equalsIgnoreCase(function.getId())) {
                    return false;
                }
            }
        }
        // also check if a procedure with the same name exists
        for (Statement i : this.procedures) {
            if (i instanceof ProcedureDeclaration p) {
                if (p.getId().equalsIgnoreCase(function.getId())) {
                    return false;
                }
            }
        }
        this.functions.add(function);
        return true;
    }

    public boolean addProcedure(ProcedureDeclaration procedure) {
        // check if procedure already exists
        for (Statement i : this.procedures) {
            if (i instanceof ProcedureDeclaration p) {
                if (p.getId().equalsIgnoreCase(procedure.getId())) {
                    return false;
                }
            }
        }

        // also check if a function with the same name exists
        for (Statement i : this.functions) {
            if (i instanceof FunctionDeclaration f) {
                if (f.getId().equalsIgnoreCase(procedure.getId())) {
                    return false;
                }
            }
        }
        this.procedures.add(procedure);
        return true;
    }

    public FunctionDeclaration getFunction(String id) {
        for (Statement i : this.functions) {
            if (i instanceof FunctionDeclaration function) {
                if (function.getId().equalsIgnoreCase(id)) {
                    return function;
                }
            }
        }
        return null;
    }

    public ProcedureDeclaration getProcedure(String id) {
        for (Statement i : this.procedures) {
            if (i instanceof ProcedureDeclaration procedure) {
                if (procedure.getId().equalsIgnoreCase(id)) {
                    return procedure;
                }
            }
        }
        return null;
    }

    public LinkedList<Statement> getFunctions() {
        return functions;
    }

    public LinkedList<Statement> getProcedures() {
        return procedures;
    }

//    public void addMethod(Statement method) {
//        this.methods.add((MethodDeclaration) method);
//    }

//    public boolean setStruct(StructDefinition struct) {
//        if (structs.containsKey(struct.getStructName())) {
//            return false;
//        }
//        structs.put(struct.getStructName(), struct);
//        return true;
//    }

//    public StructDefinition getStruct(String id) {
//        return structs.get(id);
//    }

    public Statement getMethod(String id) {
        for (Statement i : this.functions) {
            if (i instanceof FunctionDeclaration function) {
                if (function.getId().equalsIgnoreCase(id)) {
                    return function;
                }
            }
        }

        for (Statement i : this.procedures) {
            if (i instanceof ProcedureDeclaration procedure) {
                if (procedure.getId().equalsIgnoreCase(id)) {
                    return procedure;
                }
            }
        }
        return null;
    }

    public void addError(PError error) {
        this.errors.add(error);
    }

    public LinkedList<Statement> getStatements() {
        return statements;
    }

    public void setInstructions(LinkedList<Statement> instructions) {
        this.statements = instructions;
    }

    public SymbolTable getGlobalTable() {
        return globalTable;
    }

    public void setGlobalTable(SymbolTable globalTable) {
        this.globalTable = globalTable;
    }

    public LinkedList<PError> getErrors() {
        return errors;
    }

    public void setErrors(LinkedList<PError> errors) {
        this.errors = errors;
    }

//    public Object getMethod(String methodName) {
//    }
}
