package at.tuwien.rocreateprofil;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class TemporaryResourceStore {

    private static List<File> temporaryResources;


    public static synchronized void storeNewTemporaryResource(String pathToTemporaryFile) {
        ensureStoreInitialized();
        temporaryResources.add(new File(pathToTemporaryFile));
    }

    public static synchronized void clearStore() {
        temporaryResources.stream().forEach(deleteResource);
    }

    private static void ensureStoreInitialized() {
        if (temporaryResources == null) {
            temporaryResources = new ArrayList<>();
        }
    }

    private static Consumer<File> deleteResource = new Consumer<File>() {
        @Override
        public void accept(File file) {
            try {
                FileUtils.deleteDirectory(file);
            } catch (IOException e) {
                System.out.println("[WARN] Failed to remove temporary resource '" + file.getAbsolutePath() + "'.");
            }
        }
    };

}
