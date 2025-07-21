package com.example.dinithi_pahana_edu.model;

import java.util.Date;
import java.util.List;

public class Bill {
    private int id;
    private int customerId;
    private String billNumber;
    private Date billDate;
    private String billDateTime;
    private double totalAmount;
    private double paidAmount;
    private double balance;
    private List<BillItem> billItems; // For display purposes

    public Bill() {}

    public Bill(int id, int customerId, String billNumber, Date billDate, String billDateTime, double totalAmount, double paidAmount, double balance) {
        this.id = id;
        this.customerId = customerId;
        this.billNumber = billNumber;
        this.billDate = billDate;
        this.billDateTime = billDateTime;
        this.totalAmount = totalAmount;
        this.paidAmount = paidAmount;
        this.balance = balance;
    }

    public Bill(int customerId, String billNumber, Date billDate, String billDateTime, double totalAmount, double paidAmount, double balance) {
        this.customerId = customerId;
        this.billNumber = billNumber;
        this.billDate = billDate;
        this.billDateTime = billDateTime;
        this.totalAmount = totalAmount;
        this.paidAmount = paidAmount;
        this.balance = balance;
    }

    public Bill(int customerId, Date billDate, double totalAmount) {
        this.customerId = customerId;
        this.billDate = billDate;
        this.totalAmount = totalAmount;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public String getBillNumber() { return billNumber; }
    public void setBillNumber(String billNumber) { this.billNumber = billNumber; }
    public Date getBillDate() { return billDate; }
    public void setBillDate(Date billDate) { this.billDate = billDate; }
    public String getBillDateTime() { return billDateTime; }
    public void setBillDateTime(String billDateTime) { this.billDateTime = billDateTime; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public double getPaidAmount() { return paidAmount; }
    public void setPaidAmount(double paidAmount) { this.paidAmount = paidAmount; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
    public List<BillItem> getBillItems() { return billItems; }
    public void setBillItems(List<BillItem> billItems) { this.billItems = billItems; }
} 