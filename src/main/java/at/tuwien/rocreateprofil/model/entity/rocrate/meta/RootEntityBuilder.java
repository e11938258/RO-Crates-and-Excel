package at.tuwien.rocreateprofil.model.entity.rocrate.meta;

import org.json.simple.JSONObject;

import static at.tuwien.rocreateprofil.output.rocrate.RoCrateSchema.*;

public class RootEntityBuilder {

    public static JSONObject build() {
        JSONObject rootEntity = new JSONObject();
        rootEntity.put(TYPE, TYPE_CREATIVE_WORK);
        rootEntity.put(ID, ROOT_ENTITY_ID);
        rootEntity.put(ABOUT, buildConnectionToRootContextEntity());
        rootEntity.put(CONFORMS_TO, buildConformsTo());
        return rootEntity;
    }

    private static JSONObject buildConnectionToRootContextEntity() {
        JSONObject about = new JSONObject();
        about.put(ID, ROOT_DATA_ENTITY_ID);
        return about;
    }

    private static Object buildConformsTo() {
        JSONObject conformsToContent = new JSONObject();
        conformsToContent.put(ID, SCHEMA_VERSION);
        return conformsToContent;
    }
}
