package org.example.Action;

import annotation.OwnInject;
import com.opencsv.exceptions.CsvValidationException;
import dao.model.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.IAction;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class NumberOfGuestsAction implements IAction {
    private  static final Logger logger = LoggerFactory.getLogger(NumberOfGuestsAction.class);
    @OwnInject
    RoomService roomService;
    @Override
    public void execute() throws ParseException, IOException, CsvValidationException, SQLException {
        logger.info("Executing NumberOfGuestsAction");
        roomService.numberOfGuestsInRoom();
        logger.info("Exiting NumberOfGuestsAction");
    }
}
