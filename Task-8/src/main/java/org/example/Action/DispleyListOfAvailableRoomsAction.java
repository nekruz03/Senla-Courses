package org.example.Action;

import annotation.OwnInject;
import model.Room;
import model.RoomManager;
import util.DI.DI;
import util.IAction;
import viev.ConsoleView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DispleyListOfAvailableRoomsAction implements IAction {
    @OwnInject
    RoomManager roomManager;
    @OwnInject
    private ConsoleView consoleView ;
    public DispleyListOfAvailableRoomsAction( ) throws IllegalAccessException {}
    @Override
    public void execute() {
        int number =  Integer.parseInt(consoleView.getInput("Select the option to display, available numbers (1), " +
                "display available numbers - sorted by price (2), sorted by capacity(3), sorted by number of stars(4)\n"));
        List<Room> rooms = roomManager.getRooms().values().stream()
                .filter(room -> !room.isOccupied())
                .collect(Collectors.toList());
        List<Room> sortedRooms = new ArrayList<>(rooms);
        if (number == 1){consoleView.printList(rooms);}
        else if (number == 2){
            sortedRooms.sort(Comparator.comparingDouble(Room::getPrise));
            consoleView.printList(sortedRooms);
        }
        else if (number == 3){
            sortedRooms.sort(Comparator.comparingInt(Room::getCapasity));
            consoleView.printList(sortedRooms);
        }
        else if (number == 4){
            sortedRooms.sort(Comparator.comparingInt(Room::getNumberOfStars));
            consoleView.printList(sortedRooms);
        }
        consoleView.SuccessfulNotification();
    }
}
