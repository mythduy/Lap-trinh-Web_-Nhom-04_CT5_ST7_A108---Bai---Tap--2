package dao;

import model.AppUser;
import entity.User;
import jakarta.persistence.*;
import java.util.List;

public class UserDao {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("dataSource");

    private AppUser toModelUser(entity.User entityUser) {
        if (entityUser == null) return null;
        AppUser user = new AppUser();
        user.setId(entityUser.getId() != null ? entityUser.getId().intValue() : 0);
        user.setUsername(entityUser.getName());
        user.setEmail(entityUser.getEmail() != null ? entityUser.getEmail() : "");
        user.setPassword(entityUser.getPassword() != null ? entityUser.getPassword() : "");
        user.setRoleId(entityUser.getRoleId());
        return user;
    }

    public AppUser getUserByUsername(String username) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<entity.User> query = em.createQuery("SELECT u FROM User u WHERE u.name = :username", entity.User.class);
            query.setParameter("username", username);
            List<entity.User> result = query.getResultList();
            if (result.isEmpty()) return null;
            return toModelUser(result.get(0));
        } finally {
            em.close();
        }
    }

    public boolean checkUserExists(String username) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(u) FROM User u WHERE u.name = :username", Long.class);
            query.setParameter("username", username);
            return query.getSingleResult() > 0;
        } finally {
            em.close();
        }
    }

    public boolean checkEmailExists(String email) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(u) FROM User u WHERE u.email = :email", Long.class);
            query.setParameter("email", email);
            return query.getSingleResult() > 0;
        } finally {
            em.close();
        }
    }

    public boolean insertUser(AppUser user) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            entity.User entityUser = new entity.User();
            entityUser.setName(user.getUsername());
            entityUser.setEmail(user.getEmail());
            entityUser.setPassword(user.getPassword());
            entityUser.setRoleId(user.getRoleId());
            em.persist(entityUser);
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

    public AppUser getUserByEmail(String email) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<entity.User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email", entity.User.class);
            query.setParameter("email", email);
            List<entity.User> result = query.getResultList();
            if (result.isEmpty()) return null;
            return toModelUser(result.get(0));
        } finally {
            em.close();
        }
    }

    public boolean updatePasswordByEmail(String email, String newPassword) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            TypedQuery<entity.User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email", entity.User.class);
            query.setParameter("email", email);
            List<entity.User> result = query.getResultList();
            if (result.isEmpty()) {
                tx.rollback();
                return false;
            }
            entity.User entityUser = result.get(0);
            entityUser.setPassword(newPassword);
            em.merge(entityUser);
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
}
