package ltw3;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
public class DBConnect {
	
	private static final String URL = "jdbc:mysql://localhost:3306/mydb?useSSL=false&serverTimezone=UTC";
    private static final String USER = "user";
    private static final String PASSWORD = "pass123";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // load MySQL driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        try (Connection conn = DBConnect.getConnection()) {
            if (conn != null) {
                System.out.println("Kết nối MySQL thành công!");

                try (Statement stmt = conn.createStatement()) {
                    String sql = "SELECT id, username, email FROM users";
                    ResultSet rs = stmt.executeQuery(sql);

                    System.out.println("Danh sách users:");
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String username = rs.getString("username");
                        String email = rs.getString("email");
                        System.out.println("ID: " + id + " | Username: " + username + " | Email: " + email);
                    }
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Kết nối MySQL thất bại!");
        }
    }
}
