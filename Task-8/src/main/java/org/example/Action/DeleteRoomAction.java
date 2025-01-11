package org.example.Action;

import annotation.OwnInject;
import model.RoomManager;
import util.DI.DI;
import util.IAction;
import viev.ConsoleView;

public class DeleteRoomAction implements IAction {
    @OwnInject
    RoomManager roomManager;
    @OwnInject
    private ConsoleView consoleView;
    public  DeleteRoomAction() throws IllegalAccessException {}
    @Override
    public void execute() {
        int roomNumber = Integer.parseInt(consoleView.getInput("Inter room number , you want to delete:\n"));
        roomManager.deleteRoom(roomNumber);
        consoleView.SuccessfulNotification();
    }
}
