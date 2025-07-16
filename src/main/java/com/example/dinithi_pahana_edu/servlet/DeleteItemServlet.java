package com.example.dinithi_pahana_edu.servlet;

import com.example.dinithi_pahana_edu.service.ItemService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/DeleteItemServlet")
public class DeleteItemServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        ItemService itemService = new ItemService();
        boolean success = itemService.deleteItem(id);
        if (success) {
            request.setAttribute("message", "Item deleted successfully!");
        } else {
            request.setAttribute("message", "Failed to delete item.");
        }
        javax.servlet.http.HttpSession session = request.getSession(false);
        com.example.dinithi_pahana_edu.model.User user = (session != null) ? (com.example.dinithi_pahana_edu.model.User) session.getAttribute("user") : null;
        String forwardPage = "ViewItemsServlet";
        if (user != null && "coadmin".equalsIgnoreCase(user.getRole())) {
            forwardPage = "viewItems_coadmin.jsp";
        }
        request.getRequestDispatcher(forwardPage).forward(request, response);
    }
} 