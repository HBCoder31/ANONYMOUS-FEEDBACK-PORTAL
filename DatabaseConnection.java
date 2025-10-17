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
        String createFeedbackTable = "CREATE TABLE IF NOT EXISTS feedback (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "organization VARCHAR(255) NOT NULL, " +
                "feedback_text TEXT NOT NULL, " +
                "rating INT, " +
                "submission_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "INDEX(organization))";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createFeedbackTable);
            System.out.println("Database initialized successfully");
        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
        }
    }
}
