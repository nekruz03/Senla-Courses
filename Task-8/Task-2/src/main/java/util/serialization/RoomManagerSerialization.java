package util.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Room;
import model.RoomManager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

public class RoomManagerSerialization {
    static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    public static void saveState(RoomManager roomManager) throws IOException {
        List<Room> rooms = roomManager.getAllRooms();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(dateFormat);
        objectMapper.writerWithDefaultPrettyPrinter()
                .writeValue(new File("src/main/java/org/example/data/states/rooms.json"), rooms);
    }

    public static RoomManager loadState(RoomManager roomManager) throws IOException {
        Gson gson = new GsonBuilder()
                .setDateFormat("dd.MM.yyy")
                .create();
        File file = new File("src/main/java/org/example/data/states/rooms.json");
        Type roomListType = new TypeToken<List<Room>>(){}.getType();
        List<Room> rooms = gson.fromJson(new FileReader(file), roomListType);
        if (rooms != null) {
            for (Room room : rooms) {
                roomManager.addMapRoom(room.getRoomNumber(), room);
            }
        }
        return  roomManager;
    }
}
