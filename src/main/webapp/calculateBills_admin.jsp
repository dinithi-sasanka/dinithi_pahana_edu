<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.dinithi_pahana_edu.model.Item" %>
<%@ page import="com.example.dinithi_pahana_edu.model.Customer" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html>
<head>
    <title>Calculate Bill (Admin)</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"/>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <style>
        .info-box { border: 1px solid #ccc; border-radius: 5px; padding: 10px; margin: 5px; display: inline-block; min-width: 180px; }
        .icon-btn { background: none; border: none; font-size: 1.2em; cursor: pointer; }
        .icon-btn:focus { outline: none; }
        .bill-header { background-color: #f8f9fa; padding: 15px; border-radius: 5px; margin-bottom: 20px; }
    </style>
</head>
<body>
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
                        <input type="text" class="form-control" id="billNumber" name="billNumber" placeholder="Enter bill number" required/>
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
                        <button type="button" class="icon-btn add-row" title="Add Row">+</button>
                        <button type="button" class="icon-btn remove-row" title="Remove Row">🗑️</button>
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
        
        <button type="submit" class="btn btn-success">Save Bill</button>
    </form>
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
    
    // Update form validation to require user input for bill number
    $('#bill-form').on('submit', function(e) {
        // Check if customer is selected
        if (!$('#customerId').val()) {
            alert('Please select a customer first.');
            e.preventDefault();
            return false;
        }
        // Check if bill number is entered
        if (!$('#billNumber').val()) {
            alert('Please enter a bill number.');
            e.preventDefault();
            return false;
        }
        // Check if bill date time is set
        if (!$('#billDateTime').val()) {
            alert('Please select a customer to generate bill information.');
            e.preventDefault();
            return false;
        }
        // Check if at least one item is selected
        var hasValidItem = false;
        $('#items-table tbody tr').each(function() {
            var itemId = $(this).find('.item-select').val();
            var quantity = $(this).find('.qty-input').val();
            var unitPrice = $(this).find('.unit-price').val();
            if (itemId && quantity && unitPrice && 
                itemId !== '' && quantity !== '' && unitPrice !== '') {
                hasValidItem = true;
                return false; // break the loop
            }
        });
        if (!hasValidItem) {
            alert('Please select at least one item with valid quantity and price.');
            e.preventDefault();
            return false;
        }
        return true; // allow form submission
    });
});
</script>
</body>
</html> 