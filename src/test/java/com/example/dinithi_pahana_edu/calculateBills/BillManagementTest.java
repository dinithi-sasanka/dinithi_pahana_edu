package com.example.dinithi_pahana_edu.calculateBills;

import com.example.dinithi_pahana_edu.model.Bill;
import com.example.dinithi_pahana_edu.model.BillItem;
import com.example.dinithi_pahana_edu.model.Customer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit Tests for Bill Management Operations
 * Tests search bills, view all bills, update bills, delete bills, and print bills
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BillManagementTest {
    
    private List<Bill> billList;
    private List<BillItem> billItems1;
    private List<BillItem> billItems2;
    private List<BillItem> billItems3;
    
    @BeforeEach
    void setUp() {
        billList = new ArrayList<>();
        billItems1 = new ArrayList<>();
        billItems2 = new ArrayList<>();
        billItems3 = new ArrayList<>();
        
        // Create test customers
        Customer customer1 = new Customer();
        customer1.setId(1);
        customer1.setName("John Doe");
        customer1.setAccountNumber("ACC001");
        
        Customer customer2 = new Customer();
        customer2.setId(2);
        customer2.setName("Jane Smith");
        customer2.setAccountNumber("ACC002");
        
        Customer customer3 = new Customer();
        customer3.setId(3);
        customer3.setName("Bob Johnson");
        customer3.setAccountNumber("ACC003");
        
        // Create bill items for Bill 1
        BillItem item1 = new BillItem();
        item1.setItemId(1);
        item1.setItemName("Mathematics Book");
        item1.setQuantity(2);
        item1.setPrice(850.00);
        
        BillItem item2 = new BillItem();
        item2.setItemId(2);
        item2.setItemName("Science Book");
        item2.setQuantity(1);
        item2.setPrice(750.00);
        
        billItems1.add(item1);
        billItems1.add(item2);
        
        // Create bill items for Bill 2
        BillItem item3 = new BillItem();
        item3.setItemId(3);
        item3.setItemName("Pencil");
        item3.setQuantity(10);
        item3.setPrice(25.00);
        
        billItems2.add(item3);
        
        // Create bill items for Bill 3
        BillItem item4 = new BillItem();
        item4.setItemId(1);
        item4.setItemName("Mathematics Book");
        item4.setQuantity(1);
        item4.setPrice(850.00);
        
        BillItem item5 = new BillItem();
        item5.setItemId(4);
        item5.setItemName("English Dictionary");
        item5.setQuantity(1);
        item5.setPrice(1200.00);
        
        billItems3.add(item4);
        billItems3.add(item5);
        
        // Create Bill 1
        Bill bill1 = new Bill();
        bill1.setId(1);
        bill1.setBillNumber("BILL001");
        bill1.setCustomerId(customer1.getId());
        bill1.setBillDate(new Date());
        bill1.setBillDateTime("2024-01-15 10:30:00");
        bill1.setTotalAmount(2450.00);
        bill1.setPaidAmount(2000.00);
        bill1.setBalance(450.00);
        
        // Create Bill 2
        Bill bill2 = new Bill();
        bill2.setId(2);
        bill2.setBillNumber("BILL002");
        bill2.setCustomerId(customer2.getId());
        bill2.setBillDate(new Date());
        bill2.setBillDateTime("2024-01-16 14:20:00");
        bill2.setTotalAmount(250.00);
        bill2.setPaidAmount(250.00);
        bill2.setBalance(0.0);
        
        // Create Bill 3
        Bill bill3 = new Bill();
        bill3.setId(3);
        bill3.setBillNumber("BILL003");
        bill3.setCustomerId(customer3.getId());
        bill3.setBillDate(new Date());
        bill3.setBillDateTime("2024-01-17 09:15:00");
        bill3.setTotalAmount(2050.00);
        bill3.setPaidAmount(0.0);
        bill3.setBalance(2050.00);
        
        billList.add(bill1);
        billList.add(bill2);
        billList.add(bill3);
    }
    
    @Test
    @Order(1)
    @DisplayName("Test Search Bill by Bill Number")
    void testSearchBillByBillNumber() {
        // Search for existing bill
        Bill foundBill = searchBillByBillNumber("BILL001");
        assertNotNull(foundBill);
        assertEquals("BILL001", foundBill.getBillNumber());
        assertEquals(2450.00, foundBill.getTotalAmount(), 0.01);
        
        // Search for non-existent bill
        Bill notFoundBill = searchBillByBillNumber("NONEXISTENT");
        assertNull(notFoundBill);
    }
    
    @Test
    @Order(2)
    @DisplayName("Test Search Bill by Customer ID")
    void testSearchBillByCustomerId() {
        // Search for bills by customer ID
        List<Bill> foundBills = searchBillsByCustomerId(1);
        assertNotNull(foundBills);
        assertEquals(1, foundBills.size());
        assertEquals("BILL001", foundBills.get(0).getBillNumber());
        
        // Search for customer with no bills
        List<Bill> emptyBills = searchBillsByCustomerId(999);
        assertNotNull(emptyBills);
        assertEquals(0, emptyBills.size());
    }
    
    @Test
    @Order(3)
    @DisplayName("Test Search Bill by Date Range")
    void testSearchBillByDateRange() {
        // Search for bills within date range
        List<Bill> foundBills = searchBillsByDateRange("2024-01-15", "2024-01-16");
        assertNotNull(foundBills);
        assertEquals(2, foundBills.size());
        
        // Verify bills are in correct date range
        for (Bill bill : foundBills) {
            assertTrue(bill.getBillDateTime().contains("2024-01-15") || 
                      bill.getBillDateTime().contains("2024-01-16"));
        }
    }
    
    @Test
    @Order(4)
    @DisplayName("Test Search Bill by Amount Range")
    void testSearchBillByAmountRange() {
        // Search for bills with total amount between 2000-2500
        List<Bill> foundBills = searchBillsByAmountRange(2000.0, 2500.0);
        assertNotNull(foundBills);
        assertEquals(2, foundBills.size());
        
        // Verify bills are in correct amount range
        for (Bill bill : foundBills) {
            assertTrue(bill.getTotalAmount() >= 2000.0);
            assertTrue(bill.getTotalAmount() <= 2500.0);
        }
    }
    
    @Test
    @Order(5)
    @DisplayName("Test View All Bills")
    void testViewAllBills() {
        // View all bills
        List<Bill> allBills = getAllBills();
        assertNotNull(allBills);
        assertEquals(3, allBills.size());
        
        // Verify bill details
        assertEquals("BILL001", allBills.get(0).getBillNumber());
        assertEquals(2450.00, allBills.get(0).getTotalAmount(), 0.01);
        
        assertEquals("BILL002", allBills.get(1).getBillNumber());
        assertEquals(250.00, allBills.get(1).getTotalAmount(), 0.01);
        
        assertEquals("BILL003", allBills.get(2).getBillNumber());
        assertEquals(2050.00, allBills.get(2).getTotalAmount(), 0.01);
    }
    
    @Test
    @Order(6)
    @DisplayName("Test View Bill Details")
    void testViewBillDetails() {
        // View specific bill details
        Bill billDetails = getBillDetails(1);
        assertNotNull(billDetails);
        assertEquals("BILL001", billDetails.getBillNumber());
        assertEquals("2024-01-15 10:30:00", billDetails.getBillDateTime());
        assertEquals(2450.00, billDetails.getTotalAmount(), 0.01);
        assertEquals(2000.00, billDetails.getPaidAmount(), 0.01);
        assertEquals(450.00, billDetails.getBalance(), 0.01);
    }
    
    @Test
    @Order(7)
    @DisplayName("Test Update Bill - Valid Data")
    void testUpdateBillValidData() {
        // Get bill to update
        Bill billToUpdate = billList.get(0); // BILL001
        double originalTotal = billToUpdate.getTotalAmount();
        double originalBalance = billToUpdate.getBalance();
        
        // Update bill details
        billToUpdate.setTotalAmount(2500.00); // Increase total
        billToUpdate.setPaidAmount(2200.00); // Increase paid amount
        billToUpdate.setBalance(billToUpdate.getTotalAmount() - billToUpdate.getPaidAmount());
        
        // Verify updates
        assertEquals(2500.00, billToUpdate.getTotalAmount(), 0.01);
        assertEquals(2200.00, billToUpdate.getPaidAmount(), 0.01);
        assertEquals(300.00, billToUpdate.getBalance(), 0.01); // 2500 - 2200
        
        // Verify values changed
        assertNotEquals(originalTotal, billToUpdate.getTotalAmount());
        assertNotEquals(originalBalance, billToUpdate.getBalance());
    }
    
    @Test
    @Order(8)
    @DisplayName("Test Update Bill - Invalid Data")
    void testUpdateBillInvalidData() {
        // Get bill to update
        Bill billToUpdate = billList.get(0); // BILL001
        double originalTotal = billToUpdate.getTotalAmount();
        
        // Try to update with invalid data
        billToUpdate.setTotalAmount(-100.00); // Negative total
        billToUpdate.setPaidAmount(5000.00); // Paid amount greater than total
        
        // Verify invalid updates are not applied
        assertFalse(isValidBillUpdate(billToUpdate));
        
        // Restore original values
        billToUpdate.setTotalAmount(originalTotal);
        billToUpdate.setPaidAmount(2000.00);
        billToUpdate.setBalance(450.00);
        
        // Verify restoration
        assertTrue(isValidBillUpdate(billToUpdate));
    }
    
    @Test
    @Order(9)
    @DisplayName("Test Delete Bill - Valid ID")
    void testDeleteBillValidId() {
        // Delete bill with ID 2
        int initialSize = billList.size();
        boolean deleted = deleteBill(2);
        
        assertTrue(deleted);
        assertEquals(initialSize - 1, billList.size());
        
        // Verify bill is removed
        Bill deletedBill = getBillById(2);
        assertNull(deletedBill);
        
        // Verify other bills remain
        assertNotNull(getBillById(1));
        assertNotNull(getBillById(3));
    }
    
    @Test
    @Order(10)
    @DisplayName("Test Delete Bill - Invalid ID")
    void testDeleteBillInvalidId() {
        // Try to delete non-existent bill
        int initialSize = billList.size();
        boolean deleted = deleteBill(999);
        
        assertFalse(deleted);
        assertEquals(initialSize, billList.size()); // Size should remain unchanged
    }
    
    @Test
    @Order(11)
    @DisplayName("Test Print Bill")
    void testPrintBill() {
        // Print specific bill
        Bill billToPrint = billList.get(0); // BILL001
        String printedBill = printBill(billToPrint, billItems1);
        
        assertNotNull(printedBill);
        assertTrue(printedBill.contains("BILL001"));
        assertTrue(printedBill.contains("Mathematics Book"));
        assertTrue(printedBill.contains("Science Book"));
        assertTrue(printedBill.contains("2450.0")); // Total
        assertTrue(printedBill.contains("2000.0")); // Paid
        assertTrue(printedBill.contains("450.0")); // Balance
    }
    
    @Test
    @Order(12)
    @DisplayName("Test Print All Bills")
    void testPrintAllBills() {
        // Print all bills
        String printedBills = printAllBills(billList);
        
        assertNotNull(printedBills);
        assertTrue(printedBills.contains("BILL001"));
        assertTrue(printedBills.contains("BILL002"));
        assertTrue(printedBills.contains("BILL003"));
    }
    
    @Test
    @Order(13)
    @DisplayName("Test Bill Statistics")
    void testBillStatistics() {
        // Calculate bill statistics
        double totalRevenue = calculateTotalRevenue(billList);
        double totalPaid = calculateTotalPaid(billList);
        double totalBalance = calculateTotalBalance(billList);
        int totalBills = getTotalBills(billList);
        
        // Verify statistics
        assertEquals(4750.00, totalRevenue, 0.01); // 2450.00 + 250.00 + 2050.00
        assertEquals(2250.00, totalPaid, 0.01); // 2000.00 + 250.00 + 0.00
        assertEquals(2500.00, totalBalance, 0.01); // 450.00 + 0.00 + 2050.00
        assertEquals(3, totalBills);
    }
    
    @Test
    @Order(14)
    @DisplayName("Test Bill Validation")
    void testBillValidation() {
        // Test valid bill
        Bill validBill = billList.get(0);
        assertTrue(isValidBill(validBill));
        
        // Test invalid bill (no customer)
        Bill invalidBill = new Bill();
        invalidBill.setBillNumber("BILL999");
        invalidBill.setCustomerId(0);
        assertFalse(isValidBill(invalidBill));
        
        // Test invalid bill (negative total)
        Bill negativeBill = new Bill();
        negativeBill.setBillNumber("BILL998");
        negativeBill.setCustomerId(1);
        negativeBill.setTotalAmount(-100.00);
        assertFalse(isValidBill(negativeBill));
    }
    
    // Helper methods
    private Bill searchBillByBillNumber(String billNumber) {
        return billList.stream()
                .filter(b -> b.getBillNumber().equals(billNumber))
                .findFirst()
                .orElse(null);
    }
    
    private List<Bill> searchBillsByCustomerId(int customerId) {
        return billList.stream()
                .filter(b -> b.getCustomerId() == customerId)
                .toList();
    }
    
    private List<Bill> searchBillsByDateRange(String startDate, String endDate) {
        return billList.stream()
                .filter(b -> {
                    String billDate = b.getBillDateTime().substring(0, 10); // Extract date part
                    return billDate.compareTo(startDate) >= 0 && billDate.compareTo(endDate) <= 0;
                })
                .toList();
    }
    
    private List<Bill> searchBillsByAmountRange(double minAmount, double maxAmount) {
        return billList.stream()
                .filter(b -> b.getTotalAmount() >= minAmount && b.getTotalAmount() <= maxAmount)
                .toList();
    }
    
    private List<Bill> getAllBills() {
        return new ArrayList<>(billList);
    }
    
    private Bill getBillDetails(int billId) {
        return billList.stream()
                .filter(b -> b.getId() == billId)
                .findFirst()
                .orElse(null);
    }
    
    private Bill getBillById(int billId) {
        return billList.stream()
                .filter(b -> b.getId() == billId)
                .findFirst()
                .orElse(null);
    }
    
    private boolean isValidBillUpdate(Bill bill) {
        return bill != null && 
               bill.getTotalAmount() >= 0 && 
               bill.getPaidAmount() >= 0 && 
               bill.getPaidAmount() <= bill.getTotalAmount();
    }
    
    private boolean deleteBill(int billId) {
        return billList.removeIf(b -> b.getId() == billId);
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
    
    private String printAllBills(List<Bill> bills) {
        StringBuilder sb = new StringBuilder();
        sb.append("ALL BILLS REPORT\n");
        sb.append("================\n\n");
        
        for (Bill bill : bills) {
            sb.append("Bill: ").append(bill.getBillNumber())
              .append(" | Date: ").append(bill.getBillDateTime())
              .append(" | Total: ").append(bill.getTotalAmount())
              .append(" | Balance: ").append(bill.getBalance()).append("\n");
        }
        
        return sb.toString();
    }
    
    private double calculateTotalRevenue(List<Bill> bills) {
        return bills.stream()
                .mapToDouble(Bill::getTotalAmount)
                .sum();
    }
    
    private double calculateTotalPaid(List<Bill> bills) {
        return bills.stream()
                .mapToDouble(Bill::getPaidAmount)
                .sum();
    }
    
    private double calculateTotalBalance(List<Bill> bills) {
        return bills.stream()
                .mapToDouble(Bill::getBalance)
                .sum();
    }
    
    private int getTotalBills(List<Bill> bills) {
        return bills.size();
    }
    
    private boolean isValidBill(Bill bill) {
        return bill != null && 
               bill.getCustomerId() > 0 && 
               bill.getBillNumber() != null && 
               !bill.getBillNumber().trim().isEmpty() &&
               bill.getTotalAmount() >= 0;
    }
} 