package lk.ijse.demo.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.demo.bo.BOFactory;
import lk.ijse.demo.bo.custom.CustomerBO;
import lk.ijse.demo.bo.custom.ItemBO;
import lk.ijse.demo.dto.CustomerDTO;
import lk.ijse.demo.dto.ItemDTO;
import lk.ijse.demo.dto.SalesOrderDTO;
import lk.ijse.demo.dto.SalesOrderItemDTO;
import lk.ijse.demo.entity.Item;
//import lk.ijse.demo.model.CustomerModel;
//import lk.ijse.demo.model.ItemModel;
//import lk.ijse.demo.model.SalesOrderModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SalesOrderController implements Initializable {

    @FXML private ComboBox<CustomerDTO> cmbCustomer;
    @FXML private ComboBox<String> cmbSaleType, cmbStatus, cmbUpdateStatus;
    @FXML private ComboBox<ItemDTO> cmbItem;
    @FXML private TextArea txtNotes;
    @FXML private TextField txtQty, txtSearch;
    @FXML private Label lblTotal;

    @FXML private TableView<SalesOrderItemDTO> tblCart;
    @FXML private TableColumn<SalesOrderItemDTO, String> colCartItem;
    @FXML private TableColumn<SalesOrderItemDTO, Integer> colCartQty;
    @FXML private TableColumn<SalesOrderItemDTO, Double> colCartPrice, colCartSubTotal;

    @FXML private TableView<SalesOrderDTO> tblOrders;
    @FXML private TableColumn<SalesOrderDTO, Integer> colOrderId;
    @FXML private TableColumn<SalesOrderDTO, String> colCustomer, colDate, colType, colStatus, colNotes;
    @FXML private TableColumn<SalesOrderDTO, Double> colTotal;

    private final ObservableList<SalesOrderItemDTO> cartItems = FXCollections.observableArrayList();
    private CustomerBO customerBO = (CustomerBO) BOFactory.getInstance().getBO(BOFactory.BOType.CUSTOMER);
    private ItemBO itemBO = (ItemBO) BOFactory.getInstance().getBO(BOFactory.BOType.ITEM);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupCartTable();
        setupOrdersTable();
        loadCombos();
        loadOrders();
    }

    private void setupCartTable() {
        colCartItem.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colCartQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colCartPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colCartSubTotal.setCellValueFactory(new PropertyValueFactory<>("subTotal"));
        tblCart.setItems(cartItems);
    }

    private void setupOrdersTable() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colCustomer.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colType.setCellValueFactory(new PropertyValueFactory<>("saleType"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colNotes.setCellValueFactory(new PropertyValueFactory<>("notes"));
    }

    private void loadCombos() {
        try {
            cmbCustomer.setItems(FXCollections.observableArrayList(customerBO.getAllCustomers()));
            cmbItem.setItems(FXCollections.observableArrayList(itemBO.getAllItems()));
            cmbSaleType.setItems(FXCollections.observableArrayList("in-store", "delivery"));
            cmbStatus.setItems(FXCollections.observableArrayList("pending", "confirmed", "delivered", "cancelled"));
            cmbUpdateStatus.setItems(FXCollections.observableArrayList("pending", "confirmed", "delivered", "cancelled"));
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    private void loadOrders() {
        try {
            tblOrders.setItems(FXCollections.observableArrayList(salesOrderBO.getAllOrders()));
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML void addItemToCart() {
        ItemDTO item = cmbItem.getValue();
        if (item == null || txtQty.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Select an item and enter quantity!"); return;
        }
        try {
            int qty = Integer.parseInt(txtQty.getText());
            if (qty <= 0) { showAlert(Alert.AlertType.WARNING, "Validation", "Quantity must be > 0!"); return; }
            if (qty > item.getStockQuantity()) {
                showAlert(Alert.AlertType.WARNING, "Stock", "Not enough stock! Available: " + item.getStockQuantity()); return;
            }

            // Check duplicate
            for (SalesOrderItemDTO ci : cartItems) {
                if (ci.getItemId() == item.getItemId()) {
                    ci.setQuantity(ci.getQuantity() + qty);
                    tblCart.refresh();
                    updateTotal();
                    txtQty.clear();
                    return;
                }
            }
            cartItems.add(new SalesOrderItemDTO(item.getItemId(), item.getName(), qty, item.getUnitPrice()));
            updateTotal();
            txtQty.clear();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid quantity!");
        }
    }

    @FXML void removeCartItem() {
        SalesOrderItemDTO selected = tblCart.getSelectionModel().getSelectedItem();
        if (selected != null) { cartItems.remove(selected); updateTotal(); }
    }

    @FXML void placeOrder() {
        if (cmbCustomer.getValue() == null || cmbSaleType.getValue() == null || cartItems.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Customer, sale type, and at least one item are required!"); return;
        }
        try {
            SalesOrderDTO order = new SalesOrderDTO(0, cmbCustomer.getValue().getCustomerId(),
                    null, cmbSaleType.getValue(),
                    cmbStatus.getValue() != null ? cmbStatus.getValue() : "pending",
                    txtNotes.getText());
            order.setItems(new ArrayList<>(cartItems));

            if (salesOrderBO.saveSalesOrder(order)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Order placed successfully!");
                clearCart(); loadOrders();
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML void clearCart() {
        cartItems.clear(); cmbCustomer.setValue(null);
        cmbSaleType.setValue(null); cmbStatus.setValue(null);
        txtNotes.clear(); updateTotal();
    }

    @FXML void updateOrderStatus() {
        SalesOrderDTO selected = tblOrders.getSelectionModel().getSelectedItem();
        if (selected == null || cmbUpdateStatus.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Select an order and a new status!"); return;
        }
        try {
            if (salesOrderBO.updateOrderStatus(selected.getOrderId(), cmbUpdateStatus.getValue())) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Order status updated!");
                loadOrders();
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML void searchOrder() {
        try {
            String kw = txtSearch.getText().trim();
            tblOrders.setItems(FXCollections.observableArrayList(
                    kw.isEmpty() ? salesOrderBO.getAllOrders() : salesOrderBO.searchOrders(kw)));
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML void onOrderSelected(MouseEvent event) {
        // Just selection, no form fill needed for orders
    }

    private void updateTotal() {
        double total = cartItems.stream().mapToDouble(SalesOrderItemDTO::getSubTotal).sum();
        lblTotal.setText(String.format("Rs. %.2f", total));
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type, msg, ButtonType.OK);
        alert.setTitle(title); alert.setHeaderText(null); alert.showAndWait();
    }
}
