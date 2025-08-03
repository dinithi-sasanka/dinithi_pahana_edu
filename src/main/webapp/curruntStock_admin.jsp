<%@ page import="java.util.List" %>
<%@ page import="com.example.dinithi_pahana_edu.model.Item" %>
<%@ page session="true" %>
<%
    com.example.dinithi_pahana_edu.model.User user = (com.example.dinithi_pahana_edu.model.User) session.getAttribute("user");
    if (user == null || !"admin".equalsIgnoreCase(user.getRole())) {
        response.sendRedirect("error.jsp");
        return;
    }
    List<Item> items = (List<Item>) request.getAttribute("items");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Current Stock (Admin)</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"/>
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
        .table {
            background: #fff !important;
        }
        .table th, .table td {
            color: #232b3e !important;
        }
        .thead-dark th {
            background-color: #343a40 !important;
            color: #fff !important;
            border-color: #454d55 !important;
        }
    </style>
</head>
<body>
    <jsp:include page="sidebar_admin.jspf" />
    <div class="main-content">
        <div class="container mt-5">
            <h2>Current Stock</h2>
            <table class="table table-bordered table-striped mt-4">
                <thead class="thead-dark">
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