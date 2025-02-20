package dao.model.service;

import annotation.OwnInject;
import dao.model.Dao.ServiceTypeDao;
import dao.model.entity.ServiceType;
import viev.ConsoleView;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ServiceTypeService {
    @OwnInject
    ConsoleView consoleView;

    @OwnInject
    ServiceTypeDao serviceTypeDao;

    @OwnInject
    Connection connection;
    public void addServiceType() throws SQLException {
        if (connection == null) {
            throw new SQLException("Error: No connection to the database.");
        }
        try {
            connection.setAutoCommit(false);
            ServiceType serviceType = serviceTypeDao.getServiceFromConsole();
            serviceTypeDao.save(serviceType);
            connection.commit();
        } catch (SQLException | RuntimeException e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void getAllServiceTypes() throws SQLException {
        if (connection == null) {
            throw new SQLException("Error: No connection to the database.");
        }
        try {
            connection.setAutoCommit(false);
            connection.setAutoCommit(false);
            List<ServiceType> serviceTypes = serviceTypeDao.findAll();
            if (serviceTypes.isEmpty()) {
                throw new SQLException("Service type list is empty");
            }
            for (ServiceType serviceType : serviceTypes) {
                System.out.println(serviceType);
            }
            connection.commit();
        } catch (SQLException | RuntimeException e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void updateServiceType() throws SQLException {

        if (connection == null) {
            throw new SQLException("Error: No connection to the database.");
        }
        try {
            connection.setAutoCommit(false);
            int id = Integer.parseInt(consoleView.getInput("Enter the ID of the service you want to update: "));
            ServiceType before = serviceTypeDao.findById(id);
        if (before == null) {
            throw new RuntimeException("Service type with ID " + id + " not found.");
        }

            ServiceType serviceType = serviceTypeDao.getServiceFromConsole();
            if (serviceType.getName() != null) before.setName(serviceType.getName());
            if (serviceType.getPrice() >= 0) before.setPrice(serviceType.getPrice());
            before.setId(id);

            serviceTypeDao.update(before);
            connection.commit();
        } catch (SQLException | RuntimeException e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void getServiceTypeById() throws SQLException {
        if (connection == null) {
            throw new SQLException("Error: No connection to the database.");
        }
        int id = Integer.parseInt(consoleView.getInput("Enter the ID of the service you want to view: "));
        ServiceType serviceType = serviceTypeDao.findById(id);
        if (serviceType == null) {
            throw new SQLException("Service type with ID " + id + " not found");
        }
        System.out.println(serviceType);
    }

    public void deleteServiceTypeById() throws SQLException {
        if (connection == null) {
            throw new SQLException("Error: No connection to the database.");
        }
        try {
            connection.setAutoCommit(false);
            int id = Integer.parseInt(consoleView.getInput("Enter the ID of the service you want to delete: "));
            ServiceType serviceType = serviceTypeDao.findById(id);
            if (serviceType == null) {
                throw new SQLException("Service type with ID " + id + " not found");
            }
            serviceTypeDao.delete(serviceType);
            connection.commit();
        } catch (SQLException | RuntimeException e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
