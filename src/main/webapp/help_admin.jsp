<%@ page session="true" %>
<%@ page import="com.example.dinithi_pahana_edu.model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || !"admin".equalsIgnoreCase(user.getRole())) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Help - Admin</title>
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,700&display=swap" rel="stylesheet">
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
            background: #ffffffe7;
            min-height: 100vh;
        }
        .help-container { 
            max-width: 700px; 
            margin: 40px auto; 
            background: #fff; 
            border-radius: 10px; 
            box-shadow: 0 2px 8px rgba(0,0,0,0.1); 
            padding: 30px; 
        }
        .help-guide {
            max-width: 900px;
            margin: 48px auto;
            background: #fff;
            border-radius: 18px;
            box-shadow: 0 4px 24px rgba(0,0,0,0.1);
            padding: 40px 36px 32px 36px;
            font-family: 'Segoe UI', Arial, sans-serif;
        }
        .help-guide h2 {
            color: #232b3e;
            margin-bottom: 24px;
            font-size: 2.1em;
            display: flex;
            align-items: center;
            gap: 14px;
        }
        .help-guide .intro {
            font-size: 1.15em;
            margin-bottom: 32px;
            color: #333;
        }
        .help-guide .intro span {
            color: #21b701;
            font-weight: 500;
        }
        .feature-cards {
            display: flex;
            flex-direction: column;
            gap: 28px;
        }
        .feature-card {
            display: flex;
            align-items: flex-start;
            gap: 24px;
            background: #f8fafd;
            border-radius: 12px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            padding: 24px 22px;
            transition: box-shadow 0.2s, background 0.2s;
        }
        .feature-card:hover {
            background: #eaffea;
            box-shadow: 0 4px 16px rgba(33,183,1,0.1);
        }
        .feature-card .icon {
            font-size: 2.5em;
            color: #21b701;
            min-width: 48px;
            margin-top: 2px;
        }
        .feature-card .title {
            color: #232b3e;
            font-size: 1.18em;
            font-weight: bold;
            margin-bottom: 6px;
        }
        .feature-card .desc {
            color: #444;
            font-size: 1.05em;
            margin-bottom: 2px;
        }
        .feature-card .howto {
            color: #21b701;
            font-size: 1em;
            font-style: italic;
        }
        .help-tip {
            background: #f8f9fa;
            border-left: 4px solid #21b701;
            padding: 15px;
            margin: 20px 0;
            border-radius: 4px;
        }
        
        .tips-section, .help-contact {
            background: #fff;
            border-radius: 8px;
            padding: 25px;
            margin: 30px 0;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            border: 1px solid #e9ecef;
        }
        
        .tips-section h3, .help-contact h3 {
            color: #232d3b;
            margin-bottom: 20px;
            font-size: 1.4rem;
            font-weight: 600;
        }
        
        .tips-content ul {
            list-style: none;
            padding: 0;
            margin: 0;
        }
        
        .tips-content li {
            padding: 12px 0;
            border-bottom: 1px solid #f1f3f4;
            color: #555;
            line-height: 1.6;
        }
        
        .tips-content li:last-child {
            border-bottom: none;
        }
        
        .tips-content strong {
            color: #232d3b;
            font-weight: 600;
        }
        
        .contact-content p {
            color: #232b3e;
            margin-bottom: 15px;
            line-height: 1.6;
            font-weight: 500;
        }
        
        .contact-info {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 6px;
            border-left: 4px solid #21b701;
            margin: 15px 0;
        }
        
        .contact-info strong {
            color: #232b3e;
            font-weight: 600;
        }
        
        .contact-info {
            color: #232b3e;
            font-weight: 500;
        }
        
        .phone-number {
            color: #21b701;
            font-weight: 700;
            font-size: 1.2rem;
        }
        
        .contact-note {
            font-style: italic;
            color: #232b3e;
            font-size: 1rem;
            margin-top: 15px;
            font-weight: 500;
        }
        
        @media (max-width: 768px) {
            .help-guide { padding: 16px 2vw; }
            .feature-card { flex-direction: column; gap: 10px; }
        }
    </style>
</head>
<body>
    <jsp:include page="sidebar_admin.jspf" />
    <div class="main-content">
        <div class="help-guide">
            <h2><i class="fa fa-question-circle" style="color:#21b701"></i> Admin Help & User Guide</h2>
            <p class="intro">
                As an <b>Admin</b>, you have full access to all features of the Pahana Edu Bookshop Management System.<br>
                <span>Use the cards and sidebar to navigate. Click any feature below to learn more!</span>
            </p>
            <div class="feature-cards">
                <div class="feature-card">
                    <div class="icon"><i class="fa fa-chart-line"></i></div>
                    <div>
                        <div class="title">View Dashboards</div>
                        <div class="desc">See sales analytics, trends, and system overviews.<br>
                        <span class="howto">How: Click the "View Dashboards" card for charts and summaries.</span><br>
                        <span class="when"><b>When to use:</b> To get an overview of sales, customer activity, or inventory trends at a glance.</span><br>
                        <span class="tip"><b>Tip:</b> Use this before making business decisions or planning stock orders.</span><br>
                        <span class="example"><b>Example:</b> At the end of the month, check the dashboard to see which items sold best.</span>
                        </div>
                    </div>
                </div>
                <div class="feature-card">
                    <div class="icon"><i class="fa fa-user-plus"></i></div>
                    <div>
                        <div class="title">Add New Customer</div>
                        <div class="desc">Register new customers in the system.<br>
                        <span class="howto">How: Click "Add New Customer", fill in details, and click Save.</span><br>
                        <span class="when"><b>When to use:</b> When a new customer visits the shop for the first time or wants to make a purchase on account.</span><br>
                        <span class="tip"><b>Tip:</b> Always double-check the customer's contact details before saving.</span><br>
                        <span class="example"><b>Example:</b> A parent comes to buy books for their child and is not yet in the system. Use this feature to add them before billing.</span>
                        </div>
                    </div>
                </div>
                <div class="feature-card">
                    <div class="icon"><i class="fa fa-user-edit"></i></div>
                    <div>
                        <div class="title">Edit Customer Info</div>
                        <div class="desc">Update existing customer information.<br>
                        <span class="howto">How: Click "Edit Customer Info", search, edit, and save changes.</span><br>
                        <span class="when"><b>When to use:</b> If a customer changes their phone number, address, or other details.</span><br>
                        <span class="tip"><b>Tip:</b> Use the search function to quickly find the customer you want to edit.</span><br>
                        <span class="example"><b>Example:</b> A customer calls to update their email address for future notifications.</span>
                        </div>
                    </div>
                </div>
                <div class="feature-card">
                    <div class="icon"><i class="fa fa-id-card"></i></div>
                    <div>
                        <div class="title">View Customer Account</div>
                        <div class="desc">See account details and purchase history for any customer.<br>
                        <span class="howto">How: Click "View Customer Account", search for a customer, and review their account.</span><br>
                        <span class="when"><b>When to use:</b> When you need to check a customer's total purchases, outstanding balance, or their entire purchase history.</span><br>
                        <span class="tip"><b>Tip:</b> This helps you manage customer relationships and financial records.</span><br>
                        <span class="example"><b>Example:</b> If a customer asks for their account summary, you'd view their account.</span>
                        </div>
                    </div>
                </div>
                <div class="feature-card">
                    <div class="icon"><i class="fa fa-boxes"></i></div>
                    <div>
                        <div class="title">Add/Update/Delete Items</div>
                        <div class="desc">Manage inventory items and their details.<br>
                        <span class="howto">How: Click this card to add new items, update stock, or remove items from inventory.</span><br>
                        <span class="when"><b>When to use:</b> When you need to add new items to the inventory, update existing stock, or remove items that are no longer available.</span><br>
                        <span class="tip"><b>Tip:</b> Always verify the stock quantity before adding or updating.</span><br>
                        <span class="example"><b>Example:</b> You received a new shipment of books. Add them to the inventory to make them available for sale.</span>
                        </div>
                    </div>
                </div>
                <div class="feature-card">
                    <div class="icon"><i class="fa fa-calculator"></i></div>
                    <div>
                        <div class="title">Calculate Bill</div>
                        <div class="desc">Generate bills for customer purchases.<br>
                        <span class="howto">How: Click "Calculate Bill", select items, enter quantities, and generate the bill.</span><br>
                        <span class="when"><b>When to use:</b> When a customer makes a purchase and you need to generate a receipt or invoice.</span><br>
                        <span class="tip"><b>Tip:</b> Double-check the total amount and item details before finalizing the bill.</span><br>
                        <span class="example"><b>Example:</b> A customer buys 5 books. You calculate the bill, and they pay. Generate the receipt.</span>
                        </div>
                    </div>
                </div>
                <div class="feature-card">
                    <div class="icon"><i class="fa fa-print"></i></div>
                    <div>
                        <div class="title">Print/View Bills</div>
                        <div class="desc">Access and print previous bills or view bill history.<br>
                        <span class="howto">How: Click "Print/View Bills" to see all bills, search, and print as needed.</span><br>
                        <span class="when"><b>When to use:</b> When you need to reprint a bill, check bill history, or review past transactions.</span><br>
                        <span class="tip"><b>Tip:</b> Use the search and filter options to quickly find specific bills.</span><br>
                        <span class="example"><b>Example:</b> A customer lost their receipt and needs a copy. Use this feature to find and reprint it.</span>
                        </div>
                    </div>
                </div>
                <div class="feature-card">
                    <div class="icon"><i class="fa fa-chart-bar"></i></div>
                    <div>
                        <div class="title">Reports</div>
                        <div class="desc">Generate various business reports and analytics.<br>
                        <span class="howto">How: Click "Reports" to access sales reports, customer reports, and inventory reports.</span><br>
                        <span class="when"><b>When to use:</b> When you need business insights, sales analysis, or inventory status reports.</span><br>
                        <span class="tip"><b>Tip:</b> Regular reports help track business performance and identify trends.</span><br>
                        <span class="example"><b>Example:</b> At month-end, generate sales reports to analyze performance and plan for the next month.</span>
                        </div>
                    </div>
                </div>
                <div class="feature-card">
                    <div class="icon"><i class="fa fa-users-cog"></i></div>
                    <div>
                        <div class="title">User Role Management</div>
                        <div class="desc">Manage user accounts and system access permissions.<br>
                        <span class="howto">How: Click "User Role Management" to add, edit, or manage user accounts and roles.</span><br>
                        <span class="when"><b>When to use:</b> When you need to add new staff members, change user roles, or manage system access.</span><br>
                        <span class="tip"><b>Tip:</b> Regularly review user access to maintain system security.</span><br>
                        <span class="example"><b>Example:</b> A new staff member joins. Use this feature to create their account with appropriate permissions.</span>
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="tips-section">
                <h3><i class="fa fa-lightbulb" style="color:#21b701"></i> Tips for Effective Use</h3>
                <div class="tips-content">
                    <ul>
                        <li><strong>Keep Customer and Item Data Up to Date:</strong> Ensure all customer information and item details are current for accurate billing.</li>
                        <li><strong>Regularly Check Stock Alerts:</strong> Monitor stock levels frequently to prevent selling out-of-stock items.</li>
                        <li><strong>Always Review Bill Details:</strong> Double-check all bill information before finalizing to avoid errors.</li>
                        <li><strong>Log Out When You Finish Your Sessions:</strong> Always log out when done to keep the system secure.</li>
                    </ul>
                </div>
            </div>
            
            <div class="help-contact">
                <h3><i class="fa fa-phone" style="color:#21b701"></i> Getting Further Help</h3>
                <div class="contact-content">
                    <p>If you encounter any issues or need additional support:</p>
                    <div class="contact-info">
                        <strong>Contact Number:</strong> <span class="phone-number">0713441715</span><br>
                        <strong>Available Hours:</strong> Monday - Friday, 8:00 AM - 6:00 PM<br>
                        <strong>Response Time:</strong> Within 24 hours during business days
                    </div>
                    <p class="contact-note">Please have your user ID and a description of the issue ready when contacting support.</p>
                </div>
            </div>
        </div>
    </div>
</body>
</html> 