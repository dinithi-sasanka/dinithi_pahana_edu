package com.example.dinithi_pahana_edu.service;

import com.example.dinithi_pahana_edu.dao.UserDAO;
import com.example.dinithi_pahana_edu.model.User;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    public User authenticate(String username, String password) {
        return userDAO.getUserByUsernameAndPassword(username, password);
    }

    public boolean addUser(User user) {
        return userDAO.addUser(user);
    }
} 