package at.tuwien.rocreateprofil.rocrate;

import at.tuwien.rocreateprofil.model.entity.RoCrateModel;

public class RoCrateBuilder {

    private RoCrateModel model;
    private String outputFolder;

    public RoCrateBuilder(RoCrateModel model, String outputFolder) {
        this.model = model;
        this.outputFolder = outputFolder;
    }

    public void buildCrate() {
        // create output folder (ro-crate root)

        // create ro-crate-metadata.json

        // build folder structure - for each dataset part
            // move the data


    }


}
