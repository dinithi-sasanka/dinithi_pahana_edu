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
        ItemService itemService = new ItemService();
        String name = request.getParameter("name");
        String category = request.getParameter("category");
        String minPriceStr = request.getParameter("minPrice");
        String maxPriceStr = request.getParameter("maxPrice");
        Double minPrice = (minPriceStr != null && !minPriceStr.isEmpty()) ? Double.parseDouble(minPriceStr) : null;
        Double maxPrice = (maxPriceStr != null && !maxPriceStr.isEmpty()) ? Double.parseDouble(maxPriceStr) : null;
        boolean hasFilter = (name != null && !name.isEmpty()) || (category != null && !category.isEmpty()) || minPrice != null || maxPrice != null;
        java.util.List<Item> itemList;
        if (hasFilter) {
            itemList = itemService.getFilteredItems(name, category, minPrice, maxPrice);
        } else {
            itemList = itemService.getAllItems();
        }
        request.setAttribute("itemList", itemList);
        request.getRequestDispatcher("viewItems_admin.jsp").forward(request, response);
    }
} 