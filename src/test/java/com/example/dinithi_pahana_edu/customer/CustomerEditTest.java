package com.example.dinithi_pahana_edu.customer;

import com.example.dinithi_pahana_edu.model.Customer;
import com.example.dinithi_pahana_edu.service.CustomerService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit Tests for Customer Edit Functionality
 * Tests search customers and update customers operations
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerEditTest {
    
    private CustomerService customerService;
    private List<Customer> customerList;
    
    @BeforeEach
    void setUp() {
        customerService = new CustomerService();
        customerList = new ArrayList<>();
        
        // Add test customers
        Customer customer1 = new Customer();
        customer1.setId(1);
        customer1.setAccountNumber("ACC001");
        customer1.setName("John Doe");
        customer1.setAddress("123 Main Street, City");
        customer1.setTelephone("0712345678");
        customer1.setEmail("john.doe@email.com");
        
        Customer customer2 = new Customer();
        customer2.setId(2);
        customer2.setAccountNumber("ACC002");
        customer2.setName("Jane Smith");
        customer2.setAddress("456 Oak Avenue, Town");
        customer2.setTelephone("0787654321");
        customer2.setEmail("jane.smith@email.com");
        
        Customer customer3 = new Customer();
        customer3.setId(3);
        customer3.setAccountNumber("ACC003");
        customer3.setName("Alice Johnson");
        customer3.setAddress("789 Pine Road, Village");
        customer3.setTelephone("0711122233");
        customer3.setEmail("alice.johnson@email.com");
        
        customerList.add(customer1);
        customerList.add(customer2);
        customerList.add(customer3);
    }
    
    @Test
    @Order(1)
    @DisplayName("Test Search Customer by Account Number")
    void testSearchCustomerByAccountNumber() {
        // Search for existing customer
        Customer foundCustomer = searchCustomerByAccountNumber(customerList, "ACC001");
        assertNotNull(foundCustomer);
        assertEquals("ACC001", foundCustomer.getAccountNumber());
        assertEquals("John Doe", foundCustomer.getName());
        assertEquals("john.doe@email.com", foundCustomer.getEmail());
        
        // Search for non-existent customer
        Customer notFoundCustomer = searchCustomerByAccountNumber(customerList, "ACC999");
        assertNull(notFoundCustomer);
    }
    
    @Test
    @Order(2)
    @DisplayName("Test Search Customer by Name")
    void testSearchCustomerByName() {
        // Search for existing customer
        Customer foundCustomer = searchCustomerByName(customerList, "Jane Smith");
        assertNotNull(foundCustomer);
        assertEquals("Jane Smith", foundCustomer.getName());
        assertEquals("ACC002", foundCustomer.getAccountNumber());
        assertEquals("jane.smith@email.com", foundCustomer.getEmail());
        
        // Search for non-existent customer
        Customer notFoundCustomer = searchCustomerByName(customerList, "Non Existent");
        assertNull(notFoundCustomer);
    }
    
    @Test
    @Order(3)
    @DisplayName("Test Search Customer by Telephone")
    void testSearchCustomerByTelephone() {
        // Search for existing customer
        Customer foundCustomer = searchCustomerByTelephone(customerList, "0711122233");
        assertNotNull(foundCustomer);
        assertEquals("0711122233", foundCustomer.getTelephone());
        assertEquals("Alice Johnson", foundCustomer.getName());
        assertEquals("ACC003", foundCustomer.getAccountNumber());
        
        // Search for non-existent customer
        Customer notFoundCustomer = searchCustomerByTelephone(customerList, "9999999999");
        assertNull(notFoundCustomer);
    }
    
    @Test
    @Order(4)
    @DisplayName("Test Search Customer by Email")
    void testSearchCustomerByEmail() {
        // Search for existing customer
        Customer foundCustomer = searchCustomerByEmail(customerList, "john.doe@email.com");
        assertNotNull(foundCustomer);
        assertEquals("john.doe@email.com", foundCustomer.getEmail());
        assertEquals("John Doe", foundCustomer.getName());
        assertEquals("ACC001", foundCustomer.getAccountNumber());
        
        // Search for non-existent customer
        Customer notFoundCustomer = searchCustomerByEmail(customerList, "nonexistent@email.com");
        assertNull(notFoundCustomer);
    }
    
    @Test
    @Order(5)
    @DisplayName("Test Search Customer - Partial Name Match")
    void testSearchCustomerPartialNameMatch() {
        // Search with partial name
        List<Customer> foundCustomers = searchCustomersByPartialName(customerList, "Doe");
        assertNotNull(foundCustomers);
        assertEquals(1, foundCustomers.size());
        assertEquals("John Doe", foundCustomers.get(0).getName());
        
        // Search with another partial name
        List<Customer> foundCustomers2 = searchCustomersByPartialName(customerList, "Jane");
        assertNotNull(foundCustomers2);
        assertEquals(1, foundCustomers2.size());
        assertEquals("Jane Smith", foundCustomers2.get(0).getName());
        
        // Search with "Alice" which should find one customer
        List<Customer> foundCustomers3 = searchCustomersByPartialName(customerList, "Alice");
        assertNotNull(foundCustomers3);
        assertEquals(1, foundCustomers3.size());
        assertEquals("Alice Johnson", foundCustomers3.get(0).getName());
    }
    
    @Test
    @Order(6)
    @DisplayName("Test Search Customer - Case Insensitive")
    void testSearchCustomerCaseInsensitive() {
        // Search with different case
        Customer foundCustomer = searchCustomerByName(customerList, "JANE SMITH");
        assertNotNull(foundCustomer);
        assertEquals("Jane Smith", foundCustomer.getName());
        
        Customer foundCustomer2 = searchCustomerByName(customerList, "jane smith");
        assertNotNull(foundCustomer2);
        assertEquals("Jane Smith", foundCustomer2.getName());
    }
    
    @Test
    @Order(7)
    @DisplayName("Test Update Customer - Valid Data")
    void testUpdateCustomerValidData() {
        // Get customer to update
        Customer customerToUpdate = findCustomerById(customerList, 1);
        assertNotNull(customerToUpdate);
        assertEquals("John Doe", customerToUpdate.getName());
        assertEquals("123 Main Street, City", customerToUpdate.getAddress());
        
        // Update customer data
        customerToUpdate.setName("John Updated Doe");
        customerToUpdate.setAddress("456 Updated Street, New City");
        customerToUpdate.setTelephone("0798765432");
        customerToUpdate.setEmail("john.updated@email.com");
        
        // Verify update
        assertEquals("John Updated Doe", customerToUpdate.getName());
        assertEquals("456 Updated Street, New City", customerToUpdate.getAddress());
        assertEquals("0798765432", customerToUpdate.getTelephone());
        assertEquals("john.updated@email.com", customerToUpdate.getEmail());
    }
    
    @Test
    @Order(8)
    @DisplayName("Test Update Customer - Invalid Data")
    void testUpdateCustomerInvalidData() {
        Customer customerToUpdate = findCustomerById(customerList, 1);
        assertNotNull(customerToUpdate);
        
        // Try to update with invalid data
        String originalName = customerToUpdate.getName();
        String originalTelephone = customerToUpdate.getTelephone();
        String originalEmail = customerToUpdate.getEmail();
        
        // Update with invalid data
        customerToUpdate.setName(""); // Invalid empty name
        customerToUpdate.setTelephone("123"); // Invalid phone number
        customerToUpdate.setEmail("invalid-email"); // Invalid email
        
        // Test validation
        assertFalse(isValidName(customerToUpdate.getName()));
        assertFalse(isValidTelephone(customerToUpdate.getTelephone()));
        assertFalse(isValidEmail(customerToUpdate.getEmail()));
        
        // Restore original data
        customerToUpdate.setName(originalName);
        customerToUpdate.setTelephone(originalTelephone);
        customerToUpdate.setEmail(originalEmail);
        
        // Verify restoration
        assertTrue(isValidName(customerToUpdate.getName()));
        assertTrue(isValidTelephone(customerToUpdate.getTelephone()));
        assertTrue(isValidEmail(customerToUpdate.getEmail()));
    }
    
    @Test
    @Order(9)
    @DisplayName("Test Update Customer - Duplicate Account Number")
    void testUpdateCustomerDuplicateAccountNumber() {
        Customer customer1 = findCustomerById(customerList, 1);
        Customer customer2 = findCustomerById(customerList, 2);
        
        assertNotNull(customer1);
        assertNotNull(customer2);
        
        String originalAccountNumber = customer1.getAccountNumber();
        
        // Try to update customer1 with customer2's account number
        customer1.setAccountNumber(customer2.getAccountNumber());
        
        // Check for duplicate
        assertTrue(hasDuplicateAccountNumber(customerList, customer1.getAccountNumber()));
        
        // Restore original account number
        customer1.setAccountNumber(originalAccountNumber);
        
        // Verify no duplicate
        assertFalse(hasDuplicateAccountNumber(customerList, originalAccountNumber));
    }
    
    @Test
    @Order(10)
    @DisplayName("Test Update Customer - All Fields")
    void testUpdateCustomerAllFields() {
        Customer customerToUpdate = findCustomerById(customerList, 1);
        assertNotNull(customerToUpdate);
        
        // Store original values
        String originalAccountNumber = customerToUpdate.getAccountNumber();
        String originalName = customerToUpdate.getName();
        String originalAddress = customerToUpdate.getAddress();
        String originalTelephone = customerToUpdate.getTelephone();
        String originalEmail = customerToUpdate.getEmail();
        
        // Update all fields
        customerToUpdate.setAccountNumber("ACC001_UPDATED");
        customerToUpdate.setName("John Doe Updated");
        customerToUpdate.setAddress("123 Updated Street, Updated City");
        customerToUpdate.setTelephone("0712345679");
        customerToUpdate.setEmail("john.updated@email.com");
        
        // Verify all updates
        assertEquals("ACC001_UPDATED", customerToUpdate.getAccountNumber());
        assertEquals("John Doe Updated", customerToUpdate.getName());
        assertEquals("123 Updated Street, Updated City", customerToUpdate.getAddress());
        assertEquals("0712345679", customerToUpdate.getTelephone());
        assertEquals("john.updated@email.com", customerToUpdate.getEmail());
        
        // Restore original values
        customerToUpdate.setAccountNumber(originalAccountNumber);
        customerToUpdate.setName(originalName);
        customerToUpdate.setAddress(originalAddress);
        customerToUpdate.setTelephone(originalTelephone);
        customerToUpdate.setEmail(originalEmail);
    }
    
    @Test
    @Order(11)
    @DisplayName("Test Search Customer - Empty Results")
    void testSearchCustomerEmptyResults() {
        // Search with non-matching criteria
        Customer foundCustomer = searchCustomerByAccountNumber(customerList, "NONEXISTENT");
        assertNull(foundCustomer);
        
        Customer foundCustomer2 = searchCustomerByName(customerList, "Non Existent Name");
        assertNull(foundCustomer2);
        
        List<Customer> foundCustomers = searchCustomersByPartialName(customerList, "XYZ");
        assertNotNull(foundCustomers);
        assertEquals(0, foundCustomers.size());
    }
    
    @Test
    @Order(12)
    @DisplayName("Test Update Customer - Null Values")
    void testUpdateCustomerNullValues() {
        Customer customerToUpdate = findCustomerById(customerList, 1);
        assertNotNull(customerToUpdate);
        
        // Store original values
        String originalName = customerToUpdate.getName();
        String originalEmail = customerToUpdate.getEmail();
        
        // Try to update with null values
        customerToUpdate.setName(null);
        customerToUpdate.setEmail(null);
        
        // Test validation
        assertFalse(isValidName(customerToUpdate.getName()));
        assertFalse(isValidEmail(customerToUpdate.getEmail()));
        
        // Restore original values
        customerToUpdate.setName(originalName);
        customerToUpdate.setEmail(originalEmail);
        
        // Verify restoration
        assertTrue(isValidName(customerToUpdate.getName()));
        assertTrue(isValidEmail(customerToUpdate.getEmail()));
    }
    
    // Helper methods
    private Customer findCustomerById(List<Customer> customers, int id) {
        return customers.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    private Customer searchCustomerByAccountNumber(List<Customer> customers, String accountNumber) {
        return customers.stream()
                .filter(c -> c.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElse(null);
    }
    
    private Customer searchCustomerByName(List<Customer> customers, String name) {
        return customers.stream()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
    
    private Customer searchCustomerByTelephone(List<Customer> customers, String telephone) {
        return customers.stream()
                .filter(c -> c.getTelephone().equals(telephone))
                .findFirst()
                .orElse(null);
    }
    
    private Customer searchCustomerByEmail(List<Customer> customers, String email) {
        return customers.stream()
                .filter(c -> c.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }
    
    private List<Customer> searchCustomersByPartialName(List<Customer> customers, String partialName) {
        return customers.stream()
                .filter(c -> c.getName().toLowerCase().contains(partialName.toLowerCase()))
                .toList();
    }
    
    private boolean hasDuplicateAccountNumber(List<Customer> customers, String accountNumber) {
        long count = customers.stream()
                .filter(c -> c.getAccountNumber().equals(accountNumber))
                .count();
        return count > 1;
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
}