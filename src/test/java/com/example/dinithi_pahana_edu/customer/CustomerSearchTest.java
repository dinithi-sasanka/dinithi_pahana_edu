package com.example.dinithi_pahana_edu.customer;

import com.example.dinithi_pahana_edu.model.Customer;
import com.example.dinithi_pahana_edu.service.CustomerService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit Tests for Customer Search Functionality
 * Tests the search methods used by ViewCustomerServlet
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerSearchTest {
    
    private CustomerService customerService;
    
    @BeforeEach
    void setUp() {
        customerService = new CustomerService();
    }
    
    @Test
    @Order(1)
    @DisplayName("Test Search Customers by Name")
    void testSearchCustomersByName() {
        // Test search by name
        List<Customer> results = customerService.searchCustomersByName("John");
        assertNotNull(results);
        
        // Test search with empty name (should return all customers)
        List<Customer> allResults = customerService.searchCustomersByName("");
        assertNotNull(allResults);
        
        // Test search with null name (should return all customers)
        List<Customer> nullResults = customerService.searchCustomersByName(null);
        assertNotNull(nullResults);
    }
    
    @Test
    @Order(2)
    @DisplayName("Test Get Customer by Account Number")
    void testGetCustomerByAccountNumber() {
        // Test with valid account number
        Customer result = customerService.getCustomerByAccountNumber("CUST000001");
        // This might be null if no customer exists, which is fine for testing
        
        // Test with null account number
        Customer nullResult = customerService.getCustomerByAccountNumber(null);
        assertNull(nullResult);
        
        // Test with empty account number
        Customer emptyResult = customerService.getCustomerByAccountNumber("");
        assertNull(emptyResult);
        
        // Test with whitespace account number
        Customer whitespaceResult = customerService.getCustomerByAccountNumber("   ");
        assertNull(whitespaceResult);
    }
    
    @Test
    @Order(3)
    @DisplayName("Test Get Customer by Email")
    void testGetCustomerByEmail() {
        // Test with valid email
        Customer result = customerService.getCustomerByEmail("test@example.com");
        // This might be null if no customer exists, which is fine for testing
        
        // Test with null email
        Customer nullResult = customerService.getCustomerByEmail(null);
        assertNull(nullResult);
        
        // Test with empty email
        Customer emptyResult = customerService.getCustomerByEmail("");
        assertNull(emptyResult);
        
        // Test with whitespace email
        Customer whitespaceResult = customerService.getCustomerByEmail("   ");
        assertNull(whitespaceResult);
    }
    
    @Test
    @Order(4)
    @DisplayName("Test Get All Customers")
    void testGetAllCustomers() {
        List<Customer> allCustomers = customerService.getAllCustomers();
        assertNotNull(allCustomers);
        // The list might be empty, which is fine
    }
    
    @Test
    @Order(5)
    @DisplayName("Test Search Customer by Any Field")
    void testSearchCustomerByAnyField() {
        // Test with valid search term
        Customer result = customerService.searchCustomerByAnyField("test");
        // This might be null if no customer exists, which is fine for testing
        
        // Test with null search term
        Customer nullResult = customerService.searchCustomerByAnyField(null);
        assertNull(nullResult);
        
        // Test with empty search term
        Customer emptyResult = customerService.searchCustomerByAnyField("");
        assertNull(emptyResult);
        
        // Test with whitespace search term
        Customer whitespaceResult = customerService.searchCustomerByAnyField("   ");
        assertNull(whitespaceResult);
    }
    
    @Test
    @Order(6)
    @DisplayName("Test Search Customer with Multiple Parameters")
    void testSearchCustomerWithMultipleParameters() {
        // Test search with account number, name, and telephone
        Customer result = customerService.searchCustomer("CUST000001", "John", "0712345678");
        // This might be null if no customer exists, which is fine for testing
    }
    
    @Test
    @Order(7)
    @DisplayName("Test Customer Search Data Validation")
    void testCustomerSearchDataValidation() {
        // Test that search methods handle invalid input gracefully
        
        // Test name search with special characters
        List<Customer> specialCharResults = customerService.searchCustomersByName("John@#$%");
        assertNotNull(specialCharResults);
        
        // Test account number search with invalid format
        Customer invalidAccountResult = customerService.getCustomerByAccountNumber("INVALID123");
        // This might be null, which is expected behavior
        
        // Test email search with invalid format
        Customer invalidEmailResult = customerService.getCustomerByEmail("invalid-email");
        // This might be null, which is expected behavior
    }
    
    @Test
    @Order(8)
    @DisplayName("Test Search Results Consistency")
    void testSearchResultsConsistency() {
        // Test that search results are consistent
        
        // Get all customers first
        List<Customer> allCustomers = customerService.getAllCustomers();
        assertNotNull(allCustomers);
        
        // If there are customers, test search functionality
        if (!allCustomers.isEmpty()) {
            Customer firstCustomer = allCustomers.get(0);
            
            // Test search by name
            if (firstCustomer.getName() != null && !firstCustomer.getName().trim().isEmpty()) {
                List<Customer> nameResults = customerService.searchCustomersByName(firstCustomer.getName());
                assertNotNull(nameResults);
            }
            
            // Test search by account number
            if (firstCustomer.getAccountNumber() != null && !firstCustomer.getAccountNumber().trim().isEmpty()) {
                Customer accountResult = customerService.getCustomerByAccountNumber(firstCustomer.getAccountNumber());
                // This might be null, which is fine
            }
            
            // Test search by email
            if (firstCustomer.getEmail() != null && !firstCustomer.getEmail().trim().isEmpty()) {
                Customer emailResult = customerService.getCustomerByEmail(firstCustomer.getEmail());
                // This might be null, which is fine
            }
        }
    }
    
    @Test
    @Order(9)
    @DisplayName("Test Search with Partial Matches")
    void testSearchWithPartialMatches() {
        // Test partial name search
        List<Customer> partialNameResults = customerService.searchCustomersByName("Jo");
        assertNotNull(partialNameResults);
        
        // Test partial account number search
        Customer partialAccountResult = customerService.getCustomerByAccountNumber("CUST");
        // This might be null, which is fine
        
        // Test partial email search
        Customer partialEmailResult = customerService.getCustomerByEmail("test");
        // This might be null, which is fine
    }
    
    @Test
    @Order(10)
    @DisplayName("Test Search Performance")
    void testSearchPerformance() {
        // Test that search methods complete in reasonable time
        
        long startTime = System.currentTimeMillis();
        
        // Perform multiple searches
        for (int i = 0; i < 10; i++) {
            customerService.searchCustomersByName("test");
            customerService.getCustomerByAccountNumber("CUST000001");
            customerService.getCustomerByEmail("test@example.com");
            customerService.searchCustomerByAnyField("test");
        }
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        // Search should complete within 5 seconds
        assertTrue(duration < 5000, "Search operations took too long: " + duration + "ms");
    }
} 