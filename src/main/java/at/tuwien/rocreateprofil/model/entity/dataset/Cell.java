package at.tuwien.rocreateprofil.model.entity.dataset;

import at.tuwien.rocreateprofil.model.entity.value.Type;
import at.tuwien.rocreateprofil.model.entity.value.Value;

public class Cell implements Comparable<Cell> {

    private final Column column;
    private final Row row;
    
    private final String id, functionName;
    private final Type type;
    private final Value value;

    public Cell(String id, String functionName, Type type, Value value, Column column,
            Row row) {
        this.id = id;
        this.functionName = functionName;
        this.column = column;
        this.row = row;
        this.type = type;
        this.value = value;
    }

    public String getId() {
        return id;
    }
    
    public Type getType() {
        return type;
    }

    public Value getValue() {
        return value;
    }

    @Override
    public int compareTo(Cell o) {
        return Integer.parseInt(row.getId()) - Integer.parseInt(o.row.getId());
    }
}
