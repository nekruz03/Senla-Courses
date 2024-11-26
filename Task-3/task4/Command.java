import Enums.RoomStatus;
import Enums.RoomType;
import java.util.ArrayList;

public class Command {
    private RoomManager roomManager;

    public Command(RoomManager roomManager) {
        this.roomManager = roomManager;
    }

    public void addRoom(int roomNumber, RoomType roomType, RoomStatus roomStatus, double price) {
        Room room = new Room(roomNumber, roomType, roomStatus, price);
        roomManager.addToListRoom(room);
    }

    public void changePrice(int roomNumber, double price) {
        ArrayList<Room> rooms = roomManager.getRooms();
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                room.setPrise(price);
                return;
            }
        }
    }

    public void changeType(int roomNumber, RoomType roomType) {
        ArrayList<Room> rooms = roomManager.getRooms();
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                room.setRoomType(roomType);
                return;
            }
        }
    }

    public void deleteRoom(int roomNumber) {
        ArrayList<Room> rooms = roomManager.getRooms();
        rooms.removeIf(room -> room.getRoomNumber() == roomNumber);
    }

    public void changeRoomStatus(int roomNumber, RoomStatus roomStatus) {
        ArrayList<Room> rooms = roomManager.getRooms();
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                room.setRoomStatus(roomStatus);
                return;
            }
        }
    }

    public void checkIn(int roomNumber, String guestName, String passportNumber) {
        ArrayList<Room> rooms = roomManager.getRooms();
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                if (room.isOccupied() || room.getRoomStatus() == RoomStatus.REPAIRABLE) {
                    return;
                }
                room.setOccupied(true);
                room.setGuestName(guestName);
                room.setPassportNumber(passportNumber);
                return;
            }
        }
    }

    public void checkOut(int roomNumber) {
        ArrayList<Room> rooms = roomManager.getRooms();
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                room.setOccupied(false);
                room.setGuestName(null);
                room.setPassportNumber(null);
                return;
            }
        }
    }

    public ArrayList<Room> display() {
        return roomManager.getRooms();
    }
}
