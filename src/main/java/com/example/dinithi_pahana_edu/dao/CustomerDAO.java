package com.example.dinithi_pahana_edu.dao;

import com.example.dinithi_pahana_edu.model.Customer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    
    // Add a new customer
    public boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO customers (account_number, name, address, telephone, email, created_at) VALUES (?, ?, ?, ?, ?, NOW())";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, customer.getAccountNumber());
            pstmt.setString(2, customer.getName());
            pstmt.setString(3, customer.getAddress());
            pstmt.setString(4, customer.getTelephone());
            pstmt.setString(5, customer.getEmail());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Get all customers
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers ORDER BY created_at DESC";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setAccountNumber(rs.getString("account_number"));
                customer.setName(rs.getString("name"));
                customer.setAddress(rs.getString("address"));
                customer.setTelephone(rs.getString("telephone"));
                customer.setEmail(rs.getString("email"));
                customer.setCreatedAt(rs.getString("created_at"));
                customers.add(customer);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return customers;
    }
    
    // Get customer by ID
    public Customer getCustomerById(int id) {
        String sql = "SELECT * FROM customers WHERE id = ?";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setAccountNumber(rs.getString("account_number"));
                customer.setName(rs.getString("name"));
                customer.setAddress(rs.getString("address"));
                customer.setTelephone(rs.getString("telephone"));
                customer.setEmail(rs.getString("email"));
                customer.setCreatedAt(rs.getString("created_at"));
                return customer;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    // Get customer by account number
    public Customer getCustomerByAccountNumber(String accountNumber) {
        String sql = "SELECT * FROM customers WHERE account_number = ?";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setAccountNumber(rs.getString("account_number"));
                customer.setName(rs.getString("name"));
                customer.setAddress(rs.getString("address"));
                customer.setTelephone(rs.getString("telephone"));
                customer.setEmail(rs.getString("email"));
                customer.setCreatedAt(rs.getString("created_at"));
                return customer;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    // Update customer
    public boolean updateCustomer(Customer customer) {
        String sql = "UPDATE customers SET account_number = ?, name = ?, address = ?, telephone = ?, email = ? WHERE id = ?";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, customer.getAccountNumber());
            pstmt.setString(2, customer.getName());
            pstmt.setString(3, customer.getAddress());
            pstmt.setString(4, customer.getTelephone());
            pstmt.setString(5, customer.getEmail());
            pstmt.setInt(6, customer.getId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Delete customer
    public boolean deleteCustomer(int id) {
        String sql = "DELETE FROM customers WHERE id = ?";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Check if account number already exists
    public boolean isAccountNumberExists(String accountNumber) {
        String sql = "SELECT COUNT(*) FROM customers WHERE account_number = ?";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    // Search customers by name
    public List<Customer> searchCustomersByName(String name) {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers WHERE name LIKE ? ORDER BY created_at DESC";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + name + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setAccountNumber(rs.getString("account_number"));
                customer.setName(rs.getString("name"));
                customer.setAddress(rs.getString("address"));
                customer.setTelephone(rs.getString("telephone"));
                customer.setEmail(rs.getString("email"));
                customer.setCreatedAt(rs.getString("created_at"));
                customers.add(customer);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return customers;
    }

    // Search customer by account number, name, or telephone (all optional, match if all non-empty fields match)
    public Customer searchCustomer(String accountNumber, String name, String telephone) {
        StringBuilder sql = new StringBuilder("SELECT * FROM customers WHERE 1=1");
        java.util.List<Object> params = new java.util.ArrayList<>();
        if (accountNumber != null && !accountNumber.trim().isEmpty()) {
            sql.append(" AND account_number = ?");
            params.add(accountNumber.trim());
        }
        if (name != null && !name.trim().isEmpty()) {
            sql.append(" AND name = ?");
            params.add(name.trim());
        }
        if (telephone != null && !telephone.trim().isEmpty()) {
            sql.append(" AND telephone = ?");
            params.add(telephone.trim());
        }
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setAccountNumber(rs.getString("account_number"));
                customer.setName(rs.getString("name"));
                customer.setAddress(rs.getString("address"));
                customer.setTelephone(rs.getString("telephone"));
                customer.setEmail(rs.getString("email"));
                customer.setCreatedAt(rs.getString("created_at"));
                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Search customer by email
    public Customer getCustomerByEmail(String email) {
        String sql = "SELECT * FROM customers WHERE email = ?";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setAccountNumber(rs.getString("account_number"));
                customer.setName(rs.getString("name"));
                customer.setAddress(rs.getString("address"));
                customer.setTelephone(rs.getString("telephone"));
                customer.setEmail(rs.getString("email"));
                customer.setCreatedAt(rs.getString("created_at"));
                return customer;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    // Search customer by any field (id, account number, name, address, email, telephone)
    public Customer searchCustomerByAnyField(String searchTerm) {
        String sql = "SELECT * FROM customers WHERE id = ? OR account_number = ? OR LOWER(name) LIKE ? OR LOWER(address) LIKE ? OR LOWER(email) LIKE ? OR telephone = ? LIMIT 1";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            int id = -1;
            try { id = Integer.parseInt(searchTerm); } catch (NumberFormatException ignored) {}
            String lowerTerm = searchTerm.toLowerCase();
            System.out.println("[DEBUG] SQL: " + sql);
            System.out.println("[DEBUG] Parameters: id=" + id + ", account_number=" + searchTerm + ", name LIKE %" + lowerTerm + "% , address LIKE %" + lowerTerm + "% , email LIKE %" + lowerTerm + "% , telephone=" + searchTerm);
            pstmt.setInt(1, id);
            pstmt.setString(2, searchTerm);
            pstmt.setString(3, "%" + lowerTerm + "%");
            pstmt.setString(4, "%" + lowerTerm + "%");
            pstmt.setString(5, "%" + lowerTerm + "%");
            pstmt.setString(6, searchTerm);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setAccountNumber(rs.getString("account_number"));
                customer.setName(rs.getString("name"));
                customer.setAddress(rs.getString("address"));
                customer.setTelephone(rs.getString("telephone"));
                customer.setEmail(rs.getString("email"));
                customer.setCreatedAt(rs.getString("created_at"));
                return customer;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
} 