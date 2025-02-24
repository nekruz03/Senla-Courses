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

public class UpdateGuestRecordAction implements IAction {
    private  static final Logger logger = LoggerFactory.getLogger(UpdateGuestRecordAction.class);
    @OwnInject
    GuestRecordService guestRecordService;
    @Override
    public void execute() throws ParseException, IOException, CsvValidationException, SQLException {
        logger.info("Executing UpdateGuestRecordAction");
        guestRecordService.updateGuestRecord();
        logger.info("Exiting UpdateGuestRecordAction");
    }
}
