import Enums.RoomStatus;
import Enums.RoomType;

import java.util.ArrayList;
import java.util.Scanner;

public class Command {
    private RoomStatus st;
    private RoomManager roomManager = new RoomManager();
    ;
    private RoomStatus roomStatus;
    private RoomType roomType = null;
    private ArrayList<Room> rooms;
    private Scanner input = new Scanner(System.in);

    public Command(RoomManager roomManager) {
        this.roomManager = roomManager;
    }

    public void addRoom() {
        System.out.println("Please indicate the room number: ");
        int roomNumber = input.nextInt();
        System.out.println("Indicate room type STANDARD(1), DELUXE(2), VIP(3)");
        int typeChoice = input.nextInt();
        if (typeChoice == 1) roomType = RoomType.STANDARD;
        else if (typeChoice == 2) roomType = RoomType.DELUXE;
        else if (typeChoice == 3) roomType = RoomType.VIP;
        System.out.println("SERVICED(1), REPAIRABLE(2)\n");
        int statusChoise = input.nextInt();
        if (statusChoise == 1) roomStatus = RoomStatus.SERVICED;
        else if (statusChoise == 2) roomStatus = RoomStatus.REPAIRABLE;
        System.out.println("Specify price");
        double price = input.nextDouble();
        Room room = new Room(roomNumber, roomType, roomStatus, price);
        roomManager.addToListRoom(room);
    }

    public void changePrice() {
        System.out.println("Please, enter room number");
        int roomNumber = input.nextInt();
        System.out.println("Please, set a new price");
        double prise = input.nextDouble();
        int i = 0;
        rooms = roomManager.getRooms();
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                room.setPrise(prise);
                break;
            }
        }
        System.out.println("Successfully, the price has been changed.");
    }

    public void changeType() {
        System.out.println("Please, enter room number");
        int roomNumber = input.nextInt();
        System.out.println("Please, set type STANDARD(1), DELUXE(2), VIP(3)");
        int typeChoice = input.nextInt();
        rooms = roomManager.getRooms();
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                if (typeChoice == 1) {
                    room.setRoomType(RoomType.STANDARD);
                } else if (typeChoice == 2) {
                    room.setRoomType(RoomType.DELUXE);
                } else if (typeChoice == 3) {
                    room.setRoomType(RoomType.VIP);
                }
            }
        }
        System.out.println("Successfully changed the type.");
    }

    public void deleteRoom() {
        System.out.println("Please, enter the room number you want, to delete.");
        int roomNumber = input.nextInt();
        rooms = roomManager.getRooms();
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                rooms.remove(room);
                break;
            }
        }
        System.out.println("The room was successfully deleted.");
    }

    public void changeRoomStatus() {
        System.out.println("Please, enter the room number for which you want to change the status.\n");
        int roomNumber = input.nextInt();
        System.out.println("SERVICED(1), REPAIRABLE(2)\n");
        int statusChoise = input.nextInt();
        rooms = roomManager.getRooms();
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                if (statusChoise == 1) roomType = RoomType.STANDARD;
                else if (statusChoise == 2) roomType = RoomType.DELUXE;
                else if (statusChoise == 3) roomType = RoomType.VIP;
            }
        }
        System.out.println("Successfully,changed the room status.");
    }

    public void chekIn() {
        System.out.println("Enter room number for check-in:");
        int roomNumber = input.nextInt();
        input.nextLine();
        rooms = roomManager.getRooms();
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                if (room.isOccupied() == true) {
                    System.out.println("Room is already occupied!");
                    return;
                }
                if (room.getRoomStatus() == RoomStatus.REPAIRABLE) {
                    System.out.println("Room is under repair and cannot be occupied!");
                    return;
                }
                System.out.println("Enter guest name:");
                String guestName = input.nextLine();
                System.out.println("Enter guest passport number:");
                String passportNumber = input.nextLine();
                room.setOccupied(true);
                room.setGuestName(guestName);
                room.setPassportNumber(passportNumber);
                System.out.println("Guest " + guestName + " with passport " + passportNumber +
                        " successfully checked into room " + roomNumber);
                return;
            }
        }
    }

    public void checkOut() {
        System.out.println("Enter room number for check-out:");
        int roomNumber = input.nextInt();
        rooms = roomManager.getRooms();
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                if (!room.isOccupied()) {
                    System.out.println("Room is already vacant!");
                    return;
                }
            }
            String guestName = room.getGuestName();
            String passportNumber = room.getPassportNumber();
            room.setOccupied(false);
            room.setGuestName(null);
            room.setPassportNumber(null);
            System.out.println("Guest " + guestName + " with passport " + passportNumber +
                    " successfully checked out from room " + roomNumber);
            return;
        }
    }
    public void display () {
        System.out.println(roomManager.getRooms()+ "\n");
    }
}


