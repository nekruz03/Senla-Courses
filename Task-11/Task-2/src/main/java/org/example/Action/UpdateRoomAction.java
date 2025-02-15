package org.example.Action;

import annotation.OwnInject;
import com.opencsv.exceptions.CsvValidationException;
import dao.model.entity.Room;
import dao.model.service.Room_Service;
import util.IAction;
import viev.ConsoleView;

import java.io.IOException;
import java.text.ParseException;

public class UpdateRoomAction implements IAction {
    @OwnInject
    Room_Service roomService;
    @OwnInject
    ConsoleView consoleView;
    @Override
    public void execute() throws ParseException, IOException, CsvValidationException {
        int  number = roomService.updateRoom();
        System.out.println("room: " + number + " updated!");
    }
}
