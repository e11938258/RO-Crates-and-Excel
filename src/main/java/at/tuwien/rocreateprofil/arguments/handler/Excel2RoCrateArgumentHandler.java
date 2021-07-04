package at.tuwien.rocreateprofil.arguments.handler;

import at.tuwien.rocreateprofil.arguments.CommandLineArgument;
import at.tuwien.rocreateprofil.convertor.Convertor;
import at.tuwien.rocreateprofil.convertor.Excel2RoCrateConvertor;
import at.tuwien.rocreateprofil.exception.RoCrateError;
import at.tuwien.rocreateprofil.exception.RoCrateProfileBaseException;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Stack;

public class Excel2RoCrateArgumentHandler implements ArgumentHandler {

    private static final CommandLineArgument argument = CommandLineArgument.EXCEL_TO_RO_CRATE;

    private boolean additionalParametersProcessed = false;

    @Override
    public Map<String, Object> consumeAdditionalParameters(Stack<String> remainingArgumentStack, Map<String, Object> currentArgumentMap) {
        this.guardEnoughParameters(this.getArgument(), remainingArgumentStack);

        consumeExcelFileLocationParameter(remainingArgumentStack.pop(), currentArgumentMap);
        consumeLicenseParameter(remainingArgumentStack.pop(), currentArgumentMap);

        this.additionalParametersProcessed = true;
        return currentArgumentMap;
    }

    private void consumeLicenseParameter(String licenseUrlString, Map<String, Object> currentArgumentMap) {
        try {
            URL licenseUrl = new URL(licenseUrlString);
            currentArgumentMap.put(Excel2RoCrateConvertor.LICENSE_URL, licenseUrl);
        } catch (MalformedURLException e) {
            throw new RoCrateProfileBaseException(RoCrateError.INVALID_LICENSE_URL, licenseUrlString);
        }
    }

    private void consumeExcelFileLocationParameter(String location, Map<String, Object> currentArgumentMap) {
        File excelFile = new File(location);
        currentArgumentMap.put(Excel2RoCrateConvertor.EXCEL_FILE, excelFile);
    }

    @Override
    public Convertor handle(Map<String, Object> parameterMap) {
        guardParametersProcessed();
        return new Excel2RoCrateConvertor(parameterMap);
    }

    @Override
    public boolean wereParametersProcessed() {
        return additionalParametersProcessed;
    }


    @Override
    public CommandLineArgument getArgument() {
        return argument;
    }
}
