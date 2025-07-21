<%@ page import="com.example.dinithi_pahana_edu.model.Bill" %>
<%@ page import="com.example.dinithi_pahana_edu.model.BillItem" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.dinithi_pahana_edu.model.Customer" %>
<%
    Bill bill = (Bill) request.getAttribute("bill");
    List<BillItem> items = (List<BillItem>) request.getAttribute("items");
    Customer customer = (Customer) request.getAttribute("customer");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Print Bill</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"/>
    <style>
        body { background: #fff; color: #232b3e; }
        .print-container { max-width: 700px; margin: 40px auto; padding: 32px; border: 1.5px solid #ccc; border-radius: 12px; }
        h2, h4 { text-align: center; }
        .bill-info, .customer-info { margin-bottom: 18px; }
        .table th, .table td { text-align: center; }
    </style>
</head>
<body onload="window.print()">
    <div class="print-container">
        <h2>Pahana Edu Bookshop</h2>
        <h4>Bill Receipt</h4>
        <div class="bill-info">
            <b>Bill Number:</b> <%= bill.getBillNumber() %><br/>
            <b>Date & Time:</b> <%= bill.getBillDateTime() %><br/>
        </div>
        <div class="customer-info">
            <b>Customer ID:</b> <%= customer != null ? customer.getId() : "" %><br/>
            <b>Account #:</b> <%= customer != null ? customer.getAccountNumber() : "" %><br/>
            <b>Name:</b> <%= customer != null ? customer.getName() : "" %><br/>
            <b>Address:</b> <%= customer != null ? customer.getAddress() : "" %><br/>
            <b>Phone:</b> <%= customer != null ? customer.getTelephone() : "" %>
        </div>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>#</th>
                    <th>Item Name</th>
                    <th>Quantity</th>
                    <th>Unit Price</th>
                    <th>Total</th>
                </tr>
            </thead>
            <tbody>
                <% int idx = 1;
                   for (BillItem item : items) { %>
                    <tr>
                        <td><%= idx++ %></td>
                        <td><%= item.getItemName() %></td>
                        <td><%= item.getQuantity() %></td>
                        <td><%= item.getPrice() %></td>
                        <td><%= item.getQuantity() * item.getPrice() %></td>
                    </tr>
                <% } %>
            </tbody>
        </table>
        <div style="text-align:right; margin-top:18px;">
            <b>Total:</b> <%= bill.getTotalAmount() %><br/>
            <b>Paid:</b> <%= bill.getPaidAmount() %><br/>
            <b>Balance:</b> <%= bill.getBalance() %>
        </div>
        <div style="text-align:center; margin-top:24px;">Thank you for your purchase!</div>
    </div>
</body>
</html> 