import java.sql.Timestamp;

public class FeedbackData {
    private int id;
    private String organization;
    private String feedbackText;
    private int rating;
    private Timestamp submissionDate;
    private String category;
    private int userId;
    private String username;

    public FeedbackData(int id, String organization, String feedbackText, int rating, 
                        Timestamp submissionDate, String category, int userId, String username) {
        this.id = id;
        this.organization = organization;
        this.feedbackText = feedbackText;
        this.rating = rating;
        this.submissionDate = submissionDate;
        this.category = category;
        this.userId = userId;
        this.username = username;
    }

    // Getters
    public int getId() { return id; }
    public String getOrganization() { return organization; }
    public String getFeedbackText() { return feedbackText; }
    public int getRating() { return rating; }
    public Timestamp getSubmissionDate() { return submissionDate; }
    public String getCategory() { return category; }
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
}
