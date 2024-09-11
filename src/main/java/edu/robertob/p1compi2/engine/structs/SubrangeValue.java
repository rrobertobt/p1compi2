package edu.robertob.p1compi2.engine.structs;

import edu.robertob.p1compi2.engine.base.Statement;
import edu.robertob.p1compi2.engine.expressions.Primitive;

public class SubrangeValue {
    private final int lowerBound; // Lower bound of the subrange
    private final int upperBound; // Upper bound of the subrange
    private Object value;

    public SubrangeValue(int lowerBound, int upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.value = new Primitive(TypesTable.DefaultIds.INTEGER, 0,0, this.lowerBound);
    }

    public Object getValue() {
        return value;
    }

    // Setter for the current value, ensures it is within the subrange
    public void setValue(int newValue) {
        if (newValue < lowerBound || newValue > upperBound) {
            throw new IllegalArgumentException("Value must be within the defined subrange.");
        }
        this.value = new Primitive(TypesTable.DefaultIds.INTEGER, 0,0, newValue);
    }

    // Method to check if a value is within the range
    public boolean isWithinRange(int valueToCheck) {
        return valueToCheck >= lowerBound && valueToCheck <= upperBound;
    }
}
