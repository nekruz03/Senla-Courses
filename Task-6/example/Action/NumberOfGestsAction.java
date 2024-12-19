package org.example.Action;

import model.Room;
import model.RoomManager;
import util.IAction;
import viev.ConsoleView;

import java.util.List;

public class NumberOfGestsAction implements IAction {
    private ConsoleView consoleView = new ConsoleView();
    RoomManager roomManager;
    public NumberOfGestsAction(RoomManager roomManager) {
        this.roomManager=roomManager;
    }
    @Override
    public void execute()   {
        List<Room> rooms = roomManager.getAllRooms();
        long col = rooms.stream()
                .filter(x -> x.getGuestName() != null)
                .count();
        consoleView.display(col);
    }
}
