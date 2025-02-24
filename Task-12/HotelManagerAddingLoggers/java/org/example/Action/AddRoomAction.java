package org.example.Action;
import annotation.OwnInject;
import dao.model.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.IAction;
import viev.ConsoleView;

import java.sql.SQLException;

public class AddRoomAction implements IAction {
    @OwnInject
    private ConsoleView consoleView;
    @OwnInject
    private RoomService roomService;
    int roomNumber;
    private  static final Logger logger = LoggerFactory.getLogger(AddRoomAction.class);
    @Override
    public void execute() throws SQLException {
        logger.info("Executing AddRoomAction");
        roomNumber = roomService.addRoom();
        if (roomNumber >= 1 ) {
            System.out.println("room: " + roomNumber + " successfully added!");
        }
        else {
            System.out.println("room: " + roomNumber + " failed to add!");
        }
        logger.info("Exiting AddRoomAction");
    }
}