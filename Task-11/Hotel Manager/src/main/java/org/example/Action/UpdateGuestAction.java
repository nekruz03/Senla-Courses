package org.example.Action;

import annotation.OwnInject;
import com.opencsv.exceptions.CsvValidationException;
import dao.model.service.GuestService;
import util.IAction;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class UpdateGuestAction implements IAction {
    @OwnInject
    GuestService guest_Service;
    @Override
    public void execute() throws ParseException, IOException, CsvValidationException, SQLException {
        int id = guest_Service.updateGuest();
        System.out.println("guest is updated id = : " + id);
    }
}
