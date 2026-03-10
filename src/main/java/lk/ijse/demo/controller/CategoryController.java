package lk.ijse.demo.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.demo.bo.BOFactory;
import lk.ijse.demo.bo.custom.CategoryBO;
import lk.ijse.demo.dto.CategoryDTO;


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CategoryController implements Initializable {

    @FXML private TextField txtCategoryId, txtName;
    @FXML private TextArea txtDescription;

    @FXML private TableView<CategoryDTO> tblCategories;
    @FXML private TableColumn<CategoryDTO, Integer> colId;
    @FXML private TableColumn<CategoryDTO, String> colName, colDescription;

    private CategoryBO categoryBO = (CategoryBO) BOFactory.getInstance().getBO(BOFactory.BOType.Category);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        loadCategories();
    }

    private void loadCategories() {
        try {
            tblCategories.setItems(FXCollections.observableArrayList(categoryBO.getAllCategories()));
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML void saveCategory() {
        if (txtName.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Category name is required!"); return;
        }
        try {
            if (categoryBO.saveCategory(new CategoryDTO(0, txtName.getText(), txtDescription.getText()))) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Category saved!");
                clearForm(); loadCategories();
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML void updateCategory() {
        if (txtCategoryId.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Select a category to update!"); return;
        }
        try {
            if (categoryBO.updateCategory(new CategoryDTO(
                    Integer.parseInt(txtCategoryId.getText()), txtName.getText(), txtDescription.getText()))) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Category updated!");
                clearForm(); loadCategories();
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML void deleteCategory() {
        if (txtCategoryId.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Select a category to delete!"); return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Delete this category?", ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText(null);
        confirm.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.YES) {
                try {
                    categoryBO.deleteCategory(Integer.parseInt(txtCategoryId.getText()));
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Category deleted!");
                    clearForm(); loadCategories();
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
                }
            }
        });
    }

    @FXML void clearForm() {
        txtCategoryId.clear(); txtName.clear(); txtDescription.clear();
        tblCategories.getSelectionModel().clearSelection();
    }

    @FXML void onTableRowClick(MouseEvent event) {
        CategoryDTO c = tblCategories.getSelectionModel().getSelectedItem();
        if (c != null) {
            txtCategoryId.setText(String.valueOf(c.getCategoryId()));
            txtName.setText(c.getName());
            txtDescription.setText(c.getDescription());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type, msg, ButtonType.OK);
        alert.setTitle(title); alert.setHeaderText(null); alert.showAndWait();
    }
}
