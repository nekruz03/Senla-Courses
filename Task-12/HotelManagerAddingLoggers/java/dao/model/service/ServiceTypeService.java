package dao.model.service;

import annotation.OwnInject;
import dao.model.Dao.ServiceTypeDao;
import dao.model.entity.ServiceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import viev.ConsoleView;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ServiceTypeService {
    private  static final Logger logger = LoggerFactory.getLogger(ServiceTypeService.class);
    @OwnInject
    ConsoleView consoleView;

    @OwnInject
    ServiceTypeDao serviceTypeDao;

    @OwnInject
    Connection connection;
    public void addServiceType() throws SQLException {
        logger.info("Executing addServiceType");
        if (connection == null) {
            logger.error("Сonnection is null");
            throw new SQLException("Error: No connection to the database.");
        }
        try {
            connection.setAutoCommit(false);
            ServiceType serviceType = serviceTypeDao.getServiceFromConsole();
            serviceTypeDao.save(serviceType);
            connection.commit();
        } catch (SQLException | RuntimeException e) {
            logger.error("Error when adding service {}", e.getMessage(),e);
            connection.rollback();
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
        logger.info("Exiting addServiceType");
    }

    public void getAllServiceTypes() throws SQLException {
        logger.info("Executing getAllServiceTypes");
        if (connection == null) {
            logger.error("Connection is null");
            throw new SQLException("Error: No connection to the database.");
        }
        try {
            connection.setAutoCommit(false);
            connection.setAutoCommit(false);
            List<ServiceType> serviceTypes = serviceTypeDao.findAll();
            if (serviceTypes.isEmpty()) {
                logger.warn("No services found, List is empty");
                throw new SQLException("Service type list is empty");
            }
            for (ServiceType serviceType : serviceTypes) {
                System.out.println(serviceType);
            }
            connection.commit();
        } catch (SQLException | RuntimeException e) {
            logger.error("Error when getting services list {}", e.getMessage(),e);
            connection.rollback();
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
        logger.info("Exiting getAllServiceTypes");
    }

    public void updateServiceType() throws SQLException {
        if (connection == null) {
            logger.error("Connection is null");
            throw new SQLException("Error: No connection to the database.");
        }
        try {
            connection.setAutoCommit(false);
            int id = Integer.parseInt(consoleView.getInput("Enter the ID of the service you want to update: "));
            ServiceType before = serviceTypeDao.findById(id);
        if (before == null) {
            logger.warn("No service found with id {}", id);
            throw new RuntimeException("Service type with ID " + id + " not found.");
        }
            ServiceType serviceType = serviceTypeDao.getServiceFromConsole();
            if (serviceType.getName() != null) before.setName(serviceType.getName());
            if (serviceType.getPrice() >= 0) before.setPrice(serviceType.getPrice());
            before.setId(id);
            serviceTypeDao.update(before);
            connection.commit();
        } catch (SQLException | RuntimeException e) {
            logger.error("Error when updating service type {}", e.getMessage(),e);
            connection.rollback();
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
        logger.info("Exiting updateServiceType");
    }

    public void getServiceTypeById() throws SQLException {
        logger.info("Executing get Service Type By Id");
        if (connection == null) {
            logger.error("Connection is null");
            throw new SQLException("Error: No connection to the database.");
        }
        int id = Integer.parseInt(consoleView.getInput("Enter the ID of the service you want to view: "));
        ServiceType serviceType = serviceTypeDao.findById(id);
        if (serviceType == null) {
            logger.warn("No service found with id {}", id);
            throw new SQLException("Service type with ID " + id + " not found");
        }
        System.out.println(serviceType);
        logger.info("Exiting get Service Type By Id");
    }

    public void deleteServiceTypeById() throws SQLException {
        if (connection == null) {
            logger.error("Connection is null");
            throw new SQLException("Error: No connection to the database.");
        }
        try {
            connection.setAutoCommit(false);
            int id = Integer.parseInt(consoleView.getInput("Enter the ID of the service you want to delete: "));
            ServiceType serviceType = serviceTypeDao.findById(id);
            if (serviceType == null) {
                logger.warn("No service found with id {}", id);
                throw new SQLException("Service type with ID " + id + " not found");
            }
            serviceTypeDao.delete(serviceType);
            connection.commit();
        } catch (SQLException | RuntimeException e) {
            logger.error("Error when deleting service type {}", e.getMessage(),e);
            connection.rollback();
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
        logger.info("Exiting delete Service Type By Id");
    }
}
