package dao.model.service;

import annotation.OwnInject;
import dao.model.Dao.RoomDao;
import dao.model.entity.Room;
import viev.ConsoleView;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RoomService {
    @OwnInject
    private ConsoleView consoleView;
    @OwnInject
    private ServiceTypeService serviceType_Service;
    @OwnInject
    private RoomDao roomDao;
    @OwnInject
    private Connection connection;

    public int addRoom() throws SQLException {
        if (connection == null) {
            throw new SQLException("Error: No connection to the database.");
        }
        try {
            connection.setAutoCommit(false); // Start transaction
            Room room = roomDao.getRoomFromConsole();
            roomDao.save(room);
            connection.commit(); // End transaction
            return room.getRoomNumber();
        } catch (SQLException | RuntimeException e) {
            connection.rollback();
            throw new SQLException("Error adding room", e);
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public int updateRoom() throws SQLException {
        if (connection == null) {
            throw new SQLException("Error: No connection to the database.");
        }
        try {
            connection.setAutoCommit(false);
            Room room = roomDao.getRoomFromConsole();
            roomDao.update(room);
            connection.commit();
            return room.getRoomNumber();
        } catch (SQLException | RuntimeException e) {
            connection.rollback();
            throw new SQLException("Error updating room", e);
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void find_by_id(int id) throws SQLException {
        Room room = roomDao.findById(id);
        if (room == null) {
            throw new SQLException("Room with ID " + id + " not found");
        }
        consoleView.print(room.toString());
    }

    public void getAllRooms() throws SQLException {
        try {
            connection.setAutoCommit(false);
            List<Room> rooms = roomDao.findAll();
            if (rooms.isEmpty()) {
                throw new SQLException("Room list is empty");
            }
            for (Room room : rooms) {
                System.out.println(room);
            }
            connection.commit();
        } catch (SQLException | RuntimeException e) {
            connection.rollback();
            throw new SQLException("Error retrieving list of rooms", e);
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void displayRoomsSortedByNumber() throws SQLException {
        try {
            connection.setAutoCommit(false);
            List<Room> rooms = roomDao.getRoomsSortedByNumber();
            if (rooms.isEmpty()) {
                throw new SQLException("No available rooms to sort by number");
            }
            for (Room room : rooms) {
                System.out.println(room);
            }
            connection.commit();
        } catch (SQLException | RuntimeException e) {
            connection.rollback();
            throw new SQLException("Error sorting rooms by number", e);
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void displayRoomsSortedByPrice() throws SQLException {
        try {
            connection.setAutoCommit(false);
            List<Room> rooms = roomDao.getRoomsSortedByPrice();
            if (rooms.isEmpty()) {
                throw new SQLException("No available rooms to sort by price");
            }
            for (Room room : rooms) {
                System.out.println(room);
            }
            connection.commit();
        } catch (SQLException | RuntimeException e) {
            connection.rollback();
            throw new SQLException("Error sorting rooms by price", e);
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void deleteRoom() throws SQLException {
        if (connection == null) {
            throw new SQLException("Error: No connection to the database.");
        }
        try {
            connection.setAutoCommit(false);
            Room room = roomDao.getRoomFromConsole();
            roomDao.delete(room);
            connection.commit();
        } catch (SQLException | RuntimeException e) {
            connection.rollback();
            throw new SQLException("Error deleting room", e);
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void addingAdditionalServices() throws SQLException {
        try {
            connection.setAutoCommit(false);
            serviceType_Service.getAllServiceTypes();
            roomDao.addServicesInToRoom();
            connection.commit();
        } catch (SQLException | RuntimeException e) {
            connection.rollback();
            throw new SQLException("Error adding additional services", e);
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void calculate() throws SQLException {
        try {
            connection.setAutoCommit(false);
            double cost = roomDao.calculate();
            consoleView.display("Total cost: $" + cost);
            connection.commit();
        } catch (SQLException | RuntimeException e) {
            connection.rollback();
            throw new SQLException("Error calculating total cost", e);
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void numberOfGuestsInRoom() throws SQLException {
        try {
            connection.setAutoCommit(false);
            roomDao.numberOfGuestsInRoom();
            connection.commit();
        } catch (SQLException | RuntimeException e) {
            connection.rollback();
            throw new SQLException("Error counting the number of guests in the room", e);
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
