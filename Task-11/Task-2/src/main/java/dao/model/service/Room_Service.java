package dao.model.service;

import annotation.OwnInject;
import dao.model.Dao.RoomDao;
import dao.model.entity.Room;
import viev.ConsoleView;

import java.util.List;

public class Room_Service {
    @OwnInject
    ConsoleView consoleView;
    @OwnInject
    ServiceType_Service serviceType_Service;
    @OwnInject
    private RoomDao roomDao;
    public int addRoom(){
        Room room = roomDao.getRoomFromConsole();
        roomDao.save(room);
        return room.getRoomNumber();
    }
    public int updateRoom(){
        Room room = roomDao.getRoomFromConsole();
        roomDao.update(room);
        return room.getRoomNumber();
    }
    public void find_by_id (int id){
        Room room = roomDao.findById(id);
        if (room.getRoomNumber() == null) consoleView.print("Room not found");
        else {
            System.out.println(room);
        }
    }
    public void getAllRooms(){
        List<Room> rooms = roomDao.findAll();
        if (rooms.isEmpty()) consoleView.print("No rooms found");
        for (Room room : rooms) {
            System.out.println(room);
        }
    }
    public void displayRoomsSortedByNumber(){
        List<Room> rooms = roomDao.getRoomsSortedByNumber();
        if (rooms == null) System.out.println("Empty list of rooms!");
        else {
            for (Room room : rooms) {
                System.out.println(room);
            }
        }
    }
    public void displayRoomsSortedByPrice(){
        List<Room> rooms = roomDao.getRoomsSortedByPrice();
        if (rooms == null) System.out.println("Empty list of rooms!");
        else {
            for (Room room : rooms) {
                System.out.println(room);
            }
        }
    }
    public void deleteRoom(){
        Room room = roomDao.getRoomFromConsole();
        roomDao.delete(room);
    }
    public void addingAdditionalServices(){
        serviceType_Service.getAllServiceTypes();
        roomDao.addServicesInToRoom();
    }
    public void calculate(){
        double cost =  roomDao.calculate();
        consoleView.display("Total price: $" + cost);
    }
    public  void  numberOfGuestsInRoom(){
        roomDao.numberOfGuestsInRoom();
    }
}

