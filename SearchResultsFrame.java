import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class SearchResultsFrame extends JFrame {
    private JTable resultsTable;
    private DefaultTableModel tableModel;
    private String searchTerm;
    
    public SearchResultsFrame(String searchTerm) {
        this.searchTerm = searchTerm;
        setTitle("Search Results - " + searchTerm);
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(52, 152, 219));
        headerPanel.setPreferredSize(new Dimension(800, 60));
        JLabel titleLabel = new JLabel("Feedback for: " + searchTerm);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLACK);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Table
        String[] columnNames = {"ID", "Organization", "Feedback", "Rating", "Date"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        resultsTable = new JTable(tableModel);
        resultsTable.setFont(new Font("Arial", Font.PLAIN, 12));
        resultsTable.setRowHeight(25);
        resultsTable.getColumnModel().getColumn(2).setPreferredWidth(300);
        
        JScrollPane scrollPane = new JScrollPane(resultsTable);
        add(scrollPane, BorderLayout.CENTER);
        
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
        
        loadSearchResults();
    }
    
    private void loadSearchResults() {
        List<FeedbackData> results = FeedbackOperations.searchFeedback(searchTerm);
        
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No feedback found for: " + searchTerm, 
                "No Results", 
                JOptionPane.INFORMATION_MESSAGE);
        }
        
        for (FeedbackData data : results) {
            Object[] row = {
                data.getId(),
                data.getOrganization(),
                data.getFeedbackText(),
                data.getRating() + "/5",
                data.getSubmissionDate()
            };
            tableModel.addRow(row);
        }
    }
}
