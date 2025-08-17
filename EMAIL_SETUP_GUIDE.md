# 📧 Email Setup Guide for Pahana Edu Bookshop

This guide will help you set up automatic email notifications for new customers.

## 🎯 What This Feature Does

The system now sends automatic emails in two scenarios:

### 1. **Welcome Emails for New Customers**
When a new customer is added to the system, an automatic welcome email will be sent to their email address containing:
- Welcome message
- Account details
- Information about the bookshop
- Next steps

### 2. **Bill Receipt Emails**
When a bill is saved, printed, or updated, an automatic bill receipt email will be sent to the customer containing:
- Complete bill details
- Customer information
- Itemized list of purchases
- Total amounts and balance
- Professional HTML formatting

## ⚙️ Setup Instructions

### Step 1: Gmail Account Setup

1. **Use a Gmail Account**: You need a Gmail account to send emails
2. **Enable 2-Step Verification**:
   - Go to your Google Account settings
   - Navigate to Security
   - Enable "2-Step Verification"

3. **Generate App Password**:
   - Go to Google Account settings
   - Navigate to Security
   - Click on "App passwords" (under 2-Step Verification)
   - Select "Mail" and "Other (Custom name)"
   - Name it "Pahana Bookshop"
   - Copy the 16-character password

### Step 2: Update Email Configuration

1. **Open the file**: `src/main/resources/email.properties`

2. **Update the settings**:
   ```properties
   email.from=your-gmail@gmail.com
   email.password=your-16-character-app-password
   ```

   **Example**:
   ```properties
   email.from=bookshop@gmail.com
   email.password=abcd efgh ijkl mnop
   ```

### Step 3: Test the Setup

1. **Compile the project**:
   ```bash
   mvn clean compile
   ```

2. **Add a new customer** with an email address through the system

3. **Create a bill** for that customer and save it

4. **Print a bill** to test bill email functionality

5. **Check the console logs** for email status messages

6. **Check the customer's email** for both welcome and bill receipt messages

## 🔧 Troubleshooting

### Common Issues:

1. **"Authentication Failed" Error**:
   - Make sure you're using an App Password, not your regular Gmail password
   - Ensure 2-Step Verification is enabled
   - Double-check the email and password in `email.properties`

2. **"Email configuration file not found"**:
   - Make sure `email.properties` is in `src/main/resources/`
   - Rebuild the project after adding the file

3. **"Email could not be sent"**:
   - Check your internet connection
   - Verify Gmail SMTP settings
   - Check console logs for specific error messages

### Debug Information:

The system will log email activities in the console:
- `[EMAIL DEBUG]` - Information about email sending process
- `[EMAIL ERROR]` - Error messages if email fails

## 📧 Email Templates

### Welcome Email Template
The welcome email includes:
- Professional HTML formatting
- Customer's account details
- Welcome message
- Information about the bookshop
- Contact information

### Bill Receipt Email Template
The bill receipt email includes:
- Professional HTML formatting with bookshop branding
- Complete bill information (number, date, time)
- Customer details (account number, name, address, phone, email)
- Itemized table of purchased items with quantities and prices
- Total amounts (total, paid, balance)
- Thank you message
- Professional styling matching the bookshop theme

## 🚀 How It Works

### Welcome Email Process
1. **Customer Registration**: When a customer is added through the system
2. **Email Trigger**: The system automatically detects the new customer
3. **Email Generation**: A personalized welcome email is created
4. **Email Sending**: The email is sent via Gmail SMTP
5. **Confirmation**: Success/failure messages are logged

### Bill Email Process
1. **Bill Operation**: When a bill is saved, printed, or updated
2. **Email Trigger**: The system automatically detects the bill operation
3. **Customer Lookup**: Retrieves customer information and email
4. **Email Generation**: Creates a detailed bill receipt with all items and totals
5. **Email Sending**: The bill receipt is sent via Gmail SMTP
6. **Confirmation**: Success/failure messages are logged

## 🔒 Security Notes

- **App Passwords**: More secure than regular passwords
- **SMTP with TLS**: Encrypted email transmission
- **No Password Storage**: Passwords are only in the properties file
- **Error Handling**: Failed emails don't break the customer registration

## 📞 Support

If you encounter issues:
1. Check the console logs for error messages
2. Verify your Gmail settings
3. Test with a simple email first
4. Ensure all dependencies are properly installed

---

**Note**: This feature requires an active internet connection and a valid Gmail account with App Password authentication. 