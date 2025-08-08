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
    
    // Get search parameter from request
    String searchTerm = request.getParameter("searchTerm");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Most Sold Items Report - Admin</title>
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
            flex: 1;
            min-width: 200px;
        }
        .form-group label {
            color: #232b3e;
            font-weight: 600;
            font-size: 0.95rem;
        }
        .form-group input[type="text"] {
            padding: 10px 12px;
            border: 1.5px solid #ddd;
            border-radius: 6px;
            font-size: 1rem;
            color: #232b3e;
            background: #fff;
            transition: border-color 0.2s;
        }
        .form-group input[type="text"]:focus {
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
        .search-info {
            color: #666;
            font-size: 0.9rem;
            margin-bottom: 15px;
            text-align: center;
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
            <form class="search-form" method="get" action="mostSoldItems_admin.jsp">
                <div class="form-group">
                    <label for="searchTerm">Search by Item Name or ID</label>
                    <input type="text" id="searchTerm" name="searchTerm" value="<%= searchTerm != null ? searchTerm : "" %>" 
                           placeholder="Enter item name or ID to search...">
                </div>
                <div class="search-buttons">
                    <button type="submit" class="btn btn-primary">
                        <i class="fa fa-search"></i> Search
                    </button>
                    <button type="button" class="btn btn-secondary" onclick="clearSearch()">
                        <i class="fa fa-times"></i> Clear
                    </button>
                </div>
            </form>
        </div>
        
        <div class="table-area">
            <div class="table-header">
                <h2 class="table-title">
                    <i class="fa fa-box"></i> 
                    Most Sold Items Report
                    <% if (searchTerm != null && !searchTerm.trim().isEmpty()) { %>
                        (Search: "<%= searchTerm %>")
                    <% } %>
                </h2>
                <a href="reports_admin.jsp" class="btn btn-outline-primary">
                    <i class="fa fa-arrow-left"></i> Back to Reports Dashboard
                </a>
            </div>
            
            <%
                BillService billService = new BillService();
                List<Object[]> mostSoldItems;
                int totalItems = 0;
                int totalSold = 0;
                
                if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                    // Get items matching search term
                    mostSoldItems = billService.getMostSoldItemsBySearch(searchTerm.trim(), 50);
                } else {
                    // Get top 10 most sold items
                    mostSoldItems = billService.getMostSoldItems(10);
                }
                
                // Calculate summary statistics
                for (Object[] row : mostSoldItems) {
                    if (row[2] != null) {
                        totalItems++;
                        totalSold += ((Number) row[2]).intValue();
                    }
                }
                
                double averageSold = totalItems > 0 ? (double) totalSold / totalItems : 0.0;
            %>
            
            <!-- Summary Cards -->
            <div class="summary-cards">
                <div class="summary-card">
                    <h4>Total Items Found</h4>
                    <div class="value"><%= totalItems %></div>
                </div>
                <div class="summary-card">
                    <h4>Total Units Sold</h4>
                    <div class="value"><%= totalSold %></div>
                </div>
                <div class="summary-card">
                    <h4>Average Units per Item</h4>
                    <div class="value"><%= String.format("%.1f", averageSold) %></div>
                </div>
                <div class="summary-card">
                    <h4>Search Status</h4>
                    <div class="value">
                        <% if (searchTerm != null && !searchTerm.trim().isEmpty()) { %>
                            Filtered
                        <% } else { %>
                            All Items
                        <% } %>
                    </div>
                </div>
            </div>
            
            <% if (searchTerm != null && !searchTerm.trim().isEmpty()) { %>
                <div class="search-info">
                    <i class="fa fa-info-circle"></i> 
                    Showing results for: "<%= searchTerm %>" 
                    (<%= mostSoldItems.size() %> items found)
                </div>
            <% } %>
            
            <table>
                <thead>
                    <tr>
                        <th>Item ID</th>
                        <th>Name</th>
                        <th>Total Sold</th>
                        <th>Rank</th>
                    </tr>
                </thead>
                <tbody>
                <% if (!mostSoldItems.isEmpty()) { 
                    int rank = 1;
                    for (Object[] row : mostSoldItems) { %>
                   <tr>
                       <td><%= row[0] %></td>
                       <td><%= row[1] %></td>
                       <td><%= row[2] %></td>
                       <td><%= rank++ %></td>
                   </tr>
                <% } 
                } else { %>
                   <tr>
                       <td colspan="4" class="no-data">
                           <i class="fa fa-info-circle"></i> 
                           <% if (searchTerm != null && !searchTerm.trim().isEmpty()) { %>
                               No items found matching "<%= searchTerm %>".
                           <% } else { %>
                               No sales data available.
                           <% } %>
                       </td>
                   </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
    
    <script>
        // Clear search function
        function clearSearch() {
            document.getElementById('searchTerm').value = '';
            document.querySelector('form').submit();
        }
        
        // Auto-submit on Enter key
        document.getElementById('searchTerm').addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                e.preventDefault();
                document.querySelector('form').submit();
            }
        });
    </script>
</body>
</html> 