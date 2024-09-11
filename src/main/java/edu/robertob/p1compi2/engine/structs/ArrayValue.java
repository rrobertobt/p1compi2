package edu.robertob.p1compi2.engine.structs;

import java.util.LinkedList;

public class ArrayValue {
    private int typeId;
    //we'll omit dimension because we'll work with 1D arrays
    private LinkedList<Object> values;
    private int size;
    private int lowerBound;
    private int upperBound;

    public ArrayValue(int typeId, LinkedList<Object> values, int size, int lowerBound, int upperBound) {
        this.typeId = typeId;
        this.values = values;
        this.size = size;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    private int convertIndex(int pascalIndex) {
        if (pascalIndex < lowerBound || pascalIndex > upperBound) {
            throw new IndexOutOfBoundsException("Index out of range: " + pascalIndex);
        }
        return pascalIndex - lowerBound;
    }

    // Set value at the Pascal index
    public void set(int pascalIndex, Object value) {
        int internalIndex = convertIndex(pascalIndex);
        this.values.set(internalIndex, value);
    }

    // Get value at the Pascal index
    public Object get(int pascalIndex) {
        int internalIndex = convertIndex(pascalIndex);
        return this.values.get(internalIndex);
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public LinkedList<Object> getValues() {
        return values;
    }

    public void setValues(LinkedList<Object> values) {
        this.values = values;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(int lowerBound) {
        this.lowerBound = lowerBound;
    }

    public int getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(int upperBound) {
        this.upperBound = upperBound;
    }
}
