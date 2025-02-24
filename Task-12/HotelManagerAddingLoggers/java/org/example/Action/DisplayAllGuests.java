package org.example.Action;

import annotation.OwnInject;
import com.opencsv.exceptions.CsvValidationException;
import dao.model.service.GuestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.IAction;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class DisplayAllGuests implements IAction {
    private  static final Logger logger = LoggerFactory.getLogger(DisplayAllGuests.class);
    @OwnInject
    GuestService guest_Service;
    @Override
    public void execute() throws ParseException, IOException, CsvValidationException, SQLException {
        logger.info("Executing DisplayAllGuests");
        guest_Service.getAllGuests();
        logger.info("Exiting DisplayAllGuests");
    }
}
