package edu.robertob.p1compi2.engine.structs;

public class SymbolVariable {
    private int typeId;
    private String id;
    private String scopeName;
    private Object value;
    private boolean constant;
    private int line;
    private int column;

    public SymbolVariable(int typeId, String id) {
        this.typeId = typeId;
        this.id = id;
    }

    public SymbolVariable(int typeId, boolean constant, String id, Object value, int line, int column) {
        this.typeId = typeId;
        this.constant = constant;
        this.id = id;
        this.value = value;
        this.line = line;
        this.column = column;
    }

    public SymbolVariable(int typeId, boolean constant, String id, ArrayValue value, int line, int column) {
        this.typeId = typeId;
        this.constant = constant;
        this.id = id;
        this.value = value;
        this.line = line;
        this.column = column;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScopeName() {
        return scopeName;
    }

    public void setScopeName(String scopeName) {
        this.scopeName = scopeName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isConstant() {
        return constant;
    }

    public void setConstant(boolean constant) {
        this.constant = constant;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
