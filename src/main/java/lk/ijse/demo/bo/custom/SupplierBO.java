package lk.ijse.demo.bo.custom;

import lk.ijse.demo.bo.SuperBO;
import lk.ijse.demo.dto.SupplierDTO;
import lk.ijse.demo.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface SupplierBO extends SuperBO {
    public List<SupplierDTO> getAllSupplier() throws SQLException ;

    public  boolean saveSupplier(SupplierDTO dto) throws SQLException ;

    public  boolean updateSupplier(SupplierDTO dto) throws SQLException ;

    public boolean deleteSupplier(int id) throws SQLException ;
}

