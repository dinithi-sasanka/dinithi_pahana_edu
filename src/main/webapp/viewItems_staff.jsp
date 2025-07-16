<%@ page session="true" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.dinithi_pahana_edu.model.Item" %>
<%
    com.example.dinithi_pahana_edu.model.User user = (com.example.dinithi_pahana_edu.model.User) session.getAttribute("user");
    if (user == null || !"staff".equalsIgnoreCase(user.getRole())) {
        response.sendRedirect("error.jsp");
        return;
    }
    List<Item> itemList = (List<Item>) request.getAttribute("itemList");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Items - Staff</title>
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body { margin: 0; font-family: 'Roboto', Arial, sans-serif; background: linear-gradient(120deg, #232b3e, #1a2233); color: #d7dee5; min-height: 100vh; }
        .sidebar { position: fixed; left: 0; top: 0; bottom: 0; width: 220px; background: #232b3e; padding: 30px 0 0 0; box-shadow: 2px 0 10px rgba(0,0,0,0.1); display: flex; flex-direction: column; z-index: 10; }
        .sidebar h2 { color: #fff; text-align: center; margin-bottom: 2rem; font-size: 1.7rem; letter-spacing: 1px; }
        .sidebar ul { list-style: none; padding: 0; margin: 0; }
        .sidebar ul li { margin: 18px 0; }
        .sidebar ul li a { color: #21b701; text-decoration: none; font-size: 1.1em; padding: 10px 30px; display: block; border-radius: 6px; transition: background 0.2s, color 0.2s, border-color 0.2s; border-left: 4px solid transparent; }
        .sidebar ul li a:hover { background: #fcfbfb; color: #232b3e; border-left: 4px solid #232b3e; }
        .sidebar ul li a i { color: #acacac; margin-right: 10px; transition: color 0.2s; }
        .sidebar ul li a:hover i { color: #21b701; }
        .main-content { margin-left: 240px; padding: 40px 30px; background: #ffffffe7; min-height: 100vh; }
        .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px; }
        .header h1 { color: #232b3e; font-size: 2rem; margin: 0; }
        .user-info { font-size: 1.1em; color: #21b701; background: #27304a; padding: 8px 18px; border-radius: 20px; display: flex; align-items: center; gap: 10px; }
        .table-area { max-width: 1100px; margin: 0 auto; background: #fff; border-radius: 12px; box-shadow: 0 2px 8px rgba(44,62,80,0.10); padding: 30px 30px 20px 30px; }
        table { width: 100%; border-collapse: collapse; margin-top: 10px; background: #fff; }
        th, td { padding: 12px 10px; text-align: left; border-bottom: 1px solid #e0e0e0; color: #232b3e; }
        th { background: #f4f4f4; font-weight: 700; }
        tr:last-child td { border-bottom: none; }
        @media (max-width: 800px) {
            .sidebar { width: 60px; padding: 20px 0 0 0; }
            .sidebar h2 { display: none; }
            .sidebar ul li a { padding: 10px 10px; font-size: 1.2em; text-align: center; }
            .main-content { margin-left: 70px; padding: 20px 10px; }
            .table-area { padding: 10px 2vw; }
        }
        @media (max-width: 600px) {
            .main-content { margin-left: 0; padding: 10px 2vw; }
            .sidebar { position: static; width: 100%; height: auto; flex-direction: row; }
            .sidebar ul { display: flex; flex-direction: row; justify-content: space-around; }
            .sidebar ul li { margin: 0; }
            .table-area { padding: 5px 1vw; }
        }
    </style>
</head>
<body>
    <div class="sidebar">
        <h2>Staff</h2>
        <ul>
            <li><a href="dashboard_staff.jsp"><i class="fa fa-chart-line"></i> Dashboards</a></li>
            <li><a href="addCustomer_staff.jsp"><i class="fa fa-user-plus"></i> Add Customer</a></li>
            <li><a href="editCustomer_staff.jsp"><i class="fa fa-user-edit"></i> Edit Customer</a></li>
            <li><a href="viewAccount.jsp"><i class="fa fa-id-card"></i> View Account</a></li>
            <li><a href="ViewItemsServlet"><i class="fa fa-boxes"></i> View Items</a></li>
            <li><a href="calculateBill.jsp"><i class="fa fa-calculator"></i> Calculate Bill</a></li>
            <li><a href="printBill.jsp"><i class="fa fa-print"></i> Print/View Bills</a></li>
            <li><a href="help.jsp"><i class="fa fa-question-circle"></i> Help</a></li>
            <li><a href="logout"><i class="fa fa-sign-out-alt"></i> Logout</a></li>
        </ul>
    </div>
    <div class="main-content">
<%
   String message = (String) request.getAttribute("message");
   if (message == null) message = request.getParameter("message");
   if (message != null) {
       String bg = message.contains("success") ? "#d4edda" : "#f8d7da";
       String color = message.contains("success") ? "#155724" : "#721c24";
       String border = message.contains("success") ? "#c3e6cb" : "#f5c6cb";
%>
    <div style="background:<%= bg %>; color:<%= color %>; border:1px solid <%= border %>; padding:16px; border-radius:7px; margin-bottom:18px; font-weight:600; text-align:center; max-width:700px; margin-left:auto; margin-right:auto;">
        <%= message %>
    </div>
<% } %>
        <div class="header">
            <div>
                <h1>Pahana Edu Bookshop Management System</h1>
            </div>
            <div class="user-info">
                <i class="fa fa-user"></i> <span>Role: staff</span>
            </div>
        </div>
        <div class="table-area">
            <div style="display: flex; gap: 18px; align-items: flex-end; margin-bottom: 18px; flex-wrap: wrap;">
                <form method="get" action="ViewItemsServlet" style="margin: 0;">
                    <button type="submit" style="background:#43e97b; color:#232b3e; font-weight:700; border:none; border-radius:5px; padding:10px 28px; font-size:1.08em; cursor:pointer; transition:background 0.2s; display:flex; align-items:center; gap:8px;">
                        <i class="fa fa-sync-alt"></i> <span>Sync &amp; View All Items</span>
                    </button>
                </form>
                <form method="get" action="ViewItemsServlet" style="display: flex; gap: 16px; align-items: flex-end; flex-wrap: wrap; margin: 0;">
                    <div style="display: flex; flex-direction: column;">
                        <label for="name" style="color:#232b3e; font-weight:500;">Item Name</label>
                        <input type="text" id="name" name="name" placeholder="Item Name" style="padding:7px 10px; border-radius:5px; border:1px solid #ccc; min-width:140px;" value="<%= request.getParameter("name") != null ? request.getParameter("name") : "" %>">
                    </div>
                    <div style="display: flex; flex-direction: column;">
                        <label for="category" style="color:#232b3e; font-weight:500;">Category</label>
                        <input type="text" id="category" name="category" placeholder="Category" style="padding:7px 10px; border-radius:5px; border:1px solid #ccc; min-width:120px;" value="<%= request.getParameter("category") != null ? request.getParameter("category") : "" %>">
                    </div>
                    <div style="display: flex; flex-direction: column;">
                        <label for="minPrice" style="color:#232b3e; font-weight:500;">Min Price</label>
                        <input type="number" id="minPrice" name="minPrice" placeholder="Min Price" style="padding:7px 10px; border-radius:5px; border:1px solid #ccc; min-width:90px;" value="<%= request.getParameter("minPrice") != null ? request.getParameter("minPrice") : "" %>">
                    </div>
                    <div style="display: flex; flex-direction: column;">
                        <label for="maxPrice" style="color:#232b3e; font-weight:500;">Max Price</label>
                        <input type="number" id="maxPrice" name="maxPrice" placeholder="Max Price" style="padding:7px 10px; border-radius:5px; border:1px solid #ccc; min-width:90px;" value="<%= request.getParameter("maxPrice") != null ? request.getParameter("maxPrice") : "" %>">
                    </div>
                    <button type="submit" style="background:#1976d2; color:#fff; font-weight:600; border:none; border-radius:5px; padding:10px 32px; margin-top:18px; font-size:1.08em; cursor:pointer; transition:background 0.2s;">Search</button>
                </form>
            </div>
            <h2 style="color:#232b3e;">All Items</h2>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Category</th>
                        <th>Description</th>
                        <th>Price</th>
                        <th>Stock</th>
                    </tr>
                </thead>
                <tbody>
                <% if (itemList != null && !itemList.isEmpty()) {
                    for (Item item : itemList) { %>
                    <tr>
                        <td><%= item.getId() %></td>
                        <td><%= item.getName() %></td>
                        <td><%= item.getCategory() %></td>
                        <td><%= item.getDescription() %></td>
                        <td><%= item.getPrice() %></td>
                        <td><%= item.getStock() %></td>
                    </tr>
                <%  } 
                } else { %>
                    <tr><td colspan="6" style="text-align:center; color:#888;">No items found.</td></tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html> 