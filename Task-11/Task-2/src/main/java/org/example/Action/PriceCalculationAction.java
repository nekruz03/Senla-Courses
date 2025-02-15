package org.example.Action;

import annotation.OwnInject;
import dao.model.connection.ConnectionManager;
import dao.model.entity.RoomService;
import dao.model.service.Room_Service;
import util.IAction;
import viev.ConsoleView;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PriceCalculationAction implements IAction {
    @OwnInject
    private ConsoleView consoleView;
   @OwnInject
   Room_Service roomService;
    @Override
    public void execute() {
        roomService.calculate();
    }
}
