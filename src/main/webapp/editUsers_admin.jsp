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
        .form-container {
            max-width: 600px;
            margin: 0 auto;
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 4px 20px rgba(44,62,80,0.15);
            padding: 40px;
        }
        .form-title {
            text-align: center;
            color: #232b3e;
            font-size: 1.8rem;
            font-weight: 700;
            margin-bottom: 30px;
            border-bottom: 2px solid #21b701;
            padding-bottom: 15px;
        }
        .form-area {
            display: flex;
            flex-direction: column;
            gap: 20px;
        }
        .input-card {
            display: flex;
            flex-direction: column;
            gap: 8px;
        }
        .input-card label {
            color: #232b3e;
            font-weight: 600;
            font-size: 14px;
            margin-bottom: 5px;
        }
        .input-card input,
        .input-card select {
            padding: 12px 15px;
            border: 2px solid #e0e0e0;
            border-radius: 8px;
            font-size: 14px;
            transition: all 0.3s ease;
            background: #fff;
            color: #232b3e;
        }
        .input-card input:focus,
        .input-card select:focus {
            outline: none;
            border-color: #21b701;
            box-shadow: 0 0 0 3px rgba(33, 183, 1, 0.1);
        }
        .input-card input:hover,
        .input-card select:hover {
            border-color: #21b701;
        }
        .input-card input[type="password"] {
            font-family: monospace;
        }
        .button-card {
            margin-top: 20px;
            display: flex;
            gap: 15px;
            justify-content: center;
        }
        .submit-btn {
            background: #21b701;
            color: #fff;
            border: none;
            border-radius: 8px;
            padding: 12px 30px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            box-shadow: 0 2px 8px rgba(33, 183, 1, 0.3);
        }
        .submit-btn:hover {
            background: #43e97b;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(33, 183, 1, 0.4);
        }
        .submit-btn:active {
            transform: translateY(0);
        }
        .cancel-btn {
            background: #6c757d;
            color: #fff;
            border: none;
            border-radius: 8px;
            padding: 12px 30px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            text-decoration: none;
            display: inline-block;
        }
        .cancel-btn:hover {
            background: #5a6268;
            transform: translateY(-2px);
        }
        .form-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
        }
        .full-width {
            grid-column: 1 / -1;
        }
        .back-link {
            display: inline-flex;
            align-items: center;
            gap: 8px;
            color: #21b701;
            text-decoration: none;
            font-weight: 600;
            margin-bottom: 20px;
            transition: color 0.3s ease;
        }
        .back-link:hover {
            color: #43e97b;
        }
        .error-message {
            background: #f8d7da;
            color: #721c24;
            padding: 12px;
            border-radius: 6px;
            margin-bottom: 20px;
            border: 1px solid #f5c6cb;
        }
        .success-message {
            background: #d4edda;
            color: #155724;
            padding: 12px;
            border-radius: 6px;
            margin-bottom: 20px;
            border: 1px solid #c3e6cb;
        }
        @media (max-width: 768px) {
            .main-content {
                margin-left: 0;
                padding: 20px 15px;
            }
            .form-container {
                padding: 25px;
                margin: 0 10px;
            }
            .form-row {
                grid-template-columns: 1fr;
            }
            .header h1 {
                font-size: 1.5rem;
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
        
        <div class="form-container">
            <a href="ViewUsersServlet" class="back-link">
                <i class="fa fa-arrow-left"></i> Back to Users List
            </a>
            
            <h2 class="form-title">
                <i class="fa fa-user-edit"></i> Edit User
            </h2>
            
            <!-- Display error/success messages if any -->
            <% if (request.getAttribute("error") != null) { %>
                <div class="error-message">
                    <i class="fa fa-exclamation-triangle"></i> <%= request.getAttribute("error") %>
                </div>
            <% } %>
            <% if (request.getAttribute("success") != null) { %>
                <div class="success-message">
                    <i class="fa fa-check-circle"></i> <%= request.getAttribute("success") %>
                </div>
            <% } %>
            
            <form class="form-area" action="UpdateUserServlet" method="post">
                <input type="hidden" name="id" value="<%= editUser.getId() %>" />
                
                <div class="form-row">
                    <div class="input-card">
                        <label for="use_name">
                            <i class="fa fa-user"></i> Full Name
                        </label>
                        <input type="text" id="use_name" name="use_name" value="<%= editUser.getUseName() %>" required />
                    </div>
                    <div class="input-card">
                        <label for="username">
                            <i class="fa fa-at"></i> Username
                        </label>
                        <input type="text" id="username" name="username" value="<%= editUser.getUsername() %>" required />
                    </div>
                </div>
                
                <div class="form-row">
                    <div class="input-card">
                        <label for="password">
                            <i class="fa fa-lock"></i> Password
                        </label>
                        <input type="password" id="password" name="password" value="<%= editUser.getPassword() %>" required />
                    </div>
                    <div class="input-card">
                        <label for="role">
                            <i class="fa fa-user-tag"></i> Role
                        </label>
                        <select id="role" name="role" required>
                            <option value="admin" <%= "admin".equals(editUser.getRole()) ? "selected" : "" %>>Admin</option>
                            <option value="coadmin" <%= "coadmin".equals(editUser.getRole()) ? "selected" : "" %>>Coadmin</option>
                            <option value="staff" <%= "staff".equals(editUser.getRole()) ? "selected" : "" %>>Staff</option>
                        </select>
                    </div>
                </div>
                
                <div class="form-row">
                    <div class="input-card">
                        <label for="email">
                            <i class="fa fa-envelope"></i> Email
                        </label>
                        <input type="email" id="email" name="email" value="<%= editUser.getEmail() %>" required />
                    </div>
                    <div class="input-card">
                        <label for="telephone">
                            <i class="fa fa-phone"></i> Telephone
                        </label>
                        <input type="text" id="telephone" name="telephone" value="<%= editUser.getTelephone() %>" required />
                    </div>
                </div>
                
                <div class="button-card">
                    <a href="ViewUsersServlet" class="cancel-btn">
                        <i class="fa fa-times"></i> Cancel
                    </a>
                    <button type="submit" class="submit-btn">
                        <i class="fa fa-save"></i> Update User
                    </button>
                </div>
            </form>
        </div>
    </div>
</body>
</html> 