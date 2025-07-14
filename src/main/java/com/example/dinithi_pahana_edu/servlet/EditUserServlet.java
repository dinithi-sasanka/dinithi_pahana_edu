package com.example.dinithi_pahana_edu.servlet;

import com.example.dinithi_pahana_edu.model.User;
import com.example.dinithi_pahana_edu.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/EditUserServlet")
public class EditUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null) {
            int id = Integer.parseInt(idStr);
            UserService userService = new UserService();
            User user = userService.getUserById(id);
            request.setAttribute("editUser", user);
            request.getRequestDispatcher("editUsers_admin.jsp").forward(request, response);
        } else {
            response.sendRedirect("ViewUsersServlet");
        }
    }
} 