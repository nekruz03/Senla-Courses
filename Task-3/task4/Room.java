import Enums.RoomStatus;
import Enums.RoomType;

public class Room {
    private int roomNumber;
    private RoomStatus roomStatus;
    private double prise;
    private RoomType roomType;
    private boolean isOccupied;
    private String guestName;
    private String passportNumber;

    public Room(int roomNumber, RoomType roomType, RoomStatus roomStatus, double prise) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.roomStatus = roomStatus;
        this.prise = prise;
        this.isOccupied = false;
        this.guestName = null;
        this.passportNumber = null;
    }

    public boolean isOccupied() {
        return isOccupied;
    }
    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }

    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    public void setPrise(double prise) {
        this.prise = prise;
    }

    public double getPrise() {
        return prise;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    @Override
    public String toString() {
        return "roomNumber: " + roomNumber +
                ", roomStatus: " + roomStatus +
                ", prise: " + prise +
                ", roomType: " + roomType +
                ", isOccupied: " + isOccupied +
                (isOccupied ? ", guestName: " + guestName + ", passportNumber: " + passportNumber : "");
    }
}
