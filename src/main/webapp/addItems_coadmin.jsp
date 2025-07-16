<%@ page session="true" %>
<%
    com.example.dinithi_pahana_edu.model.User user = (com.example.dinithi_pahana_edu.model.User) session.getAttribute("user");
    if (user == null || !"coadmin".equalsIgnoreCase(user.getRole())) {
        response.sendRedirect("error.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add New Item - Coadmin</title>
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body { margin: 0; font-family: 'Roboto', Arial, sans-serif; background: linear-gradient(120deg, #232b3e, #1a2233); color: #d7dee5; min-height: 100vh; }
        .sidebar { position: fixed; left: 0; top: 0; bottom: 0; width: 220px; background: #232b3e; padding: 30px 0 0 0; box-shadow: 2px 0 10px rgba(0,0,0,0.1); display: flex; flex-direction: column; z-index: 10; }
        .sidebar h2 { color: #fff; text-align: center; margin-bottom: 2rem; font-size: 1.7rem; letter-spacing: 1px; }
        .sidebar ul { list-style: none; padding: 0; margin: 0; }
        .sidebar ul li { margin: 18px 0; }
        .sidebar ul li a { color: #21b701; text-decoration: none; font-size: 1.1em; padding: 10px 30px; display: block; border-radius: 6px; transition: background 0.2s, color 0.2s, border-color 0.2s; border-left: 4px solid transparent; }
        .sidebar ul li a:hover { background: #fcfbfb; color: #232b3e; border-left: 4px solid #232b3e; }
        .sidebar ul li a i { color: #acacac; margin-right: 10px; transition: color 0.2s; }
        .sidebar ul li a:hover i { color: #21b701; }
        .main-content { margin-left: 240px; padding: 40px 30px; background: #ffffffe7; min-height: 100vh; }
        .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px; }
        .header h1 { color: #232b3e; font-size: 2rem; margin: 0; }
        .user-info { font-size: 1.1em; color: #21b701; background: #27304a; padding: 8px 18px; border-radius: 20px; display: flex; align-items: center; gap: 10px; }
        .form-area { display: flex; flex-direction: column; gap: 22px; max-width: 700px; width: 100%; margin: 0 auto; }
        .input-card { background: #fff; border-radius: 10px; box-shadow: 0 2px 8px rgba(44,62,80,0.10); border: 1px solid #e0e0e0; padding: 22px 28px 16px 28px; display: flex; flex-direction: column; width: 100%; }
        .input-card label { color: #232b3e; font-size: 1.08rem; font-weight: 500; margin-bottom: 7px; font-family: system-ui, Arial, sans-serif; }
        .input-card input, .input-card textarea { width: 100%; background: #fff; color: #232b3e; border: 1.5px solid #bdbdbd; font-size: 1.13rem; padding: 12px 14px; border-radius: 6px; transition: border-color 0.2s; box-sizing: border-box; font-family: system-ui, Arial, sans-serif; }
        .input-card textarea { min-height: 50px; resize: vertical; }
        .input-card input:focus, .input-card textarea:focus { border-color: #21b701; outline: none; }
        .button-card { background: #fff; border-radius: 10px; box-shadow: 0 2px 8px rgba(44,62,80,0.10); border: 1px solid #e0e0e0; padding: 18px 28px 18px 28px; display: flex; justify-content: center; align-items: center; }
        .submit-btn { width: 100%; background: #21b701; color: #fff; border-radius: 6px; font-size: 1.18rem; font-weight: 700; border: none; padding: 15px; transition: background 0.2s, color 0.2s; box-shadow: 0 2px 6px rgba(33,183,1,0.07); font-family: system-ui, Arial, sans-serif; }
        .submit-btn:hover { background: #43e97b; color: #232b3e; }
        .message { padding: 15px; border-radius: 6px; margin-bottom: 20px; font-weight: 500; text-align: center; }
        .message.success { background: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .message.error { background: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        @media (max-width: 800px) { .sidebar { width: 60px; padding: 20px 0 0 0; } .sidebar h2 { display: none; } .sidebar ul li a { padding: 10px 10px; font-size: 1.2em; text-align: center; } .main-content { margin-left: 70px; padding: 20px 10px; } }
        @media (max-width: 600px) { .main-content { margin-left: 0; padding: 10px 2vw; } .sidebar { position: static; width: 100%; height: auto; flex-direction: row; } .sidebar ul { display: flex; flex-direction: row; justify-content: space-around; } .sidebar ul li { margin: 0; } }
        .view-users-btn {
            background: #21b701;
            color: #fff;
            border-radius: 6px;
            font-size: 1.08rem;
            font-weight: 600;
            border: none;
            padding: 10px 22px;
            text-decoration: none;
            display: inline-block;
            transition: background 0.2s, color 0.2s;
            box-shadow: 0 2px 6px rgba(33,183,1,0.07);
            margin-bottom: 0;
        }
        .view-users-btn:hover {
            background: #43e97b;
            color: #232b3e;
            text-decoration: none;
        }
    </style>
</head>
<body>
    <div class="sidebar">
        <h2>Coadmin</h2>
        <ul>
            <li><a href="dashboard_coadmin.jsp"><i class="fa fa-chart-line"></i> Dashboards</a></li>
            <li><a href="addCustomer_coadmin.jsp"><i class="fa fa-user-plus"></i> Add Customer</a></li>
            <li><a href="editCustomer_coadmin.jsp"><i class="fa fa-user-edit"></i> Edit Customer</a></li>
            <li><a href="viewAccount.jsp"><i class="fa fa-id-card"></i> View Account</a></li>
            <li><a href="addItems_coadmin.jsp"><i class="fa fa-boxes"></i> Manage Items</a></li>
            <li><a href="calculateBill.jsp"><i class="fa fa-calculator"></i> Calculate Bill</a></li>
            <li><a href="printBill.jsp"><i class="fa fa-print"></i> Print/View Bills</a></li>
            <li><a href="help.jsp"><i class="fa fa-question-circle"></i> Help</a></li>
            <li><a href="logout"><i class="fa fa-sign-out-alt"></i> Logout</a></li>
        </ul>
    </div>
    <div class="main-content">
        <div style="display: flex; justify-content: flex-end; max-width: 1100px; margin: 0 auto 0 auto;">
            <div class="user-info" style="background: #232b3e; color: #21b701; border-radius: 25px; padding: 8px 22px; font-size: 1.18em; display: flex; align-items: center; gap: 8px; margin-top: 10px;">
                <i class="fa fa-user-shield" style="color: #21b701; font-size: 1.3em;"></i>
                Role: coadmin
            </div>
        </div>
        <div style="text-align:left; max-width: 1100px; margin: 0 auto 20px auto;">
            <h1 style="color:#232b3e; font-size:2.2rem; margin:0;">Pahana Edu Bookshop Management System</h1>
        </div>
        <div style="display: flex; justify-content: flex-end; max-width: 1100px; margin: 0 auto 20px auto;">
            <a href="ViewItemsServlet" class="view-users-btn" style="margin:0;">
                <i class="fa fa-users"></i> View Items
            </a>
        </div>
        <div style="display: flex; justify-content: center; align-items: flex-start; min-height: 60vh;">
            <form class="form-area" action="AddItemServlet" method="post">
                <h2 style="text-align:center; color:#232b3e;">Add New Item</h2>
                <% String message = (String) request.getAttribute("message");
                   if (message != null) { %>
                    <div class="message <%= message.contains("fail") || message.contains("Invalid") ? "error" : "success" %>"><%= message %></div>
                <% } %>
                <div class="input-card">
                    <label for="name">Item Name</label>
                    <input type="text" id="name" name="name" required>
                </div>
                <div class="input-card">
                    <label for="category">Category</label>
                    <select id="category" name="category" required onchange="handleCategoryChange()">
                        <option value="">-- Select Category --</option>
                        <option value="Textbooks">Textbooks</option>
                        <option value="Novels">Novels</option>
                        <option value="Stationery">Stationery</option>
                        <option value="Reference">Reference</option>
                        <option value="Children">Children</option>
                        <option value="Comics">Comics</option>
                        <option value="Magazines">Magazines</option>
                        <option value="Other">Other</option>
                    </select>
                    <input type="text" id="customCategory" name="customCategory" placeholder="Enter custom category" style="display:none; margin-top:10px;" />
                </div>
                <div class="input-card">
                    <label for="description">Description</label>
                    <textarea id="description" name="description" required></textarea>
                </div>
                <div class="input-card">
                    <label for="price">Price</label>
                    <input type="number" id="price" name="price" step="0.01" min="0" required>
                </div>
                <div class="input-card">
                    <label for="stock">Stock</label>
                    <input type="number" id="stock" name="stock" min="0" required>
                </div>
                <div class="button-card">
                    <button type="submit" class="submit-btn">Add Item</button>
                </div>
            </form>
        </div>
    </div>
    <script>
function handleCategoryChange() {
    var categorySelect = document.getElementById('category');
    var customInput = document.getElementById('customCategory');
    if (categorySelect.value === 'Other') {
        customInput.style.display = 'block';
        customInput.required = true;
    } else {
        customInput.style.display = 'none';
        customInput.required = false;
        customInput.value = '';
    }
}
// On submit, if customCategory is shown, copy its value to category
var form = document.querySelector('.form-area').closest('form');
form.addEventListener('submit', function(e) {
    var categorySelect = document.getElementById('category');
    var customInput = document.getElementById('customCategory');
    if (categorySelect.value === 'Other' && customInput.value.trim() !== '') {
        categorySelect.value = customInput.value.trim();
    }
});
</script>
</body>
</html> 