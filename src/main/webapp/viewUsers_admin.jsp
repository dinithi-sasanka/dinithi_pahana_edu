<%@ page session="true" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.dinithi_pahana_edu.model.User" %>
<%
    com.example.dinithi_pahana_edu.model.User user = (com.example.dinithi_pahana_edu.model.User) session.getAttribute("user");
    if (user == null || !"admin".equalsIgnoreCase(user.getRole())) {
        response.sendRedirect("error.jsp");
        return;
    }
    List<User> userList = (List<User>) request.getAttribute("userList");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Users - Admin</title>
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
        .action-btn { background: #21b701; color: #fff; border: none; border-radius: 5px; padding: 7px 16px; font-size: 1em; font-weight: 600; margin-right: 8px; cursor: pointer; transition: background 0.2s, color 0.2s; }
        .action-btn.edit { background: #007bff; }
        .action-btn.delete { background: #dc3545; }
        .action-btn.edit:hover { background: #0056b3; }
        .action-btn.delete:hover { background: #a71d2a; }
        .view-users-btn {
            background: #21b701;
            color: #fff;
            border-radius: 6px;
            font-size: 1.08rem;
            font-weight: 600;
            border: none;
            padding: 10px 22px;
            text-decoration: none;
            display: inline-block;
            transition: background 0.2s, color 0.2s;
            box-shadow: 0 2px 6px rgba(33,183,1,0.07);
            margin-bottom: 0;
        }
        .view-users-btn:hover {
            background: #43e97b;
            color: #232b3e;
            text-decoration: none;
        }
        @media (max-width: 800px) { .sidebar { width: 60px; padding: 20px 0 0 0; } .sidebar h2 { display: none; } .sidebar ul li a { padding: 10px 10px; font-size: 1.2em; text-align: center; } .main-content { margin-left: 70px; padding: 20px 10px; } .table-area { padding: 10px 2vw; } }
        @media (max-width: 600px) { .main-content { margin-left: 0; padding: 10px 2vw; } .sidebar { position: static; width: 100%; height: auto; flex-direction: row; } .sidebar ul { display: flex; flex-direction: row; justify-content: space-around; } .sidebar ul li { margin: 0; } .table-area { padding: 5px 1vw; } }
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
        <div style="display: flex; justify-content: flex-end; max-width: 1100px; margin: 0 auto 18px auto;">
            <a href="useRoleManage_admin.jsp" class="view-users-btn">
                <i class="fa fa-user-plus"></i> Add New User
            </a>
        </div>
        <div class="table-area">
            <h2 style="color:#232b3e;">All Users</h2>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Full Name</th>
                        <th>Username</th>
                        <th>Role</th>
                        <th>Email</th>
                        <th>Telephone</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                <% if (userList != null && !userList.isEmpty()) {
                    for (User u : userList) { %>
                    <tr>
                        <td><%= u.getId() %></td>
                        <td><%= u.getUseName() %></td>
                        <td><%= u.getUsername() %></td>
                        <td><%= u.getRole() %></td>
                        <td><%= u.getEmail() %></td>
                        <td><%= u.getTelephone() %></td>
                        <td>
                            <form action="EditUserServlet" method="get" style="display:inline;">
                                <input type="hidden" name="id" value="<%= u.getId() %>" />
                                <button type="submit" class="action-btn edit"><i class="fa fa-edit"></i> Edit</button>
                            </form>
                            <form action="DeleteUserServlet" method="post" style="display:inline;" onsubmit="return confirm('Are you sure you want to delete this user?');">
                                <input type="hidden" name="id" value="<%= u.getId() %>" />
                                <button type="submit" class="action-btn delete"><i class="fa fa-trash"></i> Delete</button>
                            </form>
                        </td>
                    </tr>
                <%  } 
                } else { %>
                    <tr><td colspan="7" style="text-align:center; color:#888;">No users found.</td></tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html> 