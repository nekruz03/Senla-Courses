package org.example.Action;

import annotation.OwnInject;
import model.Room;
import model.RoomManager;
import util.DI.DI;
import util.IAction;
import viev.ConsoleView;

public class ChekOutAction implements IAction {
    @OwnInject
    RoomManager roomManager;
    @OwnInject
    private ConsoleView consoleView;
    public ChekOutAction() throws IllegalAccessException {
        DI.infectDependencies(this);
    }
    @Override
    public void execute() {
        int roomNumber = Integer.parseInt(consoleView.getInput("Inter room number , you want to check out guest:\n"));
        Room room = roomManager.getRooms().get(roomNumber);
        if (room.getGuestName() != null) {
            room.setOccupied(false);
            room.setGuestName(null);
            room.setGuestSurname(null);
            room.setPassportNumber(null);
            room.setDateOfOccupation(null);
            room.setDateOfEviction(null);
            consoleView.SuccessfulNotification();
        }
    }
}
