package lk.ijse.demo.dao.custom;

import lk.ijse.demo.dao.CrudDAO;
import lk.ijse.demo.entity.Customer;

import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<Customer> {

    public int getTotalCustomers() throws SQLException;
}
