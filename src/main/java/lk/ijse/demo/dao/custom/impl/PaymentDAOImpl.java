package lk.ijse.demo.dao.custom.impl;

import lk.ijse.demo.dao.CrudUtil;
import lk.ijse.demo.dao.custom.PaymentDAO;
import lk.ijse.demo.dto.PaymentDTO;
import lk.ijse.demo.entity.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {
    @Override
    public boolean save(Payment entity) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO payments (order_id, amount, method, status, reference_no, notes) VALUES (?,?,?,?,?,?)",
                entity.getOrderId(), entity.getAmount(), entity.getMethod(), entity.getStatus(),
                entity.getReferenceNo(), entity.getNotes()
        );
    }

    @Override
    public boolean update(Payment entity) throws SQLException {
return CrudUtil.execute("UPDATE payments SET status=? , WHERE payment_id=? ",entity.getStatus(),entity.getPaymentId());
    }

    @Override
    public boolean delete(int Id) throws SQLException {
        return CrudUtil.execute("DELETE FROM items WHERE item_id=?", Id);
    }

    @Override
    public List<Payment> getAll() throws SQLException {
        List<Payment> list = new ArrayList<>();
        ResultSet rs = CrudUtil.execute(
                "SELECT p.*, c.name AS customer_name FROM payments p " +
                        "JOIN sales_orders so ON p.order_id = so.order_id " +
                        "JOIN customers c ON so.customer_id = c.customer_id " +
                        "ORDER BY p.payment_date DESC"
        );
        while (rs.next()) {
            Payment entity = new Payment(
                    rs.getInt("payment_id"),
                    rs.getInt("order_id"),
                    rs.getString("payment_date"),
                    rs.getDouble("amount"),
                    rs.getString("method"),
                    rs.getString("status"),
                    rs.getString("reference_no"),
                    rs.getString("notes")
            );
            entity.setCustomerName(rs.getString("customer_name"));
            list.add(entity);
        }
        return list;
    }

    @Override
    public List<Payment> search(String id) throws SQLException {
        List<Payment> list = new ArrayList<>();
        ResultSet rs = CrudUtil.execute(
                "SELECT p.*, c.name AS customer_name FROM payments p " +
                        "JOIN sales_orders so ON p.order_id = so.order_id " +
                        "JOIN customers c ON so.customer_id = c.customer_id " +
                        "WHERE c.name LIKE ? OR p.method LIKE ? OR p.status LIKE ? OR p.reference_no LIKE ? " +
                        "ORDER BY p.payment_date DESC",
                "%" +id  + "%", "%" + id + "%", "%" + id+ "%", "%" + id+ "%"
        );
        while (rs.next()) {
            Payment entity= new Payment(
                    rs.getInt("payment_id"),
                    rs.getInt("order_id"),
                    rs.getString("payment_date"),
                    rs.getDouble("amount"),
                    rs.getString("method"),
                    rs.getString("status"),
                    rs.getString("reference_no"),
                    rs.getString("notes")
            );
           entity.setCustomerName(rs.getString("customer_name"));
            list.add(entity);
        }
        return list;
    }
}
