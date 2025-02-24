package org.example.Action;

import annotation.OwnInject;
import com.opencsv.exceptions.CsvValidationException;
import dao.model.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.IAction;
import viev.ConsoleView;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
public class AddingAdditionalServicesAction implements IAction {
    @OwnInject
    private ConsoleView consoleView;
    @OwnInject
    private RoomService roomService;
    private  static final Logger logger = LoggerFactory.getLogger(AddingAdditionalServicesAction.class);
    @Override
    public void execute() throws ParseException, IOException, CsvValidationException, SQLException {
        logger.info("Executing AddingAdditionalServicesAction");
      roomService.addingAdditionalServices();
      logger.info("Exiting AddingAdditionalServicesAction");
    }
}
