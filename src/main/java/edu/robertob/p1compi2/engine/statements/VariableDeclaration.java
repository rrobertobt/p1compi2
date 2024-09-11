package edu.robertob.p1compi2.engine.statements;

import edu.robertob.p1compi2.engine.base.Statement;
import edu.robertob.p1compi2.engine.expressions.Primitive;
import edu.robertob.p1compi2.engine.structs.*;
import edu.robertob.p1compi2.engine.utils.ArrayValueUtils;

import java.util.Collections;
import java.util.LinkedList;

public class VariableDeclaration extends Statement {
    // helper values
    private LinkedList<String> ids;
    private Statement value;
    private boolean isArray;
    private boolean isRange;
    private boolean isRecord;

    private Object minVal;
    private Object maxVal;

    // in case of a new type, we need to know the parent type
    // if it is not a primitive type
    private String parentTypeName;

    private int originalParentTypeId;

    // for primitive types
    private int parentTypeId;

    public VariableDeclaration(
            int typeId, int line, int column, LinkedList<String> ids, Statement value, boolean isArray, boolean isRange, boolean isRecord, Object minVal, Object maxVal, String parentTypeName, int parentTypeId
    ) {
        super(typeId, line, column);
        this.ids = ids;
        this.value = value;
        this.isArray = isArray;
        this.isRange = isRange;
        this.isRecord = isRecord;
        this.minVal = minVal;
        this.maxVal = maxVal;
        this.parentTypeName = parentTypeName;
        this.parentTypeId = parentTypeId;
    }

    private int recursivelyResolveParentType(int parentTypeId, TypesTable typesTable) {
        if (parentTypeId < 6 && parentTypeId >= 0) {
            return parentTypeId;
        } else {
            return recursivelyResolveParentType(typesTable.getType(parentTypeId).parentTypeId, typesTable);
        }
    }

    @Override
    public Object execute(Tree tree, SymbolTable table, TypesTable typesTable) {

        // if a new type is defined within the variable declaration, we have to register it
        // but only if it does not exist already

        // check if there are duplicate ids
        for (int i = 0; i < ids.size(); i++) {
            for (int j = i + 1; j < ids.size(); j++) {
                if (ids.get(i).equals(ids.get(j))) {
                    var err = new PError("Semantica", "Identificador duplicado " + ids.get(i) + " en declaración de variables", this.line, this.column);
                    tree.addError(err);
                    return err;
                }
            }
        }

        boolean createType = false;
        if (this.parentTypeName != null && typesTable.getType(this.parentTypeName) == null) {
            createType = true;
        } else if (this.parentTypeName == null && typesTable.getType(this.parentTypeId) == null) {
            createType = true;
        }
//        System.out.println("VariableDeclaration.willCreateType: "+this.parentTypeName+ typesTable.getType(this.parentTypeId));

        if ((isArray || isRange || isRecord)) {
            System.out.println("VariableDeclaration.createType: id = " + ids + ", value = " + this.value + "parentTypeId = " + this.parentTypeId);
            final String[] idName = {""};
            ids.forEach(id -> {
                idName[0] = idName[0] + id;
            });
            String typeName = generateTypeName(idName[0]);
//            int parentTypeId;
//            if (this.parentTypeName != null) {
//                parentTypeId = typesTable.getType(this.parentTypeName).id;
//            } else {
//                parentTypeId = this.parentTypeId;
//            }
            TypesTable.TypeTableEntry parentType;
            if (this.parentTypeName != null) {
                parentType = typesTable.getType(this.parentTypeName);
            } else {
                parentType = typesTable.getType(this.parentTypeId);
            }
            this.parentTypeId = parentType.id;
            this.originalParentTypeId = parentType.id;

            int newTypeId = recursivelyResolveParentType(this.parentTypeId, typesTable);
            this.parentTypeId = newTypeId;
            this.typeId = newTypeId;

            TypeDeclaration newType = new TypeDeclaration(
                    this.line,
                    this.column,
                    new LinkedList<>(Collections.singletonList(typeName)),
                    parentTypeId,
                    isArray,
                    isRange,
                    isRecord,
                    calculateSize(),
                    (int) minVal,
                    (int) maxVal
            );
            System.out.println("VariableDeclaration.createType: id = " + ids + ", value = " + this.value + "parentTypeId = " + this.parentTypeId);
            System.out.println("generating type: " + typeName);
            Object result = newType.execute(tree, table, typesTable);
            this.typeId = typesTable.getType(typeName).id;
//            if (result instanceof PError) {
//                return result; // Return error if type registration fails
//            }
            // Register the variable in the symbol table
            if (isArray) {
                for (String id : ids) {
                    var listOfVals = new LinkedList<>();
                    ArrayValueUtils.fillArrayWithPrimitives(this.parentTypeId, listOfVals, calculateSize(), (int) this.minVal, (int) this.maxVal, this.line, this.column);
                    var arrayVal = new ArrayValue(this.parentTypeId, listOfVals, this.calculateSize(), (int) this.minVal, (int) this.maxVal);
                    SymbolVariable symbol = new SymbolVariable(this.typeId, false, id, arrayVal, this.line, this.column);
                    System.out.println(arrayVal.getValues());
                    System.out.println(arrayVal.get(0));
                    System.out.println(arrayVal.get(4));
                    System.out.println(arrayVal.get(25));

                    boolean created = table.setSymbol(symbol);
                    if (!created) {
                        return new PError("Semantica", "Variable " + id + " ya se ha declarado", this.line, this.column);
                    }
                }
            }
            return null;
        } else {
            TypesTable.TypeTableEntry parentType;
            if (this.parentTypeName != null) {
                parentType = typesTable.getType(this.parentTypeName);
            } else {
                parentType = typesTable.getType(this.parentTypeId);
            }
            this.typeId = parentType.id;
            this.parentTypeId = parentType.id;
            this.originalParentTypeId = parentType.id;
            System.out.println("original type"+typesTable.getType(this.parentTypeId).name);

            int newType = recursivelyResolveParentType(this.parentTypeId, typesTable);
            this.typeId = newType;
            this.parentTypeId = newType;

        }

        // Register the variable in the symbol table
        for (String id : ids) {
            // resolve the parentTypeId (if applies)
            System.out.println("VariableDeclaration.execute: id = " + id + ", value = " + this.value + "parentTypeId = " + this.parentTypeId);
//            if (this.parentTypeName != null) {
//                this.parentTypeId = typesTable.getType(this.parentTypeName).id;
//            } else if (this.parentTypeId > 5) {
//                this.parentTypeId = typesTable.getType(this.parentTypeId).id;
//            }

            // before, depending on the type, set a default value
            switch (this.parentTypeId) {
                case TypesTable.DefaultIds.INTEGER:
                    this.value = new Primitive(TypesTable.DefaultTypes.INTEGER.id, this.line, this.column, 0);
                    break;
                case TypesTable.DefaultIds.REAL:
                    this.value = new Primitive(TypesTable.DefaultTypes.REAL.id, this.line, this.column, 0.0);
                    break;
                case TypesTable.DefaultIds.BOOLEAN:
                    this.value = new Primitive(TypesTable.DefaultTypes.BOOLEAN.id, this.line, this.column, false);
                    break;
                case TypesTable.DefaultIds.STRING:
                    this.value = new Primitive(TypesTable.DefaultTypes.STRING.id, this.line, this.column, "");
                    break;
                case TypesTable.DefaultIds.CHARACTER:
                    this.value = new Primitive(TypesTable.DefaultTypes.CHARACTER.id, this.line, this.column, '\u0000');
                    break;
            }
//            var arrayVal = new ArrayValue(this.parentTypeId, null, this.calculateSize(), (int) this.minVal, (int) this.maxVal);

            System.out.println("VariableDeclaration.execute: id = " + id + ", value = " + this.value + "parentTypeId = " + this.parentTypeId);
            // execute the value
            var result = this.value.execute(tree, table, typesTable);

            SymbolVariable symbol = new SymbolVariable(this.typeId, false, id, result, this.line, this.column);
            if (originalParentTypeId != parentTypeId) {
                symbol.setOriginalTypeId(originalParentTypeId);
            }
            boolean created = table.setSymbol(symbol);
            if (!created) {
                return new PError("Semantica", "Variable " + id + " ya se ha declarado", this.line, this.column);
            }
        }

//        // check if the variable is constant and attach a type to it
//        if (values == null) {
//            if (this.constant) {
//                this.typeId = value.typeId;
//            }
//
//            // set default values
//            if (this.value == null) {
//                switch (TypesTable.DefaultTypes.values()[this.typeId]) {
//                    case TypesTable.DefaultTypes.INTEGER:
//                        this.value = new Primitive(TypesTable.DefaultTypes.INTEGER.id, this.line, this.column, 0);
//                        break;
//                    case TypesTable.DefaultTypes.REAL:
//                        this.value = new Primitive(TypesTable.DefaultTypes.REAL.id, this.line, this.column, 0.0);
//                        break;
//                    case TypesTable.DefaultTypes.BOOLEAN:
//                        this.value = new Primitive(TypesTable.DefaultTypes.BOOLEAN.id, this.line, this.column, false);
//                        break;
//                    case TypesTable.DefaultTypes.STRING:
//                        this.value = new Primitive(TypesTable.DefaultTypes.STRING.id, this.line, this.column, "");
//                        break;
//                    case TypesTable.DefaultTypes.CHARACTER:
//                        this.value = new Primitive(TypesTable.DefaultTypes.CHARACTER.id, this.line, this.column, '\u0000');
//                        break;
//                }
//            }
//
//            var result = this.value.execute(tree, table, typesTable);
//
//            if (result instanceof PError) return result;
//
//            // add the variable to the symbol table
//            var symbolForTable = new SymbolVariable(this.typeId, this.constant, this.id, result, this.line, this.column);
//
//            boolean created = table.setSymbol(symbolForTable);
//            if (!created) {
//                return new PError("Semantica", "Variable " + this.id + " ya se ha declarado", this.line, this.column);
//            }
//        } else if (values != null) {
//            var symbol = new SymbolVariable(this.typeId, this.constant, this.id, new ArrayValue(this.typeId, this.values, this.line, this.column), this.line, this.column);
//        }
//

        return null;
    }

    private int calculateSize() {
        if (isArray) {
            return ((int) maxVal - (int) minVal) + 1; // Size for arrays
        } else if (isRange) {
            return 1; // For ranges, you may adjust accordingly
        } else if (isRecord) {
            return 0; // Records' size depends on fields; you should adjust accordingly
        }
        return 0;
    }

    private String generateTypeName(String idName) {
        // Logic to generate a unique type name
        return "AnonymousType:" + idName;
    }

    public LinkedList<String> getIds() {
        return ids;
    }

    public void setIds(LinkedList<String> ids) {
        this.ids = ids;
    }

    public Statement getValue() {
        return value;
    }

    public void setValue(Statement value) {
        this.value = value;
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

    public String getParentTypeName() {
        return parentTypeName;
    }

    public void setParentTypeName(String parentTypeName) {
        this.parentTypeName = parentTypeName;
    }

    public int getOriginalParentTypeId() {
        return originalParentTypeId;
    }

    public void setOriginalParentTypeId(int originalParentTypeId) {
        this.originalParentTypeId = originalParentTypeId;
    }

    public int getParentTypeId() {
        return parentTypeId;
    }

    public void setParentTypeId(int parentTypeId) {
        this.parentTypeId = parentTypeId;
    }
}
