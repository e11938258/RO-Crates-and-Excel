package at.tuwien.rocreateprofil.output.rocrate.util;

import java.util.HashMap;
import java.util.UUID;

public class UniqueIdentifierGenerator {

    private static final String LOCAL_RESOLUTION_IDENTIFIER = "#";

    private static final HashMap<String, String> uuidsInUse = new HashMap<>();

    public static synchronized String generateUniqueUUID() {
        String notYetUsedUuid = generateRandomUuid();
        while (uuidsInUse.containsKey(notYetUsedUuid)) {
            notYetUsedUuid = generateRandomUuid();
        }
        // prepend with # to indicate that the reference is within Ro-Crate
        notYetUsedUuid = LOCAL_RESOLUTION_IDENTIFIER + notYetUsedUuid;
        return notYetUsedUuid;
    }

    private static String generateRandomUuid() {
        UUID newUuid = UUID.randomUUID();
        return newUuid.toString();
    }
}
