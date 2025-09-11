package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import entity.Category;
import service.CategoryService;
import model.EntityUser;

@WebServlet("/manager/home")
public class ManagerHomeServlet extends HttpServlet {
    private CategoryService categoryService = new CategoryService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        EntityUser user = (EntityUser) session.getAttribute("user");
        List<Category> categories = categoryService.getCategoriesByUserId(user.getId());
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/WEB-INF/manager_home.jsp").forward(request, response);
    }
}
