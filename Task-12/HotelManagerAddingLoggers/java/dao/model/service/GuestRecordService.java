package dao.model.service;

import annotation.OwnInject;
import dao.model.Dao.GuestRecordDao;
import dao.model.Dao.RoomDao;
import dao.model.Dao.ServiceTypeDao;
import dao.model.entity.GuestRecord;
import dao.model.entity.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import viev.ConsoleView;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class GuestRecordService {
    private  static final Logger logger = LoggerFactory.getLogger(GuestRecordService.class);
    @OwnInject
    GuestService guestService;

    @OwnInject
    GuestRecordDao guestRecordDao;

    @OwnInject
    RoomDao roomDao;

    @OwnInject
    ConsoleView consoleView;

    @OwnInject
    Connection connection;
    public void checkIn() throws ParseException, SQLException {
        logger.info("Entering checkIn method");
        if (connection == null) {
            logger.error("Connection is null");
            throw new SQLException("Error: No connection to the database.");
        }
        connection.setAutoCommit(false);
        try {
            int id = guestService.addGuest();
            GuestRecord guestRecord = guestRecordDao.getGuestRecordFromConsole(id);
            guestRecordDao.save(guestRecord);
            connection.commit();
            logger.info("Guest with ID {} checked in successfully", id);
        } catch (SQLException | RuntimeException e) {
            connection.rollback();
            logger.error("Error while checking in", e);
           e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
            logger.info("Exiting checkIn method");
        }
    }

    public int checkOut() throws SQLException, ParseException {
        logger.info("Entering checkOut method");
        if (connection == null) {
            logger.error("Connection is null");
            throw new SQLException("Error: No connection to the database.");
        }
        try {
            connection.setAutoCommit(false);
            Room room = roomDao.getRoomFromConsole();
            Room roomCheck = roomDao.findById(room.getRoomNumber());
            if (roomCheck == null ) {
                logger.warn("Room with ID {} not found", room.getRoomNumber());
                throw new SQLException("Error: Room not found.");
            }
            guestRecordDao.deleteGuestRecord(room);
            connection.commit();
            logger.info("Guest with ID {} checked out successfully", room.getRoomNumber());
            return room.getRoomNumber();
        } catch (SQLException | RuntimeException e) {
            connection.rollback();
            logger.error("Error while checking out", e);
          e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
            logger.info("Exiting checkOut method");
        }
        return 0;
    }

    public void updateGuestRecord() throws SQLException, ParseException {
        logger.info("Entering updateGuestRecord method");
        if (connection == null) {
            logger.error("Connection is null");
            throw new SQLException("Error: No connection to the database.");
        }
        try {
            connection.setAutoCommit(false);
            String guestInput = consoleView.getInput("Enter guest record id: ");
            int guestId = Integer.parseInt(guestInput);
            GuestRecord recordCheck = guestRecordDao.findById(guestId);
            if (recordCheck == null) {
                logger.warn("Guest with ID {} not found", guestId);
                throw new SQLException("Error: Record not found.");
            }
            GuestRecord guestRecord = guestRecordDao.getGuestRecordFromConsole(guestId);
            guestRecordDao.update(guestRecord);
            connection.commit();
            logger.info("Guest record with ID {} updated successfully", guestId);
        } catch (SQLException | RuntimeException e) {
            connection.rollback();
            logger.error("Error during guest record update", e);
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
            logger.info("Exiting updateGuestRecord method");
        }
    }
    public void findGuestRecordById() throws SQLException, ParseException {
        logger.info("Entering findGuestRecordById method");
        if (connection == null) {
            logger.error("Connection is null");
            throw new SQLException("Error: No connection to the database.");
        }
        String guestInput = consoleView.getInput("Enter guest record id: ");
        int guestRecordId = Integer.parseInt(guestInput);
        GuestRecord guestRecord = guestRecordDao.findById(guestRecordId);
        if (guestRecord == null) {
            logger.warn("Guest with ID {} not found", guestRecordId);
            throw new SQLException("Guest record not found");
        } else {
            logger.info("Guest record with ID {} found", guestRecordId);
            consoleView.print(guestRecord.toString());
        }
    }
    public void getALLGuestRecord() throws SQLException {
        logger.info("Entering getALLGuestRecord method");
        if (connection == null) {
            logger.error("Connection is null");
            throw new SQLException("Error: No connection to the database.");
        }
        try {
            connection.setAutoCommit(false);
            List<GuestRecord> guestRecords = guestRecordDao.findAll();
            if (guestRecords.isEmpty()) {
                logger.warn("Guests not found in database");
                throw new RuntimeException("No guest records found");
            } else {
                for (GuestRecord guestRecord : guestRecords) {
                    consoleView.print(guestRecord.toString());
                }
            }
            logger.info("Successfully getALLGuestRecord method");
            connection.commit();
        } catch (SQLException | RuntimeException e) {
            connection.rollback();
           e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
            logger.info("Exiting getALLGuestRecord method");
        }
    }


}
