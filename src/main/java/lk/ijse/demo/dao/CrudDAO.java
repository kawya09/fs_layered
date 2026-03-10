package lk.ijse.demo.dao;

import lk.ijse.demo.dto.CustomerDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CrudDAO<T> extends  SuperDAO{

    public boolean save(T entity) throws SQLException ;

    public boolean update(T entity) throws SQLException ;

    public boolean delete(int Id) throws SQLException ;

    public List<T> getAll() throws SQLException ;

    public List<T> search(String id) throws SQLException ;
}
