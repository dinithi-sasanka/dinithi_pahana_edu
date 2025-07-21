package com.example.dinithi_pahana_edu.model;

public class BillItem {
    private int id;
    private int billId;
    private int itemId;
    private int quantity;
    private double price;
    private String itemName; // For display purposes

    public BillItem() {}

    public BillItem(int id, int billId, int itemId, int quantity, double price) {
        this.id = id;
        this.billId = billId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.price = price;
    }

    public BillItem(int billId, int itemId, int quantity, double price) {
        this.billId = billId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getBillId() { return billId; }
    public void setBillId(int billId) { this.billId = billId; }
    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }
} 