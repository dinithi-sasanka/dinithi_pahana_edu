package com.example.dinithi_pahana_edu.service;

import com.example.dinithi_pahana_edu.dao.CustomerDAO;
import com.example.dinithi_pahana_edu.model.Customer;
import java.util.List;

public class CustomerService {
    private CustomerDAO customerDAO;
    
    public CustomerService() {
        this.customerDAO = new CustomerDAO();
    }
    
    // Add a new customer with validation
    public boolean addCustomer(Customer customer) {
        // Validate customer data
        if (customer == null || 
            customer.getAccountNumber() == null || customer.getAccountNumber().trim().isEmpty() ||
            customer.getName() == null || customer.getName().trim().isEmpty() ||
            customer.getAddress() == null || customer.getAddress().trim().isEmpty() ||
            customer.getTelephone() == null || customer.getTelephone().trim().isEmpty()) {
            return false;
        }
        
        // Check if account number already exists
        if (customerDAO.isAccountNumberExists(customer.getAccountNumber().trim())) {
            return false;
        }
        
        // Trim whitespace from all fields
        customer.setAccountNumber(customer.getAccountNumber().trim());
        customer.setName(customer.getName().trim());
        customer.setAddress(customer.getAddress().trim());
        customer.setTelephone(customer.getTelephone().trim());
        
        return customerDAO.addCustomer(customer);
    }
    
    // Get all customers
    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }
    
    // Get customer by ID
    public Customer getCustomerById(int id) {
        return customerDAO.getCustomerById(id);
    }
    
    // Get customer by account number
    public Customer getCustomerByAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            return null;
        }
        return customerDAO.getCustomerByAccountNumber(accountNumber.trim());
    }
    
    // Update customer with validation
    public boolean updateCustomer(Customer customer) {
        // Validate customer data
        if (customer == null || 
            customer.getAccountNumber() == null || customer.getAccountNumber().trim().isEmpty() ||
            customer.getName() == null || customer.getName().trim().isEmpty() ||
            customer.getAddress() == null || customer.getAddress().trim().isEmpty() ||
            customer.getTelephone() == null || customer.getTelephone().trim().isEmpty()) {
            return false;
        }
        
        // Check if account number already exists for another customer
        Customer existingCustomer = customerDAO.getCustomerByAccountNumber(customer.getAccountNumber().trim());
        if (existingCustomer != null && existingCustomer.getId() != customer.getId()) {
            return false;
        }
        
        // Trim whitespace from all fields
        customer.setAccountNumber(customer.getAccountNumber().trim());
        customer.setName(customer.getName().trim());
        customer.setAddress(customer.getAddress().trim());
        customer.setTelephone(customer.getTelephone().trim());
        
        return customerDAO.updateCustomer(customer);
    }
    
    // Delete customer
    public boolean deleteCustomer(int id) {
        return customerDAO.deleteCustomer(id);
    }
    
    // Check if account number exists
    public boolean isAccountNumberExists(String accountNumber) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            return false;
        }
        return customerDAO.isAccountNumberExists(accountNumber.trim());
    }
    
    // Search customers by name
    public List<Customer> searchCustomersByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return getAllCustomers();
        }
        return customerDAO.searchCustomersByName(name.trim());
    }
} 