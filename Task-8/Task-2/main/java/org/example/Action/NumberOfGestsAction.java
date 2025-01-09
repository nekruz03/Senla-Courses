package org.example.Action;

import annotation.OwnInject;
import model.Room;
import model.RoomManager;
import util.DI.DI;
import util.IAction;
import viev.ConsoleView;

import java.util.List;

public class NumberOfGestsAction implements IAction {
    @OwnInject
    private ConsoleView consoleView ;
    @OwnInject
    RoomManager roomManager;
    public NumberOfGestsAction() throws IllegalAccessException {
        DI.infectDependencies(this);
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
