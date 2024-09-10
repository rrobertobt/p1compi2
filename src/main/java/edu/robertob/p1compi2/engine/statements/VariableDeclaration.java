package edu.robertob.p1compi2.engine.statements;

import edu.robertob.p1compi2.engine.base.Statement;
import edu.robertob.p1compi2.engine.structs.*;

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

    @Override
    public Object execute(Tree tree, SymbolTable table, TypesTable typesTable) {

        // if a new type is defined within the variable declaration, we have to register it
        // but only if it does not exist already

        boolean createType = false;
        if (this.parentTypeName != null && typesTable.getType(this.parentTypeName) == null) {
            createType = true;
        } else if (this.parentTypeName == null && typesTable.getType(this.parentTypeId) == null) {
            createType = true;
        }



        if ((isArray || isRange || isRecord) && createType) {
            final String[] idName = {""};
            ids.forEach(id -> {
                idName[0] = idName[0] + id;
            });
            String typeName = generateTypeName(idName[0]);
            int parentTypeId;
            if (this.parentTypeName != null) {
                parentTypeId = typesTable.getType(this.parentTypeName).id;
            } else {
                parentTypeId = this.parentTypeId;
            }
            TypeDeclaration newType = new TypeDeclaration(this.line, this.column, new LinkedList<>(Collections.singletonList(typeName)), parentTypeId, isArray, isRange, isRecord, calculateSize(), (int) minVal, (int) maxVal);
            Object result = newType.execute(tree, table, typesTable);
            this.typeId = typesTable.getType(typeName).id;
//            if (result instanceof PError) {
//                return result; // Return error if type registration fails
//            }
        } else {
            TypesTable.TypeTableEntry parentType; //= typesTable.getType(this.parentTypeName);
            if (this.parentTypeName != null) {
                parentType = typesTable.getType(this.parentTypeName);
            } else {
                parentType = typesTable.getType(this.parentTypeId);
            }
            this.typeId = parentType.id;
            this.parentTypeId = parentType.id;
        }

        // Register the variable in the symbol table
        for (String id : ids) {
            SymbolVariable symbol = new SymbolVariable(this.typeId, false, id, null, this.line, this.column);
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
}
