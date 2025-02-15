package org.example.Action;

import annotation.OwnInject;
import com.opencsv.exceptions.CsvValidationException;
import dao.model.Dao.ServiceType_Dao;
import dao.model.service.ServiceType_Service;
import util.IAction;

import java.io.IOException;
import java.text.ParseException;

public class DeleteServiceTypeByIdAction implements IAction {
    @OwnInject
    ServiceType_Service serviceTypeService;
    @Override
    public void execute() throws ParseException, IOException, CsvValidationException {
        serviceTypeService.deleteServiceTypeById();
    }
}
