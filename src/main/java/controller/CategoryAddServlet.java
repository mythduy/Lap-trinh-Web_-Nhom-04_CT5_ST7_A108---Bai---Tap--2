package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import entity.Category;
import service.CategoryService;
import model.AppUser;

@WebServlet("/category/add")
public class CategoryAddServlet extends HttpServlet {
    private CategoryService categoryService = new CategoryService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/category_add.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        HttpSession session = request.getSession(false);
        AppUser user = (AppUser) session.getAttribute("user");
        if (name == null || name.trim().isEmpty()) {
            request.setAttribute("error", "Category name is required");
            request.getRequestDispatcher("/WEB-INF/category_add.jsp").forward(request, response);
            return;
        }
        Category category = new Category();
        category.setName(name);
        entity.User entityUser = categoryService.getEntityUserById(user.getId());
        if (entityUser == null) {
            request.setAttribute("error", "User not found in database");
            request.getRequestDispatcher("/WEB-INF/category_add.jsp").forward(request, response);
            return;
        }
        category.setUser(entityUser);
        categoryService.addCategory(category);
        response.sendRedirect(request.getContextPath() + "/" + (user.getRoleId() == 2 ? "manager" : user.getRoleId() == 3 ? "admin" : "user") + "/home");
    }
}
