package edu.robertob.p1compi2.engine.structs;

//import edu.robertob.olc1.vj24.Engine.Base.Instruction;
//import edu.robertob.olc1.vj24.Engine.Statements.MethodDeclaration;

import edu.robertob.p1compi2.engine.statements.FunctionDeclaration;
import edu.robertob.p1compi2.engine.statements.ProcedureDeclaration;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SymbolTable {
    private SymbolTable parentTable;
    private HashMap<String, Object> symbols;
    private LinkedList<SymbolTable> children;
    private String name;

    private LinkedList<FunctionDeclaration> functions;
    private LinkedList<ProcedureDeclaration> procedures;

    public SymbolTable(String name) {
        this.parentTable = null;
        this.children = new LinkedList<>();
        this.symbols = new HashMap<>();
        this.name = name;
        this.functions = new LinkedList<>();
        this.procedures = new LinkedList<>();
    }

//    public boolean setSymbol(SymbolVariable symbol) {
////        SymbolVariable search = (SymbolVariable) this.getSymbol(symbol.getId());
////        if (search == null) {
////            this.symbols.put(symbol.getId().toLowerCase(), symbol);
////            return true;
////        }
////        return false;
//        if (this.symbols.containsKey(symbol.getId().toLowerCase())) {
//            return false;
//        }
//        this.symbols.put(symbol.getId().toLowerCase(), symbol);
//        return true;
//
//    }

//    public SymbolVariable getSymbol(String id) {
//        for (SymbolTable table = this; table != null; table = table.getParentTable()) {
//
//            SymbolVariable symbol = (SymbolVariable) table.symbols.get(id.toLowerCase());
//            if (symbol != null) return symbol;
//        }
//        return null;
//    }

    public SymbolTable(SymbolTable parentTable) {
        this.parentTable = parentTable;
        this.children = new LinkedList<>();
        this.symbols = new HashMap<>();
        this.name = "";
    }

    public void addFunction(FunctionDeclaration function) {
        if (this.functions == null) {
            this.functions = new LinkedList<>();
        }
        this.functions.add(function);
    }

    public void addProcedure(ProcedureDeclaration procedure) {
        if (this.procedures == null) {
            this.procedures = new LinkedList<>();
        }
        this.procedures.add(procedure);
    }

    public SymbolVariable getSymbol(String id) {
        for (SymbolTable table = this; table != null; table = table.getParentTable()) {

            SymbolVariable symbol = (SymbolVariable) table.symbols.get(id.toLowerCase());
            if (symbol != null) return symbol;
        }
        return null;
    }

    public boolean setSymbol(SymbolVariable symbol) {
        if (this.symbols.containsKey(symbol.getId().toLowerCase())) {
            return false;
        }
        this.symbols.put(symbol.getId().toLowerCase(), symbol);
        return true;
    }

    public SymbolTable getParentTable() {
        return parentTable;
    }

    public void setParentTable(SymbolTable parentTable) {
        this.parentTable = parentTable;
    }

    public HashMap<String, Object> getSymbols() {
        return symbols;
    }

    public void setSymbols(HashMap<String, Object> symbols) {
        this.symbols = symbols;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<SymbolTable> getChildren() {
        return children;
    }

    public void setChildren(LinkedList<SymbolTable> children) {
        this.children = children;
    }

//    public List<SymbolTable> collectAllSymbolTables() {
//        List<SymbolTable> allTables = new LinkedList<>();
//        allTables.add(this);
//        for (SymbolTable child : children) {
//            allTables.addAll(child.collectAllSymbolTables());
//        }
//        return allTables;
//    }

//    public Map<String, Object> collectAllSymbols() {
//        Map<String, Object> allSymbols = new HashMap<>();
////        collectSymbolsRecursively(this, allSymbols);
//        return allSymbols;
//    }

//    public void addMethods(Tree tree){
//        for (Instruction i : tree.getMethods()) {
//            if (i instanceof MethodDeclaration method) {
//                this.setSymbol(new SymbolVariable(Types.METHOD, true, method.getId(), method, method.line, method.column));
//            }
//        }
//    }

//    private void collectSymbolsRecursively(SymbolTable table, Map<String, Object> allSymbols) {
//        for (Map.Entry<String, Object> entry : table.symbols.entrySet()) {
//            ((SymbolVariable)entry.getValue()).setScopeName(table.name);
//            allSymbols.put(table.name + ":" + entry.getKey(), entry.getValue());
//        }
//        if (table.children == null) return;
//        for (SymbolTable child : table.children) {
//            collectSymbolsRecursively(child, allSymbols);
//        }
//    }

    public LinkedList<SymbolVariable> collectAllEntries() {
        LinkedList<SymbolVariable> allEntries = new LinkedList<>();
        collectEntriesRecursive(this, allEntries);
        return allEntries;
    }

    public void collectEntriesRecursive(SymbolTable symbolTable, LinkedList<SymbolVariable> allEntries) {
        allEntries.addAll(symbolTable.symbols.values().stream().map(symbol -> {
            SymbolVariable symbolVariable = (SymbolVariable) symbol;
            symbolVariable.setScopeName(symbolTable.name);
            return symbolVariable;
        }).toList());
        for (SymbolTable child : symbolTable.children) {
            collectEntriesRecursive(child, allEntries);
        }
    }

    public LinkedList<FunctionDeclaration> getFunctions() {
        return functions;
    }

    public LinkedList<ProcedureDeclaration> getProcedures() {
        return procedures;
    }
}
