package com.example.dinithi_pahana_edu.model;

public class Customer {
    private int id;
    private String accountNumber;
    private String name;
    private String address;
    private String telephone;
    private String email;
    private String createdAt;

    // Default constructor
    public Customer() {
    }

    // Constructor with all fields
    public Customer(String accountNumber, String name, String address, String telephone, String email) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.address = address;
        this.telephone = telephone;
        this.email = email;
    }

    // Constructor with all fields including id
    public Customer(int id, String accountNumber, String name, String address, String telephone, String email, String createdAt) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.name = name;
        this.address = address;
        this.telephone = telephone;
        this.email = email;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
} 