package org.example.Action;

import annotation.OwnInject;
import com.opencsv.exceptions.CsvValidationException;
import dao.model.service.GuestRecordService;
import util.IAction;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class CheckOutAction implements IAction {
    @OwnInject
    GuestRecordService guest_Record_Service;
    @Override
    public void execute() throws ParseException, IOException, CsvValidationException, SQLException {
        guest_Record_Service.checkOut();
    }
}
