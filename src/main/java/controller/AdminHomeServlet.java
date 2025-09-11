package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import entity.Category;
import service.CategoryService;

@WebServlet("/admin/home")
public class AdminHomeServlet extends HttpServlet {
    private CategoryService categoryService = new CategoryService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = categoryService.getAllCategories();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/WEB-INF/admin_home.jsp").forward(request, response);
    }
}
