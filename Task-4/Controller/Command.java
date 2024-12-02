package Controller;

import Enums.RoomStatus;
import Enums.RoomType;
import Enums.ServiceType;
import Model.Room;
import Model.RoomManager;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Command {
    List<Room>currentGuests  = new ArrayList<>();
    private RoomManager roomManager;

    public Command(RoomManager roomManager) {
        this.roomManager = roomManager;
    }
    public void addRoom(int roomNumber, RoomType roomType, RoomStatus roomStatus, double price, int capasity, int numberOfStars ) {
        Room room = new Room(roomNumber, roomType, roomStatus, price,capasity, numberOfStars);
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
    public void checkIn(int roomNumber, String guestName, String passportNumber, Date dateOfOccupation, Date dateOfEviction) {
        Room room = roomManager.getRooms().get(roomNumber);
        if (room != null) {
            if (!room.isOccupied() || room.getRoomStatus() == RoomStatus.REPAIRABLE) {
                System.out.println("Room is already occupied or under repair.");
                return;
            }
            room.setOccupied(false);
            room.setGuestName(guestName);
            room.setPassportNumber(passportNumber);
            room.setDateOfOccupation(dateOfOccupation);
            room.setDateOfEviction(dateOfEviction);
            currentGuests.add(room);
        } else {
            System.out.println("Room with number " + roomNumber + " does not exist.");
        }
    }

    public void checkOut(int roomNumber) {
        Room room = roomManager.getRooms().get(roomNumber);
        if (room != null) {
            room.setOccupied(true);
            room.setGuestName(null);
            room.setPassportNumber(null);
            room.setDateOfOccupation(null);
            room.setDateOfEviction(null);
            currentGuests.remove(room);
        } else {
            System.out.println("Room with number " + roomNumber + " does not exist.");
        }
    }
    public List<Room> getListOfAvailableRooms(){
        List<Room> rooms = new ArrayList<>();
        for (Room room : roomManager.getRooms().values()){
            if (!room.isOccupied()) rooms.add(room);
        }
        return rooms;
    }
    public List<Room> getListOfGuests(){
        List<Room> rooms = new ArrayList<>();
        for (Room room : roomManager.getRooms().values()){
            if (room.getGuestName() != null) rooms.add(room);
        }
        return rooms;
    }
    public int numberOfFreeRooms(){
        int col  = 0 ;
        List<Room>rooms = roomManager.getAllRooms();
        for (Room room : rooms){
            if (room.isOccupied()) col++;
        }
        return  col;
    }
    public int numberOfGuests(){
        int col  = 0 ;
        List<Room>rooms = roomManager.getAllRooms();
        for (Room room : rooms){
            if (room.getGuestName() != null) col++;
        }
        return col;
    }
     public List<Room> getListOfVailableRoomsInDate(Date date){
         List<Room> ans = new ArrayList<>();
         List<Room> rooms = roomManager.getAllRooms();
        for (Room room : rooms){
            if (!room.isOccupied()){
              if (date.before(room.getDateOfOccupation())) ans.add(room);
              else {
                  if (date.after(room.getDateOfEviction()) || date.compareTo(room.getDateOfEviction()) == 0) ans.add(room);
              }
            }

            if (room.isOccupied()) ans.add(room);
        }
        return ans;
     }
     public double priseColculation(int roomNumber){
        double sum = 0;
        Room room = roomManager.getRooms().get(roomNumber);
         LocalDate dateOfOccupation = room.getDateOfOccupation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
         LocalDate dateOfEviction = room.getDateOfEviction().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
         List<ServiceType> serviceTypes = room.getServiceTypes();
         for (ServiceType serviceType : serviceTypes){
            sum = sum + serviceType.getPrise();
         }
         int colDays = (int) ChronoUnit.DAYS.between(dateOfOccupation, dateOfEviction);
       return (room.getPrise() + sum)* colDays;
     }

     public List<Room> display() {
        return new ArrayList<>(roomManager.getRooms().values());
    }
    public List<Room> viewTheLastThreeGuests(){
        List<Room> lastThreeGuests = new ArrayList<>();
        for (int i = currentGuests.size() - 1; i >= 0 && lastThreeGuests.size() < 3; i--) {
            lastThreeGuests.add(currentGuests.get(i));
        }
        return lastThreeGuests;
    }
    public void addingAdditionalServices(int roomNumber , List<ServiceType> serviceType){
        Room room = roomManager.getRooms().get(roomNumber);
        room.addServiceType(serviceType);
    }
}
