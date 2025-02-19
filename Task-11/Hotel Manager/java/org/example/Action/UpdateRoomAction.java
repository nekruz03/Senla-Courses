package org.example.Action;

import annotation.OwnInject;
import com.opencsv.exceptions.CsvValidationException;
import dao.model.service.RoomService;
import util.IAction;
import viev.ConsoleView;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class UpdateRoomAction implements IAction {
    @OwnInject
    RoomService roomService;
    @OwnInject
    ConsoleView consoleView;
    @Override
    public void execute() throws ParseException, IOException, CsvValidationException, SQLException {
        int  number = roomService.updateRoom();
        System.out.println("room: " + number + " updated!");
    }
}
