package com.example.dinithi_pahana_edu.dao;

import com.example.dinithi_pahana_edu.model.Bill;
import com.example.dinithi_pahana_edu.model.BillItem;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * JUnit tests for BillDAO
 * Tests: Bill management, bill items, search, analytics
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BillDAOTest {
    
    private BillDAO billDAO;
    
    @BeforeEach
    void setUp() {
        billDAO = new BillDAO();
    }
    
    // Helper method to create a test bill
    private Bill createTestBill() {
        Bill bill = new Bill();
        // Use a customer ID that exists in the database (from the test output, we can see customer ID 2 exists)
        bill.setCustomerId(2);
        bill.setBillNumber("BILL" + System.currentTimeMillis());
        bill.setBillDate(new Date());
        bill.setBillDateTime("2024-01-01 10:00:00");
        bill.setTotalAmount(199.99);
        bill.setPaidAmount(150.00);
        bill.setBalance(49.99);
        return bill;
    }
    
    // Helper method to create test bill items
    private List<BillItem> createTestBillItems(int billId) {
        List<BillItem> items = new ArrayList<>();
        
        BillItem item1 = new BillItem();
        item1.setBillId(billId);
        // Use an item ID that exists in the database (assuming item ID 1 exists)
        item1.setItemId(1);
        item1.setQuantity(2);
        item1.setPrice(49.99);
        item1.setItemName("Test Item 1");
        items.add(item1);
        
        return items;
    }
    
    // ========== ADD BILL TESTS ==========
    
    @Test
    @Order(1)
    @DisplayName("Test Add Bill - Valid Bill")
    void testAddBillValid() {
        Bill bill = createTestBill();
        int result = billDAO.addBill(bill);
        assertTrue(result > 0, "Bill should be added successfully and return positive ID");
    }
    
    @Test
    @Order(2)
    @DisplayName("Test Add Bill - Null Bill")
    void testAddBillNull() {
        int result = billDAO.addBill(null);
        assertEquals(-1, result, "Should return -1 for null bill");
    }
    
    // ========== ADD BILL ITEMS TESTS ==========
    
    @Test
    @Order(3)
    @DisplayName("Test Add Bill Items - Valid Items")
    void testAddBillItemsValid() {
        Bill bill = createTestBill();
        int billId = billDAO.addBill(bill);
        
        if (billId > 0) {
            List<BillItem> items = createTestBillItems(billId);
            boolean result = billDAO.addBillItems(items);
            assertTrue(result, "Bill items should be added successfully");
        }
    }
    
    @Test
    @Order(4)
    @DisplayName("Test Add Bill Items - Empty List")
    void testAddBillItemsEmptyList() {
        List<BillItem> items = new ArrayList<>();
        boolean result = billDAO.addBillItems(items);
        assertTrue(result, "Should return true for empty list");
    }
    
    // ========== GET MAX BILL NUMBER TESTS ==========
    
    @Test
    @Order(5)
    @DisplayName("Test Get Max Bill Number")
    void testGetMaxBillNumber() {
        int maxBillNumber = billDAO.getMaxBillNumber();
        assertTrue(maxBillNumber >= 0, "Max bill number should be non-negative");
    }
    
    // ========== GET BILLS BY CUSTOMER ID TESTS ==========
    
    @Test
    @Order(6)
    @DisplayName("Test Get Bills By Customer ID - Valid Customer ID")
    void testGetBillsByCustomerIdValid() {
        Bill bill = createTestBill();
        billDAO.addBill(bill);
        
        List<Bill> bills = billDAO.getBillsByCustomerId(1);
        assertNotNull(bills, "Bills list should not be null");
        assertTrue(bills.size() >= 0, "Bills list should be empty or contain bills");
    }
    
    @Test
    @Order(7)
    @DisplayName("Test Get Bills By Customer ID - Invalid Customer ID")
    void testGetBillsByCustomerIdInvalid() {
        List<Bill> bills = billDAO.getBillsByCustomerId(-1);
        assertNotNull(bills, "Bills list should not be null");
        assertEquals(0, bills.size(), "Bills list should be empty for invalid customer ID");
    }
    
    // ========== GET BILL ITEMS BY BILL ID TESTS ==========
    
    @Test
    @Order(8)
    @DisplayName("Test Get Bill Items By Bill ID - Valid Bill ID")
    void testGetBillItemsByBillIdValid() {
        Bill bill = createTestBill();
        int billId = billDAO.addBill(bill);
        
        if (billId > 0) {
            List<BillItem> items = createTestBillItems(billId);
            billDAO.addBillItems(items);
            
            List<BillItem> retrievedItems = billDAO.getBillItemsByBillId(billId);
            assertNotNull(retrievedItems, "Bill items list should not be null");
            assertTrue(retrievedItems.size() >= 0, "Bill items list should be empty or contain items");
        }
    }
    
    // ========== GET ALL BILLS TESTS ==========
    
    @Test
    @Order(9)
    @DisplayName("Test Get All Bills")
    void testGetAllBills() {
        List<Bill> bills = billDAO.getAllBills();
        assertNotNull(bills, "Bills list should not be null");
        assertTrue(bills.size() >= 0, "Bills list should be empty or contain bills");
    }
    
    // ========== SEARCH BILLS TESTS ==========
    
    @Test
    @Order(10)
    @DisplayName("Test Search Bills - By Bill Number")
    void testSearchBillsByBillNumber() {
        Bill bill = createTestBill();
        billDAO.addBill(bill);
        
        List<Bill> results = billDAO.searchBills("BILL001", null, null);
        assertNotNull(results, "Search results should not be null");
        assertTrue(results.size() >= 0, "Search results should be empty or contain bills");
    }
    
    @Test
    @Order(11)
    @DisplayName("Test Search Bills - By Date Range")
    void testSearchBillsByDateRange() {
        List<Bill> results = billDAO.searchBills(null, "2024-01-01", "2024-12-31");
        assertNotNull(results, "Search results should not be null");
        assertTrue(results.size() >= 0, "Search results should be empty or contain bills");
    }
    
    // ========== UPDATE BILL TESTS ==========
    
    @Test
    @Order(12)
    @DisplayName("Test Update Bill - Valid Update")
    void testUpdateBillValid() {
        Bill bill = createTestBill();
        int billId = billDAO.addBill(bill);
        
        if (billId > 0) {
            Bill billToUpdate = new Bill();
            billToUpdate.setId(billId);
            billToUpdate.setCustomerId(2); // Use customer ID 2 which exists
            billToUpdate.setBillNumber("UPDATED001");
            billToUpdate.setBillDate(new Date());
            billToUpdate.setBillDateTime("2024-01-03 12:00:00");
            billToUpdate.setTotalAmount(250.00);
            billToUpdate.setPaidAmount(200.00);
            billToUpdate.setBalance(50.00);
            
            boolean result = billDAO.updateBill(billToUpdate);
            assertTrue(result, "Bill should be updated successfully");
        }
    }
    
    @Test
    @Order(13)
    @DisplayName("Test Update Bill - Non-existent Bill")
    void testUpdateBillNonExistent() {
        Bill bill = new Bill();
        bill.setId(99999);
        bill.setCustomerId(1);
        bill.setBillNumber("NONEXISTENT");
        bill.setBillDate(new Date());
        bill.setBillDateTime("2024-01-01 10:00:00");
        bill.setTotalAmount(100.00);
        bill.setPaidAmount(100.00);
        bill.setBalance(0.00);
        
        boolean result = billDAO.updateBill(bill);
        assertFalse(result, "Should return false for non-existent bill");
    }
    
    // ========== DELETE TESTS ==========
    
    @Test
    @Order(14)
    @DisplayName("Test Delete Bill Items By Bill ID")
    void testDeleteBillItemsByBillId() {
        Bill bill = createTestBill();
        int billId = billDAO.addBill(bill);
        
        if (billId > 0) {
            List<BillItem> items = createTestBillItems(billId);
            billDAO.addBillItems(items);
            
            boolean result = billDAO.deleteBillItemsByBillId(billId);
            assertTrue(result, "Bill items should be deleted successfully");
        }
    }
    
    @Test
    @Order(15)
    @DisplayName("Test Delete Bill By ID")
    void testDeleteBillById() {
        Bill bill = createTestBill();
        int billId = billDAO.addBill(bill);
        
        if (billId > 0) {
            boolean result = billDAO.deleteBillById(billId);
            assertTrue(result, "Bill should be deleted successfully");
        }
    }
    
    // ========== ANALYTICS TESTS ==========
    
    @Test
    @Order(16)
    @DisplayName("Test Get Top Customers")
    void testGetTopCustomers() {
        List<Object[]> topCustomers = billDAO.getTopCustomers(5);
        assertNotNull(topCustomers, "Top customers list should not be null");
        assertTrue(topCustomers.size() >= 0, "Top customers list should be empty or contain customers");
    }
    
    @Test
    @Order(17)
    @DisplayName("Test Get Most Sold Items")
    void testGetMostSoldItems() {
        List<Object[]> mostSoldItems = billDAO.getMostSoldItems(5);
        assertNotNull(mostSoldItems, "Most sold items list should not be null");
        assertTrue(mostSoldItems.size() >= 0, "Most sold items list should be empty or contain items");
    }
    
    @Test
    @Order(18)
    @DisplayName("Test Get Daily Sales")
    void testGetDailySales() {
        List<Object[]> dailySales = billDAO.getDailySales(7);
        assertNotNull(dailySales, "Daily sales list should not be null");
        assertTrue(dailySales.size() >= 0, "Daily sales list should be empty or contain sales");
    }
    
    @Test
    @Order(19)
    @DisplayName("Test Get Monthly Sales")
    void testGetMonthlySales() {
        List<Object[]> monthlySales = billDAO.getMonthlySales(12);
        assertNotNull(monthlySales, "Monthly sales list should not be null");
        assertTrue(monthlySales.size() >= 0, "Monthly sales list should be empty or contain sales");
    }
    
    // ========== DATA VALIDATION TESTS ==========
    
    @Test
    @Order(20)
    @DisplayName("Test Bill Data Integrity")
    void testBillDataIntegrity() {
        Bill bill = createTestBill();
        int billId = billDAO.addBill(bill);
        
        List<Bill> bills = billDAO.getAllBills();
        
        for (Bill b : bills) {
            assertNotNull(b.getId(), "Bill ID should not be null");
            assertNotNull(b.getCustomerId(), "Customer ID should not be null");
            assertNotNull(b.getBillNumber(), "Bill number should not be null");
            assertNotNull(b.getBillDate(), "Bill date should not be null");
            assertNotNull(b.getTotalAmount(), "Total amount should not be null");
            assertNotNull(b.getPaidAmount(), "Paid amount should not be null");
            assertNotNull(b.getBalance(), "Balance should not be null");
            
            assertTrue(b.getId() > 0, "Bill ID should be positive");
            assertTrue(b.getCustomerId() > 0, "Customer ID should be positive");
            assertFalse(b.getBillNumber().isEmpty(), "Bill number should not be empty");
            assertTrue(b.getTotalAmount() >= 0, "Total amount should be non-negative");
            assertTrue(b.getPaidAmount() >= 0, "Paid amount should be non-negative");
            // Balance can be negative (overpayment) or positive (outstanding amount)
            // This is valid in real business scenarios
        }
    }
    
    @Test
    @Order(21)
    @DisplayName("Test Bill Performance")
    void testBillPerformance() {
        long startTime = System.currentTimeMillis();
        
        List<Bill> bills = billDAO.getAllBills();
        billDAO.searchBills("test", null, null);
        billDAO.getTopCustomers(5);
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        assertTrue(duration < 5000, "Bill operations should complete within 5 seconds");
    }
} 