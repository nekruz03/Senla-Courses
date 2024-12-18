package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RoomManager {
    List<Room> lastThreeGuests = new ArrayList<>();
    private HashMap<Integer, Room> rooms = new HashMap<>();
    public HashMap<Integer, Room> getRooms() {
        return rooms;
    }
    public void addMapRoom(int number, Room room) {
        rooms.put(number, room);
    }

    public void deleteRoom(int roomNumber) {
        rooms.remove(roomNumber);
    }
    public ArrayList<Room> getAllRooms() {
        return new ArrayList<>(rooms.values());
    }
    public List<Room> getLastThreeGuests() {
        return lastThreeGuests;
    }
}
