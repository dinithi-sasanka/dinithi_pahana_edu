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
 * JUnit Tests for Account Reporting and Analytics
 * Tests bill status analysis, payment trends, and account analytics
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountReportingTest {
    
    private Customer testCustomer;
    private List<Bill> customerBills;
    
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
        
        // Create test bills
        Bill bill1 = new Bill();
        bill1.setId(1007);
        bill1.setBillNumber("1007");
        bill1.setCustomerId(testCustomer.getId());
        bill1.setTotalAmount(650.00);
        bill1.setPaidAmount(700.00);
        bill1.setBalance(-50.00);
        
        Bill bill2 = new Bill();
        bill2.setId(1006);
        bill2.setBillNumber("1006");
        bill2.setCustomerId(testCustomer.getId());
        bill2.setTotalAmount(58500.00);
        bill2.setPaidAmount(59000.00);
        bill2.setBalance(-500.00);
        
        Bill bill3 = new Bill();
        bill3.setId(1005);
        bill3.setBillNumber("1005");
        bill3.setCustomerId(testCustomer.getId());
        bill3.setTotalAmount(58500.00);
        bill3.setPaidAmount(60000.00);
        bill3.setBalance(-1500.00);
        
        customerBills.add(bill1);
        customerBills.add(bill2);
        customerBills.add(bill3);
    }
    
    @Test
    @Order(1)
    @DisplayName("Test Analyze Bill Status Distribution")
    void testAnalyzeBillStatusDistribution() {
        // Test analyzing bill status distribution
        int totalBills = customerBills.size();
        int paidBills = (int) customerBills.stream()
                .filter(bill -> bill.getPaidAmount() >= bill.getTotalAmount())
                .count();
        
        assertEquals(3, totalBills);
        assertEquals(3, paidBills);
        assertEquals(100.0, (double) paidBills / totalBills * 100, 0.01);
    }
    
    @Test
    @Order(2)
    @DisplayName("Test Calculate Payment Trends")
    void testCalculatePaymentTrends() {
        // Test calculating payment trends
        double totalBilled = customerBills.stream()
                .mapToDouble(Bill::getTotalAmount)
                .sum();
        double totalPaid = customerBills.stream()
                .mapToDouble(Bill::getPaidAmount)
                .sum();
        double paymentRate = totalBilled > 0 ? (totalPaid / totalBilled) * 100 : 0;
        
        assertEquals(117650.00, totalBilled, 0.01);
        assertEquals(119700.00, totalPaid, 0.01);
        assertEquals(101.74, paymentRate, 0.01);
    }
    
    @Test
    @Order(3)
    @DisplayName("Test Generate Account Summary Report")
    void testGenerateAccountSummaryReport() {
        // Test generating account summary report
        double totalBilled = customerBills.stream()
                .mapToDouble(Bill::getTotalAmount)
                .sum();
        double totalPaid = customerBills.stream()
                .mapToDouble(Bill::getPaidAmount)
                .sum();
        double totalBalance = customerBills.stream()
                .mapToDouble(Bill::getBalance)
                .sum();
        
        assertEquals(117650.00, totalBilled, 0.01);
        assertEquals(119700.00, totalPaid, 0.01);
        assertEquals(-2050.00, totalBalance, 0.01);
        
        // Determine payment status
        String paymentStatus = totalBalance < 0 ? "Excellent" : 
                              totalBalance == 0 ? "Good" : "Needs Attention";
        assertEquals("Excellent", paymentStatus);
    }
    
    @Test
    @Order(4)
    @DisplayName("Test Calculate Overpayment Analysis")
    void testCalculateOverpaymentAnalysis() {
        // Test calculating overpayment analysis
        List<Bill> overpaidBills = customerBills.stream()
                .filter(bill -> bill.getBalance() < 0)
                .toList();
        
        double totalOverpaid = overpaidBills.stream()
                .mapToDouble(bill -> Math.abs(bill.getBalance()))
                .sum();
        
        assertEquals(3, overpaidBills.size());
        assertEquals(2050.00, totalOverpaid, 0.01);
        assertEquals(683.33, totalOverpaid / overpaidBills.size(), 0.01);
    }
    
    @Test
    @Order(5)
    @DisplayName("Test Validate Account Data")
    void testValidateAccountData() {
        // Test validating account data
        assertTrue(isValidAccountData(testCustomer, customerBills));
        
        // Test invalid customer
        Customer invalidCustomer = new Customer();
        assertFalse(isValidAccountData(invalidCustomer, customerBills));
        
        // Test null bills list
        assertFalse(isValidAccountData(testCustomer, null));
    }
    
    // Helper methods
    private boolean isValidAccountData(Customer customer, List<Bill> bills) {
        return customer != null && 
               customer.getAccountNumber() != null && 
               !customer.getAccountNumber().trim().isEmpty() &&
               bills != null;
    }
} 