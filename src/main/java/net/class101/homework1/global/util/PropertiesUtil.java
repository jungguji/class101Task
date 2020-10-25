package net.class101.homework1.global.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
    public static final String CHARSET_ISO = "ISO-8859-1";
    public static final String CHARSET_UTF8 = "UTF-8";

    private static final String PREFIX = "src/main/resources/properties/";
    private static final String SUFFIX = ".properties";

    public static String getMessage(String domainName, String key) {
        String resource = PREFIX + domainName + SUFFIX;

        String value = "";
        try(FileInputStream propFis = new FileInputStream(resource)) {
            Properties properties = new Properties();
            properties.load(new BufferedInputStream(propFis));

            value = new String(properties.getProperty(key).getBytes(CHARSET_ISO), CHARSET_UTF8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return value;
    }
}
