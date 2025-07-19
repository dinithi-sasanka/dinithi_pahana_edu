package com.example.dinithi_pahana_edu.servlet;

import com.example.dinithi_pahana_edu.model.Bill;
import com.example.dinithi_pahana_edu.model.BillItem;
import com.example.dinithi_pahana_edu.model.Item;
import com.example.dinithi_pahana_edu.service.BillService;
import com.example.dinithi_pahana_edu.service.ItemService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/calculateBill")
public class CalculateBillServlet extends HttpServlet {
    private BillService billService = new BillService();
    private ItemService itemService = new ItemService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Item> items = itemService.getAllItems();
        request.setAttribute("items", items);
        int nextBillNumber = billService.getNextBillNumber();
        request.setAttribute("nextBillNumber", nextBillNumber);
        request.getRequestDispatcher("calculateBills_admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String billNumber = request.getParameter("billNumber");
        String customerId = request.getParameter("customerId");
        String billDateTime = request.getParameter("billDateTime");
        String paidAmountStr = request.getParameter("paidAmount");
        System.out.println("[DEBUG] billNumber: '" + billNumber + "'");
        System.out.println("[DEBUG] customerId: '" + customerId + "'");
        System.out.println("[DEBUG] billDateTime: '" + billDateTime + "'");
        System.out.println("[DEBUG] paidAmount: '" + paidAmountStr + "'");
        String[] itemIds = request.getParameterValues("itemId[]");
        String[] quantities = request.getParameterValues("quantity[]");
        String[] unitPrices = request.getParameterValues("unitPrice[]");
        System.out.println("[DEBUG] itemIds: " + java.util.Arrays.toString(itemIds));
        System.out.println("[DEBUG] quantities: " + java.util.Arrays.toString(quantities));
        System.out.println("[DEBUG] unitPrices: " + java.util.Arrays.toString(unitPrices));
        double totalAmount = 0.0;
        double paidAmount = 0.0;
        
        // Parse paid amount
        if (paidAmountStr != null && !paidAmountStr.isEmpty()) {
            paidAmount = Double.parseDouble(paidAmountStr);
        }
        
        List<BillItem> billItems = new ArrayList<>();
        for (int i = 0; i < itemIds.length; i++) {
            if (itemIds[i] == null || itemIds[i].isEmpty() ||
                quantities[i] == null || quantities[i].isEmpty() ||
                unitPrices[i] == null || unitPrices[i].isEmpty()) {
                continue; // skip this row
            }
            int itemId = Integer.parseInt(itemIds[i]);
            int qty = Integer.parseInt(quantities[i]);
            double price = Double.parseDouble(unitPrices[i]);
            totalAmount += price * qty;
            BillItem billItem = new BillItem();
            billItem.setItemId(itemId);
            billItem.setQuantity(qty);
            billItem.setPrice(price);
            billItems.add(billItem);
        }
        
        double balance = totalAmount - paidAmount;
        int customerIdInt = Integer.parseInt(customerId); // Parse customerId to int
        Bill bill = new Bill(customerIdInt, billNumber, new Date(), billDateTime, totalAmount, paidAmount, balance);
        int billId = billService.addBill(bill);
        if (billId > 0) {
            for (BillItem item : billItems) {
                item.setBillId(billId);
            }
            billService.addBillItems(billItems);
            request.setAttribute("message", "Bill saved successfully! Bill Number: " + billNumber);
        } else {
            request.setAttribute("message", "Failed to save bill.");
        }
        // Reload items for the dropdown after saving
        List<Item> items = itemService.getAllItems();
        request.setAttribute("items", items);
        request.getRequestDispatcher("calculateBills_admin.jsp").forward(request, response);
    }
} 