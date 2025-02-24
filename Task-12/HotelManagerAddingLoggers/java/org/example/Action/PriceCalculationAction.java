package org.example.Action;

import annotation.OwnInject;
import dao.model.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.IAction;
import viev.ConsoleView;

import java.sql.*;

public class PriceCalculationAction implements IAction {
    private  static final Logger logger = LoggerFactory.getLogger(PriceCalculationAction.class);
   @OwnInject
   RoomService roomService;
    @Override
    public void execute() throws SQLException {
        logger.info("Executing PriceCalculationAction");
        roomService.calculate();
        logger.info("Exiting PriceCalculationAction");
    }
}
