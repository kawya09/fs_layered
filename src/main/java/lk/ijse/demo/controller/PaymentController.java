package lk.ijse.demo.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.demo.bo.BOFactory;
import lk.ijse.demo.bo.custom.CustomerBO;
import lk.ijse.demo.bo.custom.PaymentBO;
import lk.ijse.demo.dto.PaymentDTO;
import lk.ijse.demo.dto.SalesOrderDTO;
import lk.ijse.demo.entity.Payment;
import lk.ijse.demo.model.PaymentModel;
import lk.ijse.demo.model.SalesOrderModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    @FXML private ComboBox<String> cmbOrderId, cmbMethod, cmbStatus;
    @FXML private TextField txtCustomerName, txtAmount, txtReference, txtSearch;
    @FXML private TextArea txtNotes;
    @FXML private Label lblTotalRevenue;

    @FXML private TableView<PaymentDTO> tblPayments;
    @FXML private TableColumn<PaymentDTO, Integer> colId, colOrderId;
    @FXML private TableColumn<PaymentDTO, String> colCustomer, colDate, colMethod, colStatus, colRef;
    @FXML private TableColumn<PaymentDTO, Double> colAmount;

    private PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colCustomer.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colMethod.setCellValueFactory(new PropertyValueFactory<>("method"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colRef.setCellValueFactory(new PropertyValueFactory<>("referenceNo"));

        cmbMethod.setItems(FXCollections.observableArrayList("cash", "card", "bank_transfer", "online"));
        cmbStatus.setItems(FXCollections.observableArrayList("pending", "completed", "failed", "refunded"));

        loadOrderIds();
        loadPayments();
    }

    private void loadOrderIds() {
        try {
            allOrders = salesOrderModel.getAllOrders();
            cmbOrderId.setItems(FXCollections.observableArrayList(
                    allOrders.stream().map(o -> String.valueOf(o.getOrderId())).toList()
            ));
            cmbOrderId.setOnAction(e -> {
                String selected = cmbOrderId.getValue();
                if (selected != null) {
                    allOrders.stream()
                            .filter(o -> String.valueOf(o.getOrderId()).equals(selected))
                            .findFirst()
                            .ifPresent(o -> {
                                txtCustomerName.setText(o.getCustomerName());
                                txtAmount.setText(String.format("%.2f", o.getTotalAmount()));
                            });
                }
            });
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    private void loadPayments() {
        try {
            tblPayments.setItems(FXCollections.observableArrayList(paymentBO.getAllPayments()));
            lblTotalRevenue.setText(String.format("Rs. %.2f", paymentBO.getTotalRevenue()));
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML void savePayment() {
        if (cmbOrderId.getValue() == null || txtAmount.getText().isEmpty() || cmbMethod.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Order, Amount, and Method are required!"); return;
        }
        try {
            PaymentDTO dto = new PaymentDTO(0,
                    Integer.parseInt(cmbOrderId.getValue()),
                    null,
                    Double.parseDouble(txtAmount.getText()),
                    cmbMethod.getValue(),
                    cmbStatus.getValue() != null ? cmbStatus.getValue() : "completed",
                    txtReference.getText(),
                    txtNotes.getText()
            );
            if (paymentBO.savePayment(dto)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Payment recorded!");
                clearForm(); loadPayments();
            }
        } catch (SQLException | NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML void clearForm() {
        cmbOrderId.setValue(null); txtCustomerName.clear(); txtAmount.clear();
        cmbMethod.setValue(null); cmbStatus.setValue(null);
        txtReference.clear(); txtNotes.clear();
        tblPayments.getSelectionModel().clearSelection();
    }

    @FXML void searchPayment() {
        try {
            String kw = txtSearch.getText().trim();
            tblPayments.setItems(FXCollections.observableArrayList(
                    kw.isEmpty() ? paymentModel.getAllPayments() : paymentModel.searchPayments(kw)));
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML void onTableRowClick(MouseEvent event) {
        PaymentDTO p = tblPayments.getSelectionModel().getSelectedItem();
        if (p != null) {
            cmbOrderId.setValue(String.valueOf(p.getOrderId()));
            txtCustomerName.setText(p.getCustomerName());
            txtAmount.setText(String.valueOf(p.getAmount()));
            cmbMethod.setValue(p.getMethod());
            cmbStatus.setValue(p.getStatus());
            txtReference.setText(p.getReferenceNo());
            txtNotes.setText(p.getNotes());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type, msg, ButtonType.OK);
        alert.setTitle(title); alert.setHeaderText(null); alert.showAndWait();
    }
}
