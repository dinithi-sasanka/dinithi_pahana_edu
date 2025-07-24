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
<html>
<head>
    <title>Help - Admin</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .help-container { max-width: 700px; margin: 40px auto; background: #fff; border-radius: 10px; box-shadow: 0 2px 8px #0001; padding: 30px; }
        h2 { color: #232b3e; }
        ul { font-size: 1.1em; }
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
                        <div class="title">Edit/Delete/View Previous Bills</div>
                        <div class="desc">Manage and review all past bills.<br>
                        <span class="howto">How: Click this card, search for a bill, and choose to view, edit, or delete it.</span><br>
                        <span class="when"><b>When to use:</b> When you need to review past transactions, make corrections to previous bills, or remove incorrect entries.</span><br>
                        <span class="tip"><b>Tip:</b> Always ensure you have the correct bill ID before editing or deleting.</span><br>
                        <span class="example"><b>Example:</b> You notice an error in a previous bill. You can edit it to correct the details.</span>
                        </div>
                    </div>
                </div>
                <div class="feature-card">
                    <div class="icon"><i class="fa fa-users-cog"></i></div>
                    <div>
                        <div class="title">Manage User Roles/Settings</div>
                        <div class="desc">Add, edit, or remove users and assign roles.<br>
                        <span class="howto">How: Click this card to manage user accounts and permissions.</span><br>
                        <span class="when"><b>When to use:</b> When you need to add a new staff member, change a user's role, or remove a user from the system.</span><br>
                        <span class="tip"><b>Tip:</b> Only admins should have access to this feature. Double-check user roles before saving changes.</span><br>
                        <span class="example"><b>Example:</b> A new staff member joins the shop. You add them as a user and assign the 'staff' role.</span>
                        </div>
                    </div>
                </div>
                <div class="feature-card">
                    <div class="icon"><i class="fa fa-warehouse"></i></div>
                    <div>
                        <div class="title">Check Current Stock</div>
                        <div class="desc">View current inventory and low stock alerts.<br>
                        <span class="howto">How: Click "Check Current Stock" to see all items and their quantities.</span><br>
                        <span class="when"><b>When to use:</b> Regularly to ensure you have enough stock for sales and avoid stockouts.</span><br>
                        <span class="tip"><b>Tip:</b> This helps you manage inventory levels and identify items that need reordering.</span><br>
                        <span class="example"><b>Example:</b> If you notice a textbook is low on stock, you'd check the inventory to see how many are left.</span>
                        </div>
                    </div>
                </div>
                <div class="feature-card">
                    <div class="icon"><i class="fa fa-chart-bar"></i></div>
                    <div>
                        <div class="title">Manage Reports</div>
                        <div class="desc">Access and generate various system reports.<br>
                        <span class="howto">How: Click this card, select the report type, and view or print the report.</span><br>
                        <span class="when"><b>When to use:</b> When you need to analyze sales, inventory, or customer trends over time.</span><br>
                        <span class="tip"><b>Tip:</b> Use reports to identify best-selling items and plan future stock orders.</span><br>
                        <span class="example"><b>Example:</b> At the end of the quarter, generate a sales report to review performance.</span>
                        </div>
                    </div>
                </div>
                <div class="feature-card">
                    <div class="icon"><i class="fa fa-user"></i></div>
                    <div>
                        <div class="title">Profile Management</div>
                        <div class="desc">View and update your own profile details.<br>
                        <span class="howto">How: Click "Profile Management", update your info, and click Save.</span><br>
                        <span class="when"><b>When to use:</b> When you need to change your personal information or password.</span><br>
                        <span class="tip"><b>Tip:</b> Ensure your password is strong and unique.</span><br>
                        <span class="example"><b>Example:</b> If you want to change your email address, you'd update your profile.</span>
                        </div>
                    </div>
                </div>
                <div class="feature-card">
                    <div class="icon"><i class="fa fa-sign-out-alt"></i></div>
                    <div>
                        <div class="title">Logout</div>
                        <div class="desc">Securely exit the system.<br>
                        <span class="howto">How: Click "Logout" to finish your session.</span><br>
                        <span class="when"><b>When to use:</b> When you are done using the system for the day or need to change user.</span><br>
                        <span class="tip"><b>Tip:</b> Always log out to ensure your session is secure.</span><br>
                        <span class="example"><b>Example:</b> If you need to switch to a different user, you'd log out and log in as the new user.</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="help-tip">
                <b>Tip:</b> Use the sidebar on the left to quickly access any feature.<br>
                If you need further assistance, please contact your system administrator.
            </div>
        </div>
    </div>
</body>
</html>
<style>
.help-guide {
    max-width: 900px;
    margin: 48px auto;
    background: #fff;
    border-radius: 18px;
    box-shadow: 0 4px 24px #0002;
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
    box-shadow: 0 2px 8px #0001;
    padding: 24px 22px;
    transition: box-shadow 0.2s, background 0.2s;
}
.feature-card:hover {
    background: #eaffea;
    box-shadow: 0 4px 16px #21b70122;
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
    margin-top: 40px;
    background: #f6fff6;
    border-left: 6px solid #21b701;
    padding: 18px 26px;
    border-radius: 10px;
    color: #232b3e;
    font-size: 1.13em;
}
@media (max-width: 700px) {
    .help-guide { padding: 16px 2vw; }
    .feature-card { flex-direction: column; gap: 10px; }
}
</style> 