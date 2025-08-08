package com.example.dinithi_pahana_edu.reports;

import com.example.dinithi_pahana_edu.service.BillService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit Tests for Top Customers Report Functionality
 * Tests the top customers report features including ranking and data validation
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TopCustomersReportTest {
    
    private BillService billService;
    
    @BeforeEach
    void setUp() {
        billService = new BillService();
    }
    
    @Test
    @Order(1)
    @DisplayName("Test Get Top Customers - Top 10")
    void testGetTopCustomersTop10() {
        List<Object[]> topCustomers = billService.getTopCustomers(10);
        
        assertNotNull(topCustomers, "Top customers list should not be null");
        assertTrue(topCustomers.size() <= 10, "Should not return more than 10 customers");
        
        // Verify each row has correct structure
        for (Object[] row : topCustomers) {
            assertNotNull(row, "Row should not be null");
            assertEquals(4, row.length, "Each row should have 4 elements (id, name, account_number, total_spent)");
            assertNotNull(row[0], "Customer ID should not be null");
            assertNotNull(row[1], "Customer name should not be null");
            assertNotNull(row[2], "Account number should not be null");
            assertNotNull(row[3], "Total spent should not be null");
        }
    }
    
    @Test
    @Order(2)
    @DisplayName("Test Get Top Customers - Top 5")
    void testGetTopCustomersTop5() {
        List<Object[]> topCustomers = billService.getTopCustomers(5);
        
        assertNotNull(topCustomers, "Top customers list should not be null");
        assertTrue(topCustomers.size() <= 5, "Should not return more than 5 customers");
        
        // Verify data structure
        for (Object[] row : topCustomers) {
            assertNotNull(row, "Row should not be null");
            assertEquals(4, row.length, "Each row should have 4 elements");
        }
    }
    
    @Test
    @Order(3)
    @DisplayName("Test Get Top Customers - Top 1")
    void testGetTopCustomersTop1() {
        List<Object[]> topCustomers = billService.getTopCustomers(1);
        
        assertNotNull(topCustomers, "Top customers list should not be null");
        assertTrue(topCustomers.size() <= 1, "Should not return more than 1 customer");
        
        if (!topCustomers.isEmpty()) {
            Object[] row = topCustomers.get(0);
            assertNotNull(row, "Row should not be null");
            assertEquals(4, row.length, "Each row should have 4 elements");
        }
    }
    
    @Test
    @Order(4)
    @DisplayName("Test Get Top Customers - Zero Limit")
    void testGetTopCustomersZeroLimit() {
        List<Object[]> topCustomers = billService.getTopCustomers(0);
        
        assertNotNull(topCustomers, "Top customers list should not be null");
        assertTrue(topCustomers.isEmpty(), "Should return empty list for zero limit");
    }
    
    @Test
    @Order(5)
    @DisplayName("Test Get Top Customers - Large Limit")
    void testGetTopCustomersLargeLimit() {
        List<Object[]> topCustomers = billService.getTopCustomers(100);
        
        assertNotNull(topCustomers, "Top customers list should not be null");
        assertTrue(topCustomers.size() <= 100, "Should not return more than 100 customers");
        
        // Verify data structure
        for (Object[] row : topCustomers) {
            assertNotNull(row, "Row should not be null");
            assertEquals(4, row.length, "Each row should have 4 elements");
        }
    }
    
    @Test
    @Order(6)
    @DisplayName("Test Top Customers Data Validation")
    void testTopCustomersDataValidation() {
        List<Object[]> topCustomers = billService.getTopCustomers(10);
        
        assertNotNull(topCustomers, "Top customers should not be null");
        
        for (Object[] row : topCustomers) {
            assertNotNull(row, "Row should not be null");
            assertEquals(4, row.length, "Each row should have 4 elements");
            
            // Verify customer ID is numeric
            if (row[0] != null) {
                assertTrue(row[0] instanceof Number, "Customer ID should be a number");
                int customerId = ((Number) row[0]).intValue();
                assertTrue(customerId > 0, "Customer ID should be positive");
            }
            
            // Verify customer name is string
            if (row[1] != null) {
                assertTrue(row[1] instanceof String, "Customer name should be a string");
                String customerName = (String) row[1];
                assertFalse(customerName.trim().isEmpty(), "Customer name should not be empty");
            }
            
            // Verify account number is string
            if (row[2] != null) {
                assertTrue(row[2] instanceof String, "Account number should be a string");
                String accountNumber = (String) row[2];
                assertFalse(accountNumber.trim().isEmpty(), "Account number should not be empty");
            }
            
            // Verify total spent is numeric and non-negative
            if (row[3] != null) {
                assertTrue(row[3] instanceof Number, "Total spent should be a number");
                double totalSpent = ((Number) row[3]).doubleValue();
                assertTrue(totalSpent >= 0, "Total spent should be non-negative");
            }
        }
    }
    
    @Test
    @Order(7)
    @DisplayName("Test Top Customers Ranking Order")
    void testTopCustomersRankingOrder() {
        List<Object[]> topCustomers = billService.getTopCustomers(10);
        
        assertNotNull(topCustomers, "Top customers should not be null");
        
        // Verify customers are ordered by total spent (descending)
        for (int i = 0; i < topCustomers.size() - 1; i++) {
            Object[] currentRow = topCustomers.get(i);
            Object[] nextRow = topCustomers.get(i + 1);
            
            if (currentRow[3] != null && nextRow[3] != null) {
                double currentSpent = ((Number) currentRow[3]).doubleValue();
                double nextSpent = ((Number) nextRow[3]).doubleValue();
                
                assertTrue(currentSpent >= nextSpent, 
                    "Customers should be ordered by total spent (descending). " +
                    "Customer " + (i+1) + " spent " + currentSpent + 
                    " but customer " + (i+2) + " spent " + nextSpent);
            }
        }
    }
    
    @Test
    @Order(8)
    @DisplayName("Test Top Customers Summary Calculations")
    void testTopCustomersSummaryCalculations() {
        List<Object[]> topCustomers = billService.getTopCustomers(10);
        
        assertNotNull(topCustomers, "Top customers should not be null");
        
        int totalCustomers = 0;
        double totalSpent = 0.0;
        
        for (Object[] row : topCustomers) {
            if (row[3] != null) {
                totalCustomers++;
                totalSpent += ((Number) row[3]).doubleValue();
            }
        }
        
        double averageSpent = totalCustomers > 0 ? totalSpent / totalCustomers : 0.0;
        
        assertTrue(totalCustomers >= 0, "Total customers should be non-negative");
        assertTrue(totalSpent >= 0, "Total spent should be non-negative");
        assertTrue(averageSpent >= 0, "Average spent should be non-negative");
    }
    
    @Test
    @Order(9)
    @DisplayName("Test Top Customers Performance")
    void testTopCustomersPerformance() {
        long startTime = System.currentTimeMillis();
        
        List<Object[]> topCustomers = billService.getTopCustomers(10);
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        assertNotNull(topCustomers, "Top customers should not be null");
        assertTrue(duration < 5000, "Query should complete within 5 seconds: " + duration + "ms");
    }
    
    @Test
    @Order(10)
    @DisplayName("Test Top Customers Edge Cases")
    void testTopCustomersEdgeCases() {
        // Test with very large limit
        List<Object[]> largeLimitCustomers = billService.getTopCustomers(1000);
        assertNotNull(largeLimitCustomers, "Should handle large limit");
        
        // Test multiple calls to ensure consistency
        List<Object[]> firstCall = billService.getTopCustomers(5);
        List<Object[]> secondCall = billService.getTopCustomers(5);
        
        assertNotNull(firstCall, "First call should not be null");
        assertNotNull(secondCall, "Second call should not be null");
        assertEquals(firstCall.size(), secondCall.size(), "Both calls should return same number of customers");
    }
    
    @Test
    @Order(11)
    @DisplayName("Test Top Customers Data Consistency")
    void testTopCustomersDataConsistency() {
        List<Object[]> topCustomers = billService.getTopCustomers(10);
        
        assertNotNull(topCustomers, "Top customers should not be null");
        
        for (Object[] row : topCustomers) {
            assertNotNull(row, "Row should not be null");
            assertEquals(4, row.length, "Each row should have 4 elements");
            
            // Verify all required fields are present
            assertNotNull(row[0], "Customer ID should not be null");
            assertNotNull(row[1], "Customer name should not be null");
            assertNotNull(row[2], "Account number should not be null");
            assertNotNull(row[3], "Total spent should not be null");
            
            // Verify data types
            assertTrue(row[0] instanceof Number, "Customer ID should be a number");
            assertTrue(row[1] instanceof String, "Customer name should be a string");
            assertTrue(row[2] instanceof String, "Account number should be a string");
            assertTrue(row[3] instanceof Number, "Total spent should be a number");
        }
    }
    
    @Test
    @Order(12)
    @DisplayName("Test Top Customers Empty Database")
    void testTopCustomersEmptyDatabase() {
        // This test assumes the database might be empty
        List<Object[]> topCustomers = billService.getTopCustomers(10);
        
        assertNotNull(topCustomers, "Should return empty list if no customers exist");
        
        // If there are customers, verify structure
        for (Object[] row : topCustomers) {
            assertNotNull(row, "Row should not be null");
            assertEquals(4, row.length, "Each row should have 4 elements");
        }
    }
} 