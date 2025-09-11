package dao;

import entity.Category;
import entity.User;
import jakarta.persistence.*;
import java.util.*;

public class CategoryDao {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("dataSource");

    public List<Category> getAllCategories() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT c FROM Category c", Category.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Category> getCategoriesByUserId(int userId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT c FROM Category c WHERE c.user.id = :userId", Category.class)
                    .setParameter("userId", (long)userId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public boolean addCategory(Category category) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(category);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    public boolean updateCategory(Category category) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(category);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    public boolean deleteCategory(long categoryId, int userId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Category category = em.find(Category.class, categoryId);
            if (category != null && (category.getUser().getId() == userId || category.getUser().getRoleId() == 3)) {
                em.remove(category);
                tx.commit();
                return true;
            }
            tx.rollback();
            return false;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    public Category getCategoryById(long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Category.class, id);
        } finally {
            em.close();
        }
    }
}
