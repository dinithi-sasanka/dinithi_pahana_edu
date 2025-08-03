package com.example.dinithi_pahana_edu.servlet;

import com.example.dinithi_pahana_edu.model.Customer;
import com.example.dinithi_pahana_edu.model.User;
import com.example.dinithi_pahana_edu.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/updateCustomer")
public class UpdateCustomerServlet extends HttpServlet {
    private CustomerService customerService = new CustomerService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNumber = request.getParameter("accountNumber");
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String telephone = request.getParameter("telephone");
        String email = request.getParameter("email");

        Customer customer = customerService.getCustomerByAccountNumber(accountNumber);
        if (customer != null) {
            customer.setName(name);
            customer.setAddress(address);
            customer.setTelephone(telephone);
            customer.setEmail(email);

            boolean updated = customerService.updateCustomer(customer);

            if (updated) {
                request.setAttribute("message", "Customer updated successfully!");
                request.setAttribute("messageType", "success");
            } else {
                request.setAttribute("message", "Failed to update customer.");
                request.setAttribute("messageType", "error");
            }
            // Set updated values for display
            request.setAttribute("accountNumber", customer.getAccountNumber());
            request.setAttribute("name", customer.getName());
            request.setAttribute("address", customer.getAddress());
            request.setAttribute("telephone", customer.getTelephone());
            request.setAttribute("email", customer.getEmail());
        } else {
            request.setAttribute("message", "Customer not found.");
            request.setAttribute("messageType", "error");
        }

        // Forward back to the correct JSP based on user role
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String forwardPage = "editCustomer_admin.jsp";
        if (user != null) {
            if ("coadmin".equalsIgnoreCase(user.getRole())) {
                forwardPage = "editCustomer_coadmin.jsp";
            } else if ("staff".equalsIgnoreCase(user.getRole())) {
                forwardPage = "editCustomer_staff.jsp";
            }
        }
        request.getRequestDispatcher(forwardPage).forward(request, response);
    }
} 