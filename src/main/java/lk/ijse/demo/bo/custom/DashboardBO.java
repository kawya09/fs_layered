package lk.ijse.demo.bo.custom;

import lk.ijse.demo.bo.SuperBO;
import lk.ijse.demo.dao.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DashboardBO extends SuperBO {
    public int getTotalCustomers() throws SQLException ;

    public int getTotalItems() throws SQLException ;

    public int getTotalOrders() throws SQLException ;

    public double getTotalRevenue() throws SQLException ;

    public int getPendingOrders() throws SQLException ;

    public int getLowStockCount() throws SQLException ;
}
