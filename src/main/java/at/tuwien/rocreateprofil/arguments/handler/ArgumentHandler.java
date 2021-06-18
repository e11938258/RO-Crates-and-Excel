package at.tuwien.rocreateprofil.arguments.handler;

import at.tuwien.rocreateprofil.convertor.Convertor;
import at.tuwien.rocreateprofil.arguments.CommandLineArgument;
import at.tuwien.rocreateprofil.exception.Error;
import at.tuwien.rocreateprofil.exception.RoCrateProfileBaseException;

import java.util.Map;
import java.util.Stack;

import static at.tuwien.rocreateprofil.exception.Error.ARGUMENT_MANDATORY_PARAMETERS_NOT_PROCESSED;

public interface ArgumentHandler {

    Map<String, Object> consumeAdditionalParameters(Stack<String> remainingArgumentStack, Map<String, Object> currentArgumentMap);

    Convertor handle(Map<String, Object> parameterMap);

    boolean wereParametersProcessed();

    default void guardEnoughParameters(CommandLineArgument argument, Stack<String> remainingArgumentStack) {
        int argumentsLeft = remainingArgumentStack.size();
        int argumentsRequired = argument.getNumberOfAdditionalArgumentsConsumed();
        if (argumentsLeft < argumentsRequired) {
            throw new RoCrateProfileBaseException(Error.INSUFFICIENT_ARGUMENTS_TO_CONSUME, argument.getArgument(), argumentsRequired, argumentsLeft);
        }
    }

    default void guardParametersProcessed() {
        if (!wereParametersProcessed()) {
            throw new RoCrateProfileBaseException(ARGUMENT_MANDATORY_PARAMETERS_NOT_PROCESSED);
        }
    }

    CommandLineArgument getArgument();
}
