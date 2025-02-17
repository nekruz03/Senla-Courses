package org.example.Action;

import annotation.OwnInject;
import com.opencsv.exceptions.CsvValidationException;
import dao.model.service.Guest_Record_Service;
import util.IAction;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class ExportGuestRecordAction implements IAction {
    @OwnInject
    Guest_Record_Service guest_Record_Service;
    @Override
    public void execute() throws ParseException, IOException, CsvValidationException, SQLException {
        guest_Record_Service.exportGuestRecord();
    }
}
