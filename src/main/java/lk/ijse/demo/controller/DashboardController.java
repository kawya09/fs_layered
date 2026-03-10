package lk.ijse.demo.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.demo.dto.SalesOrderDTO;
import lk.ijse.demo.model.DashboardModel;
import lk.ijse.demo.model.SalesOrderModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML private Label lblTotalCustomers, lblTotalItems, lblTotalOrders;
    @FXML private Label lblRevenue, lblPendingOrders, lblLowStock;

    @FXML private TableView<SalesOrderDTO> tblRecentOrders;
    @FXML private TableColumn<SalesOrderDTO, Integer> colOrderId;
    @FXML private TableColumn<SalesOrderDTO, String> colCustomer, colDate, colType, colStatus;
    @FXML private TableColumn<SalesOrderDTO, Double> colTotal;

    private final DashboardModel dashboardModel = new DashboardModel();
    private final SalesOrderModel salesOrderModel = new SalesOrderModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
        loadDashboard();
    }

    private void setupTable() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colCustomer.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colType.setCellValueFactory(new PropertyValueFactory<>("saleType"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    @FXML
    public void loadDashboard() {
        try {
            lblTotalCustomers.setText(String.valueOf(dashboardModel.getTotalCustomers()));
            lblTotalItems.setText(String.valueOf(dashboardModel.getTotalItems()));
            lblTotalOrders.setText(String.valueOf(dashboardModel.getTotalOrders()));
            lblRevenue.setText(String.format("Rs. %.2f", dashboardModel.getTotalRevenue()));
            lblPendingOrders.setText(String.valueOf(dashboardModel.getPendingOrders()));
            lblLowStock.setText(String.valueOf(dashboardModel.getLowStockCount()));

            List<SalesOrderDTO> orders = salesOrderModel.getAllOrders();
            tblRecentOrders.setItems(FXCollections.observableArrayList(
                    orders.size() > 10 ? orders.subList(0, 10) : orders
            ));
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load dashboard: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type, msg, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
