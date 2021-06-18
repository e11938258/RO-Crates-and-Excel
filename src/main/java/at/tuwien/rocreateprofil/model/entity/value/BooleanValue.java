package at.tuwien.rocreateprofil.model.entity.value;

import org.json.simple.JSONObject;

public class BooleanValue implements Value  {
    
    private final Boolean value;

    public BooleanValue(Boolean value) {
        this.value = value;
    }
    
    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void write(JSONObject cell) {
        cell.put("value", value);
        cell.put("type", "BooleanValue");
    }
    
}
