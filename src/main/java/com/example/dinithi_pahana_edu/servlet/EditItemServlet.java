package com.example.dinithi_pahana_edu.servlet;

import com.example.dinithi_pahana_edu.model.Item;
import com.example.dinithi_pahana_edu.service.ItemService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/EditItemServlet")
public class EditItemServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null) {
            int id = Integer.parseInt(idStr);
            ItemService itemService = new ItemService();
            Item item = itemService.getItemById(id);
            request.setAttribute("item", item);
            request.getRequestDispatcher("editItems_admin.jsp").forward(request, response);
        } else {
            response.sendRedirect("ViewItemsServlet");
        }
    }
} 