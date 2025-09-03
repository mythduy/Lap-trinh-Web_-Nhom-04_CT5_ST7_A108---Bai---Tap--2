<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Forgot Password</title>
    <style>
        /* ...bạn có thể dùng lại style từ login.jsp... */
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; }
        .container { background-color: white; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1); width: 350px; margin: 100px auto; }
        h2 { text-align: center; color: #333; }
        .error-message { color: red; text-align: center; }
        .success-message { color: green; text-align: center; }
        input[type="email"] { width: 100%; padding: 10px; margin: 8px 0; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        button { background-color: #4CAF50; color: white; padding: 14px 20px; margin: 8px 0; border: none; border-radius: 4px; cursor: pointer; width: 100%; }
        button:hover { background-color: #45a049; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Forgot Password</h2>
        <% if(request.getAttribute("error") != null) { %>
            <p class="error-message"><%= request.getAttribute("error") %></p>
        <% } %>
        <% if(request.getAttribute("success") != null) { %>
            <p class="success-message"><%= request.getAttribute("success") %></p>
        <% } %>
        <form action="forgot-password" method="post">
            <label for="email">Enter your email:</label>
            <input type="email" id="email" name="email" required>
            <button type="submit">Send</button>
        </form>
        <div style="text-align:center;margin-top:15px;">
            <a href="login.jsp">Back to Login</a>
        </div>
    </div>
</body>
</html>
