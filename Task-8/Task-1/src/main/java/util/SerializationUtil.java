package util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class SerializationUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final File STATE_FILE = new File("state.json");
    public static void saveState(Object state) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(STATE_FILE, state);
            System.out.println("Состояние успешно сохранено.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
