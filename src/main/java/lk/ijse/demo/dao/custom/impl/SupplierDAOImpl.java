package lk.ijse.demo.dao.custom.impl;

import lk.ijse.demo.dao.CrudUtil;
import lk.ijse.demo.dao.custom.SupplierDAO;
import lk.ijse.demo.dto.SupplierDTO;
import lk.ijse.demo.entity.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAOImpl implements SupplierDAO {
    @Override
    public boolean save(Supplier entity) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO suppliers (name, contact_person, phone, email, address) VALUES (?,?,?,?,?)",
                entity.getName(), entity.getContactPerson(), entity.getPhone(), entity.getEmail(), entity.getAddress()
        );
    }

    @Override
    public boolean update(Supplier entity) throws SQLException {
        return CrudUtil.execute(
                "UPDATE suppliers SET name=?, contact_person=?, phone=?, email=?, address=? WHERE supplier_id=?",
                entity.getName(), entity.getContactPerson(), entity.getPhone(),entity.getEmail(), entity.getAddress(), entity.getSupplierId()
        );
    }

    @Override
    public boolean delete(int Id) throws SQLException {
        return CrudUtil.execute("DELETE FROM suppliers WHERE supplier_id=?", Id);
    }

    @Override
    public List<Supplier> getAll() throws SQLException {
        List<Supplier> list = new ArrayList<>();
        ResultSet rs = CrudUtil.execute("SELECT * FROM suppliers ORDER BY name");
        while (rs.next()) {
            list.add(new Supplier(
                    rs.getInt("supplier_id"),
                    rs.getString("name"),
                    rs.getString("contact_person"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("address")
            ));
        }
        return list;
    }

    @Override
    public List<Supplier> search(String id) throws SQLException {
        List<Supplier> list = new ArrayList<>();
        ResultSet rs = CrudUtil.execute(
                "SELECT * FROM suppliers WHERE name LIKE ? OR phone LIKE ? OR email LIKE ?",
                "%" + id + "%", "%" + id + "%", "%" + id + "%"
        );
        while (rs.next()) {
            list.add(new Supplier(
                    rs.getInt("supplier_id"),
                    rs.getString("name"),
                    rs.getString("contact_person"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("address")
            ));
        }
        return list;
    }
}
