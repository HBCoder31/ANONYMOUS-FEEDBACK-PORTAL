import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StatisticsFrame extends JFrame {
    private String organization;
    
    public StatisticsFrame(String organization) {
        this.organization = organization;
        setTitle("Statistics - " + organization);
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(155, 89, 182));
        headerPanel.setPreferredSize(new Dimension(500, 60));
        JLabel titleLabel = new JLabel("Statistics: " + organization);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLACK);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Stats Panel
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(3, 2, 20, 20));
        statsPanel.setBackground(new Color(236, 240, 241));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        
        int feedbackCount = FeedbackOperations.getFeedbackCount(organization);
        double avgRating = FeedbackOperations.getAverageRating(organization);
        
        JLabel countLabel = new JLabel("Total Feedback:");
        countLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statsPanel.add(countLabel);
        
        JLabel countValue = new JLabel(String.valueOf(feedbackCount));
        countValue.setFont(new Font("Arial", Font.PLAIN, 16));
        statsPanel.add(countValue);
        
        JLabel avgLabel = new JLabel("Average Rating:");
        avgLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statsPanel.add(avgLabel);
        
        JLabel avgValue = new JLabel(String.format("%.2f / 5.00", avgRating));
        avgValue.setFont(new Font("Arial", Font.PLAIN, 16));
        statsPanel.add(avgValue);
        
        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statsPanel.add(statusLabel);
        
        String status = avgRating >= 4.0 ? "Excellent" : avgRating >= 3.0 ? "Good" : avgRating >= 2.0 ? "Average" : "Needs Improvement";
        JLabel statusValue = new JLabel(status);
        statusValue.setFont(new Font("Arial", Font.PLAIN, 16));
        Color statusColor = avgRating >= 4.0 ? new Color(46, 204, 113) : avgRating >= 3.0 ? new Color(52, 152, 219) : new Color(231, 76, 60);
        statusValue.setForeground(statusColor);
        statsPanel.add(statusValue);
        
        add(statsPanel, BorderLayout.CENTER);
        
        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(236, 240, 241));
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.setBackground(new Color(149, 165, 166));
        closeButton.setForeground(Color.BLACK);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
