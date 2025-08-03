package com.example.dinithi_pahana_edu.servlet;

import com.example.dinithi_pahana_edu.model.Bill;
import com.example.dinithi_pahana_edu.model.BillItem;
import com.example.dinithi_pahana_edu.model.Customer;
import com.example.dinithi_pahana_edu.service.BillService;
import com.example.dinithi_pahana_edu.service.CustomerService;
import com.example.dinithi_pahana_edu.service.EmailService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/printBill")
public class PrintBillServlet extends HttpServlet {
    private EmailService emailService = new EmailService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String billIdStr = request.getParameter("billId");
        if (billIdStr == null) {
            response.sendRedirect("error.jsp");
            return;
        }
        int billId = Integer.parseInt(billIdStr);
        BillService billService = new BillService();
        List<Bill> allBills = billService.getAllBills();
        Bill bill = null;
        for (Bill b : allBills) {
            if (b.getId() == billId) {
                bill = b;
                break;
            }
        }
        if (bill == null) {
            response.sendRedirect("error.jsp");
            return;
        }
        List<BillItem> items = billService.getBillItemsByBillId(billId);
        CustomerService customerService = new CustomerService();
        Customer customer = customerService.getCustomerById(bill.getCustomerId());
        
        // Send bill email to customer when bill is printed
        if (customer != null && customer.getEmail() != null && !customer.getEmail().trim().isEmpty()) {
            try {
                System.out.println("[EMAIL DEBUG] Attempting to send bill email for printed bill: " + bill.getBillNumber());
                boolean emailSent = emailService.sendBillEmail(customer, bill, items);
                if (emailSent) {
                    System.out.println("[EMAIL DEBUG] Bill email sent successfully for printed bill!");
                    request.setAttribute("emailMessage", "Bill email sent successfully to customer: " + customer.getEmail());
                } else {
                    System.out.println("[EMAIL DEBUG] Failed to send bill email for printed bill.");
                    request.setAttribute("emailMessage", "Bill email could not be sent to customer.");
                }
            } catch (Exception e) {
                System.err.println("[EMAIL ERROR] Error sending bill email for printed bill: " + e.getMessage());
                e.printStackTrace();
                request.setAttribute("emailMessage", "Bill email could not be sent due to an error.");
            }
        } else {
            String reason = customer == null ? "Customer not found" : 
                          (customer.getEmail() == null ? "Customer email is null" : "Customer email is empty");
            System.out.println("[EMAIL DEBUG] No email sent for printed bill. Reason: " + reason);
            request.setAttribute("emailMessage", "No bill email sent (" + reason + ").");
        }
        
        request.setAttribute("bill", bill);
        request.setAttribute("items", items);
        request.setAttribute("customer", customer);
        request.getRequestDispatcher("printBill.jsp").forward(request, response);
    }
} 