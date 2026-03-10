package lk.ijse.demo.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.demo.dto.SupplierDTO;
import lk.ijse.demo.model.SupplierModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SupplierController implements Initializable {

    @FXML private TextField txtSupplierId, txtName, txtContactPerson, txtPhone, txtEmail, txtSearch;
    @FXML private TextArea txtAddress;

    @FXML private TableView<SupplierDTO> tblSuppliers;
    @FXML private TableColumn<SupplierDTO, Integer> colId;
    @FXML private TableColumn<SupplierDTO, String> colName, colContact, colPhone, colEmail, colAddress;

    private final SupplierModel supplierModel = new SupplierModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contactPerson"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        loadSuppliers();
    }

    private void loadSuppliers() {
        try {
            tblSuppliers.setItems(FXCollections.observableArrayList(supplierModel.getAllSuppliers()));
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load suppliers: " + e.getMessage());
        }
    }

    @FXML void saveSupplier() {
        if (txtName.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Company name is required!");
            return;
        }
        try {
            SupplierDTO dto = new SupplierDTO(0, txtName.getText(), txtContactPerson.getText(),
                    txtPhone.getText(), txtEmail.getText(), txtAddress.getText());
            if (supplierModel.saveSupplier(dto)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Supplier saved!");
                clearForm(); loadSuppliers();
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML void updateSupplier() {
        if (txtSupplierId.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Select a supplier to update!"); return;
        }
        try {
            SupplierDTO dto = new SupplierDTO(Integer.parseInt(txtSupplierId.getText()),
                    txtName.getText(), txtContactPerson.getText(), txtPhone.getText(),
                    txtEmail.getText(), txtAddress.getText());
            if (supplierModel.updateSupplier(dto)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Supplier updated!");
                clearForm(); loadSuppliers();
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML void deleteSupplier() {
        if (txtSupplierId.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Select a supplier to delete!"); return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Delete this supplier?", ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText(null);
        confirm.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.YES) {
                try {
                    supplierModel.deleteSupplier(Integer.parseInt(txtSupplierId.getText()));
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Supplier deleted!");
                    clearForm(); loadSuppliers();
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
                }
            }
        });
    }

    @FXML void clearForm() {
        txtSupplierId.clear(); txtName.clear(); txtContactPerson.clear();
        txtPhone.clear(); txtEmail.clear(); txtAddress.clear();
        tblSuppliers.getSelectionModel().clearSelection();
    }

    @FXML void searchSupplier() {
        try {
            String kw = txtSearch.getText().trim();
            tblSuppliers.setItems(FXCollections.observableArrayList(
                    kw.isEmpty() ? supplierModel.getAllSuppliers() : supplierModel.searchSuppliers(kw)));
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML void onTableRowClick(MouseEvent event) {
        SupplierDTO s = tblSuppliers.getSelectionModel().getSelectedItem();
        if (s != null) {
            txtSupplierId.setText(String.valueOf(s.getSupplierId()));
            txtName.setText(s.getName());
            txtContactPerson.setText(s.getContactPerson());
            txtPhone.setText(s.getPhone());
            txtEmail.setText(s.getEmail());
            txtAddress.setText(s.getAddress());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type, msg, ButtonType.OK);
        alert.setTitle(title); alert.setHeaderText(null); alert.showAndWait();
    }
}
