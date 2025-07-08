<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <style>
        body {
            background: #f0f2f5;
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .login-card {
            background: #fff;
            padding: 2rem 2.5rem;
            border-radius: 10px;
            box-shadow: 0 4px 24px rgba(0,0,0,0.10);
            min-width: 320px;
        }
        .login-card h2 {
            margin-bottom: 1.5rem;
            color: #333;
            text-align: center;
        }
        .login-card label {
            display: block;
            margin-bottom: 0.5rem;
            color: #555;
        }
        .login-card input[type="text"],
        .login-card input[type="password"] {
            width: 100%;
            padding: 0.5rem;
            margin-bottom: 1rem;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 1rem;
        }
        .login-card input[type="submit"] {
            width: 100%;
            padding: 0.7rem;
            background: #1976d2;
            color: #fff;
            border: none;
            border-radius: 5px;
            font-size: 1rem;
            cursor: pointer;
            transition: background 0.2s;
        }
        .login-card input[type="submit"]:hover {
            background: #1565c0;
        }
        .error-message {
            color: #d32f2f;
            text-align: center;
            margin-bottom: 1rem;
        }
    </style>
</head>
<body>
<div class="login-card">
    <h2>Login</h2>
    <form action="login" method="post">
        <label for="username">Username</label>
        <input type="text" id="username" name="username" required>
        <label for="password">Password</label>
        <div style="position: relative;">
            <input type="password" id="password" name="password" required style="padding-right: 2.5rem;">
            <i class="fa fa-eye" id="togglePassword" style="position: absolute; right: 10px; top: 50%; transform: translateY(-50%); cursor: pointer;"></i>
        </div>
        <input type="submit" value="Login">
    </form>
    <c:if test="${not empty error}">
        <p class="error-message">${error}</p>
    </c:if>
</div>
<!-- Font Awesome CDN for eye icon -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<script>
    const togglePassword = document.addEventListener('DOMContentLoaded', function() {
        const toggle = document.getElementById('togglePassword');
        const password = document.getElementById('password');
        toggle.addEventListener('click', function () {
            const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
            password.setAttribute('type', type);
            this.classList.toggle('fa-eye-slash');
        });
    });
</script>
</body>
</html> 