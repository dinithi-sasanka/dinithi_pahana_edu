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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String searchTerm = request.getParameter("searchTerm");
        Customer customer = customerService.searchCustomerByAnyField(searchTerm);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        java.io.PrintWriter out = response.getWriter();
        if (customer != null) {
            int nextBillNumber = billService.getNextBillNumber();
            String json = String.format(
                "{\"id\":\"%s\",\"accountNumber\":\"%s\",\"name\":\"%s\",\"address\":\"%s\",\"telephone\":\"%s\",\"nextBillNumber\":\"%s\"}",
                customer.getId(), customer.getAccountNumber(), customer.getName(),
                customer.getAddress(), customer.getTelephone(), nextBillNumber
            );
            out.print(json);
        } else {
            out.print("{}");
        }
        out.flush();
    }
} 