package org.example.Action;

import annotation.OwnInject;
import com.opencsv.exceptions.CsvValidationException;
import dao.model.service.GuestService;
import util.IAction;
import viev.ConsoleView;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class AddGuestAction implements IAction {
    @OwnInject
    ConsoleView consoleView;
    @OwnInject
    GuestService guestService;
    @Override
    public void execute() throws ParseException, IOException, CsvValidationException, SQLException {
    int id = guestService.addGuest();
        System.out.println("guest id is: " + id);
    consoleView.SuccessfulNotification();
    }
}
