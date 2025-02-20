package org.example.Action;

import annotation.OwnInject;
import dao.model.service.RoomService;
import util.IAction;

import java.sql.SQLException;

public class DeleteRoomAction implements IAction {
    @OwnInject
    RoomService roomService;
    @Override
    public void execute() throws SQLException {
      roomService.deleteRoom();
    }
}
