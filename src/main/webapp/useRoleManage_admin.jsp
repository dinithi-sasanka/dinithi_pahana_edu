<%@ page session="true" %>
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
    <title>User Role Management - Admin</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"/>
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
            background: #fff;
            min-height: 100vh;
            color: #232b3e;
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
        .form-area {
            max-width: 600px;
            margin: 0 auto;
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 2px 8px rgba(44,62,80,0.10);
            padding: 30px;
        }
        .input-card {
            margin-bottom: 20px;
        }
        .input-card label {
            color: #232b3e;
            font-size: 1.08rem;
            font-weight: 500;
            margin-bottom: 7px;
            display: block;
            font-family: system-ui, Arial, sans-serif;
        }
        .input-card input, .input-card select {
            width: 100%;
            background: #fff;
            color: #232b3e;
            border: 1.5px solid #bdbdbd;
            font-size: 1.13rem;
            padding: 12px 14px;
            border-radius: 6px;
            transition: border-color 0.2s;
            box-sizing: border-box;
            font-family: system-ui, Arial, sans-serif;
        }
        .input-card input:focus, .input-card select:focus {
            border-color: #21b701;
            outline: none;
        }
        .button-card {
            display: flex;
            justify-content: center;
            align-items: center;
            margin-top: 30px;
        }
        .submit-btn {
            background: #21b701;
            color: #fff;
            border: none;
            border-radius: 6px;
            font-size: 1.18rem;
            font-weight: 700;
            padding: 15px 40px;
            cursor: pointer;
            transition: background 0.2s;
            font-family: system-ui, Arial, sans-serif;
        }
        .submit-btn:hover {
            background: #1a9e01;
        }
        .message {
            padding: 12px;
            border-radius: 6px;
            margin-bottom: 20px;
            font-weight: 500;
        }
        .message.success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        .view-users-btn {
            background: #007bff;
            color: #fff;
            border: none;
            border-radius: 6px;
            font-size: 1.1rem;
            font-weight: 600;
            padding: 12px 24px;
            cursor: pointer;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
            gap: 8px;
            transition: background 0.2s;
        }
        .view-users-btn:hover {
            background: #0056b3;
            color: #fff;
            text-decoration: none;
        }
        .form-control {
            border-color: #dbeafe;
        }
        label {
            color: #232b3e !important;
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
        <div style="display: flex; justify-content: flex-end; max-width: 700px; margin: 0 auto 18px auto;">
            <a href="ViewUsersServlet" class="view-users-btn">
                <i class="fa fa-users"></i> View Users
            </a>
        </div>
        <div class="form-area">
            <h3 style="text-align: center; margin-bottom: 30px; color: #232b3e;">Add New User</h3>
            <form action="AddUserServlet" method="post">
                <% String message = (String) request.getAttribute("message");
                   if (message != null) { %>
                    <div class="message success"><%= message %></div>
                <% } %>
                <div class="input-card">
                    <label for="use_name">Full Name</label>
                    <input type="text" id="use_name" name="use_name" class="form-control" required />
                </div>
                <div class="input-card">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username" class="form-control" required />
                </div>
                <div class="input-card">
                    <label for="password">Password</label>
                    <div class="password-container">
                        <input type="password" id="password" name="password" class="form-control" required />
                        <button type="button" class="password-toggle" id="togglePassword">
                            <i class="fa fa-eye"></i>
                        </button>
                    </div>
                </div>
                <div class="input-card">
                    <label for="role">Role</label>
                    <select id="role" name="role" class="form-control" required>
                        <option value="" selected disabled>Select Role</option>
                        <option value="admin">Admin</option>
                        <option value="coadmin">Coadmin</option>
                        <option value="staff">Staff</option>
                    </select>
                </div>
                <div class="input-card">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" class="form-control" required />
                </div>
                <div class="input-card">
                    <label for="telephone">Telephone</label>
                    <input type="text" id="telephone" name="telephone" class="form-control" required />
                </div>
                <div class="button-card">
                    <button type="submit" class="submit-btn">
                        <i class="fa fa-save"></i> Save User
                    </button>
                </div>
            </form>
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
    </script>
</body>
</html> 