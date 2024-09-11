package edu.robertob.p1compi2.engine.statements;

import edu.robertob.p1compi2.engine.base.Statement;
import edu.robertob.p1compi2.engine.structs.PError;
import edu.robertob.p1compi2.engine.structs.SymbolTable;
import edu.robertob.p1compi2.engine.structs.Tree;
import edu.robertob.p1compi2.engine.structs.TypesTable;
import edu.robertob.p1compi2.engine.utils.TypeUtils;

import java.util.LinkedList;

public class TypeDeclaration extends Statement {
//    private String name;
    private LinkedList<String> names;
    private String scopeName;
    private int parentTypeId;
    private String parentTypeName;
    private boolean isArray;
    private boolean isRange;
    private boolean isRecord;
    private int size;
    private int start;
    private int end;

    public TypeDeclaration(int line, int column, LinkedList<String> names, int parentTypeId, boolean isArray, boolean isRange, boolean isRecord, int size, int start, int end) {
        super(-1, line, column);
        this.names = names;
        this.parentTypeId = parentTypeId;
          this.isArray = isArray;
        this.isRange = isRange;
        this.isRecord = isRecord;
        this.size = size;
        this.start = start;
        this.end = end;
    }

    public TypeDeclaration(int line, int column, LinkedList<String> names, String parentTypeName, boolean isArray, boolean isRange, boolean isRecord, int size, int start, int end) {
        super(-1, line, column);
        this.names = names;
        this.parentTypeName = parentTypeName;
        this.isArray = isArray;
        this.isRange = isRange;
        this.isRecord = isRecord;
        this.size = size;
        this.start = start;
        this.end = end;
    }



    @Override
    public Object execute(Tree tree, SymbolTable table, TypesTable typesTable) {
//        for(var entry : typesTable.getTypes().entrySet()) {
//            System.out.println("Entry: "+((TypesTable.TypeTableEntry)entry.getValue()).name);
//        }
        if (!isArray && !isRange && !isRecord && (parentTypeName == null)) {
            System.out.println("Parent type id: " + parentTypeId);
            for (String name : names) {
                System.out.println(name);
                var newType = new TypesTable.TypeTableEntry(typesTable.getIdCounter(), name, parentTypeId,  TypeUtils.recursivelyResolveBaseType(parentTypeId, typesTable), size, isArray, isRecord, isRange, null, null);
                boolean created = typesTable.setType(newType);
                if (!created) {
                    return new PError("Semantica", "Tipo " + name + " ya está declarado", this.line, this.column);
                }
            }
        } else if (!isArray && !isRange && !isRecord && (parentTypeName != null)) {
            for (String name : names) {
                if (typesTable.getType(parentTypeName) == null){
                    tree.addError(new PError("Semantica", "Tipo " + parentTypeName + " no existe (o ha sido declarado despues)", this.line, this.column));
                    return new PError("Semantica", "Tipo " + parentTypeName + " no existe (o ha sido declarado despues)", this.line, this.column);
                }

                var newType = new TypesTable.TypeTableEntry(typesTable.getIdCounter(), name, typesTable.getType(parentTypeName).id, TypeUtils.recursivelyResolveBaseType(typesTable.getType(parentTypeName).id, typesTable), size, isArray, isRecord, isRange, null, null);
                boolean created = typesTable.setType(newType);
                if (!created) {
                    tree.addError(new PError("Semantica", "Tipo " + name + " ya está declarado", this.line, this.column));
                    return new PError("Semantica", "Tipo " + name + " ya está declarado", this.line, this.column);
                }
            }
        } else if (isArray || isRange) {
            if ((int) this.end < (int) this.start) {
                tree.addError(new PError("Semantica", "Valor máximo de rango/arreglo debe ser mayor que el valor mínimo", this.line, this.column));
                return new PError("Semantica", "Valor máximo de rango/arreglo debe ser mayor que el valor mínimo", this.line, this.column);
            }
            for (String name : names) {
                System.out.println(name);
                var newType = new TypesTable.TypeTableEntry(typesTable.getIdCounter(), name, parentTypeId, TypeUtils.recursivelyResolveBaseType(parentTypeId, typesTable),size, isArray, isRecord, isRange, start, end);
                boolean created = typesTable.setType(newType);
                if (!created) {
                    tree.addError(new PError("Semantica", "Tipo " + name + " ya está declarado", this.line, this.column));
                    return new PError("Semantica", "Tipo " + name + " ya está declarado", this.line, this.column);
                }
            }
        }
//        else if (isArray || isRange) {
//            for (String name : names) {
//                var newType = new TypesTable.TypeTableEntry(typesTable.getIdCounter(), name, parentTypeId, size, isArray, isRecord, isRange, start, end);
//                boolean created = typesTable.setType(newType);
//                if (!created) {
//                    return new PError("Semantica", "Tipo " + name + " ya está declarado", this.line, this.column);
//                }
//            }
//        }
//        if (isArray || isRange) {
//            var newType = new TypesTable.TypeTableEntry(typesTable.getIdCounter(), name, parentTypeId, size, isArray, isRecord, isRange, start, end);
//            boolean created = typesTable.setType(newType);
//            if (!created) {
//                return new PError("Semantica", "Tipo " + name + " ya está declarado", this.line, this.column);
//            }
//        }

        return null;
    }

    public static class ArrayTypeDeclarationHelper {
        private Object[] rangeDefinition;
        private int parentTypeId;
        private String parentTypeName;

        public ArrayTypeDeclarationHelper(Object[] rangeDefinition, int parentTypeId) {
            this.rangeDefinition = rangeDefinition;
            this.parentTypeId = parentTypeId;
            this.parentTypeName = parentTypeName;
        }

        public ArrayTypeDeclarationHelper(Object[] rangeDefinition, String parentTypeName) {
            this.rangeDefinition = rangeDefinition;
            this.parentTypeId = parentTypeId;
            this.parentTypeName = parentTypeName;
        }

        public Object[] getRangeDefinition() {
            return rangeDefinition;
        }

        public int getParentTypeId() {
            return parentTypeId;
        }

        public String getParentTypeName() {
            return parentTypeName;
        }
    }

    public LinkedList<String> getNames() {
        return names;
    }

    public void setNames(LinkedList<String> names) {
        this.names = names;
    }

    public String getScopeName() {
        return scopeName;
    }

    public void setScopeName(String scopeName) {
        this.scopeName = scopeName;
    }

    public int getParentTypeId() {
        return parentTypeId;
    }

    public void setParentTypeId(int parentTypeId) {
        this.parentTypeId = parentTypeId;
    }

    public boolean isArray() {
        return isArray;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
