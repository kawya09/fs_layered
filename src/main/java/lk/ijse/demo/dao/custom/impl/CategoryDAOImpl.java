package lk.ijse.demo.dao.custom.impl;

import lk.ijse.demo.dao.CrudUtil;
import lk.ijse.demo.dao.custom.CategoryDAO;
import lk.ijse.demo.dto.CategoryDTO;
import lk.ijse.demo.entity.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAOImpl implements CategoryDAO {

    @Override
    public boolean save(Category entity) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO categories (name, description) VALUES (?,?)",
                entity.getName(), entity.getDescription()
        );
    }

    @Override
    public boolean update(Category entity) throws SQLException {
        return CrudUtil.execute(
                "UPDATE categories SET name=?, description=? WHERE category_id=?",
              entity.getName(), entity.getDescription(), entity.getCategoryId()
        );
    }

    @Override
    public boolean delete(int Id) throws SQLException {
        return CrudUtil.execute("DELETE FROM categories WHERE category_id=?", Id);
    }

    @Override
    public List<Category> getAll() throws SQLException {
        List<Category> list = new ArrayList<>();
        ResultSet rs = CrudUtil.execute("SELECT * FROM categories ORDER BY name");
        while (rs.next()) {
            list.add(new Category(
                    rs.getInt("category_id"),
                    rs.getString("name"),
                    rs.getString("description")
            ));
        }
        return list;
    }

    @Override
    public List<Category> search(String id) throws SQLException {
        return List.of();
    }
}
