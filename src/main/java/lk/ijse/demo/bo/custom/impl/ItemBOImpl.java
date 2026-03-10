package lk.ijse.demo.bo.custom.impl;

import lk.ijse.demo.bo.custom.ItemBO;
import lk.ijse.demo.dao.DAOFactory;
import lk.ijse.demo.dao.custom.CustomerDAO;
import lk.ijse.demo.dao.custom.ItemDAO;
import lk.ijse.demo.dto.CustomerDTO;
import lk.ijse.demo.dto.ItemDTO;
import lk.ijse.demo.entity.Customer;
import lk.ijse.demo.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.delete;

public class ItemBOImpl implements ItemBO {
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ITEM);

    public List<ItemDTO> getAllItems() throws SQLException {
        List<Item> entities = itemDAO.getAll();
        List<ItemDTO> list = new ArrayList<>();

        for(Item entity : entities){
            list.add(new ItemDTO(entity.getItemId(), entity.getName(), entity.getSupplierId(), entity.getCategoryId(), entity.getDescription(),entity.getUnitCost(),entity.getUnitPrice(),entity.getStockQuantity()));
        }

        return list;
    }

    public  boolean saveItem(ItemDTO dto) throws SQLException {

        return itemDAO.save(new Item(dto.getItemId(),dto.getName(), dto.getSupplierId(), dto.getCategoryId(), dto.getDescription(),dto.getUnitCost(), dto.getUnitPrice(), dto.getStockQuantity()));
    }

    public  boolean updateItem(ItemDTO dto) throws SQLException {
        return itemDAO.update(new Item(dto.getItemId(),dto.getName(), dto.getSupplierId(), dto.getCategoryId(), dto.getDescription(),dto.getUnitCost(), dto.getUnitPrice(), dto.getStockQuantity()));
    }

    public  boolean deleteItem(int id) throws SQLException {
        return itemDAO.delete(id);
    }
    }



