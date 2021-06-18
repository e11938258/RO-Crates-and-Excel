package at.tuwien.rocreateprofil.arguments;

import at.tuwien.rocreateprofil.arguments.handler.Excel2RoCrateArgumentHandler;
import at.tuwien.rocreateprofil.arguments.handler.RoCrate2ExcelArgumentHandler;
import at.tuwien.rocreateprofil.exception.Error;
import at.tuwien.rocreateprofil.exception.RoCrateProfileBaseException;

public enum CommandLineArgument {

    EXCEL_TO_RO_CRATE("excel2rocrate", true, 1, Excel2RoCrateArgumentHandler.class),
    RO_CRATE_TO_EXCEL("rocrate2excel", true, 1, RoCrate2ExcelArgumentHandler.class);

    private CommandLineArgument(String argument, boolean consumesAdditionalArguments, int numberOfAdditionalArgumentsConsumed, Class handlerClass) {
        this.argument = argument;
        this.consumesAdditionalArguments = consumesAdditionalArguments;
        this.numberOfAdditionalArgumentsConsumed = numberOfAdditionalArgumentsConsumed;
        this.handlerClass = handlerClass;
    }
    private String argument;
    private boolean consumesAdditionalArguments;
    private int numberOfAdditionalArgumentsConsumed;
    private Class handlerClass;

    public static CommandLineArgument fromString(String argumentToResolve) {
        for (CommandLineArgument checkedArgument: CommandLineArgument.values()) {
            if (checkedArgument.argument.equals(argumentToResolve)) {
                return checkedArgument;
            }
        }
        throw new RoCrateProfileBaseException(Error.UNKNOWN_ARGUMENT, argumentToResolve);
    }

    public String getArgument() {
        return this.argument;
    }

    public boolean consumesAdditionalArguments() {
        return this.consumesAdditionalArguments;
    }

    public int getNumberOfAdditionalArgumentsConsumed() {
        return this.numberOfAdditionalArgumentsConsumed;
    }

    public Class getHandlerClass() {
        return this.handlerClass;
    }
}
