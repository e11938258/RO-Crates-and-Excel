package at.tuwien.rocreateprofil.model.entity.rocrate.meta;

import at.tuwien.rocreateprofil.output.rocrate.RoCrateSchema;
import org.json.simple.JSONObject;

public class SoftwareApplicationContextEntityBuilder {

    public static JSONObject build(String id, String applicationName, String applicationVersion) {
        JSONObject softwareApplication = new JSONObject();
        softwareApplication.put(RoCrateSchema.ID, id);
        softwareApplication.put(RoCrateSchema.TYPE, RoCrateSchema.TYPE_APPLICATION);
        softwareApplication.put(RoCrateSchema.NAME, applicationName);
        softwareApplication.put(RoCrateSchema.VERSION, applicationVersion);

        return softwareApplication;
    }
}
