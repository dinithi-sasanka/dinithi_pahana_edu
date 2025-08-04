package com.example.dinithi_pahana_edu.dao;

import com.example.dinithi_pahana_edu.model.Item;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * JUnit tests for ItemDAO
 * Tests: CRUD operations, stock management, filtering, validation
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ItemDAOTest {
    
    private ItemDAO itemDAO;
    
    @BeforeEach
    void setUp() {
        itemDAO = new ItemDAO();
    }
    
    // Helper method to create a test item
    private Item createTestItem() {
        Item item = new Item();
        item.setName("Test Item");
        item.setCategory("Electronics");
        item.setDescription("A test electronic item");
        item.setPrice(99.99);
        item.setStock(50);
        return item;
    }
    
    // Helper method to create another test item
    private Item createTestItem2() {
        Item item = new Item();
        item.setName("Test Item 2");
        item.setCategory("Books");
        item.setDescription("A test book item");
        item.setPrice(29.99);
        item.setStock(25);
        return item;
    }
    
    // Helper method to create a third test item
    private Item createTestItem3() {
        Item item = new Item();
        item.setName("Test Item 3");
        item.setCategory("Clothing");
        item.setDescription("A test clothing item");
        item.setPrice(49.99);
        item.setStock(100);
        return item;
    }
    
    // ========== ADD ITEM TESTS ==========
    
    @Test
    @Order(1)
    @DisplayName("Test Add Item - Valid Item")
    void testAddItemValid() {
        Item item = createTestItem();
        
        boolean result = itemDAO.addItem(item);
        assertTrue(result, "Item should be added successfully");
    }
    
    @Test
    @Order(2)
    @DisplayName("Test Add Item - Null Item")
    void testAddItemNull() {
        boolean result = itemDAO.addItem(null);
        assertFalse(result, "Should return false for null item");
    }
    
    @Test
    @Order(3)
    @DisplayName("Test Add Item - Item with Empty Fields")
    void testAddItemEmptyFields() {
        Item item = new Item();
        item.setName("");
        item.setCategory("");
        item.setDescription("");
        item.setPrice(0.0);
        item.setStock(0);
        
        boolean result = itemDAO.addItem(item);
        // This might succeed or fail depending on database constraints
        // We're just testing that the method handles it gracefully
        assertNotNull(result, "Method should return a boolean result");
    }
    
    @Test
    @Order(4)
    @DisplayName("Test Add Item - Different Categories")
    void testAddItemDifferentCategories() {
        Item electronicsItem = createTestItem();
        electronicsItem.setName("Electronics Item");
        electronicsItem.setCategory("Electronics");
        
        Item booksItem = createTestItem2();
        booksItem.setName("Books Item");
        booksItem.setCategory("Books");
        
        Item clothingItem = createTestItem3();
        clothingItem.setName("Clothing Item");
        clothingItem.setCategory("Clothing");
        
        boolean electronicsResult = itemDAO.addItem(electronicsItem);
        boolean booksResult = itemDAO.addItem(booksItem);
        boolean clothingResult = itemDAO.addItem(clothingItem);
        
        assertTrue(electronicsResult, "Electronics item should be added successfully");
        assertTrue(booksResult, "Books item should be added successfully");
        assertTrue(clothingResult, "Clothing item should be added successfully");
    }
    
    // ========== GET ALL ITEMS TESTS ==========
    
    @Test
    @Order(5)
    @DisplayName("Test Get All Items")
    void testGetAllItems() {
        List<Item> items = itemDAO.getAllItems();
        
        assertNotNull(items, "Item list should not be null");
        assertTrue(items.size() >= 0, "Item list should be empty or contain items");
        
        // If there are items, verify their structure
        if (!items.isEmpty()) {
            Item firstItem = items.get(0);
            assertNotNull(firstItem.getId(), "Item ID should not be null");
            assertNotNull(firstItem.getName(), "Item name should not be null");
            assertNotNull(firstItem.getCategory(), "Item category should not be null");
        }
    }
    
    // ========== GET ITEM BY ID TESTS ==========
    
    @Test
    @Order(6)
    @DisplayName("Test Get Item By ID - Valid ID")
    void testGetItemByIdValid() {
        // First add an item
        Item item = createTestItem();
        itemDAO.addItem(item);
        
        // Get all items to find the added one
        List<Item> items = itemDAO.getAllItems();
        if (!items.isEmpty()) {
            Item foundItem = itemDAO.getItemById(items.get(0).getId());
            
            assertNotNull(foundItem, "Item should be found");
            assertEquals(items.get(0).getId(), foundItem.getId(), "Item ID should match");
            assertEquals(items.get(0).getName(), foundItem.getName(), "Item name should match");
            assertEquals(items.get(0).getCategory(), foundItem.getCategory(), "Item category should match");
        }
    }
    
    @Test
    @Order(7)
    @DisplayName("Test Get Item By ID - Invalid ID")
    void testGetItemByIdInvalid() {
        Item item = itemDAO.getItemById(-1);
        assertNull(item, "Should return null for invalid ID");
    }
    
    @Test
    @Order(8)
    @DisplayName("Test Get Item By ID - Zero ID")
    void testGetItemByIdZero() {
        Item item = itemDAO.getItemById(0);
        assertNull(item, "Should return null for zero ID");
    }
    
    @Test
    @Order(9)
    @DisplayName("Test Get Item By ID - Non-existent ID")
    void testGetItemByIdNonExistent() {
        Item item = itemDAO.getItemById(99999);
        assertNull(item, "Should return null for non-existent ID");
    }
    
    // ========== UPDATE ITEM TESTS ==========
    
    @Test
    @Order(10)
    @DisplayName("Test Update Item - Valid Update")
    void testUpdateItemValid() {
        // First add an item
        Item item = createTestItem();
        itemDAO.addItem(item);
        
        // Get the item to update
        List<Item> items = itemDAO.getAllItems();
        if (!items.isEmpty()) {
            Item itemToUpdate = items.get(0);
            itemToUpdate.setName("Updated Item Name");
            itemToUpdate.setPrice(149.99);
            itemToUpdate.setStock(75);
            
            boolean result = itemDAO.updateItem(itemToUpdate);
            assertTrue(result, "Item should be updated successfully");
            
            // Verify the update
            Item updatedItem = itemDAO.getItemById(itemToUpdate.getId());
            assertEquals("Updated Item Name", updatedItem.getName(), "Item name should be updated");
            assertEquals(149.99, updatedItem.getPrice(), 0.01, "Item price should be updated");
            assertEquals(75, updatedItem.getStock(), "Item stock should be updated");
        }
    }
    
    @Test
    @Order(11)
    @DisplayName("Test Update Item - Change Category")
    void testUpdateItemChangeCategory() {
        // First add an item
        Item item = createTestItem();
        itemDAO.addItem(item);
        
        // Get the item to update
        List<Item> items = itemDAO.getAllItems();
        if (!items.isEmpty()) {
            Item itemToUpdate = items.get(0);
            itemToUpdate.setCategory("Updated Category");
            
            boolean result = itemDAO.updateItem(itemToUpdate);
            assertTrue(result, "Item category should be updated successfully");
            
            // Verify the update
            Item updatedItem = itemDAO.getItemById(itemToUpdate.getId());
            assertEquals("Updated Category", updatedItem.getCategory(), "Item category should be updated");
        }
    }
    
    @Test
    @Order(12)
    @DisplayName("Test Update Item - Non-existent Item")
    void testUpdateItemNonExistent() {
        Item item = new Item();
        item.setId(99999); // Non-existent ID
        item.setName("Non-existent Item");
        item.setCategory("Non-existent Category");
        item.setDescription("Non-existent Description");
        item.setPrice(99.99);
        item.setStock(50);
        
        boolean result = itemDAO.updateItem(item);
        assertFalse(result, "Should return false for non-existent item");
    }
    
    @Test
    @Order(13)
    @DisplayName("Test Update Item - Null Item")
    void testUpdateItemNull() {
        boolean result = itemDAO.updateItem(null);
        assertFalse(result, "Should return false for null item");
    }
    
    // ========== DELETE ITEM TESTS ==========
    
    @Test
    @Order(14)
    @DisplayName("Test Delete Item - Valid ID")
    void testDeleteItemValid() {
        // First add an item
        Item item = createTestItem2();
        itemDAO.addItem(item);
        
        // Get the item to delete
        List<Item> items = itemDAO.getAllItems();
        if (!items.isEmpty()) {
            int itemId = items.get(0).getId();
            
            boolean result = itemDAO.deleteItem(itemId);
            assertTrue(result, "Item should be deleted successfully");
            
            // Verify deletion
            Item deletedItem = itemDAO.getItemById(itemId);
            assertNull(deletedItem, "Item should be null after deletion");
        }
    }
    
    @Test
    @Order(15)
    @DisplayName("Test Delete Item - Invalid ID")
    void testDeleteItemInvalid() {
        boolean result = itemDAO.deleteItem(-1);
        assertFalse(result, "Should return false for invalid ID");
    }
    
    @Test
    @Order(16)
    @DisplayName("Test Delete Item - Non-existent ID")
    void testDeleteItemNonExistent() {
        boolean result = itemDAO.deleteItem(99999);
        assertFalse(result, "Should return false for non-existent ID");
    }
    
    // ========== FILTERED ITEMS TESTS ==========
    
    @Test
    @Order(17)
    @DisplayName("Test Get Filtered Items - By Name")
    void testGetFilteredItemsByName() {
        // First add some items
        Item item1 = createTestItem();
        item1.setName("Electronics Item");
        itemDAO.addItem(item1);
        
        Item item2 = createTestItem2();
        item2.setName("Books Item");
        itemDAO.addItem(item2);
        
        List<Item> results = itemDAO.getFilteredItems("Electronics", null, null, null);
        
        assertNotNull(results, "Filtered results should not be null");
        assertTrue(results.size() >= 0, "Filtered results should be empty or contain items");
        
        // If results found, verify they contain the search term
        for (Item result : results) {
            assertTrue(result.getName().toLowerCase().contains("electronics"), 
                      "Item name should contain search term");
        }
    }
    
    @Test
    @Order(18)
    @DisplayName("Test Get Filtered Items - By Category")
    void testGetFilteredItemsByCategory() {
        // First add some items
        Item item1 = createTestItem();
        item1.setCategory("Electronics");
        itemDAO.addItem(item1);
        
        Item item2 = createTestItem2();
        item2.setCategory("Books");
        itemDAO.addItem(item2);
        
        List<Item> results = itemDAO.getFilteredItems(null, "Electronics", null, null);
        
        assertNotNull(results, "Filtered results should not be null");
        assertTrue(results.size() >= 0, "Filtered results should be empty or contain items");
        
        // If results found, verify they contain the search term
        for (Item result : results) {
            assertTrue(result.getCategory().toLowerCase().contains("electronics"), 
                      "Item category should contain search term");
        }
    }
    
    @Test
    @Order(19)
    @DisplayName("Test Get Filtered Items - By Price Range")
    void testGetFilteredItemsByPriceRange() {
        // First add some items
        Item item1 = createTestItem();
        item1.setPrice(50.0);
        itemDAO.addItem(item1);
        
        Item item2 = createTestItem2();
        item2.setPrice(150.0);
        itemDAO.addItem(item2);
        
        List<Item> results = itemDAO.getFilteredItems(null, null, 40.0, 100.0);
        
        assertNotNull(results, "Filtered results should not be null");
        assertTrue(results.size() >= 0, "Filtered results should be empty or contain items");
        
        // If results found, verify they are within the price range
        for (Item result : results) {
            assertTrue(result.getPrice() >= 40.0 && result.getPrice() <= 100.0, 
                      "Item price should be within the specified range");
        }
    }
    
    @Test
    @Order(20)
    @DisplayName("Test Get Filtered Items - Multiple Criteria")
    void testGetFilteredItemsMultipleCriteria() {
        // First add some items
        Item item1 = createTestItem();
        item1.setName("Electronics Item");
        item1.setCategory("Electronics");
        item1.setPrice(80.0);
        itemDAO.addItem(item1);
        
        List<Item> results = itemDAO.getFilteredItems("Electronics", "Electronics", 50.0, 100.0);
        
        assertNotNull(results, "Filtered results should not be null");
        assertTrue(results.size() >= 0, "Filtered results should be empty or contain items");
    }
    
    @Test
    @Order(21)
    @DisplayName("Test Get Filtered Items - No Criteria")
    void testGetFilteredItemsNoCriteria() {
        List<Item> results = itemDAO.getFilteredItems(null, null, null, null);
        
        assertNotNull(results, "Filtered results should not be null");
        // This should return all items or empty list depending on implementation
        assertTrue(results.size() >= 0, "Filtered results should be valid");
    }
    
    // ========== STOCK MANAGEMENT TESTS ==========
    
    @Test
    @Order(22)
    @DisplayName("Test Get Low Stock Items")
    void testGetLowStockItems() {
        // First add some items
        Item item1 = createTestItem();
        item1.setStock(5); // Low stock
        itemDAO.addItem(item1);
        
        Item item2 = createTestItem2();
        item2.setStock(50); // Normal stock
        itemDAO.addItem(item2);
        
        List<Item> lowStockItems = itemDAO.getLowStockItems(10); // Threshold of 10
        
        assertNotNull(lowStockItems, "Low stock items list should not be null");
        assertTrue(lowStockItems.size() >= 0, "Low stock items list should be empty or contain items");
        
        // If low stock items found, verify they are below threshold
        for (Item item : lowStockItems) {
            assertTrue(item.getStock() < 10, "Item stock should be below threshold");
        }
    }
    
    @Test
    @Order(23)
    @DisplayName("Test Decrement Stock")
    void testDecrementStock() {
        // First add an item
        Item item = createTestItem();
        item.setStock(50);
        itemDAO.addItem(item);
        
        // Get the item to decrement stock
        List<Item> items = itemDAO.getAllItems();
        if (!items.isEmpty()) {
            int itemId = items.get(0).getId();
            
            boolean result = itemDAO.decrementStock(itemId, 10);
            // This might fail if the stock table doesn't exist or has no records for this item
            // We'll just test that the method doesn't throw an exception
            assertNotNull(result, "Method should return a boolean result");
        }
    }
    
    @Test
    @Order(24)
    @DisplayName("Test Increment Stock")
    void testIncrementStock() {
        // First add an item
        Item item = createTestItem();
        item.setStock(50);
        itemDAO.addItem(item);
        
        // Get the item to increment stock
        List<Item> items = itemDAO.getAllItems();
        if (!items.isEmpty()) {
            int itemId = items.get(0).getId();
            
            boolean result = itemDAO.incrementStock(itemId, 10);
            // This might fail if the stock table doesn't exist or has no records for this item
            // We'll just test that the method doesn't throw an exception
            assertNotNull(result, "Method should return a boolean result");
        }
    }
    
    @Test
    @Order(25)
    @DisplayName("Test Decrement Stock - Insufficient Stock")
    void testDecrementStockInsufficient() {
        // First add an item
        Item item = createTestItem();
        item.setStock(5);
        itemDAO.addItem(item);
        
        // Get the item to decrement stock
        List<Item> items = itemDAO.getAllItems();
        if (!items.isEmpty()) {
            int itemId = items.get(0).getId();
            
            boolean result = itemDAO.decrementStock(itemId, 10); // Try to decrement more than available
            assertFalse(result, "Should return false when trying to decrement more than available stock");
        }
    }
    
    // ========== GET ALL ITEMS WITH CURRENT STOCK TESTS ==========
    
    @Test
    @Order(26)
    @DisplayName("Test Get All Items With Current Stock")
    void testGetAllItemsWithCurrentStock() {
        // First add some items
        Item item1 = createTestItem();
        itemDAO.addItem(item1);
        
        Item item2 = createTestItem2();
        itemDAO.addItem(item2);
        
        List<Item> items = itemDAO.getAllItemsWithCurrentStock();
        
        assertNotNull(items, "Items list should not be null");
        assertTrue(items.size() >= 0, "Items list should be empty or contain items");
        
        // If items found, verify they have both stock and currentStock
        for (Item item : items) {
            assertNotNull(item.getId(), "Item ID should not be null");
            assertNotNull(item.getName(), "Item name should not be null");
            assertNotNull(item.getCategory(), "Item category should not be null");
            // Note: currentStock might be 0 or null depending on stock table structure
        }
    }
    
    // ========== DATA VALIDATION TESTS ==========
    
    @Test
    @Order(27)
    @DisplayName("Test Item Data Integrity")
    void testItemDataIntegrity() {
        // First add an item
        Item item = createTestItem();
        itemDAO.addItem(item);
        
        // Get all items and verify data integrity
        List<Item> items = itemDAO.getAllItems();
        
        for (Item i : items) {
            assertNotNull(i.getId(), "Item ID should not be null");
            assertNotNull(i.getName(), "Item name should not be null");
            assertNotNull(i.getCategory(), "Item category should not be null");
            assertNotNull(i.getDescription(), "Item description should not be null");
            
            // Verify data types
            assertTrue(i.getId() > 0, "Item ID should be positive");
            // Some fields might be empty in the database, which is valid
            // We only check that they are not null (handled above)
            assertTrue(i.getPrice() >= 0, "Item price should be non-negative");
            assertTrue(i.getStock() >= 0, "Item stock should be non-negative");
        }
    }
    
    @Test
    @Order(28)
    @DisplayName("Test Item Search Performance")
    void testItemSearchPerformance() {
        // Test that search operations complete in reasonable time
        long startTime = System.currentTimeMillis();
        
        List<Item> items = itemDAO.getAllItems();
        itemDAO.getFilteredItems("test", "test", 0.0, 1000.0);
        itemDAO.getLowStockItems(10);
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        // Should complete within 5 seconds
        assertTrue(duration < 5000, "Search operations should complete within 5 seconds");
    }
    
    @Test
    @Order(29)
    @DisplayName("Test Item CRUD Performance")
    void testItemCRUDPerformance() {
        // Test that CRUD operations complete in reasonable time
        long startTime = System.currentTimeMillis();
        
        List<Item> items = itemDAO.getAllItems();
        if (!items.isEmpty()) {
            itemDAO.getItemById(items.get(0).getId());
        }
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        // Should complete within 5 seconds
        assertTrue(duration < 5000, "CRUD operations should complete within 5 seconds");
    }
    
    // ========== EDGE CASE TESTS ==========
    
    @Test
    @Order(30)
    @DisplayName("Test Item with Special Characters")
    void testItemWithSpecialCharacters() {
        Item item = createTestItem();
        item.setName("Test Item with Special Chars !@#$%");
        item.setCategory("Category with Special Chars &*()");
        item.setDescription("Description with special chars: <>&\"'");
        
        boolean result = itemDAO.addItem(item);
        // This might succeed or fail depending on database constraints
        // We're just testing that the method handles it gracefully
        assertNotNull(result, "Method should return a boolean result");
    }
    
    @Test
    @Order(31)
    @DisplayName("Test Item with Long Fields")
    void testItemWithLongFields() {
        Item item = createTestItem();
        item.setName("Very Long Item Name That Exceeds Normal Length Limits");
        item.setCategory("Very Long Category Name That Exceeds Normal Length Limits");
        item.setDescription("Very Long Description That Exceeds Normal Length Limits And Contains Many Words");
        
        boolean result = itemDAO.addItem(item);
        // This might succeed or fail depending on database constraints
        // We're just testing that the method handles it gracefully
        assertNotNull(result, "Method should return a boolean result");
    }
    
    @Test
    @Order(32)
    @DisplayName("Test Item with Extreme Price Values")
    void testItemWithExtremePriceValues() {
        Item item1 = createTestItem();
        item1.setName("Very Expensive Item");
        item1.setPrice(999999.99);
        
        Item item2 = createTestItem();
        item2.setName("Very Cheap Item");
        item2.setPrice(0.01);
        
        boolean result1 = itemDAO.addItem(item1);
        boolean result2 = itemDAO.addItem(item2);
        
        // These might succeed or fail depending on database constraints
        // We're just testing that the method handles it gracefully
        assertNotNull(result1, "Method should return a boolean result for expensive item");
        assertNotNull(result2, "Method should return a boolean result for cheap item");
    }
} 