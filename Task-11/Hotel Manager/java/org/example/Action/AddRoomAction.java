package org.example.Action;
import annotation.OwnInject;
import dao.model.service.RoomService;
import util.IAction;
import viev.ConsoleView;

import java.sql.SQLException;

public class AddRoomAction implements IAction {
    @OwnInject
    private ConsoleView consoleView;
    @OwnInject
    private RoomService roomService;
    int roomNumber ;
    public void execute() throws SQLException {
        roomNumber = roomService.addRoom();
        if (roomNumber >= 1 ) System.out.println("room: " + roomNumber + " successfully added!");
        else System.out.println("room: " + roomNumber + " failed to add!");
    }
}