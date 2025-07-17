package com.example.dinithi_pahana_edu.servlet;

import com.example.dinithi_pahana_edu.model.Item;
import com.example.dinithi_pahana_edu.service.ItemService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/itemsForBill")
public class ItemsForBillServlet extends HttpServlet {
    private ItemService itemService = new ItemService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        List<Item> items = itemService.getAllItems();
        PrintWriter out = response.getWriter();
        out.print("[");
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            out.print("{\"id\":" + item.getId() + ",\"name\":\"" + item.getName() + "\",\"price\":" + item.getPrice() + "}");
            if (i < items.size() - 1) out.print(",");
        }
        out.print("]");
        out.flush();
    }
} 