package dao.model.entity;

import java.util.Date;

public class GuestRecord {
    private int roomNumber;
    private int guestId;
    private Date dateOfOccupation;
    private Date dateOfEviction;
    private String status;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GuestRecord(int roomNumber, int guestId, Date dateOfOccupation, Date dateOfEviction, String status) {
        this.roomNumber = roomNumber;
        this.guestId = guestId;
        this.dateOfOccupation = dateOfOccupation;
        this.dateOfEviction = dateOfEviction;
        this.status = status;
    }

    public Date getDateOfEviction() {
        return dateOfEviction;
    }

    public void setDateOfEviction(Date dateOfEviction) {
        this.dateOfEviction = dateOfEviction;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public Date getDateOfOccupation() {
        return dateOfOccupation;
    }

    public void setDateOfOccupation(Date dateOfOccupation) {
        this.dateOfOccupation = dateOfOccupation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "GuestRecord{" +
                "roomNumber=" + roomNumber +
                ", guestId=" + guestId +
                ", dateOfOccupation=" + dateOfOccupation +
                ", dateOfEviction=" + dateOfEviction +
                ", status='" + status + '\'' +
                '}';
    }
}
