package org.example.Action;

import annotation.OwnInject;
import com.opencsv.exceptions.CsvValidationException;
import dao.model.connection.ConnectionManager;
import dao.model.service.ServiceType_Service;
import util.IAction;
import viev.ConsoleView;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;

public class AddServiceTypeAction implements IAction {
    @OwnInject
    ConsoleView consoleView;
    @OwnInject
    ServiceType_Service serviceTypeService;
    @Override
    public void execute() throws ParseException, IOException, CsvValidationException {
        serviceTypeService.addServiceType();
    }
}