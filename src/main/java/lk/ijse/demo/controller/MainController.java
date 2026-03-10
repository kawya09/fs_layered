package lk.ijse.demo.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML private StackPane contentPane;
    @FXML private Button btnDashboard, btnCustomers, btnSuppliers, btnCategories;
    @FXML private Button btnItems, btnSales, btnPayments, btnDeliveries;

    private Button activeButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showDashboard();
    }

    @FXML
    void showDashboard() {
        loadView("/lk/ijse/demo/view/DashboardView.fxml");
        setActiveButton(btnDashboard);
    }

    @FXML
    void showCustomers() {
        loadView("/lk/ijse/demo/view/CustomerView.fxml");
        setActiveButton(btnCustomers);
    }

    @FXML
    void showSuppliers() {
        loadView("/lk/ijse/demo/view/SupplierView.fxml");
        setActiveButton(btnSuppliers);
    }

    @FXML
    void showCategories() {
        loadView("/lk/ijse/demo/view/CategoryView.fxml");
        setActiveButton(btnCategories);
    }

    @FXML
    void showItems() {
        loadView("/lk/ijse/demo/view/ItemView.fxml");
        setActiveButton(btnItems);
    }

    @FXML
    void showSales() {
        loadView("/lk/ijse/demo/view/SalesOrderView.fxml");
        setActiveButton(btnSales);
    }

    @FXML
    void showPayments() {
        loadView("/lk/ijse/demo/view/PaymentView.fxml");
        setActiveButton(btnPayments);
    }

    @FXML
    void showDeliveries() {
        loadView("/lk/ijse/demo/view/DeliveryView.fxml");
        setActiveButton(btnDeliveries);
    }

    private void loadView(String fxmlPath) {
        try {
            Node view = FXMLLoader.load(getClass().getResource(fxmlPath));
            contentPane.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setActiveButton(Button button) {
        if (activeButton != null) {
            activeButton.getStyleClass().remove("nav-button-active");
        }
        button.getStyleClass().add("nav-button-active");
        activeButton = button;
    }
}
