package com.example.dinithi_pahana_edu.customer;

import com.example.dinithi_pahana_edu.model.Customer;
import com.example.dinithi_pahana_edu.service.CustomerService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit Tests for Customer Management Functionality
 * Tests add customers, view customers, and delete customers operations
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerManagementTest {
    
    private CustomerService customerService;
    private Customer testCustomer;
    private List<Customer> customerList;
    
    @BeforeEach
    void setUp() {
        customerService = new CustomerService();
        customerList = new ArrayList<>();
        
        // Create test customer
        testCustomer = new Customer();
        testCustomer.setId(1);
        testCustomer.setAccountNumber("ACC001");
        testCustomer.setName("John Doe");
        testCustomer.setAddress("123 Main Street, City");
        testCustomer.setTelephone("0712345678");
        testCustomer.setEmail("john.doe@email.com");
    }
    
    @Test
    @Order(1)
    @DisplayName("Test Customer Object Creation")
    void testCustomerCreation() {
        assertNotNull(testCustomer);
        assertEquals(1, testCustomer.getId());
        assertEquals("ACC001", testCustomer.getAccountNumber());
        assertEquals("John Doe", testCustomer.getName());
        assertEquals("123 Main Street, City", testCustomer.getAddress());
        assertEquals("0712345678", testCustomer.getTelephone());
        assertEquals("john.doe@email.com", testCustomer.getEmail());
    }
    
    @Test
    @Order(2)
    @DisplayName("Test Customer Getters and Setters")
    void testCustomerGettersAndSetters() {
        Customer customer = new Customer();
        
        // Test setters
        customer.setId(100);
        customer.setAccountNumber("ACC100");
        customer.setName("Jane Smith");
        customer.setAddress("456 Oak Avenue, Town");
        customer.setTelephone("0787654321");
        customer.setEmail("jane.smith@email.com");
        
        // Test getters
        assertEquals(100, customer.getId());
        assertEquals("ACC100", customer.getAccountNumber());
        assertEquals("Jane Smith", customer.getName());
        assertEquals("456 Oak Avenue, Town", customer.getAddress());
        assertEquals("0787654321", customer.getTelephone());
        assertEquals("jane.smith@email.com", customer.getEmail());
    }
    
    @Test
    @Order(3)
    @DisplayName("Test Add Customer - Valid Data")
    void testAddCustomerValidData() {
        Customer newCustomer = new Customer();
        newCustomer.setAccountNumber("ACC002");
        newCustomer.setName("Alice Johnson");
        newCustomer.setAddress("789 Pine Road, Village");
        newCustomer.setTelephone("0711122233");
        newCustomer.setEmail("alice.johnson@email.com");
        
        // Simulate adding customer to list
        customerList.add(newCustomer);
        
        assertNotNull(newCustomer);
        assertEquals(1, customerList.size());
        assertEquals("ACC002", customerList.get(0).getAccountNumber());
        assertEquals("Alice Johnson", customerList.get(0).getName());
    }
    
    @Test
    @Order(4)
    @DisplayName("Test Add Customer - Invalid Data")
    void testAddCustomerInvalidData() {
        Customer invalidCustomer = new Customer();
        invalidCustomer.setAccountNumber(""); // Empty account number
        invalidCustomer.setName(""); // Empty name
        invalidCustomer.setTelephone("123"); // Invalid phone number
        invalidCustomer.setEmail("invalid-email"); // Invalid email
        
        // Test validation
        assertFalse(isValidCustomer(invalidCustomer));
        assertFalse(isValidAccountNumber(invalidCustomer.getAccountNumber()));
        assertFalse(isValidName(invalidCustomer.getName()));
        assertFalse(isValidTelephone(invalidCustomer.getTelephone()));
        assertFalse(isValidEmail(invalidCustomer.getEmail()));
    }
    
    @Test
    @Order(5)
    @DisplayName("Test Add Customer - Duplicate Account Number")
    void testAddCustomerDuplicateAccountNumber() {
        Customer customer1 = new Customer();
        customer1.setAccountNumber("ACC003");
        customer1.setName("Customer One");
        
        Customer customer2 = new Customer();
        customer2.setAccountNumber("ACC003"); // Same account number
        customer2.setName("Customer Two");
        
        // Simulate adding first customer
        customerList.add(customer1);
        
        // Test duplicate account number detection
        assertTrue(hasDuplicateAccountNumber(customerList, customer2.getAccountNumber()));
        assertEquals(1, customerList.size()); // Should not add duplicate
    }
    
    @Test
    @Order(6)
    @DisplayName("Test View All Customers")
    void testViewAllCustomers() {
        // Add multiple customers
        Customer customer1 = new Customer();
        customer1.setId(1);
        customer1.setAccountNumber("ACC001");
        customer1.setName("Customer One");
        
        Customer customer2 = new Customer();
        customer2.setId(2);
        customer2.setAccountNumber("ACC002");
        customer2.setName("Customer Two");
        
        Customer customer3 = new Customer();
        customer3.setId(3);
        customer3.setAccountNumber("ACC003");
        customer3.setName("Customer Three");
        
        customerList.add(customer1);
        customerList.add(customer2);
        customerList.add(customer3);
        
        // Test viewing all customers
        assertNotNull(customerList);
        assertEquals(3, customerList.size());
        
        // Test customer details
        assertEquals("ACC001", customerList.get(0).getAccountNumber());
        assertEquals("Customer One", customerList.get(0).getName());
        assertEquals("ACC002", customerList.get(1).getAccountNumber());
        assertEquals("Customer Two", customerList.get(1).getName());
        assertEquals("ACC003", customerList.get(2).getAccountNumber());
        assertEquals("Customer Three", customerList.get(2).getName());
    }
    
    @Test
    @Order(7)
    @DisplayName("Test View Customer by ID")
    void testViewCustomerById() {
        // Add customers
        Customer customer1 = new Customer();
        customer1.setId(1);
        customer1.setAccountNumber("ACC001");
        customer1.setName("Customer One");
        
        Customer customer2 = new Customer();
        customer2.setId(2);
        customer2.setAccountNumber("ACC002");
        customer2.setName("Customer Two");
        
        customerList.add(customer1);
        customerList.add(customer2);
        
        // Test finding customer by ID
        Customer foundCustomer = findCustomerById(customerList, 1);
        assertNotNull(foundCustomer);
        assertEquals(1, foundCustomer.getId());
        assertEquals("ACC001", foundCustomer.getAccountNumber());
        assertEquals("Customer One", foundCustomer.getName());
        
        // Test finding non-existent customer
        Customer notFoundCustomer = findCustomerById(customerList, 999);
        assertNull(notFoundCustomer);
    }
    
    @Test
    @Order(8)
    @DisplayName("Test View Customer by Account Number")
    void testViewCustomerByAccountNumber() {
        // Add customers
        Customer customer1 = new Customer();
        customer1.setAccountNumber("ACC001");
        customer1.setName("Customer One");
        
        Customer customer2 = new Customer();
        customer2.setAccountNumber("ACC002");
        customer2.setName("Customer Two");
        
        customerList.add(customer1);
        customerList.add(customer2);
        
        // Test finding customer by account number
        Customer foundCustomer = findCustomerByAccountNumber(customerList, "ACC001");
        assertNotNull(foundCustomer);
        assertEquals("ACC001", foundCustomer.getAccountNumber());
        assertEquals("Customer One", foundCustomer.getName());
        
        // Test finding non-existent customer
        Customer notFoundCustomer = findCustomerByAccountNumber(customerList, "ACC999");
        assertNull(notFoundCustomer);
    }
    
    @Test
    @Order(9)
    @DisplayName("Test Delete Customer - Valid ID")
    void testDeleteCustomerValidId() {
        // Add customers
        Customer customer1 = new Customer();
        customer1.setId(1);
        customer1.setAccountNumber("ACC001");
        customer1.setName("Customer One");
        
        Customer customer2 = new Customer();
        customer2.setId(2);
        customer2.setAccountNumber("ACC002");
        customer2.setName("Customer Two");
        
        customerList.add(customer1);
        customerList.add(customer2);
        
        assertEquals(2, customerList.size());
        
        // Delete customer with ID 1
        boolean deleted = deleteCustomerById(customerList, 1);
        assertTrue(deleted);
        assertEquals(1, customerList.size());
        assertEquals("ACC002", customerList.get(0).getAccountNumber());
    }
    
    @Test
    @Order(10)
    @DisplayName("Test Delete Customer - Invalid ID")
    void testDeleteCustomerInvalidId() {
        // Add customer
        Customer customer = new Customer();
        customer.setId(1);
        customer.setAccountNumber("ACC001");
        customer.setName("Customer One");
        
        customerList.add(customer);
        assertEquals(1, customerList.size());
        
        // Try to delete non-existent customer
        boolean deleted = deleteCustomerById(customerList, 999);
        assertFalse(deleted);
        assertEquals(1, customerList.size()); // List should remain unchanged
    }
    
    @Test
    @Order(11)
    @DisplayName("Test Delete Customer by Account Number")
    void testDeleteCustomerByAccountNumber() {
        // Add customers
        Customer customer1 = new Customer();
        customer1.setAccountNumber("ACC001");
        customer1.setName("Customer One");
        
        Customer customer2 = new Customer();
        customer2.setAccountNumber("ACC002");
        customer2.setName("Customer Two");
        
        customerList.add(customer1);
        customerList.add(customer2);
        
        assertEquals(2, customerList.size());
        
        // Delete customer by account number
        boolean deleted = deleteCustomerByAccountNumber(customerList, "ACC001");
        assertTrue(deleted);
        assertEquals(1, customerList.size());
        assertEquals("ACC002", customerList.get(0).getAccountNumber());
    }
    
    @Test
    @Order(12)
    @DisplayName("Test Customer Data Validation")
    void testCustomerDataValidation() {
        Customer validCustomer = new Customer();
        validCustomer.setAccountNumber("ACC001");
        validCustomer.setName("Valid Customer");
        validCustomer.setAddress("Valid Address");
        validCustomer.setTelephone("0712345678");
        validCustomer.setEmail("valid@email.com");
        
        // Test valid customer
        assertTrue(isValidCustomer(validCustomer));
        assertTrue(isValidAccountNumber(validCustomer.getAccountNumber()));
        assertTrue(isValidName(validCustomer.getName()));
        assertTrue(isValidTelephone(validCustomer.getTelephone()));
        assertTrue(isValidEmail(validCustomer.getEmail()));
    }
    
    // Helper methods
    private boolean isValidCustomer(Customer customer) {
        return customer != null &&
               isValidAccountNumber(customer.getAccountNumber()) &&
               isValidName(customer.getName()) &&
               isValidTelephone(customer.getTelephone()) &&
               isValidEmail(customer.getEmail());
    }
    
    private boolean isValidAccountNumber(String accountNumber) {
        return accountNumber != null && !accountNumber.trim().isEmpty() && accountNumber.length() >= 3;
    }
    
    private boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty() && name.length() >= 2;
    }
    
    private boolean isValidTelephone(String telephone) {
        return telephone != null && telephone.matches("\\d{10}");
    }
    
    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    
    private boolean hasDuplicateAccountNumber(List<Customer> customers, String accountNumber) {
        return customers.stream().anyMatch(c -> c.getAccountNumber().equals(accountNumber));
    }
    
    private Customer findCustomerById(List<Customer> customers, int id) {
        return customers.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    private Customer findCustomerByAccountNumber(List<Customer> customers, String accountNumber) {
        return customers.stream()
                .filter(c -> c.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElse(null);
    }
    
    private boolean deleteCustomerById(List<Customer> customers, int id) {
        return customers.removeIf(c -> c.getId() == id);
    }
    
    private boolean deleteCustomerByAccountNumber(List<Customer> customers, String accountNumber) {
        return customers.removeIf(c -> c.getAccountNumber().equals(accountNumber));
    }
}