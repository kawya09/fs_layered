package lk.ijse.demo.bo.custom.impl;

import lk.ijse.demo.bo.custom.SalesOrderBO;
import lk.ijse.demo.dao.CrudUtil;
import lk.ijse.demo.dao.DAOFactory;
import lk.ijse.demo.dao.custom.SalesOrderDAO;
import lk.ijse.demo.db.DBConnection;
import lk.ijse.demo.dto.SalesOrderItemDTO;
import lk.ijse.demo.entity.SalesOrder;
import lk.ijse.demo.entity.SalesOrderItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalesOrderBOImpl implements SalesOrderBO {
    SalesOrderDAO salesOrderDAO = (SalesOrderDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SALESORDER);

    public boolean save(SalesOrder entity) throws SQLException {
//        return CrudUtil.execute("INSERT INTO sales_order_items (order_id, item_id, quantity, unit_price) VALUES (?,?,?,?)",entity.getOrderId(),entity.getItemId(),entity.getQuantity(),entity.getUnitPrice());
        Connection conn = DBConnection.getInstance().getConnection();
        conn.setAutoCommit(false);
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO sales_orders (customer_id, sale_type, status, notes) VALUES (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setInt(1, entity.getCustomerId());
            ps.setString(2, entity.getSaleType());
            ps.setString(3, entity.getStatus());
            ps.setString(4, entity.getNotes());
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            int orderId = 0;
            if (keys.next()) orderId = keys.getInt(1);

            for (SalesOrderItemDTO item : entity.getItems()) {
                CrudUtil.execute(
                        "INSERT INTO sales_order_items (order_id, item_id, quantity, unit_price) VALUES (?,?,?,?)",
                        orderId, item.getItemId(), item.getQuantity(), item.getUnitPrice()
                );
                CrudUtil.execute(
                        "UPDATE items SET stock_quantity = stock_quantity - ? WHERE item_id=?",
                        item.getQuantity(), item.getItemId()
                );
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    @Override
    public boolean update(SalesOrder entity) throws SQLException {
        return CrudUtil.execute("UPDATE sales_orders SET status=? WHERE order_id=?", entity.getStatus(), entity.getOrderId());

    }

    @Override
    public boolean delete(int Id) throws SQLException {
        return CrudUtil.execute("DELETE FROM items WHERE item_id=?", Id);
    }

    @Override
    public List<SalesOrder> getAll() throws SQLException {
        List<SalesOrder> list = new ArrayList<>();
        ResultSet rs = CrudUtil.execute(
                "SELECT so.*, c.name AS customer_name, " +
                        "COALESCE((SELECT SUM(soi.quantity * soi.unit_price) FROM sales_order_items soi WHERE soi.order_id = so.order_id), 0) AS total_amount " +
                        "FROM sales_orders so JOIN customers c ON so.customer_id = c.customer_id " +
                        "ORDER BY so.order_date DESC"
        );
        while (rs.next()) {
            SalesOrder entity = new SalesOrder(
                    rs.getInt("order_id"),
                    rs.getInt("customer_id"),
                    rs.getString("order_date"),
                    rs.getString("sale_type"),
                    rs.getString("status"),
                    rs.getString("notes")
            );
            entity.setCustomerName(rs.getString("customer_name"));
            entity.setTotalAmount(rs.getDouble("total_amount"));
            list.add(entity);
        }
        return list;
    }

    @Override
    public List<SalesOrder> search(String id) throws SQLException {
        List<SalesOrder> list = new ArrayList<>();
        ResultSet rs = CrudUtil.execute(
                "SELECT so.*, c.name AS customer_name, " +
                        "COALESCE((SELECT SUM(soi.quantity * soi.unit_price) FROM sales_order_items soi WHERE soi.order_id = so.order_id), 0) AS total_amount " +
                        "FROM sales_orders so JOIN customers c ON so.customer_id = c.customer_id " +
                        "WHERE c.name LIKE ? OR so.status LIKE ? OR so.sale_type LIKE ? " +
                        "ORDER BY so.order_date DESC",
                "%" + id+ "%", "%" + id + "%", "%" +id + "%"
        );
        while (rs.next()) {
            SalesOrder entity = new SalesOrder(
                    rs.getInt("order_id"),
                    rs.getInt("customer_id"),
                    rs.getString("order_date"),
                    rs.getString("sale_type"),
                    rs.getString("status"),
                    rs.getString("notes")
            );
            entity.setCustomerName(rs.getString("customer_name"));
            entity.setTotalAmount(rs.getDouble("total_amount"));
            list.add(entity);
        }
        return list;
    }

    @Override
    public List<SalesOrderItem> getOrderItems(int orderId) throws SQLException {
        List<SalesOrderItem> list = new ArrayList<>();
        ResultSet rs = CrudUtil.execute(
                "SELECT soi.*, i.name AS item_name FROM sales_order_items soi " +
                        "JOIN items i ON soi.item_id = i.item_id WHERE soi.order_id=?", orderId
        );
        while (rs.next()) {
            SalesOrderItem entity= new SalesOrderItem(
                    rs.getInt("item_id"),
                    rs.getString("item_name"),
                    rs.getInt("quantity"),
                    rs.getDouble("unit_price")
            );
            entity.setOrderItemId(rs.getInt("order_item_id"));
            entity.setOrderId(orderId);
            list.add(entity);
        }
        return list;
    }





}
