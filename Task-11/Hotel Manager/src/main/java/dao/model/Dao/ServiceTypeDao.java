package dao.model.Dao;

import annotation.OwnInject;
import dao.model.GenericDao;
import dao.model.connection.ConnectionManager;
import dao.model.entity.ServiceType;
import viev.ConsoleView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class ServiceTypeDao implements GenericDao<ServiceType> {
    @OwnInject
    ConsoleView consoleView;
    public static final String INSERT_SERVICE_TYPE = "INSERT INTO service_type (name, price) VALUES (?,?::text::money)";
    public  static final String DISPLAY_SORTED_SERVICES_BY_PRICE = "SELECT id, name, price::NUMERIC(10,2)  FROM service_type ORDER BY price";
    public static final String UPDATE_SERVICE = "UPDATE service_type SET name = ?, price = ?::numeric::money WHERE id = ?";
    private static final String DELETE_SERVICE_TYPE_BY_ID = "DELETE FROM service_type WHERE id = ?";
    @Override
    public int save(ServiceType serviceType) {
        Connection connection = ConnectionManager.connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SERVICE_TYPE);
            preparedStatement.setString(1, serviceType.getName());
            preparedStatement.setDouble(2, serviceType.getPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  1;
    }

    @Override
    public void update(ServiceType entity) {
        ServiceType before = findById(entity.getId());
           if (entity.getName() != null) before.setName(entity.getName());
           if (entity.getPrice() >= 0) before.setPrice(entity.getPrice());

        try (Connection connection = ConnectionManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SERVICE)) {
            preparedStatement.setString(1, before.getName());
            preparedStatement.setDouble(2, before.getPrice());
            preparedStatement.setInt(3, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(ServiceType entity) {
        int id = parseInt(consoleView.getInput("Enter id service you want to update\n"));
        try (Connection connection = ConnectionManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SERVICE_TYPE_BY_ID)) {
            preparedStatement.setInt(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Service type deleted successfully.");
            } else {
                System.out.println("Service type not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ServiceType findById(int id) {
        String SELECT_SERVICE_TYPE_BY_ID = "SELECT id, name, price::NUMERIC(10,2) FROM service_type WHERE id = ?";
        try (Connection connection = ConnectionManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SERVICE_TYPE_BY_ID)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int serviceTypeId = resultSet.getInt("id");
                    String serviceTypeName = resultSet.getString("name");
                    double price = resultSet.getDouble("price");
                    return new ServiceType(serviceTypeId, serviceTypeName, price);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public List<ServiceType> findAll() {
        List<ServiceType> serviceTypes = new ArrayList<>();
        int serviceTypeId = 0;
        int kol  =  0 ;
        String serviceTypeName = "";
        double price = 0;
        try (Connection connection = ConnectionManager.connect();
             PreparedStatement statement = connection.prepareStatement(DISPLAY_SORTED_SERVICES_BY_PRICE)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                serviceTypeId = resultSet.getInt("id");
                serviceTypeName = resultSet.getString("name");
                price =  resultSet.getDouble("price");
                serviceTypes.add(new ServiceType(serviceTypeId,serviceTypeName,price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serviceTypes;
    }
    public ServiceType getServiceFromConsole(){
        String nameService = consoleView.getInput("Enter the name of the new service you want to add:\n");
        String priceInput = consoleView.getInput("Enter price (or press Enter to skip):\n");
        Double price = priceInput.isEmpty() ? null : Double.parseDouble(priceInput);
        return new ServiceType(nameService, price);
    }
}
