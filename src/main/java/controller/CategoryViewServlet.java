package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import entity.Category;
import service.CategoryService;

@WebServlet("/category/view")
public class CategoryViewServlet extends HttpServlet {
    private CategoryService categoryService = new CategoryService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        Category category = categoryService.getCategoryById(id);
        request.setAttribute("category", category);
        request.getRequestDispatcher("/WEB-INF/category_view.jsp").forward(request, response);
    }
}
