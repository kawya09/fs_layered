package lk.ijse.demo.dao.custom;

import lk.ijse.demo.dao.CrudDAO;
import lk.ijse.demo.dao.CrudUtil;
import lk.ijse.demo.entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ItemDAO extends CrudDAO<Item> {

    public int getTotalItems() throws SQLException ;
    public int getLowStockCount() throws SQLException ;

}
