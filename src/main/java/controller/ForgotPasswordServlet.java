package controller;

import service.UserService;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("forgot_password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("error", "Email is required");
            request.getRequestDispatcher("forgot_password.jsp").forward(request, response);
            return;
        }

        User user = userService.getUserByEmail(email);
        if (user == null) {
            request.setAttribute("error", "Email not found");
            request.getRequestDispatcher("forgot_password.jsp").forward(request, response);
        } else {
            // Chuyển sang trang đổi mật khẩu, truyền username/email qua request
            request.setAttribute("email", email);
            request.getRequestDispatcher("reset_password.jsp").forward(request, response);
        }
    }
}
