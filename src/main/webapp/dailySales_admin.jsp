<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.example.dinithi_pahana_edu.service.BillService" %>
<%@ page session="true" %>
<%
    com.example.dinithi_pahana_edu.model.User user = (com.example.dinithi_pahana_edu.model.User) session.getAttribute("user");
    if (user == null || !"admin".equalsIgnoreCase(user.getRole())) {
        response.sendRedirect("error.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Daily Sales Report - Admin</title>
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body {
            margin: 0;
            font-family: 'Roboto', Arial, sans-serif;
            background: linear-gradient(120deg, #232b3e, #1a2233);
            color: #d7dee5;
            min-height: 100vh;
        }
        .main-content {
            margin-left: 240px;
            padding: 40px 30px;
            background: #ffffffe7;
            min-height: 100vh;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
        }
        .header h1 {
            color: #232b3e;
            font-size: 2rem;
            margin: 0;
        }
        .user-info {
            font-size: 1.1em;
            color: #21b701;
            background: #27304a;
            padding: 8px 18px;
            border-radius: 20px;
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .table-area {
            max-width: 1100px;
            margin: 0 auto;
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 2px 8px rgba(44,62,80,0.10);
            padding: 30px 30px 20px 30px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
            background: #fff;
        }
        th, td {
            padding: 12px 10px;
            text-align: left;
            border-bottom: 1px solid #e0e0e0;
            color: #232b3e;
        }
        th {
            background: #f4f4f4;
            font-weight: 700;
        }
        tr:last-child td {
            border-bottom: none;
        }
        .btn {
            padding: 8px 16px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            text-decoration: none;
            display: inline-block;
            margin-bottom: 20px;
        }
        .btn-outline-primary {
            background: transparent;
            color: #007bff;
            border: 1px solid #007bff;
        }
        .btn-outline-primary:hover {
            background: #007bff;
            color: white;
        }
    </style>
</head>
<body>
    <jsp:include page="sidebar_admin.jspf" />
    <div class="main-content">
        <div class="header">
            <div>
                <h1>Pahana Edu Bookshop Management System</h1>
            </div>
            <div class="user-info">
                <i class="fa fa-user-shield"></i> <span>Role: <%= user.getRole() %></span>
            </div>
        </div>
        <div class="table-area">
            <a href="reports_admin.jsp" class="btn btn-outline-primary"><i class="fa fa-arrow-left"></i> Back to Reports Dashboard</a>
            <h2 style="color: #232b3e; margin-bottom: 20px; text-align: center;"><i class="fa fa-calendar-day"></i> Daily Sales Report (Last 30 Days)</h2>
            <table>
                <thead>
                    <tr>
                        <th>Date</th>
                        <th>Total Sales</th>
                    </tr>
                </thead>
                <tbody>
                <% BillService billService = new BillService();
                   List<Object[]> dailySales = billService.getDailySales(30);
                   for (Object[] row : dailySales) { %>
                   <tr>
                       <td><%= row[0] %></td>
                       <td>Rs. <%= String.format("%.2f", row[1]) %></td>
                   </tr>
                <% } if (dailySales.isEmpty()) { %>
                   <tr><td colspan="2" style="text-align: center; color: #666; font-style: italic;">No data available.</td></tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html> 