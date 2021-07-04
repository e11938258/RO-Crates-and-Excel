package at.tuwien.rocreateprofil.convertor;

import at.tuwien.rocreateprofil.TemporaryResourceStore;
import at.tuwien.rocreateprofil.exception.RoCrateProfileBaseException;
import net.lingala.zip4j.ZipFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static at.tuwien.rocreateprofil.exception.RoCrateError.EXCEL_UNZIP_FAILED;

public class ExcelFileDecompressor {

    private static final String UNZIPPED_EXCEL_FOLDER_NAME =  "unzipped-excel";

    public static void unzip(File excelFile) {
        try {
            String extractedLocation = buildExtractedPath(excelFile).toString();
            new ZipFile(excelFile).extractAll(extractedLocation);
            TemporaryResourceStore.storeNewTemporaryResource(extractedLocation);
        } catch (IOException e) {
            throw new RoCrateProfileBaseException(EXCEL_UNZIP_FAILED, e.getMessage());
        }
    }

    public static Path buildExtractedPath(File excelFile) {
        Path excelFolder = Paths.get(excelFile.getParent());
        return Paths.get(excelFolder + "/" + UNZIPPED_EXCEL_FOLDER_NAME);
    }


}
