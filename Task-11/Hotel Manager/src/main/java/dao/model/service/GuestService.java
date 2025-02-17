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
        try {
            connection.setAutoCommit(false);
            Guest guest = guestDao.getGuestFromConsole();
            int guestId = guestDao.save(guest);
            connection.commit();
            return guestId;
        } catch (SQLException | RuntimeException e) {
            connection.rollback();
            throw new SQLException("Error adding guest", e);
        } finally {
            connection.setAutoCommit(true);
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
        Guest guest = guestDao.findById(id);
        if (guest == null || guest.getPassport_number() == null) {
            consoleView.print("Guest not found");
        } else {
            consoleView.print(guest.toString());
        }
    }

    public void getAllGuests() throws SQLException {
        try {
            connection.setAutoCommit(false);
            List<Guest> guests = guestDao.findAll();
            if (guests.isEmpty()) {
                consoleView.print("No guests found");
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
            if (guest == null || guest.getGuest_name() == null) {
                consoleView.print("Guest not found");
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
