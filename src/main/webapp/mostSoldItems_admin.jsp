<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.example.dinithi_pahana_edu.service.BillService" %>
<jsp:include page="sidebar_admin.jspf" />
<!DOCTYPE html>
<html>
<head>
    <title>Most Sold Items Report</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
<div class="main-content">
    <div class="container mt-4">
        <a href="reports_admin.jsp" class="btn btn-outline-primary mb-3"><i class="fa fa-arrow-left"></i> Back to Reports Dashboard</a>
        <h2 class="mb-4"><i class="fa fa-box"></i> Most Sold Items Report</h2>
        <table class="table table-bordered report-table">
            <thead><tr><th>Item ID</th><th>Name</th><th>Total Sold</th></tr></thead>
            <tbody>
            <% BillService billService = new BillService();
               List<Object[]> mostSoldItems = billService.getMostSoldItems(10);
               for (Object[] row : mostSoldItems) { %>
               <tr>
                   <td><%= row[0] %></td>
                   <td><%= row[1] %></td>
                   <td><%= row[2] %></td>
               </tr>
            <% } if (mostSoldItems.isEmpty()) { %>
               <tr><td colspan="3">No data available.</td></tr>
            <% } %>
            </tbody>
        </table>
    </div>
</div>
</body>
</html> 