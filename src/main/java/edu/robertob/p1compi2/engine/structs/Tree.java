package edu.robertob.p1compi2.engine.structs;

import edu.robertob.p1compi2.engine.base.Statement;
import edu.robertob.p1compi2.engine.statements.FunctionDeclaration;

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

    public void addFunction(FunctionDeclaration function) {
        this.functions.add(function);
    }

    public void addProcedure(FunctionDeclaration procedure) {
        this.procedures.add(procedure);
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

//    public Statement getMethod(String id) {
//        for (Statement i : this.methods) {
//            if (i instanceof MethodDeclaration method && method.getId().equalsIgnoreCase(id)) {
//                return method;
//            }
//        }
//        return null;
//    }

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
}
