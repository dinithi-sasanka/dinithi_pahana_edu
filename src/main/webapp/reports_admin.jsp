<%@ page language="java" contentType="text/html; charset=UTF-8" %>
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
    <title>Manage Reports - Admin</title>
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
        .report-options { 
            display: flex; 
            flex-wrap: wrap; 
            gap: 32px; 
            margin-top: 32px; 
        }
        .report-card {
            flex: 1 1 260px;
            min-width: 220px;
            max-width: 320px;
            background: #f8f9fa;
            border: 1.5px solid #ddd;
            border-radius: 12px;
            padding: 32px 18px;
            text-align: center;
            transition: box-shadow 0.2s, border-color 0.2s;
            cursor: pointer;
        }
        .report-card:hover {
            box-shadow: 0 4px 18px rgba(33,183,1,0.08);
            border-color: #21b701;
        }
        .report-card i { 
            font-size: 2.2em; 
            color: #21b701; 
            margin-bottom: 12px; 
        }
        .report-card h5 { 
            margin: 12px 0 0 0; 
            color: #232b3e; 
            font-weight: 600; 
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
        <div style="max-width: 1200px; margin: 0 auto;">
            <h2 style="color: #232b3e; margin-bottom: 30px; text-align: center;"><i class="fa fa-chart-bar"></i> Manage Reports</h2>
            <div class="report-options">
                <div class="report-card" onclick="location.href='topCustomers_admin.jsp'"><i class="fa fa-user-tie"></i><h5>Top Customer Report</h5></div>
                <div class="report-card" onclick="location.href='mostSoldItems_admin.jsp'"><i class="fa fa-box"></i><h5>Most Sold Items Report</h5></div>
                <div class="report-card" onclick="location.href='dailySales_admin.jsp'"><i class="fa fa-calendar-day"></i><h5>Daily Sales Report</h5></div>
                <div class="report-card" onclick="location.href='monthlySales_admin.jsp'"><i class="fa fa-calendar-alt"></i><h5>Monthly Sales Report</h5></div>
                <div class="report-card" onclick="location.href='lowStockItems_admin.jsp'"><i class="fa fa-exclamation-triangle"></i><h5>Low Stock Items Report</h5></div>
            </div>
        </div>
    </div>
</body>
</html> 