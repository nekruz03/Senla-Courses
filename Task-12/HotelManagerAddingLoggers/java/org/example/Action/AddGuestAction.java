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


public class AddGuestAction implements IAction {
    private  static final Logger logger = LoggerFactory.getLogger(AddGuestAction.class);
    @OwnInject
    private ConsoleView consoleView;
    @OwnInject
   private GuestService guestService;
    @Override
    public void execute() throws ParseException, IOException, CsvValidationException, SQLException {
    logger.info(" Executing AddGuestAction");
    int id = guestService.addGuest();
    logger.info("Executed AddGuestAction");
    }
}
