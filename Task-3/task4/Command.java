import Enums.RoomStatus;
import Enums.RoomType;
import java.util.ArrayList;
import java.util.HashMap;

public class Command {
    private RoomManager roomManager;

    public Command(RoomManager roomManager) {
        this.roomManager = roomManager;
    }
    public void addRoom(int roomNumber, RoomType roomType, RoomStatus roomStatus, double price) {
        Room room = new Room(roomNumber, roomType, roomStatus, price);
        roomManager.addMapRoom(roomNumber, room);
    }
    public void changePrice(int roomNumber, double price) {
        roomManager.getRooms().get(roomNumber).setPrise(price);;
    }
    public void changeType(int roomNumber, RoomType roomType) {
       roomManager.getRooms().get(roomNumber).setRoomType(roomType);
    }
    public void deleteRoom(int roomNumber) {
        roomManager.deleteRoom(roomNumber);
    }
    public void changeStatus(int roomNumber, RoomStatus roomStatus) {
        roomManager.getRooms().get(roomNumber).setRoomStatus(roomStatus);
    }
    public void checkIn(int roomNumber, String guestName, String passportNumber) {
        Room room = roomManager.getRooms().get(roomNumber);
        if (room != null) {
            if (room.isOccupied() || room.getRoomStatus() == RoomStatus.REPAIRABLE) {
                System.out.println("Room is already occupied or under repair.");
                return;
            }
            room.setOccupied(true);
            room.setGuestName(guestName);
            room.setPassportNumber(passportNumber);
        } else {
            System.out.println("Room with number " + roomNumber + " does not exist.");
        }
    }

    public void checkOut(int roomNumber) {
        Room room = roomManager.getRooms().get(roomNumber);
        if (room != null) {
            room.setOccupied(false);
            room.setGuestName(null);
            room.setPassportNumber(null);
        } else {
            System.out.println("Room with number " + roomNumber + " does not exist.");
        }
    }

    public ArrayList<Room> display() {
        return new ArrayList<>(roomManager.getRooms().values());
    }
}
