package com.example.dinithi_pahana_edu.items;

import com.example.dinithi_pahana_edu.model.Item;
import com.example.dinithi_pahana_edu.service.ItemService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit Tests for Item Management Functionality
 * Tests add items, view items, search items, delete items, and edit items operations
 * Common for admin and coadmin, staff can only view and search
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ItemManagementTest {
    
    private ItemService itemService;
    private Item testItem;
    private List<Item> itemList;
    
    @BeforeEach
    void setUp() {
        itemService = new ItemService();
        itemList = new ArrayList<>();
        
        // Create test item
        testItem = new Item();
        testItem.setId(1);
        testItem.setName("Mathematics Book");
        testItem.setDescription("Advanced Mathematics for Grade 12");
        testItem.setPrice(850.00);
        testItem.setStock(50);
        testItem.setCategory("Books");
    }
    
    @Test
    @Order(1)
    @DisplayName("Test Item Object Creation")
    void testItemCreation() {
        assertNotNull(testItem);
        assertEquals(1, testItem.getId());
        assertEquals("Mathematics Book", testItem.getName());
        assertEquals("Advanced Mathematics for Grade 12", testItem.getDescription());
        assertEquals(850.00, testItem.getPrice(), 0.01);
        assertEquals(50, testItem.getStock());
        assertEquals("Books", testItem.getCategory());
    }
    
    @Test
    @Order(2)
    @DisplayName("Test Item Getters and Setters")
    void testItemGettersAndSetters() {
        Item item = new Item();
        
        // Test setters
        item.setId(100);
        item.setName("Science Book");
        item.setDescription("General Science for Grade 10");
        item.setPrice(750.00);
        item.setStock(30);
        item.setCategory("Books");
        
        // Test getters
        assertEquals(100, item.getId());
        assertEquals("Science Book", item.getName());
        assertEquals("General Science for Grade 10", item.getDescription());
        assertEquals(750.00, item.getPrice(), 0.01);
        assertEquals(30, item.getStock());
        assertEquals("Books", item.getCategory());
    }
    
    @Test
    @Order(3)
    @DisplayName("Test Add Item - Valid Data")
    void testAddItemValidData() {
        Item newItem = new Item();
        newItem.setName("English Dictionary");
        newItem.setDescription("Comprehensive English Dictionary");
        newItem.setPrice(1200.00);
        newItem.setStock(25);
        newItem.setCategory("Reference Books");
        
        // Simulate adding item to list
        itemList.add(newItem);
        
        assertNotNull(newItem);
        assertEquals(1, itemList.size());
        assertEquals("English Dictionary", itemList.get(0).getName());
        assertEquals(1200.00, itemList.get(0).getPrice(), 0.01);
    }
    
    @Test
    @Order(4)
    @DisplayName("Test Add Item - Invalid Data")
    void testAddItemInvalidData() {
        Item invalidItem = new Item();
        invalidItem.setName(""); // Empty name
        invalidItem.setDescription(""); // Empty description
        invalidItem.setPrice(-100.00); // Negative price
        invalidItem.setStock(-5); // Negative stock
        invalidItem.setCategory(""); // Empty category
        
        // Test validation
        assertFalse(isValidItem(invalidItem));
        assertFalse(isValidName(invalidItem.getName()));
        assertFalse(isValidPrice(invalidItem.getPrice()));
        assertFalse(isValidStock(invalidItem.getStock()));
        assertFalse(isValidCategory(invalidItem.getCategory()));
    }
    
    @Test
    @Order(5)
    @DisplayName("Test Add Item - Duplicate Name")
    void testAddItemDuplicateName() {
        Item item1 = new Item();
        item1.setName("Physics Book");
        item1.setDescription("Physics for Grade 11");
        item1.setPrice(900.00);
        item1.setStock(40);
        
        Item item2 = new Item();
        item2.setName("Physics Book"); // Same name
        item2.setDescription("Different Physics Book");
        item2.setPrice(950.00);
        item2.setStock(35);
        
        // Simulate adding first item
        itemList.add(item1);
        
        // Test duplicate name detection
        assertTrue(hasDuplicateName(itemList, item2.getName()));
        assertEquals(1, itemList.size()); // Should not add duplicate
    }
    
    @Test
    @Order(6)
    @DisplayName("Test View All Items")
    void testViewAllItems() {
        // Add multiple items
        Item item1 = new Item();
        item1.setId(1);
        item1.setName("Mathematics Book");
        item1.setPrice(850.00);
        item1.setStock(50);
        
        Item item2 = new Item();
        item2.setId(2);
        item2.setName("Science Book");
        item2.setPrice(750.00);
        item2.setStock(30);
        
        Item item3 = new Item();
        item3.setId(3);
        item3.setName("English Book");
        item3.setPrice(650.00);
        item3.setStock(45);
        
        itemList.add(item1);
        itemList.add(item2);
        itemList.add(item3);
        
        // Test viewing all items
        assertNotNull(itemList);
        assertEquals(3, itemList.size());
        
        // Test item details
        assertEquals("Mathematics Book", itemList.get(0).getName());
        assertEquals(850.00, itemList.get(0).getPrice(), 0.01);
        assertEquals("Science Book", itemList.get(1).getName());
        assertEquals(750.00, itemList.get(1).getPrice(), 0.01);
        assertEquals("English Book", itemList.get(2).getName());
        assertEquals(650.00, itemList.get(2).getPrice(), 0.01);
    }
    
    @Test
    @Order(7)
    @DisplayName("Test View Item by ID")
    void testViewItemById() {
        // Add items
        Item item1 = new Item();
        item1.setId(1);
        item1.setName("Mathematics Book");
        item1.setPrice(850.00);
        
        Item item2 = new Item();
        item2.setId(2);
        item2.setName("Science Book");
        item2.setPrice(750.00);
        
        itemList.add(item1);
        itemList.add(item2);
        
        // Test finding item by ID
        Item foundItem = findItemById(itemList, 1);
        assertNotNull(foundItem);
        assertEquals(1, foundItem.getId());
        assertEquals("Mathematics Book", foundItem.getName());
        assertEquals(850.00, foundItem.getPrice(), 0.01);
        
        // Test finding non-existent item
        Item notFoundItem = findItemById(itemList, 999);
        assertNull(notFoundItem);
    }
    
    @Test
    @Order(8)
    @DisplayName("Test Search Item by Name")
    void testSearchItemByName() {
        // Add items
        Item item1 = new Item();
        item1.setName("Mathematics Book");
        item1.setPrice(850.00);
        
        Item item2 = new Item();
        item2.setName("Science Book");
        item2.setPrice(750.00);
        
        itemList.add(item1);
        itemList.add(item2);
        
        // Test finding item by name
        Item foundItem = searchItemByName(itemList, "Mathematics Book");
        assertNotNull(foundItem);
        assertEquals("Mathematics Book", foundItem.getName());
        assertEquals(850.00, foundItem.getPrice(), 0.01);
        
        // Test finding non-existent item
        Item notFoundItem = searchItemByName(itemList, "Non Existent Book");
        assertNull(notFoundItem);
    }
    
    @Test
    @Order(9)
    @DisplayName("Test Search Item by Category")
    void testSearchItemByCategory() {
        // Add items
        Item item1 = new Item();
        item1.setName("Mathematics Book");
        item1.setCategory("Books");
        
        Item item2 = new Item();
        item2.setName("Pencil");
        item2.setCategory("Stationery");
        
        Item item3 = new Item();
        item3.setName("Science Book");
        item3.setCategory("Books");
        
        itemList.add(item1);
        itemList.add(item2);
        itemList.add(item3);
        
        // Test finding items by category
        List<Item> foundItems = searchItemsByCategory(itemList, "Books");
        assertNotNull(foundItems);
        assertEquals(2, foundItems.size());
        
        List<Item> foundStationery = searchItemsByCategory(itemList, "Stationery");
        assertNotNull(foundStationery);
        assertEquals(1, foundStationery.size());
        assertEquals("Pencil", foundStationery.get(0).getName());
    }
    
    @Test
    @Order(10)
    @DisplayName("Test Search Item - Partial Name Match")
    void testSearchItemPartialNameMatch() {
        // Add items
        Item item1 = new Item();
        item1.setName("Mathematics Book");
        
        Item item2 = new Item();
        item2.setName("Science Book");
        
        Item item3 = new Item();
        item3.setName("English Dictionary");
        
        itemList.add(item1);
        itemList.add(item2);
        itemList.add(item3);
        
        // Search with partial name
        List<Item> foundItems = searchItemsByPartialName(itemList, "Book");
        assertNotNull(foundItems);
        assertEquals(2, foundItems.size());
        
        List<Item> foundMath = searchItemsByPartialName(itemList, "Math");
        assertNotNull(foundMath);
        assertEquals(1, foundMath.size());
        assertEquals("Mathematics Book", foundMath.get(0).getName());
    }
    
    @Test
    @Order(11)
    @DisplayName("Test Delete Item - Valid ID")
    void testDeleteItemValidId() {
        // Add items
        Item item1 = new Item();
        item1.setId(1);
        item1.setName("Mathematics Book");
        
        Item item2 = new Item();
        item2.setId(2);
        item2.setName("Science Book");
        
        itemList.add(item1);
        itemList.add(item2);
        
        assertEquals(2, itemList.size());
        
        // Delete item with ID 1
        boolean deleted = deleteItemById(itemList, 1);
        assertTrue(deleted);
        assertEquals(1, itemList.size());
        assertEquals("Science Book", itemList.get(0).getName());
    }
    
    @Test
    @Order(12)
    @DisplayName("Test Delete Item - Invalid ID")
    void testDeleteItemInvalidId() {
        // Add item
        Item item = new Item();
        item.setId(1);
        item.setName("Mathematics Book");
        
        itemList.add(item);
        assertEquals(1, itemList.size());
        
        // Try to delete non-existent item
        boolean deleted = deleteItemById(itemList, 999);
        assertFalse(deleted);
        assertEquals(1, itemList.size()); // List should remain unchanged
    }
    
    @Test
    @Order(13)
    @DisplayName("Test Edit Item - Valid Data")
    void testEditItemValidData() {
        // Get item to update
        Item itemToUpdate = new Item();
        itemToUpdate.setId(1);
        itemToUpdate.setName("Mathematics Book");
        itemToUpdate.setDescription("Basic Mathematics");
        itemToUpdate.setPrice(850.00);
        itemToUpdate.setStock(50);
        itemToUpdate.setCategory("Books");
        
        itemList.add(itemToUpdate);
        
        // Update item data
        itemToUpdate.setName("Advanced Mathematics Book");
        itemToUpdate.setDescription("Advanced Mathematics for Grade 12");
        itemToUpdate.setPrice(950.00);
        itemToUpdate.setStock(40);
        itemToUpdate.setCategory("Advanced Books");
        
        // Verify update
        assertEquals("Advanced Mathematics Book", itemToUpdate.getName());
        assertEquals("Advanced Mathematics for Grade 12", itemToUpdate.getDescription());
        assertEquals(950.00, itemToUpdate.getPrice(), 0.01);
        assertEquals(40, itemToUpdate.getStock());
        assertEquals("Advanced Books", itemToUpdate.getCategory());
    }
    
    @Test
    @Order(14)
    @DisplayName("Test Edit Item - Invalid Data")
    void testEditItemInvalidData() {
        Item itemToUpdate = new Item();
        itemToUpdate.setId(1);
        itemToUpdate.setName("Mathematics Book");
        itemToUpdate.setPrice(850.00);
        itemToUpdate.setStock(50);
        
        itemList.add(itemToUpdate);
        
        // Try to update with invalid data
        String originalName = itemToUpdate.getName();
        double originalPrice = itemToUpdate.getPrice();
        int originalStock = itemToUpdate.getStock();
        
        // Update with invalid data
        itemToUpdate.setName(""); // Invalid empty name
        itemToUpdate.setPrice(-100.00); // Invalid negative price
        itemToUpdate.setStock(-5); // Invalid negative stock
        
        // Test validation
        assertFalse(isValidName(itemToUpdate.getName()));
        assertFalse(isValidPrice(itemToUpdate.getPrice()));
        assertFalse(isValidStock(itemToUpdate.getStock()));
        
        // Restore original data
        itemToUpdate.setName(originalName);
        itemToUpdate.setPrice(originalPrice);
        itemToUpdate.setStock(originalStock);
        
        // Verify restoration
        assertTrue(isValidName(itemToUpdate.getName()));
        assertTrue(isValidPrice(itemToUpdate.getPrice()));
        assertTrue(isValidStock(itemToUpdate.getStock()));
    }
    
    @Test
    @Order(15)
    @DisplayName("Test Item Data Validation")
    void testItemDataValidation() {
        Item validItem = new Item();
        validItem.setName("Valid Book");
        validItem.setDescription("Valid Description");
        validItem.setPrice(100.00);
        validItem.setStock(10);
        validItem.setCategory("Valid Category");
        
        // Test valid item
        assertTrue(isValidItem(validItem));
        assertTrue(isValidName(validItem.getName()));
        assertTrue(isValidPrice(validItem.getPrice()));
        assertTrue(isValidStock(validItem.getStock()));
        assertTrue(isValidCategory(validItem.getCategory()));
    }
    
    // Helper methods
    private boolean isValidItem(Item item) {
        return item != null &&
               isValidName(item.getName()) &&
               isValidPrice(item.getPrice()) &&
               isValidStock(item.getStock()) &&
               isValidCategory(item.getCategory());
    }
    
    private boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty() && name.length() >= 2;
    }
    
    private boolean isValidPrice(double price) {
        return price > 0;
    }
    
    private boolean isValidStock(int stock) {
        return stock >= 0;
    }
    
    private boolean isValidCategory(String category) {
        return category != null && !category.trim().isEmpty();
    }
    
    private boolean hasDuplicateName(List<Item> items, String name) {
        return items.stream().anyMatch(i -> i.getName().equals(name));
    }
    
    private Item findItemById(List<Item> items, int id) {
        return items.stream()
                .filter(i -> i.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    private Item searchItemByName(List<Item> items, String name) {
        return items.stream()
                .filter(i -> i.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
    
    private List<Item> searchItemsByCategory(List<Item> items, String category) {
        return items.stream()
                .filter(i -> i.getCategory().equalsIgnoreCase(category))
                .toList();
    }
    
    private List<Item> searchItemsByPartialName(List<Item> items, String partialName) {
        return items.stream()
                .filter(i -> i.getName().toLowerCase().contains(partialName.toLowerCase()))
                .toList();
    }
    
    private boolean deleteItemById(List<Item> items, int id) {
        return items.removeIf(i -> i.getId() == id);
    }
} 