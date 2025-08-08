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
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

@WebServlet("/ViewCustomerServlet")
public class ViewCustomerServlet extends HttpServlet {
    private CustomerService customerService = new CustomerService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            response.sendRedirect("error.jsp");
            return;
        }

        List<Customer> customerList;
        
        // Get search parameters
        String searchTerm = request.getParameter("searchTerm");
        String searchType = request.getParameter("searchType");
        
        // Perform search based on parameters
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            switch (searchType) {
                case "all":
                case null:
                    // For general search, use name search which returns a list
                    customerList = customerService.searchCustomersByName(searchTerm);
                    break;
                case "name":
                    customerList = customerService.searchCustomersByName(searchTerm);
                    break;
                case "accountNumber":
                    Customer accountCustomer = customerService.getCustomerByAccountNumber(searchTerm);
                    customerList = accountCustomer != null ? Arrays.asList(accountCustomer) : new ArrayList<>();
                    break;
                case "email":
                    Customer emailCustomer = customerService.getCustomerByEmail(searchTerm);
                    customerList = emailCustomer != null ? Arrays.asList(emailCustomer) : new ArrayList<>();
                    break;
                case "telephone":
                    // For telephone search, use name search as a fallback since there's no specific telephone list method
                    customerList = customerService.searchCustomersByName(searchTerm);
                    break;
                case "address":
                    // For address search, use name search as a fallback since there's no specific address list method
                    customerList = customerService.searchCustomersByName(searchTerm);
                    break;
                default:
                    customerList = customerService.searchCustomersByName(searchTerm);
                    break;
            }
        } else {
            customerList = customerService.getAllCustomers();
        }
        
        request.setAttribute("customerList", customerList);
        request.setAttribute("searchTerm", searchTerm);
        request.setAttribute("searchType", searchType);
        
        // Forward to the appropriate JSP based on user role
        String forwardPage = "viewCustomer_admin.jsp";
        if ("coadmin".equalsIgnoreCase(user.getRole())) {
            forwardPage = "viewCustomer_coadmin.jsp";
        } else if ("staff".equalsIgnoreCase(user.getRole())) {
            forwardPage = "viewCustomer_staff.jsp";
        }
        
        request.getRequestDispatcher(forwardPage).forward(request, response);
    }
} 