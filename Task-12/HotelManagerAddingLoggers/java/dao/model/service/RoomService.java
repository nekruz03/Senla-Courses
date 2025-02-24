package dao.model.service;

import annotation.OwnInject;
import dao.model.Dao.RoomDao;
import dao.model.entity.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private  static final Logger logger = LoggerFactory.getLogger(GuestRecordService.class);
    public int addRoom() throws SQLException {
        logger.info("Executing addRoom method");
        if (connection == null) {
            logger.error("connection is null");
            throw new SQLException("Error: No connection to the database.");
        }
        try {
            connection.setAutoCommit(false);
            Room room = roomDao.getRoomFromConsole();
            Room checkRoom = roomDao.findById(room.getRoomNumber());
            if (checkRoom != null) {
                logger.warn("Room with number {} already exists", room.getRoomNumber());
                throw new SQLException("Error: Room already exists.");
            }
            roomDao.save(room);
            connection.commit();
            return room.getRoomNumber();
        } catch (SQLException | RuntimeException e) {
            logger.error("Error occurred while adding room: {}", e.getMessage(), e);
            connection.rollback();
            e.printStackTrace();
        } finally {
         logger.info("Exiting addRoom method");
            connection.setAutoCommit(true);
        }
        return 0;
    }

    public int updateRoom() throws SQLException {
        logger.info("Executing updateRoom method");
        if (connection == null) {
            logger.error("connection is null");
            throw new SQLException("Error: No connection to the database.");
        }
        try {
            connection.setAutoCommit(false);
            Room room = roomDao.getRoomFromConsole();
            Room existingRoom = roomDao.findById(room.getRoomNumber());
            if (existingRoom == null) {
                logger.warn("Room whith id {} not founded" , room.getRoomNumber());
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
            logger.info("Exiting updateRoom method");
            return existingRoom.getRoomNumber();
        } catch (SQLException | RuntimeException e) {
            logger.error("Error occurred while updating room: {}", e.getMessage(), e);
            connection.rollback();
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
        logger.info("Exiting updateRoom method");
        return 0;
    }

    public void findByid(int id) throws SQLException {
        logger.info("Executing find By id method");
        if (connection == null) {
            logger.error("connection is null");
            throw new SQLException("Error: No connection to the database.");
        }
        Room room = roomDao.findById(id);
        if (room == null) {
            logger.warn("Room whith id {} not founded" , id);
            throw new SQLException("Room with ID " + id + " not found");
        }
        logger.info("Exiting find By id method");
        consoleView.print(room.toString());
    }

    public void getAllRooms() throws SQLException {
        logger.info("Executing getAllRooms method");
        if (connection == null) {
            logger.error("connection is null");
            throw new SQLException("Error: No connection to the database.");
        }
        try {
            connection.setAutoCommit(false);
            List<Room> rooms = roomDao.findAll();
            if (rooms.isEmpty()) {
                logger.warn("Room empty");
                throw new SQLException("Room list is empty");
            }
            for (Room room : rooms) {
                System.out.println(room);
            }
            connection.commit();
        } catch (SQLException | RuntimeException e) {
            logger.error("Error occurred while retrieving all rooms: {}", e.getMessage(), e);
            connection.rollback();
           e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
        logger.info("Exiting getAllRooms method");
    }

    public void displayRoomsSortedByNumber() throws SQLException {
        logger.info("Executing display Rooms Sorted By Number method");
        if (connection == null) {
            logger.error("connection is null");
            throw new SQLException("Error: No connection to the database.");
        }
        try {
            connection.setAutoCommit(false);
            List<Room> rooms = roomDao.getRoomsSortedByNumber();
            if (rooms.isEmpty()) {
                logger.warn("No available rooms to sort by number");
                throw new SQLException("No available rooms to sort by number");
            }
            for (Room room : rooms) {
                System.out.println(room);
            }
            connection.commit();
        } catch (SQLException | RuntimeException e) {
            logger.error("Error occurred while displaying rooms sorted by number: {}", e.getMessage(), e);
            connection.rollback();
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
        logger.info("Exiting display Rooms Sorted By Number method");
    }

    public void displayRoomsSortedByPrice() throws SQLException {
        logger.info("Executing display Rooms Sorted By Price method");
        if (connection == null) {
            logger.error("connection is null");
            throw new SQLException("Error: No connection to the database.");
        }
        try {
            connection.setAutoCommit(false);
            List<Room> rooms = roomDao.getRoomsSortedByPrice();
            if (rooms.isEmpty()) {
                logger.warn("No available rooms to sort by price");
                throw new SQLException("No available rooms to sort by price");
            }
            for (Room room : rooms) {
                System.out.println(room);
            }
            connection.commit();
        } catch (SQLException | RuntimeException e) {
            logger.error("Error occurred while displaying rooms sorted by price: {}", e.getMessage(), e);
            connection.rollback();
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
        logger.info("Exiting display Rooms Sorted By Price method");
    }

    public void deleteRoom() throws SQLException {
        logger.info("Executing deleteRoom method");

        if (connection == null) {
            logger.error("connection is null");
            throw new SQLException("Error: No connection to the database.");
        }
        try {
            connection.setAutoCommit(false);
            Room room = roomDao.getRoomFromConsole();
            Room checkRoom = roomDao.findById(room.getRoomNumber());
            if (checkRoom == null) {
                logger.warn("Room whith id {} not founded" , room.getRoomNumber());
                throw new SQLException("Error: Room with ID " + room.getRoomNumber() + " not found" );
            }

            roomDao.delete(room);
            connection.commit();
        } catch (SQLException | RuntimeException e) {
            logger.error("Error occurred while deleting room: {}", e.getMessage(), e);
            connection.rollback();
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
        logger.info("Exiting deleteRoom method");
    }

    public void addingAdditionalServices() throws SQLException {
        logger.info("Executing addingAdditionalServices method");
        if (connection == null) {
            logger.error("connection is null");
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
            logger.error("Error occurred while addingAdditionalServices : {}", e.getMessage(), e);
            if (!connection.getAutoCommit()) {
                connection.rollback();
            }
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(previousAutoCommit);
        }
        logger.info("Exiting addingAdditionalServices method");
    }





    public void calculate() throws SQLException {
        logger.info("Executing calculate method");
        if (connection == null) {
            logger.error("connection is null");
            throw new SQLException("Error: No connection to the database.");
        }
        try {
            connection.setAutoCommit(false);
            double cost = roomDao.calculate();
            consoleView.display("Total cost: $" + cost);
            connection.commit();
        } catch (SQLException | RuntimeException e) {
            logger.error("Error occurred while calculating : {}", e.getMessage(), e);
            connection.rollback();
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
        logger.info("Exiting calculate method");
    }

    public void numberOfGuestsInRoom() throws SQLException {
        logger.info("Executing numberOfGuestsInRoom method");
        if (connection == null) {
            logger.error("connection is null");
            throw new SQLException("Error: No connection to the database.");
        }
        try {
            connection.setAutoCommit(false);
            int kol = roomDao.numberOfGuestsInRoom();
            connection.commit();
            consoleView.display("Number of guests in room: " + kol);
        } catch (SQLException | RuntimeException e) {
            logger.error("Error occurred while counting guests in room: {}", e.getMessage(), e);
            connection.rollback();
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
        logger.info("Exiting numberOfGuestsInRoom method");
    }
}
