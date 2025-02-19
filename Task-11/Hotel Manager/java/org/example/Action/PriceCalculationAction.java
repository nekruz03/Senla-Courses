package org.example.Action;

import annotation.OwnInject;
import dao.model.service.RoomService;
import util.IAction;
import viev.ConsoleView;

import java.sql.*;

public class PriceCalculationAction implements IAction {
    @OwnInject
    private ConsoleView consoleView;
   @OwnInject
   RoomService roomService;
    @Override
    public void execute() throws SQLException {
        roomService.calculate();
    }
}
