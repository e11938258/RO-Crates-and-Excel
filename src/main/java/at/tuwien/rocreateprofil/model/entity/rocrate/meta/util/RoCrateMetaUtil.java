package at.tuwien.rocreateprofil.model.entity.rocrate.meta.util;

import at.tuwien.rocreateprofil.output.rocrate.RoCrateSchema;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ListIterator;

public class RoCrateMetaUtil {

    public static JSONArray getRoCrateMetadataGraphArray(JSONObject roCrateMetadata) {
        guardRoCrateInitialized(roCrateMetadata);
        return (JSONArray) roCrateMetadata.get(RoCrateSchema.GRAPH);
    }

    public static JSONObject getRootDataEntity(JSONObject roCrateMetadata) {
        guardRoCrateInitialized(roCrateMetadata);
        return retrieveEntityByIdAndType(getRoCrateMetadataGraphArray(roCrateMetadata), RoCrateSchema.ROOT_DATA_ENTITY_ID, RoCrateSchema.TYPE_DATASET);
    }

// Might not be necessary thanks to passing by reference, TODO: @marwin remove once dataset integration is done
//    public static JSONObject updateEntity(JSONObject roCrateMetadata, JSONObject updatedEntity) {
//        JSONArray entityGraph = getRoCrateMetadataGraphArray(roCrateMetadata);
//        // TODO: @marwin this might have issues if the type is an array - make sure to add unit tests checking the functionality
//        JSONObject entityToUpdate = retrieveEntityByIdAndType(entityGraph, updatedEntity.get(RoCrateSchema.ID).toString(), updatedEntity.get(RoCrateSchema.TYPE).toString());
//        return roCrateMetadata;
//    }

    public static JSONObject retrieveEntityByIdAndType(JSONArray objectArray, String id, String type) {
        ListIterator iterator = objectArray.listIterator();
        while (iterator.hasNext()) {
            JSONObject object = (JSONObject) iterator.next();
            JSONArray typeArray = getTypeArray(object);
            String objectId = (String) object.get(RoCrateSchema.ID);
            if (typeArray.contains(type) && objectId.equals(id)) {
                return object;
            }
        }
        throw new IllegalStateException("Object not found for id and type");  // TODO: @marwin change this to a defined error
    }

    /*
    * Retrieves type as array regardless even if there is only a single type
     */
    private static JSONArray getTypeArray(JSONObject object) {
        JSONArray typeArray;
        try {
            typeArray = (JSONArray) object.get(RoCrateSchema.TYPE);
        } catch (ClassCastException e) {
            typeArray = new JSONArray();
            typeArray.add(object.get(RoCrateSchema.TYPE));
        }
        return typeArray;
    }

    public static JSONObject retrieveEntityById(JSONArray objectArray, String id) {
        ListIterator iterator = objectArray.listIterator();
        while (iterator.hasNext()) {
            JSONObject object = (JSONObject) iterator.next();
            String objectId = (String) object.get(RoCrateSchema.ID);
            if (objectId != null && objectId.equals(id)) {
                return object;
            }
        }
        throw new IllegalStateException("Object not found for id");  // TODO: @marwin change this to a defined error
    }

    private static void guardRoCrateInitialized(JSONObject roCrateMetadata) {
        if (roCrateMetadata == null) {
            throw new IllegalStateException(); // TODO: @marwin change this to a defined error
        }
    }


    private static String JSON = "{\n" +
            "  \"@graph\": [\n" +
            "    {\n" +
            "      \"@type\": \"CreativeWork\",\n" +
            "      \"about\": {\n" +
            "        \"@id\": \".\\/\"\n" +
            "      },\n" +
            "      \"@id\": \"ro-crate-metadata.json\",\n" +
            "      \"conformsTo\": {\n" +
            "        \"@id\": \"https:\\/\\/w3id.org\\/ro\\/crate\\/1.1\"\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"@id\": \".\\/\",\n" +
            "      \"datePublished\": \"2021-06-18T01:04:32Z\",\n" +
            "      \"license\": \"\",\n" +
            "      \"application\": {\n" +
            "        \"@id\": \"72ce19e6-d248-4961-a7ed-cdb40d127534\"\n" +
            "      },\n" +
            "      \"@type\": \"Dataset\",\n" +
            "      \"name\": null,\n" +
            "      \"description\": \"\",\n" +
            "      \"hasPart\": [\n" +
            "        {\n" +
            "          \"@id\": \"input.xlsx\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"@id\": \".\\/\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"@type\": \"file\",\n" +
            "      \"name\": \"input.xlsx\",\n" +
            "      \"encodingFormat\": \"application\\/json\",\n" +
            "      \"@id\": \"input.xlsx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"@type\": \"SoftwareApplication\",\n" +
            "      \"name\": \"Microsoft Excel\",\n" +
            "      \"@id\": \"72ce19e6-d248-4961-a7ed-cdb40d127534\",\n" +
            "      \"version\": \"16.0300\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"@context\": \"https:\\/\\/w3id.org\\/ro\\/crate\\/1.1\\/context\"\n" +
            "}\n" +
            "\n" +
            "\n" +
            "\n";
}
