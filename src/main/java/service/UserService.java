package service;

import dao.UserDao;
import model.AppUser;

public class UserService {
    private UserDao userDAO;

    public UserService() {
        this.userDAO = new UserDao();
    }

    public boolean authenticateUser(String username, String password) {
        if (username == null || password == null) {
            return false;
        }

        AppUser user = userDAO.getUserByUsername(username);

        if (user != null) {
            // Loại bỏ khoảng trắng ở hai đầu password (nếu có)
            String dbPassword = user.getPassword();
            if (dbPassword != null) dbPassword = dbPassword.trim();
            if (password != null) password = password.trim();

            System.out.println("Input username: " + username);
            System.out.println("Input password: [" + password + "]");
            System.out.println("DB username: " + user.getUsername());
            System.out.println("DB password: [" + dbPassword + "]");
            System.out.println("Equals? " + password.equals(dbPassword));
            return password.equals(dbPassword);
        } else {
            System.out.println("User not found in DB");
        }

        return false;
    }

    public boolean registerUser(String username, String email, String password) {
        if (userDAO.checkUserExists(username)) {
            return false;
        }

        if (userDAO.checkEmailExists(email)) {
            return false;
        }

        // Loại bỏ khoảng trắng ở hai đầu password khi lưu
        if (password != null) password = password.trim();
        AppUser newUser = new AppUser(username, email, password);

        return userDAO.insertUser(newUser);
    }

    public AppUser getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    public AppUser getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    public boolean updatePasswordByEmail(String email, String newPassword) {
        if (email == null || newPassword == null) return false;
        return userDAO.updatePasswordByEmail(email.trim(), newPassword.trim());
    }
}
