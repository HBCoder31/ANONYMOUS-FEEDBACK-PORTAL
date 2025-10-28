import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class AddFeedbackFrame extends JFrame {
    private JTextField organizationField;
    private JTextArea feedbackArea;
    private JSlider ratingSlider;
    private JComboBox<String> categoryCombo;
    private JButton submitButton;
    private JButton cancelButton;
    private User currentUser;
    private JFrame parentFrame;

    public AddFeedbackFrame(JFrame parent, User user) {
        this.parentFrame = parent;
        this.currentUser = user;

        setTitle("Submit Feedback - " + user.getUsername());
        setSize(650, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Header
        GradientPanel headerPanel = new GradientPanel(
                new Color(0, 210, 211),
                new Color(58, 123, 213)
        );
        headerPanel.setPreferredSize(new Dimension(650, 80));
        JLabel titleLabel = new JLabel("<html><span style='font-family: Segoe UI Emoji;'>✨</span> Share Your Experience</html>", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setBackground(Color.WHITE);
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10, 0, 10, 0);

        // Organization
        gbc.gridy = 0;
        JLabel orgLabel = new JLabel("<html><span style='font-family: Segoe UI Emoji;'>🏢</span> Organization Name *</html>");
        orgLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        orgLabel.setForeground(new Color(52, 73, 94));
        formPanel.add(orgLabel, gbc);

        gbc.gridy = 1;
        organizationField = new JTextField();
        organizationField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        organizationField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 2, true),
                BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        formPanel.add(organizationField, gbc);

        // Category
        gbc.gridy = 2;
        gbc.insets = new Insets(20, 0, 10, 0);
        JLabel categoryLabel = new JLabel("<html><span style='font-family: Segoe UI Emoji;'>📂</span> Feedback Category *</html>");
        categoryLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        categoryLabel.setForeground(new Color(52, 73, 94));
        formPanel.add(categoryLabel, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 10, 0);
        List<String> categories = FeedbackOperations.getAllCategories();
        categoryCombo = new JComboBox<>(categories.toArray(new String[0]));
        categoryCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        categoryCombo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.add(categoryCombo, gbc);

        // Feedback Text
        gbc.gridy = 4;
        gbc.insets = new Insets(20, 0, 10, 0);
        JLabel feedbackLabel = new JLabel("<html><span style='font-family: Segoe UI Emoji;'>💬</span> Your Feedback *</html>");
        feedbackLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        feedbackLabel.setForeground(new Color(52, 73, 94));
        formPanel.add(feedbackLabel, gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 10, 0);
        feedbackArea = new JTextArea(6, 30);
        feedbackArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        feedbackArea.setLineWrap(true);
        feedbackArea.setWrapStyleWord(true);
        feedbackArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 2, true),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        JScrollPane scrollPane = new JScrollPane(feedbackArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 2, true));
        formPanel.add(scrollPane, gbc);

        // Rating
        gbc.gridy = 6;
        gbc.insets = new Insets(20, 0, 10, 0);
        JLabel ratingLabel = new JLabel("<html><span style='font-family: Segoe UI Emoji;'>⭐</span> Rating (1-5) *</html>");
        ratingLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        ratingLabel.setForeground(new Color(52, 73, 94));
        formPanel.add(ratingLabel, gbc);

        gbc.gridy = 7;
        gbc.insets = new Insets(0, 0, 10, 0);
        JPanel ratingPanel = new JPanel(new BorderLayout());
        ratingPanel.setBackground(Color.WHITE);

        ratingSlider = new JSlider(1, 5, 3);
        ratingSlider.setMajorTickSpacing(1);
        ratingSlider.setPaintTicks(true);
        ratingSlider.setPaintLabels(true);
        ratingSlider.setBackground(Color.WHITE);
        ratingSlider.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JLabel ratingValueLabel = new JLabel("<html>Rating: 3 <span style='font-family: Segoe UI Emoji;'>⭐⭐⭐</span></html>", SwingConstants.CENTER);
        ratingValueLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        ratingValueLabel.setForeground(new Color(243, 156, 18));

        ratingSlider.addChangeListener(e -> {
            int value = ratingSlider.getValue();
            String stars = "⭐".repeat(value);
            ratingValueLabel.setText("<html>Rating: " + value + " <span style='font-family: Segoe UI Emoji;'>" + stars + "</span></html>");
        });

        ratingPanel.add(ratingSlider, BorderLayout.CENTER);
        ratingPanel.add(ratingValueLabel, BorderLayout.SOUTH);
        formPanel.add(ratingPanel, gbc);

        // Buttons
        gbc.gridy = 8;
        gbc.insets = new Insets(30, 0, 10, 0);
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        buttonPanel.setBackground(Color.WHITE);

        submitButton = new JButton("<html><span style='font-family: Segoe UI Emoji;'>✓</span> SUBMIT FEEDBACK</html>");
        submitButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        submitButton.setForeground(Color.WHITE);
        submitButton.setBackground(new Color(46, 204, 113));
        submitButton.setFocusPainted(false);
        submitButton.setBorderPainted(false);
        submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submitButton.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        cancelButton = new JButton("<html><span style='font-family: Segoe UI Emoji;'>✗</span> CANCEL</html>");
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBackground(new Color(231, 76, 60));
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);
        formPanel.add(buttonPanel, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Action Listeners
        submitButton.addActionListener(e -> submitFeedback());
        cancelButton.addActionListener(e -> dispose());

        addHoverEffect(submitButton, new Color(39, 174, 96), new Color(46, 204, 113));
        addHoverEffect(cancelButton, new Color(192, 57, 43), new Color(231, 76, 60));
    }

    private void submitFeedback() {
        String organization = organizationField.getText().trim();
        String feedback = feedbackArea.getText().trim();
        String category = (String) categoryCombo.getSelectedItem();
        int rating = ratingSlider.getValue();

        if (organization.isEmpty() || feedback.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in all required fields!",
                    "Incomplete Form",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean success = FeedbackOperations.insertFeedback(
                organization, feedback, rating, category, currentUser.getUserId()
        );

        if (success) {
            JOptionPane.showMessageDialog(this,
                    "<html><span style='font-family: Segoe UI Emoji;'>✓</span> Feedback submitted successfully!<br>Thank you for sharing your experience!</html>",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            organizationField.setText("");
            feedbackArea.setText("");
            ratingSlider.setValue(3);
            categoryCombo.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Failed to submit feedback. Please try again!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addHoverEffect(JButton button, Color hoverColor, Color normalColor) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(normalColor);
            }
        });
    }
}
