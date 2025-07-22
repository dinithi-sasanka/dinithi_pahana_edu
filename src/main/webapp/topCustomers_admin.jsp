<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.example.dinithi_pahana_edu.service.BillService" %>
<jsp:include page="sidebar_admin.jspf" />
<!DOCTYPE html>
<html>
<head>
    <title>Top Customer Report</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
<div class="main-content">
    <div class="container mt-4">
        <a href="reports_admin.jsp" class="btn btn-outline-primary mb-3"><i class="fa fa-arrow-left"></i> Back to Reports Dashboard</a>
        <h2 class="mb-4"><i class="fa fa-user-tie"></i> Top Customer Report</h2>
        <table class="table table-bordered report-table">
            <thead><tr><th>Customer ID</th><th>Name</th><th>Account #</th><th>Total Spent</th></tr></thead>
            <tbody>
            <% BillService billService = new BillService();
               List<Object[]> topCustomers = billService.getTopCustomers(10);
               for (Object[] row : topCustomers) { %>
               <tr>
                   <td><%= row[0] %></td>
                   <td><%= row[1] %></td>
                   <td><%= row[2] %></td>
                   <td>Rs. <%= String.format("%.2f", row[3]) %></td>
               </tr>
            <% } if (topCustomers.isEmpty()) { %>
               <tr><td colspan="4">No data available.</td></tr>
            <% } %>
            </tbody>
        </table>
    </div>
</div>
</body>
</html> 