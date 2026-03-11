package lk.ijse.demo.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.demo.bo.BOFactory;
import lk.ijse.demo.bo.custom.CustomerBO;
import lk.ijse.demo.bo.custom.ItemBO;
import lk.ijse.demo.dto.CategoryDTO;
import lk.ijse.demo.dto.ItemDTO;
import lk.ijse.demo.dto.SupplierDTO;
import lk.ijse.demo.entity.Item;
//import lk.ijse.demo.model.CategoryModel;
//import lk.ijse.demo.model.ItemModel;
//import lk.ijse.demo.model.SupplierModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ItemController implements Initializable {

    @FXML private TextField txtItemId, txtName, txtUnitCost, txtUnitPrice, txtStock, txtSearch;
    @FXML private TextArea txtDescription;
    @FXML private ComboBox<CategoryDTO> cmbCategory;
    @FXML private ComboBox<SupplierDTO> cmbSupplier;

    @FXML private TableView<ItemDTO> tblItems;
    @FXML private TableColumn<ItemDTO, Integer> colId, colStock;
    @FXML private TableColumn<ItemDTO, String> colName, colCategory, colSupplier;
    @FXML private TableColumn<ItemDTO, Double> colCost, colPrice;

    private ItemBO itemBO = (ItemBO) BOFactory.getInstance().getBO(BOFactory.BOType.ITEM);
                                                                            @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        colSupplier.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("unitCost"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stockQuantity"));
        loadCombos();
        loadItems();
    }

    private void loadCombos() {
        try {
            List<CategoryDTO> cats = categoryModel.getAllCategories();
            cmbCategory.setItems(FXCollections.observableArrayList(cats));

            List<SupplierDTO> sups = supplierModel.getAllSuppliers();
            cmbSupplier.setItems(FXCollections.observableArrayList(sups));
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load combos: " + e.getMessage());
        }
    }

    private void loadItems() {
        try {
            tblItems.setItems(FXCollections.observableArrayList(itemBO.getAllItems()));
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML void saveItem() {
        if (txtName.getText().isEmpty() || cmbCategory.getValue() == null || cmbSupplier.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Name, Category, and Supplier are required!"); return;
        }
        try {
            ItemDTO dto = buildDTO(0);
            if (itemBO.saveItem(dto)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Item saved!");
                clearForm(); loadItems();
            }
        } catch (SQLException | NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML void updateItem() {
        if (txtItemId.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Select an item to update!"); return;
        }
        try {
            ItemDTO dto = buildDTO(Integer.parseInt(txtItemId.getText()));
            if (itemBO.updateItem(dto)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Item updated!");
                clearForm(); loadItems();
            }
        } catch (SQLException | NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML void deleteItem() {
        if (txtItemId.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Select an item to delete!"); return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Delete this item?", ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText(null);
        confirm.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.YES) {
                try {
                    itemBO.deleteItem(Integer.parseInt(txtItemId.getText()));
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Item deleted!");
                    clearForm(); loadItems();
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
                }
            }
        });
    }

    @FXML void clearForm() {
        txtItemId.clear(); txtName.clear(); txtUnitCost.clear();
        txtUnitPrice.clear(); txtStock.clear(); txtDescription.clear();
        cmbCategory.setValue(null); cmbSupplier.setValue(null);
        tblItems.getSelectionModel().clearSelection();
    }

    @FXML void searchItem() {
        try {
            String kw = txtSearch.getText().trim();
            tblItems.setItems(FXCollections.observableArrayList(
                    kw.isEmpty() ? itemBO.getAllItems() : itemBO.searchItems(kw)));
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML void onTableRowClick(MouseEvent event) {
        ItemDTO item = tblItems.getSelectionModel().getSelectedItem();
        if (item != null) {
            txtItemId.setText(String.valueOf(item.getItemId()));
            txtName.setText(item.getName());
            txtUnitCost.setText(String.valueOf(item.getUnitCost()));
            txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
            txtStock.setText(String.valueOf(item.getStockQuantity()));
            txtDescription.setText(item.getDescription());

            cmbCategory.getItems().stream()
                    .filter(c -> c.getCategoryId() == item.getCategoryId())
                    .findFirst().ifPresent(cmbCategory::setValue);

            cmbSupplier.getItems().stream()
                    .filter(s -> s.getSupplierId() == item.getSupplierId())
                    .findFirst().ifPresent(cmbSupplier::setValue);
        }
    }

    private ItemDTO buildDTO(int id) {
        return new ItemDTO(id,
                cmbSupplier.getValue().getSupplierId(),
                cmbCategory.getValue().getCategoryId(),
                txtName.getText(),
                txtDescription.getText(),
                Double.parseDouble(txtUnitCost.getText().isEmpty() ? "0" : txtUnitCost.getText()),
                Double.parseDouble(txtUnitPrice.getText().isEmpty() ? "0" : txtUnitPrice.getText()),
                Integer.parseInt(txtStock.getText().isEmpty() ? "0" : txtStock.getText())
        );
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type, msg, ButtonType.OK);
        alert.setTitle(title); alert.setHeaderText(null); alert.showAndWait();
    }
}
