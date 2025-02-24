package org.example.Action;

import annotation.OwnInject;
import com.opencsv.exceptions.CsvValidationException;
import dao.model.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.IAction;
import viev.ConsoleView;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class UpdateRoomAction implements IAction {
    private  static final Logger logger = LoggerFactory.getLogger(UpdateRoomAction.class);
    @OwnInject
    RoomService roomService;
    @OwnInject
    ConsoleView consoleView;
    @Override
    public void execute() throws ParseException, IOException, CsvValidationException, SQLException {
        logger.info("Executing UpdateRoomAction");
        int  number = roomService.updateRoom();
        System.out.println("room: " + number + " updated!");
        logger.info("Exiting UpdateRoomAction");
    }
}
