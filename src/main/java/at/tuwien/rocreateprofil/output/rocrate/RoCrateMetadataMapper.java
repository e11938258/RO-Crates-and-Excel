package at.tuwien.rocreateprofil.output.rocrate;


import at.tuwien.rocreateprofil.model.entity.RoCrateModel;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import static at.tuwien.rocreateprofil.output.rocrate.RoCrateSchema.*;

public class RoCrateMetadataMapper {

    public static JSONObject mapFromModel(RoCrateModel model) {
        JSONObject roCrateMetadata = new JSONObject();
        insertRoCrateContext(roCrateMetadata);
        JSONArray graphContent = buildGraphContents(model);
        insertRoCrateGraph(roCrateMetadata, graphContent);
        return roCrateMetadata;
    }

    private static JSONArray buildGraphContents(RoCrateModel model) {
        JSONArray graphContent = new JSONArray();
        graphContent.add(buildRootEntity());
        graphContent.add(describeRootEntity(model));

        return graphContent;
    }

    private static Object describeRootEntity(RoCrateModel model) {
        JSONObject aboutRootEntity = new JSONObject();
        aboutRootEntity.put(ID, ROOT_ENTITY_ABOUT_ID);
        aboutRootEntity.put(TYPE, TYPE_DATASET);
        aboutRootEntity.put(DATE_PUBLISHED, model.getModified());
        // TODO: make sure those values are filled with something relevant (they are mandatory)
        aboutRootEntity.put(NAME, model.getWorkbookName());
        aboutRootEntity.put(DESCRIPTION, StringUtils.EMPTY);
        // TODO: Don't think we have any licence at all if coming from excel
        aboutRootEntity.put(LICENSE, StringUtils.EMPTY);
        return aboutRootEntity;
    }

    private static JSONObject buildRootEntity() {
        JSONObject rootEntity = new JSONObject();
        rootEntity.put(TYPE, ROOT_ENTITY_TYPE);
        rootEntity.put(ID, ROOT_ENTITY_ID);
        rootEntity.put(ABOUT, buildMandatoryAboutContent());
        rootEntity.put(CONFORMS_TO, buildMandatoryConformsToContent());
        return rootEntity;
    }

    private static Object buildMandatoryConformsToContent() {
        JSONObject conformsToContent = new JSONObject();
        conformsToContent.put(ID, SCHEMA_VERSION);
        return conformsToContent;
    }

    private static JSONObject buildMandatoryAboutContent() {
        JSONObject aboutContent = new JSONObject();
        aboutContent.put(ID, ROOT_ENTITY_ABOUT_ID);
        return aboutContent;
    }

    private static void insertRoCrateContext(JSONObject roCrateMetadata) {
        roCrateMetadata.put(CONTEXT, CONTEXT_VALUE);
    }

    private static void insertRoCrateGraph(JSONObject roCrateMetadata, JSONArray graphContent) {
        roCrateMetadata.put(RoCrateSchema.GRAPH, graphContent);
    }
}
