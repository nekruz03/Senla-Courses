package org.example.Action;

import annotation.OwnInject;
import com.opencsv.exceptions.CsvValidationException;
import dao.model.service.Guest_Service;
import util.IAction;
import viev.ConsoleView;

import java.io.IOException;
import java.text.ParseException;

public class AddGuestAction implements IAction {
    @OwnInject
    ConsoleView consoleView;
    @OwnInject
    Guest_Service guestService;
    @Override
    public void execute() throws ParseException, IOException, CsvValidationException {
    int id = guestService.addGuest();
        System.out.println("guest id is: " + id);
    consoleView.SuccessfulNotification();
    }
}
