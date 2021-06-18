package at.tuwien.rocreateprofil;

import at.tuwien.rocreateprofil.arguments.CommandLineArgumentHandler;
import at.tuwien.rocreateprofil.convertor.Convertor;

import java.io.IOException;

public class Run {

    public static void main(String args[]) throws IOException {
        CommandLineArgumentHandler argumentHandler = new CommandLineArgumentHandler();
        Convertor convertor = argumentHandler.handle(args);
        if (convertor != null) {
            convertor.convert();
        }
    }
}
