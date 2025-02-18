package org.example.Action;

import annotation.OwnInject;
import dao.model.service.ServiceTypeService;
import util.IAction;
import viev.ConsoleView;

import java.sql.SQLException;
import java.text.ParseException;

public class DisplayServiceTypesAction implements IAction {
    @OwnInject
    private ConsoleView consoleView;
    @OwnInject
    ServiceTypeService serviceType_Service;
    @Override
    public void execute() throws ParseException, SQLException {
        serviceType_Service.getAllServiceTypes();
    }
}
