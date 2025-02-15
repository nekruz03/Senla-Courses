package org.example.Action;

import annotation.OwnInject;
import com.opencsv.exceptions.CsvValidationException;
import dao.model.service.Room_Service;
import util.IAction;

import java.io.IOException;
import java.text.ParseException;

public class DisplayAllRoomsAction implements IAction {
    @OwnInject
    Room_Service roomService;
    @Override
    public void execute() throws ParseException, IOException, CsvValidationException {
        roomService.getAllRooms();
    }
}
