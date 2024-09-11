package edu.robertob.p1compi2.engine.utils;

import edu.robertob.p1compi2.engine.expressions.Primitive;
import edu.robertob.p1compi2.engine.structs.TypesTable;

import java.util.LinkedList;

public class ArrayValueUtils {
    public static void fillArrayWithPrimitives(int typeId, LinkedList<Object> listToFill, int size, int lowerIndex, int upperIndex, int line, int column) {
        // Ensure the list is initialized to the correct size
        if (listToFill.size() < size) {
            for (int i = 0; i < size; i++) {
                listToFill.add(null);  // Initialize with null or appropriate default
            }
        }

        // Fill the list with default primitive values based on the typeId
        switch (typeId) {
            case TypesTable.DefaultIds.INTEGER -> {
                for (int i = lowerIndex; i <= upperIndex; i++) {
                    int internalIndex = i - lowerIndex; // Convert Pascal index to LinkedList index
                    listToFill.set(internalIndex, new Primitive(TypesTable.DefaultTypes.INTEGER.id, line, column, 0));
                }
            }
            case TypesTable.DefaultIds.REAL -> {
                for (int i = lowerIndex; i <= upperIndex; i++) {
                    int internalIndex = i - lowerIndex;
                    listToFill.set(internalIndex, new Primitive(TypesTable.DefaultTypes.REAL.id, line, column, 0.0));
                }
            }
            case TypesTable.DefaultIds.BOOLEAN -> {
                for (int i = lowerIndex; i <= upperIndex; i++) {
                    int internalIndex = i - lowerIndex;
                    listToFill.set(internalIndex, new Primitive(TypesTable.DefaultTypes.BOOLEAN.id, line, column, false));
                }
            }
            case TypesTable.DefaultIds.CHARACTER -> {
                for (int i = lowerIndex; i <= upperIndex; i++) {
                    int internalIndex = i - lowerIndex;
                    listToFill.set(internalIndex, new Primitive(TypesTable.DefaultTypes.CHARACTER.id, line, column, '\u0000'));
                }
            }
            case TypesTable.DefaultIds.STRING -> {
                for (int i = lowerIndex; i <= upperIndex; i++) {
                    int internalIndex = i - lowerIndex;
                    listToFill.set(internalIndex, new Primitive(TypesTable.DefaultTypes.STRING.id, line, column, ""));
                }
            }
        }
    }

}
