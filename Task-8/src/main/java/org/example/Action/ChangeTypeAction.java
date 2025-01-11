package org.example.Action;

import annotation.OwnInject;
import org.example.Enum.RoomType;
import model.RoomManager;
import util.Auxiliary;
import util.DI.DI;
import util.IAction;
import viev.ConsoleView;

public class ChangeTypeAction implements IAction {
    @OwnInject
    RoomManager roomManager;
    @OwnInject
    private ConsoleView consoleView ;
    public ChangeTypeAction() throws IllegalAccessException {}
    @Override
    public void execute() {
        int roomNumber = Integer.parseInt(consoleView.getInput("Inter room number, you want to changeType:\n"));
        int choseRoomType = Integer.parseInt(consoleView.getInput("Chose new room type: STANDARD(1),DELUXE(2), VIP(3):\n"));
        RoomType roomType = Auxiliary.getRoomTypeByNumber(choseRoomType);
        roomManager.getRooms().get(roomNumber).setRoomType(roomType);
        consoleView.SuccessfulNotification();
    }
}
