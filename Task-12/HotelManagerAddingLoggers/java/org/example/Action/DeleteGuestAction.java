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

public class DeleteGuestAction implements IAction {
    @OwnInject
    GuestService guest_Service;
    private  static final Logger logger = LoggerFactory.getLogger(DeleteGuestAction.class);
    @Override
    public void execute() throws ParseException, IOException, CsvValidationException, SQLException {
        logger.info("Executing DeleteGuestAction");
        guest_Service.deleteGuest();
        logger.info("Exiting DeleteGuestAction");
    }
}
