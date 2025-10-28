import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackOperations {

    // Insert feedback with user ID
    public static boolean insertFeedback(String organization, String feedback, int rating, 
                                        String category, int userId) {
        String sql = "INSERT INTO feedback (organization, feedback_text, rating, category, user_id) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, organization);
            pstmt.setString(2, feedback);
            pstmt.setInt(3, rating);
            pstmt.setString(4, category);
            pstmt.setInt(5, userId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error inserting feedback: " + e.getMessage());
            return false;
        }
    }

    // Search feedback by organization
    public static List<FeedbackData> searchFeedback(String organization) {
        List<FeedbackData> results = new ArrayList<>();
        String sql = "SELECT f.*, u.username FROM feedback f " +
                     "LEFT JOIN users u ON f.user_id = u.user_id " +
                     "WHERE f.organization LIKE ? ORDER BY f.submission_date DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + organization + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                FeedbackData data = new FeedbackData(
                    rs.getInt("id"),
                    rs.getString("organization"),
                    rs.getString("feedback_text"),
                    rs.getInt("rating"),
                    rs.getTimestamp("submission_date"),
                    rs.getString("category"),
                    rs.getInt("user_id"),
                    rs.getString("username")
                );
                results.add(data);
            }
            
        } catch (SQLException e) {
            System.err.println("Error searching feedback: " + e.getMessage());
        }
        
        return results;
    }

    // Get user's feedback
    public static List<FeedbackData> getUserFeedback(int userId) {
        List<FeedbackData> results = new ArrayList<>();
        String sql = "SELECT f.*, u.username FROM feedback f " +
                     "LEFT JOIN users u ON f.user_id = u.user_id " +
                     "WHERE f.user_id = ? ORDER BY f.submission_date DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                FeedbackData data = new FeedbackData(
                    rs.getInt("id"),
                    rs.getString("organization"),
                    rs.getString("feedback_text"),
                    rs.getInt("rating"),
                    rs.getTimestamp("submission_date"),
                    rs.getString("category"),
                    rs.getInt("user_id"),
                    rs.getString("username")
                );
                results.add(data);
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching user feedback: " + e.getMessage());
        }
        
        return results;
    }

    // Get statistics
    public static int[] getStatistics(String organization) {
        int[] stats = new int[3]; // [totalFeedback, avgRating, totalUsers]
        
        String sql = "SELECT COUNT(*) as total, " +
                     "COALESCE(AVG(rating), 0) as avgRating, " +
                     "COUNT(DISTINCT user_id) as totalUsers " +
                     "FROM feedback WHERE organization = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, organization);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                stats[0] = rs.getInt("total");
                stats[1] = (int) Math.round(rs.getDouble("avgRating"));
                stats[2] = rs.getInt("totalUsers");
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching statistics: " + e.getMessage());
        }
        
        return stats;
    }

    // Get all categories
    public static List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        String sql = "SELECT category_name FROM categories ORDER BY category_name";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                categories.add(rs.getString("category_name"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching categories: " + e.getMessage());
        }
        
        return categories;
    }

    // Delete user's feedback
    public static boolean deleteFeedback(int feedbackId, int userId) {
        String sql = "DELETE FROM feedback WHERE id = ? AND user_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, feedbackId);
            pstmt.setInt(2, userId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting feedback: " + e.getMessage());
            return false;
        }
    }
}
