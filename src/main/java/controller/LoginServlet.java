package controller;

import service.UserService;
import model.AppUser;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            response.sendRedirect("home.jsp");
            return;
        }
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Debug: log thông tin đầu vào
        System.out.println("Login attempt: username=" + username + ", password=" + password);

        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Username and password are required");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        boolean authenticated = userService.authenticateUser(username, password);
        System.out.println("Authenticated: " + authenticated);

        if (authenticated) {
            AppUser user = userService.getUserByUsername(username);
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            // Chuyển hướng theo roleId
            int roleId = user.getRoleId();
            if (roleId == 1) {
                response.sendRedirect(request.getContextPath() + "/user/home");
            } else if (roleId == 2) {
                response.sendRedirect(request.getContextPath() + "/manager/home");
            } else if (roleId == 3) {
                response.sendRedirect(request.getContextPath() + "/admin/home");
            } else {
                response.sendRedirect("login.jsp");
            }
        } else {
            // Đảm bảo luôn báo lỗi nếu sai tài khoản hoặc mật khẩu
            request.setAttribute("error", "Invalid username or password");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
