package at.tuwien.rocreateprofil.arguments.handler;

import at.tuwien.rocreateprofil.convertor.Convertor;
import at.tuwien.rocreateprofil.convertor.RoCrate2ExcelGenerator;
import at.tuwien.rocreateprofil.arguments.CommandLineArgument;

import java.util.Map;
import java.util.Stack;

public class RoCrate2ExcelArgumentHandler implements ArgumentHandler {

    private static final CommandLineArgument argument = CommandLineArgument.RO_CRATE_TO_EXCEL;

    private boolean additionalParametersProcessed = false;

    @Override
    public Map<String, Object> consumeAdditionalParameters(Stack<String> remainingArgumentStack, Map<String, Object> currentArgumentMap) {
        this.guardEnoughParameters(this.getArgument(), remainingArgumentStack);

        consumeRoCrateRootLocationParameter(remainingArgumentStack.pop(), currentArgumentMap);

        this.additionalParametersProcessed = true;
        return currentArgumentMap;
    }

    private void consumeRoCrateRootLocationParameter(String location, Map<String, Object> currentArgumentMap) {
        currentArgumentMap.put(RoCrate2ExcelGenerator.RO_CRATE_ROOT, location);
    }

    @Override
    public Convertor handle(Map<String, Object> parameterMap) {
        guardParametersProcessed();
        return new RoCrate2ExcelGenerator(parameterMap);
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
