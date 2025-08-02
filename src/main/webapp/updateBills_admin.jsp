<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.dinithi_pahana_edu.model.Item" %>
<%@ page import="com.example.dinithi_pahana_edu.model.Bill" %>
<%@ page import="com.example.dinithi_pahana_edu.model.BillItem" %>
<%@ page import="com.example.dinithi_pahana_edu.model.Customer" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page session="true" %>
<%
    com.example.dinithi_pahana_edu.model.User user = (com.example.dinithi_pahana_edu.model.User) session.getAttribute("user");
    if (user == null || !"admin".equalsIgnoreCase(user.getRole())) {
        response.sendRedirect("error.jsp");
        return;
    }
%>
<%-- This page is for updating an existing bill. All fields are pre-filled. --%>
<% Bill bill = (Bill) request.getAttribute("bill");
   Customer customer = (Customer) request.getAttribute("customer");
   List<BillItem> billItems = (List<BillItem>) request.getAttribute("billItems");
   List<Item> items = (List<Item>) request.getAttribute("items");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Bill - Admin</title>
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
            max-width: 1200px;
            margin: 0 auto;
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 2px 8px rgba(44,62,80,0.10);
            padding: 30px;
        }
        .card {
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(44,62,80,0.10);
            border: 1px solid #e0e0e0;
            padding: 20px;
            margin-bottom: 20px;
        }
        .form-control {
            width: 100%;
            background: #fff;
            color: #232b3e;
            border: 1.5px solid #bdbdbd;
            font-size: 1.13rem;
            padding: 12px 14px;
            border-radius: 6px;
            transition: border-color 0.2s;
            box-sizing: border-box;
        }
        .form-control:focus {
            border-color: #21b701;
            outline: none;
        }
        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 6px;
            font-size: 1rem;
            font-weight: 600;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            transition: background 0.2s;
        }
        .btn-primary {
            background: #007bff;
            color: white;
        }
        .btn-success {
            background: #21b701;
            color: white;
        }
        .btn-danger {
            background: #dc3545;
            color: white;
        }
        .btn-outline-primary {
            background: transparent;
            color: #007bff;
            border: 1px solid #007bff;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
            background: #fff;
        }
        th, td {
            padding: 12px 10px;
            text-align: left;
            border-bottom: 1px solid #e0e0e0;
            color: #232b3e;
        }
        th {
            background: #f4f4f4;
            font-weight: 700;
        }
        .alert {
            padding: 15px;
            border-radius: 6px;
            margin-bottom: 20px;
        }
        .alert-info {
            background: #d1ecf1;
            color: #0c5460;
            border: 1px solid #bee5eb;
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
        <div class="form-area">
    <% if (request.getAttribute("message") != null) { %>
        <div class="alert alert-info alert-dismissible fade show" role="alert">
            <%= request.getAttribute("message") %>
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    <% } %>
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3>Update Bill</h3>
        <a href="printViewBills_admin.jsp" class="btn btn-outline-primary"><i class="fa fa-arrow-left"></i> Back to View Bills</a>
    </div>
    <!-- Customer Info Boxes -->
    <div id="customer-info-section" class="card mb-4" style="max-width: 600px;">
        <div class="card-body">
            <h5 class="card-title mb-3">Customer Details</h5>
            <div class="row">
                <div class="col-md-6 mb-2"><strong>ID:</strong> <span id="info-id"><%= customer != null ? customer.getId() : "" %></span></div>
                <div class="col-md-6 mb-2"><strong>Account #:</strong> <span id="info-account"><%= customer != null ? customer.getAccountNumber() : "" %></span></div>
                <div class="col-md-12 mb-2"><strong>Name:</strong> <span id="info-name"><%= customer != null ? customer.getName() : "" %></span></div>
                <div class="col-md-12 mb-2"><strong>Address:</strong> <span id="info-address"><%= customer != null ? customer.getAddress() : "" %></span></div>
                <div class="col-md-12 mb-2"><strong>Phone:</strong> <span id="info-phone"><%= customer != null ? customer.getTelephone() : "" %></span></div>
            </div>
        </div>
    </div>
    <form id="bill-form" method="post" action="updateBill">
        <input type="hidden" name="billId" value="<%= bill != null ? bill.getId() : "" %>"/>
        <input type="hidden" name="customerId" id="customerId" value="<%= customer != null ? customer.getId() : "" %>"/>
        <!-- Bill Header Information -->
        <div class="bill-header" id="bill-header-section">
            <h5>Bill Information</h5>
            <div class="row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="billNumber">Bill Number:</label>
                        <input type="text" class="form-control" id="billNumber" name="billNumber" required value="<%= bill != null ? bill.getBillNumber() : "" %>" readonly/>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="billDateTimeDisplay">Bill Date & Time:</label>
                        <input type="text" class="form-control" id="billDateTimeDisplay" readonly value="<%= bill != null ? bill.getBillDateTime() : "" %>"/>
                        <input type="hidden" id="billDateTime" name="billDateTime" value="<%= bill != null ? bill.getBillDateTime() : "" %>"/>
                    </div>
                </div>
            </div>
        </div>
        <h4 class="mt-4">Add Items</h4>
        <table class="table table-bordered" id="items-table">
            <thead>
                <tr>
                    <th>Item</th>
                    <th>Quantity</th>
                    <th>Unit Price</th>
                    <th>Total</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <% if (billItems != null && !billItems.isEmpty()) {
                    for (BillItem billItem : billItems) { %>
                        <tr>
                            <td>
                                <select class="form-control item-select" name="itemId[]">
                                    <option value="">Select Item</option>
                                    <% if (items != null) {
                                        for (Item item : items) { %>
                                            <option value="<%= item.getId() %>" data-price="<%= item.getPrice() %>" <%= billItem.getItemId() == item.getId() ? "selected" : "" %>><%= item.getName() %></option>
                                    <%  } } %>
                                </select>
                            </td>
                            <td><input type="number" class="form-control qty-input" name="quantity[]" min="1" value="<%= billItem.getQuantity() %>"/></td>
                            <td><input type="text" class="form-control unit-price" name="unitPrice[]" readonly value="<%= billItem.getPrice() %>"/></td>
                            <td><input type="text" class="form-control total-price" name="totalPrice[]" readonly value="<%= billItem.getPrice() * billItem.getQuantity() %>"/></td>
                            <td>
                                <div style="display: flex; align-items: center;">
                                    <button type="button" class="btn btn-outline-success btn-sm add-row mx-1" title="Add Item"><i class="fa fa-plus-circle"></i></button>
                                    <button type="button" class="btn btn-outline-danger btn-sm remove-row mx-1" title="Remove Item"><i class="fa fa-trash"></i></button>
                                </div>
                            </td>
                        </tr>
                <%  } 
                } else { %>
                    <tr>
                        <td>
                            <select class="form-control item-select" name="itemId[]">
                                <option value="">Select Item</option>
                                <% if (items != null) {
                                    for (Item item : items) { %>
                                        <option value="<%= item.getId() %>" data-price="<%= item.getPrice() %>"><%= item.getName() %></option>
                                <%  } } %>
                            </select>
                        </td>
                        <td><input type="number" class="form-control qty-input" name="quantity[]" min="1" value="1"/></td>
                        <td><input type="text" class="form-control unit-price" name="unitPrice[]" readonly/></td>
                        <td><input type="text" class="form-control total-price" name="totalPrice[]" readonly/></td>
                        <td>
                            <div style="display: flex; align-items: center;">
                                <button type="button" class="btn btn-outline-success btn-sm add-row mx-1" title="Add Item"><i class="fa fa-plus-circle"></i></button>
                                <button type="button" class="btn btn-outline-danger btn-sm remove-row mx-1" title="Remove Item"><i class="fa fa-trash"></i></button>
                            </div>
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>
        <div class="bill-summary mt-4" id="bill-summary-section">
            <h5>Bill Summary</h5>
            <div class="row">
                <div class="col-md-4">
                    <div class="form-group">
                        <label for="totalAmount">Total Amount:</label>
                        <input type="text" class="form-control" id="totalAmount" name="totalAmount" readonly style="font-weight: bold; font-size: 1.1em; color: #28a745;" value="<%= bill != null ? bill.getTotalAmount() : "0.00" %>"/>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <label for="paidAmount">Paid Amount:</label>
                        <input type="number" class="form-control" id="paidAmount" name="paidAmount" min="0" step="0.01" placeholder="Enter paid amount" value="<%= bill != null ? bill.getPaidAmount() : "" %>"/>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <label for="balanceAmount">Balance:</label>
                        <input type="text" class="form-control" id="balanceAmount" name="balanceAmount" readonly style="font-weight: bold; font-size: 1.1em;" value="<%= bill != null ? bill.getBalance() : "0.00" %>"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="d-flex" style="gap: 12px;">
            <button type="submit" class="btn btn-success">Update Bill</button>
            <button type="button" class="btn btn-secondary" onclick="printBillPage()">Print Bill</button>
        </div>
    </form>
</div>
<script>
$(function() {
    // Item dropdown: set unit price and total
    function updateRow(row) {
        var price = parseFloat(row.find('.item-select option:selected').data('price')) || 0;
        var qty = parseInt(row.find('.qty-input').val()) || 1;
        row.find('.unit-price').val(price.toFixed(2));
        row.find('.total-price').val((price * qty).toFixed(2));
        calculateTotalAmount();
    }
    // Calculate total amount for all items
    function calculateTotalAmount() {
        var total = 0;
        $('#items-table tbody tr').each(function() {
            var rowTotal = parseFloat($(this).find('.total-price').val()) || 0;
            total += rowTotal;
        });
        $('#totalAmount').val(total.toFixed(2));
        calculateBalance();
    }
    // Calculate balance based on paid amount
    function calculateBalance() {
        var total = parseFloat($('#totalAmount').val()) || 0;
        var paid = parseFloat($('#paidAmount').val()) || 0;
        var balance = total - paid;
        $('#balanceAmount').val(balance.toFixed(2));
        if (balance < 0) {
            $('#balanceAmount').css('color', '#dc3545');
        } else if (balance === 0) {
            $('#balanceAmount').css('color', '#28a745');
        } else {
            $('#balanceAmount').css('color', '#ffc107');
        }
    }
    $('#items-table').on('change', '.item-select', function() {
        updateRow($(this).closest('tr'));
    });
    $('#items-table').on('input', '.qty-input', function() {
        updateRow($(this).closest('tr'));
    });
    $('#paidAmount').on('input', function() {
        calculateBalance();
    });
    $('#items-table').on('click', '.add-row', function() {
        var row = $(this).closest('tr').clone();
        row.find('input, select').val('');
        row.find('.unit-price, .total-price').val('');
        $('#items-table tbody').append(row);
    });
    $('#items-table').on('click', '.remove-row', function() {
        if ($('#items-table tbody tr').length > 1) {
            $(this).closest('tr').remove();
            calculateTotalAmount();
        }
    });
    // Initial calculation for pre-filled data
    $('#items-table tbody tr').each(function() {
        updateRow($(this));
    });
});
function printBill() {
    document.getElementById('print-billNumber').innerText = document.getElementById('billNumber').value;
    document.getElementById('print-billDateTime').innerText = document.getElementById('billDateTimeDisplay').value;
    document.getElementById('print-customerName').innerText = document.getElementById('info-name') ? document.getElementById('info-name').innerText : '';
    document.getElementById('print-customerAccount').innerText = document.getElementById('info-account') ? document.getElementById('info-account').innerText : '';
    document.getElementById('print-customerPhone').innerText = document.getElementById('info-phone') ? document.getElementById('info-phone').innerText : '';
    document.getElementById('print-customerAddress').innerText = document.getElementById('info-address') ? document.getElementById('info-address').innerText : '';
    document.getElementById('print-totalAmount').innerText = document.getElementById('totalAmount').value;
    document.getElementById('print-paidAmount').innerText = document.getElementById('paidAmount').value;
    document.getElementById('print-balance').innerText = document.getElementById('balanceAmount').value;
    var itemsTable = document.getElementById('print-items');
    itemsTable.innerHTML = '';
    var rows = document.querySelectorAll('#items-table tbody tr');
    rows.forEach(function(row) {
        var item = row.querySelector('select, input[name^="itemId"]');
        var qty = row.querySelector('input[name^="quantity"]');
        var price = row.querySelector('input[name^="unitPrice"]');
        var total = row.querySelector('input[name^="totalPrice"]');
        if(item && qty && price && total) {
            var tr = document.createElement('tr');
            tr.innerHTML = '<td>' + item.value + '</td><td>' + qty.value + '</td><td>' + price.value + '</td><td>' + total.value + '</td>';
            itemsTable.appendChild(tr);
        }
    });
    var printContents = document.getElementById('printableBill').innerHTML;
    var win = window.open('', '', 'height=700,width=900');
    win.document.write('<html><head><title>Print Bill</title>');
    win.document.write('<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"/>' );
    win.document.write('</head><body>');
    win.document.write(printContents);
    win.document.write('</body></html>');
    win.document.close();
    win.focus();
    win.print();
    win.close();
}
function printBillPage() {
  var billId = document.querySelector('input[name="billId"]').value;
  window.open('printBill?billId=' + billId, '_blank', 'width=800,height=600');
}
</script>
</body>
</html> 