import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.text.SimpleDateFormat;

public class MyFeedbackFrame extends JFrame {
    private JTable feedbackTable;
    private DefaultTableModel tableModel;
    private User currentUser;
    private JButton refreshButton;
    private JButton deleteButton;

    public MyFeedbackFrame(User user) {
        this.currentUser = user;
        
        setTitle("📋 My Feedback History - " + user.getUsername());
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Colorful Gradient Header
        GradientPanel headerPanel = new GradientPanel(
            new Color(108, 92, 231),   // Purple
            new Color(150, 108, 231)   // Light Purple
        );
        headerPanel.setPreferredSize(new Dimension(1000, 80));
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel titleLabel = new JLabel("📋 MY FEEDBACK HISTORY");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel countLabel = new JLabel();
        countLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        countLabel.setForeground(Color.WHITE);
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(countLabel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Table
        String[] columnNames = {"ID", "Organization", "Category", "Feedback", "Rating", "Date"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        feedbackTable = new JTable(tableModel);
        feedbackTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        feedbackTable.setRowHeight(30);
        feedbackTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        feedbackTable.getTableHeader().setBackground(new Color(52, 73, 94));
        feedbackTable.getTableHeader().setForeground(Color.WHITE);
        feedbackTable.setSelectionBackground(new Color(155, 89, 182));
        feedbackTable.setSelectionForeground(Color.WHITE);
        feedbackTable.setGridColor(new Color(189, 195, 199));

        // Set column widths
        feedbackTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        feedbackTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        feedbackTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        feedbackTable.getColumnModel().getColumn(3).setPreferredWidth(350);
        feedbackTable.getColumnModel().getColumn(4).setPreferredWidth(80);
        feedbackTable.getColumnModel().getColumn(5).setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(feedbackTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(new Color(236, 240, 241));

        refreshButton = createStyledButton(" Refresh", new Color(52, 152, 219));
        deleteButton = createStyledButton(" Delete Selected", new Color(231, 76, 60));

        buttonPanel.add(refreshButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Load feedback
        loadUserFeedback();
        countLabel.setText("Total Feedbacks: " + tableModel.getRowCount());

        // Action Listeners
        refreshButton.addActionListener(e -> {
            loadUserFeedback();
            countLabel.setText("Total Feedbacks: " + tableModel.getRowCount());
        });

        deleteButton.addActionListener(e -> deleteSelectedFeedback());
    }

    private void loadUserFeedback() {
        tableModel.setRowCount(0);
        List<FeedbackData> feedbacks = FeedbackOperations.getUserFeedback(currentUser.getUserId());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        for (FeedbackData data : feedbacks) {
            String stars = "⭐".repeat(data.getRating());
            Object[] row = {
                data.getId(),
                data.getOrganization(),
                data.getCategory(),
                data.getFeedbackText(),
                data.getRating() + " " + stars,
                sdf.format(data.getSubmissionDate())
            };
            tableModel.addRow(row);
        }
    }

    private void deleteSelectedFeedback() {
        int selectedRow = feedbackTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select a feedback to delete!",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int feedbackId = (int) tableModel.getValueAt(selectedRow, 0);
        String organization = (String) tableModel.getValueAt(selectedRow, 1);

        int choice = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete feedback for '" + organization + "'?",
            "Confirm Deletion",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (choice == JOptionPane.YES_OPTION) {
            boolean success = FeedbackOperations.deleteFeedback(feedbackId, currentUser.getUserId());
            
            if (success) {
                JOptionPane.showMessageDialog(this,
                    "Feedback deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                loadUserFeedback();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Failed to delete feedback!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(180, 40));
        
        Color hoverColor = bgColor.darker();
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
}
