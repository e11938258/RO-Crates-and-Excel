package at.tuwien.rocreateprofil.convertor.excel;

import at.tuwien.rocreateprofil.exception.RoCrateError;
import at.tuwien.rocreateprofil.exception.RoCrateProfileBaseException;

import java.io.File;

import static at.tuwien.rocreateprofil.exception.RoCrateError.FILE_NOT_FOUND;

public class ExcelFileGuard {

    private static final String ACCEPTABLE_SUFFIX = "xlsx";

    public static void guardValidExcelFile(String fileLocation) {
        guardFileExists(fileLocation);
        guardFileHasAcceptableSuffix(fileLocation);
    }

    private static void guardFileHasAcceptableSuffix(String fileLocation) {
        String suffix = resolveSuffix(fileLocation);
        if (!ACCEPTABLE_SUFFIX.equals(suffix)) {
            throw new RoCrateProfileBaseException(RoCrateError.INVALID_EXCEL_FILE_SUFFIX);
        }
    }

    private static String resolveSuffix(String fileLocation) {
        String[] splitByDot = fileLocation.split("\\.");
        return splitByDot[splitByDot.length-1];
    }

    private static void guardFileExists(String fileLocation) {
        File file = new File(fileLocation);
        if (!file.exists()) {
            throw new RoCrateProfileBaseException(FILE_NOT_FOUND);
        }
    }

}
