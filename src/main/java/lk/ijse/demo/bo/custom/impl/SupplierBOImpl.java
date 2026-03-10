package lk.ijse.demo.bo.custom.impl;

import lk.ijse.demo.bo.custom.SupplierBO;
import lk.ijse.demo.dao.DAOFactory;
import lk.ijse.demo.dao.custom.SalesOrderDAO;
import lk.ijse.demo.dao.custom.SupplierDAO;
import lk.ijse.demo.dto.CustomerDTO;
import lk.ijse.demo.dto.PaymentDTO;
import lk.ijse.demo.dto.SalesOrderDTO;
import lk.ijse.demo.dto.SupplierDTO;
import lk.ijse.demo.entity.Customer;
import lk.ijse.demo.entity.SalesOrder;
import lk.ijse.demo.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierBOImpl implements SupplierBO {
    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SUPPLIER);

    public List<SupplierDTO> getAllSupplier() throws SQLException {
        List<Supplier> entities = supplierDAO.getAll();
        List<SupplierDTO> list = new ArrayList<>();

        for(Supplier entity : entities){
            list.add(new SupplierDTO(entity.getSupplierId(), entity.getName(), entity.getContactPerson(), entity.getPhone(), entity.getEmail(),entity.getAddress()));
        }

        return list;
    }

    public  boolean saveSupplier(SupplierDTO dto) throws SQLException {

        return supplierDAO.save(new Supplier(dto.getSupplierId(),dto.getName(), dto.getContactPerson(), dto.getPhone(), dto.getEmail(),dto.getAddress()));
    }

    public  boolean updateSupplier(SupplierDTO dto) throws SQLException {
        return supplierDAO.update(new Supplier(dto.getSupplierId(),dto.getName(),dto.getContactPerson(), dto.getPhone(), dto.getEmail(), dto.getAddress()));
    }

    public boolean deleteSupplier(int id) throws SQLException {
        return supplierDAO.delete(id);


    }
}
