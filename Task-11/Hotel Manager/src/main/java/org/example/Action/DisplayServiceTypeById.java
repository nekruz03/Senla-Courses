package org.example.Action;

import annotation.OwnInject;
import com.opencsv.exceptions.CsvValidationException;
import dao.model.service.ServiceTypeService;
import util.IAction;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class DisplayServiceTypeById implements IAction {
    @OwnInject
    ServiceTypeService serviceType_Service;
    @Override
    public void execute() throws ParseException, IOException, CsvValidationException, SQLException {
        serviceType_Service.getServiceTypeById();
    }
}
