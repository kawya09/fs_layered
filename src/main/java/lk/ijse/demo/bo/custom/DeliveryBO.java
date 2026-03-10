package lk.ijse.demo.bo.custom;

import lk.ijse.demo.bo.SuperBO;
import lk.ijse.demo.dto.DeliveryDTO;
import lk.ijse.demo.entity.Delivery;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface DeliveryBO extends SuperBO {
    public List<DeliveryDTO> getAllDeliveries() throws SQLException;
    public  boolean saveDelivery(DeliveryDTO dto) throws SQLException ;

    public boolean updateDelivery(DeliveryDTO dto) throws SQLException ;
}


