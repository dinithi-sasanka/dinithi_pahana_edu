package com.example.dinithi_pahana_edu.servlet;

import com.example.dinithi_pahana_edu.model.Bill;
import com.example.dinithi_pahana_edu.model.BillItem;
import com.example.dinithi_pahana_edu.model.Customer;
import com.example.dinithi_pahana_edu.model.Item;
import com.example.dinithi_pahana_edu.service.BillService;
import com.example.dinithi_pahana_edu.service.CustomerService;
import com.example.dinithi_pahana_edu.service.EmailService;
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
    private CustomerService customerService = new CustomerService();
    private EmailService emailService = new EmailService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Item> items = itemService.getAllItems();
        request.setAttribute("items", items);
        int nextBillNumber = billService.getNextBillNumber();
        request.setAttribute("nextBillNumber", nextBillNumber);
        javax.servlet.http.HttpSession session = request.getSession(false);
        String jspPage = "calculateBills_admin.jsp";
        if (session != null) {
            com.example.dinithi_pahana_edu.model.User user = (com.example.dinithi_pahana_edu.model.User) session.getAttribute("user");
            if (user != null) {
                if ("coadmin".equalsIgnoreCase(user.getRole())) {
                    jspPage = "calculateBills_coadmin.jsp";
                } else if ("staff".equalsIgnoreCase(user.getRole())) {
                    jspPage = "calculateBills_staff.jsp";
                }
            }
        }
        request.getRequestDispatcher(jspPage).forward(request, response);
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
                // Decrement stock for each item
                itemService.decrementStock(item.getItemId(), item.getQuantity());
            }
            billService.addBillItems(billItems);
            
            // Send bill email to customer
            try {
                System.out.println("[EMAIL DEBUG] Attempting to send bill email for bill: " + billNumber);
                Customer customer = customerService.getCustomerById(customerIdInt);
                System.out.println("[EMAIL DEBUG] Customer found: " + (customer != null ? "YES" : "NO"));
                
                if (customer != null) {
                    System.out.println("[EMAIL DEBUG] Customer email: " + customer.getEmail());
                    System.out.println("[EMAIL DEBUG] Customer name: " + customer.getName());
                }
                
                if (customer != null && customer.getEmail() != null && !customer.getEmail().trim().isEmpty()) {
                    // Get bill items with item names for email
                    List<BillItem> billItemsWithNames = billService.getBillItemsByBillId(billId);
                    System.out.println("[EMAIL DEBUG] Bill items count: " + billItemsWithNames.size());
                    
                    System.out.println("[EMAIL DEBUG] Calling emailService.sendBillEmail...");
                    boolean emailSent = emailService.sendBillEmail(customer, bill, billItemsWithNames);
                    System.out.println("[EMAIL DEBUG] Email sent result: " + emailSent);
                    
                    if (emailSent) {
                        request.setAttribute("message", "Bill saved successfully! Bill Number: " + billNumber + ". Bill email sent to customer: " + customer.getEmail());
                    } else {
                        request.setAttribute("message", "Bill saved successfully! Bill Number: " + billNumber + ". Bill email could not be sent.");
                    }
                } else {
                    String reason = customer == null ? "Customer not found" : 
                                  (customer.getEmail() == null ? "Customer email is null" : "Customer email is empty");
                    System.out.println("[EMAIL DEBUG] No email sent. Reason: " + reason);
                    request.setAttribute("message", "Bill saved successfully! Bill Number: " + billNumber + ". No bill email sent (" + reason + ").");
                }
            } catch (Exception e) {
                System.err.println("[EMAIL ERROR] Error sending bill email: " + e.getMessage());
                e.printStackTrace();
                request.setAttribute("message", "Bill saved successfully! Bill Number: " + billNumber + ". Bill email could not be sent due to an error: " + e.getMessage());
            }
        } else {
            request.setAttribute("message", "Failed to save bill.");
        }
        // Reload items for the dropdown after saving
        List<Item> items = itemService.getAllItems();
        request.setAttribute("items", items);
        request.getRequestDispatcher("calculateBills_admin.jsp").forward(request, response);
    }
} 