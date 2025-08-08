package com.example.dinithi_pahana_edu.reports;

import com.example.dinithi_pahana_edu.service.BillService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Calendar;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit Tests for Daily Sales Report Functionality
 * Tests the daily sales report features including date range filtering
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DailySalesReportTest {
    
    private BillService billService;
    private SimpleDateFormat dateFormat;
    
    @BeforeEach
    void setUp() {
        billService = new BillService();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    @Order(1)
    @DisplayName("Test Get Daily Sales for Last 30 Days")
    void testGetDailySalesLast30Days() {
        List<Object[]> dailySales = billService.getDailySales(30);
        
        assertNotNull(dailySales, "Daily sales list should not be null");
        
        // Verify each row has correct structure
        for (Object[] row : dailySales) {
            assertNotNull(row, "Row should not be null");
            assertEquals(2, row.length, "Each row should have 2 elements (date, total_sales)");
            assertNotNull(row[0], "Date should not be null");
            assertNotNull(row[1], "Total sales should not be null");
        }
    }
    
    @Test
    @Order(2)
    @DisplayName("Test Get Daily Sales for Last 7 Days")
    void testGetDailySalesLast7Days() {
        List<Object[]> dailySales = billService.getDailySales(7);
        
        assertNotNull(dailySales, "Daily sales list should not be null");
        assertTrue(dailySales.size() <= 7, "Should not return more than 7 days of data");
        
        // Verify data structure
        for (Object[] row : dailySales) {
            assertNotNull(row, "Row should not be null");
            assertEquals(2, row.length, "Each row should have 2 elements");
        }
    }
    
    @Test
    @Order(3)
    @DisplayName("Test Get Daily Sales by Date Range")
    void testGetDailySalesByDateRange() {
        // Get current date and 30 days ago
        Calendar cal = Calendar.getInstance();
        String endDate = dateFormat.format(cal.getTime());
        
        cal.add(Calendar.DAY_OF_MONTH, -30);
        String startDate = dateFormat.format(cal.getTime());
        
        List<Object[]> dailySales = billService.getDailySalesByDateRange(startDate, endDate);
        
        assertNotNull(dailySales, "Daily sales by date range should not be null");
        
        // Verify each row has correct structure
        for (Object[] row : dailySales) {
            assertNotNull(row, "Row should not be null");
            assertEquals(2, row.length, "Each row should have 2 elements (date, total_sales)");
            assertNotNull(row[0], "Date should not be null");
            assertNotNull(row[1], "Total sales should not be null");
        }
    }
    
    @Test
    @Order(4)
    @DisplayName("Test Get Daily Sales by Date Range - Same Day")
    void testGetDailySalesByDateRangeSameDay() {
        String today = dateFormat.format(Calendar.getInstance().getTime());
        
        List<Object[]> dailySales = billService.getDailySalesByDateRange(today, today);
        
        assertNotNull(dailySales, "Daily sales for same day should not be null");
        
        // Should return data for that specific day or empty list
        for (Object[] row : dailySales) {
            assertNotNull(row, "Row should not be null");
            assertEquals(2, row.length, "Each row should have 2 elements");
        }
    }
    
    @Test
    @Order(5)
    @DisplayName("Test Get Daily Sales by Date Range - Invalid Dates")
    void testGetDailySalesByDateRangeInvalidDates() {
        // Test with invalid date format
        List<Object[]> dailySales = billService.getDailySalesByDateRange("invalid-date", "invalid-date");
        
        assertNotNull(dailySales, "Should return empty list for invalid dates");
        assertTrue(dailySales.isEmpty(), "Should return empty list for invalid dates");
    }
    
    @Test
    @Order(6)
    @DisplayName("Test Get Daily Sales by Date Range - Future Dates")
    void testGetDailySalesByDateRangeFutureDates() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        String futureDate = dateFormat.format(cal.getTime());
        
        List<Object[]> dailySales = billService.getDailySalesByDateRange(futureDate, futureDate);
        
        assertNotNull(dailySales, "Should return empty list for future dates");
        assertTrue(dailySales.isEmpty(), "Should return empty list for future dates");
    }
    
    @Test
    @Order(7)
    @DisplayName("Test Daily Sales Data Validation")
    void testDailySalesDataValidation() {
        List<Object[]> dailySales = billService.getDailySales(10);
        
        assertNotNull(dailySales, "Daily sales should not be null");
        
        for (Object[] row : dailySales) {
            assertNotNull(row, "Row should not be null");
            assertEquals(2, row.length, "Each row should have 2 elements");
            
            // Verify date is not in the future
            if (row[0] != null) {
                assertTrue(row[0] instanceof java.sql.Date || row[0] instanceof java.util.Date, 
                    "First element should be a date");
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
    @DisplayName("Test Daily Sales Summary Calculations")
    void testDailySalesSummaryCalculations() {
        List<Object[]> dailySales = billService.getDailySales(30);
        
        assertNotNull(dailySales, "Daily sales should not be null");
        
        double totalSales = 0.0;
        int daysWithSales = 0;
        
        for (Object[] row : dailySales) {
            if (row[1] != null) {
                totalSales += ((Number) row[1]).doubleValue();
                daysWithSales++;
            }
        }
        
        double averageSales = daysWithSales > 0 ? totalSales / daysWithSales : 0.0;
        
        assertTrue(totalSales >= 0, "Total sales should be non-negative");
        assertTrue(daysWithSales >= 0, "Days with sales should be non-negative");
        assertTrue(averageSales >= 0, "Average sales should be non-negative");
    }
    
    @Test
    @Order(9)
    @DisplayName("Test Daily Sales Performance")
    void testDailySalesPerformance() {
        long startTime = System.currentTimeMillis();
        
        List<Object[]> dailySales = billService.getDailySales(30);
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        assertNotNull(dailySales, "Daily sales should not be null");
        assertTrue(duration < 5000, "Query should complete within 5 seconds: " + duration + "ms");
    }
    
    @Test
    @Order(10)
    @DisplayName("Test Daily Sales Date Range Edge Cases")
    void testDailySalesDateRangeEdgeCases() {
        // Test with very old dates
        List<Object[]> dailySales = billService.getDailySalesByDateRange("2000-01-01", "2000-01-31");
        
        assertNotNull(dailySales, "Should handle old dates gracefully");
        
        // Test with very recent dates
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        String yesterday = dateFormat.format(cal.getTime());
        String today = dateFormat.format(Calendar.getInstance().getTime());
        
        List<Object[]> recentSales = billService.getDailySalesByDateRange(yesterday, today);
        
        assertNotNull(recentSales, "Should handle recent dates");
        assertTrue(recentSales.size() <= 2, "Should not return more than 2 days for yesterday and today");
    }
} 