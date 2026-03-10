package lk.ijse.demo.dto;

import java.util.List;

public class SalesOrderDTO {
    private int orderId;
    private int customerId;
    private String orderDate;
    private String saleType;
    private String status;
    private String notes;
    private String customerName;
    private double totalAmount;
    private List<SalesOrderItemDTO> items;

    public SalesOrderDTO() {}

    public SalesOrderDTO(int orderId, int customerId, String orderDate, String saleType, String status, String notes) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.saleType = saleType;
        this.status = status;
        this.notes = notes;
    }

    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public String getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
    public String getSaleType() {
        return saleType;
    }
    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public double getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    public List<SalesOrderItemDTO> getItems() {
        return items;
    }
    public void setItems(List<SalesOrderItemDTO> items) {
        this.items = items;
    }
}


