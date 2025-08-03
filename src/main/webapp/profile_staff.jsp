<%@ page session="true" %>
<%@ page import="com.example.dinithi_pahana_edu.model.User" %>
<%
    com.example.dinithi_pahana_edu.model.User user = (com.example.dinithi_pahana_edu.model.User) session.getAttribute("user");
    if (user == null || !"staff".equalsIgnoreCase(user.getRole())) {
        response.sendRedirect("error.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Staff Profile Management</title>
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,700&display=swap" rel="stylesheet">
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
        .profile-card-header {
            background: #232d3b;
            color: #fff;
            font-weight: bold;
            font-size: 1.3rem;
            padding: 1rem 1.5rem;
            border-top-left-radius: .25rem;
            border-top-right-radius: .25rem;
        }
        .container {
            max-width: 500px;
            margin: 40px auto;
        }
        .btn-custom-green {
            background-color: #21b701 !important;
            border-color: #21b701 !important;
            color: #fff !important;
        }
        .btn-custom-green:hover, .btn-custom-green:focus {
            background-color: #199900 !important;
            border-color: #199900 !important;
            color: #fff !important;
        }
    </style>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <jsp:include page="sidebar_staff.jspf" />
    <div class="main-content">
        <div class="header">
            <div>
                <h1>Pahana Edu Bookshop Management System</h1>
            </div>
            <div class="user-info">
                <i class="fa fa-user-shield"></i> <span>Role: <%= user.getRole() %></span>
            </div>
        </div>
        <div class="container">
            <div class="card shadow">
                <div class="profile-card-header">
                    Staff Profile Management
                </div>
                <div class="card-body">
                    <% if (request.getParameter("success") != null) { %>
                        <div class="alert alert-success">Profile updated successfully!</div>
                    <% } %>
                    <form action="profile" method="post">
                        <div class="form-group">
                            <label>Username</label>
                            <input type="text" name="username" value="<%= user.getUsername() %>" required class="form-control" />
                        </div>
                        <div class="form-group">
                            <label>Password</label>
                            <input type="password" name="password" value="<%= user.getPassword() %>" required class="form-control" />
                        </div>
                        <div class="form-group">
                            <label>Role</label>
                            <input type="text" value="<%= user.getRole() %>" readonly class="form-control-plaintext" />
                        </div>
                        <div class="form-group">
                            <label>Email</label>
                            <input type="email" name="email" value="<%= user.getEmail() %>" required class="form-control" />
                        </div>
                        <div class="form-group">
                            <label>Telephone</label>
                            <input type="text" name="telephone" value="<%= user.getTelephone() %>" required class="form-control" />
                        </div>
                        <button type="submit" class="btn btn-custom-green btn-block">Save</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <script>
        function confirmLogout() {
            if (confirm("Are you sure you want to logout from the system?")) {
                window.location.href = 'logout';
            }
        }
    </script>
</body>
</html> 