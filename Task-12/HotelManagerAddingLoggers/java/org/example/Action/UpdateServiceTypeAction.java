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

public class UpdateServiceTypeAction implements IAction {
    private  static final Logger logger = LoggerFactory.getLogger(UpdateServiceTypeAction.class);
    @OwnInject
    ServiceTypeService serviceTypeService;
    @Override
    public void execute() throws ParseException, IOException, CsvValidationException, SQLException {
        logger.info("Executing UpdateServiceTypeAction");
        serviceTypeService.updateServiceType();
        logger.info("Exiting UpdateServiceTypeAction");
    }
}
