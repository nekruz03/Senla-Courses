package org.example.Action;

import annotation.OwnInject;
import com.opencsv.exceptions.CsvValidationException;
import dao.model.service.Room_Service;
import util.IAction;
import viev.ConsoleView;

import java.io.IOException;
import java.text.ParseException;

public class DisplayRoomByNumberAction implements IAction {
    @OwnInject
    Room_Service roomService;
    @OwnInject
    ConsoleView consoleView;
    @Override
    public void execute() throws ParseException, IOException, CsvValidationException {
        String roomNumberInput = consoleView.getInput("Enter room number: ");
        int roomNumber = Integer.parseInt(roomNumberInput);
        roomService.find_by_id(roomNumber);
    }
}
