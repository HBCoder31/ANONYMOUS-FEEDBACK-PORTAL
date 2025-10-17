import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackOperations {
    
    public static boolean insertFeedback(String organization, String feedback, int rating) {
        String sql = "INSERT INTO feedback (organization, feedback_text, rating) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, organization);
            pstmt.setString(2, feedback);
            pstmt.setInt(3, rating);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error inserting feedback: " + e.getMessage());
            return false;
        }
    }
    
    public static List<FeedbackData> searchFeedback(String organization) {
        List<FeedbackData> results = new ArrayList<>();
        String sql = "SELECT * FROM feedback WHERE organization LIKE ? ORDER BY submission_date DESC";
        
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
                    rs.getTimestamp("submission_date")
                );
                results.add(data);
            }
            
        } catch (SQLException e) {
            System.err.println("Error searching feedback: " + e.getMessage());
        }
        
        return results;
    }
    
    public static int getFeedbackCount(String organization) {
        String sql = "SELECT COUNT(*) as count FROM feedback WHERE organization LIKE ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + organization + "%");
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("count");
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting feedback count: " + e.getMessage());
        }
        
        return 0;
    }
    
    public static double getAverageRating(String organization) {
        String sql = "SELECT AVG(rating) as avg_rating FROM feedback WHERE organization LIKE ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + organization + "%");
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble("avg_rating");
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting average rating: " + e.getMessage());
        }
        
        return 0.0;
    }
}
