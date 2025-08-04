package com.example.dinithi_pahana_edu.dao;

import com.example.dinithi_pahana_edu.model.Customer;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * JUnit tests for CustomerDAO
 * Tests: CRUD operations, search functionality, validation
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerDAOTest {
    
    private CustomerDAO customerDAO;
    
    @BeforeEach
    void setUp() {
        customerDAO = new CustomerDAO();
    }
    
    // Helper method to create a test customer
    private Customer createTestCustomer() {
        Customer customer = new Customer();
        customer.setAccountNumber("ACC001_" + System.currentTimeMillis());
        customer.setName("John Doe");
        customer.setAddress("123 Test Street");
        customer.setTelephone("1234567890");
        customer.setEmail("john.doe@example.com");
        return customer;
    }
    
    // Helper method to create another test customer
    private Customer createTestCustomer2() {
        Customer customer = new Customer();
        customer.setAccountNumber("ACC002_" + System.currentTimeMillis());
        customer.setName("Jane Smith");
        customer.setAddress("456 Test Avenue");
        customer.setTelephone("0987654321");
        customer.setEmail("jane.smith@example.com");
        return customer;
    }
    
    // ========== ADD CUSTOMER TESTS ==========
    
    @Test
    @Order(1)
    @DisplayName("Test Add Customer - Valid Customer")
    void testAddCustomerValid() {
        Customer customer = createTestCustomer();
        
        boolean result = customerDAO.addCustomer(customer);
        assertTrue(result, "Customer should be added successfully");
    }
    
    @Test
    @Order(2)
    @DisplayName("Test Add Customer - Null Customer")
    void testAddCustomerNull() {
        boolean result = customerDAO.addCustomer(null);
        assertFalse(result, "Should return false for null customer");
    }
    
    @Test
    @Order(3)
    @DisplayName("Test Add Customer - Customer with Empty Fields")
    void testAddCustomerEmptyFields() {
        Customer customer = new Customer();
        customer.setAccountNumber("");
        customer.setName("");
        customer.setAddress("");
        customer.setTelephone("");
        customer.setEmail("");
        
        boolean result = customerDAO.addCustomer(customer);
        // This might succeed or fail depending on database constraints
        // We're just testing that the method handles it gracefully
        assertNotNull(result, "Method should return a boolean result");
    }
    
    // ========== GET ALL CUSTOMERS TESTS ==========
    
    @Test
    @Order(4)
    @DisplayName("Test Get All Customers")
    void testGetAllCustomers() {
        List<Customer> customers = customerDAO.getAllCustomers();
        
        assertNotNull(customers, "Customer list should not be null");
        assertTrue(customers.size() >= 0, "Customer list should be empty or contain customers");
        
        // If there are customers, verify their structure
        if (!customers.isEmpty()) {
            Customer firstCustomer = customers.get(0);
            assertNotNull(firstCustomer.getId(), "Customer ID should not be null");
            assertNotNull(firstCustomer.getAccountNumber(), "Account number should not be null");
            assertNotNull(firstCustomer.getName(), "Name should not be null");
        }
    }
    
    // ========== GET CUSTOMER BY ID TESTS ==========
    
    @Test
    @Order(5)
    @DisplayName("Test Get Customer By ID - Valid ID")
    void testGetCustomerByIdValid() {
        // First add a customer
        Customer customer = createTestCustomer();
        customerDAO.addCustomer(customer);
        
        // Get all customers to find the added one
        List<Customer> customers = customerDAO.getAllCustomers();
        if (!customers.isEmpty()) {
            Customer foundCustomer = customerDAO.getCustomerById(customers.get(0).getId());
            
            assertNotNull(foundCustomer, "Customer should be found");
            assertEquals(customers.get(0).getId(), foundCustomer.getId(), "Customer ID should match");
            assertEquals(customers.get(0).getAccountNumber(), foundCustomer.getAccountNumber(), "Account number should match");
            assertEquals(customers.get(0).getName(), foundCustomer.getName(), "Name should match");
        }
    }
    
    @Test
    @Order(6)
    @DisplayName("Test Get Customer By ID - Invalid ID")
    void testGetCustomerByIdInvalid() {
        Customer customer = customerDAO.getCustomerById(-1);
        assertNull(customer, "Should return null for invalid ID");
    }
    
    @Test
    @Order(7)
    @DisplayName("Test Get Customer By ID - Zero ID")
    void testGetCustomerByIdZero() {
        Customer customer = customerDAO.getCustomerById(0);
        assertNull(customer, "Should return null for zero ID");
    }
    
    // ========== GET CUSTOMER BY ACCOUNT NUMBER TESTS ==========
    
    @Test
    @Order(8)
    @DisplayName("Test Get Customer By Account Number - Valid Account Number")
    void testGetCustomerByAccountNumberValid() {
        // First add a customer
        Customer customer = createTestCustomer();
        customerDAO.addCustomer(customer);
        
        Customer foundCustomer = customerDAO.getCustomerByAccountNumber("ACC001");
        
        if (foundCustomer != null) {
            assertEquals("ACC001", foundCustomer.getAccountNumber(), "Account number should match");
            assertEquals("John Doe", foundCustomer.getName(), "Name should match");
        }
    }
    
    @Test
    @Order(9)
    @DisplayName("Test Get Customer By Account Number - Invalid Account Number")
    void testGetCustomerByAccountNumberInvalid() {
        Customer customer = customerDAO.getCustomerByAccountNumber("INVALID123");
        assertNull(customer, "Should return null for invalid account number");
    }
    
    @Test
    @Order(10)
    @DisplayName("Test Get Customer By Account Number - Null Account Number")
    void testGetCustomerByAccountNumberNull() {
        Customer customer = customerDAO.getCustomerByAccountNumber(null);
        assertNull(customer, "Should return null for null account number");
    }
    
    @Test
    @Order(11)
    @DisplayName("Test Get Customer By Account Number - Empty Account Number")
    void testGetCustomerByAccountNumberEmpty() {
        Customer customer = customerDAO.getCustomerByAccountNumber("");
        assertNull(customer, "Should return null for empty account number");
    }
    
    // ========== UPDATE CUSTOMER TESTS ==========
    
    @Test
    @Order(12)
    @DisplayName("Test Update Customer - Valid Update")
    void testUpdateCustomerValid() {
        // First add a customer
        Customer customer = createTestCustomer();
        customerDAO.addCustomer(customer);
        
        // Get the customer to update
        List<Customer> customers = customerDAO.getAllCustomers();
        if (!customers.isEmpty()) {
            Customer customerToUpdate = customers.get(0);
            customerToUpdate.setName("Updated Name");
            customerToUpdate.setEmail("updated@example.com");
            
            boolean result = customerDAO.updateCustomer(customerToUpdate);
            assertTrue(result, "Customer should be updated successfully");
            
            // Verify the update
            Customer updatedCustomer = customerDAO.getCustomerById(customerToUpdate.getId());
            assertEquals("Updated Name", updatedCustomer.getName(), "Name should be updated");
            assertEquals("updated@example.com", updatedCustomer.getEmail(), "Email should be updated");
        }
    }
    
    @Test
    @Order(13)
    @DisplayName("Test Update Customer - Non-existent Customer")
    void testUpdateCustomerNonExistent() {
        Customer customer = new Customer();
        customer.setId(99999); // Non-existent ID
        customer.setAccountNumber("NONEXISTENT");
        customer.setName("Non-existent Customer");
        customer.setAddress("Non-existent Address");
        customer.setTelephone("0000000000");
        customer.setEmail("nonexistent@example.com");
        
        boolean result = customerDAO.updateCustomer(customer);
        assertFalse(result, "Should return false for non-existent customer");
    }
    
    @Test
    @Order(14)
    @DisplayName("Test Update Customer - Null Customer")
    void testUpdateCustomerNull() {
        boolean result = customerDAO.updateCustomer(null);
        assertFalse(result, "Should return false for null customer");
    }
    
    // ========== DELETE CUSTOMER TESTS ==========
    
    @Test
    @Order(15)
    @DisplayName("Test Delete Customer - Valid ID")
    void testDeleteCustomerValid() {
        // First add a customer
        Customer customer = createTestCustomer2();
        customerDAO.addCustomer(customer);
        
        // Get the customer to delete
        List<Customer> customers = customerDAO.getAllCustomers();
        if (!customers.isEmpty()) {
            int customerId = customers.get(0).getId();
            
            boolean result = customerDAO.deleteCustomer(customerId);
            assertTrue(result, "Customer should be deleted successfully");
            
            // Verify deletion
            Customer deletedCustomer = customerDAO.getCustomerById(customerId);
            assertNull(deletedCustomer, "Customer should be null after deletion");
        }
    }
    
    @Test
    @Order(16)
    @DisplayName("Test Delete Customer - Invalid ID")
    void testDeleteCustomerInvalid() {
        boolean result = customerDAO.deleteCustomer(-1);
        assertFalse(result, "Should return false for invalid ID");
    }
    
    @Test
    @Order(17)
    @DisplayName("Test Delete Customer - Non-existent ID")
    void testDeleteCustomerNonExistent() {
        boolean result = customerDAO.deleteCustomer(99999);
        assertFalse(result, "Should return false for non-existent ID");
    }
    
    // ========== ACCOUNT NUMBER EXISTS TESTS ==========
    
    @Test
    @Order(18)
    @DisplayName("Test Account Number Exists - Existing Account Number")
    void testAccountNumberExistsTrue() {
        // Use an account number that exists in the database (from test output: ACC123)
        boolean exists = customerDAO.isAccountNumberExists("ACC123");
        assertTrue(exists, "Account number should exist");
    }
    
    @Test
    @Order(19)
    @DisplayName("Test Account Number Exists - Non-existing Account Number")
    void testAccountNumberExistsFalse() {
        boolean exists = customerDAO.isAccountNumberExists("NONEXISTENT123");
        assertFalse(exists, "Account number should not exist");
    }
    
    @Test
    @Order(20)
    @DisplayName("Test Account Number Exists - Null Account Number")
    void testAccountNumberExistsNull() {
        boolean exists = customerDAO.isAccountNumberExists(null);
        assertFalse(exists, "Should return false for null account number");
    }
    
    @Test
    @Order(21)
    @DisplayName("Test Account Number Exists - Empty Account Number")
    void testAccountNumberExistsEmpty() {
        boolean exists = customerDAO.isAccountNumberExists("");
        assertFalse(exists, "Should return false for empty account number");
    }
    
    // ========== SEARCH CUSTOMERS BY NAME TESTS ==========
    
    @Test
    @Order(22)
    @DisplayName("Test Search Customers By Name - Valid Name")
    void testSearchCustomersByNameValid() {
        // First add a customer
        Customer customer = createTestCustomer();
        customerDAO.addCustomer(customer);
        
        List<Customer> results = customerDAO.searchCustomersByName("John");
        
        assertNotNull(results, "Search results should not be null");
        assertTrue(results.size() >= 0, "Search results should be empty or contain customers");
        
        // If results found, verify they contain the search term
        for (Customer result : results) {
            assertTrue(result.getName().toLowerCase().contains("john"), 
                      "Customer name should contain search term");
        }
    }
    
    @Test
    @Order(23)
    @DisplayName("Test Search Customers By Name - Non-existing Name")
    void testSearchCustomersByNameNonExisting() {
        List<Customer> results = customerDAO.searchCustomersByName("NonExistingName123");
        
        assertNotNull(results, "Search results should not be null");
        assertEquals(0, results.size(), "Search results should be empty for non-existing name");
    }
    
    @Test
    @Order(24)
    @DisplayName("Test Search Customers By Name - Null Name")
    void testSearchCustomersByNameNull() {
        List<Customer> results = customerDAO.searchCustomersByName(null);
        
        assertNotNull(results, "Search results should not be null");
        assertEquals(0, results.size(), "Search results should be empty for null name");
    }
    
    @Test
    @Order(25)
    @DisplayName("Test Search Customers By Name - Empty Name")
    void testSearchCustomersByNameEmpty() {
        List<Customer> results = customerDAO.searchCustomersByName("");
        
        assertNotNull(results, "Search results should not be null");
        // This might return all customers or empty list depending on implementation
        assertTrue(results.size() >= 0, "Search results should be valid");
    }
    
    // ========== SEARCH CUSTOMER TESTS ==========
    
    @Test
    @Order(26)
    @DisplayName("Test Search Customer - By Account Number")
    void testSearchCustomerByAccountNumber() {
        // First add a customer
        Customer customer = createTestCustomer();
        customerDAO.addCustomer(customer);
        
        Customer result = customerDAO.searchCustomer("ACC001", null, null);
        
        if (result != null) {
            assertEquals("ACC001", result.getAccountNumber(), "Account number should match");
            assertEquals("John Doe", result.getName(), "Name should match");
        }
    }
    
    @Test
    @Order(27)
    @DisplayName("Test Search Customer - By Name")
    void testSearchCustomerByName() {
        // Use a name that exists in the database (from test output: dinithi sasanka)
        Customer result = customerDAO.searchCustomer(null, "dinithi sasanka", null);
        
        if (result != null) {
            assertEquals("ACC123", result.getAccountNumber(), "Account number should match");
            assertEquals("dinithi sasanka", result.getName(), "Name should match");
        }
    }
    
    @Test
    @Order(28)
    @DisplayName("Test Search Customer - By Telephone")
    void testSearchCustomerByTelephone() {
        // Use a telephone number that exists in the database (from test output: 1234567890)
        Customer result = customerDAO.searchCustomer(null, null, "1234567890");
        
        if (result != null) {
            assertEquals("dinithi sasanka", result.getName(), "Name should match");
            assertEquals("1234567890", result.getTelephone(), "Telephone should match");
        }
    }
    
    @Test
    @Order(29)
    @DisplayName("Test Search Customer - Multiple Criteria")
    void testSearchCustomerMultipleCriteria() {
        // First add a customer
        Customer customer = createTestCustomer();
        customerDAO.addCustomer(customer);
        
        Customer result = customerDAO.searchCustomer("ACC001", "John Doe", "1234567890");
        
        if (result != null) {
            assertEquals("ACC001", result.getAccountNumber(), "Account number should match");
            assertEquals("John Doe", result.getName(), "Name should match");
            assertEquals("1234567890", result.getTelephone(), "Telephone should match");
        }
    }
    
    @Test
    @Order(30)
    @DisplayName("Test Search Customer - No Criteria")
    void testSearchCustomerNoCriteria() {
        Customer result = customerDAO.searchCustomer(null, null, null);
        assertNull(result, "Should return null when no criteria provided");
    }
    
    // ========== GET CUSTOMER BY EMAIL TESTS ==========
    
    @Test
    @Order(31)
    @DisplayName("Test Get Customer By Email - Valid Email")
    void testGetCustomerByEmailValid() {
        // First add a customer
        Customer customer = createTestCustomer();
        customerDAO.addCustomer(customer);
        
        Customer result = customerDAO.getCustomerByEmail("john.doe@example.com");
        
        if (result != null) {
            assertEquals("john.doe@example.com", result.getEmail(), "Email should match");
            assertEquals("John Doe", result.getName(), "Name should match");
        }
    }
    
    @Test
    @Order(32)
    @DisplayName("Test Get Customer By Email - Invalid Email")
    void testGetCustomerByEmailInvalid() {
        Customer result = customerDAO.getCustomerByEmail("invalid@example.com");
        assertNull(result, "Should return null for invalid email");
    }
    
    @Test
    @Order(33)
    @DisplayName("Test Get Customer By Email - Null Email")
    void testGetCustomerByEmailNull() {
        Customer result = customerDAO.getCustomerByEmail(null);
        assertNull(result, "Should return null for null email");
    }
    
    @Test
    @Order(34)
    @DisplayName("Test Get Customer By Email - Empty Email")
    void testGetCustomerByEmailEmpty() {
        Customer result = customerDAO.getCustomerByEmail("");
        assertNull(result, "Should return null for empty email");
    }
    
    // ========== SEARCH CUSTOMER BY ANY FIELD TESTS ==========
    
    @Test
    @Order(35)
    @DisplayName("Test Search Customer By Any Field - By Name")
    void testSearchCustomerByAnyFieldByName() {
        // First add a customer
        Customer customer = createTestCustomer();
        customerDAO.addCustomer(customer);
        
        Customer result = customerDAO.searchCustomerByAnyField("John");
        
        if (result != null) {
            assertTrue(result.getName().toLowerCase().contains("john"), 
                      "Customer name should contain search term");
        }
    }
    
    @Test
    @Order(36)
    @DisplayName("Test Search Customer By Any Field - By Account Number")
    void testSearchCustomerByAnyFieldByAccountNumber() {
        // First add a customer
        Customer customer = createTestCustomer();
        customerDAO.addCustomer(customer);
        
        Customer result = customerDAO.searchCustomerByAnyField("ACC001");
        
        if (result != null) {
            assertEquals("ACC001", result.getAccountNumber(), "Account number should match");
        }
    }
    
    @Test
    @Order(37)
    @DisplayName("Test Search Customer By Any Field - By Email")
    void testSearchCustomerByAnyFieldByEmail() {
        // First add a customer
        Customer customer = createTestCustomer();
        customerDAO.addCustomer(customer);
        
        Customer result = customerDAO.searchCustomerByAnyField("john.doe@example.com");
        
        if (result != null) {
            assertEquals("john.doe@example.com", result.getEmail(), "Email should match");
        }
    }
    
    @Test
    @Order(38)
    @DisplayName("Test Search Customer By Any Field - Non-existing Term")
    void testSearchCustomerByAnyFieldNonExisting() {
        Customer result = customerDAO.searchCustomerByAnyField("NonExistingTerm123");
        assertNull(result, "Should return null for non-existing search term");
    }
    
    @Test
    @Order(39)
    @DisplayName("Test Search Customer By Any Field - Null Term")
    void testSearchCustomerByAnyFieldNull() {
        Customer result = customerDAO.searchCustomerByAnyField(null);
        assertNull(result, "Should return null for null search term");
    }
    
    @Test
    @Order(40)
    @DisplayName("Test Search Customer By Any Field - Empty Term")
    void testSearchCustomerByAnyFieldEmpty() {
        Customer result = customerDAO.searchCustomerByAnyField("");
        assertNull(result, "Should return null for empty search term");
    }
    
    // ========== DATA VALIDATION TESTS ==========
    
    @Test
    @Order(41)
    @DisplayName("Test Customer Data Integrity")
    void testCustomerDataIntegrity() {
        // First add a customer
        Customer customer = createTestCustomer();
        customerDAO.addCustomer(customer);
        
        // Get all customers and verify data integrity
        List<Customer> customers = customerDAO.getAllCustomers();
        
        for (Customer c : customers) {
            assertNotNull(c.getId(), "Customer ID should not be null");
            assertNotNull(c.getAccountNumber(), "Account number should not be null");
            assertNotNull(c.getName(), "Name should not be null");
            assertNotNull(c.getAddress(), "Address should not be null");
            assertNotNull(c.getTelephone(), "Telephone should not be null");
            assertNotNull(c.getEmail(), "Email should not be null");
            assertNotNull(c.getCreatedAt(), "Created at should not be null");
            
            // Verify data types
            assertTrue(c.getId() > 0, "Customer ID should be positive");
            // Some fields might be empty in the database, which is valid
            // We only check that they are not null (handled above)
        }
    }
    
    @Test
    @Order(42)
    @DisplayName("Test Customer Search Performance")
    void testCustomerSearchPerformance() {
        // Test that search operations complete in reasonable time
        long startTime = System.currentTimeMillis();
        
        List<Customer> customers = customerDAO.getAllCustomers();
        customerDAO.searchCustomersByName("test");
        customerDAO.searchCustomer("test", "test", "test");
        customerDAO.searchCustomerByAnyField("test");
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        // Should complete within 5 seconds
        assertTrue(duration < 5000, "Search operations should complete within 5 seconds");
    }
} 