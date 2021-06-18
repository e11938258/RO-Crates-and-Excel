package at.tuwien.rocreateprofil.output.rocrate.util;

import java.util.HashMap;
import java.util.UUID;

public class UniqueIdentifierGenerator {

    private static final HashMap<String, String> uuidsInUse = new HashMap<>();

    public static synchronized String generateUniqueUUID() {
        String notYetUsedUuid = generateRandomUuid();
        while (uuidsInUse.containsKey(notYetUsedUuid)) {
            notYetUsedUuid = generateRandomUuid();
        }
        return notYetUsedUuid;
    }

    private static String generateRandomUuid() {
        UUID newUuid = UUID.randomUUID();
        return newUuid.toString();
    }
}
