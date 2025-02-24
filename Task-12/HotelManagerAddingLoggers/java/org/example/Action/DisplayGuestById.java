package org.example.Action;

import annotation.OwnInject;
import com.opencsv.exceptions.CsvValidationException;
import dao.model.service.GuestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.IAction;
import viev.ConsoleView;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class DisplayGuestById implements IAction {
    private  static final Logger logger = LoggerFactory.getLogger(DisplayGuestById.class);
    @OwnInject
    GuestService guest_Service;
    @OwnInject
    ConsoleView consoleView;
    @Override
    public void execute() throws ParseException, IOException, CsvValidationException, SQLException {
        logger.info("Executing DisplayGuestById");
        String guestInput = consoleView.getInput("Enter Guest id: ");
        int guestId = Integer.parseInt(guestInput);
        guest_Service.findById(guestId);
        logger.info("Exiting DisplayGuestById");
    }
}
