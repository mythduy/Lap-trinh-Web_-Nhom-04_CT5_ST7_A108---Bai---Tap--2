package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import entity.Category;
import service.CategoryService;
import model.AppUser;

@WebServlet("/category/edit")
public class CategoryEditServlet extends HttpServlet {
    private CategoryService categoryService = new CategoryService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        Category category = categoryService.getCategoryById(id);
        request.setAttribute("category", category);
        request.getRequestDispatcher("/WEB-INF/category_edit.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name");
        HttpSession session = request.getSession(false);
        AppUser user = (AppUser) session.getAttribute("user");
        Category category = categoryService.getCategoryById(id);
        if (category == null || (user.getRoleId() != 3 && category.getUser().getId() != user.getId())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        category.setName(name);
        categoryService.updateCategory(category);
        response.sendRedirect(request.getContextPath() + "/" + (user.getRoleId() == 2 ? "manager" : user.getRoleId() == 3 ? "admin" : "user") + "/home");
    }
}
