package lk.ijse.demo.dao.custom;

import lk.ijse.demo.dao.CrudDAO;
import lk.ijse.demo.dao.CrudUtil;
import lk.ijse.demo.entity.SalesOrder;
import lk.ijse.demo.entity.SalesOrderItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface SalesOrderDAO extends CrudDAO<SalesOrder> {
    public boolean save(SalesOrder entity) throws SQLException;
    public boolean update(SalesOrder entity) throws SQLException ;
    public boolean delete(int Id) throws SQLException;
    public List<SalesOrder> getAll() throws SQLException;
    public List<SalesOrderItem> getOrderItems(int orderId) throws SQLException ;
    public static int getTotalOrders() throws SQLException ;
    public int getPendingOrders() throws SQLException ;



}
