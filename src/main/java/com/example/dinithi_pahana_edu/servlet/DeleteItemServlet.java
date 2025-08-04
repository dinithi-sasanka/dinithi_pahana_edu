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
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            ItemService itemService = new ItemService();
            boolean success = itemService.deleteItem(id);
            
            if (success) {
                request.setAttribute("message", "Item deleted successfully!");
            } else {
                request.setAttribute("message", "Failed to delete item.");
            }
            
            // Get user from session
            javax.servlet.http.HttpSession session = request.getSession(false);
            com.example.dinithi_pahana_edu.model.User user = (session != null) ? (com.example.dinithi_pahana_edu.model.User) session.getAttribute("user") : null;
            
            // Redirect based on user role
            if (user != null) {
                String role = user.getRole().toLowerCase();
                switch (role) {
                    case "admin":
                        response.sendRedirect("ViewItemsServlet?message=" + (success ? "Item deleted successfully!" : "Failed to delete item."));
                        break;
                    case "coadmin":
                        response.sendRedirect("ViewItemsServlet?message=" + (success ? "Item deleted successfully!" : "Failed to delete item."));
                        break;
                    case "staff":
                        response.sendRedirect("ViewItemsServlet?message=" + (success ? "Item deleted successfully!" : "Failed to delete item."));
                        break;
                    default:
                        response.sendRedirect("ViewItemsServlet?message=" + (success ? "Item deleted successfully!" : "Failed to delete item."));
                        break;
                }
            } else {
                response.sendRedirect("ViewItemsServlet?message=" + (success ? "Item deleted successfully!" : "Failed to delete item."));
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("ViewItemsServlet?message=Invalid item ID.");
        } catch (Exception e) {
            response.sendRedirect("ViewItemsServlet?message=Error occurred while deleting item.");
        }
    }
} 