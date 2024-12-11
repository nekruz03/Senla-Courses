package model;

import Enum.RoomStatus;
import Enum.RoomType;
import Enum.ServiceType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Room {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private int roomNumber;
    private RoomStatus roomStatus;
    private double prise;
    private RoomType roomType;
    private boolean isOccupied;
    private String guestName;
    private String passportNumber;
    private int capasity;
    private int numberOfStars;
    private Date dateOfOccupation;
    private  Date dateOfEviction;
    private List<ServiceType> serviceTypes;
    public Room(int roomNumber, RoomType roomType, RoomStatus roomStatus, double prise, int capasity, int numberOfStars) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.roomStatus = roomStatus;
        this.prise = prise;
        this.isOccupied = false;
        this.guestName = null;
        this.passportNumber = null;
        this.capasity = capasity;
        this.numberOfStars = numberOfStars;
        this.serviceTypes = new ArrayList<>();
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
    @Override
    public String toString() {
        return "roomNumber: " + roomNumber +
                ", roomStatus: " + roomStatus +
                ", prise: " + prise +
                ", roomType: " + roomType +
                ", isOccupied: " + isOccupied +
                ", guestName: " + guestName +
                ", passportNumber: " + passportNumber +
                ", capasity: " + capasity +
                ", numberOfStars: " + numberOfStars +
                ", dateOfOccupation: " + (dateOfOccupation != null ? dateFormat.format(dateOfOccupation) : "null") +
                ", dateOfEviction: " + (dateOfEviction != null ? dateFormat.format(dateOfEviction) : "null") +
                " serviceType " + serviceTypes + "\n";

    }
    public List<ServiceType> getServiceTypes() {
        return serviceTypes;
    }
    public void addServiceType(List<ServiceType> serviceTypes) {
        for (ServiceType serviceType : serviceTypes) {
            this.serviceTypes.add(serviceType);
        }
    }
}