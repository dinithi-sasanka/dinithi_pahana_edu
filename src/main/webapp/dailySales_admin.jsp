<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.example.dinithi_pahana_edu.service.BillService" %>
<%@ page session="true" %>
<%
    com.example.dinithi_pahana_edu.model.User user = (com.example.dinithi_pahana_edu.model.User) session.getAttribute("user");
    if (user == null || !"admin".equalsIgnoreCase(user.getRole())) {
        response.sendRedirect("error.jsp");
        return;
    }
    
    // Get date parameters from request
    String startDate = request.getParameter("startDate");
    String endDate = request.getParameter("endDate");
    
    // Default to last 30 days if no dates provided
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Calendar cal = Calendar.getInstance();
    
    if (startDate == null || startDate.isEmpty()) {
        cal.add(Calendar.DAY_OF_MONTH, -30);
        startDate = dateFormat.format(cal.getTime());
    }
    
    if (endDate == null || endDate.isEmpty()) {
        cal = Calendar.getInstance();
        endDate = dateFormat.format(cal.getTime());
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
        .search-section {
            max-width: 1100px;
            margin: 0 auto 20px auto;
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 2px 8px rgba(44,62,80,0.10);
            padding: 25px 30px;
        }
        .search-form {
            display: flex;
            gap: 20px;
            align-items: center;
            flex-wrap: wrap;
        }
        .form-group {
            display: flex;
            flex-direction: column;
            gap: 8px;
        }
        .form-group label {
            color: #232b3e;
            font-weight: 600;
            font-size: 0.95rem;
        }
        .form-group input[type="date"] {
            padding: 10px 12px;
            border: 1.5px solid #ddd;
            border-radius: 6px;
            font-size: 1rem;
            color: #232b3e;
            background: #fff;
            transition: border-color 0.2s;
        }
        .form-group input[type="date"]:focus {
            outline: none;
            border-color: #21b701;
        }
        .search-buttons {
            display: flex;
            gap: 10px;
            align-items: flex-end;
        }
        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 14px;
            text-decoration: none;
            display: inline-block;
            font-weight: 600;
            transition: all 0.2s;
        }
        .btn-primary {
            background: #21b701;
            color: white;
        }
        .btn-primary:hover {
            background: #1a8f01;
        }
        .btn-secondary {
            background: #6c757d;
            color: white;
        }
        .btn-secondary:hover {
            background: #5a6268;
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
        .table-area {
            max-width: 1100px;
            margin: 0 auto;
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 2px 8px rgba(44,62,80,0.10);
            padding: 30px 30px 20px 30px;
        }
        .table-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
            flex-wrap: wrap;
            gap: 15px;
        }
        .table-title {
            color: #232b3e;
            margin: 0;
            font-size: 1.5rem;
        }
        .summary-cards {
            display: flex;
            gap: 15px;
            margin-bottom: 20px;
            flex-wrap: wrap;
        }
        .summary-card {
            background: #f8f9fa;
            border: 1px solid #e9ecef;
            border-radius: 8px;
            padding: 15px 20px;
            flex: 1;
            min-width: 150px;
            text-align: center;
        }
        .summary-card h4 {
            margin: 0 0 8px 0;
            color: #6c757d;
            font-size: 0.9rem;
            font-weight: 600;
        }
        .summary-card .value {
            font-size: 1.5rem;
            font-weight: 700;
            color: #232b3e;
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
        .no-data {
            text-align: center;
            color: #666;
            font-style: italic;
            padding: 40px 20px;
        }
        @media (max-width: 768px) {
            .search-form {
                flex-direction: column;
                align-items: stretch;
            }
            .search-buttons {
                justify-content: center;
            }
            .summary-cards {
                flex-direction: column;
            }
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
        
        <!-- Search Section -->
        <div class="search-section">
            <form class="search-form" method="get" action="dailySales_admin.jsp">
                <div class="form-group">
                    <label for="startDate">Start Date</label>
                    <input type="date" id="startDate" name="startDate" value="<%= startDate %>" required>
                </div>
                <div class="form-group">
                    <label for="endDate">End Date</label>
                    <input type="date" id="endDate" name="endDate" value="<%= endDate %>" required>
                </div>
                <div class="search-buttons">
                    <button type="submit" class="btn btn-primary">
                        <i class="fa fa-search"></i> Search
                    </button>
                </div>
            </form>
        </div>
        
        <div class="table-area">
            <div class="table-header">
                <h2 class="table-title">
                    <i class="fa fa-calendar-day"></i> 
                    Daily Sales Report 
                    <% if (startDate != null && endDate != null) { %>
                        (<%= startDate %> to <%= endDate %>)
                    <% } %>
                </h2>
                <a href="reports_admin.jsp" class="btn btn-outline-primary">
                    <i class="fa fa-arrow-left"></i> Back to Reports Dashboard
                </a>
            </div>
            
            <%
                BillService billService = new BillService();
                List<Object[]> dailySales;
                double totalSales = 0.0;
                int totalDays = 0;
                
                if (startDate != null && endDate != null) {
                    // Get sales for specific date range
                    dailySales = billService.getDailySalesByDateRange(startDate, endDate);
                } else {
                    // Default to last 30 days
                    dailySales = billService.getDailySales(30);
                }
                
                // Calculate summary statistics
                for (Object[] row : dailySales) {
                    if (row[1] != null) {
                        totalSales += ((Number) row[1]).doubleValue();
                        totalDays++;
                    }
                }
                
                double averageSales = totalDays > 0 ? totalSales / totalDays : 0.0;
            %>
            
            <!-- Summary Cards -->
            <div class="summary-cards">
                <div class="summary-card">
                    <h4>Total Sales</h4>
                    <div class="value">Rs. <%= String.format("%.2f", totalSales) %></div>
                </div>
                <div class="summary-card">
                    <h4>Days with Sales</h4>
                    <div class="value"><%= totalDays %></div>
                </div>
                <div class="summary-card">
                    <h4>Average Daily Sales</h4>
                    <div class="value">Rs. <%= String.format("%.2f", averageSales) %></div>
                </div>
                <div class="summary-card">
                    <h4>Date Range</h4>
                    <div class="value"><%= startDate %> to <%= endDate %></div>
                </div>
            </div>
            
            <table>
                <thead>
                    <tr>
                        <th>Date</th>
                        <th>Total Sales</th>
                        <th>Percentage of Total</th>
                    </tr>
                </thead>
                <tbody>
                <% if (!dailySales.isEmpty()) { 
                    for (Object[] row : dailySales) { 
                        double daySales = row[1] != null ? ((Number) row[1]).doubleValue() : 0.0;
                        double percentage = totalSales > 0 ? (daySales / totalSales) * 100 : 0.0;
                %>
                   <tr>
                       <td><%= row[0] %></td>
                       <td>Rs. <%= String.format("%.2f", daySales) %></td>
                       <td><%= String.format("%.1f", percentage) %>%</td>
                   </tr>
                <% } 
                } else { %>
                   <tr>
                       <td colspan="3" class="no-data">
                           <i class="fa fa-info-circle"></i> No sales data available for the selected date range.
                       </td>
                   </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
    
    <script>
        // Set date range to last 30 days
        function setLast30Days() {
            const endDate = new Date();
            const startDate = new Date();
            startDate.setDate(endDate.getDate() - 30);
            
            document.getElementById('startDate').value = formatDate(startDate);
            document.getElementById('endDate').value = formatDate(endDate);
            
            // Submit the form automatically
            document.querySelector('form').submit();
        }
        
        // Set date range to last 7 days
        function setLast7Days() {
            const endDate = new Date();
            const startDate = new Date();
            startDate.setDate(endDate.getDate() - 7);
            
            document.getElementById('startDate').value = formatDate(startDate);
            document.getElementById('endDate').value = formatDate(endDate);
            
            // Submit the form automatically
            document.querySelector('form').submit();
        }
        
        // Set date range to this month
        function setThisMonth() {
            const now = new Date();
            const startDate = new Date(now.getFullYear(), now.getMonth(), 1);
            const endDate = new Date();
            
            document.getElementById('startDate').value = formatDate(startDate);
            document.getElementById('endDate').value = formatDate(endDate);
            
            // Submit the form automatically
            document.querySelector('form').submit();
        }
        
        // Format date to YYYY-MM-DD
        function formatDate(date) {
            const year = date.getFullYear();
            const month = String(date.getMonth() + 1).padStart(2, '0');
            const day = String(date.getDate()).padStart(2, '0');
            return `${year}-${month}-${day}`;
        }
        
        // Validate date range
        document.querySelector('form').addEventListener('submit', function(e) {
            const startDate = new Date(document.getElementById('startDate').value);
            const endDate = new Date(document.getElementById('endDate').value);
            
            if (startDate > endDate) {
                e.preventDefault();
                alert('Start date cannot be after end date!');
                return false;
            }
            
            // Check if date range is not more than 1 year
            const diffTime = Math.abs(endDate - startDate);
            const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
            
            if (diffDays > 365) {
                if (!confirm('The selected date range is more than 1 year. This might take a while to load. Continue?')) {
                    e.preventDefault();
                    return false;
                }
            }
        });
        
        // Set max date for end date input
        document.getElementById('endDate').setAttribute('max', formatDate(new Date()));
        
        // Update end date max when start date changes
        document.getElementById('startDate').addEventListener('change', function() {
            const startDate = this.value;
            if (startDate) {
                document.getElementById('endDate').setAttribute('min', startDate);
            }
        });
    </script>
</body>
</html> 