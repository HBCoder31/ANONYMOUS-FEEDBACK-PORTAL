import java.sql.Timestamp;

public class FeedbackData {
    private int id;
    private String organization;
    private String feedbackText;
    private int rating;
    private Timestamp submissionDate;
    
    public FeedbackData(int id, String organization, String feedbackText, int rating, Timestamp submissionDate) {
        this.id = id;
        this.organization = organization;
        this.feedbackText = feedbackText;
        this.rating = rating;
        this.submissionDate = submissionDate;
    }
    
    public int getId() { return id; }
    public String getOrganization() { return organization; }
    public String getFeedbackText() { return feedbackText; }
    public int getRating() { return rating; }
    public Timestamp getSubmissionDate() { return submissionDate; }
}
