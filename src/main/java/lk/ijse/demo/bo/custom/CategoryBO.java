package lk.ijse.demo.bo.custom;

import lk.ijse.demo.bo.SuperBO;
import lk.ijse.demo.dto.CategoryDTO;
import lk.ijse.demo.entity.Category;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CategoryBO extends SuperBO {

    public List<CategoryDTO> getAllCategories() throws SQLException;

    public  boolean saveCategory(CategoryDTO dto) throws SQLException;

    public boolean updateCategory(CategoryDTO dto) throws SQLException;

    public boolean deleteCategory(int id) throws SQLException;
}
