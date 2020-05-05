package helper;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class CharacterEncoding {
    //Use this to re-encode UTF-8 string (Result of database output)
    public static String utf8toNativeEncoding(String input) throws Exception {
        Charset defaultCharset = Charset.defaultCharset();
        byte[] sourceBytes = input.getBytes(StandardCharsets.UTF_8);
        return new String(sourceBytes , defaultCharset.name());
    }

    public static String NativeEncodingtoUtf8(String input) throws Exception {
        Charset defaultCharset = Charset.defaultCharset();
        byte[] sourceBytes = input.getBytes(defaultCharset.name());
        return new String(sourceBytes, StandardCharsets.UTF_8);
    }
}
