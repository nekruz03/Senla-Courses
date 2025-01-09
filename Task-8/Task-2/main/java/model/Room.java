package model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.example.Enum.RoomStatus;
import org.example.Enum.RoomType;
import org.example.Enum.ServiceType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Room {
    @JsonIgnore
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private int roomNumber;
    private RoomStatus roomStatus;
    private double prise;
    private RoomType roomType;
    private boolean isOccupied;
    private String guestName;
    private String guestSurname;
    private String passportNumber;
    private int capasity;
    private int numberOfStars;
    private Date dateOfOccupation;
    private  Date dateOfEviction;
    private List<ServiceType> serviceTypes;
    private List<GuestRecord> guestRecords;
    private RoomManager roomManager;
    public Room(int roomNumber, RoomType roomType, RoomStatus roomStatus, double prise, int capasity, int numberOfStars) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.roomStatus = roomStatus;
        this.prise = prise;
        this.isOccupied = false;
        this.guestName = null;
        this.guestSurname = null;
        this.passportNumber = null;
        this.capasity = capasity;
        this.numberOfStars = numberOfStars;
        this.serviceTypes = new ArrayList<>();
        this.guestRecords = new ArrayList<>();
    }
    public Room(){};
    public boolean isOccupied() {
        return isOccupied;
    }
    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }
    public boolean getIsOccupied() {
        return isOccupied;
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

    public int getCapasity() {
        return capasity;
    }
    public void setCapasity(int capasity) {
        this.capasity = capasity;
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
    public void setNumberOfStars(int numberOfStars) {
        this.numberOfStars = numberOfStars;
    }
    public int getNumberOfStars() {
        return numberOfStars;
    }
    public void setDateOfOccupation(Date dateOfOccupation) {
        this.dateOfOccupation = dateOfOccupation;
    }
    public Date getDateOfOccupation() {
        return dateOfOccupation;
    }
    public void setDateOfEviction(Date dateOfEviction) {
        this.dateOfEviction = dateOfEviction;
    }
    public Date getDateOfEviction() {
        return dateOfEviction;
    }
    public void setGuestSurname(String guestSurname){
        this.guestSurname = guestSurname;
    }
    public String getGuestSurname(){
        return guestSurname;
    }
    @Override
    public String toString() {
        return "roomNumber: " + roomNumber +
                ", roomStatus: " + roomStatus +
                ", prise: " + prise +
                ", roomType: " + roomType +
                ", isOccupied: " + isOccupied +
                ", guestName: " + guestName +
                ", guestSurname: " + guestSurname +
                ", passportNumber: " + passportNumber +
                ", capasity: " + capasity +
                ", numberOfStars: " + numberOfStars +
                ", dateOfOccupation: " + (dateOfOccupation != null ? dateFormat.format(dateOfOccupation) : "null") +
                ", dateOfEviction: " + (dateOfEviction != null ? dateFormat.format(dateOfEviction) : "null") +
                " serviceType: " + serviceTypes + "\n" +
                "history_guests: " + guestRecords.toString()+ "\n";

    }
    public void addGuestToHistory(String guestName, String guestSurname, String passportNumber, Date dateOfOccupation, Date dateOfEviction) {
            if (guestRecords.size() > roomManager.getGuestHistoryMax()) {
                guestRecords.remove(0);
            }
        GuestRecord guestRecord = new GuestRecord(guestName, guestSurname, passportNumber, dateOfOccupation, dateOfEviction);
        guestRecords.add(guestRecord);
    }
    public List<ServiceType> getServiceTypes() {
        return serviceTypes;
    }
    public void addServiceType(List<ServiceType> serviceTypes) {
        if (this.serviceTypes == null) {
            this.serviceTypes = new ArrayList<>();
        }
        for (ServiceType serviceType : serviceTypes) {
            this.serviceTypes.add(serviceType);
        }
    }
}