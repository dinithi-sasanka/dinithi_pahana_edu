package com.example.dinithi_pahana_edu.servlet;

import com.example.dinithi_pahana_edu.service.BillService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteBill")
public class DeleteBillServlet extends HttpServlet {
    private BillService billService = new BillService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String billIdStr = request.getParameter("billId");
        System.out.println("[DEBUG] DeleteBillServlet called with billId=" + billIdStr);
        response.setContentType("text/plain");
        if (billIdStr == null) {
            System.out.println("[DEBUG] billId is null");
            response.getWriter().write("error");
            return;
        }
        try {
            int billId = Integer.parseInt(billIdStr);
            // 1. Restock all items in the bill before deleting
            java.util.List<com.example.dinithi_pahana_edu.model.BillItem> billItems = new com.example.dinithi_pahana_edu.service.BillService().getBillItemsByBillId(billId);
            com.example.dinithi_pahana_edu.service.ItemService itemService = new com.example.dinithi_pahana_edu.service.ItemService();
            for (com.example.dinithi_pahana_edu.model.BillItem item : billItems) {
                itemService.incrementStock(item.getItemId(), item.getQuantity());
            }
            // 2. Delete the bill and its items
            boolean deleted = new com.example.dinithi_pahana_edu.service.BillService().deleteBillById(billId);
            System.out.println("[DEBUG] deleteBillById returned: " + deleted);
            if (deleted) {
                response.getWriter().write("success");
            } else {
                response.getWriter().write("error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("error");
        }
    }
} 