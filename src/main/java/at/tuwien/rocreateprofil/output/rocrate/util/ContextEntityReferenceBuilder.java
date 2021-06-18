package at.tuwien.rocreateprofil.output.rocrate.util;

import at.tuwien.rocreateprofil.output.rocrate.RoCrateSchema;
import org.json.simple.JSONObject;

public class ContextEntityReferenceBuilder {

    public static JSONObject buildReference() {
        JSONObject referenceObject = new JSONObject();
        referenceObject.put(RoCrateSchema.ID, UniqueIdentifierGenerator.generateUniqueUUID());
        return referenceObject;
    }
}
