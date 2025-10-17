import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddFeedbackFrame extends JFrame {
    private JTextField organizationField;
    private JTextField nameField;
    private JTextArea feedbackArea;
    private JSlider ratingSlider;
    private JButton submitButton;
    private JButton homeButton;
    private JFrame parentFrame;
    
    public AddFeedbackFrame(JFrame parent) {
        this.parentFrame = parent;
        setTitle("Submit Anonymous Feedback");
        setSize(550, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(46, 204, 113));
        headerPanel.setPreferredSize(new Dimension(550, 60));
        JLabel titleLabel = new JLabel("Submit Your Feedback");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLACK);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(new Color(236, 240, 241));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel orgLabel = new JLabel("Organization/Topic:");
        orgLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(orgLabel, gbc);
        
        organizationField = new JTextField(25);
        organizationField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 1;
        formPanel.add(organizationField, gbc);
        
        JLabel nameLabel = new JLabel("Enter Your Name (Optional):");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridy = 2;
        formPanel.add(nameLabel, gbc);
        
        nameField = new JTextField(25);
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 3;
        formPanel.add(nameField, gbc);
        
        JLabel feedbackLabel = new JLabel("Enter Your Feedback - BE HONEST:");
        feedbackLabel.setFont(new Font("Arial", Font.BOLD, 14));
        feedbackLabel.setForeground(new Color(231, 76, 60));
        gbc.gridy = 4;
        formPanel.add(feedbackLabel, gbc);
        
        feedbackArea = new JTextArea(8, 25);
        feedbackArea.setFont(new Font("Arial", Font.PLAIN, 14));
        feedbackArea.setLineWrap(true);
        feedbackArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(feedbackArea);
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.BOTH;
        formPanel.add(scrollPane, gbc);
        
        JLabel ratingLabel = new JLabel("Rate Your Experience (1-5):");
        ratingLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(ratingLabel, gbc);
        
        ratingSlider = new JSlider(1, 5, 3);
        ratingSlider.setMajorTickSpacing(1);
        ratingSlider.setPaintTicks(true);
        ratingSlider.setPaintLabels(true);
        ratingSlider.setBackground(new Color(236, 240, 241));
        gbc.gridy = 7;
        formPanel.add(ratingSlider, gbc);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(236, 240, 241));
        submitButton = new JButton("Submit Feedback");
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setBackground(new Color(52, 152, 219));
        submitButton.setForeground(Color.BLACK);
        submitButton.setFocusPainted(false);
        
        homeButton = new JButton("Home");
        homeButton.setFont(new Font("Arial", Font.BOLD, 14));
        homeButton.setBackground(new Color(149, 165, 166));
        homeButton.setForeground(Color.BLACK);
        homeButton.setFocusPainted(false);
        
        buttonPanel.add(submitButton);
        buttonPanel.add(homeButton);
        gbc.gridy = 8;
        formPanel.add(buttonPanel, gbc);
        
        add(formPanel, BorderLayout.CENTER);
        
        // Action Listeners
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitFeedback();
            }
        });
        
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    private void submitFeedback() {
        String organization = organizationField.getText().trim();
        String feedback = feedbackArea.getText().trim();
        int rating = ratingSlider.getValue();
        
        if (organization.isEmpty() || feedback.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please fill in organization and feedback fields", 
                "Missing Information", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        boolean success = FeedbackOperations.insertFeedback(organization, feedback, rating);
        
        if (success) {
            JOptionPane.showMessageDialog(this, 
                "Thanks for giving feedback!\nYour name will not be shown anywhere.", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            organizationField.setText("");
            nameField.setText("");
            feedbackArea.setText("");
            ratingSlider.setValue(3);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Error submitting feedback. Please try again.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
