package org.example.Action;

import annotation.OwnInject;
import com.opencsv.exceptions.CsvValidationException;
import dao.model.service.ServiceTypeService;
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
    @Override
    public void execute() throws ParseException, IOException, CsvValidationException, SQLException {
        serviceTypeService.addServiceType();
    }
}