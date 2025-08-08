<%@ page session="true" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.dinithi_pahana_edu.model.Customer" %>
<%
    com.example.dinithi_pahana_edu.model.User user = (com.example.dinithi_pahana_edu.model.User) session.getAttribute("user");
    if (user == null || !"staff".equalsIgnoreCase(user.getRole())) {
        response.sendRedirect("error.jsp");
        return;
    }
    List<Customer> customerList = (List<Customer>) request.getAttribute("customerList");
    String searchTerm = (String) request.getAttribute("searchTerm");
    String searchType = (String) request.getAttribute("searchType");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Customers - Staff</title>
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body { margin: 0; font-family: 'Roboto', Arial, sans-serif; background: linear-gradient(120deg, #232b3e, #1a2233); color: #d7dee5; min-height: 100vh; }
        .main-content { margin-left: 240px; padding: 40px 30px; background: #ffffffe7; min-height: 100vh; }
        .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px; }
        .header h1 { color: #232b3e; font-size: 2rem; margin: 0; }
        .user-info { font-size: 1.1em; color: #21b701; background: #27304a; padding: 8px 18px; border-radius: 20px; display: flex; align-items: center; gap: 10px; }
        .table-area { max-width: 1100px; margin: 0 auto; background: #fff; border-radius: 12px; box-shadow: 0 2px 8px rgba(44,62,80,0.10); padding: 30px 30px 20px 30px; }
        table { width: 100%; border-collapse: collapse; margin-top: 10px; background: #fff; }
        th, td { padding: 12px 10px; text-align: left; border-bottom: 1px solid #e0e0e0; color: #232b3e; }
        th { background: #f4f4f4; font-weight: 700; }
        tr:last-child td { border-bottom: none; }
        .search-section {
            max-width: 1100px;
            margin: 0 auto 20px auto;
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 2px 8px rgba(44,62,80,0.10);
            padding: 20px;
        }
        .search-form {
            display: flex;
            gap: 10px;
            align-items: center;
            flex-wrap: wrap;
        }
        .search-input {
            flex: 1;
            min-width: 200px;
            padding: 10px 15px;
            border: 2px solid #e0e0e0;
            border-radius: 6px;
            font-size: 14px;
            transition: border-color 0.3s ease;
        }
        .search-input:focus {
            outline: none;
            border-color: #21b701;
        }
        .search-select {
            padding: 10px 15px;
            border: 2px solid #e0e0e0;
            border-radius: 6px;
            font-size: 14px;
            background: #fff;
            min-width: 150px;
        }
        .search-btn {
            background: #21b701;
            color: #fff;
            border: none;
            border-radius: 6px;
            padding: 10px 20px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            transition: background 0.3s;
        }
        .search-btn:hover {
            background: #43e97b;
        }
        .clear-btn {
            background: #6c757d;
            color: #fff;
            border: none;
            border-radius: 6px;
            padding: 10px 20px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            transition: background 0.3s;
            text-decoration: none;
        }
        .clear-btn:hover {
            background: #5a6268;
            color: #fff;
            text-decoration: none;
        }
        .search-results-info {
            margin-bottom: 15px;
            color: #232b3e;
            font-size: 14px;
        }
        @media (max-width: 800px) { .main-content { margin-left: 70px; padding: 20px 10px; } .table-area { padding: 10px 2vw; } }
        @media (max-width: 600px) { .main-content { margin-left: 0; padding: 10px 2vw; } .table-area { padding: 5px 1vw; } }
    </style>
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
        
        <!-- Search Section -->
        <div class="search-section">
            <h2 style="color:#232b3e;">Search Customers</h2>
            <form action="ViewCustomerServlet" method="get" class="search-form">
                <input type="text" name="searchTerm" value="<%= searchTerm != null ? searchTerm : "" %>" placeholder="Search customers..." class="search-input">
                <select name="searchType" class="search-select">
                    <option value="all" <%= "all".equals(searchType) || searchType == null ? "selected" : "" %>>All Fields</option>
                    <option value="name" <%= "name".equals(searchType) ? "selected" : "" %>>Name</option>
                    <option value="accountNumber" <%= "accountNumber".equals(searchType) ? "selected" : "" %>>Account Number</option>
                    <option value="email" <%= "email".equals(searchType) ? "selected" : "" %>>Email</option>
                    <option value="telephone" <%= "telephone".equals(searchType) ? "selected" : "" %>>Telephone</option>
                    <option value="address" <%= "address".equals(searchType) ? "selected" : "" %>>Address</option>
                </select>
                <button type="submit" class="search-btn">
                    <i class="fa fa-search"></i> Search
                </button>
                <a href="ViewCustomerServlet" class="clear-btn">
                    <i class="fa fa-times"></i> Clear
                </a>
            </form>
        </div>
        
        <div class="table-area">
            <h2 style="color:#232b3e;">All Customers</h2>
            
            <!-- Search Results Info -->
            <% if (searchTerm != null && !searchTerm.trim().isEmpty()) { %>
                <div class="search-results-info">
                    <i class="fa fa-search"></i> 
                    Search results for "<strong><%= searchTerm %></strong>" 
                    <% if (searchType != null && !"all".equals(searchType)) { %>
                        in <strong><%= searchType %></strong>
                    <% } %>
                    - Found <strong><%= customerList != null ? customerList.size() : 0 %></strong> customer(s)
                </div>
            <% } %>
            
            <table>
                <thead>
                    <tr>
                        <th>Account Number</th>
                        <th>Name</th>
                        <th>Address</th>
                        <th>Telephone</th>
                        <th>Email</th>
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
                        <td><%= customer.getEmail() != null ? customer.getEmail() : "" %></td>
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
                    <tr><td colspan="7" style="text-align:center; color:#888;">
                        <% if (searchTerm != null && !searchTerm.trim().isEmpty()) { %>
                            No customers found matching your search criteria.
                        <% } else { %>
                            No customers found.
                        <% } %>
                    </td></tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html> 