package at.tuwien.rocreateprofil.arguments.handler;

import at.tuwien.rocreateprofil.convertor.Convertor;
import at.tuwien.rocreateprofil.convertor.RoCrate2ExcelConvertor;
import at.tuwien.rocreateprofil.arguments.CommandLineArgument;

import java.util.Map;
import java.util.Stack;

public class RoCrate2ExcelArgumentHandler implements ArgumentHandler {

    private static final CommandLineArgument argument = CommandLineArgument.RO_CRATE_TO_EXCEL;

    private boolean additionalParametersProcessed = false;

    @Override
    public Map<String, Object> consumeAdditionalParameters(Stack<String> remainingArgumentStack, Map<String, Object> currentArgumentMap) {
        this.guardEnoughParameters(this.getArgument(), remainingArgumentStack);

        consumeRoCrateBaseLocationParameter(remainingArgumentStack.pop(), currentArgumentMap);

        this.additionalParametersProcessed = true;
        return currentArgumentMap;
    }

    private void consumeRoCrateBaseLocationParameter(String location, Map<String, Object> currentArgumentMap) {
        currentArgumentMap.put(RoCrate2ExcelConvertor.RO_CRATE_ROOT, location);
    }

    @Override
    public Convertor resolve(Map<String, Object> parameterMap) {
        guardParametersProcessed();
        return new RoCrate2ExcelConvertor(parameterMap);
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
