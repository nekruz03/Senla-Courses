package org.example.Action;

import annotation.OwnInject;
import dao.model.service.Room_Service;
import util.IAction;
public class DeleteRoomAction implements IAction {
    @OwnInject
    Room_Service roomService;
    @Override
    public void execute() {
      roomService.deleteRoom();
    }
}
