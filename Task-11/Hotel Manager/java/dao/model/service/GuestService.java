package dao.model.service;

import annotation.OwnInject;
import dao.model.Dao.GuestDao;
import dao.model.entity.Guest;
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

    public int addGuest() throws SQLException {
        if (connection == null) {
            throw new SQLException("Error: No connection to the database.");
        }
        boolean previousAutoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);

        try {
            Guest guest = guestDao.getGuestFromConsole();

            if (guestDao.guestExists(guest.getPassport_number())) {
                throw new SQLException("Guest with this passport number already exists.");
            }

            int guestId = guestDao.save(guest);
            connection.commit();

            return guestId;
        } catch (SQLException | RuntimeException e) {
            if (!connection.getAutoCommit()) {
                connection.rollback();
            }
            throw new SQLException("Error adding guest", e);
        } finally {
            connection.setAutoCommit(previousAutoCommit);
        }
    }


    public int updateGuest() throws SQLException {
        if (connection == null) {
            throw new SQLException("Error: No connection to the database.");
        }
        try {
            connection.setAutoCommit(false);
            String guestInput = consoleView.getInput("Enter Guest id: ");
            int guestId = Integer.parseInt(guestInput);
            Guest guest = guestDao.getGuestFromConsole();

            Guest checkGuest = guestDao.findById(guestId);
            if ( checkGuest.getPassport_number() == null) {
                throw new SQLException("Guest not found");}

            guest.setId(guestId);
            guestDao.update(guest);
            connection.commit();
            return guest.getId();
        } catch (SQLException | RuntimeException e) {
            connection.rollback();
            throw new SQLException("Error updating guest", e);
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void findById(int id) throws SQLException {
        if (connection == null) {
            throw new SQLException("Error: No connection to the database.");
        }
        Guest guest = guestDao.findById(id);
        if (guest.getPassport_number() == null) {
            throw new SQLException("Guest not found");
        } else {
            consoleView.print(guest.toString());
        }
    }

    public void getAllGuests() throws SQLException {
        if (connection == null) {
            throw new SQLException("Error: No connection to the database.");
        }
        try {
            connection.setAutoCommit(false);
            List<Guest> guests = guestDao.findAll();
            if (guests.isEmpty()) {
                throw new SQLException("No guests found");
            } else {
                for (Guest guest : guests) {
                    consoleView.print(guest.toString());
                }
            }
            connection.commit();
        } catch (SQLException | RuntimeException e) {
            connection.rollback();
            throw new SQLException("Error retrieving guests", e);
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void deleteGuest() throws SQLException {
        if (connection == null) {
            throw new SQLException("Error: No connection to the database.");
        }
        try {
            connection.setAutoCommit(false);
            String guestInput = consoleView.getInput("Enter Guest id: ");

            int guestId = Integer.parseInt(guestInput);
            Guest guest = guestDao.findById(guestId);
            if ( guest.getGuest_name() == null) {
                throw new SQLException("Guest not found");
            } else {
                guestDao.delete(guest);
            }
            connection.commit();
        } catch (SQLException | RuntimeException e) {
            connection.rollback();
            throw new SQLException("Error deleting guest", e);
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
