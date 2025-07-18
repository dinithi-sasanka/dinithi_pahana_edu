package com.example.dinithi_pahana_edu.dao;

import com.example.dinithi_pahana_edu.model.Bill;
import com.example.dinithi_pahana_edu.model.BillItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillDAO {
    public int addBill(Bill bill) {
        String sql = "INSERT INTO bills (customer_id, bill_number, bill_date, bill_date_time, total_amount, paid_amount, balance) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, bill.getCustomerId());
            pstmt.setString(2, bill.getBillNumber());
            pstmt.setTimestamp(3, new Timestamp(bill.getBillDate().getTime()));
            pstmt.setString(4, bill.getBillDateTime());
            pstmt.setDouble(5, bill.getTotalAmount());
            pstmt.setDouble(6, bill.getPaidAmount());
            pstmt.setDouble(7, bill.getBalance());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) return -1;
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean addBillItems(List<BillItem> items) {
        String sql = "INSERT INTO bill_items (bill_id, item_id, quantity, price) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (BillItem item : items) {
                pstmt.setInt(1, item.getBillId());
                pstmt.setInt(2, item.getItemId());
                pstmt.setInt(3, item.getQuantity());
                pstmt.setDouble(4, item.getPrice());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get the maximum bill number as integer (assuming bill_number is numeric or can be parsed as int)
    public int getMaxBillNumber() {
        String sql = "SELECT MAX(CAST(bill_number AS UNSIGNED)) AS max_bill_number FROM bills";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("max_bill_number");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // If no bills, start from 0
    }
} 