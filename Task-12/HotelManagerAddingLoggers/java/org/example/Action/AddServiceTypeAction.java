package org.example.Action;

import annotation.OwnInject;
import com.opencsv.exceptions.CsvValidationException;
import dao.model.service.ServiceTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.IAction;
import viev.ConsoleView;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class AddServiceTypeAction implements IAction {
    @OwnInject
    ConsoleView consoleView;
    @OwnInject
    ServiceTypeService serviceTypeService;
    private  static final Logger logger = LoggerFactory.getLogger(AddServiceTypeAction.class);
    @Override
    public void execute() throws ParseException, IOException, CsvValidationException, SQLException {
        logger.info("Executing AddServiceTypeAction");
        serviceTypeService.addServiceType();
        logger.info("Exiting AddServiceTypeAction");
    }
}