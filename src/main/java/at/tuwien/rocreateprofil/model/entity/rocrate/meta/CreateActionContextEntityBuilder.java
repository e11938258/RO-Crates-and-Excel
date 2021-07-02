package at.tuwien.rocreateprofil.model.entity.rocrate.meta;

import at.tuwien.rocreateprofil.output.rocrate.RoCrateSchema;
import at.tuwien.rocreateprofil.output.rocrate.util.ContextEntityReferenceBuilder;
import org.json.simple.JSONObject;

public class CreateActionContextEntityBuilder {

    public static JSONObject build(String outputFileName) {
        JSONObject createAction = ContextEntityReferenceBuilder.buildReference();
        createAction.put(RoCrateSchema.TYPE, RoCrateSchema.TYPE_CREATE_ACTION);
        createAction.put(RoCrateSchema.INSTRUMENT, ContextEntityReferenceBuilder.buildReference());
        createAction.put(RoCrateSchema.DESCRIPTION, RoCrateSchema.CREATE_ACTION_GENERIC_DESCRIPTION);
        createAction.put(RoCrateSchema.RESULT, buildConnectionToFile(outputFileName));

        return createAction;
    }

    private static JSONObject buildConnectionToFile(String outputFileName) {
        JSONObject fileConnection = new JSONObject();
        fileConnection.put(RoCrateSchema.ID, outputFileName);

        return fileConnection;
    }

}
