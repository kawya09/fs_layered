package lk.ijse.demo.bo.custom.impl;

import lk.ijse.demo.bo.custom.CustomerBO;
import lk.ijse.demo.dao.DAOFactory;
import lk.ijse.demo.dao.custom.CustomerDAO;
import lk.ijse.demo.dto.CustomerDTO;
import lk.ijse.demo.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CASTOMER);

    public List<CustomerDTO> getAllCustomers() throws SQLException {
        List<Customer> entities = customerDAO.getAll();
        List<CustomerDTO> list = new ArrayList<>();

        for(Customer entity : entities){
            list.add(new CustomerDTO(entity.getCustomerId(), entity.getName(), entity.getPhone(), entity.getEmail(), entity.getAddress()));
        }

        return list;
    }

    public  boolean saveCustomer(CustomerDTO dto) throws SQLException {

        return customerDAO.save(new Customer(dto.getCustomerId(),dto.getName(), dto.getPhone(), dto.getEmail(), dto.getAddress()));
    }

    public  boolean updateCustomer(CustomerDTO dto) throws SQLException {
        return customerDAO.update(new Customer(dto.getCustomerId(),dto.getName(), dto.getPhone(), dto.getEmail(), dto.getAddress()));
    }

    public boolean deleteCustomer(int id) throws SQLException {
        return customerDAO.delete(id);


    }

    @Override
    public List<CustomerDTO> search(String id) throws SQLException {
        List<Customer> entities = customerDAO.getAll();
        List<CustomerDTO> list = new ArrayList<>();

        for(Customer entity : entities){
            list.add(new CustomerDTO(entity.getCustomerId(), entity.getName(), entity.getPhone(), entity.getEmail(), entity.getAddress()));
        }

        return list;
    }
    }


