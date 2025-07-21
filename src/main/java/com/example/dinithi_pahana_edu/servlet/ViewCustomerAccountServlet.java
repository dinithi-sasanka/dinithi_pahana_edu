package com.example.dinithi_pahana_edu.servlet;

import com.example.dinithi_pahana_edu.model.Customer;
import com.example.dinithi_pahana_edu.model.Bill;
import com.example.dinithi_pahana_edu.model.BillItem;
import com.example.dinithi_pahana_edu.service.CustomerService;
import com.example.dinithi_pahana_edu.service.BillService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/viewCustomerAccount")
public class ViewCustomerAccountServlet extends HttpServlet {
    private CustomerService customerService = new CustomerService();
    private BillService billService = new BillService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String customerIdParam = request.getParameter("customerId");
        
        if (customerIdParam != null && !customerIdParam.trim().isEmpty()) {
            Customer customer = null;
            
            // First try to parse as integer (direct customer ID)
            try {
                int customerId = Integer.parseInt(customerIdParam);
                customer = customerService.getCustomerById(customerId);
            } catch (NumberFormatException e) {
                // If not a number, treat as search term
                customer = customerService.searchCustomerByAnyField(customerIdParam);
            }
            
            if (customer != null) {
                // Get customer's bills
                List<Bill> bills = billService.getBillsByCustomerId(customer.getId());
                
                // For each bill, get its items
                for (Bill bill : bills) {
                    List<BillItem> items = billService.getBillItemsByBillId(bill.getId());
                    bill.setBillItems(items);
                }
                
                request.setAttribute("customer", customer);
                request.setAttribute("bills", bills);
            } else {
                request.setAttribute("error", "Customer not found! Please try a different search term.");
            }
        }
        
        HttpSession session = request.getSession(false);
        String jspPage = "viewCustomerAccount_admin.jsp";
        if (session != null) {
            com.example.dinithi_pahana_edu.model.User user = (com.example.dinithi_pahana_edu.model.User) session.getAttribute("user");
            if (user != null) {
                if ("staff".equalsIgnoreCase(user.getRole())) {
                    jspPage = "viewCustomerAccount_staff.jsp";
                } else if ("coadmin".equalsIgnoreCase(user.getRole())) {
                    jspPage = "viewCustomerAccount_coadmin.jsp";
                }
            }
        }
        request.getRequestDispatcher(jspPage).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
} 