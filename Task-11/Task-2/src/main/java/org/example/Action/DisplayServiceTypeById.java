package org.example.Action;

import annotation.OwnInject;
import com.opencsv.exceptions.CsvValidationException;
import dao.model.service.ServiceType_Service;
import util.IAction;

import java.io.IOException;
import java.text.ParseException;

public class DisplayServiceTypeById implements IAction {
    @OwnInject
    ServiceType_Service serviceType_Service;
    @Override
    public void execute() throws ParseException, IOException, CsvValidationException {
        serviceType_Service.getServiceTypeById();
    }
}
