package lk.ijse.demo.bo.custom;

import lk.ijse.demo.bo.SuperBO;
import lk.ijse.demo.dto.ItemDTO;
import lk.ijse.demo.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ItemBO extends SuperBO {
    public List<ItemDTO> getAllItems() throws SQLException ;

    public  boolean saveItem(ItemDTO dto) throws SQLException ;

    public  boolean updateItem(ItemDTO dto) throws SQLException ;

    public  boolean deleteItem(int id) throws SQLException ;
}
