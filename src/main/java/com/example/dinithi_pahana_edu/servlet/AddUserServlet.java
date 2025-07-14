package com.example.dinithi_pahana_edu.servlet;

import com.example.dinithi_pahana_edu.model.User;
import com.example.dinithi_pahana_edu.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/AddUserServlet")
public class AddUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String useName = request.getParameter("use_name");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String email = request.getParameter("email");
        String telephone = request.getParameter("telephone");

        User user = new User();
        user.setUseName(useName);
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        user.setEmail(email);
        user.setTelephone(telephone);

        UserService userService = new UserService();
        boolean success = userService.addUser(user);

        if (success) {
            request.setAttribute("message", "User added successfully!");
        } else {
            request.setAttribute("message", "Failed to add user.");
        }
        request.getRequestDispatcher("useRoleManage_admin.jsp").forward(request, response);
    }
} 