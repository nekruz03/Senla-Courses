package dao.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.text.SimpleDateFormat;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Room {
    @JsonIgnore
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private int roomNumber;
    private String roomStatus;
    private int capacity;
    private int numberOfStars;
    private double price;

    public Room(int roomNumber, String roomStatus, int capacity, int numberOfStars, double price) {
        this.roomNumber = roomNumber;
        this.roomStatus = roomStatus;
        this.capacity = capacity;
        this.numberOfStars = numberOfStars;
        this.price = price;
    }

    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getNumberOfStars() {
        return numberOfStars;
    }

    public void setNumberOfStars(int numberOfStars) {
        this.numberOfStars = numberOfStars;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Room{" +
                "dateFormat=" + dateFormat +
                ", roomNumber=" + roomNumber +
                ", roomStatus='" + roomStatus + '\'' +
                ", capacity=" + capacity +
                ", numberOfStars=" + numberOfStars +
                ", price=" + price +
                '}';
    }
}