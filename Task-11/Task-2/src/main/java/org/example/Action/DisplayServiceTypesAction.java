package org.example.Action;

import annotation.OwnInject;
import dao.model.service.ServiceType_Service;
import util.IAction;
import viev.ConsoleView;

import java.text.ParseException;

public class DisplayServiceTypesAction implements IAction {
    @OwnInject
    private ConsoleView consoleView;
    @OwnInject
    ServiceType_Service serviceType_Service;
    @Override
    public void execute() throws ParseException {
        serviceType_Service.getAllServiceTypes();
    }
}
