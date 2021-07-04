package at.tuwien.rocreateprofil.output.rocrate;

import at.tuwien.rocreateprofil.exception.RoCrateError;
import at.tuwien.rocreateprofil.exception.RoCrateProfileBaseException;
import at.tuwien.rocreateprofil.model.entity.rocrate.RoCrate;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class RoCrateOutputCreator {

    // TODO: temporary, this should be taken as an argument
    private static final String RO_CRATE_ROOT = "./output/ro-crate";
    private static final String RO_CRATE_METADATA_FILENAME = "ro-crate-metadata.json";
    private static final String RO_CRATE_DATA_FOLDER = "data/";

    public static void dumpRoCrate(RoCrate roCrate) {
        Path roCrateRootPath = createRoCrateRoot(roCrate);
        writeRoCrateMetadata(roCrateRootPath, roCrate.getRoCrateMetadata());
        writeDataContentFiles(roCrate.getFiles(), roCrateRootPath.toString());
    }

    private static Path createRoCrateRoot(RoCrate roCrate) {
        String roCrateRoot = resolveRoCrateRoot(roCrate.getRoCrateLocation());
        Path roCrateRootPath = createRootDirectory(roCrateRoot);
        return roCrateRootPath;
    }

    private static void writeDataContentFiles(List<File> files, String roCrateRoot) {
        // Create folder with data
        String roCrateDataRoot = resolveDataRoot(roCrateRoot);
        createFolder(roCrateDataRoot);
        // Save each file
        copyEachFileToRoCrate(roCrateDataRoot, files);
    }

    private static String resolveDataRoot(String roCrateRoot) {
        roCrateRoot = ensureFolderEndsWithSlash(roCrateRoot);
        String dataRoot = ensureFolderEndsWithSlash(roCrateRoot + RO_CRATE_DATA_FOLDER);
        return dataRoot;
    }

    private static String ensureFolderEndsWithSlash(String folderName) {
        if (!StringUtils.endsWith(folderName, "/")) {
            folderName += "/";
        }
        return folderName;
    }

    private static void copyEachFileToRoCrate(String roCrateDataRoot, List<File> files) {
        String currentFile = null;
        try {
            for (File file : files) {
                currentFile = file.getName();
                copyFile(roCrateDataRoot, file);
            }
        } catch (IOException e) {
            throw new RoCrateProfileBaseException(RoCrateError.CANNOT_MOVE_FILE_TO_RO_CRATE, currentFile);
        }
    }

    private static void copyFile(String roCrateDataRoot, File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        File targetFile = new File(roCrateDataRoot + file.getName());
        if (!targetFile.exists()) {
            targetFile.createNewFile();
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(targetFile.getAbsoluteFile()));
        String line;
        while ((line = reader.readLine()) != null) {
            writer.write(line);
        }
        writer.flush();
        writer.close();
        reader.close();
    }

    private static String createFolder(String folderToCreate) {
        File dir = new File(folderToCreate);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return folderToCreate;
    }

    private static String resolveRoCrateRoot(String roCrateRoot) {
        String targetFolder = roCrateRoot != null ? roCrateRoot : RO_CRATE_ROOT;
        if (!StringUtils.endsWith(targetFolder, "/")) {
            targetFolder += "/";
        }
        return targetFolder;
    }

    private static void writeRoCrateMetadata(Path roCrateRoot, JSONObject roCrateMetadata) {
        String metadataPath = roCrateRoot.toAbsolutePath() + "/" + RO_CRATE_METADATA_FILENAME;
        FileWriter file = null;
        try {
            file = new FileWriter(metadataPath);
            file.write(roCrateMetadata.toString());
        } catch (IOException e) {
            throw new RoCrateProfileBaseException(RoCrateError.RO_CRATE_CONTENT_WRITE_FAILED, metadataPath);
        } finally {
            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                throw new RoCrateProfileBaseException(RoCrateError.RO_CRATE_CONTENT_WRITE_FAILED, metadataPath);
            }
        }
    }

    private static Path createRootDirectory(String roCrateRoot) {
        Path rootDirectoryPath = Paths.get(roCrateRoot);
        try {
            if (!rootDirectoryExists(rootDirectoryPath)) {
                Files.createDirectory(rootDirectoryPath);
            }
        } catch (IOException e) {
            throw new RoCrateProfileBaseException(RoCrateError.RO_CRATE_ROOT_DIRECTORY_FAILED, rootDirectoryPath.toString());
        }
        return rootDirectoryPath;
    }

    private static boolean rootDirectoryExists(Path rootDirectoryPath) {
        return new File(rootDirectoryPath.toAbsolutePath().toString()).exists();
    }

}
