package at.tuwien.rocreateprofil.model.entity.rocrate.meta;

import at.tuwien.rocreateprofil.output.rocrate.RoCrateSchema;
import org.json.simple.JSONObject;

import java.io.File;

public class FileDataEntityBuilder {

    public static JSONObject buildMinimal(File file) {
        JSONObject fileDataEntity = new JSONObject();
        fileDataEntity.put(RoCrateSchema.ID, file.getName());
        fileDataEntity.put(RoCrateSchema.TYPE, RoCrateSchema.TYPE_FILE);

        return fileDataEntity;
    }

}
