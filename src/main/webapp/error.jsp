<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error - Pahana Edu</title>
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,700&display=swap" rel="stylesheet">
    <style>
        body {
            margin: 0;
            font-family: 'Roboto', Arial, sans-serif;
            background: linear-gradient(120deg, #232b3e, #1a2233);
            color: #d7dee5;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .error-container {
            background: #ffffffe7;
            border-radius: 20px;
            padding: 40px;
            text-align: center;
            box-shadow: 0 8px 32px rgba(0,0,0,0.1);
            max-width: 500px;
            width: 90%;
        }
        .error-icon {
            font-size: 4rem;
            color: #e74c3c;
            margin-bottom: 20px;
        }
        .error-title {
            color: #232b3e;
            font-size: 2rem;
            margin-bottom: 15px;
            font-weight: 700;
        }
        .error-message {
            color: #666;
            font-size: 1.1rem;
            margin-bottom: 30px;
            line-height: 1.6;
        }
        .back-button {
            background: #21b701;
            color: #fff;
            padding: 12px 30px;
            border-radius: 25px;
            text-decoration: none;
            font-size: 1.1rem;
            font-weight: 600;
            transition: all 0.3s ease;
            display: inline-block;
            border: 2px solid #21b701;
        }
        .back-button:hover {
            background: #fff;
            color: #21b701;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(33,183,1,0.3);
        }
        .error-details {
            margin-top: 20px;
            padding: 15px;
            background: #f8f9fa;
            border-radius: 10px;
            border-left: 4px solid #e74c3c;
        }
        .error-details h4 {
            color: #232b3e;
            margin: 0 0 10px 0;
            font-size: 1rem;
        }
        .error-details p {
            color: #666;
            margin: 0;
            font-size: 0.9rem;
        }
        @media (max-width: 600px) {
            .error-container {
                padding: 30px 20px;
                margin: 20px;
            }
            .error-title {
                font-size: 1.5rem;
            }
            .error-message {
                font-size: 1rem;
            }
        }
    </style>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <div class="error-container">
        <div class="error-icon">
            <i class="fa fa-exclamation-triangle"></i>
        </div>
        <h1 class="error-title">Oops! Something went wrong</h1>
        <p class="error-message">
            We encountered an unexpected error. This might be due to:
        </p>
        <div class="error-details">
            <h4><i class="fa fa-info-circle"></i> Possible reasons:</h4>
            <p>• Invalid user session or expired login</p>
            <p>• Insufficient permissions for this page</p>
            <p>• System temporarily unavailable</p>
        </div>
        <a href="login.jsp" class="back-button">
            <i class="fa fa-sign-in-alt"></i> Back to Login
        </a>
    </div>
</body>
</html> 