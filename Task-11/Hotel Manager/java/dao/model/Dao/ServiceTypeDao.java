package dao.model.Dao;


import annotation.OwnInject;
import dao.model.GenericDao;
import dao.model.connection.ConnectionManager;
import dao.model.entity.ServiceType;
import viev.ConsoleView;




import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import static java.lang.Integer.parseInt;


public class ServiceTypeDao implements GenericDao<ServiceType> {
    @OwnInject
    private ConsoleView consoleView;

private  Connection connection = ConnectionManager.connect() ;
    public static final String INSERT_SERVICE_TYPE = "INSERT INTO service_type (name, price) VALUES (?,?::text::money)";
    public static final String DISPLAY_SORTED_SERVICES_BY_PRICE = "SELECT id, name, price::NUMERIC(10,2) FROM service_type ORDER BY price";
    public static final String UPDATE_SERVICE = "UPDATE service_type SET name = ?, price = ?::numeric::money WHERE id = ?";
    private static final String DELETE_SERVICE_TYPE_BY_ID = "DELETE FROM service_type WHERE id = ?";
    @Override
    public int save(ServiceType serviceType) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SERVICE_TYPE)) {
            preparedStatement.setString(1, serviceType.getName());
            preparedStatement.setDouble(2, serviceType.getPrice());
            preparedStatement.executeUpdate();
            return 1;
        } catch (SQLException e) {
            throw new RuntimeException("Database error while saving service type", e);
        }
    }


    @Override
    public void update(ServiceType entity)  {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SERVICE)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setDouble(2, entity.getPrice());
            preparedStatement.setInt(3, entity.getId());
            int resultSet  =  preparedStatement.executeUpdate();
            if (resultSet == 0) {throw  new SQLException("Failed to update");}

        } catch (SQLException e) {
            try {
                throw new SQLException("No rows updated: ServiceType with ID " + entity.getId() + " not found.");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }


    @Override
    public void delete(ServiceType entity) {
        int id = parseInt(consoleView.getInput("Enter id service you want to delete:"));
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SERVICE_TYPE_BY_ID)) {
            preparedStatement.setInt(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted == 0) {
                throw new RuntimeException("Service type with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error while deleting service type", e);
        }
    }


    @Override
    public ServiceType findById(int id) {
        int serviceTypeId = 0;
        String serviceTypeName = "";
        double price = 0;
        String SELECT_SERVICE_TYPE_BY_ID = "SELECT id, name, price::NUMERIC(10,2) FROM service_type WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SERVICE_TYPE_BY_ID)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) throw  new SQLException("Service type with ID " + id + " not found.");
                    serviceTypeId = resultSet.getInt("id");
                    serviceTypeName = resultSet.getString("name");
                    price = resultSet.getDouble("price");

            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error while fetching service type by ID", e);
        }
        return new ServiceType(serviceTypeId, serviceTypeName, price);
    }


    @Override
    public List<ServiceType> findAll() {
        List<ServiceType> serviceTypes = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(DISPLAY_SORTED_SERVICES_BY_PRICE)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int serviceTypeId = resultSet.getInt("id");
                    String serviceTypeName = resultSet.getString("name");
                    double price = resultSet.getDouble("price");
                    serviceTypes.add(new ServiceType(serviceTypeId, serviceTypeName, price));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error while fetching all service types", e);
        }
        return serviceTypes;
    }


    public ServiceType getServiceFromConsole() {
        String nameService = consoleView.getInput("Enter the name of the new service you want to add:");
        String priceInput = consoleView.getInput("Enter price (or press Enter to skip):");
        Double price = priceInput.isEmpty() ? null : Double.parseDouble(priceInput);
        return new ServiceType(nameService, price);
    }
}


