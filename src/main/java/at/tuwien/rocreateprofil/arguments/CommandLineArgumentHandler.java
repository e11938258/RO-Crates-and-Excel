package at.tuwien.rocreateprofil.arguments;

import at.tuwien.rocreateprofil.convertor.Convertor;
import at.tuwien.rocreateprofil.arguments.handler.ArgumentHandler;
import at.tuwien.rocreateprofil.exception.RoCrateProfileBaseException;

import java.lang.reflect.Constructor;
import java.util.*;

import static at.tuwien.rocreateprofil.exception.RoCrateError.*;

public class CommandLineArgumentHandler {

    Map<String, Object> parameterMap = new HashMap<>();
    private Convertor convertor;

    public Convertor handle(String[] argv) {
        Stack<String> argStack = buildArgumentStack(argv);
        while (!argStack.isEmpty()) {
            String processedArgument = argStack.pop();
            CommandLineArgument resolvedArg = CommandLineArgument.fromString(processedArgument);

            ArgumentHandler handler = instantiateHandler(resolvedArg);
            parameterMap = handler.consumeAdditionalParameters(argStack, parameterMap);
            Convertor resolvedConvertor = handler.handle(parameterMap);
            guardOnlyOneConvertorResolved(resolvedConvertor);
            this.convertor = resolvedConvertor;
        }

        return this.convertor;
    }

    private void guardOnlyOneConvertorResolved(Convertor resolvedConvertor) {
        if (this.convertor != null && resolvedConvertor != null) {
            throw new RoCrateProfileBaseException(MULTIPLE_CONVERSIONS_NOT_ALLOWED);
        }
    }

    private ArgumentHandler instantiateHandler(CommandLineArgument resolvedArg) {
        Class clazz = resolvedArg.getHandlerClass();
        try {
            List<Constructor> constructors = Arrays.asList(clazz.getConstructors());
            for (Constructor c: constructors) {
                if (c.getParameterCount() == 0) {
                    return (ArgumentHandler) c.newInstance();
                }
            }
            throw new RoCrateProfileBaseException(ZERO_PARAMETER_CONSTRUCTOR_REQUIRED, clazz.getName());
        } catch (ReflectiveOperationException e) {
            throw new RoCrateProfileBaseException(HANDLER_CANNOT_BE_INSTANTIATED, clazz.getName());
        }
    }

    private Stack<String> buildArgumentStack(String[] argv) {
        Stack<String> argStack = new Stack<>();
        for (int i = argv.length - 1; i >= 0; i--) {
            argStack.push(argv[i]);
        }
        return argStack;
    }

}
