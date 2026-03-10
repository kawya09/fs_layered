package lk.ijse.demo.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.demo.bo.BOFactory;
import lk.ijse.demo.bo.custom.CustomerBO;
import lk.ijse.demo.bo.custom.DeliveryBO;
import lk.ijse.demo.dto.DeliveryDTO;
import lk.ijse.demo.dto.SalesOrderDTO;
import lk.ijse.demo.entity.Delivery;
import lk.ijse.demo.model.DeliveryModel;
import lk.ijse.demo.model.SalesOrderModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class DeliveryController implements Initializable {

    @FXML private TextField txtDeliveryId, txtCustomerName, txtFee, txtDriver, txtSearch;
    @FXML private TextArea txtAddress, txtNotes;
    @FXML private ComboBox<String> cmbOrderId, cmbStatus;
    @FXML private DatePicker dpScheduled, dpDelivered;

    @FXML private TableView<DeliveryDTO> tblDeliveries;
    @FXML private TableColumn<DeliveryDTO, Integer> colId, colOrderId;
    @FXML private TableColumn<DeliveryDTO, String> colCustomer, colAddress, colScheduled, colDriver, colStatus;
    @FXML private TableColumn<DeliveryDTO, Double> colFee;

    private DeliveryBO deliveryBO = (DeliveryBO)  BOFactory.getInstance().getBO(BOFactory.BOType.DELIVERY);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("deliveryId"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colCustomer.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("deliveryAddress"));
        colScheduled.setCellValueFactory(new PropertyValueFactory<>("scheduledDate"));
        colDriver.setCellValueFactory(new PropertyValueFactory<>("driverName"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("deliveryFee"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        cmbStatus.setItems(FXCollections.observableArrayList("scheduled", "in-transit", "delivered", "failed"));
        loadOrderIds();
        loadDeliveries();
    }

    private void loadOrderIds() {
        try {
            deliveryOrders = salesOrderModel.getAllOrders().stream()
                    .filter(o -> "delivery".equals(o.getSaleType())).toList();
            cmbOrderId.setItems(FXCollections.observableArrayList(
                    deliveryOrders.stream().map(o -> String.valueOf(o.getOrderId())).toList()
            ));
            cmbOrderId.setOnAction(e -> {
                String selected = cmbOrderId.getValue();
                if (selected != null) {
                    deliveryOrders.stream()
                            .filter(o -> String.valueOf(o.getOrderId()).equals(selected))
                            .findFirst()
                            .ifPresent(o -> txtCustomerName.setText(o.getCustomerName()));
                }
            });
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    private void loadDeliveries() {
        try {
            tblDeliveries.setItems(FXCollections.observableArrayList(deliveryModel.getAllDeliveries()));
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML void saveDelivery() {
        if (cmbOrderId.getValue() == null || txtAddress.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Order ID and Address are required!"); return;
        }
        try {
            DeliveryDTO dto = buildDTO(0);
            if (deliveryModel.saveDelivery(dto)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Delivery scheduled!");
                clearForm(); loadDeliveries();
            }
        } catch (SQLException | NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML void updateDelivery() {
        if (txtDeliveryId.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Select a delivery to update!"); return;
        }
        try {
            DeliveryDTO dto = buildDTO(Integer.parseInt(txtDeliveryId.getText()));
            if (deliveryModel.updateDelivery(dto)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Delivery updated!");
                clearForm(); loadDeliveries();
            }
        } catch (SQLException | NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML void clearForm() {
        txtDeliveryId.clear(); txtCustomerName.clear(); txtAddress.clear();
        txtFee.clear(); txtDriver.clear(); txtNotes.clear();
        cmbOrderId.setValue(null); cmbStatus.setValue(null);
        dpScheduled.setValue(null); dpDelivered.setValue(null);
        tblDeliveries.getSelectionModel().clearSelection();
    }

    @FXML void searchDelivery() {
        try {
            String kw = txtSearch.getText().trim();
            tblDeliveries.setItems(FXCollections.observableArrayList(
                    kw.isEmpty() ? deliveryModel.getAllDeliveries() : deliveryModel.searchDeliveries(kw)));
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML void onTableRowClick(MouseEvent event) {
        DeliveryDTO d = tblDeliveries.getSelectionModel().getSelectedItem();
        if (d != null) {
            txtDeliveryId.setText(String.valueOf(d.getDeliveryId()));
            cmbOrderId.setValue(String.valueOf(d.getOrderId()));
            txtCustomerName.setText(d.getCustomerName());
            txtAddress.setText(d.getDeliveryAddress());
            txtFee.setText(String.valueOf(d.getDeliveryFee()));
            txtDriver.setText(d.getDriverName());
            cmbStatus.setValue(d.getStatus());
            txtNotes.setText(d.getNotes());
            if (d.getScheduledDate() != null) dpScheduled.setValue(LocalDate.parse(d.getScheduledDate()));
            if (d.getDeliveredDate() != null) dpDelivered.setValue(LocalDate.parse(d.getDeliveredDate()));
        }
    }

    private DeliveryDTO buildDTO(int id) {
        return new DeliveryDTO(id,
                Integer.parseInt(cmbOrderId.getValue()),
                txtAddress.getText(),
                dpScheduled.getValue() != null ? dpScheduled.getValue().toString() : null,
                dpDelivered.getValue() != null ? dpDelivered.getValue().toString() : null,
                Double.parseDouble(txtFee.getText().isEmpty() ? "0" : txtFee.getText()),
                cmbStatus.getValue() != null ? cmbStatus.getValue() : "scheduled",
                txtDriver.getText(),
                txtNotes.getText()
        );
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type, msg, ButtonType.OK);
        alert.setTitle(title); alert.setHeaderText(null); alert.showAndWait();
    }
}
