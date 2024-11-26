import java.util.ArrayList;

public class RoomManager {
    private ArrayList<Room> rooms = new ArrayList<>();
    public ArrayList<Room> getRooms() {
        return rooms;
    }
    public void addToListRoom(Room room) {
         rooms.add(room);
    }
}
