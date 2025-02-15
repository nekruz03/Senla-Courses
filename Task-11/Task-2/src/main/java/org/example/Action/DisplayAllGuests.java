package org.example.Action;

import annotation.OwnInject;
import com.opencsv.exceptions.CsvValidationException;
import dao.model.service.Guest_Service;
import util.IAction;

import java.io.IOException;
import java.text.ParseException;

public class DisplayAllGuests implements IAction {
    @OwnInject
    Guest_Service guest_Service;
    @Override
    public void execute() throws ParseException, IOException, CsvValidationException {
        guest_Service.getAllGuests();
    }
}
