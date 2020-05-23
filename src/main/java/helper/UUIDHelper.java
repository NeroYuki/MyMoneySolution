package helper;

import java.util.UUID;

public class UUIDHelper {
    public static String newUUIDString() {
        return UUID.randomUUID().toString();
    }
}
