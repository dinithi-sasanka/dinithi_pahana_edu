package com.example.dinithi_pahana_edu.servlet;

import com.example.dinithi_pahana_edu.model.Bill;
import com.example.dinithi_pahana_edu.model.BillItem;
import com.example.dinithi_pahana_edu.model.Customer;
import com.example.dinithi_pahana_edu.service.BillService;
import com.example.dinithi_pahana_edu.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/printBill")
public class PrintBillServlet extends HttpServlet {
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
        request.setAttribute("bill", bill);
        request.setAttribute("items", items);
        request.setAttribute("customer", customer);
        request.getRequestDispatcher("printBill.jsp").forward(request, response);
    }
} 