import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HomeFrame extends JFrame {
    private JTextField searchField;
    private JButton searchButton;
    private JButton addPostButton;
    private JButton statsButton;
    
    public HomeFrame() {
        setTitle("Anonymous Feedback Portal");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(41, 128, 185));
        headerPanel.setPreferredSize(new Dimension(600, 80));
        JLabel titleLabel = new JLabel("Anonymous Feedback Portal");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Center Panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBackground(new Color(236, 240, 241));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel welcomeLabel = new JLabel("Welcome! Search or Post Feedback");
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        centerPanel.add(welcomeLabel, gbc);
        
        searchField = new JTextField(25);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setToolTipText("Type organization name or topic");
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        centerPanel.add(searchField, gbc);
        
        searchButton = new JButton("Search Feedback");
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.setBackground(new Color(52, 152, 219));
        searchButton.setForeground(Color.BLACK);
        searchButton.setFocusPainted(false);
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        centerPanel.add(searchButton, gbc);
        
        addPostButton = new JButton("Add Post");
        addPostButton.setFont(new Font("Arial", Font.BOLD, 14));
        addPostButton.setBackground(new Color(46, 204, 113));
        addPostButton.setForeground(Color.BLACK);
        addPostButton.setFocusPainted(false);
        gbc.gridx = 1;
        centerPanel.add(addPostButton, gbc);
        
        statsButton = new JButton("View Statistics");
        statsButton.setFont(new Font("Arial", Font.BOLD, 14));
        statsButton.setBackground(new Color(155, 89, 182));
        statsButton.setForeground(Color.BLACK);
        statsButton.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        centerPanel.add(statsButton, gbc);
        
        add(centerPanel, BorderLayout.CENTER);
        
        // Action Listeners
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchField.getText().trim();
                if (searchTerm.isEmpty()) {
                    JOptionPane.showMessageDialog(HomeFrame.this, 
                        "Please enter an organization name or topic to search", 
                        "Input Required", 
                        JOptionPane.WARNING_MESSAGE);
                } else {
                    new SearchResultsFrame(searchTerm).setVisible(true);
                }
            }
        });
        
        addPostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddFeedbackFrame(HomeFrame.this).setVisible(true);
            }
        });
        
        statsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchField.getText().trim();
                if (searchTerm.isEmpty()) {
                    JOptionPane.showMessageDialog(HomeFrame.this, 
                        "Please enter an organization name to view statistics", 
                        "Input Required", 
                        JOptionPane.WARNING_MESSAGE);
                } else {
                    new StatisticsFrame(searchTerm).setVisible(true);
                }
            }
        });
    }
}
