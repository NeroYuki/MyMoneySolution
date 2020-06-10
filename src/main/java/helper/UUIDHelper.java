package helper;

import java.util.UUID;

public class UUIDHelper {
    //Create a 128-bit unique String (36 character long)
    public static String newUUIDString() {
        return UUID.randomUUID().toString();
    }
}
