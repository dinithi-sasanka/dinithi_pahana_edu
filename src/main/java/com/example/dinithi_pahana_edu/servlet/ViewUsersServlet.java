package com.example.dinithi_pahana_edu.servlet;

import com.example.dinithi_pahana_edu.model.User;
import com.example.dinithi_pahana_edu.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/ViewUsersServlet")
public class ViewUsersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = new UserService();
        List<User> userList;
        
        // Get search parameters
        String searchTerm = request.getParameter("searchTerm");
        String searchType = request.getParameter("searchType");
        
        // Perform search based on parameters
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            switch (searchType) {
                case "name":
                    userList = userService.searchUsersByName(searchTerm);
                    break;
                case "username":
                    userList = userService.searchUsersByUsername(searchTerm);
                    break;
                case "role":
                    userList = userService.searchUsersByRole(searchTerm);
                    break;
                case "password":
                    userList = userService.searchUsersByPassword(searchTerm);
                    break;
                case "email":
                    userList = userService.searchUsersByEmail(searchTerm);
                    break;
                case "telephone":
                    userList = userService.searchUsersByTelephone(searchTerm);
                    break;
                default:
                    userList = userService.searchUsers(searchTerm);
                    break;
            }
        } else {
            userList = userService.getAllUsers();
        }
        
        request.setAttribute("userList", userList);
        request.setAttribute("searchTerm", searchTerm);
        request.setAttribute("searchType", searchType);
        request.getRequestDispatcher("viewUsers_admin.jsp").forward(request, response);
    }
} 