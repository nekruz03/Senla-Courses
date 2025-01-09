package org.example.Action;

import annotation.OwnInject;
import model.Room;
import model.RoomManager;
import util.DI.DI;
import util.IAction;
import viev.ConsoleView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class GetListOfVailableRoomsInDateAction implements IAction {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    @OwnInject
    private ConsoleView consoleView;
    @OwnInject
    RoomManager roomManager;
    public GetListOfVailableRoomsInDateAction() throws IllegalAccessException {
        DI.infectDependencies(this);
    }
    @Override
    public void execute() throws ParseException {
        String input = consoleView.getInput("Enter  Date in the format dd.MM.yyyy:\n");
        Date date = dateFormat.parse(input);
        List<Room> rooms = roomManager.getAllRooms();
        List<Room> availableRooms = rooms.stream()
                .filter(room -> !room.isOccupied()
                        ? date.before(room.getDateOfOccupation()) || date.compareTo(room.getDateOfEviction()) >= 0
                        : true)
                .collect(Collectors.toList());

        consoleView.printList(availableRooms);
    }
}
