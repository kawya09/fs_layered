package lk.ijse.demo.entity;

public class Item {
    private int itemId;
    private int supplierId;
    private int categoryId;
    private String name;
    private String description;
    private double unitCost;
    private double unitPrice;
    private int stockQuantity;
    private String supplierName;
    private String categoryName;

    public Item() {}

    public Item(int itemId, int supplierId, int categoryId, String name, String description,
                   double unitCost, double unitPrice, int stockQuantity) {
        this.itemId = itemId;
        this.supplierId = supplierId;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.unitCost = unitCost;
        this.unitPrice = unitPrice;
        this.stockQuantity = stockQuantity;
    }

    public Item(int itemId, String name, int supplierId, int categoryId, String description, double unitCost, double unitPrice, int stockQuantity) {
        this.itemId = itemId;
        this.supplierId = supplierId;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.unitCost = unitCost;
        this.unitPrice = unitPrice;
        this.stockQuantity = stockQuantity;
    }

    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }
    public int getSupplierId() { return supplierId; }
    public void setSupplierId(int supplierId) { this.supplierId = supplierId; }
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getUnitCost() { return unitCost; }
    public void setUnitCost(double unitCost) { this.unitCost = unitCost; }
    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

}
