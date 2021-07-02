package at.tuwien.rocreateprofil.output.rocrate;


import at.tuwien.rocreateprofil.model.entity.dataset.Dataset;
import at.tuwien.rocreateprofil.model.entity.RoCrateModel;
import at.tuwien.rocreateprofil.output.rocrate.util.ContextEntityReferenceBuilder;
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
        JSONObject rootEntityDescription = describeRootEntity(model);
        graphContent.add(rootEntityDescription);
        graphContent.add(buildApplicationContextEntity(model, (JSONObject) rootEntityDescription.get(APPLICATION)));
        graphContent.add(buildDatasetInfoEntity(model));
        // For each dataset
        for (Dataset dataset : model.getDatasets()) {
            graphContent.add(buildDatasetEntity(dataset));
        }

        return graphContent;
    }

    private static JSONObject buildApplicationContextEntity(RoCrateModel model, JSONObject application) {
        String applicationID = (String) application.get(ID);
        JSONObject applicationContextEntity = new JSONObject();
        applicationContextEntity.put(ID, applicationID);
        applicationContextEntity.put(TYPE, TYPE_APPLICATION);
        applicationContextEntity.put(NAME, model.getApplicationName());
        applicationContextEntity.put(VERSION, model.getApplicationVersion());

        return applicationContextEntity;
    }

    private static JSONObject describeRootEntity(RoCrateModel model) {
        JSONObject aboutRootEntity = new JSONObject();
        aboutRootEntity.put(ID, ROOT_DATA_ENTITY_ID);
        aboutRootEntity.put(TYPE, TYPE_DATASET);
        aboutRootEntity.put(DATE_PUBLISHED, model.getModified().toString());
        // TODO: make sure those values are filled with something relevant (they are mandatory)
        aboutRootEntity.put(NAME, model.getWorkbookName());
        aboutRootEntity.put(DESCRIPTION, StringUtils.EMPTY);
        // TODO: Don't think we have any licence at all if coming from excel
        aboutRootEntity.put(LICENSE, StringUtils.EMPTY);
        aboutRootEntity.put(APPLICATION, ContextEntityReferenceBuilder.buildReference());
        return aboutRootEntity;
    }

    private static JSONObject buildRootEntity() {
        JSONObject rootEntity = new JSONObject();
        rootEntity.put(TYPE, TYPE_CREATIVE_WORK);
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
        aboutContent.put(ID, ROOT_DATA_ENTITY_ID);
        return aboutContent;
    }

    private static void insertRoCrateContext(JSONObject roCrateMetadata) {
        roCrateMetadata.put(CONTEXT, RO_CRATE_CONTEXT_VALUE);
    }

    private static void insertRoCrateGraph(JSONObject roCrateMetadata, JSONArray graphContent) {
        roCrateMetadata.put(RoCrateSchema.GRAPH, graphContent);
    }

    private static JSONObject buildDatasetInfoEntity(RoCrateModel model) {
        final JSONObject datasetInfoEntity = new JSONObject();
        // ID
        datasetInfoEntity.put(ID, ROOT_DATA_ENTITY_ID);
        
        // Types
        final JSONArray typeContent = new JSONArray();
        typeContent.add(DATASET);
        datasetInfoEntity.put(TYPE, typeContent);

        // Has part
        final JSONArray parts = new JSONArray();
        for (Dataset dataset : model.getDatasets()) {
            final JSONObject datasetId = new JSONObject();
            datasetId.put(ID, dataset.getId());
            parts.add(datasetId);
        }
        datasetInfoEntity.put(HAS_PART, parts);
        
        return datasetInfoEntity;
    }
    
    private static JSONObject buildDatasetEntity(Dataset dataset) {
        final JSONObject datasetInfoEntity = new JSONObject();
        
        // ID
        datasetInfoEntity.put(ID, dataset.getId());
        // Type
        datasetInfoEntity.put(TYPE, TYPE_FILE);
        // Name
        datasetInfoEntity.put(NAME, dataset.getName());
        // Encoding format
        datasetInfoEntity.put(ENCODING_FORMAT, dataset.getFormat());
        
        return datasetInfoEntity;
    }
}
