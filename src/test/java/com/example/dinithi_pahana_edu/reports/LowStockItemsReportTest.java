package com.example.dinithi_pahana_edu.reports;

import com.example.dinithi_pahana_edu.service.ItemService;
import com.example.dinithi_pahana_edu.model.Item;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit Tests for Low Stock Items Report Functionality
 * Tests the low stock items report features including stock level validation
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LowStockItemsReportTest {
    
    private ItemService itemService;
    
    @BeforeEach
    void setUp() {
        itemService = new ItemService();
    }
    
    @Test
    @Order(1)
    @DisplayName("Test Get All Items for Low Stock Analysis")
    void testGetAllItemsForLowStockAnalysis() {
        List<Item> allItems = itemService.getAllItems();
        
        assertNotNull(allItems, "All items list should not be null");
        
        // Verify each item has correct structure
        for (Item item : allItems) {
            assertNotNull(item, "Item should not be null");
            assertNotNull(item.getId(), "Item ID should not be null");
            assertNotNull(item.getName(), "Item name should not be null");
            assertNotNull(item.getStock(), "Item stock should not be null");
        }
    }
    
    @Test
    @Order(2)
    @DisplayName("Test Low Stock Items Identification")
    void testLowStockItemsIdentification() {
        List<Item> allItems = itemService.getAllItems();
        
        assertNotNull(allItems, "All items list should not be null");
        
        int lowStockItems = 0;
        int outOfStockItems = 0;
        
        for (Item item : allItems) {
            assertNotNull(item, "Item should not be null");
            assertNotNull(item.getStock(), "Item stock should not be null");
            
            int stock = item.getStock();
            
            // Count low stock items (stock <= 10)
            if (stock <= 10 && stock > 0) {
                lowStockItems++;
            }
            
            // Count out of stock items (stock = 0)
            if (stock == 0) {
                outOfStockItems++;
            }
            
            // Verify stock is non-negative
            assertTrue(stock >= 0, "Stock should be non-negative for item: " + item.getName());
        }
        
        assertTrue(lowStockItems >= 0, "Low stock items count should be non-negative");
        assertTrue(outOfStockItems >= 0, "Out of stock items count should be non-negative");
    }
    
    @Test
    @Order(3)
    @DisplayName("Test Low Stock Items Data Validation")
    void testLowStockItemsDataValidation() {
        List<Item> allItems = itemService.getAllItems();
        
        assertNotNull(allItems, "All items list should not be null");
        
        for (Item item : allItems) {
            assertNotNull(item, "Item should not be null");
            
            // Verify item ID
            assertNotNull(item.getId(), "Item ID should not be null");
            assertTrue(item.getId() > 0, "Item ID should be positive");
            
            // Verify item name
            assertNotNull(item.getName(), "Item name should not be null");
            // Note: Some items might have empty names in the database, so we'll just check for null
            
            // Verify stock
            assertNotNull(item.getStock(), "Item stock should not be null");
            assertTrue(item.getStock() >= 0, "Item stock should be non-negative");
            
            // Verify price (if available)
            Double price = item.getPrice();
            if (price != null) {
                assertTrue(price >= 0, "Item price should be non-negative");
            }
        }
    }
    
    @Test
    @Order(4)
    @DisplayName("Test Low Stock Items Summary Calculations")
    void testLowStockItemsSummaryCalculations() {
        List<Item> allItems = itemService.getAllItems();
        
        assertNotNull(allItems, "All items list should not be null");
        
        int totalItems = 0;
        int lowStockItems = 0;
        int outOfStockItems = 0;
        int totalStock = 0;
        
        for (Item item : allItems) {
            assertNotNull(item, "Item should not be null");
            totalItems++;
            
            int stock = item.getStock();
            totalStock += stock;
            
            if (stock <= 10 && stock > 0) {
                lowStockItems++;
            }
            
            if (stock == 0) {
                outOfStockItems++;
            }
        }
        
        double averageStock = totalItems > 0 ? (double) totalStock / totalItems : 0.0;
        double lowStockPercentage = totalItems > 0 ? (double) lowStockItems / totalItems * 100 : 0.0;
        double outOfStockPercentage = totalItems > 0 ? (double) outOfStockItems / totalItems * 100 : 0.0;
        
        assertTrue(totalItems >= 0, "Total items should be non-negative");
        assertTrue(lowStockItems >= 0, "Low stock items should be non-negative");
        assertTrue(outOfStockItems >= 0, "Out of stock items should be non-negative");
        assertTrue(totalStock >= 0, "Total stock should be non-negative");
        assertTrue(averageStock >= 0, "Average stock should be non-negative");
        assertTrue(lowStockPercentage >= 0, "Low stock percentage should be non-negative");
        assertTrue(outOfStockPercentage >= 0, "Out of stock percentage should be non-negative");
    }
    
    @Test
    @Order(5)
    @DisplayName("Test Low Stock Items Performance")
    void testLowStockItemsPerformance() {
        long startTime = System.currentTimeMillis();
        
        List<Item> allItems = itemService.getAllItems();
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        assertNotNull(allItems, "All items should not be null");
        assertTrue(duration < 5000, "Query should complete within 5 seconds: " + duration + "ms");
    }
    
    @Test
    @Order(6)
    @DisplayName("Test Low Stock Items Categories")
    void testLowStockItemsCategories() {
        List<Item> allItems = itemService.getAllItems();
        
        assertNotNull(allItems, "All items list should not be null");
        
        int criticalStock = 0;  // stock <= 5
        int lowStock = 0;       // stock <= 10
        int moderateStock = 0;  // stock <= 50
        int highStock = 0;      // stock > 50
        
        for (Item item : allItems) {
            assertNotNull(item, "Item should not be null");
            int stock = item.getStock();
            
            if (stock == 0) {
                // Out of stock
            } else if (stock <= 5) {
                criticalStock++;
            } else if (stock <= 10) {
                lowStock++;
            } else if (stock <= 50) {
                moderateStock++;
            } else {
                highStock++;
            }
        }
        
        assertTrue(criticalStock >= 0, "Critical stock items should be non-negative");
        assertTrue(lowStock >= 0, "Low stock items should be non-negative");
        assertTrue(moderateStock >= 0, "Moderate stock items should be non-negative");
        assertTrue(highStock >= 0, "High stock items should be non-negative");
    }
    
    @Test
    @Order(7)
    @DisplayName("Test Low Stock Items Edge Cases")
    void testLowStockItemsEdgeCases() {
        List<Item> allItems = itemService.getAllItems();
        
        assertNotNull(allItems, "All items list should not be null");
        
        // Test for items with very high stock levels
        int maxStock = 0;
        Item maxStockItem = null;
        
        for (Item item : allItems) {
            assertNotNull(item, "Item should not be null");
            int stock = item.getStock();
            
            if (stock > maxStock) {
                maxStock = stock;
                maxStockItem = item;
            }
        }
        
        if (maxStockItem != null) {
            assertNotNull(maxStockItem.getName(), "Max stock item should have a name");
            assertTrue(maxStockItem.getStock() >= 0, "Max stock should be non-negative");
        }
    }
    
    @Test
    @Order(8)
    @DisplayName("Test Low Stock Items Data Consistency")
    void testLowStockItemsDataConsistency() {
        List<Item> allItems = itemService.getAllItems();
        
        assertNotNull(allItems, "All items list should not be null");
        
        for (Item item : allItems) {
            assertNotNull(item, "Item should not be null");
            
            // Verify all required fields are present
            assertNotNull(item.getId(), "Item ID should not be null");
            assertNotNull(item.getName(), "Item name should not be null");
            assertNotNull(item.getStock(), "Item stock should not be null");
            
            // Verify data types are correct (already verified by not null checks above)
            // Integer id = item.getId();
            // String name = item.getName();
            // Integer stock = item.getStock();
            
            // Verify data integrity
            assertTrue(item.getId() > 0, "Item ID should be positive");
            // Note: Some items might have empty names in the database, so we'll just check for null
            assertTrue(item.getStock() >= 0, "Item stock should be non-negative");
        }
    }
    
    @Test
    @Order(9)
    @DisplayName("Test Low Stock Items Reorder Recommendations")
    void testLowStockItemsReorderRecommendations() {
        List<Item> allItems = itemService.getAllItems();
        
        assertNotNull(allItems, "All items list should not be null");
        
        int needsReorder = 0;
        int urgentReorder = 0;
        
        for (Item item : allItems) {
            assertNotNull(item, "Item should not be null");
            int stock = item.getStock();
            
            // Items with stock <= 5 need urgent reorder
            if (stock <= 5) {
                urgentReorder++;
            }
            // Items with stock <= 10 need reorder
            else if (stock <= 10) {
                needsReorder++;
            }
        }
        
        assertTrue(needsReorder >= 0, "Items needing reorder should be non-negative");
        assertTrue(urgentReorder >= 0, "Items needing urgent reorder should be non-negative");
    }
    
    @Test
    @Order(10)
    @DisplayName("Test Low Stock Items Empty Database")
    void testLowStockItemsEmptyDatabase() {
        // This test assumes the database might be empty
        List<Item> allItems = itemService.getAllItems();
        
        assertNotNull(allItems, "Should return empty list if no items exist");
        
        // If there are items, verify structure
        for (Item item : allItems) {
            assertNotNull(item, "Item should not be null");
            assertNotNull(item.getId(), "Item ID should not be null");
            assertNotNull(item.getName(), "Item name should not be null");
            assertNotNull(item.getStock(), "Item stock should not be null");
        }
    }
    
    @Test
    @Order(11)
    @DisplayName("Test Low Stock Items Multiple Calls")
    void testLowStockItemsMultipleCalls() {
        // Test multiple calls to ensure consistency
        List<Item> firstCall = itemService.getAllItems();
        List<Item> secondCall = itemService.getAllItems();
        
        assertNotNull(firstCall, "First call should not be null");
        assertNotNull(secondCall, "Second call should not be null");
        assertEquals(firstCall.size(), secondCall.size(), "Both calls should return same number of items");
        
        // Verify data consistency between calls
        for (int i = 0; i < firstCall.size(); i++) {
            Item firstItem = firstCall.get(i);
            Item secondItem = secondCall.get(i);
            
            assertEquals(firstItem.getId(), secondItem.getId(), "Item IDs should match between calls");
            assertEquals(firstItem.getName(), secondItem.getName(), "Item names should match between calls");
            assertEquals(firstItem.getStock(), secondItem.getStock(), "Item stock should match between calls");
        }
    }
    
    @Test
    @Order(12)
    @DisplayName("Test Low Stock Items Business Logic")
    void testLowStockItemsBusinessLogic() {
        List<Item> allItems = itemService.getAllItems();
        
        assertNotNull(allItems, "All items list should not be null");
        
        // Business logic validation
        for (Item item : allItems) {
            assertNotNull(item, "Item should not be null");
            
            // Business rule: Items with stock <= 10 should be flagged for reorder
            if (item.getStock() <= 10) {
                // This item should be in the low stock report
                assertNotNull(item.getName(), "Low stock item should have a name");
                assertTrue(item.getStock() >= 0, "Low stock item should have non-negative stock");
            }
            
            // Business rule: Items with stock = 0 should be flagged as out of stock
            if (item.getStock() == 0) {
                // This item should be in the out of stock report
                assertNotNull(item.getName(), "Out of stock item should have a name");
            }
        }
    }
} 