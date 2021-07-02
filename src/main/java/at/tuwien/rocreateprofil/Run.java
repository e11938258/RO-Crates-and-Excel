package at.tuwien.rocreateprofil;

import at.tuwien.rocreateprofil.arguments.CommandLineArgumentHandler;
import at.tuwien.rocreateprofil.convertor.Convertor;
import at.tuwien.rocreateprofil.exception.RoCrateProfileBaseException;

import java.io.IOException;

public class Run {

    public static void main(String args[]) throws IOException {
        try {
            CommandLineArgumentHandler argumentHandler = new CommandLineArgumentHandler();
            Convertor convertor = argumentHandler.handle(args);
            if (convertor != null) {
                convertor.convert();
                convertor.writeOutput();
            }
        } catch (RoCrateProfileBaseException e) {
            System.out.println("[ERROR]:" + e.getMessage());
        } finally {
            TemporaryResourceStore.clearStore();
        }
    }
}
