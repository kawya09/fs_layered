package lk.ijse.demo.dao.custom.impl;

import lk.ijse.demo.dao.CrudDAO;
import lk.ijse.demo.dao.CrudUtil;
import lk.ijse.demo.entity.Delivery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeliveryDAOImpl implements CrudDAO<Delivery> {
    @Override
    public boolean save(Delivery entity) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO deliveries (order_id, delivery_address, scheduled_date, delivery_fee, status, driver_name, notes) VALUES (?,?,?,?,?,?,?)",
                entity.getOrderId(), entity.getDeliveryAddress(), entity.getScheduledDate(),
                entity.getDeliveryFee(), entity.getStatus(), entity.getDriverName(), entity.getNotes()
        );
    }

    @Override
    public boolean update(Delivery entity) throws SQLException {
     return CrudUtil.execute(
             "UPDATE deliveries SET delivery_address=?, scheduled_date=?, delivered_date=?, delivery_fee=?, status=?, driver_name=?, notes=? , WHERE delivery-id=?",
             entity.getDeliveryAddress(),entity.getScheduledDate(), entity.getDeliveredDate(),
             entity.getDeliveryFee(),entity.getStatus(),entity.getDriverName(),entity.getNotes(),entity.getDeliveryId()

     );
    }

    @Override
    public boolean delete(int Id) throws SQLException {
        return false;
    }

    @Override
    public List<Delivery> getAll() throws SQLException {
        List<Delivery> list = new ArrayList<>();
        ResultSet rs = CrudUtil.execute(
                "SELECT d.*, c.name AS customer_name FROM deliveries d " +
                        "JOIN sales_orders so ON d.order_id = so.order_id " +
                        "JOIN customers c ON so.customer_id = c.customer_id " +
                        "ORDER BY d.scheduled_date DESC"
        );
        while (rs.next()) {
            Delivery entity = new Delivery(
                    rs.getInt("delivery_id"),
                    rs.getInt("order_id"),
                    rs.getString("delivery_address"),
                    rs.getString("scheduled_date"),
                    rs.getString("delivered_date"),
                    rs.getDouble("delivery_fee"),
                    rs.getString("status"),
                    rs.getString("driver_name"),
                    rs.getString("notes")
            );
            entity.setCustomerName(rs.getString("customer_name"));
            list.add(entity);
        }
        return list;
    }

    @Override
    public List<Delivery> search(String id) throws SQLException {
        List<Delivery> list = new ArrayList<>();
        ResultSet rs = CrudUtil.execute(
                "SELECT d.*, c.name AS customer_name FROM deliveries d " +
                        "JOIN sales_orders so ON d.order_id = so.order_id " +
                        "JOIN customers c ON so.customer_id = c.customer_id " +
                        "WHERE c.name LIKE ? OR d.status LIKE ? OR d.driver_name LIKE ? " +
                        "ORDER BY d.scheduled_date DESC",
                "%" + id + "%", "%" + id + "%", "%" + id + "%"
        );
        while (rs.next()) {
            Delivery entity = new Delivery(
                    rs.getInt("delivery_id"),
                    rs.getInt("order_id"),
                    rs.getString("delivery_address"),
                    rs.getString("scheduled_date"),
                    rs.getString("delivered_date"),
                    rs.getDouble("delivery_fee"),
                    rs.getString("status"),
                    rs.getString("driver_name"),
                    rs.getString("notes")
            );
            entity.setCustomerName(rs.getString("customer_name"));
            list.add(entity);
        }
        return list;
    }

}
