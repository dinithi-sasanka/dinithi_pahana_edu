package com.example.dinithi_pahana_edu.service;

import com.example.dinithi_pahana_edu.dao.UserDAO;
import com.example.dinithi_pahana_edu.model.User;

import java.util.List;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    public User authenticate(String username, String password) {
        return userDAO.getUserByUsernameAndPassword(username, password);
    }

    public boolean addUser(User user) {
        return userDAO.addUser(user);
    }

    public java.util.List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }
    public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }

    public boolean deleteUser(int id) {
        return userDAO.deleteUser(id);
    }

    // Search methods
    public List<User> searchUsers(String searchTerm) {
        return userDAO.searchUsers(searchTerm);
    }

    public List<User> searchUsersByRole(String role) {
        return userDAO.searchUsersByRole(role);
    }

    public List<User> searchUsersByName(String name) {
        return userDAO.searchUsersByName(name);
    }

    public List<User> searchUsersByUsername(String username) {
        return userDAO.searchUsersByUsername(username);
    }

    public List<User> searchUsersByEmail(String email) {
        return userDAO.searchUsersByEmail(email);
    }

    public List<User> searchUsersByTelephone(String telephone) {
        return userDAO.searchUsersByTelephone(telephone);
    }

    public List<User> searchUsersByPassword(String password) {
        return userDAO.searchUsersByPassword(password);
    }
} 