package service;

import dao.CategoryDao;
import entity.Category;
import entity.User;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class CategoryService {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("dataSource");
    private CategoryDao categoryDao = new CategoryDao();
    public List<Category> getAllCategories() {
        return categoryDao.getAllCategories();
    }
    public List<Category> getCategoriesByUserId(int userId) {
        return categoryDao.getCategoriesByUserId(userId);
    }
    public boolean addCategory(Category category) {
        return categoryDao.addCategory(category);
    }
    public boolean updateCategory(Category category) {
        return categoryDao.updateCategory(category);
    }
    public boolean deleteCategory(long categoryId, int userId) {
        return categoryDao.deleteCategory(categoryId, userId);
    }
    public Category getCategoryById(long id) {
        return categoryDao.getCategoryById(id);
    }
    public User getEntityUserById(long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }
}
