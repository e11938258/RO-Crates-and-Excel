package at.tuwien.rocreateprofil.model.entity.dataset;

import at.tuwien.rocreateprofil.model.entity.value.Type;
import at.tuwien.rocreateprofil.model.entity.value.Value;
import org.json.simple.JSONObject;

public class Cell {

    private final String id, functionName;
    private final Column column;
    private final Row row;
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

    public Type getType() {
        return type;
    }

    public Value getValue() {
        return value;
    }
    
    public JSONObject getJSONObject() {
        final JSONObject cell = new JSONObject();

        // Put values to json cell
        cell.put("id", id);
        cell.put("columnId", column.getId());
        cell.put("rowId", row.getId());
        cell.put("type", type.toString());
        value.write(cell);
        if (functionName != null) {
            cell.put("functionName", functionName);
        }

        return cell;
    }
}
