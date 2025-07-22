package com.example.dinithi_pahana_edu.servlet;

import com.example.dinithi_pahana_edu.model.Bill;
import com.example.dinithi_pahana_edu.model.BillItem;
import com.example.dinithi_pahana_edu.model.Customer;
import com.example.dinithi_pahana_edu.model.Item;
import com.example.dinithi_pahana_edu.model.User;
import com.example.dinithi_pahana_edu.service.BillService;
import com.example.dinithi_pahana_edu.service.CustomerService;
import com.example.dinithi_pahana_edu.service.ItemService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/updateBill")
public class UpdateBillServlet extends HttpServlet {
    private BillService billService = new BillService();
    private CustomerService customerService = new CustomerService();
    private ItemService itemService = new ItemService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String billIdStr = request.getParameter("billId");
        if (billIdStr == null) {
            response.sendRedirect("error.jsp");
            return;
        }
        int billId = Integer.parseInt(billIdStr);
        Bill bill = null;
        for (Bill b : billService.getAllBills()) {
            if (b.getId() == billId) {
                bill = b;
                break;
            }
        }
        if (bill == null) {
            response.sendRedirect("error.jsp");
            return;
        }
        List<BillItem> billItems = billService.getBillItemsByBillId(billId);
        Customer customer = customerService.getCustomerById(bill.getCustomerId());
        List<Item> items = itemService.getAllItems();
        request.setAttribute("bill", bill);
        request.setAttribute("billItems", billItems);
        request.setAttribute("customer", customer);
        request.setAttribute("items", items);
        HttpSession session = request.getSession(false);
        String jsp = "updateBills_admin.jsp";
        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                if ("coadmin".equalsIgnoreCase(user.getRole())) {
                    jsp = "updateBills_coadmin.jsp";
                } else if ("staff".equalsIgnoreCase(user.getRole())) {
                    jsp = "updateBills_staff.jsp";
                }
            }
        }
        request.getRequestDispatcher(jsp).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String billIdStr = request.getParameter("billId");
        String billNumber = request.getParameter("billNumber");
        String customerId = request.getParameter("customerId");
        String billDateTime = request.getParameter("billDateTime");
        String paidAmountStr = request.getParameter("paidAmount");
        String[] itemIds = request.getParameterValues("itemId[]");
        String[] quantities = request.getParameterValues("quantity[]");
        String[] unitPrices = request.getParameterValues("unitPrice[]");
        double totalAmount = 0.0;
        double paidAmount = 0.0;
        if (paidAmountStr != null && !paidAmountStr.isEmpty()) {
            paidAmount = Double.parseDouble(paidAmountStr);
        }
        int billId = Integer.parseInt(billIdStr);
        // 1. Restock all old items before deleting them
        java.util.List<com.example.dinithi_pahana_edu.model.BillItem> oldBillItems = billService.getBillItemsByBillId(billId);
        for (com.example.dinithi_pahana_edu.model.BillItem oldItem : oldBillItems) {
            itemService.incrementStock(oldItem.getItemId(), oldItem.getQuantity());
        }
        // 2. Delete old bill items
        billService.deleteBillItemsByBillId(billId);
        // 3. Prepare new bill items and decrement stock for each
        java.util.List<com.example.dinithi_pahana_edu.model.BillItem> billItems = new java.util.ArrayList<>();
        for (int i = 0; i < itemIds.length; i++) {
            if (itemIds[i] == null || itemIds[i].isEmpty() ||
                quantities[i] == null || quantities[i].isEmpty() ||
                unitPrices[i] == null || unitPrices[i].isEmpty()) {
                continue;
            }
            int itemId = Integer.parseInt(itemIds[i]);
            int qty = Integer.parseInt(quantities[i]);
            double price = Double.parseDouble(unitPrices[i]);
            totalAmount += price * qty;
            com.example.dinithi_pahana_edu.model.BillItem billItem = new com.example.dinithi_pahana_edu.model.BillItem();
            billItem.setBillId(billId);
            billItem.setItemId(itemId);
            billItem.setQuantity(qty);
            billItem.setPrice(price);
            billItems.add(billItem);
            // Decrement stock for each new item
            itemService.decrementStock(itemId, qty);
        }
        double balance = totalAmount - paidAmount;
        int customerIdInt = Integer.parseInt(customerId);
        com.example.dinithi_pahana_edu.model.Bill bill = new com.example.dinithi_pahana_edu.model.Bill();
        bill.setId(billId);
        bill.setCustomerId(customerIdInt);
        bill.setBillNumber(billNumber);
        bill.setBillDate(new java.util.Date());
        bill.setBillDateTime(billDateTime);
        bill.setTotalAmount(totalAmount);
        bill.setPaidAmount(paidAmount);
        bill.setBalance(balance);
        boolean updated = billService.updateBill(bill);
        if (updated) {
            billService.addBillItems(billItems);
            request.setAttribute("message", "Bill updated successfully! Bill Number: " + billNumber);
        } else {
            request.setAttribute("message", "Failed to update bill.");
        }
        // Reload bill for display
        doGet(request, response);
    }
} 