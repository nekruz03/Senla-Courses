package org.example.Action;
import annotation.OwnInject;
import dao.model.entity.RoomService;
import dao.model.service.Room_Service;
import util.IAction;
import viev.ConsoleView;
public class AddRoomAction implements IAction {
    @OwnInject
    private ConsoleView consoleView;
    @OwnInject
    private Room_Service roomService;
    int roomNumber ;
    public void execute() {
        roomNumber = roomService.addRoom();
        if (roomNumber >= 1 ) System.out.println("room: " + roomNumber + " successfully added!");
        else System.out.println("room: " + roomNumber + " failed to add!");
    }
}