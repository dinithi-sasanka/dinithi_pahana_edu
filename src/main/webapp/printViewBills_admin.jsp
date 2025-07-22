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
<html>
<head>
    <title>All Bills (Admin)</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
</head>
<body>
    <jsp:include page="sidebar_admin.jspf" />
    <div class="main-content">
        <div class="container mt-5">
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
                        <tr><td colspan="6">No bills found.</td></tr>
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