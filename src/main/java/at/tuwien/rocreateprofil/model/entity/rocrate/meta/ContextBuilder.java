package at.tuwien.rocreateprofil.model.entity.rocrate.meta;

import at.tuwien.rocreateprofil.output.rocrate.RoCrateSchema;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ContextBuilder {

    public static JSONArray build() {
        JSONArray context = new JSONArray();
        // schema for current ro-crate version
        context.add(RoCrateSchema.RO_CRATE_CONTEXT_VALUE);
        // Datacube for dataset structure description terms
        context.add(buildDataCubeContext());
        // Custom ontology for types defining dataset & dimension properties
        context.add(buildCustomOntologyContext());

        return context;
    }

    private static Object buildDataCubeContext() {
        JSONObject dataCubeContext = new JSONObject();
        dataCubeContext.put(RoCrateSchema.DATA_CUBE_NAMESPACE, RoCrateSchema.DATA_CUBE_CONTEXT);
        return dataCubeContext;
    }

    private static Object buildCustomOntologyContext() {
        JSONObject customOntologyContext = new JSONObject();
        customOntologyContext.put(RoCrateSchema.CUSTOM_ONTOLOGY_NAMESPACE, RoCrateSchema.CUSTOM_ONTOLOGY_CONTEXT);
        return customOntologyContext;
    }

}
