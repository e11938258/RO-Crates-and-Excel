package at.tuwien.rocreateprofil.model.entity.rocrate.meta;

import at.tuwien.rocreateprofil.output.rocrate.RoCrateSchema;
import org.json.simple.JSONObject;

import java.net.URL;

public class LicenceContextEntityBuilder {

    public static JSONObject build(URL licenseUrl) {
        JSONObject licenseContextEntity = new JSONObject();
        licenseContextEntity.put(RoCrateSchema.ID, licenseUrl.toString());
        licenseContextEntity.put(RoCrateSchema.TYPE, RoCrateSchema.TYPE_CREATIVE_WORK);
        licenseContextEntity.put(RoCrateSchema.DESCRIPTION, RoCrateSchema.LICENSE_GENERIC_DESCRIPTION);

        return licenseContextEntity;
    }

}
