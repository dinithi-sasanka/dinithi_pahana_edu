<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.dinithi_pahana_edu.model.Item" %>
<%@ page import="com.example.dinithi_pahana_edu.model.Customer" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html>
<head>
    <title>Calculate Bill (Staff)</title>
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
        .icon-btn { background: none; border: none; font-size: 1.2em; cursor: pointer; }
        .icon-btn:focus { outline: none; }
        .bill-header { background: #f8f9fa; color: #232b3e; padding: 15px; border-radius: 5px; margin-bottom: 20px; }
        .bill-summary { background: #f8f9fa; color: #232b3e; }
        label, .form-group label { color: #232b3e !important; }
        .form-control, .table, .table th, .table td { background: #fff !important; border-color: #dbeafe; }
        ::placeholder { color: #888 !important; opacity: 1; }
        input[readonly], input[disabled] { background: #f4f4f4 !important; color: #232b3e !important; }
        .icon-btn.add-row i { color: #21b701; font-size: 1.4em; }
        .icon-btn.remove-row i { color: #dc3545; font-size: 1.4em; }
        .icon-btn.remove-row { margin-left: 8px; }
    </style>
</head>
<body>
    <jsp:include page="sidebar_staff.jspf" />
    <div class="main-content">
        <div id="save-message" style="margin-bottom: 16px;"></div>
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
            
            <!-- Customer Search Section -->
            <div id="customer-search-section" <% if (request.getAttribute("customer") != null) { %> style="display:none;" <% } %>>
                <h4>Search Customer</h4>
                <div class="form-inline mb-3">
                    <input type="text" class="form-control mr-2" id="customerSearchBox" placeholder="Type phone, name, account number, etc."/>
                    <button type="button" class="btn btn-primary" id="loadCustomerBtn">Load</button>
                </div>
            </div>
            <!-- Customer Info Boxes -->
            <div id="customer-info-section" style="display:none;">
                <h5>Customer Details</h5>
                <div class="d-flex flex-wrap">
                    <div class="info-box">ID: <span id="info-id"></span></div>
                    <div class="info-box">Account #: <span id="info-account"></span></div>
                    <div class="info-box">Name: <span id="info-name"></span></div>
                    <div class="info-box">Address: <span id="info-address"></span></div>
                    <div class="info-box">Phone: <span id="info-phone"></span></div>
                    <div class="info-box">Email: <span id="info-email"></span></div>
                </div>
                <button class="btn btn-secondary mt-2" id="change-customer-btn">Change Customer</button>
            </div>
            
            <form id="bill-form" method="post" action="calculateBill">
                <!-- Bill Header Information -->
                <div class="bill-header" id="bill-header-section" style="display:none;">
                    <h5>Bill Information</h5>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="billNumber">Bill Number:</label>
                                <input type="text" class="form-control" id="billNumber" name="billNumber" placeholder="Enter bill number" required value="<%= request.getAttribute("nextBillNumber") != null ? request.getAttribute("nextBillNumber") : "" %>"/>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="billDateTimeDisplay">Bill Date & Time:</label>
                                <input type="text" class="form-control" id="billDateTimeDisplay" readonly/>
                                <input type="hidden" id="billDateTime" name="billDateTime"/>
                            </div>
                        </div>
                    </div>
                </div>
                <input type="hidden" name="customerId" id="customerId" value="<%= request.getAttribute("customer") != null ? ((Customer)request.getAttribute("customer")).getId() : "" %>"/>
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
                        <!-- First row template -->
                        <tr>
                            <td>
                                <select class="form-control item-select" name="itemId[]">
                                    <option value="">Select Item</option>
                                    <% 
                                    List<Item> items = (List<Item>)request.getAttribute("items");
                                    if (items != null) {
                                        for (Item item : items) { %>
                                            <option value="<%= item.getId() %>" data-price="<%= item.getPrice() %>"><%= item.getName() %></option>
                                    <%  }
                                    } %>
                                </select>
                            </td>
                            <td><input type="number" class="form-control qty-input" name="quantity[]" min="1" value="1"/></td>
                            <td><input type="text" class="form-control unit-price" name="unitPrice[]" readonly/></td>
                            <td><input type="text" class="form-control total-price" name="totalPrice[]" readonly/></td>
                            <td>
                                <div style="display: flex; align-items: center;">
                                    <button type="button" class="icon-btn add-row" title="Add Item"><i class="fa fa-plus-circle"></i></button>
                                    <button type="button" class="icon-btn remove-row" title="Remove Item"><i class="fa fa-trash"></i></button>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
                
                <!-- Bill Summary Section -->
                <div class="bill-summary mt-4" id="bill-summary-section" style="display:none;">
                    <h5>Bill Summary</h5>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label for="totalAmount">Total Amount:</label>
                                <input type="text" class="form-control" id="totalAmount" name="totalAmount" readonly style="font-weight: bold; font-size: 1.1em; color: #28a745;"/>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label for="paidAmount">Paid Amount:</label>
                                <input type="number" class="form-control" id="paidAmount" name="paidAmount" min="0" step="0.01" placeholder="Enter paid amount"/>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label for="balanceAmount">Balance:</label>
                                <input type="text" class="form-control" id="balanceAmount" name="balanceAmount" readonly style="font-weight: bold; font-size: 1.1em;"/>
                            </div>
                        </div>
                    </div>
                </div>
                
                <div class="d-flex" style="gap: 12px;">
                    <button type="submit" class="btn btn-success">Save Bill</button>
                    <button type="button" class="btn btn-secondary" onclick="printBill()">Print Bill</button>
                </div>
            </form>
        </div>
        <!-- Hidden Printable Bill Section -->
        <div id="printableBill" style="display:none;">
            <div style="text-align:center; margin-bottom:20px;">
                <h2>Pahana Edu Bookshop</h2>
                <h4>Bill Receipt</h4>
            </div>
            <div>
                <b>Bill Number:</b> <span id="print-billNumber"></span><br/>
                <b>Date & Time:</b> <span id="print-billDateTime"></span><br/>
                <b>Customer:</b> <span id="print-customerName"></span> (<span id="print-customerAccount"></span>)<br/>
                <b>Phone:</b> <span id="print-customerPhone"></span><br/>
                <b>Email:</b> <span id="print-customerEmail"></span><br/>
                <b>Address:</b> <span id="print-customerAddress"></span><br/>
            </div>
            <hr/>
            <table border="1" width="100%" style="border-collapse:collapse;">
                <thead>
                    <tr><th>Item</th><th>Qty</th><th>Unit Price</th><th>Total</th></tr>
                </thead>
                <tbody id="print-items"></tbody>
            </table>
            <hr/>
            <div style="text-align:right;">
                <b>Total:</b> <span id="print-totalAmount"></span><br/>
                <b>Paid:</b> <span id="print-paidAmount"></span><br/>
                <b>Balance:</b> <span id="print-balance"></span>
            </div>
            <div style="text-align:center; margin-top:20px;">Thank you for your purchase!</div>
        </div>
        <script>
        $(function() {
            // Set current date time
            function setCurrentDateTime() {
                var now = new Date();
                var dateTimeString = now.toLocaleDateString() + ' ' + now.toLocaleTimeString();
                $('#billDateTime').val(dateTimeString);
            }
            
            // Show/hide customer sections
            $('#change-customer-btn').click(function() {
                $('#customer-info-section').hide();
                $('#customer-search-section').show();
                $('#customerId').val('');
                $('#bill-header-section').hide();
            });
            
            // Remove bill number auto-generation and syncing
            // Only set bill date/time when customer is loaded
            $('#loadCustomerBtn').click(function() {
                var searchTerm = $('#customerSearchBox').val();
                if (!searchTerm) { alert('Please enter a search term.'); return; }
                $.post('searchCustomer', { searchTerm: searchTerm }, function(data) {
                    if (data && data.id) {
                        $('#info-id').text(data.id);
                        $('#info-account').text(data.accountNumber);
                        $('#info-name').text(data.name);
                        $('#info-address').text(data.address);
                        $('#info-phone').text(data.telephone);
                        $('#info-email').text(data.email || '');
                        $('#customerId').val(data.id);
                        $('#customer-search-section').hide();
                        $('#customer-info-section').show();
                        // Only set bill date/time
                        var billDT = new Date();
                        var dateTimeString = billDT.toLocaleDateString() + ' ' + billDT.toLocaleTimeString();
                        $('#billDateTimeDisplay').val(dateTimeString);
                        $('#billDateTime').val(dateTimeString);
                        $('#bill-header-section').show();
                        $('#bill-summary-section').show();
                        calculateTotalAmount(); // Initial calculation
                        if (data.nextBillNumber) {
                            $('#billNumber').val(data.nextBillNumber);
                        }
                        // Remove AJAX item loading
                    } else {
                        alert('Customer not found!');
                    }
                }, 'json');
            });
            
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
                
                // Color code the balance
                if (balance < 0) {
                    $('#balanceAmount').css('color', '#dc3545'); // Red for overpayment
                } else if (balance === 0) {
                    $('#balanceAmount').css('color', '#28a745'); // Green for fully paid
                } else {
                    $('#balanceAmount').css('color', '#ffc107'); // Yellow for partial payment
                }
            }
            
            $('#items-table').on('change', '.item-select', function() {
                updateRow($(this).closest('tr'));
            });
            $('#items-table').on('input', '.qty-input', function() {
                updateRow($(this).closest('tr'));
            });
            
            // Handle paid amount input
            $('#paidAmount').on('input', function() {
                calculateBalance();
            });
            
            // Add row
            $('#items-table').on('click', '.add-row', function() {
                var row = $(this).closest('tr').clone();
                row.find('input, select').val('');
                row.find('.unit-price, .total-price').val('');
                $('#items-table tbody').append(row);
            });
            // Remove row
            $('#items-table').on('click', '.remove-row', function() {
                if ($('#items-table tbody tr').length > 1) {
                    $(this).closest('tr').remove();
                    calculateTotalAmount();
                }
            });
            
            // AJAX Save Bill
            $('#bill-form').off('submit').on('submit', function(e) {
                e.preventDefault();
                var formData = $(this).serialize();
                $.ajax({
                    url: 'calculateBill',
                    type: 'POST',
                    data: formData,
                    success: function(response) {
                        $('#save-message').html('<div class="alert alert-success">Bill saved successfully!</div>');
                    },
                    error: function(xhr) {
                        $('#save-message').html('<div class="alert alert-danger">Error saving bill. Please try again.</div>');
                    }
                });
            });
        });
        </script>
        <script>
        function printBill() {
            // Fill printable bill with current values
            document.getElementById('print-billNumber').innerText = document.getElementById('billNumber').value;
            document.getElementById('print-billDateTime').innerText = document.getElementById('billDateTimeDisplay').value;
            document.getElementById('print-customerName').innerText = document.getElementById('info-name') ? document.getElementById('info-name').innerText : '';
            document.getElementById('print-customerAccount').innerText = document.getElementById('info-account') ? document.getElementById('info-account').innerText : '';
            document.getElementById('print-customerPhone').innerText = document.getElementById('info-phone') ? document.getElementById('info-phone').innerText : '';
            document.getElementById('print-customerEmail').innerText = document.getElementById('info-email') ? document.getElementById('info-email').innerText : '';
            document.getElementById('print-customerAddress').innerText = document.getElementById('info-address') ? document.getElementById('info-address').innerText : '';
            document.getElementById('print-totalAmount').innerText = document.getElementById('totalAmount').value;
            document.getElementById('print-paidAmount').innerText = document.getElementById('paidAmount').value;
            document.getElementById('print-balance').innerText = document.getElementById('balanceAmount').value; // Use balanceAmount for printing
            // Items
            var itemsTable = document.getElementById('print-items');
            itemsTable.innerHTML = '';
            var rows = document.querySelectorAll('#items-table tbody tr');
            rows.forEach(function(row) {
                var item = row.querySelector('select, input[name^="itemId"]'); // Changed to itemId
                var qty = row.querySelector('input[name^="quantity"]');
                var price = row.querySelector('input[name^="unitPrice"]');
                var total = row.querySelector('input[name^="totalPrice"]'); // Changed to totalPrice
                if(item && qty && price && total) {
                    var tr = document.createElement('tr');
                    tr.innerHTML = '<td>' + item.value + '</td><td>' + qty.value + '</td><td>' + price.value + '</td><td>' + total.value + '</td>';
                    itemsTable.appendChild(tr);
                }
            });
            // Print only the bill
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
        </script>
    </div>
</body>
</html> 