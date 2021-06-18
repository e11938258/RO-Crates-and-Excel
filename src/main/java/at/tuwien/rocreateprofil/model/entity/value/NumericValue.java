package at.tuwien.rocreateprofil.model.entity.value;

import org.json.simple.JSONObject;

public class NumericValue implements Value {
    
    private final Double value;

    public NumericValue(Double value) {
        this.value = value;
    }
    
    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void write(JSONObject cell) {
        cell.put("value", value);
        cell.put("type", "NumericValue");
    }
}
