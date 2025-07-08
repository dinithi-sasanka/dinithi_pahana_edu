package com.example.dinithi_pahana_edu.dao;

import com.example.dinithi_pahana_edu.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
                    rs.getString("role")
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
} 