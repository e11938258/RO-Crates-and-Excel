package at.tuwien.rocreateprofil.arguments.handler;

import at.tuwien.rocreateprofil.convertor.Convertor;
import at.tuwien.rocreateprofil.convertor.Excel2RoCrateConvertor;
import at.tuwien.rocreateprofil.arguments.CommandLineArgument;

import java.util.Map;
import java.util.Stack;

public class Excel2RoCrateArgumentHandler implements ArgumentHandler {

    private static final CommandLineArgument argument = CommandLineArgument.EXCEL_TO_RO_CRATE;

    private boolean additionalParametersProcessed = false;

    @Override
    public Map<String, Object> consumeAdditionalParameters(Stack<String> remainingArgumentStack, Map<String, Object> currentArgumentMap) {
        this.guardEnoughParameters(this.getArgument(), remainingArgumentStack);

        consumeExcelFileLocationParameter(remainingArgumentStack.pop(), currentArgumentMap);

        this.additionalParametersProcessed = true;
        return currentArgumentMap;
    }

    private void consumeExcelFileLocationParameter(String location, Map<String, Object> currentArgumentMap) {
        currentArgumentMap.put(Excel2RoCrateConvertor.EXCEL_FILE_LOCATION, location);
    }

    @Override
    public Convertor resolve(Map<String, Object> parameterMap) {
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
