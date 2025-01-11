package org.example.Action;

import annotation.OwnInject;
import org.example.Enum.RoomStatus;
import model.RoomManager;
import util.Auxiliary;
import util.DI.DI;
import util.IAction;
import viev.ConsoleView;

public class ChangeStatusAction implements IAction {
    @OwnInject
    RoomManager roomManager;
    @OwnInject
    private ConsoleView consoleView;
    public  ChangeStatusAction() throws IllegalAccessException {}
    @Override
    public void execute() {
        int roomNumber = Integer.parseInt(consoleView.getInput("Inter room number , you want to changeStatus:\n"));
        int choseRoomStatus = Integer.parseInt(consoleView.getInput("Chose new room status: SERVICED(1), REPAIRABLE(2):\n"));
        RoomStatus roomStatus = Auxiliary.getRoomStatusByNumber(choseRoomStatus);
        roomManager.getRooms().get(roomNumber).setRoomStatus(roomStatus);
        consoleView.SuccessfulNotification();
    }
}
