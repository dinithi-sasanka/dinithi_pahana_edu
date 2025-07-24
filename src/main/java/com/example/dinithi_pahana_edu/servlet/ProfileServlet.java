package com.example.dinithi_pahana_edu.servlet;

import com.example.dinithi_pahana_edu.model.User;
import com.example.dinithi_pahana_edu.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        User sessionUser = (User) session.getAttribute("user");
        User user = userService.getUserById(sessionUser.getId());
        request.setAttribute("user", user);
        String role = user.getRole();
        if ("admin".equalsIgnoreCase(role)) {
            request.getRequestDispatcher("profile_admin.jsp").forward(request, response);
        } else if ("coadmin".equalsIgnoreCase(role)) {
            request.getRequestDispatcher("profile_coadmin.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("profile_staff.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        User sessionUser = (User) session.getAttribute("user");
        User user = userService.getUserById(sessionUser.getId());
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String telephone = request.getParameter("telephone");
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setTelephone(telephone);
        userService.updateUser(user);
        session.setAttribute("user", user);
        response.sendRedirect("profile?success=1");
    }
} 