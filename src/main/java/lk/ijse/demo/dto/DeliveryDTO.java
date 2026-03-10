package lk.ijse.demo.dto;

public class DeliveryDTO {
    private int deliveryId;
    private int orderId;
    private String deliveryAddress;
    private String scheduledDate;
    private String deliveredDate;
    private double deliveryFee;
    private String status;
    private String driverName;
    private String notes;
    private String customerName;

    public  DeliveryDTO(){

    }
    public DeliveryDTO(int deliveryId, int orderId, String deliveryAddress, String scheduledDate,
                       String deliveredDate, double deliveryFee, String status, String driverName, String notes) {
        this.deliveryId = deliveryId;
        this.orderId = orderId;
        this.deliveryAddress = deliveryAddress;
        this.scheduledDate = scheduledDate;
        this.deliveredDate = deliveredDate;
        this.deliveryFee = deliveryFee;
        this.status = status;
        this.driverName = driverName;
        this.notes = notes;
    }
    public int getDeliveryId() {
        return deliveryId;
    }
    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public String getDeliveryAddress() {
        return deliveryAddress;
    }
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
    public String getScheduledDate() {
        return scheduledDate;
    }
    public void setScheduledDate(String scheduledDate) {
        this.scheduledDate = scheduledDate;
    }
    public String getDeliveredDate() {
        return deliveredDate;
    }
    public void setDeliveredDate(String deliveredDate) {
        this.deliveredDate = deliveredDate;
    }
    public double getDeliveryFee() {
        return deliveryFee;
    }
    public void setDeliveryFee(double deliveredDate) {
        this.deliveryFee = deliveryFee;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getDriverName() {
        return driverName;
    }
    public void setDriverName(String driverName) {
        this.driverName = driverName;
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
    @Override
    public String toString() {
        return "CategoryDTO{" +
                "deliveryId=" + deliveryId +
                ", orderId='" + orderId + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", scheduledDate='" + scheduledDate + '\'' +
                ", deliveredDate='" + deliveredDate + '\'' +
                ", status='" + status + '\'' +
                ", driverName='" + driverName + '\'' +
                ", notes='" + notes + '\'' +
                ", customerName='" + customerName + '\'' +
                '}';
    }




    }


