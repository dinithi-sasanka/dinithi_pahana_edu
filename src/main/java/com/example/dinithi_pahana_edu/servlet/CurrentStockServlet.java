package com.example.dinithi_pahana_edu.servlet;

import com.example.dinithi_pahana_edu.model.User;
import com.example.dinithi_pahana_edu.model.Item;
import com.example.dinithi_pahana_edu.service.ItemService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/currentStock")
public class CurrentStockServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        User user = (User) session.getAttribute("user");
        String role = user.getRole().toLowerCase();
        ItemService itemService = new ItemService();
        List<Item> items = itemService.getAllItemsWithCurrentStock();
        request.setAttribute("items", items);
        String jsp;
        switch (role) {
            case "admin":
                jsp = "curruntStock_admin.jsp";
                break;
            case "coadmin":
                jsp = "curruntStock_coadmin.jsp";
                break;
            case "staff":
                jsp = "curruntStock_staff.jsp";
                break;
            default:
                response.sendRedirect("error.jsp");
                return;
        }
        request.getRequestDispatcher(jsp).forward(request, response);
    }
} 