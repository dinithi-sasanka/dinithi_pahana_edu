package com.example.dinithi_pahana_edu.reports;

import com.example.dinithi_pahana_edu.service.BillService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Calendar;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit Tests for Monthly Sales Report Functionality
 * Tests the monthly sales report features including date range filtering
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MonthlySalesReportTest {
    
    private BillService billService;
    private SimpleDateFormat dateFormat;
    
    @BeforeEach
    void setUp() {
        billService = new BillService();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    @Order(1)
    @DisplayName("Test Get Monthly Sales for Last 12 Months")
    void testGetMonthlySalesLast12Months() {
        List<Object[]> monthlySales = billService.getMonthlySales(12);
        
        assertNotNull(monthlySales, "Monthly sales list should not be null");
        
        // Verify each row has correct structure
        for (Object[] row : monthlySales) {
            assertNotNull(row, "Row should not be null");
            assertEquals(2, row.length, "Each row should have 2 elements (month, total_sales)");
            assertNotNull(row[0], "Month should not be null");
            assertNotNull(row[1], "Total sales should not be null");
        }
    }
    
    @Test
    @Order(2)
    @DisplayName("Test Get Monthly Sales for Last 6 Months")
    void testGetMonthlySalesLast6Months() {
        List<Object[]> monthlySales = billService.getMonthlySales(6);
        
        assertNotNull(monthlySales, "Monthly sales list should not be null");
        assertTrue(monthlySales.size() <= 6, "Should not return more than 6 months of data");
        
        // Verify data structure
        for (Object[] row : monthlySales) {
            assertNotNull(row, "Row should not be null");
            assertEquals(2, row.length, "Each row should have 2 elements");
        }
    }
    
    @Test
    @Order(3)
    @DisplayName("Test Get Monthly Sales by Date Range")
    void testGetMonthlySalesByDateRange() {
        // Get current date and 12 months ago
        Calendar cal = Calendar.getInstance();
        String endDate = dateFormat.format(cal.getTime());
        
        cal.add(Calendar.MONTH, -12);
        String startDate = dateFormat.format(cal.getTime());
        
        List<Object[]> monthlySales = billService.getMonthlySalesByDateRange(startDate, endDate);
        
        assertNotNull(monthlySales, "Monthly sales by date range should not be null");
        
        // Verify each row has correct structure
        for (Object[] row : monthlySales) {
            assertNotNull(row, "Row should not be null");
            assertEquals(2, row.length, "Each row should have 2 elements (month, total_sales)");
            assertNotNull(row[0], "Month should not be null");
            assertNotNull(row[1], "Total sales should not be null");
        }
    }
    
    @Test
    @Order(4)
    @DisplayName("Test Get Monthly Sales by Date Range - Same Month")
    void testGetMonthlySalesByDateRangeSameMonth() {
        Calendar cal = Calendar.getInstance();
        String currentMonth = dateFormat.format(cal.getTime());
        
        List<Object[]> monthlySales = billService.getMonthlySalesByDateRange(currentMonth, currentMonth);
        
        assertNotNull(monthlySales, "Monthly sales for same month should not be null");
        
        // Should return data for that specific month or empty list
        for (Object[] row : monthlySales) {
            assertNotNull(row, "Row should not be null");
            assertEquals(2, row.length, "Each row should have 2 elements");
        }
    }
    
    @Test
    @Order(5)
    @DisplayName("Test Get Monthly Sales by Date Range - Invalid Dates")
    void testGetMonthlySalesByDateRangeInvalidDates() {
        // Test with invalid date format
        List<Object[]> monthlySales = billService.getMonthlySalesByDateRange("invalid-date", "invalid-date");
        
        assertNotNull(monthlySales, "Should return empty list for invalid dates");
        assertTrue(monthlySales.isEmpty(), "Should return empty list for invalid dates");
    }
    
    @Test
    @Order(6)
    @DisplayName("Test Get Monthly Sales by Date Range - Future Dates")
    void testGetMonthlySalesByDateRangeFutureDates() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        String futureDate = dateFormat.format(cal.getTime());
        
        List<Object[]> monthlySales = billService.getMonthlySalesByDateRange(futureDate, futureDate);
        
        assertNotNull(monthlySales, "Should return empty list for future dates");
        assertTrue(monthlySales.isEmpty(), "Should return empty list for future dates");
    }
    
    @Test
    @Order(7)
    @DisplayName("Test Monthly Sales Data Validation")
    void testMonthlySalesDataValidation() {
        List<Object[]> monthlySales = billService.getMonthlySales(12);
        
        assertNotNull(monthlySales, "Monthly sales should not be null");
        
        for (Object[] row : monthlySales) {
            assertNotNull(row, "Row should not be null");
            assertEquals(2, row.length, "Each row should have 2 elements");
            
            // Verify month format (YYYY-MM)
            if (row[0] != null) {
                assertTrue(row[0] instanceof String, "First element should be a string");
                String month = (String) row[0];
                assertTrue(month.matches("\\d{4}-\\d{2}"), "Month should be in YYYY-MM format: " + month);
            }
            
            // Verify sales amount is numeric and non-negative
            if (row[1] != null) {
                assertTrue(row[1] instanceof Number, "Second element should be a number");
                double sales = ((Number) row[1]).doubleValue();
                assertTrue(sales >= 0, "Sales amount should be non-negative");
            }
        }
    }
    
    @Test
    @Order(8)
    @DisplayName("Test Monthly Sales Summary Calculations")
    void testMonthlySalesSummaryCalculations() {
        List<Object[]> monthlySales = billService.getMonthlySales(12);
        
        assertNotNull(monthlySales, "Monthly sales should not be null");
        
        double totalSales = 0.0;
        int monthsWithSales = 0;
        
        for (Object[] row : monthlySales) {
            if (row[1] != null) {
                totalSales += ((Number) row[1]).doubleValue();
                monthsWithSales++;
            }
        }
        
        double averageSales = monthsWithSales > 0 ? totalSales / monthsWithSales : 0.0;
        
        assertTrue(totalSales >= 0, "Total sales should be non-negative");
        assertTrue(monthsWithSales >= 0, "Months with sales should be non-negative");
        assertTrue(averageSales >= 0, "Average sales should be non-negative");
    }
    
    @Test
    @Order(9)
    @DisplayName("Test Monthly Sales Performance")
    void testMonthlySalesPerformance() {
        long startTime = System.currentTimeMillis();
        
        List<Object[]> monthlySales = billService.getMonthlySales(12);
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        assertNotNull(monthlySales, "Monthly sales should not be null");
        assertTrue(duration < 5000, "Query should complete within 5 seconds: " + duration + "ms");
    }
    
    @Test
    @Order(10)
    @DisplayName("Test Monthly Sales Date Range Edge Cases")
    void testMonthlySalesDateRangeEdgeCases() {
        // Test with very old dates
        List<Object[]> monthlySales = billService.getMonthlySalesByDateRange("2000-01-01", "2000-12-31");
        
        assertNotNull(monthlySales, "Should handle old dates gracefully");
        
        // Test with current year
        Calendar cal = Calendar.getInstance();
        String currentYearStart = cal.get(Calendar.YEAR) + "-01-01";
        String currentYearEnd = dateFormat.format(cal.getTime());
        
        List<Object[]> currentYearSales = billService.getMonthlySalesByDateRange(currentYearStart, currentYearEnd);
        
        assertNotNull(currentYearSales, "Should handle current year dates");
        assertTrue(currentYearSales.size() <= 12, "Should not return more than 12 months for current year");
    }
} 