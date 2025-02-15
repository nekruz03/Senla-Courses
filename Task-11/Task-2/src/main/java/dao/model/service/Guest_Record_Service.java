package dao.model.service;

import annotation.OwnInject;
import dao.model.Dao.Guest_RecordDao;
import dao.model.Dao.RoomDao;
import dao.model.entity.GuestRecord;
import dao.model.entity.Room;
import viev.ConsoleView;

import java.text.ParseException;
import java.util.List;

public class Guest_Record_Service {
    @OwnInject
   Guest_Service guest_Service;
    @OwnInject
    Guest_RecordDao guest_RecordDao;
    @OwnInject
    RoomDao roomDao;
    @OwnInject
    ConsoleView consoleView;
    public void checkIn() throws ParseException {
         int id = guest_Service.addGuest();
         GuestRecord guestRecord = guest_RecordDao.getGuestRecordFromConsole(id);
        guest_RecordDao.save(guestRecord);
    }
    public int checkOut() throws ParseException {
        Room room = roomDao.getRoomFromConsole();
        guest_RecordDao.deleteGuestRecord(room);
        return  room.getRoomNumber();
    }
    public void updateGuestRecord() throws ParseException {
        String guestInput = consoleView.getInput("Enter guest record id: ");
        int guestId = Integer.parseInt(guestInput);
        GuestRecord guestRecord = guest_RecordDao.getGuestRecordFromConsole(guestId);
        guest_RecordDao.update(guestRecord);
    }
    public  void  findGuestRecordById() throws ParseException {
        String guestInput = consoleView.getInput("Enter guest record id: ");
        int guestRecordId = Integer.parseInt(guestInput);
        guest_RecordDao.findById(guestRecordId);
    }
    public void getALLGuestRecord() throws ParseException {
        List<GuestRecord> guestRecords = guest_RecordDao.findAll();
        for (GuestRecord guestRecord : guestRecords) {
            System.out.println(guestRecord);
        }
    }
}
