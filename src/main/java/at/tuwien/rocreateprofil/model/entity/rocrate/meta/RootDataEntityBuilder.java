package at.tuwien.rocreateprofil.model.entity.rocrate.meta;

import at.tuwien.rocreateprofil.output.rocrate.RoCrateSchema;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.net.URL;
import java.time.Instant;

import static at.tuwien.rocreateprofil.output.rocrate.RoCrateSchema.*;

public class RootDataEntityBuilder {

    public static JSONObject buildWithoutFiles(URL license) {
        JSONObject rootDataEntity = new JSONObject();
        rootDataEntity.put(ID, ROOT_DATA_ENTITY_ID);
        rootDataEntity.put(TYPE, TYPE_DATASET);
        rootDataEntity.put(DATE_PUBLISHED, Instant.now().toString());
        rootDataEntity.put(NAME, GENERIC_NAME);
        rootDataEntity.put(DESCRIPTION, GENERIC_DESCRIPTION);
        rootDataEntity.put(LICENSE, buildLicense(license));

         return rootDataEntity;
    }

    public static void addFile(JSONObject rootDataEntity, File file) {
        JSONArray fileArray = new JSONArray();
        fileArray = updateWithAlreadyAddedFiles(rootDataEntity, fileArray);
        appendNewFile(file, fileArray);
        rootDataEntity.put(HAS_PART, fileArray);
    }

    private static void appendNewFile(File file, JSONArray fileArray) {
        JSONObject fileObject = new JSONObject();
        fileObject.put(ID, file.getName());
        fileArray.add(fileObject);
    }

    private static JSONArray updateWithAlreadyAddedFiles(JSONObject rootDataEntity, JSONArray fileArray) {
        if (rootDataEntity.containsKey(HAS_PART)) {
            fileArray = (JSONArray) rootDataEntity.get(HAS_PART);
        }
        return fileArray;
    }

    private static JSONObject buildLicense(URL license) {
        JSONObject licenseContextEntity = new JSONObject();
        licenseContextEntity.put(ID, license.toString());
        return licenseContextEntity;
    }
}
