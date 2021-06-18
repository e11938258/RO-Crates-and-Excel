package at.tuwien.rocreateprofil.model.entity;

import at.tuwien.rocreateprofil.model.entity.value.Value;

public class Cell {
    
    private final String id, functionName, columnId, rowId;
    private final Type type;
    private final Value value;

    public Cell(String id, String functionName, Type type, Value value, String columnId, String rowId) {
        this.id = id;
        this.functionName = functionName;
        this.columnId = columnId;
        this.rowId = rowId;
        this.type = type;
        this.value = value;
    }
}
