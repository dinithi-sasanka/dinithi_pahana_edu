package com.example.dinithi_pahana_edu.service;

import com.example.dinithi_pahana_edu.dao.CustomerDAO;
import com.example.dinithi_pahana_edu.model.Customer;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerService {
    private CustomerDAO customerDAO;
    
    public CustomerService() {
        this.customerDAO = new CustomerDAO();
    }
    
    // Add a new customer with validation
    public boolean addCustomer(Customer customer) {
        // Validate customer data (remove account number check)
        if (customer == null ||
            customer.getName() == null || customer.getName().trim().isEmpty() ||
            customer.getAddress() == null || customer.getAddress().trim().isEmpty() ||
            customer.getTelephone() == null || customer.getTelephone().trim().isEmpty()) {
            return false;
        }

        // Auto-generate unique account number
        String nextAccountNumber = generateNextAccountNumber();
        customer.setAccountNumber(nextAccountNumber);

        // Trim whitespace from all fields
        customer.setName(customer.getName().trim());
        customer.setAddress(customer.getAddress().trim());
        customer.setTelephone(customer.getTelephone().trim());

        return customerDAO.addCustomer(customer);
    }

    // Generate the next unique account number in the format CUST000001
    private String generateNextAccountNumber() {
        List<Customer> allCustomers = customerDAO.getAllCustomers();
        int max = 0;
        Pattern pattern = Pattern.compile("CUST(\\d{6})");
        for (Customer c : allCustomers) {
            String acc = c.getAccountNumber();
            if (acc != null) {
                Matcher m = pattern.matcher(acc);
                if (m.matches()) {
                    int num = Integer.parseInt(m.group(1));
                    if (num > max) max = num;
                }
            }
        }
        return String.format("CUST%06d", max + 1);
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

    // Search customer by account number, name, or telephone
    public Customer searchCustomer(String accountNumber, String name, String telephone) {
        return customerDAO.searchCustomer(accountNumber, name, telephone);
    }
} 