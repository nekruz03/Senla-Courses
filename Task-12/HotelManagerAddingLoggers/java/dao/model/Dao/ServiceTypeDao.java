package dao.model.Dao;


import annotation.OwnInject;
import dao.model.GenericDao;
import dao.model.connection.ConnectionManager;
import dao.model.entity.ServiceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import viev.ConsoleView;




import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import static java.lang.Integer.parseInt;


public class ServiceTypeDao implements GenericDao<ServiceType> {
    @OwnInject
    private ConsoleView consoleView;

private  Connection connection = ConnectionManager.connect() ;
    private  static final Logger logger = LoggerFactory.getLogger(ServiceTypeDao.class);
    public static final String INSERT_SERVICE_TYPE = "INSERT INTO service_type (name, price) VALUES (?,?::text::money)";
    public static final String DISPLAY_SORTED_SERVICES_BY_PRICE = "SELECT id, name, price::NUMERIC(10,2) FROM service_type ORDER BY price";
    public static final String UPDATE_SERVICE = "UPDATE service_type SET name = ?, price = ?::numeric::money WHERE id = ?";
    private static final String DELETE_SERVICE_TYPE_BY_ID = "DELETE FROM service_type WHERE id = ?";
    @Override
    public int save(ServiceType serviceType) {
        logger.info("Saving service type {}", serviceType);
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SERVICE_TYPE)) {
            preparedStatement.setString(1, serviceType.getName());
            preparedStatement.setDouble(2, serviceType.getPrice());
            preparedStatement.executeUpdate();
            logger.info("Service type {} saved successfully", serviceType);
            return 1;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException("Database error while saving service type", e);
        }
    }


    @Override
    public void update(ServiceType entity)  {
        logger.info("Updating service type id {}", entity.getId());
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SERVICE)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setDouble(2, entity.getPrice());
            preparedStatement.setInt(3, entity.getId());
            int resultSet  =  preparedStatement.executeUpdate();
            if (resultSet == 0) {
                logger.warn("failed to update service type id {}", entity.getId());
                throw  new SQLException("Failed to update");
            }
            logger.info("Service type id {} updated successfully", entity.getId());

        } catch (SQLException e) {
            try {
                throw new SQLException("No rows updated: ServiceType with ID " + entity.getId() + " not found.");
            } catch (SQLException ex) {
                logger.error("failed to update service type id {}", entity.getId());
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void delete(ServiceType entity) {
        logger.info("Deleting service type id {}", entity.getId());
        int id = parseInt(consoleView.getInput("Enter id service you want to delete:"));
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SERVICE_TYPE_BY_ID)) {
            preparedStatement.setInt(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted == 0) {
                logger.warn("failed to delete service type id {}", entity.getId());
                throw new RuntimeException("Service type with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException("Database error while deleting service type", e);
        }
        logger.info("Service type id {} deleted successfully", entity.getId());
    }


    @Override
    public ServiceType findById(int id) {
        logger.info("Finding service type id {}", id);
        int serviceTypeId = 0;
        String serviceTypeName = "";
        double price = 0;
        String SELECT_SERVICE_TYPE_BY_ID = "SELECT id, name, price::NUMERIC(10,2) FROM service_type WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SERVICE_TYPE_BY_ID)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    logger.warn("Failed to find service type id {}", id);
                    throw  new SQLException("Service type with ID " + id + " not found.");
                }
                    serviceTypeId = resultSet.getInt("id");
                    serviceTypeName = resultSet.getString("name");
                    price = resultSet.getDouble("price");
            }
        } catch (SQLException e) {
            logger.warn("Failed to find service type id {}", id);
            throw new RuntimeException("Database error while fetching service type by ID", e);
        }
        logger.info("Service type id {} found successfully", serviceTypeId);
        return new ServiceType(serviceTypeId, serviceTypeName, price);
    }


    @Override
    public List<ServiceType> findAll() {
        logger.info("Finding all service types");
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
            logger.error(e.getMessage());
            throw new RuntimeException("Database error while fetching all service types", e);
        }
        if (serviceTypes.isEmpty()) {
            logger.warn("Service type list is empty");
        }
        else logger.info("Service type list found {}", serviceTypes);
        return serviceTypes;
    }


    public ServiceType getServiceFromConsole() {
        logger.info("Getting service type from console");
        String nameService = consoleView.getInput("Enter the name of the new service you want to add:");
        String priceInput = consoleView.getInput("Enter price (or press Enter to skip):");
        Double price = priceInput.isEmpty() ? null : Double.parseDouble(priceInput);
        logger.info("Successfully got service type from console");
        return new ServiceType(nameService, price);
    }
}


