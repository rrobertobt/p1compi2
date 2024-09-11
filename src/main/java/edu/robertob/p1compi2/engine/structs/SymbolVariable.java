package edu.robertob.p1compi2.engine.structs;

public class SymbolVariable {
    private int typeId;
    private String id;
    private String scopeName;
    private Object value;
    private boolean constant;
    private boolean isMethod;
    private boolean isMethodParam;
    private int line;
    private int column;
    private int originalTypeId;

    private boolean isArray;
    private boolean isRange;
    private boolean isRecord;

    private Object minVal;
    private Object maxVal;

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
        this.isArray = true;
    }

    public SymbolVariable(int typeId, boolean constant, String id, SubrangeValue value, int line, int column) {
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

    public int getOriginalTypeId() {
        return originalTypeId;
    }

    public void setOriginalTypeId(int originalTypeId) {
        this.originalTypeId = originalTypeId;
    }

    public boolean isArray() {
        return isArray;
    }

    public void setIsArray(boolean isArray) {
        this.isArray = isArray;
    }

    public void setIsRecord(boolean isRecord) {
        this.isRecord = isRecord;
    }


    public  void setIsRange(boolean isRange) {
        this.isRange = isRange;
    }

    public void setArray(boolean array) {
        isArray = array;
    }

    public boolean isRange() {
        return isRange;
    }

    public void setRange(boolean range) {
        isRange = range;
    }

    public boolean isRecord() {
        return isRecord;
    }

    public void setRecord(boolean record) {
        isRecord = record;
    }

    public Object getMinVal() {
        return minVal;
    }

    public void setMinVal(Object minVal) {
        this.minVal = minVal;
    }

    public Object getMaxVal() {
        return maxVal;
    }

    public void setMaxVal(Object maxVal) {
        this.maxVal = maxVal;
    }

    public boolean isMethod() {
        return isMethod;
    }

    public void setMethod(boolean isMethod) {
        this.isMethod = isMethod;
    }

    public String toString() {
        return this.id + " - " + this.typeId + " - " + this.value + " - " + this.constant + " - " + this.isRange + " - " + this.isArray + " - " + this.isRecord;
    }

    public String getCategory() {
        if (this.isMethod) {
            return "Funcion/Procedimiento";
        } else if (this.isConstant()) {
            return "Constante";
        } else if (this.isMethodParam) {
            return "Parametro";
        } else {
            return "Variable";
        }
    }
}
