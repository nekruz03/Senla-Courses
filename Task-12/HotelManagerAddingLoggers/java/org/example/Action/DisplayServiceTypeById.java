package org.example.Action;

import annotation.OwnInject;
import com.opencsv.exceptions.CsvValidationException;
import dao.model.service.ServiceTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.IAction;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class DisplayServiceTypeById implements IAction {
    private  static final Logger logger = LoggerFactory.getLogger(DisplayServiceTypeById.class);
    @OwnInject
    ServiceTypeService serviceType_Service;
    @Override
    public void execute() throws ParseException, IOException, CsvValidationException, SQLException {
        logger.info("Executing DisplayServiceTypeById");
        serviceType_Service.getServiceTypeById();
        logger.info("Exiting DisplayServiceTypeById");
    }
}
