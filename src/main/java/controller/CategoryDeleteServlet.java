package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import service.CategoryService;
import model.EntityUser;

@WebServlet("/category/delete")
public class CategoryDeleteServlet extends HttpServlet {
    private CategoryService categoryService = new CategoryService();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        HttpSession session = request.getSession(false);
        EntityUser user = (EntityUser) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        categoryService.deleteCategory(id, user.getId());
        response.sendRedirect(request.getContextPath() + "/" + (user.getRoleId() == 2 ? "manager" : user.getRoleId() == 3 ? "admin" : "user") + "/home");
    }
}
