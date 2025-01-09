package model;

import annotation.ConfigProperty;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class RoomManager {
    @ConfigProperty(propertyName = "room.status.change.enabled")
    private boolean roomStatusChangeEnabled;
    @ConfigProperty(propertyName = "guest.history.max")
    private int guestHistoryMax;
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
    public boolean getRoomStatusChangeEnabled() {
        return roomStatusChangeEnabled;
    }
    public int  getGuestHistoryMax() {
        return guestHistoryMax;
    }
}
