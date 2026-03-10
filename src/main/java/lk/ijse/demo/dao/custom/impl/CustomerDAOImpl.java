package lk.ijse.demo.dao.custom.impl;

import lk.ijse.demo.dao.CrudUtil;
import lk.ijse.demo.dao.custom.CustomerDAO;
import lk.ijse.demo.dto.CustomerDTO;
import lk.ijse.demo.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public boolean save(Customer entity) throws SQLException {
     return CrudUtil.execute(
             "INSERT INTO customers (name, phone,email, address) VALUES (?,?,?)",
             entity.getName(),entity.getPhone(),entity.getEmail(),entity.getAddress()
     );
    }

    @Override
    public boolean update(Customer entity) throws SQLException {
       return CrudUtil.execute(
               "UPDATE customers SET name=?,phone=?,email=?,address=? WHERE customer_id=?",
               entity.getName(),entity.getPhone(),entity.getEmail(),entity.getAddress(),entity.getCustomerId()
       );
    }

    @Override
    public boolean delete(int Id) throws SQLException {
      return CrudUtil.execute("DELETE FROM customers WHERE customer_id=?", Id);
    }

    @Override
    public List<Customer> getAll() throws SQLException {
        List<Customer> list = new ArrayList<>();
        ResultSet rs = CrudUtil.execute("SELECT * FROM customers ORDER BY name");
        while (rs.next()) {
            list.add(new Customer(
                    rs.getInt("customer_id"),
                    rs.getString("name"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("address")
            ));
        }
        return list;
    }

    @Override
    public List<Customer> search(String id) throws SQLException {
        List<Customer> list = new ArrayList<>();
        ResultSet rs = CrudUtil.execute(
                "SELECT * FROM customers WHERE name LIKE ? OR phone LIKE ? OR email LIKE ?",
                "%" + id + "%", "%" + id+ "%", "%" + id + "%"
        );
        while (rs.next()) {
            list.add(new Customer(
                    rs.getInt("customer_id"),
                    rs.getString("name"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("address")
            ));
        }
        return list;
    }
}
