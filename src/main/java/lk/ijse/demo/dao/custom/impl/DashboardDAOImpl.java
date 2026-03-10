package lk.ijse.demo.dao.custom.impl;

import lk.ijse.demo.dao.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardDAOImpl {
    public int getTotalCustomers() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT COUNT(*) AS cnt FROM customers");
        if (rs.next()) return rs.getInt("cnt");
        return 0;
    }

    public int getTotalItems() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT COUNT(*) AS cnt FROM items");
        if (rs.next()) return rs.getInt("cnt");
        return 0;
    }

    public int getTotalOrders() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT COUNT(*) AS cnt FROM sales_orders");
        if (rs.next()) return rs.getInt("cnt");
        return 0;
    }

    public double getTotalRevenue() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT COALESCE(SUM(amount),0) AS total FROM payments WHERE status='completed'");
        if (rs.next()) return rs.getDouble("total");
        return 0;
    }

    public int getPendingOrders() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT COUNT(*) AS cnt FROM sales_orders WHERE status='pending'");
        if (rs.next()) return rs.getInt("cnt");
        return 0;
    }

    public int getLowStockCount() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT COUNT(*) AS cnt FROM items WHERE stock_quantity < 5");
        if (rs.next()) return rs.getInt("cnt");
        return 0;
    }
}
