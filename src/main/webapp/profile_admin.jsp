<%@ page session="true" %>
<%@ page import="com.example.dinithi_pahana_edu.model.User" %>
<%
    com.example.dinithi_pahana_edu.model.User user = (com.example.dinithi_pahana_edu.model.User) session.getAttribute("user");
    if (user == null || !"admin".equalsIgnoreCase(user.getRole())) {
        response.sendRedirect("error.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Profile Management</title>
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
        .password-container {
            position: relative;
            display: flex;
            align-items: center;
        }
        .password-toggle {
            position: absolute;
            right: 12px;
            top: 50%;
            transform: translateY(-50%);
            background: none;
            border: none;
            color: #666;
            cursor: pointer;
            font-size: 16px;
            padding: 5px;
            transition: color 0.3s ease;
        }
        .password-toggle:hover {
            color: #21b701;
        }
        .password-container input[type="password"],
        .password-container input[type="text"] {
            padding-right: 45px;
        }
    </style>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
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
        <div class="container">
            <div class="card shadow">
                <div class="profile-card-header">
                    Admin Profile Management
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
                            <div class="password-container">
                                <input type="password" id="password" name="password" value="<%= user.getPassword() %>" required class="form-control" />
                                <button type="button" class="password-toggle" id="togglePassword">
                                    <i class="fa fa-eye"></i>
                                </button>
                            </div>
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
        // Password toggle functionality
        const togglePassword = document.getElementById('togglePassword');
        const password = document.getElementById('password');
        const eyeIcon = togglePassword.querySelector('i');

        togglePassword.addEventListener('click', function() {
            const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
            password.setAttribute('type', type);
            
            // Toggle eye icon
            if (type === 'text') {
                eyeIcon.classList.remove('fa-eye');
                eyeIcon.classList.add('fa-eye-slash');
            } else {
                eyeIcon.classList.remove('fa-eye-slash');
                eyeIcon.classList.add('fa-eye');
            }
        });

        function confirmLogout() {
            if (confirm("Are you sure you want to logout from the system?")) {
                window.location.href = 'logout';
            }
        }
    </script>
</body>
</html> 