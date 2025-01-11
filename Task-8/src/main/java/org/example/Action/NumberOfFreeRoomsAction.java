package org.example.Action;

import annotation.OwnInject;
import model.Room;
import model.RoomManager;
import util.DI.DI;
import util.IAction;
import viev.ConsoleView;

import java.util.List;

public class NumberOfFreeRoomsAction implements IAction {
    @OwnInject
    RoomManager roomManager;
    @OwnInject
    private ConsoleView consoleView ;
    public NumberOfFreeRoomsAction() throws IllegalAccessException {}
    @Override
    public void execute() {
        List<Room> rooms = roomManager.getAllRooms();
        long count = rooms.stream()
                .filter(room -> !room.isOccupied())
                .count();
        consoleView.display(count);
    }
}
