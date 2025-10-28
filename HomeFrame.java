import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HomeFrame extends JFrame {
    private JTextField searchField;
    private JButton searchButton;
    private JButton addPostButton;
    private JButton statsButton;
    private JButton myFeedbackButton;
    private JButton logoutButton;
    private User currentUser;

    public HomeFrame(User user) {
        this.currentUser = user;
        setTitle("Feedback Portal - Welcome " + user.getUsername());
        setSize(700, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Colorful Gradient Header
        GradientPanel headerPanel = new GradientPanel(
                new Color(88, 86, 214),
                new Color(58, 134, 255)
        );
        headerPanel.setPreferredSize(new Dimension(700, 100));
        headerPanel.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel titleLabel = new JLabel("<html><span style='font-family: Segoe UI Emoji;'>🎯</span> FEEDBACK PORTAL</html>");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel, BorderLayout.WEST);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userPanel.setOpaque(false);

        JLabel userLabel = new JLabel("<html><span style='font-family: Segoe UI Emoji;'>👤</span> " + user.getUsername() + "</html>");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userLabel.setForeground(Color.WHITE);
        userPanel.add(userLabel);

        logoutButton = createStyledButton("Logout", new Color(231, 76, 60), Color.WHITE);
        logoutButton.setPreferredSize(new Dimension(90, 35));
        userPanel.add(logoutButton);

        titlePanel.add(userPanel, BorderLayout.EAST);
        headerPanel.add(titlePanel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        // Center Panel
        GradientPanel centerPanel = new GradientPanel(
                new Color(240, 242, 245),
                new Color(255, 255, 255),
                false
        );
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Search Section
        JPanel searchPanel = new JPanel(new BorderLayout(10, 10));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 2, true),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));

        JLabel searchLabel = new JLabel("<html><span style='font-family: Segoe UI Emoji;'>🔍</span> Search Feedback by Organization</html>");
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        searchLabel.setForeground(new Color(52, 73, 94));
        searchPanel.add(searchLabel, BorderLayout.NORTH);

        JPanel searchInputPanel = new JPanel(new BorderLayout(10, 0));
        searchInputPanel.setBackground(Color.WHITE);
        searchInputPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        searchField = new JTextField();
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 2, true),
                BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        searchInputPanel.add(searchField, BorderLayout.CENTER);

        searchButton = createStyledButton("Search", new Color(52, 152, 219), Color.WHITE);
        searchButton.setPreferredSize(new Dimension(100, 45));
        searchInputPanel.add(searchButton, BorderLayout.EAST);

        searchPanel.add(searchInputPanel, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        centerPanel.add(searchPanel, gbc);

        // Action Buttons
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;

        addPostButton = createColorfulActionButton("<html><span style='font-family: Segoe UI Emoji;'>➕</span> Submit Feedback</html>",
                new Color(46, 204, 113), new Color(39, 174, 96));
        centerPanel.add(addPostButton, gbc);

        gbc.gridx = 1;
        myFeedbackButton = createColorfulActionButton("<html><span style='font-family: Segoe UI Emoji;'>📋</span> My Feedbacks</html>",
                new Color(155, 89, 182), new Color(142, 68, 173));
        centerPanel.add(myFeedbackButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        statsButton = createColorfulActionButton("<html><span style='font-family: Segoe UI Emoji;'>📊</span> View Statistics</html>",
                new Color(241, 196, 15), new Color(243, 156, 18));
        centerPanel.add(statsButton, gbc);

        add(centerPanel, BorderLayout.CENTER);

        // Footer
        GradientPanel footerPanel = new GradientPanel(
                new Color(52, 73, 94),
                new Color(44, 62, 80)
        );
        footerPanel.setPreferredSize(new Dimension(700, 50));
        JLabel footerLabel = new JLabel("<html><span style='font-family: Segoe UI Emoji;'>💡</span> Your feedback matters! Help make organizations better.</html>", SwingConstants.CENTER);
        footerLabel.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        footerLabel.setForeground(Color.WHITE);
        footerPanel.add(footerLabel);
        add(footerPanel, BorderLayout.SOUTH);

        // Action Listeners
        searchButton.addActionListener(e -> performSearch());
        searchField.addActionListener(e -> performSearch());

        addPostButton.addActionListener(e -> {
            new AddFeedbackFrame(this, currentUser).setVisible(true);
        });

        myFeedbackButton.addActionListener(e -> {
            new MyFeedbackFrame(currentUser).setVisible(true);
        });

        statsButton.addActionListener(e -> {
            String organization = JOptionPane.showInputDialog(this,
                    "Enter organization name for statistics:",
                    "Organization Statistics",
                    JOptionPane.QUESTION_MESSAGE);

            if (organization != null && !organization.trim().isEmpty()) {
                new StatisticsFrame(organization.trim()).setVisible(true);
            }
        });

        logoutButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to logout?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                new LoginFrame().setVisible(true);
                dispose();
            }
        });
    }

    private void performSearch() {
        String searchTerm = searchField.getText().trim();

        if (searchTerm.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter an organization name to search!",
                    "Empty Search",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        new SearchResultsFrame(searchTerm).setVisible(true);
    }

    private JButton createStyledButton(String text, Color bg, Color fg) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JButton createColorfulActionButton(String text, Color normalColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(normalColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        button.setPreferredSize(new Dimension(280, 80));

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

        return button;
    }
}
