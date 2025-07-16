package com.example.dinithi_pahana_edu.servlet;

import com.example.dinithi_pahana_edu.model.User;
import com.example.dinithi_pahana_edu.model.Customer;
import com.example.dinithi_pahana_edu.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/ViewCustomersServlet")
public class ViewCustomersServlet extends HttpServlet {
    private CustomerService customerService = new CustomerService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) (session != null ? session.getAttribute("user") : null);

        List<Customer> customerList = customerService.getAllCustomers();
        request.setAttribute("customerList", customerList);

        String forwardPage = "viewCustomer_admin.jsp";
        if (user != null) {
            if ("coadmin".equalsIgnoreCase(user.getRole())) {
                forwardPage = "viewCustomer_coadmin.jsp";
            } else if ("staff".equalsIgnoreCase(user.getRole())) {
                forwardPage = "viewCustomer_staff.jsp";
            }
        }
        request.getRequestDispatcher(forwardPage).forward(request, response);
    }
} 