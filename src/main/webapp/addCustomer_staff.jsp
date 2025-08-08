<%@ page session="true" %>
<%
    com.example.dinithi_pahana_edu.model.User user = (com.example.dinithi_pahana_edu.model.User) session.getAttribute("user");
    if (user == null || !"staff".equalsIgnoreCase(user.getRole())) {
        response.sendRedirect("error.jsp");
        return;
    }
%>
<%
    // Get the next customer ID and account number for display
    com.example.dinithi_pahana_edu.service.CustomerService customerService = new com.example.dinithi_pahana_edu.service.CustomerService();
    java.util.List<com.example.dinithi_pahana_edu.model.Customer> allCustomers = customerService.getAllCustomers();
    int nextId = 1;
    int max = 0;
    String nextAccountNumber = "CUST000001";
    java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("CUST(\\d{6})");
    for (com.example.dinithi_pahana_edu.model.Customer c : allCustomers) {
        if (c.getId() > nextId) nextId = c.getId();
        String acc = c.getAccountNumber();
        if (acc != null) {
            java.util.regex.Matcher m = pattern.matcher(acc);
            if (m.matches()) {
                int num = Integer.parseInt(m.group(1));
                if (num > max) max = num;
            }
        }
    }
    nextId = allCustomers.isEmpty() ? 1 : nextId + 1;
    nextAccountNumber = String.format("CUST%06d", max + 1);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add New Customer - Staff</title>
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
            display: flex;
            flex-direction: column;
            gap: 22px;
            max-width: 700px;
            width: 100%;
            margin: 0 auto;
        }
        .input-card {
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 2px 8px rgba(44,62,80,0.10);
            border: 1px solid #e0e0e0;
            padding: 22px 28px 16px 28px;
            display: flex;
            flex-direction: column;
            width: 100%;
        }
        .input-card label {
            color: #232b3e;
            font-size: 1.08rem;
            font-weight: 500;
            margin-bottom: 7px;
            font-family: system-ui, Arial, sans-serif;
        }
        .input-card input, .input-card textarea {
            width: 100%;
            background: #fff;
            color: #232b3e;
            border: 1.5px solid #bdbdbd;
            font-size: 1.13rem;
            padding: 12px 14px;
            border-radius: 6px;
            transition: border-color 0.2s;
            box-sizing: border-box;
            font-family: system-ui, Arial, sans-serif;
        }
        .input-card textarea {
            min-height: 50px;
            resize: vertical;
        }
        .input-card input:focus, .input-card textarea:focus {
            border-color: #21b701;
            outline: none;
        }
        .input-card input.error, .input-card textarea.error {
            border-color: #dc3545;
            background-color: #fff5f5;
        }
        .input-card input.valid, .input-card textarea.valid {
            border-color: #28a745;
            background-color: #f8fff9;
        }
        .error-message {
            color: #dc3545;
            font-size: 0.9rem;
            margin-top: 5px;
            display: none;
            font-weight: 500;
        }
        .success-message {
            color: #28a745;
            font-size: 0.9rem;
            margin-top: 5px;
            display: none;
            font-weight: 500;
        }
        .validation-icon {
            position: absolute;
            right: 10px;
            top: 50%;
            transform: translateY(-50%);
            font-size: 1.2rem;
        }
        .input-wrapper {
            position: relative;
        }
        .button-card {
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 2px 8px rgba(44,62,80,0.10);
            border: 1px solid #e0e0e0;
            padding: 18px 28px 18px 28px;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .submit-btn {
            width: 100%;
            background: #21b701;
            color: #fff;
            border-radius: 6px;
            font-size: 1.18rem;
            font-weight: 700;
            border: none;
            padding: 15px;
            transition: background 0.2s, color 0.2s;
            box-shadow: 0 2px 6px rgba(33,183,1,0.07);
            font-family: system-ui, Arial, sans-serif;
            cursor: pointer;
        }
        .submit-btn:hover {
            background: #43e97b;
            color: #232b3e;
        }
        .submit-btn:disabled {
            background: #6c757d;
            cursor: not-allowed;
        }
        .message {
            padding: 15px;
            border-radius: 6px;
            margin-bottom: 20px;
            font-weight: 500;
            text-align: center;
        }
        .message.success {
            background: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        .message.error {
            background: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        @media (max-width: 800px) {
            .sidebar { width: 60px; padding: 20px 0 0 0; }
            .sidebar h2 { display: none; }
            .sidebar ul li a { padding: 10px 10px; font-size: 1.2em; text-align: center; }
            .main-content { margin-left: 70px; padding: 20px 10px; }
        }
        @media (max-width: 600px) {
            .main-content { margin-left: 0; padding: 10px 2vw; }
            .sidebar { position: static; width: 100%; height: auto; flex-direction: row; }
            .sidebar ul { display: flex; flex-direction: row; justify-content: space-around; }
            .sidebar ul li { margin: 0; }
        }
    </style>
</head>
<body>
    <jsp:include page="sidebar_staff.jspf" />
    <div class="main-content">
        <div class="header">
            <div>
                <h1>Pahana Edu Bookshop Management System</h1>
            </div>
            <div class="user-info">
                <i class="fa fa-user-shield"></i> <span>Role: <%= user.getRole() %></span>
            </div>
        </div>
        <div style="display: flex; justify-content: center; gap: 16px; margin-bottom: 24px; max-width: 700px; margin-left: auto; margin-right: auto;">
            <div style="
                flex: 1;
                background: #f5f8ff;
                border: 1.5px solid #dbeafe;
                border-radius: 10px;
                padding: 10px 0 6px 0;
                display: flex;
                flex-direction: column;
                align-items: center;
                min-width: 120px;
                box-shadow: 0 2px 8px #0001;
                justify-content: center;
                height: 48px;
            ">
                <span style="font-size:0.98em;color:#555;font-weight:500;margin-bottom:2px;display:flex;align-items:center;">
                    <i class="fa fa-id-badge" style="color:#1976d2;margin-right:5px;"></i>
                    Next Customer ID
                </span>
                <span style="font-size:1.15em;color:#1976d2;font-weight:700;"><%= nextId %></span>
            </div>
            <div style="
                flex: 1;
                background: #f5f8ff;
                border: 1.5px solid #dbeafe;
                border-radius: 10px;
                padding: 10px 0 6px 0;
                display: flex;
                flex-direction: column;
                align-items: center;
                min-width: 120px;
                box-shadow: 0 2px 8px #0001;
                justify-content: center;
                height: 48px;
            ">
                <span style="font-size:0.98em;color:#555;font-weight:500;margin-bottom:2px;display:flex;align-items:center;">
                    <i class="fa fa-hashtag" style="color:#1976d2;margin-right:5px;"></i>
                    Next Account Number
                </span>
                <span style="font-size:1.15em;color:#1976d2;font-weight:700;"><%= nextAccountNumber %></span>
            </div>
            <a href="ViewCustomerServlet"
               style="
                flex: 1;
                display: flex;
                align-items: center;
                justify-content: center;
                background:#1976d2;
                color:#fff;
                font-weight:600;
                border:none;
                border-radius:10px;
                font-size:1.02em;
                text-decoration:none;
                transition:background 0.2s;
                height: 48px;
                min-width: 120px;
                box-shadow: 0 2px 8px #0001;
                text-align: center;
            ">
                View Customers
            </a>
        </div>
        <% if (request.getAttribute("message") != null && "success".equals(request.getAttribute("messageType"))) { %>
            <div style="background:#d4edda; color:#155724; border:1px solid #c3e6cb; padding:12px 24px; border-radius:7px; margin-bottom:18px; font-weight:600; text-align:center; max-width:400px; margin-left:auto; margin-right:auto;">
                <i class="fa fa-check-circle" style="color:#21b701;margin-right:8px;"></i>
                <%= request.getAttribute("message") %>
            </div>
        <% } %>
        <div style="display: flex; justify-content: center; align-items: flex-start; min-height: 60vh;">
            <form class="form-area" action="addCustomer" method="post" id="customerForm">
                <div class="input-card">
                    <label for="name">Name *</label>
                    <div class="input-wrapper">
                        <input type="text" id="name" name="name" required>
                        <i class="fa fa-check validation-icon" style="color: #28a745; display: none;"></i>
                        <i class="fa fa-times validation-icon" style="color: #dc3545; display: none;"></i>
                    </div>
                    <div class="error-message" id="nameError"></div>
                    <div class="success-message" id="nameSuccess"></div>
                </div>
                <div class="input-card">
                    <label for="address">Address *</label>
                    <div class="input-wrapper">
                        <textarea id="address" name="address" required></textarea>
                        <i class="fa fa-check validation-icon" style="color: #28a745; display: none;"></i>
                        <i class="fa fa-times validation-icon" style="color: #dc3545; display: none;"></i>
                    </div>
                    <div class="error-message" id="addressError"></div>
                    <div class="success-message" id="addressSuccess"></div>
                </div>
                <div class="input-card">
                    <label for="telephone">Telephone Number *</label>
                    <div class="input-wrapper">
                        <input type="text" id="telephone" name="telephone" required maxlength="10" placeholder="0712345678">
                        <i class="fa fa-check validation-icon" style="color: #28a745; display: none;"></i>
                        <i class="fa fa-times validation-icon" style="color: #dc3545; display: none;"></i>
                    </div>
                    <div class="error-message" id="telephoneError"></div>
                    <div class="success-message" id="telephoneSuccess"></div>
                </div>
                <div class="input-card">
                    <label for="email">Email Address</label>
                    <div class="input-wrapper">
                        <input type="email" id="email" name="email" placeholder="customer@example.com">
                        <i class="fa fa-check validation-icon" style="color: #28a745; display: none;"></i>
                        <i class="fa fa-times validation-icon" style="color: #dc3545; display: none;"></i>
                    </div>
                    <div class="error-message" id="emailError"></div>
                    <div class="success-message" id="emailSuccess"></div>
                </div>
                <div class="button-card">
                    <button type="submit" class="submit-btn" id="submitBtn">Add Customer</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        // Store existing customers data for duplicate checking
        const existingCustomers = [
            <% for (com.example.dinithi_pahana_edu.model.Customer c : allCustomers) { %>
                {
                    name: '<%= c.getName() != null ? c.getName().replace("'", "\\'") : "" %>',
                    telephone: '<%= c.getTelephone() != null ? c.getTelephone() : "" %>',
                    email: '<%= c.getEmail() != null ? c.getEmail() : "" %>',
                    address: '<%= c.getAddress() != null ? c.getAddress().replace("'", "\\'") : "" %>'
                },
            <% } %>
        ];

        // Validation functions
        function validateName(name) {
            if (!name || name.trim().length === 0) {
                return { valid: false, message: 'Name is required' };
            }
            if (name.trim().length < 2) {
                return { valid: false, message: 'Name must be at least 2 characters long' };
            }
            
            // Check for duplicate name
            const duplicate = existingCustomers.find(c => 
                c.name.toLowerCase() === name.trim().toLowerCase()
            );
            if (duplicate) {
                return { valid: false, message: 'A customer with this name already exists' };
            }
            
            return { valid: true, message: 'Name is valid' };
        }

        function validateAddress(address) {
            if (!address || address.trim().length === 0) {
                return { valid: false, message: 'Address is required' };
            }
            if (address.trim().length < 5) {
                return { valid: false, message: 'Address must be at least 5 characters long' };
            }
            
            // Check for duplicate address
            const duplicate = existingCustomers.find(c => 
                c.address.toLowerCase() === address.trim().toLowerCase()
            );
            if (duplicate) {
                return { valid: false, message: 'A customer with this address already exists' };
            }
            
            return { valid: true, message: 'Address is valid' };
        }

        function validateTelephone(telephone) {
            if (!telephone || telephone.trim().length === 0) {
                return { valid: false, message: 'Telephone number is required' };
            }
            
            // Remove any non-digit characters
            const cleanTelephone = telephone.replace(/\D/g, '');
            
            if (cleanTelephone.length !== 10) {
                return { valid: false, message: 'Telephone number must be exactly 10 digits' };
            }
            
            if (!/^0[1-9]/.test(cleanTelephone)) {
                return { valid: false, message: 'Telephone number must start with 0 followed by 1-9' };
            }
            
            // Check for duplicate telephone
            const duplicate = existingCustomers.find(c => 
                c.telephone === cleanTelephone
            );
            if (duplicate) {
                return { valid: false, message: 'A customer with this telephone number already exists' };
            }
            
            return { valid: true, message: 'Telephone number is valid' };
        }

        function validateEmail(email) {
            if (!email || email.trim().length === 0) {
                return { valid: true, message: 'Email is optional' };
            }
            
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailRegex.test(email)) {
                return { valid: false, message: 'Please enter a valid email address' };
            }
            
            // Check for duplicate email
            const duplicate = existingCustomers.find(c => 
                c.email.toLowerCase() === email.trim().toLowerCase()
            );
            if (duplicate) {
                return { valid: false, message: 'A customer with this email already exists' };
            }
            
            return { valid: true, message: 'Email is valid' };
        }

        // Update field validation and styling
        function updateFieldValidation(fieldId, validation) {
            const field = document.getElementById(fieldId);
            const errorDiv = document.getElementById(fieldId + 'Error');
            const successDiv = document.getElementById(fieldId + 'Success');
            const checkIcon = field.parentElement.querySelector('.fa-check');
            const timesIcon = field.parentElement.querySelector('.fa-times');
            
            // Remove existing classes
            field.classList.remove('error', 'valid');
            errorDiv.style.display = 'none';
            successDiv.style.display = 'none';
            checkIcon.style.display = 'none';
            timesIcon.style.display = 'none';
            
            if (validation.valid) {
                field.classList.add('valid');
                successDiv.textContent = validation.message;
                successDiv.style.display = 'block';
                checkIcon.style.display = 'block';
            } else {
                field.classList.add('error');
                errorDiv.textContent = validation.message;
                errorDiv.style.display = 'block';
                timesIcon.style.display = 'block';
            }
        }

        // Real-time validation
        document.getElementById('name').addEventListener('input', function() {
            const validation = validateName(this.value);
            updateFieldValidation('name', validation);
            checkFormValidity();
        });

        document.getElementById('address').addEventListener('input', function() {
            const validation = validateAddress(this.value);
            updateFieldValidation('address', validation);
            checkFormValidity();
        });

        document.getElementById('telephone').addEventListener('input', function() {
            // Only allow digits
            this.value = this.value.replace(/\D/g, '');
            const validation = validateTelephone(this.value);
            updateFieldValidation('telephone', validation);
            checkFormValidity();
        });

        document.getElementById('email').addEventListener('input', function() {
            const validation = validateEmail(this.value);
            updateFieldValidation('email', validation);
            checkFormValidity();
        });

        // Check overall form validity
        function checkFormValidity() {
            const name = document.getElementById('name').value;
            const address = document.getElementById('address').value;
            const telephone = document.getElementById('telephone').value;
            const email = document.getElementById('email').value;
            
            const nameValid = validateName(name).valid;
            const addressValid = validateAddress(address).valid;
            const telephoneValid = validateTelephone(telephone).valid;
            const emailValid = validateEmail(email).valid;
            
            const submitBtn = document.getElementById('submitBtn');
            
            if (nameValid && addressValid && telephoneValid && emailValid) {
                submitBtn.disabled = false;
            } else {
                submitBtn.disabled = true;
            }
        }

        // Form submission validation
        document.getElementById('customerForm').addEventListener('submit', function(e) {
            const name = document.getElementById('name').value;
            const address = document.getElementById('address').value;
            const telephone = document.getElementById('telephone').value;
            const email = document.getElementById('email').value;
            
            const nameValid = validateName(name).valid;
            const addressValid = validateAddress(address).valid;
            const telephoneValid = validateTelephone(telephone).valid;
            const emailValid = validateEmail(email).valid;
            
            if (!nameValid || !addressValid || !telephoneValid || !emailValid) {
                e.preventDefault();
                alert('Please fix all validation errors before submitting.');
                return false;
            }
        });

        // Initialize form state
        checkFormValidity();
    </script>
</body>
</html> 