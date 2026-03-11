package lk.ijse.demo.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.demo.bo.BOFactory;
import lk.ijse.demo.bo.custom.CustomerBO;
import lk.ijse.demo.dao.custom.CustomerDAO;
import lk.ijse.demo.dto.CustomerDTO;
//import lk.ijse.demo.model.CustomerModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    @FXML private TextField txtCustomerId, txtName, txtPhone, txtEmail, txtSearch;
    @FXML private TextArea txtAddress;

    @FXML private TableView<CustomerDTO> tblCustomers;
    @FXML private TableColumn<CustomerDTO, Integer> colId;
    @FXML private TableColumn<CustomerDTO, String> colName, colPhone, colEmail, colAddress;

  private CustomerBO customerBO = (CustomerBO) BOFactory.getInstance().getBO(BOFactory.BOType.CUSTOMER);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        loadCustomers();
    }

    private void loadCustomers() {
        try {
            tblCustomers.setItems(FXCollections.observableArrayList(customerBO.getAllCustomers()));
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load customers: " + e.getMessage());
        }
    }

    @FXML
    void saveCustomer() {
        if (txtName.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Customer name is required!");
            return;
        }
        try {
            CustomerDTO dto = new CustomerDTO(0, txtName.getText(), txtPhone.getText(),
                    txtEmail.getText(), txtAddress.getText());
            if (customerBO.saveCustomer(dto)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Customer saved successfully!");
                clearForm();
                loadCustomers();
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to save customer: " + e.getMessage());
        }
    }

    @FXML
    void updateCustomer() {
        if (txtCustomerId.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Select a customer to update!");
            return;
        }
        try {
            CustomerDTO dto = new CustomerDTO(
                    Integer.parseInt(txtCustomerId.getText()), txtName.getText(),
                    txtPhone.getText(), txtEmail.getText(), txtAddress.getText()
            );
            if (customerBO.updateCustomer(dto)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Customer updated successfully!");
                clearForm();
                loadCustomers();
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update customer: " + e.getMessage());
        }
    }

    @FXML
    void deleteCustomer() {
        if (txtCustomerId.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Select a customer to delete!");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this customer?", ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText(null);
        confirm.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.YES) {
                try {
                    if (customerBO.deleteCustomer(Integer.parseInt(txtCustomerId.getText()))) {
                        showAlert(Alert.AlertType.INFORMATION, "Success", "Customer deleted!");
                        clearForm();
                        loadCustomers();
                    }
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete: " + e.getMessage());
                }
            }
        });
    }

    @FXML
    void clearForm() {
        txtCustomerId.clear();
        txtName.clear();
        txtPhone.clear();
        txtEmail.clear();
        txtAddress.clear();
        tblCustomers.getSelectionModel().clearSelection();
    }

    @FXML
    void searchCustomer() {
        try {
            String keyword = txtSearch.getText().trim();
            if (keyword.isEmpty()) {
                loadCustomers();
            } else {
                tblCustomers.setItems(FXCollections.observableArrayList(customerBO.search(keyword)));
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML
    void onTableRowClick(MouseEvent event) {
        CustomerDTO selected = tblCustomers.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtCustomerId.setText(String.valueOf(selected.getCustomerId()));
            txtName.setText(selected.getName());
            txtPhone.setText(selected.getPhone());
            txtEmail.setText(selected.getEmail());
            txtAddress.setText(selected.getAddress());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type, msg, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
