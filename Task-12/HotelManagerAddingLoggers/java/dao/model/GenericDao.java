package dao.model;

import java.sql.SQLException;
import java.util.List;

public interface GenericDao <T>{
    int  save(T entity);
    void update(T entity);
    void delete(T entity) throws SQLException;
    T findById(int id);
    List<T> findAll();
}
