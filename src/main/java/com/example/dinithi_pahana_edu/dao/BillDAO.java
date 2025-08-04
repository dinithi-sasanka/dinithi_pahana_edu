package com.example.dinithi_pahana_edu.dao;

import com.example.dinithi_pahana_edu.model.Bill;
import com.example.dinithi_pahana_edu.model.BillItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillDAO {
    public int addBill(Bill bill) {
        if (bill == null) {
            return -1;
        }
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
    
    // Get all bills for a specific customer
    public List<Bill> getBillsByCustomerId(int customerId) {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM bills WHERE customer_id = ? ORDER BY bill_date DESC";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Bill bill = new Bill();
                bill.setId(rs.getInt("id"));
                bill.setCustomerId(rs.getInt("customer_id"));
                bill.setBillNumber(rs.getString("bill_number"));
                bill.setBillDate(rs.getTimestamp("bill_date"));
                bill.setBillDateTime(rs.getString("bill_date_time"));
                bill.setTotalAmount(rs.getDouble("total_amount"));
                bill.setPaidAmount(rs.getDouble("paid_amount"));
                bill.setBalance(rs.getDouble("balance"));
                bills.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }
    
    // Get bill items for a specific bill
    public List<BillItem> getBillItemsByBillId(int billId) {
        List<BillItem> items = new ArrayList<>();
        String sql = "SELECT bi.*, i.name as item_name FROM bill_items bi " +
                    "JOIN items i ON bi.item_id = i.id " +
                    "WHERE bi.bill_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, billId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                BillItem item = new BillItem();
                item.setId(rs.getInt("id"));
                item.setBillId(rs.getInt("bill_id"));
                item.setItemId(rs.getInt("item_id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPrice(rs.getDouble("price"));
                item.setItemName(rs.getString("item_name"));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<Bill> getAllBills() {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM bills ORDER BY bill_date DESC";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Bill bill = new Bill();
                bill.setId(rs.getInt("id"));
                bill.setCustomerId(rs.getInt("customer_id"));
                bill.setBillNumber(rs.getString("bill_number"));
                bill.setBillDate(rs.getTimestamp("bill_date"));
                bill.setBillDateTime(rs.getString("bill_date_time"));
                bill.setTotalAmount(rs.getDouble("total_amount"));
                bill.setPaidAmount(rs.getDouble("paid_amount"));
                bill.setBalance(rs.getDouble("balance"));
                bills.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }

    public List<Bill> searchBills(String searchTerm, String fromDate, String toDate) {
        List<Bill> bills = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT b.* FROM bills b LEFT JOIN customers c ON b.customer_id = c.id WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            sql.append(" AND (");
            sql.append("b.bill_number LIKE ? OR c.name LIKE ? OR c.account_number LIKE ?");
            sql.append(")");
            String likeTerm = "%" + searchTerm.trim() + "%";
            params.add(likeTerm);
            params.add(likeTerm);
            params.add(likeTerm);
        }
        if (fromDate != null && !fromDate.isEmpty()) {
            sql.append(" AND DATE(b.bill_date) >= ?");
            params.add(fromDate);
        }
        if (toDate != null && !toDate.isEmpty()) {
            sql.append(" AND DATE(b.bill_date) <= ?");
            params.add(toDate);
        }
        sql.append(" ORDER BY b.bill_date DESC");
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Bill bill = new Bill();
                bill.setId(rs.getInt("id"));
                bill.setCustomerId(rs.getInt("customer_id"));
                bill.setBillNumber(rs.getString("bill_number"));
                bill.setBillDate(rs.getTimestamp("bill_date"));
                bill.setBillDateTime(rs.getString("bill_date_time"));
                bill.setTotalAmount(rs.getDouble("total_amount"));
                bill.setPaidAmount(rs.getDouble("paid_amount"));
                bill.setBalance(rs.getDouble("balance"));
                bills.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }

    public boolean updateBill(Bill bill) {
        String sql = "UPDATE bills SET customer_id=?, bill_number=?, bill_date=?, bill_date_time=?, total_amount=?, paid_amount=?, balance=? WHERE id=?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bill.getCustomerId());
            pstmt.setString(2, bill.getBillNumber());
            pstmt.setTimestamp(3, new Timestamp(bill.getBillDate().getTime()));
            pstmt.setString(4, bill.getBillDateTime());
            pstmt.setDouble(5, bill.getTotalAmount());
            pstmt.setDouble(6, bill.getPaidAmount());
            pstmt.setDouble(7, bill.getBalance());
            pstmt.setInt(8, bill.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBillItemsByBillId(int billId) {
        String sql = "DELETE FROM bill_items WHERE bill_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, billId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBillById(int billId) {
        try (Connection conn = DBConnection.getInstance().getConnection()) {
            try (PreparedStatement pstmt1 = conn.prepareStatement("DELETE FROM bill_items WHERE bill_id = ?")) {
                pstmt1.setInt(1, billId);
                pstmt1.executeUpdate();
            }
            try (PreparedStatement pstmt2 = conn.prepareStatement("DELETE FROM bills WHERE id = ?")) {
                pstmt2.setInt(1, billId);
                return pstmt2.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Object[]> getTopCustomers(int limit) {
        List<Object[]> result = new ArrayList<>();
        String sql = "SELECT c.id, c.name, c.account_number, SUM(b.total_amount) AS total_spent " +
                "FROM customers c JOIN bills b ON c.id = b.customer_id " +
                "GROUP BY c.id, c.name, c.account_number " +
                "ORDER BY total_spent DESC LIMIT ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, limit);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(new Object[]{rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4)});
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return result;
    }

    public List<Object[]> getMostSoldItems(int limit) {
        List<Object[]> result = new ArrayList<>();
        String sql = "SELECT i.id, i.name, SUM(bi.quantity) AS total_sold " +
                "FROM items i JOIN bill_items bi ON i.id = bi.item_id " +
                "GROUP BY i.id, i.name ORDER BY total_sold DESC LIMIT ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, limit);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(new Object[]{rs.getInt(1), rs.getString(2), rs.getInt(3)});
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return result;
    }

    public List<Object[]> getDailySales(int days) {
        List<Object[]> result = new ArrayList<>();
        String sql = "SELECT DATE(bill_date) AS day, SUM(total_amount) AS total_sales " +
                "FROM bills GROUP BY day ORDER BY day DESC LIMIT ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, days);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(new Object[]{rs.getDate(1), rs.getDouble(2)});
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return result;
    }

    public List<Object[]> getMonthlySales(int months) {
        List<Object[]> result = new ArrayList<>();
        String sql = "SELECT DATE_FORMAT(bill_date, '%Y-%m') AS month, SUM(total_amount) AS total_sales " +
                "FROM bills GROUP BY month ORDER BY month DESC LIMIT ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, months);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(new Object[]{rs.getString(1), rs.getDouble(2)});
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return result;
    }
} 