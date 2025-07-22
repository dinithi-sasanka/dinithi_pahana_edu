<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<jsp:include page="sidebar_admin.jspf" />
<!DOCTYPE html>
<html>
<head>
    <title>Manage Reports (Admin)</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .report-options { display: flex; flex-wrap: wrap; gap: 32px; margin-top: 32px; }
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
        .report-card i { font-size: 2.2em; color: #21b701; margin-bottom: 12px; }
        .report-card h5 { margin: 12px 0 0 0; color: #232b3e; font-weight: 600; }
    </style>
</head>
<body>
<div class="main-content">
    <div class="container mt-4">
        <h2 class="mb-4"><i class="fa fa-chart-bar"></i> Manage Reports</h2>
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