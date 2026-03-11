package lk.ijse.demo.dao.custom.impl;

import lk.ijse.demo.dao.CrudUtil;
import lk.ijse.demo.dao.custom.ItemDAO;
import lk.ijse.demo.dto.ItemDTO;
import lk.ijse.demo.entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public boolean save(Item entity) throws SQLException {
            return CrudUtil.execute(
                    "INSERT INTO items (supplier_id, category_id, name, description, unit_cost, unit_price, stock_quantity) VALUES (?,?,?,?,?,?,?)",
                    entity.getSupplierId(), entity.getCategoryId(), entity.getName(), entity.getDescription(),
                    entity.getUnitCost(), entity.getUnitPrice(), entity.getStockQuantity()
            );
    }

    @Override
    public boolean update(Item entity) throws SQLException {
        return CrudUtil.execute(
                "UPDATE items SET supplier_id=?, category_id=?, name=?, description=?, unit_cost=?, unit_price=?, stock_quantity=? WHERE item_id=?",
                entity.getSupplierId(), entity.getCategoryId(), entity.getName(), entity.getDescription(),
                entity.getUnitCost(), entity.getUnitPrice(), entity.getStockQuantity(), entity.getItemId()
        );
    }

    @Override
    public boolean delete(int Id) throws SQLException {
        return CrudUtil.execute("DELETE FROM items WHERE item_id=?", Id);
    }

    @Override
    public List<Item> getAll() throws SQLException {
        List<Item> list = new ArrayList<>();
        ResultSet rs = CrudUtil.execute(
                "SELECT i.*, s.name AS supplier_name, c.name AS category_name " +
                        "FROM items i " +
                        "JOIN suppliers s ON i.supplier_id = s.supplier_id " +
                        "JOIN categories c ON i.category_id = c.category_id " +
                        "ORDER BY i.name"
        );
        while (rs.next()) {
            Item entity = new Item(
                    rs.getInt("item_id"),
                    rs.getInt("supplier_id"),
                    rs.getInt("category_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("unit_cost"),
                    rs.getDouble("unit_price"),
                    rs.getInt("stock_quantity")
            );
            entity.setSupplierName(rs.getString("supplier_name"));
            entity.setCategoryName(rs.getString("category_name"));
            list.add(entity);
        }
        return list;
    }

    @Override
    public List<Item> search(String id) throws SQLException {
        List<Item> list = new ArrayList<>();
        ResultSet rs = CrudUtil.execute(
                "SELECT i.*, s.name AS supplier_name, c.name AS category_name " +
                        "FROM items i " +
                        "JOIN suppliers s ON i.supplier_id = s.supplier_id " +
                        "JOIN categories c ON i.category_id = c.category_id " +
                        "WHERE i.name LIKE ? OR c.name LIKE ?",
                "%" + id + "%", "%" + id + "%"
        );
        while (rs.next()) {
            Item entity = new Item(
                    rs.getInt("item_id"),
                    rs.getInt("supplier_id"),
                    rs.getInt("category_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("unit_cost"),
                    rs.getDouble("unit_price"),
                    rs.getInt("stock_quantity")
            );
            entity.setSupplierName(rs.getString("supplier_name"));
            entity.setCategoryName(rs.getString("category_name"));
            list.add(entity);
        }
        return list;
    }

    public int getTotalItems() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT COUNT(*) AS cnt FROM items");
        if (rs.next()) return rs.getInt("cnt");
        return 0;
    }

    public int getLowStockCount() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT COUNT(*) AS cnt FROM items WHERE stock_quantity < 5");
        if (rs.next()) return rs.getInt("cnt");
        return 0;
    }
}
