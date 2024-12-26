package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static Properties prop = new Properties();
    static {
        try (InputStream inputStream = new FileInputStream("src/main/resources/config.properties")) {
            prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isStatusEnabled() {
        return Boolean.parseBoolean(prop.getProperty("room.status.change.enabled"));
    }
    public static int getGuestHistoryMax(){
        return Integer.parseInt(prop.getProperty("guest.history.max"));
    }

}
