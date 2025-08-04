package com.example.dinithi_pahana_edu.dao;

import com.example.dinithi_pahana_edu.model.Item;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    public boolean addItem(Item item) {
        if (item == null) {
            return false;
        }
        String sql = "INSERT INTO items (name, category, description, price, stock) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, item.getName());
            stmt.setString(2, item.getCategory());
            stmt.setString(3, item.getDescription());
            stmt.setDouble(4, item.getPrice());
            stmt.setInt(5, item.getStock());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items ORDER BY id DESC";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Item item = new Item();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setCategory(rs.getString("category"));
                item.setDescription(rs.getString("description"));
                item.setPrice(rs.getDouble("price"));
                item.setStock(rs.getInt("stock"));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<Item> getFilteredItems(String name, String category, Double minPrice, Double maxPrice) {
        List<Item> items = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM items WHERE 1=1");
        if (name != null && !name.isEmpty()) sql.append(" AND LOWER(name) LIKE ?");
        if (category != null && !category.isEmpty()) sql.append(" AND LOWER(category) LIKE ?");
        if (minPrice != null) sql.append(" AND price >= ?");
        if (maxPrice != null) sql.append(" AND price <= ?");
        sql.append(" ORDER BY id DESC");
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            int idx = 1;
            if (name != null && !name.isEmpty()) stmt.setString(idx++, "%" + name.toLowerCase() + "%");
            if (category != null && !category.isEmpty()) stmt.setString(idx++, "%" + category.toLowerCase() + "%");
            if (minPrice != null) stmt.setDouble(idx++, minPrice);
            if (maxPrice != null) stmt.setDouble(idx++, maxPrice);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setCategory(rs.getString("category"));
                item.setDescription(rs.getString("description"));
                item.setPrice(rs.getDouble("price"));
                item.setStock(rs.getInt("stock"));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public Item getItemById(int id) {
        String sql = "SELECT * FROM items WHERE id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Item item = new Item();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setCategory(rs.getString("category"));
                item.setDescription(rs.getString("description"));
                item.setPrice(rs.getDouble("price"));
                item.setStock(rs.getInt("stock"));
                return item;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateItem(Item item) {
        if (item == null) {
            return false;
        }
        String sql = "UPDATE items SET name=?, category=?, description=?, price=?, stock=? WHERE id=?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, item.getName());
            stmt.setString(2, item.getCategory());
            stmt.setString(3, item.getDescription());
            stmt.setDouble(4, item.getPrice());
            stmt.setInt(5, item.getStock());
            stmt.setInt(6, item.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteItem(int id) {
        String sql = "DELETE FROM items WHERE id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Item> getLowStockItems(int threshold) {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT i.*, s.current_stock FROM items i JOIN stock s ON i.id = s.item_id WHERE s.current_stock < ? ORDER BY s.current_stock ASC";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, threshold);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setCategory(rs.getString("category"));
                item.setDescription(rs.getString("description"));
                item.setPrice(rs.getDouble("price"));
                item.setStock(rs.getInt("current_stock")); // Use stock from stock table
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public boolean decrementStock(int itemId, int quantity) {
        String sql = "UPDATE stock SET current_stock = current_stock - ? WHERE item_id = ? AND current_stock >= ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, itemId);
            stmt.setInt(3, quantity);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean incrementStock(int itemId, int quantity) {
        String sql = "UPDATE stock SET current_stock = current_stock + ? WHERE item_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, itemId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Item> getAllItemsWithCurrentStock() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT i.*, s.current_stock FROM items i JOIN stock s ON i.id = s.item_id ORDER BY i.id DESC";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Item item = new Item();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setCategory(rs.getString("category"));
                item.setDescription(rs.getString("description"));
                item.setPrice(rs.getDouble("price"));
                item.setStock(rs.getInt("stock")); // original stock
                item.setCurrentStock(rs.getInt("current_stock")); // current stock from stock table
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
} 