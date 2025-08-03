<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.dinithi_pahana_edu.model.Customer" %>
<%@ page import="com.example.dinithi_pahana_edu.model.Bill" %>
<%@ page import="com.example.dinithi_pahana_edu.model.BillItem" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    com.example.dinithi_pahana_edu.model.User user = (com.example.dinithi_pahana_edu.model.User) session.getAttribute("user");
    if (user == null || !"staff".equalsIgnoreCase(user.getRole())) {
        response.sendRedirect("error.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>View Customer Account (Staff)</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"/>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body { margin: 0; font-family: 'Roboto', Arial, sans-serif; background: linear-gradient(120deg, #232b3e, #1a2233); color: #d7dee5; min-height: 100vh; }

        .main-content { margin-left: 240px; padding: 40px 30px; background: #fff; min-height: 100vh; color: #232b3e; }
        .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px; }
        .header h1 { color: #232b3e; font-size: 2rem; margin: 0; }
        .user-info { font-size: 1.1em; color: #21b701; background: #27304a; padding: 8px 18px; border-radius: 20px; display: flex; align-items: center; gap: 10px; }

        .info-box { background: #f8f9fa; color: #232b3e; border: 1px solid #ccc; border-radius: 5px; padding: 10px; margin: 5px; display: inline-block; min-width: 180px; }
        .customer-details { background: #f8f9fa; color: #232b3e; padding: 20px; border-radius: 8px; margin-bottom: 20px; border: 1px solid #dee2e6; }
        .bill-table { background: #fff; color: #232b3e; }
        .bill-table th { background: #f8f9fa; color: #232b3e; border-color: #dee2e6; }
        .bill-table td { border-color: #dee2e6; }
        .bill-items-table { background: #f8f9fa; margin-top: 10px; }
        .bill-items-table th { background: #e9ecef; color: #232b3e; font-size: 0.9em; }
        .bill-items-table td { font-size: 0.9em; }
        .status-paid { color: #28a745; font-weight: bold; }
        .status-pending { color: #ffc107; font-weight: bold; }
        .status-overdue { color: #dc3545; font-weight: bold; }
        .expand-btn { background: none; border: none; color: #007bff; cursor: pointer; font-size: 0.9em; }
        .expand-btn:hover { color: #0056b3; }
        .bill-summary { background: #e9ecef; padding: 10px; border-radius: 5px; margin-top: 10px; }
        label, .form-group label { color: #232b3e !important; }
        .form-control { background: #fff !important; border-color: #dbeafe; }
        ::placeholder { color: #888 !important; opacity: 1; }
    </style>
</head>
<body>
    <jsp:include page="sidebar_staff.jspf" />
    <div class="main-content">
        <div class="container mt-4">
            <!-- Success/Error Message -->
            <% if (request.getAttribute("message") != null) { %>
                <div class="alert alert-info alert-dismissible fade show" role="alert">
                    <%= request.getAttribute("message") %>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            <% } %>
            
            <% if (request.getAttribute("error") != null) { %>
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <%= request.getAttribute("error") %>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            <% } %>
            
            <!-- Customer Search Section -->
            <div id="customer-search-section" <% if (request.getAttribute("customer") != null) { %> style="display:none;" <% } %>>
                <h4>Search Customer Account</h4>
                <div class="form-inline mb-3">
                    <input type="text" class="form-control mr-2" id="customerSearchBox" placeholder="Type phone, name, account number, etc."/>
                    <button type="button" class="btn btn-primary" id="loadCustomerBtn">Load Account</button>
                </div>
            </div>
            
            <!-- Customer Details Section -->
            <% if (request.getAttribute("customer") != null) { 
                Customer customer = (Customer) request.getAttribute("customer");
                List<Bill> bills = (List<Bill>) request.getAttribute("bills");
            %>
                <div class="customer-details">
                    <h5>Customer Details</h5>
                    <div class="d-flex flex-wrap">
                        <div class="info-box">ID: <span><%= customer.getId() %></span></div>
                        <div class="info-box">Account #: <span><%= customer.getAccountNumber() %></span></div>
                        <div class="info-box">Name: <span><%= customer.getName() %></span></div>
                        <div class="info-box">Address: <span><%= customer.getAddress() %></span></div>
                        <div class="info-box">Phone: <span><%= customer.getTelephone() %></span></div>
                <div class="info-box">Email: <span><%= customer.getEmail() != null ? customer.getEmail() : "" %></span></div>
                    </div>
                    <button class="btn btn-secondary mt-2" id="change-customer-btn">Change Customer</button>
                </div>
                
                <!-- Bills History Section -->
                <div class="bills-history">
                    <h4>Bills History</h4>
                    <% if (bills != null && !bills.isEmpty()) { %>
                        <div class="table-responsive">
                            <table class="table table-bordered bill-table">
                                <thead>
                                    <tr>
                                        <th>Bill #</th>
                                        <th>Date & Time</th>
                                        <th>Total Amount</th>
                                        <th>Paid Amount</th>
                                        <th>Balance</th>
                                        <th>Status</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% 
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    for (Bill bill : bills) { 
                                    %>
                                        <tr>
                                            <td><%= bill.getBillNumber() %></td>
                                            <td><%= bill.getBillDateTime() != null ? bill.getBillDateTime() : dateFormat.format(bill.getBillDate()) %></td>
                                            <td>Rs. <%= String.format("%.2f", bill.getTotalAmount()) %></td>
                                            <td>Rs. <%= String.format("%.2f", bill.getPaidAmount()) %></td>
                                            <td>Rs. <%= String.format("%.2f", bill.getBalance()) %></td>
                                            <td>
                                                <% if (bill.getBalance() <= 0) { %>
                                                    <span class="status-paid">Paid</span>
                                                <% } else if (bill.getPaidAmount() > 0) { %>
                                                    <span class="status-pending">Partial</span>
                                                <% } else { %>
                                                    <span class="status-overdue">Unpaid</span>
                                                <% } %>
                                            </td>
                                            <td>
                                                <button class="expand-btn" onclick="toggleBillItems(<%= bill.getId() %>)">
                                                    <i class="fa fa-eye"></i> View Items
                                                </button>
                                            </td>
                                        </tr>
                                        <!-- Bill Items Row -->
                                        <tr id="bill-items-<%= bill.getId() %>" style="display:none;">
                                            <td colspan="7">
                                                <div class="bill-summary">
                                                    <h6>Bill Items for Bill #<%= bill.getBillNumber() %></h6>
                                                    <% if (bill.getBillItems() != null && !bill.getBillItems().isEmpty()) { %>
                                                        <div class="table-responsive">
                                                            <table class="table table-sm bill-items-table">
                                                                <thead>
                                                                    <tr>
                                                                        <th>Item Name</th>
                                                                        <th>Quantity</th>
                                                                        <th>Unit Price</th>
                                                                        <th>Total</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    <% for (BillItem item : bill.getBillItems()) { %>
                                                                        <tr>
                                                                            <td><%= item.getItemName() != null ? item.getItemName() : "Item #" + item.getItemId() %></td>
                                                                            <td><%= item.getQuantity() %></td>
                                                                            <td>Rs. <%= String.format("%.2f", item.getPrice()) %></td>
                                                                            <td>Rs. <%= String.format("%.2f", item.getPrice() * item.getQuantity()) %></td>
                                                                        </tr>
                                                                    <% } %>
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                    <% } else { %>
                                                        <p class="text-muted">No items found for this bill.</p>
                                                    <% } %>
                                                </div>
                                            </td>
                                        </tr>
                                    <% } %>
                                </tbody>
                            </table>
                        </div>
                        
                        <!-- Summary Statistics -->
                        <div class="row mt-4">
                            <div class="col-md-3">
                                <div class="card bg-primary text-white">
                                    <div class="card-body">
                                        <h5 class="card-title">Total Bills</h5>
                                        <h3><%= bills.size() %></h3>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="card bg-success text-white">
                                    <div class="card-body">
                                        <h5 class="card-title">Total Amount</h5>
                                        <h3>Rs. <%= String.format("%.2f", bills.stream().mapToDouble(Bill::getTotalAmount).sum()) %></h3>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="card bg-info text-white">
                                    <div class="card-body">
                                        <h5 class="card-title">Total Paid</h5>
                                        <h3>Rs. <%= String.format("%.2f", bills.stream().mapToDouble(Bill::getPaidAmount).sum()) %></h3>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="card bg-warning text-white">
                                    <div class="card-body">
                                        <h5 class="card-title">Total Balance</h5>
                                        <h3>Rs. <%= String.format("%.2f", bills.stream().mapToDouble(Bill::getBalance).sum()) %></h3>
                                    </div>
                                </div>
                            </div>
                        </div>
                    <% } else { %>
                        <div class="alert alert-info">
                            <i class="fa fa-info-circle"></i> No bills found for this customer.
                        </div>
                    <% } %>
                </div>
            <% } %>
        </div>
    </div>

    <script>
    $(function() {
        // Show/hide customer sections
        $('#change-customer-btn').click(function() {
            $('#customer-search-section').show();
            location.reload(); // Reload to clear customer data
        });
        
        // Customer search functionality
        $('#loadCustomerBtn').click(function() {
            var searchTerm = $('#customerSearchBox').val();
            if (!searchTerm) { 
                alert('Please enter a search term.'); 
                return; 
            }
            
            // Redirect to viewCustomerAccount servlet with search parameter
            window.location.href = 'viewCustomerAccount?customerId=' + encodeURIComponent(searchTerm);
        });
        
        // Allow Enter key to trigger search
        $('#customerSearchBox').keypress(function(e) {
            if (e.which == 13) { // Enter key
                $('#loadCustomerBtn').click();
            }
        });
    });
    
    // Toggle bill items visibility
    function toggleBillItems(billId) {
        var itemsRow = $('#bill-items-' + billId);
        if (itemsRow.is(':visible')) {
            itemsRow.hide();
        } else {
            itemsRow.show();
        }
    }
    </script>
</body>
</html> 