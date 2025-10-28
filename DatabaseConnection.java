import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/feedback_portal";
    private static final String USER = "root";
    private static final String PASSWORD = "India@311001";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
    }

    public static void createTables() {
        // Simplified Users Table - Only username and password
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                "user_id INT AUTO_INCREMENT PRIMARY KEY, " +
                "username VARCHAR(50) NOT NULL UNIQUE, " +
                "password VARCHAR(255) NOT NULL, " +
                "registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "INDEX(username))";

        // Feedback Table with User ID
        String createFeedbackTable = "CREATE TABLE IF NOT EXISTS feedback (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "organization VARCHAR(255) NOT NULL, " +
                "feedback_text TEXT NOT NULL, " +
                "rating INT, " +
                "category VARCHAR(100) DEFAULT 'General', " +
                "user_id INT, " +
                "submission_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE SET NULL, " +
                "INDEX(organization), INDEX(user_id))";

        // Categories Table
        String createCategoriesTable = "CREATE TABLE IF NOT EXISTS categories (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "category_name VARCHAR(100) NOT NULL UNIQUE)";

        String insertDefaultCategories = "INSERT IGNORE INTO categories (category_name) VALUES " +
                "('Service Quality'), ('Product Quality'), ('Staff Behavior'), " +
                "('Pricing'), ('Facilities'), ('General')";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createUsersTable);
            stmt.execute(createFeedbackTable);
            stmt.execute(createCategoriesTable);
            stmt.execute(insertDefaultCategories);
            System.out.println("✓ Database initialized successfully");
        } catch (SQLException e) {
            System.err.println("✗ Error creating tables: " + e.getMessage());
        }
    }
}
