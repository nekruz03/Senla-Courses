package org.example.Action;

import annotation.OwnInject;
import com.opencsv.exceptions.CsvValidationException;
import dao.model.service.GuestRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.IAction;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class CheckInGuestAction implements IAction {
    @OwnInject
    GuestRecordService guest_Record_Service;
    private  static final Logger logger = LoggerFactory.getLogger(CheckInGuestAction.class);
    @Override
    public void execute() throws ParseException, IOException, CsvValidationException, SQLException {
        logger.info("Executing CheckInGuestAction");
        guest_Record_Service.checkIn();
        logger.info("Exiting CheckInGuestAction");
    }
}
