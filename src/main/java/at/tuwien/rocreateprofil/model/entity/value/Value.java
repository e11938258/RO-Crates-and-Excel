package at.tuwien.rocreateprofil.model.entity.value;

import org.json.simple.JSONObject;

public interface Value {

    public Object getValue();

    public void write(JSONObject cell);

}
