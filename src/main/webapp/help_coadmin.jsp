<%@ page session="true" %>
<%@ page import="com.example.dinithi_pahana_edu.model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || !"coadmin".equalsIgnoreCase(user.getRole())) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Help - Coadmin</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .help-container { max-width: 700px; margin: 40px auto; background: #fff; border-radius: 10px; box-shadow: 0 2px 8px #0001; padding: 30px; }
        h2 { color: #232b3e; }
        ul { font-size: 1.1em; }
    </style>
</head>
<body>
    <jsp:include page="sidebar_coadmin.jspf" />
    <div class="main-content">
        <div class="help-guide">
            <h2><i class="fa fa-question-circle" style="color:#21b701"></i> Coadmin Help & User Guide</h2>
            <p class="intro">
                As a <b>Coadmin</b>, you have access to most features of the Pahana Edu Bookshop Management System.<br>
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
                        <span class="when"><b>When to use:</b> When a customer's details need to be updated, such as phone number or address.</span><br>
                        <span class="tip"><b>Tip:</b> Ensure you have the correct customer ID before editing.</span><br>
                        <span class="example"><b>Example:</b> A customer's phone number changed. You can edit their details to reflect the new number.</span>
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
                    <div class="icon"><i class="fa fa-question-circle"></i></div>
                    <div>
                        <div class="title">Help Section</div>
                        <div class="desc">Access this help page for guidance.<br>
                        <span class="howto">How: Click the "Help Section" card or sidebar link.</span><br>
                        <span class="when"><b>When to use:</b> Whenever you need to refer to this guide for quick access to features or specific instructions.</span><br>
                        <span class="tip"><b>Tip:</b> Bookmark this page for easy reference.</span><br>
                        <span class="example"><b>Example:</b> You need to add a new customer. You can quickly find the "Add New Customer" section in the help guide.</span>
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
                color: #555;
                margin-bottom: 15px;
                line-height: 1.6;
            }
            
            .contact-info {
                background: #f8f9fa;
                padding: 20px;
                border-radius: 6px;
                border-left: 4px solid #21b701;
                margin: 15px 0;
            }
            
            .phone-number {
                color: #21b701;
                font-weight: 600;
                font-size: 1.1rem;
            }
            
            .contact-note {
                font-style: italic;
                color: #666;
                font-size: 0.95rem;
                margin-top: 15px;
            }
            
            @media (max-width: 768px) {
    .help-guide { padding: 16px 2vw; }
    .feature-card { flex-direction: column; gap: 10px; }
}
</style> 