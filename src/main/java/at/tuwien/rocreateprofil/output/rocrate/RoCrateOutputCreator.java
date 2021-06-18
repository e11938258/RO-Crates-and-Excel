package at.tuwien.rocreateprofil.output.rocrate;

import at.tuwien.rocreateprofil.exception.Error;
import at.tuwien.rocreateprofil.exception.RoCrateProfileBaseException;
import at.tuwien.rocreateprofil.model.entity.Dataset;
import at.tuwien.rocreateprofil.model.entity.RoCrateModel;
import org.apache.commons.lang3.NotImplementedException;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RoCrateOutputCreator {

    // TODO: temporary, this should be taken as an argument
    private static final String RO_CRATE_ROOT = "output/ro-crate";
    private static final String RO_CRATE_METADATA_FILENAME = "ro-crate-metadata.json";
    private static final String RO_CRATE_DATA_FOLDER = "/data";

    public static void createRoCrateFromModel(RoCrateModel model) {
        Path roCrateRoot = createRootDirectory();
        JSONObject roCrateMetadata = RoCrateMetadataMapper.mapFromModel(model);
        writeRoCrateMetadata(roCrateRoot, roCrateMetadata);
        writeDataContentFiles(model);
    }

    private static void writeDataContentFiles(RoCrateModel model) {
        // Create folder with data
        File dir = new File(RO_CRATE_ROOT + RO_CRATE_DATA_FOLDER);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // Save each dataset
        for (Dataset dataset : model.getDatasets()) {
            dataset.writeToFile(RO_CRATE_ROOT + RO_CRATE_DATA_FOLDER);
        }
    }

    private static void writeRoCrateMetadata(Path roCrateRoot, JSONObject roCrateMetadata) {
        String metadataPath = roCrateRoot.toAbsolutePath() + "/" + RO_CRATE_METADATA_FILENAME;
        FileWriter file = null;
        try {
            file = new FileWriter(metadataPath);
            file.write(roCrateMetadata.toString());
        } catch (IOException e) {
            throw new RoCrateProfileBaseException(Error.RO_CRATE_CONTENT_WRITE_FAILED, metadataPath);
        } finally {
            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                throw new RoCrateProfileBaseException(Error.RO_CRATE_CONTENT_WRITE_FAILED, metadataPath);
            }
        }
    }

    private static Path createRootDirectory() {
        Path rootDirectoryPath = Paths.get(RO_CRATE_ROOT);
        try {
            if (!rootDirectoryExists(rootDirectoryPath)) {
                Files.createDirectory(rootDirectoryPath);
            }
        } catch (IOException e) {
            throw new RoCrateProfileBaseException(Error.RO_CRATE_ROOT_DIRECTORY_FAILED, rootDirectoryPath.toString());
        }
        return rootDirectoryPath;
    }

    private static boolean rootDirectoryExists(Path rootDirectoryPath) {
        return new File(rootDirectoryPath.toAbsolutePath().toString()).exists();
    }

}
