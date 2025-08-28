<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f4f4f4;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h2 {
            color: #333;
        }
        .logout {
            float: right;
            padding: 10px 15px;
            background-color: #f44336;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
        .user-info {
            margin-top: 20px;
            padding: 15px;
            background-color: #f9f9f9;
            border-radius: 4px;
        }
    </style>
</head>
<body>
    <%
        // Check if user is logged in
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
    %>
    <div class="container">
        <a href="logout" class="logout">Logout</a>
        <h2>Welcome, <%= user.getUsername() %>!</h2>
        <div class="user-info">
            <p><strong>Username:</strong> <%= user.getUsername() %></p>
            <p><strong>Email:</strong> <%= user.getEmail() %></p>
            <p><strong>User ID:</strong> <%= user.getId() %></p>
        </div>
    </div>
</body>
</html>
