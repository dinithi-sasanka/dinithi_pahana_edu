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
            javax.servlet.http.HttpSession session = request.getSession(false);
            com.example.dinithi_pahana_edu.model.User user = (session != null) ? (com.example.dinithi_pahana_edu.model.User) session.getAttribute("user") : null;
            String forwardPage = "editItems_admin.jsp";
            if (user != null && "coadmin".equalsIgnoreCase(user.getRole())) {
                forwardPage = "editItems_coadmin.jsp";
            }
            request.getRequestDispatcher(forwardPage).forward(request, response);
        } else {
            response.sendRedirect("ViewItemsServlet");
        }
    }
} 