<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.example.dinithi_pahana_edu.service.ItemService" %>
<%@ page import="com.example.dinithi_pahana_edu.model.Item" %>
<jsp:include page="sidebar_admin.jspf" />
<!DOCTYPE html>
<html>
<head>
    <title>Low Stock Items Report</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
<div class="main-content">
    <div class="container mt-4">
        <a href="reports_admin.jsp" class="btn btn-outline-primary mb-3"><i class="fa fa-arrow-left"></i> Back to Reports Dashboard</a>
        <h2 class="mb-4"><i class="fa fa-exclamation-triangle"></i> Low Stock Items Report</h2>
        <table class="table table-bordered report-table">
            <thead><tr><th>Item ID</th><th>Name</th><th>Current Stock</th></tr></thead>
            <tbody>
            <% ItemService itemService = new ItemService();
               List<Item> lowStockItems = itemService.getLowStockItems(20);
               for (Item item : lowStockItems) { %>
               <tr>
                   <td><%= item.getId() %></td>
                   <td><%= item.getName() %></td>
                   <td><%= item.getStock() %></td>
               </tr>
            <% } if (lowStockItems.isEmpty()) { %>
               <tr><td colspan="3">No low stock items.</td></tr>
            <% } %>
            </tbody>
        </table>
    </div>
</div>
</body>
</html> 