package lk.ijse.demo.dao.custom;

import lk.ijse.demo.dao.CrudDAO;
import lk.ijse.demo.entity.Payment;

import java.sql.SQLException;

public interface PaymentDAO extends CrudDAO<Payment> {
    public double getTotalRevenue() throws SQLException ;
}
