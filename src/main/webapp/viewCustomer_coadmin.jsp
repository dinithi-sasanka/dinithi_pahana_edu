<%@ page session="true" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.dinithi_pahana_edu.model.Customer" %>
<%
    com.example.dinithi_pahana_edu.model.User user = (com.example.dinithi_pahana_edu.model.User) session.getAttribute("user");
    if (user == null || !"coadmin".equalsIgnoreCase(user.getRole())) {
        response.sendRedirect("error.jsp");
        return;
    }
    List<Customer> customerList = (List<Customer>) request.getAttribute("customerList");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Customers - Coadmin</title>
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/styles.css">
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
        <div class="table-area">
            <h2 style="color:#232b3e;">All Customers</h2>
            <table>
                <thead>
                    <tr>
                        <th>Account Number</th>
                        <th>Name</th>
                        <th>Address</th>
                        <th>Telephone</th>
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
                    <tr><td colspan="6" style="text-align:center; color:#888;">No customers found.</td></tr>
                <% } %>
                </tbody>
            </table>
        </div>
        <div style="text-align:center; margin-top:24px;">
            <a href="addCustomer_coadmin.jsp" style="display:inline-block; background:#1976d2; color:#fff; font-weight:600; border:none; border-radius:8px; font-size:1.08em; text-decoration:none; padding:12px 32px; transition:background 0.2s; box-shadow:0 2px 8px #0001;">
                <i class="fa fa-arrow-left" style="margin-right:8px;"></i>Back
            </a>
        </div>
    </div>
</body>
</html> 