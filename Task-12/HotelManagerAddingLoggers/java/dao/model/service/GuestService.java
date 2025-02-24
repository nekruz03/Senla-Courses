package dao.model.service;

import annotation.OwnInject;
import dao.model.Dao.GuestDao;
import dao.model.entity.Guest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import viev.ConsoleView;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class GuestService {

    @OwnInject
    ConsoleView consoleView;

    @OwnInject
    GuestDao guestDao;

    @OwnInject
    Connection connection;
    private  static final Logger logger = LoggerFactory.getLogger(GuestService.class);
    public int addGuest() throws SQLException {
        logger.info("Executing addGuest");
        if (connection == null) {
            logger.error("connection is null");
            throw new SQLException("Error: No connection to the database.");
        }
        boolean previousAutoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        try {
            Guest guest = guestDao.getGuestFromConsole();
            if (guestDao.guestExists(guest.getPassportNumber())) {
                logger.warn("Guest already exists");
                throw new SQLException("Guest with this passport number already exists.");
            }
            int guestId = guestDao.save(guest);
            connection.commit();
            logger.info("Guest with id " + guestId + " added successfully.");
            return guestId;
        } catch (SQLException | RuntimeException e) {
            logger.error("Error adding guest", e);
            if (!connection.getAutoCommit()) {
                connection.rollback();
            }
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(previousAutoCommit);
        }
        logger.info("Exiting addGuest");
        return 0;
    }


    public int updateGuest() throws SQLException {
        logger.info("Executing updateGuest");
        if (connection == null) {
            logger.error("connection is null");
            throw new SQLException("Error: No connection to the database.");
        }
        try {
            connection.setAutoCommit(false);
            String guestInput = consoleView.getInput("Enter Guest id: ");
            int guestId = Integer.parseInt(guestInput);
            Guest guest = guestDao.getGuestFromConsole();

            Guest checkGuest = guestDao.findById(guestId);
            if ( checkGuest== null) {
                logger.warn("Guest with id " + guestId + " not found.");
                throw new SQLException("Guest not found");}
            guest.setId(guestId);
            guestDao.update(guest);
            connection.commit();
            return guest.getId();
        } catch (SQLException | RuntimeException e) {
            logger.error("Error updating guest", e);
            connection.rollback();
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
        logger.info("Exiting updateGuest");
        return 0;
    }

    public void findById(int id) throws SQLException {
        logger.info("Executing findById");
        if (connection == null) {
            logger.error("connection is null");
            throw new SQLException("Error: No connection to the database.");
        }
        Guest guest = guestDao.findById(id);
        if (guest == null) {
            logger.warn("Guest with id " + id + " not found.");
            throw new SQLException("Guest not found");
        } else {
            logger.info("Guest with id " + id + " found.");
            consoleView.print(guest.toString());
        }
    }
    public void getAllGuests() throws SQLException {
        logger.info("Executing getAllGuests");
        if (connection == null) {
            logger.error("connection is null");
            throw new SQLException("Error: No connection to the database.");
        }
        try {
            connection.setAutoCommit(false);
            List<Guest> guests = guestDao.findAll();
            if (guests.isEmpty()) {
                logger.warn("Guest list is empty");
                throw new SQLException("No guests found");
            } else {
                for (Guest guest : guests) {
                    consoleView.print(guest.toString());
                }
                logger.info("Guest list size: " + guests.size());
            }
            connection.commit();
        } catch (SQLException | RuntimeException e) {
            logger.error("Error finding guests", e);
            connection.rollback();
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
        logger.info("Exiting getAllGuests");
    }

    public void deleteGuest() throws SQLException {
        logger.info("Executing deleteGuest");
        if (connection == null) {
            logger.error("connection is null");
            throw new SQLException("Error: No connection to the database.");
        }
        try {
            connection.setAutoCommit(false);
            String guestInput = consoleView.getInput("Enter Guest id: ");
            int guestId = Integer.parseInt(guestInput);
            Guest guest = guestDao.findById(guestId);
            if ( guest == null) {
                logger.warn("Guest with id " + guestId + " not found.");
                throw new SQLException("Guest not found");
            } else {
                logger.info("Successfully deleted guest with id " + guestId + " from the database.");
                guestDao.delete(guest);
            }
            connection.commit();
        } catch (SQLException | RuntimeException e) {
            logger.error("Error deleting guest", e);
            connection.rollback();
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
        logger.info("Exiting deleteGuest");
    }
}
