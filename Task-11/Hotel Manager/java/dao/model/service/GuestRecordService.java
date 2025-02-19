package dao.model.service;

import annotation.OwnInject;
import dao.model.Dao.GuestRecordDao;
import dao.model.Dao.RoomDao;
import dao.model.entity.GuestRecord;
import dao.model.entity.Room;
import viev.ConsoleView;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class GuestRecordService {

    @OwnInject
    GuestService guest_Service;

    @OwnInject
    GuestRecordDao guest_RecordDao;

    @OwnInject
    RoomDao roomDao;

    @OwnInject
    ConsoleView consoleView;

    @OwnInject
    Connection connection;
    public void checkIn() throws ParseException, SQLException {
        if (connection == null) {
            throw new SQLException("Error: No connection to the database.");
        }
        connection.setAutoCommit(false);
        try {
            int id = guest_Service.addGuest();
            GuestRecord guestRecord = guest_RecordDao.getGuestRecordFromConsole(id);
            guest_RecordDao.save(guestRecord);
            connection.commit();
        } catch (SQLException | RuntimeException e) {
            connection.rollback();
            throw new SQLException("Error checking in guest", e);
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public int checkOut() throws SQLException, ParseException {
        if (connection == null) {
            throw new SQLException("Error: No connection to the database.");
        }
        try {
            connection.setAutoCommit(false);
            Room room = roomDao.getRoomFromConsole();

            Room roomCheck = roomDao.findById(room.getRoomNumber());
            if (roomCheck == null ) throw new SQLException("Error: Room not found.");
            guest_RecordDao.deleteGuestRecord(room);
            connection.commit();
            return room.getRoomNumber();
        } catch (SQLException | RuntimeException e) {
            connection.rollback();
            throw new SQLException("Error checking out guest", e);
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void updateGuestRecord() throws SQLException, ParseException {
        if (connection == null) {
            throw new SQLException("Error: No connection to the database.");
        }
        try {
            connection.setAutoCommit(false);
            String guestInput = consoleView.getInput("Enter guest record id: ");
            int guestId = Integer.parseInt(guestInput);
            GuestRecord recordCheck = guest_RecordDao.findById(guestId);
            if (recordCheck == null) throw new SQLException("Error: Record not found.");
            GuestRecord guestRecord = guest_RecordDao.getGuestRecordFromConsole(guestId);
            guest_RecordDao.update(guestRecord);
            connection.commit();
        } catch (SQLException | RuntimeException e) {
            connection.rollback();
            throw new SQLException("Error updating guest record", e);
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void findGuestRecordById() throws SQLException, ParseException {
        if (connection == null) {
            throw new SQLException("Error: No connection to the database.");
        }
        String guestInput = consoleView.getInput("Enter guest record id: ");
        int guestRecordId = Integer.parseInt(guestInput);
        GuestRecord guestRecord = guest_RecordDao.findById(guestRecordId);
        if (guestRecord == null) {
            throw new SQLException("Guest record not found");
        } else {
            consoleView.print(guestRecord.toString());
        }
    }

    public void getALLGuestRecord() throws SQLException {
        if (connection == null) {
            throw new SQLException("Error: No connection to the database.");
        }
        try {
            connection.setAutoCommit(false);
            List<GuestRecord> guestRecords = guest_RecordDao.findAll();
            if (guestRecords.isEmpty()) {
                throw new RuntimeException("No guest records found");
            } else {
                for (GuestRecord guestRecord : guestRecords) {
                    consoleView.print(guestRecord.toString());
                }
            }
            connection.commit();
        } catch (SQLException | RuntimeException e) {
            connection.rollback();
            throw new SQLException("Error retrieving guest records", e);
        } finally {
            connection.setAutoCommit(true);
        }
    }


}
