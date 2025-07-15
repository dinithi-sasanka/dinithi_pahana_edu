package com.example.dinithi_pahana_edu.servlet;

import com.example.dinithi_pahana_edu.model.Item;
import com.example.dinithi_pahana_edu.service.ItemService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/UpdateItemServlet")
public class UpdateItemServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String category = request.getParameter("category");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int stock = Integer.parseInt(request.getParameter("stock"));

        Item item = new Item(id, name, category, description, price, stock);
        ItemService itemService = new ItemService();
        boolean success = itemService.updateItem(item);
        String msg = success ? "Item updated successfully!" : "Failed to update item.";
        response.sendRedirect("ViewItemsServlet?message=" + java.net.URLEncoder.encode(msg, "UTF-8"));
    }
} 