package com.example.dinithi_pahana_edu.calculateBills;

import com.example.dinithi_pahana_edu.model.Bill;
import com.example.dinithi_pahana_edu.model.BillItem;
import com.example.dinithi_pahana_edu.model.Customer;
import com.example.dinithi_pahana_edu.model.Item;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit Tests for Bill Calculation Process
 * Tests the complete workflow: search customer, add items, calculate prices, 
 * add/delete rows, calculate totals, balance, save and print bill
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BillCalculationTest {
    
    private Bill testBill;
    private Customer testCustomer;
    private List<Item> availableItems;
    private List<BillItem> billItems;
    
    @BeforeEach
    void setUp() {
        // Create test customer
        testCustomer = new Customer();
        testCustomer.setId(1);
        testCustomer.setAccountNumber("ACC001");
        testCustomer.setName("John Doe");
        testCustomer.setAddress("123 Main Street");
        testCustomer.setTelephone("0712345678");
        testCustomer.setEmail("john.doe@email.com");
        
        // Create available items
        availableItems = new ArrayList<>();
        
        Item item1 = new Item();
        item1.setId(1);
        item1.setName("Mathematics Book");
        item1.setDescription("Advanced Mathematics for Grade 12");
        item1.setPrice(850.00);
        item1.setStock(50);
        item1.setCategory("Books");
        
        Item item2 = new Item();
        item2.setId(2);
        item2.setName("Science Book");
        item2.setDescription("General Science for Grade 10");
        item2.setPrice(750.00);
        item2.setStock(30);
        item2.setCategory("Books");
        
        Item item3 = new Item();
        item3.setId(3);
        item3.setName("Pencil");
        item3.setDescription("HB Pencil");
        item3.setPrice(25.00);
        item3.setStock(100);
        item3.setCategory("Stationery");
        
        availableItems.add(item1);
        availableItems.add(item2);
        availableItems.add(item3);
        
        // Initialize bill
        testBill = new Bill();
        testBill.setId(1);
        testBill.setBillNumber("BILL001");
        testBill.setCustomerId(testCustomer.getId());
        testBill.setBillDate(new Date());
        testBill.setBillDateTime("2024-01-15 10:30:00");
        testBill.setTotalAmount(0.0);
        testBill.setPaidAmount(0.0);
        testBill.setBalance(0.0);
        
        billItems = new ArrayList<>();
    }
    
    @Test
    @Order(1)
    @DisplayName("Test Search Customer by Account Number")
    void testSearchCustomerByAccountNumber() {
        // Test searching customer by account number
        Customer foundCustomer = searchCustomerByAccountNumber("ACC001");
        assertNotNull(foundCustomer);
        assertEquals("ACC001", foundCustomer.getAccountNumber());
        assertEquals("John Doe", foundCustomer.getName());
        assertEquals("john.doe@email.com", foundCustomer.getEmail());
    }
    
    @Test
    @Order(2)
    @DisplayName("Test Search Customer by Name")
    void testSearchCustomerByName() {
        // Test searching customer by name
        Customer foundCustomer = searchCustomerByName("John Doe");
        assertNotNull(foundCustomer);
        assertEquals("John Doe", foundCustomer.getName());
        assertEquals("ACC001", foundCustomer.getAccountNumber());
    }
    
    @Test
    @Order(3)
    @DisplayName("Test Search Customer by Telephone")
    void testSearchCustomerByTelephone() {
        // Test searching customer by telephone
        Customer foundCustomer = searchCustomerByTelephone("0712345678");
        assertNotNull(foundCustomer);
        assertEquals("0712345678", foundCustomer.getTelephone());
        assertEquals("John Doe", foundCustomer.getName());
    }
    
    @Test
    @Order(4)
    @DisplayName("Test Search Customer by Email")
    void testSearchCustomerByEmail() {
        // Test searching customer by email
        Customer foundCustomer = searchCustomerByEmail("john.doe@email.com");
        assertNotNull(foundCustomer);
        assertEquals("john.doe@email.com", foundCustomer.getEmail());
        assertEquals("John Doe", foundCustomer.getName());
    }
    
    @Test
    @Order(5)
    @DisplayName("Test Search Customer - Not Found")
    void testSearchCustomerNotFound() {
        // Test searching for non-existent customer
        Customer foundCustomer = searchCustomerByAccountNumber("NONEXISTENT");
        assertNull(foundCustomer);
    }
    
    @Test
    @Order(6)
    @DisplayName("Test Add Item to Bill")
    void testAddItemToBill() {
        // Add item to bill
        Item selectedItem = availableItems.get(0); // Mathematics Book
        int quantity = 2;
        
        BillItem billItem = new BillItem();
        billItem.setItemId(selectedItem.getId());
        billItem.setItemName(selectedItem.getName());
        billItem.setQuantity(quantity);
        billItem.setPrice(selectedItem.getPrice());
        
        billItems.add(billItem);
        
        // Verify item added
        assertEquals(1, billItems.size());
        assertEquals("Mathematics Book", billItems.get(0).getItemName());
        assertEquals(2, billItems.get(0).getQuantity());
        assertEquals(850.00, billItems.get(0).getPrice(), 0.01);
    }
    
    @Test
    @Order(7)
    @DisplayName("Test Add Multiple Items to Bill")
    void testAddMultipleItemsToBill() {
        // Add first item
        Item item1 = availableItems.get(0); // Mathematics Book
        BillItem billItem1 = new BillItem();
        billItem1.setItemId(item1.getId());
        billItem1.setItemName(item1.getName());
        billItem1.setQuantity(1);
        billItem1.setPrice(item1.getPrice());
        
        // Add second item
        Item item2 = availableItems.get(1); // Science Book
        BillItem billItem2 = new BillItem();
        billItem2.setItemId(item2.getId());
        billItem2.setItemName(item2.getName());
        billItem2.setQuantity(3);
        billItem2.setPrice(item2.getPrice());
        
        billItems.add(billItem1);
        billItems.add(billItem2);
        
        // Verify items added
        assertEquals(2, billItems.size());
        assertEquals("Mathematics Book", billItems.get(0).getItemName());
        assertEquals("Science Book", billItems.get(1).getItemName());
        assertEquals(1, billItems.get(0).getQuantity());
        assertEquals(3, billItems.get(1).getQuantity());
    }
    
    @Test
    @Order(8)
    @DisplayName("Test Calculate Item Price")
    void testCalculateItemPrice() {
        // Test price calculation for single item
        Item item = availableItems.get(0); // Mathematics Book
        int quantity = 5;
        double expectedTotal = item.getPrice() * quantity;
        
        double calculatedTotal = calculateItemPrice(item.getPrice(), quantity);
        assertEquals(expectedTotal, calculatedTotal, 0.01);
        assertEquals(4250.00, calculatedTotal, 0.01);
    }
    
    @Test
    @Order(9)
    @DisplayName("Test Calculate Bill Total")
    void testCalculateBillTotal() {
        // Add items to bill
        addTestItemsToBill();
        
        // Calculate total
        double total = calculateBillTotal(billItems);
        assertEquals(3150.00, total, 0.01); // (850*1) + (750*3) + (25*2)
    }
    
    @Test
    @Order(10)
    @DisplayName("Test Calculate Discount")
    void testCalculateDiscount() {
        double total = 1000.00;
        double discountPercentage = 10.0; // 10% discount
        
        double discount = calculateDiscount(total, discountPercentage);
        assertEquals(100.00, discount, 0.01);
    }
    
    @Test
    @Order(11)
    @DisplayName("Test Calculate Total with Discount")
    void testCalculateTotalWithDiscount() {
        double total = 1000.00;
        double discount = 100.00;
        
        double finalTotal = calculateTotalWithDiscount(total, discount);
        assertEquals(900.00, finalTotal, 0.01);
    }
    
    @Test
    @Order(12)
    @DisplayName("Test Calculate Balance")
    void testCalculateBalance() {
        double total = 1000.00;
        double paidAmount = 600.00;
        
        double balance = calculateBalance(total, paidAmount);
        assertEquals(400.00, balance, 0.01);
    }
    
    @Test
    @Order(13)
    @DisplayName("Test Add Row to Bill")
    void testAddRowToBill() {
        // Add initial item
        addTestItemsToBill();
        int initialSize = billItems.size();
        
        // Add new row
        Item newItem = availableItems.get(2); // Pencil
        BillItem newBillItem = new BillItem();
        newBillItem.setItemId(newItem.getId());
        newBillItem.setItemName(newItem.getName());
        newBillItem.setQuantity(5);
        newBillItem.setPrice(newItem.getPrice());
        
        billItems.add(newBillItem);
        
        // Verify row added
        assertEquals(initialSize + 1, billItems.size());
        assertEquals("Pencil", billItems.get(billItems.size() - 1).getItemName());
        assertEquals(5, billItems.get(billItems.size() - 1).getQuantity());
    }
    
    @Test
    @Order(14)
    @DisplayName("Test Delete Row from Bill")
    void testDeleteRowFromBill() {
        // Add items to bill
        addTestItemsToBill();
        int initialSize = billItems.size();
        
        // Delete first row
        boolean deleted = deleteBillItem(billItems, 0);
        assertTrue(deleted);
        assertEquals(initialSize - 1, billItems.size());
        
        // Verify first item is now Science Book (was second item)
        assertEquals("Science Book", billItems.get(0).getItemName());
    }
    
    @Test
    @Order(15)
    @DisplayName("Test Delete Row - Invalid Index")
    void testDeleteRowInvalidIndex() {
        // Add items to bill
        addTestItemsToBill();
        int initialSize = billItems.size();
        
        // Try to delete non-existent row
        boolean deleted = deleteBillItem(billItems, 999);
        assertFalse(deleted);
        assertEquals(initialSize, billItems.size()); // Size should remain unchanged
    }
    
    @Test
    @Order(16)
    @DisplayName("Test Save Bill")
    void testSaveBill() {
        // Prepare bill with items
        addTestItemsToBill();
        double total = calculateBillTotal(billItems);
        double discount = calculateDiscount(total, 5.0); // 5% discount
        double finalTotal = calculateTotalWithDiscount(total, discount);
        
        // Set bill details
        testBill.setTotalAmount(finalTotal);
        testBill.setPaidAmount(2000.00);
        testBill.setBalance(calculateBalance(finalTotal, 2000.00));
        
        // Save bill
        boolean saved = saveBill(testBill, billItems);
        assertTrue(saved);
        
        // Verify bill details
        assertEquals(2992.50, testBill.getTotalAmount(), 0.01); // 3150 - 157.5 (5% discount)
        assertEquals(2000.00, testBill.getPaidAmount(), 0.01);
        assertEquals(992.50, testBill.getBalance(), 0.01);
    }
    
    @Test
    @Order(17)
    @DisplayName("Test Print Bill")
    void testPrintBill() {
        // Prepare bill for printing
        addTestItemsToBill();
        testBill.setTotalAmount(calculateBillTotal(billItems));
        testBill.setPaidAmount(0.0);
        testBill.setBalance(testBill.getTotalAmount());
        
        // Print bill
        String printedBill = printBill(testBill, billItems);
        assertNotNull(printedBill);
        assertTrue(printedBill.contains("BILL001"));
        assertTrue(printedBill.contains("Mathematics Book"));
        assertTrue(printedBill.contains("Science Book"));
        assertTrue(printedBill.contains("Pencil"));
        assertTrue(printedBill.contains("3150.0")); // Total amount
    }
    
    @Test
    @Order(18)
    @DisplayName("Test Bill Validation")
    void testBillValidation() {
        // Test valid bill
        assertTrue(isValidBill(testBill));
        
        // Test invalid bill (no customer)
        Bill invalidBill = new Bill();
        invalidBill.setBillNumber("BILL002");
        invalidBill.setCustomerId(0); // Invalid customer ID
        assertFalse(isValidBill(invalidBill));
        
        // Test invalid bill (no bill number)
        Bill invalidBill2 = new Bill();
        invalidBill2.setCustomerId(1);
        assertFalse(isValidBill(invalidBill2));
    }
    
    // Helper methods
    private Customer searchCustomerByAccountNumber(String accountNumber) {
        if ("ACC001".equals(accountNumber)) {
            return testCustomer;
        }
        return null;
    }
    
    private Customer searchCustomerByName(String name) {
        if ("John Doe".equals(name)) {
            return testCustomer;
        }
        return null;
    }
    
    private Customer searchCustomerByTelephone(String telephone) {
        if ("0712345678".equals(telephone)) {
            return testCustomer;
        }
        return null;
    }
    
    private Customer searchCustomerByEmail(String email) {
        if ("john.doe@email.com".equals(email)) {
            return testCustomer;
        }
        return null;
    }
    
    private double calculateItemPrice(double unitPrice, int quantity) {
        return unitPrice * quantity;
    }
    
    private double calculateBillTotal(List<BillItem> items) {
        return items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
    
    private double calculateDiscount(double total, double discountPercentage) {
        return total * (discountPercentage / 100.0);
    }
    
    private double calculateTotalWithDiscount(double total, double discount) {
        return total - discount;
    }
    
    private double calculateBalance(double total, double paidAmount) {
        return total - paidAmount;
    }
    
    private boolean deleteBillItem(List<BillItem> items, int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
            return true;
        }
        return false;
    }
    
    private boolean saveBill(Bill bill, List<BillItem> items) {
        return bill != null && !items.isEmpty() && bill.getCustomerId() > 0;
    }
    
    private String printBill(Bill bill, List<BillItem> items) {
        StringBuilder sb = new StringBuilder();
        sb.append("BILL NUMBER: ").append(bill.getBillNumber()).append("\n");
        sb.append("DATE: ").append(bill.getBillDateTime()).append("\n");
        sb.append("ITEMS:\n");
        
        for (BillItem item : items) {
            sb.append("- ").append(item.getItemName())
              .append(" x").append(item.getQuantity())
              .append(" @ ").append(item.getPrice())
              .append(" = ").append(item.getPrice() * item.getQuantity()).append("\n");
        }
        
        sb.append("TOTAL: ").append(bill.getTotalAmount()).append("\n");
        sb.append("PAID: ").append(bill.getPaidAmount()).append("\n");
        sb.append("BALANCE: ").append(bill.getBalance()).append("\n");
        
        return sb.toString();
    }
    
    private boolean isValidBill(Bill bill) {
        return bill != null && 
               bill.getCustomerId() > 0 && 
               bill.getBillNumber() != null && 
               !bill.getBillNumber().trim().isEmpty();
    }
    
    private void addTestItemsToBill() {
        // Add Mathematics Book
        BillItem item1 = new BillItem();
        item1.setItemId(1);
        item1.setItemName("Mathematics Book");
        item1.setQuantity(1);
        item1.setPrice(850.00);
        
        // Add Science Book
        BillItem item2 = new BillItem();
        item2.setItemId(2);
        item2.setItemName("Science Book");
        item2.setQuantity(3);
        item2.setPrice(750.00);
        
        // Add Pencil
        BillItem item3 = new BillItem();
        item3.setItemId(3);
        item3.setItemName("Pencil");
        item3.setQuantity(2);
        item3.setPrice(25.00);
        
        billItems.clear();
        billItems.add(item1);
        billItems.add(item2);
        billItems.add(item3);
    }
} 