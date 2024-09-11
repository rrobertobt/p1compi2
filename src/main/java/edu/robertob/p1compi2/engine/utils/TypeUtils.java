package edu.robertob.p1compi2.engine.utils;

import edu.robertob.p1compi2.engine.structs.TypesTable;

public class TypeUtils {
    public static int recursivelyResolveBaseType(int parentTypeId, TypesTable typesTable) {
        if (parentTypeId < 6 && parentTypeId >= 0) {
            return parentTypeId;
        } else {
            return recursivelyResolveBaseType(typesTable.getType(parentTypeId).parentTypeId, typesTable);
        }
    }
}
