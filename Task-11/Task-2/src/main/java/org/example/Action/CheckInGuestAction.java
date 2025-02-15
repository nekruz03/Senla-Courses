package org.example.Action;

import annotation.OwnInject;
import com.opencsv.exceptions.CsvValidationException;
import dao.model.service.Guest_Record_Service;
import dao.model.service.Guest_Service;
import util.IAction;

import java.io.IOException;
import java.text.ParseException;

public class CheckInGuestAction implements IAction {
    @OwnInject
    Guest_Record_Service guest_Record_Service;
    @Override
    public void execute() throws ParseException, IOException, CsvValidationException {
        guest_Record_Service.checkIn();
    }
}
