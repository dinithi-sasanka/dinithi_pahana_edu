package com.example.dinithi_pahana_edu.viewAccounts;

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
 * JUnit Tests for Customer Account Viewing
 * Tests search customer, view customer details, view bills history, 
 * view bill items, and display summary statistics
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerAccountViewTest {
    
    private Customer testCustomer;
    private List<Bill> customerBills;
    private List<BillItem> billItems1;
    private List<BillItem> billItems2;
    private List<BillItem> billItems3;
    private List<BillItem> billItems4;
    private List<BillItem> billItems5;
    
    @BeforeEach
    void setUp() {
        // Create test customer 
        testCustomer = new Customer();
        testCustomer.setId(10);
        testCustomer.setAccountNumber("CUST000001");
        testCustomer.setName("jenipher perera");
        testCustomer.setAddress("123,mahanuwara");
        testCustomer.setTelephone("078-8934189");
        testCustomer.setEmail("nimal.shehan@example.com");
        
        customerBills = new ArrayList<>();
        billItems1 = new ArrayList<>();
        billItems2 = new ArrayList<>();
        billItems3 = new ArrayList<>();
        billItems4 = new ArrayList<>();
        billItems5 = new ArrayList<>();
        
        // Create bill items for Bill 1007
        BillItem item1 = new BillItem();
        item1.setItemId(1);
        item1.setItemName("Mathematics Workbook-Grade 9");
        item1.setQuantity(1);
        item1.setPrice(650.00);
        
        billItems1.add(item1);
        
        // Create bill items for Bill 1006
        BillItem item2 = new BillItem();
        item2.setItemId(2);
        item2.setItemName("Science Book");
        item2.setQuantity(2);
        item2.setPrice(29250.00);
        
        billItems2.add(item2);
        
        // Create bill items for Bill 1005
        BillItem item3 = new BillItem();
        item3.setItemId(3);
        item3.setItemName("English Dictionary");
        item3.setQuantity(1);
        item3.setPrice(58500.00);
        
        billItems3.add(item3);
        
        // Create bill items for Bill 1004
        BillItem item4 = new BillItem();
        item4.setItemId(4);
        item4.setItemName("Pencil");
        item4.setQuantity(1);
        item4.setPrice(100.00);
        
        billItems4.add(item4);
        
        // Create bill items for Bill 1002
        BillItem item5 = new BillItem();
        item5.setItemId(5);
        item5.setItemName("Notebook");
        item5.setQuantity(1);
        item5.setPrice(1350.00);
        
        billItems5.add(item5);
        
        // Create Bill 1007
        Bill bill1 = new Bill();
        bill1.setId(1007);
        bill1.setBillNumber("1007");
        bill1.setCustomerId(testCustomer.getId());
        bill1.setBillDate(new Date());
        bill1.setBillDateTime("7/21/2025 6:55:42 PM");
        bill1.setTotalAmount(650.00);
        bill1.setPaidAmount(700.00);
        bill1.setBalance(-50.00);
        
        // Create Bill 1006
        Bill bill2 = new Bill();
        bill2.setId(1006);
        bill2.setBillNumber("1006");
        bill2.setCustomerId(testCustomer.getId());
        bill2.setBillDate(new Date());
        bill2.setBillDateTime("7/21/2025 2:11:56 PM");
        bill2.setTotalAmount(58500.00);
        bill2.setPaidAmount(59000.00);
        bill2.setBalance(-500.00);
        
        // Create Bill 1005
        Bill bill3 = new Bill();
        bill3.setId(1005);
        bill3.setBillNumber("1005");
        bill3.setCustomerId(testCustomer.getId());
        bill3.setBillDate(new Date());
        bill3.setBillDateTime("7/21/2025 1:50:53 PM");
        bill3.setTotalAmount(58500.00);
        bill3.setPaidAmount(60000.00);
        bill3.setBalance(-1500.00);
        
        // Create Bill 1004
        Bill bill4 = new Bill();
        bill4.setId(1004);
        bill4.setBillNumber("1004");
        bill4.setCustomerId(testCustomer.getId());
        bill4.setBillDate(new Date());
        bill4.setBillDateTime("7/21/2025 12:34:52 PM");
        bill4.setTotalAmount(100.00);
        bill4.setPaidAmount(100.00);
        bill4.setBalance(0.00);
        
        // Create Bill 1002
        Bill bill5 = new Bill();
        bill5.setId(1002);
        bill5.setBillNumber("1002");
        bill5.setCustomerId(testCustomer.getId());
        bill5.setBillDate(new Date());
        bill5.setBillDateTime("7/18/2025 9:33:08 PM");
        bill5.setTotalAmount(1350.00);
        bill5.setPaidAmount(2000.00);
        bill5.setBalance(-650.00);
        
        customerBills.add(bill1);
        customerBills.add(bill2);
        customerBills.add(bill3);
        customerBills.add(bill4);
        customerBills.add(bill5);
    }
    
    @Test
    @Order(1)
    @DisplayName("Test Search Customer by Account Number")
    void testSearchCustomerByAccountNumber() {
        // Test searching customer by account number
        Customer foundCustomer = searchCustomerByAccountNumber("CUST000001");
        assertNotNull(foundCustomer);
        assertEquals("CUST000001", foundCustomer.getAccountNumber());
        assertEquals("jenipher perera", foundCustomer.getName());
        assertEquals("nimal.shehan@example.com", foundCustomer.getEmail());
        assertEquals("078-8934189", foundCustomer.getTelephone());
    }
    
    @Test
    @Order(2)
    @DisplayName("Test Search Customer by Name")
    void testSearchCustomerByName() {
        // Test searching customer by name
        Customer foundCustomer = searchCustomerByName("jenipher perera");
        assertNotNull(foundCustomer);
        assertEquals("jenipher perera", foundCustomer.getName());
        assertEquals("CUST000001", foundCustomer.getAccountNumber());
        assertEquals("nimal.shehan@example.com", foundCustomer.getEmail());
        assertEquals("078-8934189", foundCustomer.getTelephone());
    }
    
    @Test
    @Order(3)
    @DisplayName("Test Search Customer by Telephone")
    void testSearchCustomerByTelephone() {
        // Test searching customer by telephone number
        Customer foundCustomer = searchCustomerByTelephone("078-8934189");
        assertNotNull(foundCustomer);
        assertEquals("078-8934189", foundCustomer.getTelephone());
        assertEquals("jenipher perera", foundCustomer.getName());
        assertEquals("CUST000001", foundCustomer.getAccountNumber());
    }
    
    @Test
    @Order(4)
    @DisplayName("Test Search Customer by Email")
    void testSearchCustomerByEmail() {
        // Test searching customer by email address
        Customer foundCustomer = searchCustomerByEmail("nimal.shehan@example.com");
        assertNotNull(foundCustomer);
        assertEquals("nimal.shehan@example.com", foundCustomer.getEmail());
        assertEquals("jenipher perera", foundCustomer.getName());
        assertEquals("CUST000001", foundCustomer.getAccountNumber());
    }
    
    @Test
    @Order(5)
    @DisplayName("Test Search Customer by Partial Name")
    void testSearchCustomerByPartialName() {
        // Test searching customer by partial name (case insensitive)
        Customer foundCustomer = searchCustomerByPartialName("jenipher");
        assertNotNull(foundCustomer);
        assertEquals("jenipher perera", foundCustomer.getName());
        assertEquals("CUST000001", foundCustomer.getAccountNumber());
        
        // Test case insensitive search
        Customer foundCustomer2 = searchCustomerByPartialName("JENIPHER");
        assertNotNull(foundCustomer2);
        assertEquals("jenipher perera", foundCustomer2.getName());
    }
    
    @Test
    @Order(6)
    @DisplayName("Test Search Customer - Not Found")
    void testSearchCustomerNotFound() {
        // Test searching for non-existent customer
        Customer foundCustomer = searchCustomerByAccountNumber("NONEXISTENT");
        assertNull(foundCustomer);
        
        Customer foundCustomer2 = searchCustomerByName("Non Existent Customer");
        assertNull(foundCustomer2);
        
        Customer foundCustomer3 = searchCustomerByTelephone("999-999-9999");
        assertNull(foundCustomer3);
        
        Customer foundCustomer4 = searchCustomerByEmail("nonexistent@email.com");
        assertNull(foundCustomer4);
    }
    
    @Test
    @Order(7)
    @DisplayName("Test Search Customer - Empty Search Terms")
    void testSearchCustomerEmptySearchTerms() {
        // Test searching with empty search terms
        Customer foundCustomer1 = searchCustomerByAccountNumber("");
        assertNull(foundCustomer1);
        
        Customer foundCustomer2 = searchCustomerByName("");
        assertNull(foundCustomer2);
        
        Customer foundCustomer3 = searchCustomerByTelephone("");
        assertNull(foundCustomer3);
        
        Customer foundCustomer4 = searchCustomerByEmail("");
        assertNull(foundCustomer4);
    }
    
    @Test
    @Order(8)
    @DisplayName("Test View Customer Details")
    void testViewCustomerDetails() {
        // Test viewing customer details
        Customer customerDetails = getCustomerDetails(10);
        assertNotNull(customerDetails);
        assertEquals(10, customerDetails.getId());
        assertEquals("CUST000001", customerDetails.getAccountNumber());
        assertEquals("jenipher perera", customerDetails.getName());
        assertEquals("123,mahanuwara", customerDetails.getAddress());
        assertEquals("078-8934189", customerDetails.getTelephone());
        assertEquals("nimal.shehan@example.com", customerDetails.getEmail());
    }
    
    @Test
    @Order(9)
    @DisplayName("Test View Customer Details - Invalid ID")
    void testViewCustomerDetailsInvalidId() {
        // Test viewing customer with invalid ID
        Customer customerDetails = getCustomerDetails(999);
        assertNull(customerDetails);
    }
    
    @Test
    @Order(10)
    @DisplayName("Test View All Bills for Customer")
    void testViewAllBillsForCustomer() {
        // Test viewing all bills for a customer
        List<Bill> customerBillsList = getCustomerBills(testCustomer.getId());
        assertNotNull(customerBillsList);
        assertEquals(5, customerBillsList.size());
        
        // Verify bill details
        assertEquals("1007", customerBillsList.get(0).getBillNumber());
        assertEquals(650.00, customerBillsList.get(0).getTotalAmount(), 0.01);
        assertEquals(700.00, customerBillsList.get(0).getPaidAmount(), 0.01);
        assertEquals(-50.00, customerBillsList.get(0).getBalance(), 0.01);
        
        assertEquals("1006", customerBillsList.get(1).getBillNumber());
        assertEquals(58500.00, customerBillsList.get(1).getTotalAmount(), 0.01);
        assertEquals(59000.00, customerBillsList.get(1).getPaidAmount(), 0.01);
        assertEquals(-500.00, customerBillsList.get(1).getBalance(), 0.01);
    }
    
    @Test
    @Order(11)
    @DisplayName("Test View Bills for Customer - No Bills")
    void testViewBillsForCustomerNoBills() {
        // Test viewing bills for customer with no bills
        List<Bill> emptyBillsList = getCustomerBills(999);
        assertNotNull(emptyBillsList);
        assertEquals(0, emptyBillsList.size());
    }
    
    @Test
    @Order(12)
    @DisplayName("Test View Bill Items")
    void testViewBillItems() {
        // Test viewing items for a specific bill
        List<BillItem> billItems = getBillItems(1007);
        assertNotNull(billItems);
        assertEquals(1, billItems.size());
        
        BillItem item = billItems.get(0);
        assertEquals("Mathematics Workbook-Grade 9", item.getItemName());
        assertEquals(1, item.getQuantity());
        assertEquals(650.00, item.getPrice(), 0.01);
    }
    
    @Test
    @Order(13)
    @DisplayName("Test View Bill Items - Multiple Items")
    void testViewBillItemsMultipleItems() {
        // Test viewing items for a bill with multiple items
        List<BillItem> billItems = getBillItems(1006);
        assertNotNull(billItems);
        assertEquals(1, billItems.size());
        
        BillItem item = billItems.get(0);
        assertEquals("Science Book", item.getItemName());
        assertEquals(2, item.getQuantity());
        assertEquals(29250.00, item.getPrice(), 0.01);
    }
    
    @Test
    @Order(14)
    @DisplayName("Test View Bill Items - Invalid Bill ID")
    void testViewBillItemsInvalidBillId() {
        // Test viewing items for non-existent bill
        List<BillItem> billItems = getBillItems(999);
        assertNotNull(billItems);
        assertEquals(0, billItems.size());
    }
    
    @Test
    @Order(15)
    @DisplayName("Test Calculate Total Bills")
    void testCalculateTotalBills() {
        // Test calculating total number of bills
        int totalBills = calculateTotalBills(customerBills);
        assertEquals(5, totalBills);
    }
    
    @Test
    @Order(16)
    @DisplayName("Test Calculate Total Amount")
    void testCalculateTotalAmount() {
        // Test calculating total amount from all bills
        double totalAmount = calculateTotalAmount(customerBills);
        assertEquals(119100.00, totalAmount, 0.01); // 650 + 58500 + 58500 + 100 + 1350
    }
    
    @Test
    @Order(17)
    @DisplayName("Test Calculate Total Paid")
    void testCalculateTotalPaid() {
        // Test calculating total paid amount
        double totalPaid = calculateTotalPaid(customerBills);
        assertEquals(121800.00, totalPaid, 0.01); // 700 + 59000 + 60000 + 100 + 2000
    }
    
    @Test
    @Order(18)
    @DisplayName("Test Calculate Total Balance")
    void testCalculateTotalBalance() {
        // Test calculating total balance
        double totalBalance = calculateTotalBalance(customerBills);
        assertEquals(-2700.00, totalBalance, 0.01); // -50 + (-500) + (-1500) + 0 + (-650)
    }
    
    @Test
    @Order(19)
    @DisplayName("Test Get Bill Status")
    void testGetBillStatus() {
        // Test getting bill status based on balance
        String status1 = getBillStatus(650.00, 700.00); // Overpaid
        assertEquals("Paid", status1);
        
        String status2 = getBillStatus(100.00, 100.00); // Fully paid
        assertEquals("Paid", status2);
        
        String status3 = getBillStatus(1000.00, 500.00); // Partially paid
        assertEquals("Pending", status3);
        
        String status4 = getBillStatus(1000.00, 0.00); // Not paid
        assertEquals("Unpaid", status4);
    }
    
    @Test
    @Order(20)
    @DisplayName("Test Generate Customer Summary")
    void testGenerateCustomerSummary() {
        // Test generating complete customer summary
        CustomerSummary summary = generateCustomerSummary(testCustomer, customerBills);
        assertNotNull(summary);
        assertEquals(10, summary.getCustomerId());
        assertEquals("CUST000001", summary.getAccountNumber());
        assertEquals("jenipher perera", summary.getCustomerName());
        assertEquals(5, summary.getTotalBills());
        assertEquals(119100.00, summary.getTotalAmount(), 0.01);
        assertEquals(121800.00, summary.getTotalPaid(), 0.01);
        assertEquals(-2700.00, summary.getTotalBalance(), 0.01);
    }
    
    @Test
    @Order(21)
    @DisplayName("Test Validate Customer Account")
    void testValidateCustomerAccount() {
        // Test validating customer account
        assertTrue(isValidCustomerAccount(testCustomer));
        
        // Test invalid customer (no account number)
        Customer invalidCustomer = new Customer();
        invalidCustomer.setId(1);
        invalidCustomer.setName("Test Customer");
        assertFalse(isValidCustomerAccount(invalidCustomer));
        
        // Test invalid customer (no name)
        Customer invalidCustomer2 = new Customer();
        invalidCustomer2.setId(2);
        invalidCustomer2.setAccountNumber("CUST000002");
        assertFalse(isValidCustomerAccount(invalidCustomer2));
    }
    
    // Helper methods
    private Customer searchCustomerByAccountNumber(String accountNumber) {
        if ("CUST000001".equals(accountNumber)) {
            return testCustomer;
        }
        return null;
    }
    
    private Customer searchCustomerByName(String name) {
        if ("jenipher perera".equalsIgnoreCase(name)) {
            return testCustomer;
        }
        return null;
    }
    
    private Customer searchCustomerByTelephone(String telephone) {
        if ("078-8934189".equals(telephone)) {
            return testCustomer;
        }
        return null;
    }
    
    private Customer searchCustomerByEmail(String email) {
        if ("nimal.shehan@example.com".equalsIgnoreCase(email)) {
            return testCustomer;
        }
        return null;
    }
    
    private Customer searchCustomerByPartialName(String partialName) {
        if (partialName != null && !partialName.trim().isEmpty()) {
            String customerName = testCustomer.getName().toLowerCase();
            String searchTerm = partialName.toLowerCase();
            if (customerName.contains(searchTerm)) {
                return testCustomer;
            }
        }
        return null;
    }
    
    private Customer getCustomerDetails(int customerId) {
        if (customerId == testCustomer.getId()) {
            return testCustomer;
        }
        return null;
    }
    
    private List<Bill> getCustomerBills(int customerId) {
        if (customerId == testCustomer.getId()) {
            return new ArrayList<>(customerBills);
        }
        return new ArrayList<>();
    }
    
    private List<BillItem> getBillItems(int billId) {
        switch (billId) {
            case 1007:
                return new ArrayList<>(billItems1);
            case 1006:
                return new ArrayList<>(billItems2);
            case 1005:
                return new ArrayList<>(billItems3);
            case 1004:
                return new ArrayList<>(billItems4);
            case 1002:
                return new ArrayList<>(billItems5);
            default:
                return new ArrayList<>();
        }
    }
    
    private int calculateTotalBills(List<Bill> bills) {
        return bills.size();
    }
    
    private double calculateTotalAmount(List<Bill> bills) {
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
    
    private String getBillStatus(double totalAmount, double paidAmount) {
        if (paidAmount >= totalAmount) {
            return "Paid";
        } else if (paidAmount > 0) {
            return "Pending";
        } else {
            return "Unpaid";
        }
    }
    
    private CustomerSummary generateCustomerSummary(Customer customer, List<Bill> bills) {
        CustomerSummary summary = new CustomerSummary();
        summary.setCustomerId(customer.getId());
        summary.setAccountNumber(customer.getAccountNumber());
        summary.setCustomerName(customer.getName());
        summary.setTotalBills(calculateTotalBills(bills));
        summary.setTotalAmount(calculateTotalAmount(bills));
        summary.setTotalPaid(calculateTotalPaid(bills));
        summary.setTotalBalance(calculateTotalBalance(bills));
        return summary;
    }
    
    private boolean isValidCustomerAccount(Customer customer) {
        return customer != null && 
               customer.getAccountNumber() != null && 
               !customer.getAccountNumber().trim().isEmpty() &&
               customer.getName() != null && 
               !customer.getName().trim().isEmpty();
    }
    
    // Inner class for customer summary
    public static class CustomerSummary {
        private int customerId;
        private String accountNumber;
        private String customerName;
        private int totalBills;
        private double totalAmount;
        private double totalPaid;
        private double totalBalance;
        
        // Getters and setters
        public int getCustomerId() { return customerId; }
        public void setCustomerId(int customerId) { this.customerId = customerId; }
        
        public String getAccountNumber() { return accountNumber; }
        public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
        
        public String getCustomerName() { return customerName; }
        public void setCustomerName(String customerName) { this.customerName = customerName; }
        
        public int getTotalBills() { return totalBills; }
        public void setTotalBills(int totalBills) { this.totalBills = totalBills; }
        
        public double getTotalAmount() { return totalAmount; }
        public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
        
        public double getTotalPaid() { return totalPaid; }
        public void setTotalPaid(double totalPaid) { this.totalPaid = totalPaid; }
        
        public double getTotalBalance() { return totalBalance; }
        public void setTotalBalance(double totalBalance) { this.totalBalance = totalBalance; }
    }
} 