package org.example.Action;

import annotation.OwnInject;
import com.opencsv.exceptions.CsvValidationException;
import dao.model.service.Guest_Service;
import util.IAction;
import viev.ConsoleView;

import java.io.Console;
import java.io.IOException;
import java.text.ParseException;

public class DisplayGuestById implements IAction {
    @OwnInject
    Guest_Service guest_Service;
    @OwnInject
    ConsoleView consoleView;
    @Override
    public void execute() throws ParseException, IOException, CsvValidationException {
        String guestInput = consoleView.getInput("Enter Guest id: ");
        int guestId = Integer.parseInt(guestInput);
        guest_Service.find_by_id(guestId);
    }
}
