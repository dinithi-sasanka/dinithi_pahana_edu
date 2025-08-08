package com.example.dinithi_pahana_edu.reports;

import com.example.dinithi_pahana_edu.service.BillService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit Tests for Most Sold Items Report Functionality
 * Tests the most sold items report features including search functionality
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MostSoldItemsReportTest {
    
    private BillService billService;
    
    @BeforeEach
    void setUp() {
        billService = new BillService();
    }
    
    @Test
    @Order(1)
    @DisplayName("Test Get Most Sold Items - Top 10")
    void testGetMostSoldItemsTop10() {
        List<Object[]> mostSoldItems = billService.getMostSoldItems(10);
        
        assertNotNull(mostSoldItems, "Most sold items list should not be null");
        assertTrue(mostSoldItems.size() <= 10, "Should not return more than 10 items");
        
        // Verify each row has correct structure
        for (Object[] row : mostSoldItems) {
            assertNotNull(row, "Row should not be null");
            assertEquals(3, row.length, "Each row should have 3 elements (id, name, total_sold)");
            assertNotNull(row[0], "Item ID should not be null");
            assertNotNull(row[1], "Item name should not be null");
            assertNotNull(row[2], "Total sold should not be null");
        }
    }
    
    @Test
    @Order(2)
    @DisplayName("Test Get Most Sold Items - Top 5")
    void testGetMostSoldItemsTop5() {
        List<Object[]> mostSoldItems = billService.getMostSoldItems(5);
        
        assertNotNull(mostSoldItems, "Most sold items list should not be null");
        assertTrue(mostSoldItems.size() <= 5, "Should not return more than 5 items");
        
        // Verify data structure
        for (Object[] row : mostSoldItems) {
            assertNotNull(row, "Row should not be null");
            assertEquals(3, row.length, "Each row should have 3 elements");
        }
    }
    
    @Test
    @Order(3)
    @DisplayName("Test Get Most Sold Items by Search - Valid Search")
    void testGetMostSoldItemsBySearchValid() {
        // Test with a common search term
        List<Object[]> searchResults = billService.getMostSoldItemsBySearch("book", 20);
        
        assertNotNull(searchResults, "Search results should not be null");
        
        // Verify each row has correct structure
        for (Object[] row : searchResults) {
            assertNotNull(row, "Row should not be null");
            assertEquals(3, row.length, "Each row should have 3 elements (id, name, total_sold)");
            assertNotNull(row[0], "Item ID should not be null");
            assertNotNull(row[1], "Item name should not be null");
            assertNotNull(row[2], "Total sold should not be null");
        }
    }
    
    @Test
    @Order(4)
    @DisplayName("Test Get Most Sold Items by Search - Empty Search")
    void testGetMostSoldItemsBySearchEmpty() {
        List<Object[]> searchResults = billService.getMostSoldItemsBySearch("", 10);
        
        assertNotNull(searchResults, "Empty search should return results");
        
        // Should return items even with empty search
        for (Object[] row : searchResults) {
            assertNotNull(row, "Row should not be null");
            assertEquals(3, row.length, "Each row should have 3 elements");
        }
    }
    
    @Test
    @Order(5)
    @DisplayName("Test Get Most Sold Items by Search - Null Search")
    void testGetMostSoldItemsBySearchNull() {
        List<Object[]> searchResults = billService.getMostSoldItemsBySearch(null, 10);
        
        assertNotNull(searchResults, "Null search should return results");
        
        // Should return items even with null search
        for (Object[] row : searchResults) {
            assertNotNull(row, "Row should not be null");
            assertEquals(3, row.length, "Each row should have 3 elements");
        }
    }
    
    @Test
    @Order(6)
    @DisplayName("Test Get Most Sold Items by Search - Non-existent Item")
    void testGetMostSoldItemsBySearchNonExistent() {
        List<Object[]> searchResults = billService.getMostSoldItemsBySearch("nonexistentitem12345", 10);
        
        assertNotNull(searchResults, "Non-existent search should return empty list");
        assertTrue(searchResults.isEmpty(), "Non-existent search should return empty list");
    }
    
    @Test
    @Order(7)
    @DisplayName("Test Get Most Sold Items by Search - Item ID Search")
    void testGetMostSoldItemsBySearchItemId() {
        // First get some items to test with
        List<Object[]> allItems = billService.getMostSoldItems(5);
        
        if (!allItems.isEmpty()) {
            Object[] firstItem = allItems.get(0);
            String itemId = firstItem[0].toString();
            
            List<Object[]> searchResults = billService.getMostSoldItemsBySearch(itemId, 10);
            
            assertNotNull(searchResults, "Item ID search should return results");
            
            // Should find the item by ID
            boolean found = false;
            for (Object[] row : searchResults) {
                if (row[0].toString().equals(itemId)) {
                    found = true;
                    break;
                }
            }
            
            assertTrue(found, "Should find item by ID search");
        }
    }
    
    @Test
    @Order(8)
    @DisplayName("Test Most Sold Items Data Validation")
    void testMostSoldItemsDataValidation() {
        List<Object[]> mostSoldItems = billService.getMostSoldItems(10);
        
        assertNotNull(mostSoldItems, "Most sold items should not be null");
        
        for (Object[] row : mostSoldItems) {
            assertNotNull(row, "Row should not be null");
            assertEquals(3, row.length, "Each row should have 3 elements");
            
            // Verify item ID is numeric
            if (row[0] != null) {
                assertTrue(row[0] instanceof Number, "Item ID should be a number");
                int itemId = ((Number) row[0]).intValue();
                assertTrue(itemId > 0, "Item ID should be positive");
            }
            
            // Verify item name is string
            if (row[1] != null) {
                assertTrue(row[1] instanceof String, "Item name should be a string");
                String itemName = (String) row[1];
                assertFalse(itemName.trim().isEmpty(), "Item name should not be empty");
            }
            
            // Verify total sold is numeric and non-negative
            if (row[2] != null) {
                assertTrue(row[2] instanceof Number, "Total sold should be a number");
                int totalSold = ((Number) row[2]).intValue();
                assertTrue(totalSold >= 0, "Total sold should be non-negative");
            }
        }
    }
    
    @Test
    @Order(9)
    @DisplayName("Test Most Sold Items Summary Calculations")
    void testMostSoldItemsSummaryCalculations() {
        List<Object[]> mostSoldItems = billService.getMostSoldItems(10);
        
        assertNotNull(mostSoldItems, "Most sold items should not be null");
        
        int totalItems = 0;
        int totalSold = 0;
        
        for (Object[] row : mostSoldItems) {
            if (row[2] != null) {
                totalItems++;
                totalSold += ((Number) row[2]).intValue();
            }
        }
        
        double averageSold = totalItems > 0 ? (double) totalSold / totalItems : 0.0;
        
        assertTrue(totalItems >= 0, "Total items should be non-negative");
        assertTrue(totalSold >= 0, "Total sold should be non-negative");
        assertTrue(averageSold >= 0, "Average sold should be non-negative");
    }
    
    @Test
    @Order(10)
    @DisplayName("Test Most Sold Items Performance")
    void testMostSoldItemsPerformance() {
        long startTime = System.currentTimeMillis();
        
        List<Object[]> mostSoldItems = billService.getMostSoldItems(10);
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        assertNotNull(mostSoldItems, "Most sold items should not be null");
        assertTrue(duration < 5000, "Query should complete within 5 seconds: " + duration + "ms");
    }
    
    @Test
    @Order(11)
    @DisplayName("Test Most Sold Items Search Performance")
    void testMostSoldItemsSearchPerformance() {
        long startTime = System.currentTimeMillis();
        
        List<Object[]> searchResults = billService.getMostSoldItemsBySearch("test", 20);
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        assertNotNull(searchResults, "Search results should not be null");
        assertTrue(duration < 5000, "Search query should complete within 5 seconds: " + duration + "ms");
    }
    
    @Test
    @Order(12)
    @DisplayName("Test Most Sold Items Search Edge Cases")
    void testMostSoldItemsSearchEdgeCases() {
        // Test with special characters
        List<Object[]> specialCharResults = billService.getMostSoldItemsBySearch("@#$%", 10);
        assertNotNull(specialCharResults, "Should handle special characters");
        
        // Test with very long search term
        String longSearchTerm = "a".repeat(100);
        List<Object[]> longSearchResults = billService.getMostSoldItemsBySearch(longSearchTerm, 10);
        assertNotNull(longSearchResults, "Should handle long search terms");
        
        // Test with numbers only
        List<Object[]> numberSearchResults = billService.getMostSoldItemsBySearch("123", 10);
        assertNotNull(numberSearchResults, "Should handle numeric search terms");
    }
} 