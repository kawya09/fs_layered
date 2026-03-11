package lk.ijse.demo.bo.custom.impl;

import lk.ijse.demo.bo.custom.DashboardBO;
import lk.ijse.demo.dao.CrudUtil;
import lk.ijse.demo.dao.DAOFactory;
import lk.ijse.demo.dao.custom.*;
import lk.ijse.demo.dto.CustomerDTO;
import lk.ijse.demo.dto.SalesOrderDTO;
import lk.ijse.demo.entity.Customer;
import lk.ijse.demo.entity.SalesOrder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DashboardBOImpl implements DashboardBO {
        CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CASTOMER);
        ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ITEM);
        SalesOrderDAO salesOrderDAO = (SalesOrderDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SALESORDER);
        PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);

    public int getTotalCustomers() throws SQLException {
        return customerDAO.getTotalCustomers();
    }

    public int getTotalItems() throws SQLException {
        return itemDAO.getTotalItems();
    }
    public int getTotalOrders() throws SQLException {
        return SalesOrderDAO.getTotalOrders();
    }

    public int getPendingOrders() throws SQLException {
       return salesOrderDAO.getPendingOrders();
    }
    public int getLowStockCount() throws SQLException {
        return itemDAO.getLowStockCount();
    }

    public double getTotalRevenue() throws SQLException {
       return paymentDAO.getTotalRevenue();
    }


        public List<SalesOrderDTO> getAllOrders() throws SQLException {
            List<SalesOrder> entities =salesOrderDAO.getAll();
            List<SalesOrderDTO> list = new ArrayList<>();

            for(SalesOrder entity : entities){
                list.add(new SalesOrderDTO(entity.getCustomerId(), entity.getOrderId(),entity.getOrderDate(),entity.getSaleType(),entity.getStatus(),entity.getNotes()));
            }

            return list;
        }
    }



