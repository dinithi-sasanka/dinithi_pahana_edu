<%@ page session="true" %>
<%
    com.example.dinithi_pahana_edu.model.User user = (com.example.dinithi_pahana_edu.model.User) session.getAttribute("user");
    if (user == null || !"coadmin".equalsIgnoreCase(user.getRole())) {
        response.sendRedirect("error.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Customer - Coadmin</title>
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
        .form-area {
            display: flex;
            flex-direction: column;
            gap: 22px;
            max-width: 700px;
            width: 100%;
            margin: 0 auto;
        }
        .input-card {
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 2px 8px rgba(44,62,80,0.10);
            border: 1px solid #e0e0e0;
            padding: 22px 28px 16px 28px;
            display: flex;
            flex-direction: column;
            width: 100%;
        }
        .input-card label {
            color: #232b3e;
            font-size: 1.08rem;
            font-weight: 500;
            margin-bottom: 7px;
            font-family: system-ui, Arial, sans-serif;
        }
        .input-card input, .input-card textarea {
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
        .input-card textarea {
            min-height: 50px;
            resize: vertical;
        }
        .input-card input:focus, .input-card textarea:focus {
            border-color: #21b701;
            outline: none;
        }
        .button-card {
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 2px 8px rgba(44,62,80,0.10);
            border: 1px solid #e0e0e0;
            padding: 18px 28px 18px 28px;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .submit-btn {
            width: 100%;
            background: #21b701;
            color: #fff;
            border-radius: 6px;
            font-size: 1.18rem;
            font-weight: 700;
            border: none;
            padding: 15px;
            transition: background 0.2s, color 0.2s;
            box-shadow: 0 2px 6px rgba(33,183,1,0.07);
            font-family: system-ui, Arial, sans-serif;
        }
        .submit-btn:hover {
            background: #43e97b;
            color: #232b3e;
        }
        .search-section {
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 2px 8px rgba(44,62,80,0.10);
            border: 1px solid #e0e0e0;
            padding: 22px 28px 16px 28px;
            margin-bottom: 20px;
        }
        .search-section h3 {
            color: #232b3e;
            font-size: 1.3rem;
            font-weight: 600;
            margin-bottom: 20px;
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .search-section h3 i {
            color: #21b701;
        }
        .search-row {
            display: flex;
            flex-wrap: wrap;
            gap: 15px;
            align-items: flex-end;
        }
        .search-input {
            flex: 1;
            min-width: 200px;
        }
        .search-input label {
            color: #232b3e;
            font-size: 1.08rem;
            font-weight: 500;
            margin-bottom: 7px;
            display: block;
            font-family: system-ui, Arial, sans-serif;
        }
        .search-input input {
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
        .search-input input:focus {
            border-color: #21b701;
            outline: none;
        }
        .search-input input::placeholder {
            color: #666;
            opacity: 1;
        }
        .search-btn {
            background: #21b701;
            color: #fff;
            border: none;
            border-radius: 6px;
            font-size: 1.1rem;
            font-weight: 600;
            padding: 12px 24px;
            cursor: pointer;
            transition: background 0.2s;
            display: flex;
            align-items: center;
            gap: 8px;
            white-space: nowrap;
        }
        .search-btn:hover {
            background: #1a9e01;
        }
        .message {
            padding: 12px;
            border-radius: 6px;
            font-weight: 500;
            margin-bottom: 20px;
        }
        .message.success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        .message.error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
    </style>
</head>
<body>
    <jsp:include page="sidebar_coadmin.jspf" />
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
            <div class="form-area">
                <!-- Search Section -->
                <div class="search-section">
                    <h3><i class="fa fa-search"></i> Search Customer</h3>
                    <form action="searchCustomer" method="get">
                        <div class="search-row">
                            <div class="search-input">
                                <label for="searchAccountNumber">Account Number</label>
                                <input type="text" id="searchAccountNumber" name="searchAccountNumber" 
                                       value="<%= request.getAttribute("searchAccountNumber") != null ? request.getAttribute("searchAccountNumber") : "" %>"
                                       placeholder="Enter account number to search">
                            </div>
                            <div class="search-input">
                                <label for="searchName">Customer Name</label>
                                <input type="text" id="searchName" name="searchName" 
                                       value="<%= request.getAttribute("searchName") != null ? request.getAttribute("searchName") : "" %>"
                                       placeholder="Enter customer name to search">
                            </div>
                            <div class="search-input">
                                <label for="searchTelephone">Telephone Number</label>
                                <input type="text" id="searchTelephone" name="searchTelephone" 
                                       value="<%= request.getAttribute("searchTelephone") != null ? request.getAttribute("searchTelephone") : "" %>"
                                       placeholder="Enter telephone number to search">
                            </div>
                            <div class="search-input">
                                <label for="searchEmail">Email Address</label>
                                <input type="email" id="searchEmail" name="searchEmail" 
                                       value="<%= request.getAttribute("searchEmail") != null ? request.getAttribute("searchEmail") : "" %>"
                                       placeholder="Enter email address to search">
                            </div>
                            <button type="submit" class="search-btn">
                                <i class="fa fa-search"></i> Search
                            </button>
                        </div>
                    </form>
                </div>

                <!-- Message Display -->
                <% if (request.getAttribute("message") != null) { %>
                    <div class="message <%= request.getAttribute("messageType") %>" style="margin-bottom: 20px; padding: 12px; border-radius: 6px; font-weight: 500;">
                        <%= request.getAttribute("message") %>
                    </div>
                <% } %>

                <!-- Edit Customer Form -->
                <form action="updateCustomer" method="post">
                    <div class="input-card">
                        <label for="accountNumber">Account Number</label>
                        <input type="text" id="accountNumber" name="accountNumber" value="<%= request.getAttribute("accountNumber") != null ? request.getAttribute("accountNumber") : "" %>" required>
                    </div>
                    <div class="input-card">
                        <label for="name">Name</label>
                        <input type="text" id="name" name="name" value="<%= request.getAttribute("name") != null ? request.getAttribute("name") : "" %>" required>
                    </div>
                    <div class="input-card">
                        <label for="address">Address</label>
                        <textarea id="address" name="address" required><%= request.getAttribute("address") != null ? request.getAttribute("address") : "" %></textarea>
                    </div>
                    <div class="input-card">
                        <label for="telephone">Telephone Number</label>
                        <input type="text" id="telephone" name="telephone" value="<%= request.getAttribute("telephone") != null ? request.getAttribute("telephone") : "" %>" required>
                    </div>
                    <div class="input-card">
                        <label for="email">Email Address</label>
                        <input type="email" id="email" name="email" value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>" placeholder="customer@example.com">
                    </div>
                    <div class="button-card">
                        <button type="submit" class="submit-btn">
                            <i class="fa fa-save"></i> Update Customer
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html> 