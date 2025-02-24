package org.example.Action;

import annotation.OwnInject;
import dao.model.service.ServiceTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.IAction;
import viev.ConsoleView;

import java.sql.SQLException;
import java.text.ParseException;

public class DisplayServiceTypesAction implements IAction {
    private  static final Logger logger = LoggerFactory.getLogger(DisplayServiceTypesAction.class);
    @OwnInject
    ServiceTypeService serviceType_Service;
    @Override
    public void execute() throws ParseException, SQLException {
        logger.info("Executing DisplayServiceTypesAction");
        serviceType_Service.getAllServiceTypes();
        logger.info("Exiting DisplayServiceTypesAction");
    }
}
