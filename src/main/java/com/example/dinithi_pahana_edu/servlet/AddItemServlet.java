package com.example.dinithi_pahana_edu.servlet;

import com.example.dinithi_pahana_edu.model.Item;
import com.example.dinithi_pahana_edu.service.ItemService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/AddItemServlet")
public class AddItemServlet extends HttpServlet {
    private ItemService itemService = new ItemService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String category = request.getParameter("category");
        String description = request.getParameter("description");
        String priceStr = request.getParameter("price");
        String stockStr = request.getParameter("stock");

        String message;
        try {
            double price = Double.parseDouble(priceStr);
            int stock = Integer.parseInt(stockStr);
            Item item = new Item(name, category, description, price, stock);
            boolean success = itemService.addItem(item);
            if (success) {
                message = "Item added successfully!";
            } else {
                message = "Failed to add item. Please try again.";
            }
        } catch (NumberFormatException e) {
            message = "Invalid price or stock value.";
        }
        request.setAttribute("message", message);
        javax.servlet.http.HttpSession session = request.getSession(false);
        com.example.dinithi_pahana_edu.model.User user = (session != null) ? (com.example.dinithi_pahana_edu.model.User) session.getAttribute("user") : null;
        String forwardPage = "addItems_admin.jsp";
        if (user != null && "coadmin".equalsIgnoreCase(user.getRole())) {
            forwardPage = "addItems_coadmin.jsp";
        }
        request.getRequestDispatcher(forwardPage).forward(request, response);
    }
} 