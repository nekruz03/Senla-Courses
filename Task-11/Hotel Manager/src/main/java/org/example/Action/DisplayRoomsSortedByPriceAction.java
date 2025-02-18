package org.example.Action;

import annotation.OwnInject;
import com.opencsv.exceptions.CsvValidationException;
import dao.model.service.RoomService;
import util.IAction;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class DisplayRoomsSortedByPriceAction implements IAction {
    @OwnInject
    RoomService roomService;
    @Override
    public void execute() throws ParseException, IOException, CsvValidationException, SQLException {
        roomService.displayRoomsSortedByPrice();
    }
}
