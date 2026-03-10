package lk.ijse.demo.bo.custom.impl;

import lk.ijse.demo.bo.custom.CategoryBO;
import lk.ijse.demo.dao.DAOFactory;
import lk.ijse.demo.dao.custom.CategoryDAO;
import lk.ijse.demo.dto.CategoryDTO;
import lk.ijse.demo.entity.Category;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryBOImpl implements CategoryBO {

    CategoryDAO categoryDAO = (CategoryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CATEGORY);

    public List<CategoryDTO> getAllCategories() throws SQLException {

            List<Category> entities = categoryDAO.getAll();
            List<CategoryDTO> list = new ArrayList<>();

            for(Category entity : entities){
                list.add(new CategoryDTO(entity.getCategoryId(), entity.getName(), entity.getDescription()));
            }

            return list;

    }

    public  boolean saveCategory(CategoryDTO dto) throws SQLException {

            return categoryDAO.save(new Category(dto.getCategoryId(),dto.getName(), dto.getDescription()));
    }

    public boolean updateCategory(CategoryDTO dto) throws SQLException {


                return categoryDAO.update(new Category(dto.getCategoryId(), dto.getName(), dto.getDescription()));

    }

    public boolean deleteCategory(int id) throws SQLException {


            return categoryDAO.delete(id);


    }
}
