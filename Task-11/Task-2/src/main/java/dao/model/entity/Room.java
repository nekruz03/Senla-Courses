package dao.model.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.text.SimpleDateFormat;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Room {
    @JsonIgnore
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private Integer roomNumber;
    private String roomStatus;
    private String roomType;
    private Integer capacity;
    private Integer numberOfStars;
    private Double price;

    public Room(Integer roomNumber, String roomStatus, String roomType, Integer capacity, Integer numberOfStars, Double price) {
        this.roomNumber = roomNumber;
        this.roomStatus = roomStatus;
        this.roomType = roomType;
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

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getNumberOfStars() {
        return numberOfStars;
    }

    public void setNumberOfStars(Integer numberOfStars) {
        this.numberOfStars = numberOfStars;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Room{" +
                ", roomNumber=" + roomNumber +
                ", roomStatus='" + roomStatus + '\'' +
                ", capacity=" + capacity +
                ", numberOfStars=" + numberOfStars +
                ", price=" + price +
                '}';
    }
}