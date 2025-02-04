package dao.model;

public class RoomService {
    private int roomNumber;
    private int serviceTypeId;

    public RoomService(int roomNumber, int serviceTypeId) {
        this.roomNumber = roomNumber;
        this.serviceTypeId = serviceTypeId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(int serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    @Override
    public String toString() {
        return "RoomService{" +
                "roomNumber=" + roomNumber +
                ", serviceTypeId=" + serviceTypeId +
                '}';
    }
}
