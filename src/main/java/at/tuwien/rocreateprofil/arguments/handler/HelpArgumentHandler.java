package at.tuwien.rocreateprofil.arguments.handler;

import at.tuwien.rocreateprofil.arguments.CommandLineArgument;
import at.tuwien.rocreateprofil.convertor.Convertor;

import java.util.Map;
import java.util.Stack;

public class HelpArgumentHandler implements ArgumentHandler {

    private static final String HELP_MESSAGE = "Ro-crate <-> excel (xlsx only!) convertor:\n" +
            "Supported arguments:\n" +
            "help                                                    Prints this help message\n" +
            "excel2rocrate <excel-file-location> <license-URL>       Converts provided excel file into a RO Crate object with provided license (mandatory)\n" +
            "rocrate2excel <ro-crate-folder-location>                Reconstructs an excel file from the Ro Crate";

    public HelpArgumentHandler() {}

    @Override
    public Map<String, Object> consumeAdditionalParameters(Stack<String> remainingArgumentStack, Map<String, Object> currentArgumentMap) {
        return currentArgumentMap;
    }

    @Override
    public Convertor handle(Map<String, Object> parameterMap) {
        System.out.println(HELP_MESSAGE);
        System.exit(0);
        return null;
    }

    @Override
    public boolean wereParametersProcessed() {
        return true;
    }

    @Override
    public CommandLineArgument getArgument() {
        return null;
    }
}
