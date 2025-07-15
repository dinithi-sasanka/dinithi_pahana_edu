<%@ page session="true" %>
<%
    com.example.dinithi_pahana_edu.model.User user = (com.example.dinithi_pahana_edu.model.User) session.getAttribute("user");
    if (user == null || !(user.getRole().equalsIgnoreCase("admin") ||
                        user.getRole().equalsIgnoreCase("coadmin") ||
                        user.getRole().equalsIgnoreCase("staff"))) {
        response.sendRedirect("error.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Coadmin Dashboard</title>
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,700&display=swap" rel="stylesheet">
    <style>
        body {
            margin: 0;
            font-family: 'Roboto', Arial, sans-serif;
            background: linear-gradient(120deg, #232b3e, #1a2233);
            color: #d7dee5;
            min-height: 100vh;
        }
        .sidebar {
            position: fixed;
            left: 0; top: 0; bottom: 0;
            width: 220px;
            background: #232b3e;
            padding: 30px 0 0 0;
            box-shadow: 2px 0 10px rgba(0,0,0,0.1);
            display: flex;
            flex-direction: column;
            z-index: 10;
        }
        .sidebar h2 {
            color: #fff;
            text-align: center;
            margin-bottom: 2rem;
            font-size: 1.7rem;
            letter-spacing: 1px;
        }
        .sidebar ul {
            list-style: none;
            padding: 0;
            margin: 0;
        }
        .sidebar ul li {
            margin: 18px 0;
        }
        .sidebar ul li a {
            color: #21b701;
            text-decoration: none;
            font-size: 1.1em;
            padding: 10px 30px;
            display: block;
            border-radius: 6px;
            transition: background 0.2s, color 0.2s, border-color 0.2s;
            border-left: 4px solid transparent;
        }
        .sidebar ul li a:hover {
            background: #fcfbfb;
            color: #232b3e;
            border-left: 4px solid #232b3e;
        }
        .sidebar ul li a i {
            color: #acacac;
            margin-right: 10px;
            transition: color 0.2s;
        }
        .sidebar ul li a:hover i {
            color: #21b701;
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
        .dashboard-cards {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
            gap: 24px;
        }
        .card {
            background: #27304a;
            border-radius: 14px;
            padding: 30px 20px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.08);
            text-align: center;
            transition: transform 0.2s, box-shadow 0.2s, background 0.2s, color 0.2s, border 0.2s;
            cursor: pointer;
            color: #fff;
            border: 2px solid transparent;
        }
        .card:hover {
            background: #21b701;
            color: #232b3e;
            border: 2px solid #21b701;
            box-shadow: 0 8px 32px rgba(33,183,1,0.10);
        }
        .card i {
            font-size: 2.5em;
            margin-bottom: 12px;
            color: #21b701;
            transition: color 0.2s;
        }
        .card:hover i {
            color: #fff;
        }
        @media (max-width: 800px) {
            .sidebar { width: 60px; padding: 20px 0 0 0; }
            .sidebar h2 { display: none; }
            .sidebar ul li a { padding: 10px 10px; font-size: 1.2em; text-align: center; }
            .main-content { margin-left: 70px; padding: 20px 10px; }
        }
        @media (max-width: 600px) {
            .main-content { margin-left: 0; padding: 10px 2vw; }
            .sidebar { position: static; width: 100%; height: auto; flex-direction: row; }
            .sidebar ul { display: flex; flex-direction: row; justify-content: space-around; }
            .sidebar ul li { margin: 0; }
        }
    </style>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <div class="sidebar">
        <h2>Coadmin</h2>
        <ul>
            <li><a href="dashboard_coadmin.jsp"><i class="fa fa-chart-line"></i> Dashboards</a></li>
            <li><a href="addCustomer_coadmin.jsp"><i class="fa fa-user-plus"></i> Add Customer</a></li>
            <li><a href="editCustomer_coadmin.jsp"><i class="fa fa-user-edit"></i> Edit Customer</a></li>
            <li><a href="viewAccount.jsp"><i class="fa fa-id-card"></i> View Account</a></li>
            <li><a href="addItems_admin.jsp"><i class="fa fa-boxes"></i> Manage Items</a></li>
            <li><a href="calculateBill.jsp"><i class="fa fa-calculator"></i> Calculate Bill</a></li>
            <li><a href="printBill.jsp"><i class="fa fa-print"></i> Print/View Bills</a></li>
            <li><a href="help.jsp"><i class="fa fa-question-circle"></i> Help</a></li>
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
        <div class="dashboard-cards">
            <div class="card" onclick="window.location.href='dashboard_coadmin.jsp'"><i class="fa fa-chart-line"></i><div>View Dashboards</div></div>
            <div class="card" onclick="location.href='addCustomer_coadmin.jsp'"><i class="fa fa-user-plus"></i><div>Add New Customer</div></div>
            <div class="card" onclick="location.href='editCustomer_coadmin.jsp'"><i class="fa fa-user-edit"></i><div>Edit Customer Info</div></div>
            <div class="card" onclick="location.href='viewAccount.jsp'"><i class="fa fa-id-card"></i><div>View Customer Account</div></div>
            <div class="card" onclick="location.href='addItems_admin.jsp'"><i class="fa fa-boxes"></i><div>Add/Update/Delete Items</div></div>
            <div class="card" onclick="location.href='calculateBill.jsp'"><i class="fa fa-calculator"></i><div>Calculate Bill</div></div>
            <div class="card" onclick="location.href='printBill.jsp'"><i class="fa fa-print"></i><div>Print/View Previous Bills</div></div>
            <div class="card" onclick="location.href='help.jsp'"><i class="fa fa-question-circle"></i><div>Help Section</div></div>
            <div class="card" onclick="location.href='logout'"><i class="fa fa-sign-out-alt"></i><div>Logout</div></div>
        </div>
    </div>
    <script>
        // Optional: Highlight active menu item or add more interactivity here
    </script>
</body>
</html> 