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
        request.getRequestDispatcher("ViewItemsServlet").forward(request, response);
    }
} 