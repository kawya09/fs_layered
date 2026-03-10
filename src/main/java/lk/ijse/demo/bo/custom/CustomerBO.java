package lk.ijse.demo.bo.custom;

import lk.ijse.demo.bo.SuperBO;
import lk.ijse.demo.dto.CustomerDTO;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBO {
    public List<CustomerDTO> getAllCustomers() throws SQLException ;

    public  boolean saveCustomer(CustomerDTO dto) throws SQLException ;

    public  boolean updateCustomer(CustomerDTO dto) throws SQLException ;

    public boolean deleteCustomer(int id) throws SQLException ;

    public List<CustomerDTO> search(String id) throws SQLException ;



}
