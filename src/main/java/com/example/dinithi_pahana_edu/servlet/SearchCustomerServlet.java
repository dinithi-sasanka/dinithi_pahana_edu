package com.example.dinithi_pahana_edu.servlet;

import com.example.dinithi_pahana_edu.model.Customer;
import com.example.dinithi_pahana_edu.model.User;
import com.example.dinithi_pahana_edu.service.CustomerService;
import com.example.dinithi_pahana_edu.service.BillService;

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
    private BillService billService = new BillService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchAccountNumber = request.getParameter("searchAccountNumber");
        String searchName = request.getParameter("searchName");
        String searchTelephone = request.getParameter("searchTelephone");
        String searchEmail = request.getParameter("searchEmail");
        
        // Preserve search values for display
        request.setAttribute("searchAccountNumber", searchAccountNumber);
        request.setAttribute("searchName", searchName);
        request.setAttribute("searchTelephone", searchTelephone);
        request.setAttribute("searchEmail", searchEmail);
        
        Customer customer = null;
        
        // Search by account number first, then name, then telephone, then email
        if (searchAccountNumber != null && !searchAccountNumber.trim().isEmpty()) {
            customer = customerService.getCustomerByAccountNumber(searchAccountNumber.trim());
        } else if (searchName != null && !searchName.trim().isEmpty()) {
            customer = customerService.searchCustomerByAnyField(searchName.trim());
        } else if (searchTelephone != null && !searchTelephone.trim().isEmpty()) {
            customer = customerService.searchCustomerByAnyField(searchTelephone.trim());
        } else if (searchEmail != null && !searchEmail.trim().isEmpty()) {
            customer = customerService.getCustomerByEmail(searchEmail.trim());
        }
        
        if (customer != null) {
            // Set customer data for the form
            request.setAttribute("accountNumber", customer.getAccountNumber());
            request.setAttribute("name", customer.getName());
            request.setAttribute("address", customer.getAddress());
            request.setAttribute("telephone", customer.getTelephone());
            request.setAttribute("email", customer.getEmail());
            request.setAttribute("message", "Customer found successfully! You can now edit the details below.");
            request.setAttribute("messageType", "success");
        } else {
            // Clear any previous customer data
            request.setAttribute("accountNumber", "");
            request.setAttribute("name", "");
            request.setAttribute("address", "");
            request.setAttribute("telephone", "");
            request.setAttribute("email", "");
            
            if ((searchAccountNumber != null && !searchAccountNumber.trim().isEmpty()) ||
                (searchName != null && !searchName.trim().isEmpty()) ||
                (searchTelephone != null && !searchTelephone.trim().isEmpty()) ||
                (searchEmail != null && !searchEmail.trim().isEmpty())) {
                request.setAttribute("message", "Customer not found. Please try different search criteria.");
                request.setAttribute("messageType", "error");
            }
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String searchTerm = request.getParameter("searchTerm");
        Customer customer = customerService.searchCustomerByAnyField(searchTerm);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        java.io.PrintWriter out = response.getWriter();
        if (customer != null) {
            int nextBillNumber = billService.getNextBillNumber();
            String json = String.format(
                "{\"id\":\"%s\",\"accountNumber\":\"%s\",\"name\":\"%s\",\"address\":\"%s\",\"telephone\":\"%s\",\"email\":\"%s\",\"nextBillNumber\":\"%s\"}",
                customer.getId(), customer.getAccountNumber(), customer.getName(),
                customer.getAddress(), customer.getTelephone(), customer.getEmail(), nextBillNumber
            );
            out.print(json);
        } else {
            out.print("{}");
        }
        out.flush();
    }
} 