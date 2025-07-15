<%@ page session="true" %>
<%@ page import="com.example.dinithi_pahana_edu.model.Item" %>
<%
    com.example.dinithi_pahana_edu.model.User user = (com.example.dinithi_pahana_edu.model.User) session.getAttribute("user");
    if (user == null || !"admin".equalsIgnoreCase(user.getRole())) {
        response.sendRedirect("error.jsp");
        return;
    }
    Item item = (Item) request.getAttribute("item");
    if (item == null) {
        response.sendRedirect("ViewItemsServlet");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Item - Admin</title>
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
        .form-area { display: flex; flex-direction: column; gap: 22px; max-width: 700px; width: 100%; margin: 0 auto; }
        .input-card { background: #fff; border-radius: 10px; box-shadow: 0 2px 8px rgba(44,62,80,0.10); border: 1px solid #e0e0e0; padding: 22px 28px 16px 28px; display: flex; flex-direction: column; width: 100%; }
        .input-card label { color: #232b3e; font-size: 1.08rem; font-weight: 500; margin-bottom: 7px; font-family: system-ui, Arial, sans-serif; }
        .input-card input, .input-card textarea { width: 100%; background: #fff; color: #232b3e; border: 1.5px solid #bdbdbd; font-size: 1.13rem; padding: 12px 14px; border-radius: 6px; transition: border-color 0.2s; box-sizing: border-box; font-family: system-ui, Arial, sans-serif; }
        .input-card textarea { min-height: 50px; resize: vertical; }
        .input-card input:focus, .input-card textarea:focus { border-color: #21b701; outline: none; }
        .button-card { background: #fff; border-radius: 10px; box-shadow: 0 2px 8px rgba(44,62,80,0.10); border: 1px solid #e0e0e0; padding: 18px 28px 18px 28px; display: flex; justify-content: center; align-items: center; }
        .submit-btn { width: 100%; background: #21b701; color: #fff; border-radius: 6px; font-size: 1.18rem; font-weight: 700; border: none; padding: 15px; transition: background 0.2s, color 0.2s; box-shadow: 0 2px 6px rgba(33,183,1,0.07); font-family: system-ui, Arial, sans-serif; }
        .submit-btn:hover { background: #43e97b; color: #232b3e; }
    </style>
</head>
<body>
    <div class="sidebar">
        <h2>Admin</h2>
        <ul>
            <li><a href="dashboard_admin.jsp"><i class="fa fa-chart-line"></i> Dashboards</a></li>
            <li><a href="addCustomer_admin.jsp"><i class="fa fa-user-plus"></i> Add Customer</a></li>
            <li><a href="editCustomer_admin.jsp"><i class="fa fa-user-edit"></i> Edit Customer</a></li>
            <li><a href="viewAccount.jsp"><i class="fa fa-id-card"></i> View Account</a></li>
            <li><a href="addItems_admin.jsp"><i class="fa fa-boxes"></i> Manage Items</a></li>
            <li><a href="calculateBill.jsp"><i class="fa fa-calculator"></i> Calculate Bill</a></li>
            <li><a href="printBill.jsp"><i class="fa fa-print"></i> Print/View Bills</a></li>
            <li><a href="help.jsp"><i class="fa fa-question-circle"></i> Help</a></li>
            <li><a href="useRoleManage_admin.jsp"><i class="fa fa-users-cog"></i> User Roles/Settings</a></li>
            <li><a href="logout"><i class="fa fa-sign-out-alt"></i> Logout</a></li>
        </ul>
    </div>
    <div class="main-content">
        <div style="display: flex; justify-content: flex-start; margin-bottom: 12px;">
            <a href="viewItems_admin.jsp" style="background:#1976d2; color:#fff; font-weight:600; border:none; border-radius:5px; padding:10px 22px; font-size:1.08em; text-decoration:none; transition:background 0.2s;">Back to View Items</a>
        </div>
<% String message = (String) request.getAttribute("message");
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
                <i class="fa fa-user-shield"></i> <span>Role: <%= user.getRole() %></span>
            </div>
        </div>
        <form class="form-area" action="UpdateItemServlet" method="post">
            <input type="hidden" name="id" value="<%= item.getId() %>" />
            <h2 style="text-align:center; color:#232b3e;">Edit Item</h2>
            <div class="input-card">
                <label for="name">Item Name</label>
                <input type="text" id="name" name="name" value="<%= item.getName() %>" required>
            </div>
            <div class="input-card">
                <label for="category">Category</label>
                <input type="text" id="category" name="category" value="<%= item.getCategory() %>" required>
            </div>
            <div class="input-card">
                <label for="description">Description</label>
                <textarea id="description" name="description" required><%= item.getDescription() %></textarea>
            </div>
            <div class="input-card">
                <label for="price">Price</label>
                <input type="number" id="price" name="price" step="0.01" min="0" value="<%= item.getPrice() %>" required>
            </div>
            <div class="input-card">
                <label for="stock">Stock</label>
                <input type="number" id="stock" name="stock" min="0" value="<%= item.getStock() %>" required>
            </div>
            <div class="button-card">
                <button type="submit" class="submit-btn">Save</button>
            </div>
        </form>
    </div>
</body>
</html> 