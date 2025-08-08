package com.example.dinithi_pahana_edu.servlet;

import com.example.dinithi_pahana_edu.service.CustomerService;
import com.example.dinithi_pahana_edu.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/DeleteCustomerServlet")
public class DeleteCustomerServlet extends HttpServlet {
    private CustomerService customerService = new CustomerService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        
        // Check if user is authenticated and has appropriate role
        if (user == null || (!"admin".equalsIgnoreCase(user.getRole()) && 
            !"coadmin".equalsIgnoreCase(user.getRole()) && 
            !"staff".equalsIgnoreCase(user.getRole()))) {
            response.sendRedirect("error.jsp");
            return;
        }
        
        String idStr = request.getParameter("id");
        boolean deleted = false;
        if (idStr != null) {
            try {
                int id = Integer.parseInt(idStr);
                deleted = customerService.deleteCustomer(id);
            } catch (NumberFormatException e) {
                // ignore, will show error message
            }
        }
        
        String message = deleted ? "Customer deleted successfully!" : "Failed to delete customer.";
        String forwardPage = "ViewCustomerServlet?message=" + java.net.URLEncoder.encode(message, "UTF-8");
        response.sendRedirect(forwardPage);
    }
} 