package at.tuwien.rocreateprofil.model.entity.value;

import org.json.simple.JSONObject;

public class StringValue implements Value {
    
    private final String value;

    public StringValue(String value) {
        this.value = value;
    }
    
    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void write(JSONObject cell) {
        cell.put("value", value);
        cell.put("type", "StringValue");
    }

}
