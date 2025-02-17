package dao.model;

import java.util.List;

public interface GenericDao <T>{
    int  save(T entity);
    void update(T entity);
    void delete(T entity);
    T findById(int id);
    List<T> findAll();
}
