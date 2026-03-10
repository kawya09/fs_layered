package lk.ijse.demo.bo.custom.impl;

import lk.ijse.demo.bo.custom.DeliveryBO;
import lk.ijse.demo.dao.DAOFactory;
import lk.ijse.demo.dao.custom.CustomerDAO;
import lk.ijse.demo.dao.custom.DeliveryDAO;
import lk.ijse.demo.dao.custom.ItemDAO;
import lk.ijse.demo.dto.CategoryDTO;
import lk.ijse.demo.dto.CustomerDTO;
import lk.ijse.demo.dto.DeliveryDTO;
import lk.ijse.demo.dto.ItemDTO;
import lk.ijse.demo.entity.Category;
import lk.ijse.demo.entity.Customer;
import lk.ijse.demo.entity.Delivery;
import lk.ijse.demo.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeliveryBOImpl implements DeliveryBO {
    DeliveryDAO deliveryDAO = (DeliveryDAO)  DAOFactory.getInstance().getDAO(DAOFactory.DAOType.DELIVERY);

    public List<DeliveryDTO> getAllDeliveries() throws SQLException {
        List<Delivery> entities = deliveryDAO.getAll();
        List<DeliveryDTO> list = new ArrayList<>();

        for(Delivery entity : entities){
            list.add(new DeliveryDTO(entity.getDeliveryId(),entity.getOrderId(),entity.getDeliveryAddress(),entity.getScheduledDate(),entity.getDeliveredDate(),entity.getDeliveryFee(),entity.getStatus(),entity.getDriverName(),entity.getNotes()));
        }

        return list;
    }
    public  boolean saveDelivery(DeliveryDTO dto) throws SQLException {

        return deliveryDAO.save(new Delivery(dto.getDeliveryId(),dto.getOrderId(), dto.getDeliveryAddress(), dto.getScheduledDate(), dto.getDeliveredDate(),dto.getDeliveryFee(),dto.getStatus(),dto.getDriverName(),dto.getNotes()));
    }

    public boolean updateDelivery(DeliveryDTO dto) throws SQLException {

        return deliveryDAO.update(new Delivery(dto.getDeliveryId(),dto.getOrderId(), dto.getDeliveryAddress(), dto.getScheduledDate(), dto.getDeliveredDate(),dto.getDeliveryFee(),dto.getStatus(),dto.getDriverName(),dto.getNotes()));

    }
}
