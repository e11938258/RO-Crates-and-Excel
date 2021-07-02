package at.tuwien.rocreateprofil.output.rocrate.util;

import java.util.HashMap;
import java.util.UUID;

public class UniqueIdentifierGenerator {

    private static final String LOCAL_RESOLUTION_IDENTIFIER = "#";
    private static final String PREFIX_SUFFIX_SEPARATOR = "-";
    private static final int DEFAULT_SUFFIX_LENGTH = 4;

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

    public static synchronized String generateUniqueUUIDWithPrefix(String prefix) {
        return generateUniqueUUIDWithPrefix(prefix, DEFAULT_SUFFIX_LENGTH);
    }

    public static synchronized String generateUniqueUUIDWithPrefix(String prefix, Integer randomSuffixLength) {
        String notYetUsedUuid = prefix + PREFIX_SUFFIX_SEPARATOR + generateRandomUuid(randomSuffixLength);
        while (uuidsInUse.containsKey(notYetUsedUuid)) {
            notYetUsedUuid = prefix + PREFIX_SUFFIX_SEPARATOR + generateRandomUuid(randomSuffixLength);
        }
        // prepend with # to indicate that the reference is within Ro-Crate
        notYetUsedUuid = LOCAL_RESOLUTION_IDENTIFIER + notYetUsedUuid;
        return notYetUsedUuid;
    }

    private static String generateRandomUuid() {
        UUID newUuid = UUID.randomUUID();
        return newUuid.toString();
    }

    private static String generateRandomUuid(int length) {
        return generateRandomUuid().substring(0, length);
    }
}
