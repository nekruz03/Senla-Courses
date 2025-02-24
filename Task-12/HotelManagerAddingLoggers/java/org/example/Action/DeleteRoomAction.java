package org.example.Action;

import annotation.OwnInject;
import dao.model.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.IAction;

import java.sql.SQLException;

public class DeleteRoomAction implements IAction {
    @OwnInject
    RoomService roomService;
    private  static final Logger logger = LoggerFactory.getLogger(DeleteRoomAction.class);
    @Override
    public void execute() throws SQLException {
        logger.info("Executing DeleteRoomAction");
      roomService.deleteRoom();
      logger.info("Exiting DeleteRoomAction");
    }
}
