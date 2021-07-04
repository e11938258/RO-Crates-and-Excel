package at.tuwien.rocreateprofil.model.entity.rocrate;

import at.tuwien.rocreateprofil.convertor.rocrate.JsonFileParser;
import at.tuwien.rocreateprofil.model.entity.rocrate.meta.*;
import at.tuwien.rocreateprofil.model.entity.rocrate.meta.util.RoCrateMetaUtil;
import at.tuwien.rocreateprofil.output.rocrate.RoCrateOutputCreator;
import at.tuwien.rocreateprofil.output.rocrate.RoCrateSchema;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RoCrate {

    private static final String RO_CRATE_METADATA_LOCATION_RELATIVE_TO_ROOT = "/ro-crate-metadata.json";

    private Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    private String roCrateLocation;

    private JSONObject roCrateMetadata;
    private List<File> files = new ArrayList<>();

    public RoCrate(String roCrateLocation, URL license) {
        this.roCrateLocation = roCrateLocation;
        initializeRoCrateMetadata(license);
    }

    private RoCrate(String roCrateLocation) {
        this.roCrateLocation = roCrateLocation;
    }

    public void addExcelFile(File file) {
        this.files.add(file);
        addFileDateEntityToGraph(file);
    }

    private void addFileDateEntityToGraph(File file) {
        // create hasFile on Dataset
        JSONObject rootDataEntity = RoCrateMetaUtil.getRootDataEntity(roCrateMetadata);
        RootDataEntityBuilder.addFile(rootDataEntity, file);
        // create File Data entity
        JSONObject fileDataEntity = FileDataEntityBuilder.buildMinimal(file);
        RoCrateMetaUtil.getRoCrateMetadataGraphArray(roCrateMetadata).add(fileDataEntity);
    }

    private void initializeRoCrateMetadata(URL license) {
        if (this.roCrateMetadata != null) {
            logger.info("RO crate already initialized, skipping ro-crate-meta.json skeleton generation");
        }
        roCrateMetadata = new JSONObject();
        // add context
        roCrateMetadata.put(RoCrateSchema.CONTEXT, ContextBuilder.build());
        // add graph array
        roCrateMetadata.put(RoCrateSchema.GRAPH, new JSONArray());
        // append root entity into graph
        RoCrateMetaUtil.getRoCrateMetadataGraphArray(roCrateMetadata).add(RootEntityBuilder.build());
        // append empty root entity description
        RoCrateMetaUtil.getRoCrateMetadataGraphArray(roCrateMetadata).add(RootDataEntityBuilder.buildWithoutFiles(license));
        // append license context entity
        RoCrateMetaUtil.getRoCrateMetadataGraphArray(roCrateMetadata).add(LicenceContextEntityBuilder.build(license));

    }

    public JSONObject getRoCrateMetadata() {
        return roCrateMetadata;
    }

    private void setRoCrateMetadata(JSONObject roCrateMetadata) {
        this.roCrateMetadata = roCrateMetadata;
    }

    public List<File> getFiles() {
        return files;
    }

    public String getRoCrateLocation() {
        return roCrateLocation;
    }

    public void write() {
        RoCrateOutputCreator.dumpRoCrate(this);
    }

    public static RoCrate loadFromFile(String roCrateLocation) {
        RoCrate roCrate = new RoCrate(roCrateLocation);
        roCrate.setRoCrateMetadata(JsonFileParser.parseFileIntoJSONObject(roCrateLocation + RO_CRATE_METADATA_LOCATION_RELATIVE_TO_ROOT));
        return roCrate;
    }


}
