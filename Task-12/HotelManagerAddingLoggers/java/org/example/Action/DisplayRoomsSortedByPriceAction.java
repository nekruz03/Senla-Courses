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

public class DisplayRoomsSortedByPriceAction implements IAction {
    private  static final Logger logger = LoggerFactory.getLogger(DisplayRoomsSortedByPriceAction.class);
    @OwnInject
    RoomService roomService;
    @Override
    public void execute() throws ParseException, IOException, CsvValidationException, SQLException {
        logger.info("Executing DisplayRoomsSortedByPriceAction");
        roomService.displayRoomsSortedByPrice();
        logger.info("Exiting DisplayRoomsSortedByPriceAction");
    }
}
