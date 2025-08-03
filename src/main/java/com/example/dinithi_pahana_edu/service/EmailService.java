package com.example.dinithi_pahana_edu.service;

import com.example.dinithi_pahana_edu.model.Customer;
import com.example.dinithi_pahana_edu.model.Bill;
import com.example.dinithi_pahana_edu.model.BillItem;

import javax.mail.*;
import java.util.List;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EmailService {
    
    private Properties emailConfig;
    
    public EmailService() {
        loadEmailConfig();
    }
    
    private void loadEmailConfig() {
        emailConfig = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("email.properties")) {
            if (input == null) {
                System.err.println("Email configuration file not found!");
                return;
            }
            emailConfig.load(input);
        } catch (IOException e) {
            System.err.println("Error loading email configuration: " + e.getMessage());
        }
    }
    
    public boolean sendWelcomeEmail(Customer customer) {
        if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
            System.out.println("Customer email is null or empty. Cannot send welcome email.");
            return false;
        }
        
        try {
            // Email server properties
            Properties props = new Properties();
            props.put("mail.smtp.host", emailConfig.getProperty("email.smtp.host"));
            props.put("mail.smtp.port", emailConfig.getProperty("email.smtp.port"));
            props.put("mail.smtp.auth", emailConfig.getProperty("email.smtp.auth"));
            props.put("mail.smtp.starttls.enable", emailConfig.getProperty("email.smtp.starttls.enable"));
            props.put("mail.smtp.starttls.required", emailConfig.getProperty("email.smtp.starttls.required"));
            props.put("mail.smtp.ssl.trust", emailConfig.getProperty("email.smtp.ssl.trust"));
            
            // Create session
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                        emailConfig.getProperty("email.from"),
                        emailConfig.getProperty("email.password")
                    );
                }
            });
            
            // Create message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailConfig.getProperty("email.from")));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(customer.getEmail()));
            message.setSubject("Welcome to Pahana Edu Bookshop!");
            
            // Create HTML email content
            String htmlContent = createWelcomeEmailContent(customer);
            message.setContent(htmlContent, "text/html; charset=UTF-8");
            
            // Send email
            Transport.send(message);
            System.out.println("Welcome email sent successfully to: " + customer.getEmail());
            return true;
            
        } catch (Exception e) {
            System.err.println("Error sending welcome email: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    private String createWelcomeEmailContent(Customer customer) {
        return "<!DOCTYPE html>" +
               "<html>" +
               "<head>" +
               "    <meta charset='UTF-8'>" +
               "    <title>Welcome to Pahana Edu Bookshop</title>" +
               "    <style>" +
               "        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }" +
               "        .container { max-width: 600px; margin: 0 auto; padding: 20px; }" +
               "        .header { background: #2c3e50; color: white; padding: 20px; text-align: center; }" +
               "        .content { padding: 20px; background: #f9f9f9; }" +
               "        .footer { background: #34495e; color: white; padding: 15px; text-align: center; font-size: 12px; }" +
               "        .highlight { background: #3498db; color: white; padding: 10px; border-radius: 5px; }" +
               "    </style>" +
               "</head>" +
               "<body>" +
               "    <div class='container'>" +
               "        <div class='header'>" +
               "            <h1>🎉 Welcome to Pahana Edu Bookshop! 🎉</h1>" +
               "        </div>" +
               "        <div class='content'>" +
               "            <h2>Dear " + customer.getName() + ",</h2>" +
               "            <p>Thank you for registering with <strong>Pahana Edu Bookshop</strong>! We're excited to have you as our valued customer.</p>" +
               "            <div class='highlight'>" +
               "                <h3>Your Account Details:</h3>" +
               "                <p><strong>Account Number:</strong> " + customer.getAccountNumber() + "</p>" +
               "                <p><strong>Name:</strong> " + customer.getName() + "</p>" +
               "                <p><strong>Address:</strong> " + customer.getAddress() + "</p>" +
               "                <p><strong>Phone:</strong> " + customer.getTelephone() + "</p>" +
               "                <p><strong>Email:</strong> " + customer.getEmail() + "</p>" +
               "            </div>" +
               "            <h3>What's Next?</h3>" +
               "            <ul>" +
               "                <li>Visit our bookshop to explore our wide collection of educational materials</li>" +
               "                <li>Enjoy special discounts and offers available to our registered customers</li>" +
               "                <li>Get notified about new arrivals and promotions</li>" +
               "                <li>Build up your purchase history for future benefits</li>" +
               "            </ul>" +
               "            <p>If you have any questions or need assistance, please don't hesitate to contact us.</p>" +
               "            <p>Best regards,<br><strong>The Pahana Edu Bookshop Team</strong></p>" +
               "        </div>" +
               "        <div class='footer'>" +
               "            <p>This is an automated message. Please do not reply to this email.</p>" +
               "            <p>© 2024 Pahana Edu Bookshop. All rights reserved.</p>" +
               "        </div>" +
               "    </div>" +
               "</body>" +
               "</html>";
    }
    
    public boolean sendBillEmail(Customer customer, Bill bill, List<BillItem> billItems) {
        if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
            System.out.println("Customer email is null or empty. Cannot send bill email.");
            return false;
        }
        
        try {
            // Email server properties
            Properties props = new Properties();
            props.put("mail.smtp.host", emailConfig.getProperty("email.smtp.host"));
            props.put("mail.smtp.port", emailConfig.getProperty("email.smtp.port"));
            props.put("mail.smtp.auth", emailConfig.getProperty("email.smtp.auth"));
            props.put("mail.smtp.starttls.enable", emailConfig.getProperty("email.smtp.starttls.enable"));
            props.put("mail.smtp.starttls.required", emailConfig.getProperty("email.smtp.starttls.required"));
            props.put("mail.smtp.ssl.trust", emailConfig.getProperty("email.smtp.ssl.trust"));
            
            // Create session
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                        emailConfig.getProperty("email.from"),
                        emailConfig.getProperty("email.password")
                    );
                }
            });
            
            // Create message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailConfig.getProperty("email.from")));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(customer.getEmail()));
            message.setSubject("Bill Receipt - Pahana Edu Bookshop (Bill #" + bill.getBillNumber() + ")");
            
            // Create HTML email content
            String htmlContent = createBillEmailContent(customer, bill, billItems);
            message.setContent(htmlContent, "text/html; charset=UTF-8");
            
            // Send email
            Transport.send(message);
            System.out.println("Bill email sent successfully to: " + customer.getEmail());
            return true;
            
        } catch (Exception e) {
            System.err.println("Error sending bill email: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    private String createBillEmailContent(Customer customer, Bill bill, List<BillItem> billItems) {
        StringBuilder itemsHtml = new StringBuilder();
        double total = 0.0;
        
        for (int i = 0; i < billItems.size(); i++) {
            BillItem item = billItems.get(i);
            double itemTotal = item.getQuantity() * item.getPrice();
            total += itemTotal;
            
            itemsHtml.append("<tr>")
                    .append("<td style='padding: 8px; border: 1px solid #ddd; text-align: center;'>").append(i + 1).append("</td>")
                    .append("<td style='padding: 8px; border: 1px solid #ddd;'>").append(item.getItemName()).append("</td>")
                    .append("<td style='padding: 8px; border: 1px solid #ddd; text-align: center;'>").append(item.getQuantity()).append("</td>")
                    .append("<td style='padding: 8px; border: 1px solid #ddd; text-align: right;'>").append(String.format("%.2f", item.getPrice())).append("</td>")
                    .append("<td style='padding: 8px; border: 1px solid #ddd; text-align: right;'>").append(String.format("%.2f", itemTotal)).append("</td>")
                    .append("</tr>");
        }
        
        return "<!DOCTYPE html>" +
               "<html>" +
               "<head>" +
               "    <meta charset='UTF-8'>" +
               "    <title>Bill Receipt - Pahana Edu Bookshop</title>" +
               "    <style>" +
               "        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; margin: 0; padding: 20px; }" +
               "        .container { max-width: 700px; margin: 0 auto; background: white; border: 2px solid #2c3e50; border-radius: 10px; overflow: hidden; }" +
               "        .header { background: #2c3e50; color: white; padding: 20px; text-align: center; }" +
               "        .header h1 { margin: 0; font-size: 24px; }" +
               "        .header h2 { margin: 5px 0 0 0; font-size: 18px; font-weight: normal; }" +
               "        .content { padding: 20px; }" +
               "        .bill-info, .customer-info { margin-bottom: 20px; padding: 15px; background: #f8f9fa; border-radius: 5px; }" +
               "        .bill-info h3, .customer-info h3 { margin-top: 0; color: #2c3e50; }" +
               "        .items-table { width: 100%; border-collapse: collapse; margin: 20px 0; }" +
               "        .items-table th { background: #34495e; color: white; padding: 12px; text-align: left; }" +
               "        .items-table td { padding: 8px; border: 1px solid #ddd; }" +
               "        .items-table tr:nth-child(even) { background: #f9f9f9; }" +
               "        .total-section { text-align: right; margin-top: 20px; padding: 15px; background: #ecf0f1; border-radius: 5px; }" +
               "        .total-section div { margin: 5px 0; font-size: 16px; }" +
               "        .total-section .grand-total { font-size: 20px; font-weight: bold; color: #2c3e50; }" +
               "        .footer { background: #34495e; color: white; padding: 15px; text-align: center; font-size: 12px; }" +
               "        .thank-you { text-align: center; margin: 20px 0; padding: 15px; background: #27ae60; color: white; border-radius: 5px; font-size: 18px; }" +
               "    </style>" +
               "</head>" +
               "<body>" +
               "    <div class='container'>" +
               "        <div class='header'>" +
               "            <h1>📚 Pahana Edu Bookshop</h1>" +
               "            <h2>Bill Receipt</h2>" +
               "        </div>" +
               "        <div class='content'>" +
               "            <div class='bill-info'>" +
               "                <h3>📋 Bill Information</h3>" +
               "                <p><strong>Bill Number:</strong> " + bill.getBillNumber() + "</p>" +
               "                <p><strong>Date & Time:</strong> " + bill.getBillDateTime() + "</p>" +
               "                </div>" +
               "            <div class='customer-info'>" +
               "                <h3>👤 Customer Information</h3>" +
               "                <p><strong>Account Number:</strong> " + customer.getAccountNumber() + "</p>" +
               "                <p><strong>Name:</strong> " + customer.getName() + "</p>" +
               "                <p><strong>Address:</strong> " + customer.getAddress() + "</p>" +
               "                <p><strong>Phone:</strong> " + customer.getTelephone() + "</p>" +
               "                <p><strong>Email:</strong> " + customer.getEmail() + "</p>" +
               "            </div>" +
               "            <table class='items-table'>" +
               "                <thead>" +
               "                    <tr>" +
               "                        <th>#</th>" +
               "                        <th>Item Name</th>" +
               "                        <th>Quantity</th>" +
               "                        <th>Unit Price</th>" +
               "                        <th>Total</th>" +
               "                    </tr>" +
               "                </thead>" +
               "                <tbody>" +
               itemsHtml.toString() +
               "                </tbody>" +
               "            </table>" +
               "            <div class='total-section'>" +
               "                <div><strong>Total Amount:</strong> Rs. " + String.format("%.2f", bill.getTotalAmount()) + "</div>" +
               "                <div><strong>Paid Amount:</strong> Rs. " + String.format("%.2f", bill.getPaidAmount()) + "</div>" +
               "                <div class='grand-total'><strong>Balance:</strong> Rs. " + String.format("%.2f", bill.getBalance()) + "</div>" +
               "            </div>" +
               "            <div class='thank-you'>" +
               "                🙏 Thank you for your purchase! 🙏" +
               "            </div>" +
               "        </div>" +
               "        <div class='footer'>" +
               "            <p>This is an automated bill receipt. Please keep this for your records.</p>" +
               "            <p>© 2024 Pahana Edu Bookshop. All rights reserved.</p>" +
               "        </div>" +
               "    </div>" +
               "</body>" +
               "</html>";
    }
} 