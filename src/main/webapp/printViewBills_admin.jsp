<%@ page import="java.util.List" %>
<%@ page import="com.example.dinithi_pahana_edu.model.Bill" %>
<%@ page session="true" %>
<%
    com.example.dinithi_pahana_edu.model.User user = (com.example.dinithi_pahana_edu.model.User) session.getAttribute("user");
    if (user == null || !"admin".equalsIgnoreCase(user.getRole())) {
        response.sendRedirect("error.jsp");
        return;
    }
    List<Bill> bills = (List<Bill>) request.getAttribute("bills");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>All Bills - Admin</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"/>
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
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
        .table {
            background: #fff;
        }
        .table th, .table td {
            color: #232b3e;
        }
        .form-control {
            border-color: #dbeafe;
        }
        .btn {
            border-radius: 4px;
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
        <div class="container mt-4">
            <h2>All Bills</h2>
            <form class="form-inline mb-3" method="get" action="viewBills">
                <input type="text" class="form-control mr-2" name="search" placeholder="Search bill number, customer name, or account" value="<%= request.getParameter("search") != null ? request.getParameter("search") : "" %>"/>
                <label class="mr-2 ml-2">From:</label>
                <input type="date" class="form-control mr-2" name="fromDate" value="<%= request.getParameter("fromDate") != null ? request.getParameter("fromDate") : "" %>"/>
                <label class="mr-2">To:</label>
                <input type="date" class="form-control mr-2" name="toDate" value="<%= request.getParameter("toDate") != null ? request.getParameter("toDate") : "" %>"/>
                <button type="submit" class="btn btn-primary mr-2">Filter</button>
                <a href="viewBills" class="btn btn-secondary">Reset</a>
            </form>
            <table class="table table-bordered table-striped mt-4">
                <thead class="thead-dark">
                    <tr>
                        <th>Bill Number</th>
                        <th>Date/Time</th>
                        <th>Customer ID</th>
                        <th>Total</th>
                        <th>Paid</th>
                        <th>Balance</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <% if (bills != null && !bills.isEmpty()) {
                        for (Bill bill : bills) { %>
                            <tr>
                                <td><%= bill.getBillNumber() %></td>
                                <td><%= bill.getBillDateTime() %></td>
                                <td><%= bill.getCustomerId() %></td>
                                <td><%= bill.getTotalAmount() %></td>
                                <td><%= bill.getPaidAmount() %></td>
                                <td><%= bill.getBalance() %></td>
                                <td>
                                  <button class="btn btn-primary btn-sm" onclick="printBill('<%= bill.getId() %>')">
                                    <i class="fa fa-print"></i> Print
                                  </button>
                                  <a class="btn btn-warning btn-sm" href="updateBill?billId=<%= bill.getId() %>">
                                    <i class="fa fa-edit"></i> Update
                                  </a>
                                  <button class="btn btn-danger btn-sm" onclick="deleteBillAjax('<%= bill.getId() %>', this)">
                                    <i class="fa fa-trash"></i> Delete
                                  </button>
                                </td>
                            </tr>
                    <%  } 
                    } else { %>
                        <tr><td colspan="7" style="text-align: center; color: #666; font-style: italic;">No bills found.</td></tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
    <script>
function printBill(billId) {
    window.open('printBill?billId=' + billId, '_blank', 'width=800,height=600');
}
function deleteBillAjax(billId, btn) {
  if(confirm('Are you sure you want to delete this bill?')) {
    $.ajax({
      url: 'deleteBill',
      type: 'POST',
      data: { billId: billId },
      success: function(response) {
        if(response.trim() === 'success') {
          $(btn).closest('tr').fadeOut(300, function() { $(this).remove(); });
        } else {
          alert('Failed to delete bill.');
        }
      },
      error: function() {
        alert('Error deleting bill.');
      }
    });
  }
}
</script>
</body>
</html> 