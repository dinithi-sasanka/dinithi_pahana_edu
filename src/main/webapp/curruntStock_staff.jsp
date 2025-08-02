<%@ page import="java.util.List" %>
<%@ page import="com.example.dinithi_pahana_edu.model.Item" %>
<%@ page session="true" %>
<%
    com.example.dinithi_pahana_edu.model.User user = (com.example.dinithi_pahana_edu.model.User) session.getAttribute("user");
    if (user == null || !"staff".equalsIgnoreCase(user.getRole())) {
        response.sendRedirect("error.jsp");
        return;
    }
    List<Item> items = (List<Item>) request.getAttribute("items");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Current Stock (Staff)</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"/>
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
        <div class="table-area">
            <h2 style="color:#232b3e;">Current Stock</h2>
            <table>
                <thead>
                    <tr>
                        <th>Item ID</th>
                        <th>Item Name</th>
                        <th>Stock</th>
                        <th>Current Stock</th>
                    </tr>
                </thead>
                <tbody>
                    <% if (items != null) {
                        for (Item item : items) { %>
                            <tr>
                                <td><%= item.getId() %></td>
                                <td><%= item.getName() %></td>
                                <td><%= item.getStock() %></td>
                                <td><%= item.getCurrentStock() %></td>
                            </tr>
                    <%  } 
                    } else { %>
                        <tr><td colspan="4">No items found.</td></tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html> 