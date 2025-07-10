package com.example.dinithi_pahana_edu.servlet;

import com.example.dinithi_pahana_edu.model.Customer;
import com.example.dinithi_pahana_edu.model.User;
import com.example.dinithi_pahana_edu.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/addCustomer")
public class AddCustomerServlet extends HttpServlet {
    private CustomerService customerService;
    
    @Override
    public void init() throws ServletException {
        customerService = new CustomerService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Redirect to the add customer page
        response.sendRedirect("addCustomer_admin.jsp");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect("error.jsp");
            return;
        }
        
        // Set response content type
        response.setContentType("text/html;charset=UTF-8");
        
        // Get form parameters
        String accountNumber = request.getParameter("accountNumber");
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String telephone = request.getParameter("telephone");
        
        // Create customer object
        Customer customer = new Customer();
        customer.setAccountNumber(accountNumber);
        customer.setName(name);
        customer.setAddress(address);
        customer.setTelephone(telephone);
        
        // Add customer to database
        boolean success = customerService.addCustomer(customer);
        
        if (success) {
            // Customer added successfully
            request.setAttribute("message", "Customer added successfully!");
            request.setAttribute("messageType", "success");
            request.setAttribute("accountNumber", "");
            request.setAttribute("name", "");
            request.setAttribute("address", "");
            request.setAttribute("telephone", "");
        } else {
            // Check if account number already exists
            if (customerService.isAccountNumberExists(accountNumber)) {
                request.setAttribute("message", "Account number already exists. Please use a different account number.");
                request.setAttribute("messageType", "error");
            } else {
                request.setAttribute("message", "Failed to add customer. Please check your input and try again.");
                request.setAttribute("messageType", "error");
            }
        }
        
        // Set form data back to request for repopulation
        request.setAttribute("accountNumber", accountNumber);
        request.setAttribute("name", name);
        request.setAttribute("address", address);
        request.setAttribute("telephone", telephone);
        
        // Forward back to the add customer page
        request.getRequestDispatcher("addCustomer_admin.jsp").forward(request, response);
    }
} 