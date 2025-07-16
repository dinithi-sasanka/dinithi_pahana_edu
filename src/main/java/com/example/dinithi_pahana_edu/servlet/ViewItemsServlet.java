package com.example.dinithi_pahana_edu.servlet;

import com.example.dinithi_pahana_edu.model.Item;
import com.example.dinithi_pahana_edu.service.ItemService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/ViewItemsServlet")
public class ViewItemsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        com.example.dinithi_pahana_edu.model.User user = (com.example.dinithi_pahana_edu.model.User) request.getSession().getAttribute("user");
        System.out.println("DEBUG: ViewItemsServlet called for user: " + (user != null ? user.getRole() : "none"));
        ItemService itemService = new ItemService();
        String name = request.getParameter("name");
        String category = request.getParameter("category");
        String minPriceStr = request.getParameter("minPrice");
        String maxPriceStr = request.getParameter("maxPrice");
        name = (name != null && !name.trim().isEmpty()) ? name.trim() : null;
        category = (category != null && !category.trim().isEmpty()) ? category.trim() : null;
        Double minPrice = (minPriceStr != null && !minPriceStr.isEmpty()) ? Double.parseDouble(minPriceStr) : null;
        Double maxPrice = (maxPriceStr != null && !maxPriceStr.isEmpty()) ? Double.parseDouble(maxPriceStr) : null;
        boolean hasFilter = (name != null) || (category != null) || minPrice != null || maxPrice != null;
        java.util.List<Item> itemList;
        if (hasFilter) {
            itemList = itemService.getFilteredItems(name, category, minPrice, maxPrice);
        } else {
            itemList = itemService.getAllItems();
        }
        System.out.println("DEBUG: itemList size = " + (itemList != null ? itemList.size() : "null"));
        request.setAttribute("itemList", itemList);
        String message = request.getParameter("message");
        if (message != null) request.setAttribute("message", message);
        if (user != null && "coadmin".equalsIgnoreCase(user.getRole())) {
            System.out.println("DEBUG: Forwarding to viewItems_coadmin.jsp");
            request.getRequestDispatcher("viewItems_coadmin.jsp").forward(request, response);
        } else if (user != null && "staff".equalsIgnoreCase(user.getRole())) {
            System.out.println("DEBUG: Forwarding to viewItems_staff.jsp");
            request.getRequestDispatcher("viewItems_staff.jsp").forward(request, response);
        } else {
            System.out.println("DEBUG: Forwarding to viewItems_admin.jsp");
            request.getRequestDispatcher("viewItems_admin.jsp").forward(request, response);
        }
    }
} 