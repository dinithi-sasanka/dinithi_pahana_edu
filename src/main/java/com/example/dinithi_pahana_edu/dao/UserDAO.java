package com.example.dinithi_pahana_edu.dao;

import com.example.dinithi_pahana_edu.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public User getUserByUsernameAndPassword(String username, String password) {
        User user = null;
        try {
            System.out.println("[DEBUG] Checking username: '" + username + "', password: '" + password + "'");
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role"),
                    rs.getString("use_name"),
                    rs.getString("email"),
                    rs.getString("telephone")
                );
                System.out.println("[DEBUG] User found: id=" + user.getId() + ", username=" + user.getUsername());
            } else {
                System.out.println("[DEBUG] No user found for given credentials.");
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean addUser(User user) {
        if (user == null) {
            return false;
        }
        String sql = "INSERT INTO users (use_name, username, password, role, email, telephone) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUseName());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole());
            stmt.setString(5, user.getEmail());
            stmt.setString(6, user.getTelephone());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public java.util.List<User> getAllUsers() {
        java.util.List<User> users = new java.util.ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                User user = new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role"),
                    rs.getString("use_name"),
                    rs.getString("email"),
                    rs.getString("telephone")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public User getUserById(int id) {
        User user = null;
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role"),
                    rs.getString("use_name"),
                    rs.getString("email"),
                    rs.getString("telephone")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    public boolean updateUser(User user) {
        if (user == null) {
            return false;
        }
        String sql = "UPDATE users SET use_name=?, username=?, password=?, role=?, email=?, telephone=? WHERE id=?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUseName());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole());
            stmt.setString(5, user.getEmail());
            stmt.setString(6, user.getTelephone());
            stmt.setInt(7, user.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Search methods
    public List<User> searchUsers(String searchTerm) {
        List<User> users = new ArrayList<>();
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getAllUsers();
        }
        
        // Check if search term matches exact role names
        String trimmedTerm = searchTerm.trim().toLowerCase();
        if (trimmedTerm.equals("admin") || trimmedTerm.equals("coadmin") || trimmedTerm.equals("staff")) {
            // Use exact role matching for role-specific searches
            String sql = "SELECT * FROM users WHERE role = ?";
            try (Connection conn = DBConnection.getInstance().getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                
                stmt.setString(1, trimmedTerm);
                
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    User user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("use_name"),
                        rs.getString("email"),
                        rs.getString("telephone")
                    );
                    users.add(user);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // Use partial matching for other searches
            String sql = "SELECT * FROM users WHERE " +
                        "id LIKE ? OR " +
                        "use_name LIKE ? OR " +
                        "username LIKE ? OR " +
                        "password LIKE ? OR " +
                        "email LIKE ? OR " +
                        "telephone LIKE ?";
            
            try (Connection conn = DBConnection.getInstance().getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                
                String searchPattern = "%" + searchTerm.trim() + "%";
                stmt.setString(1, searchPattern);
                stmt.setString(2, searchPattern);
                stmt.setString(3, searchPattern);
                stmt.setString(4, searchPattern);
                stmt.setString(5, searchPattern);
                stmt.setString(6, searchPattern);
                
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    User user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("use_name"),
                        rs.getString("email"),
                        rs.getString("telephone")
                    );
                    users.add(user);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    public List<User> searchUsersByRole(String role) {
        List<User> users = new ArrayList<>();
        if (role == null || role.trim().isEmpty()) {
            return getAllUsers();
        }
        
        // For role search, use exact matching to avoid partial matches
        String sql = "SELECT * FROM users WHERE role = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, role.trim());
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role"),
                    rs.getString("use_name"),
                    rs.getString("email"),
                    rs.getString("telephone")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<User> searchUsersByName(String name) {
        List<User> users = new ArrayList<>();
        if (name == null || name.trim().isEmpty()) {
            return getAllUsers();
        }
        
        String sql = "SELECT * FROM users WHERE use_name LIKE ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String searchPattern = "%" + name.trim() + "%";
            stmt.setString(1, searchPattern);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role"),
                    rs.getString("use_name"),
                    rs.getString("email"),
                    rs.getString("telephone")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<User> searchUsersByUsername(String username) {
        List<User> users = new ArrayList<>();
        if (username == null || username.trim().isEmpty()) {
            return getAllUsers();
        }
        
        String sql = "SELECT * FROM users WHERE username LIKE ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String searchPattern = "%" + username.trim() + "%";
            stmt.setString(1, searchPattern);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role"),
                    rs.getString("use_name"),
                    rs.getString("email"),
                    rs.getString("telephone")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<User> searchUsersByEmail(String email) {
        List<User> users = new ArrayList<>();
        if (email == null || email.trim().isEmpty()) {
            return getAllUsers();
        }
        
        String sql = "SELECT * FROM users WHERE email LIKE ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String searchPattern = "%" + email.trim() + "%";
            stmt.setString(1, searchPattern);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role"),
                    rs.getString("use_name"),
                    rs.getString("email"),
                    rs.getString("telephone")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<User> searchUsersByTelephone(String telephone) {
        List<User> users = new ArrayList<>();
        if (telephone == null || telephone.trim().isEmpty()) {
            return getAllUsers();
        }
        
        String sql = "SELECT * FROM users WHERE telephone LIKE ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String searchPattern = "%" + telephone.trim() + "%";
            stmt.setString(1, searchPattern);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role"),
                    rs.getString("use_name"),
                    rs.getString("email"),
                    rs.getString("telephone")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<User> searchUsersByPassword(String password) {
        List<User> users = new ArrayList<>();
        if (password == null || password.trim().isEmpty()) {
            return getAllUsers();
        }
        
        String sql = "SELECT * FROM users WHERE password LIKE ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String searchPattern = "%" + password.trim() + "%";
            stmt.setString(1, searchPattern);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role"),
                    rs.getString("use_name"),
                    rs.getString("email"),
                    rs.getString("telephone")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
} 