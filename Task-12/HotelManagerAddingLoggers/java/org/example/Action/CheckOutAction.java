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

public class CheckOutAction implements IAction {

    @OwnInject
    GuestRecordService guest_Record_Service;
    private  static final Logger logger = LoggerFactory.getLogger(CheckOutAction.class);
    @Override
    public void execute() throws ParseException, IOException, CsvValidationException, SQLException {
        logger.info("Executing CheckOutAction");
        guest_Record_Service.checkOut();
        logger.info("Exiting CheckOutAction");
    }
}
