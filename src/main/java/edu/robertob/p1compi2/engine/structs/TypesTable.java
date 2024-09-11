package edu.robertob.p1compi2.engine.structs;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class TypesTable {
    private TypesTable parentTable;
    private HashMap<String, Object> types;
    private LinkedList<TypesTable> childrenTables;
    private String name;
    private int idCounter = 5;

    public TypesTable(String name) {
        this.parentTable = null;
        this.childrenTables = new LinkedList<>();
        this.types = new HashMap<>();
        this.name = name;
    }

    public TypesTable(TypesTable parentTable) {
        this.parentTable = parentTable;
        this.childrenTables = new LinkedList<>();
        this.types = new HashMap<>();
        this.name = "";
    }

    public boolean setType(TypeTableEntry type) {
        if (this.types.containsKey(type.name.toLowerCase())) {
            return false;
        }
        this.types.put(type.name.toLowerCase(), type);
        return true;
    }

    public TypeTableEntry getType(String name) {
        for (TypesTable table = this; table != null; table = table.getParentTable()) {
            TypeTableEntry type = (TypeTableEntry) table.types.get(name.toLowerCase());
            if (type != null) return type;
        }
        return null;
    }

    public TypeTableEntry getType(int id) {
        for (TypesTable table = this; table != null; table = table.getParentTable()) {
            for (Object type : table.types.values()) {
                if (((TypeTableEntry) type).id == id) return (TypeTableEntry) type;
            }
        }
        return null;
    }

    public int getIdCounter() {
        idCounter++;
        return idCounter;
    }

    public TypesTable getParentTable() {
        return parentTable;
    }

    public void setParentTable(TypesTable parentTable) {
        this.parentTable = parentTable;
    }

    public HashMap<String, Object> getTypes() {
        return types;
    }

    public void setTypes(HashMap<String, Object> types) {
        this.types = types;
    }

    public LinkedList<TypesTable> getChildrenTables() {
        return childrenTables;
    }

    public void setChildrenTables(LinkedList<TypesTable> childrenTables) {
        this.childrenTables = childrenTables;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPrimitiveType(TypeTableEntry type) {
        return type.id <= DefaultTypes.STRING.id;
    }

    public TypeTableEntry getParentType(TypeTableEntry type) {
        return this.getType(type.parentTypeId);
    }

    public LinkedList<TypeTableEntry> collectAllEntries() {
        LinkedList<TypeTableEntry> allEntries = new LinkedList<>();
        collectEntriesRecursive(this, allEntries);
        return allEntries;
    }

    private void collectEntriesRecursive(TypesTable table, LinkedList<TypeTableEntry> allEntries) {
        allEntries.addAll(table.types.values().stream().map(type -> {
            TypeTableEntry entry = (TypeTableEntry) type;
            entry.scopeName = table.name;
            return entry;
        }).collect(Collectors.toList()));
        for (TypesTable child : table.childrenTables) {
            collectEntriesRecursive(child, allEntries);
        }
    }

    public static class TypeTableEntry {
        public int id;
        public String name;
        public String type;
        public int size;
        public int parentTypeId;
        public int baseTypeId;
        public boolean isArray;
        public boolean isRange;
        public boolean isRecord;
        public Object recordFields;
        public Object minVal;
        public Object maxVal;
        public String scopeName;


        public TypeTableEntry() {
        }

        public TypeTableEntry(
                int id, String name, int parentTypeId, int baseTypeId, int size, boolean isArray, boolean isRecord, boolean isRange, Object minVal, Object maxVal
        ) {
            this.id = id;
            this.name = name;
            this.parentTypeId = parentTypeId;
            this.baseTypeId = baseTypeId;
            this.size = size;
            this.isArray = isArray;
            this.isRecord = isRecord;
            this.isRange = isRange;
            this.minVal = minVal;
            this.maxVal = maxVal;
        }

        public String toString() {
            return "TypeTableEntry{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", type='" + type + '\'' +
                    ", size=" + size +
                    ", parentTypeId=" + parentTypeId +
                    ", isArray=" + isArray +
                    ", isRange=" + isRange +
                    ", isRecord=" + isRecord +
                    ", recordFields=" + recordFields +
                    ", minVal=" + minVal +
                    ", maxVal=" + maxVal +
                    '}';
        }
    }


    public void fillDefaultTypes() {
        TypeTableEntry integer = new TypeTableEntry();
        integer.id = DefaultTypes.INTEGER.id;
        integer.name = "integer";
        integer.type = "int";
        integer.size = 1;
        integer.parentTypeId = -1;
        integer.baseTypeId = -1;
        integer.isArray = false;
        integer.isRange = false;
        integer.minVal = null;
        integer.maxVal = null;
        this.types.put("integer", integer);

        TypeTableEntry real = new TypeTableEntry();
        real.id = DefaultTypes.REAL.id;
        real.name = "real";
        real.type = "real";
        real.size = 1;
        real.parentTypeId = -1;
        real.baseTypeId = -1;
        real.isArray = false;
        real.isRange = false;
        real.minVal = null;
        real.maxVal = null;
        this.types.put("real", real);

        TypeTableEntry character = new TypeTableEntry();
        character.id = DefaultTypes.CHARACTER.id;
        character.name = "character";
        character.type = "char";
        character.size = 1;
        character.parentTypeId = -1;
        character.baseTypeId = -1;
        character.isArray = false;
        character.isRange = false;
        character.minVal = null;
        character.maxVal = null;
        this.types.put("character", character);

        TypeTableEntry string = new TypeTableEntry();
        string.id = DefaultTypes.STRING.id;
        string.name = "string";
        string.type = "string";
        string.size = 1;
        string.parentTypeId = -1;
        string.baseTypeId = -1;
        string.isArray = false;
        string.isRange = false;
        string.minVal = null;
        string.maxVal = null;
        this.types.put("string", string);

        TypeTableEntry booleanType = new TypeTableEntry();
        booleanType.id = DefaultTypes.BOOLEAN.id;
        booleanType.name = "boolean";
        booleanType.type = "boolean";
        booleanType.size = 1;
        booleanType.parentTypeId = -1;
        booleanType.baseTypeId = -1;
        booleanType.isArray = false;
        booleanType.isRange = false;
        booleanType.minVal = null;
        booleanType.maxVal = null;
        this.types.put("boolean", booleanType);

        TypeTableEntry voidType = new TypeTableEntry();
        voidType.id = DefaultTypes.VOID.id;
        voidType.name = "void";
        voidType.type = "void";
        voidType.size = 0;
        voidType.parentTypeId = -1;
        voidType.baseTypeId = -1;
        voidType.isArray = false;
        voidType.isRange = false;
        voidType.minVal = null;
        voidType.maxVal = null;
        this.types.put("void", voidType);
    }

    public enum DefaultTypes {
        VOID(0),
        INTEGER(1),
        REAL(2),
        CHARACTER(3),
        BOOLEAN(4),
        STRING(5);

        public final int id;

        DefaultTypes(int id) {
            this.id = id;
        }
    }

    public class DefaultIds {
        public static final int VOID = 0;
        public static final int INTEGER = 1;
        public static final int REAL = 2;
        public static final int CHARACTER = 3;
        public static final int BOOLEAN = 4;
        public static final int STRING = 5;

        public static final int[] asArray = {VOID, INTEGER, REAL, CHARACTER, BOOLEAN, STRING};
    }

}
