<%@ page session="true" %>
<%@ page import="com.example.dinithi_pahana_edu.model.User" %>
<%
    com.example.dinithi_pahana_edu.model.User user = (com.example.dinithi_pahana_edu.model.User) session.getAttribute("user");
    if (user == null || !"admin".equalsIgnoreCase(user.getRole())) {
        response.sendRedirect("error.jsp");
        return;
    }
    User editUser = (User) request.getAttribute("editUser");
    if (editUser == null) {
        response.sendRedirect("ViewUsersServlet");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Users - Admin</title>
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
        <div style="display: flex; justify-content: center; align-items: flex-start; min-height: 60vh;">
            <form class="form-area" action="UpdateUserServlet" method="post">
                <input type="hidden" name="id" value="<%= editUser.getId() %>" />
                <div class="input-card">
                    <label for="use_name">Full Name</label>
                    <input type="text" id="use_name" name="use_name" value="<%= editUser.getUseName() %>" required />
                </div>
                <div class="input-card">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username" value="<%= editUser.getUsername() %>" required />
                </div>
                <div class="input-card">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" value="<%= editUser.getPassword() %>" required />
                </div>
                <div class="input-card">
                    <label for="role">Role</label>
                    <select id="role" name="role" required>
                        <option value="admin" <%= "admin".equals(editUser.getRole()) ? "selected" : "" %>>Admin</option>
                        <option value="coadmin" <%= "coadmin".equals(editUser.getRole()) ? "selected" : "" %>>Coadmin</option>
                        <option value="staff" <%= "staff".equals(editUser.getRole()) ? "selected" : "" %>>Staff</option>
                    </select>
                </div>
                <div class="input-card">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" value="<%= editUser.getEmail() %>" required />
                </div>
                <div class="input-card">
                    <label for="telephone">Telephone</label>
                    <input type="text" id="telephone" name="telephone" value="<%= editUser.getTelephone() %>" required />
                </div>
                <div class="button-card">
                    <button type="submit" class="submit-btn">Update User</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html> 