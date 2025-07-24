<%@ page session="true" %>
<%@ page import="com.example.dinithi_pahana_edu.model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || !"staff".equalsIgnoreCase(user.getRole())) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Help - Staff</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .help-container { max-width: 700px; margin: 40px auto; background: #fff; border-radius: 10px; box-shadow: 0 2px 8px #0001; padding: 30px; }
        h2 { color: #232b3e; }
        ul { font-size: 1.1em; }
    </style>
</head>
<body>
    <jsp:include page="sidebar_staff.jspf" />
    <div class="main-content">
        <div class="help-guide">
            <h2><i class="fa fa-question-circle" style="color:#21b701"></i> Staff Help & User Guide</h2>
            <p class="intro">
                As a <b>Staff</b> member, you have access to the essential features of the Pahana Edu Bookshop Management System.<br>
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
                        <span class="when"><b>When to use:</b> When a new customer walks into the shop or you receive a new order.</span><br>
                        <span class="tip"><b>Tip:</b> Ensure all required fields are filled correctly to avoid errors.</span><br>
                        <span class="example"><b>Example:</b> If a student needs a textbook, you'd add their details to the system.</span>
                        </div>
                    </div>
                </div>
                <div class="feature-card">
                    <div class="icon"><i class="fa fa-user-edit"></i></div>
                    <div>
                        <div class="title">Edit Customer Info</div>
                        <div class="desc">Update existing customer information.<br>
                        <span class="howto">How: Click "Edit Customer Info", search, edit, and save changes.</span><br>
                        <span class="when"><b>When to use:</b> If a customer's details change (e.g., phone number, address).</span><br>
                        <span class="tip"><b>Tip:</b> Always double-check the changes before saving to avoid mistakes.</span><br>
                        <span class="example"><b>Example:</b> If a customer's phone number is incorrect, you'd edit it to the correct one.</span>
                        </div>
                    </div>
                </div>
                <div class="feature-card">
                    <div class="icon"><i class="fa fa-boxes"></i></div>
                    <div>
                        <div class="title">View Items</div>
                        <div class="desc">See all items currently in stock.<br>
                        <span class="howto">How: Click "View Items" to browse the inventory.</span><br>
                        <span class="when"><b>When to use:</b> When you need to check the current stock of a specific item or need to replenish.</span><br>
                        <span class="tip"><b>Tip:</b> This helps you manage inventory levels and identify low stock items.</span><br>
                        <span class="example"><b>Example:</b> If a textbook is low on stock, you'd check the inventory to see how many are left.</span>
                        </div>
                    </div>
                </div>
                <div class="feature-card">
                    <div class="icon"><i class="fa fa-calculator"></i></div>
                    <div>
                        <div class="title">Calculate Bill</div>
                        <div class="desc">Generate bills for customer purchases.<br>
                        <span class="howto">How: Click "Calculate Bill", select items, enter quantities, and generate the bill.</span><br>
                        <span class="when"><b>When to use:</b> Immediately after a customer makes a purchase.</span><br>
                        <span class="tip"><b>Tip:</b> Ensure all items and quantities are correct before generating the bill.</span><br>
                        <span class="example"><b>Example:</b> If a customer buys 2 textbooks and 1 notebook, you'd calculate the total bill.</span>
                        </div>
                    </div>
                </div>
                <div class="feature-card">
                    <div class="icon"><i class="fa fa-print"></i></div>
                    <div>
                        <div class="title">Edit/View Previous Bills</div>
                        <div class="desc">Manage and review all past bills.<br>
                        <span class="howto">How: Click this card, search for a bill, and choose to view or edit it.</span><br>
                        <span class="when"><b>When to use:</b> When you need to re-issue a bill for a customer or review their purchase history.</span><br>
                        <span class="tip"><b>Tip:</b> This helps you maintain accurate records and provide receipts.</span><br>
                        <span class="example"><b>Example:</b> If a customer asks for a receipt for a previous purchase, you'd view the bill.</span>
                        </div>
                    </div>
                </div>
                <div class="feature-card">
                    <div class="icon"><i class="fa fa-question-circle"></i></div>
                    <div>
                        <div class="title">Help Section</div>
                        <div class="desc">Access this help page for guidance.<br>
                        <span class="howto">How: Click the "Help Section" card or sidebar link.</span><br>
                        <span class="when"><b>When to use:</b> Whenever you need to refer to this guide or need assistance.</span><br>
                        <span class="tip"><b>Tip:</b> Bookmark this page for quick access to common questions.</span><br>
                        <span class="example"><b>Example:</b> If you forget how to add a customer, you'd refer to this help page.</span>
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