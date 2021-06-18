package at.tuwien.rocreateprofil.model.entity;

import at.tuwien.rocreateprofil.model.entity.value.Value;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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

    public JSONObject getJSONObject() {
        final JSONObject cell = new JSONObject();

        // Put values to json cell
        cell.put("id", id);
        cell.put("columnId", columnId);
        cell.put("rowId", rowId);
        cell.put("type", type.toString());
        value.write(cell);
        if (functionName != null) {
            cell.put("functionName", functionName);
        }

        return cell;
    }
}
