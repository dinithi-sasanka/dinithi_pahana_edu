package com.example.dinithi_pahana_edu.servlet;

import com.example.dinithi_pahana_edu.model.User;
import com.example.dinithi_pahana_edu.model.Bill;
import com.example.dinithi_pahana_edu.service.BillService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/viewBills")
public class ViewBillsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        User user = (User) session.getAttribute("user");
        String role = user.getRole().toLowerCase();
        String searchTerm = request.getParameter("search");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        BillService billService = new BillService();
        List<Bill> bills;
        if ((searchTerm != null && !searchTerm.trim().isEmpty()) ||
            (fromDate != null && !fromDate.isEmpty()) ||
            (toDate != null && !toDate.isEmpty())) {
            bills = billService.searchBills(searchTerm, fromDate, toDate);
        } else {
            bills = billService.getAllBills();
        }
        request.setAttribute("bills", bills);
        String jsp;
        switch (role) {
            case "admin":
                jsp = "printViewBills_admin.jsp";
                break;
            case "coadmin":
                jsp = "printViewBills_coadmin.jsp";
                break;
            case "staff":
                jsp = "printViewBills_staff.jsp";
                break;
            default:
                response.sendRedirect("error.jsp");
                return;
        }
        request.getRequestDispatcher(jsp).forward(request, response);
    }
} 