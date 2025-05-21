package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private static final Properties PROPERTIES = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("./config.properties")) {
            PROPERTIES.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ConfigManager() {
    }

    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }
}
