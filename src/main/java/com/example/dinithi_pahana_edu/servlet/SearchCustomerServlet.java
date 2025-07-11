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

@WebServlet("/searchCustomer")
public class SearchCustomerServlet extends HttpServlet {
    private CustomerService customerService = new CustomerService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNumber = request.getParameter("searchAccountNumber");
        String name = request.getParameter("searchName");
        String telephone = request.getParameter("searchTelephone");

        Customer customer = customerService.searchCustomer(accountNumber, name, telephone);

        if (customer != null) {
            request.setAttribute("accountNumber", customer.getAccountNumber());
            request.setAttribute("name", customer.getName());
            request.setAttribute("address", customer.getAddress());
            request.setAttribute("telephone", customer.getTelephone());
        } else {
            request.setAttribute("message", "No customer found.");
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