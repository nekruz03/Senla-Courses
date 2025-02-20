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
    private ServiceTypeService serviceTypeService;
    @OwnInject
    private RoomDao roomDao;
    @OwnInject
    private Connection connection;

    public int addRoom() throws SQLException {
        if (connection == null) {
            throw new SQLException("Error: No connection to the database.");
        }
        try {
            connection.setAutoCommit(false);
            Room room = roomDao.getRoomFromConsole();
            Room checkRoom = roomDao.findById(room.getRoomNumber());
            if (checkRoom != null) {throw new SQLException("Error: Room already exists.");}
            roomDao.save(room);
            connection.commit();
            return room.getRoomNumber();
        } catch (SQLException | RuntimeException e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
        return 0;
    }

    public int updateRoom() throws SQLException {
        if (connection == null) {
            throw new SQLException("Error: No connection to the database.");
        }
        try {
            connection.setAutoCommit(false);
            Room room = roomDao.getRoomFromConsole();
            Room existingRoom = roomDao.findById(room.getRoomNumber());
            if (existingRoom == null) {
                throw new SQLException("Room with ID " + room.getRoomNumber() + " not found");
            }

            if (room.getRoomStatus() != null) {
                existingRoom.setRoomStatus(room.getRoomStatus());
            }

            if (room.getRoomType() != null) {
                existingRoom.setRoomType(room.getRoomType());
            }
            if (room.getCapacity() != null) {
                existingRoom.setCapacity(room.getCapacity());
            }

            if (room.getNumberOfStars() != null) {
                existingRoom.setNumberOfStars(room.getNumberOfStars());
            }

            if (room.getPrice() != null) {
                existingRoom.setPrice(room.getPrice());
            }

            roomDao.update(existingRoom);
            connection.commit();

            return existingRoom.getRoomNumber();
        } catch (SQLException | RuntimeException e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
        return 0;
    }


    public void findByid(int id) throws SQLException {
        if (connection == null) {
            throw new SQLException("Error: No connection to the database.");
        }
        Room room = roomDao.findById(id);
        if (room == null) {
            throw new SQLException("Room with ID " + id + " not found");
        }
        consoleView.print(room.toString());
    }

    public void getAllRooms() throws SQLException {
        if (connection == null) {
            throw new SQLException("Error: No connection to the database.");
        }
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
           e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void displayRoomsSortedByNumber() throws SQLException {
        if (connection == null) {
            throw new SQLException("Error: No connection to the database.");
        }
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
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void displayRoomsSortedByPrice() throws SQLException {
        if (connection == null) {
            throw new SQLException("Error: No connection to the database.");
        }
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
            e.printStackTrace();
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
            Room checkRoom = roomDao.findById(room.getRoomNumber());
            if (checkRoom == null) {throw new SQLException("Error: Room with ID " + room.getRoomNumber() + " not found" );}

            roomDao.delete(room);
            connection.commit();
        } catch (SQLException | RuntimeException e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void addingAdditionalServices() throws SQLException {
        if (connection == null) {
            throw new SQLException("Error: No connection to the database.");
        }

        boolean previousAutoCommit = connection.getAutoCommit();

        try {
            if (previousAutoCommit) {
                connection.setAutoCommit(false);
            }

            serviceTypeService.getAllServiceTypes();
            roomDao.addServicesInToRoom();

            connection.commit();
        } catch (SQLException | RuntimeException e) {
            if (!connection.getAutoCommit()) {
                connection.rollback();
            }
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(previousAutoCommit);
        }
    }





    public void calculate() throws SQLException {
        if (connection == null) {
            throw new SQLException("Error: No connection to the database.");
        }
        try {
            connection.setAutoCommit(false);
            double cost = roomDao.calculate();
            consoleView.display("Total cost: $" + cost);
            connection.commit();
        } catch (SQLException | RuntimeException e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void numberOfGuestsInRoom() throws SQLException {
        if (connection == null) {
            throw new SQLException("Error: No connection to the database.");
        }
        try {
            connection.setAutoCommit(false);
            int kol = roomDao.numberOfGuestsInRoom();
            connection.commit();
            consoleView.display("Number of guests in room: " + kol);
        } catch (SQLException | RuntimeException e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
