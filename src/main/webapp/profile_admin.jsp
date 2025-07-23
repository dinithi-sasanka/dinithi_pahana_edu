<%@ page import="com.example.dinithi_pahana_edu.model.User" %>
<%
    User user = (User) request.getAttribute("user");
%>
<h2>Admin Profile Management</h2>
<form action="profile" method="post">
    Username: <input type="text" name="username" value="<%= user.getUsername() %>" required /><br/>
    Password: <input type="password" name="password" value="<%= user.getPassword() %>" required /><br/>
    Role: <input type="text" value="<%= user.getRole() %>" readonly /><br/>
    Email: <input type="email" name="email" value="<%= user.getEmail() %>" required /><br/>
    Telephone: <input type="text" name="telephone" value="<%= user.getTelephone() %>" required /><br/>
    <input type="submit" value="Save" />
</form> 