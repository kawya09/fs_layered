package lk.ijse.demo.bo.custom;

import lk.ijse.demo.bo.SuperBO;
import lk.ijse.demo.entity.SalesOrder;
import lk.ijse.demo.entity.SalesOrderItem;

import java.sql.SQLException;
import java.util.List;

public interface SalesOrderBO extends SuperBO {
    boolean update(SalesOrder entity) throws SQLException;

    boolean delete(int Id) throws SQLException;

    List<SalesOrder> getAll() throws SQLException;

    List<SalesOrder> search(String id) throws SQLException;

    List<SalesOrderItem> getOrderItems(int orderId) throws SQLException;
}
