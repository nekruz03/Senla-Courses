package org.example.Action;

import annotation.OwnInject;
import org.example.Enum.RoomStatus;
import org.example.Enum.RoomType;
import model.Room;
import model.RoomManager;
import util.Auxiliary;
import util.DI.DI;
import util.IAction;
import viev.ConsoleView;

public class AddRoomAction implements IAction {
    @OwnInject
    private RoomManager roomManager;
    @OwnInject
    private ConsoleView consoleView;
    public AddRoomAction() throws IllegalAccessException {}
    @Override
    public void execute() {
        int roomNumber = Integer.parseInt(consoleView.getInput("Inter room number, you want to addRoom:\n"));
        int choseRoomType = Integer.parseInt(consoleView.getInput("Chose room type: STANDARD(1),DELUXE(2), VIP(3):\n"));
        RoomType roomType = Auxiliary.getRoomTypeByNumber(choseRoomType);
        int choseRoomStatus = Integer.parseInt(consoleView.getInput("Chose room status: SERVICED(1), REPAIRABLE(2):\n"));
        RoomStatus roomStatus = Auxiliary.getRoomStatusByNumber(choseRoomStatus);
        double price = Double.parseDouble(consoleView.getInput("Enter price:\n"));
        int capacity = Integer.parseInt(consoleView.getInput("Enter capacity:\n"));
        int numberOfStars = Integer.parseInt(consoleView.getInput("Enter number of stars:\n"));
        Room room = new Room(roomNumber, roomType, roomStatus, price, capacity, numberOfStars);
        roomManager.addMapRoom(roomNumber, room);
        consoleView.SuccessfulNotification();
    }

}
