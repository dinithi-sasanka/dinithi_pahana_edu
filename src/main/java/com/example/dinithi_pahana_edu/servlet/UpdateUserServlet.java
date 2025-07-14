package com.example.dinithi_pahana_edu.servlet;

import com.example.dinithi_pahana_edu.model.User;
import com.example.dinithi_pahana_edu.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/UpdateUserServlet")
public class UpdateUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String useName = request.getParameter("use_name");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String email = request.getParameter("email");
        String telephone = request.getParameter("telephone");

        User user = new User(id, username, password, role, useName, email, telephone);
        UserService userService = new UserService();
        userService.updateUser(user);
        response.sendRedirect("ViewUsersServlet");
    }
} 