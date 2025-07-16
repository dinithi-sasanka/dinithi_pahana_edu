<%@ page session="true" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.dinithi_pahana_edu.model.Customer" %>
<%
    com.example.dinithi_pahana_edu.model.User user = (com.example.dinithi_pahana_edu.model.User) session.getAttribute("user");
    if (user == null || !"admin".equalsIgnoreCase(user.getRole())) {
        response.sendRedirect("error.jsp");
        return;
    }
    List<Customer> customerList = (List<Customer>) request.getAttribute("customerList");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Customers - Admin</title>
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
        @media (max-width: 800px) { .sidebar { width: 60px; padding: 20px 0 0 0; } .sidebar h2 { display: none; } .sidebar ul li a { padding: 10px 10px; font-size: 1.2em; text-align: center; } .main-content { margin-left: 70px; padding: 20px 10px; } .table-area { padding: 10px 2vw; } }
        @media (max-width: 600px) { .main-content { margin-left: 0; padding: 10px 2vw; } .sidebar { position: static; width: 100%; height: auto; flex-direction: row; } .sidebar ul { display: flex; flex-direction: row; justify-content: space-around; } .sidebar ul li { margin: 0; } .table-area { padding: 5px 1vw; } }
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
        <div class="header">
            <div>
                <h1>Pahana Edu Bookshop Management System</h1>
            </div>
            <div class="user-info">
                <i class="fa fa-user-shield"></i> <span>Role: <%= user.getRole() %></span>
            </div>
        </div>
        <div class="table-area">
            <h2 style="color:#232b3e;">All Customers</h2>
            <table>
                <thead>
                    <tr>
                        <th>Account Number</th>
                        <th>Name</th>
                        <th>Address</th>
                        <th>Telephone</th>
                        <th>Created At</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                <% if (customerList != null && !customerList.isEmpty()) {
                    for (Customer customer : customerList) { %>
                    <tr>
                        <td><%= customer.getAccountNumber() %></td>
                        <td><%= customer.getName() %></td>
                        <td><%= customer.getAddress() %></td>
                        <td><%= customer.getTelephone() %></td>
                        <td><%= customer.getCreatedAt() %></td>
                        <td>
                            <form action="DeleteCustomerServlet" method="post" style="display:inline;" onsubmit="return confirm('Are you sure to delete this customer?');">
                                <input type="hidden" name="id" value="<%= customer.getId() %>" />
                                <button type="submit" style="background:#e53935; color:#fff; border:none; border-radius:6px; padding:7px 18px; font-weight:600; cursor:pointer; font-size:1em;">
                                    <i class="fa fa-trash"></i> Delete
                                </button>
                            </form>
                        </td>
                    </tr>
                <%  } 
                } else { %>
                    <tr><td colspan="6" style="text-align:center; color:#888;">No customers found.</td></tr>
                <% } %>
                </tbody>
            </table>
        </div>
        <div style="text-align:center; margin-top:24px;">
            <a href="addCustomer_admin.jsp" style="display:inline-block; background:#1976d2; color:#fff; font-weight:600; border:none; border-radius:8px; font-size:1.08em; text-decoration:none; padding:12px 32px; transition:background 0.2s; box-shadow:0 2px 8px #0001;">
                <i class="fa fa-arrow-left" style="margin-right:8px;"></i>Back
            </a>
        </div>
    </div>
</body>
</html> 