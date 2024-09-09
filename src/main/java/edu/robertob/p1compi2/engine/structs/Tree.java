package edu.robertob.p1compi2.engine.structs;

import edu.robertob.p1compi2.engine.base.Statement;

import java.util.LinkedList;

public class Tree {
    private LinkedList<Statement> statements;
//    private String console;
    private SymbolTable globalTable;
    public LinkedList<PError> errors;
//    private HashMap<String, StructDefinition> structs;
    private LinkedList<Statement> methods;
//    private int graphNodeCounter = 0;

    public Tree(LinkedList<Statement> instructions) {
        this.statements = instructions;
        this.globalTable = new SymbolTable("");
        this.errors = new LinkedList<>();
//        this.structs = new HashMap<>();
        this.methods = new LinkedList<>();
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

    public LinkedList<Statement> getMethods() {
        return methods;
    }

    public void setErrors(LinkedList<PError> errors) {
        this.errors = errors;
    }
}
