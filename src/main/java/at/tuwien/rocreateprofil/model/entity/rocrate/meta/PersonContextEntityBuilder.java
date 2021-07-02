package at.tuwien.rocreateprofil.model.entity.rocrate.meta;

import at.tuwien.rocreateprofil.output.rocrate.RoCrateSchema;
import org.json.simple.JSONObject;

public class PersonContextEntityBuilder {

    public static JSONObject build(String id, String name) {
        JSONObject personEntity = new JSONObject();
        personEntity.put(RoCrateSchema.ID, id);
        personEntity.put(RoCrateSchema.TYPE, RoCrateSchema.TYPE_PERSON);
        personEntity.put(RoCrateSchema.NAME, name);
        return personEntity;
    }

}
