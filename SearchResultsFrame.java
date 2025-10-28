import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;
import java.text.SimpleDateFormat;

public class SearchResultsFrame extends JFrame {
    private JTable resultsTable;
    private DefaultTableModel tableModel;
    private String searchTerm;

    public SearchResultsFrame(String searchTerm) {
        this.searchTerm = searchTerm;

        setTitle("Search Results - " + searchTerm);
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Colorful Gradient Header - EMOJI FIXED
        GradientPanel headerPanel = new GradientPanel(
                new Color(255, 71, 87),    // Pink-Red
                new Color(255, 126, 95)    // Orange-Pink
        );
        headerPanel.setPreferredSize(new Dimension(950, 80));
        JLabel titleLabel = new JLabel("<html><span style='font-family: Segoe UI Emoji;'>🔍</span> SEARCH RESULTS: " + searchTerm.toUpperCase() + "</html>");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Table
        String[] columnNames = {"ID", "Organization", "Category", "Feedback", "Rating", "User", "Date"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        resultsTable = new JTable(tableModel);
        resultsTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        resultsTable.setRowHeight(28);
        resultsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        resultsTable.getTableHeader().setBackground(new Color(52, 73, 94));
        resultsTable.getTableHeader().setForeground(Color.WHITE);
        resultsTable.setSelectionBackground(new Color(52, 152, 219));
        resultsTable.setSelectionForeground(Color.WHITE);
        resultsTable.setGridColor(new Color(189, 195, 199));

        // Set column widths
        resultsTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        resultsTable.getColumnModel().getColumn(1).setPreferredWidth(130);
        resultsTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        resultsTable.getColumnModel().getColumn(3).setPreferredWidth(300);
        resultsTable.getColumnModel().getColumn(4).setPreferredWidth(70);
        resultsTable.getColumnModel().getColumn(5).setPreferredWidth(100);
        resultsTable.getColumnModel().getColumn(6).setPreferredWidth(130);

        JScrollPane scrollPane = new JScrollPane(resultsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Load results
        loadSearchResults();

        // Footer
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(new Color(236, 240, 241));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel resultCountLabel = new JLabel("Total Results: " + tableModel.getRowCount());
        resultCountLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        resultCountLabel.setForeground(new Color(52, 73, 94));
        footerPanel.add(resultCountLabel);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private void loadSearchResults() {
        List<FeedbackData> results = FeedbackOperations.searchFeedback(searchTerm);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        for (FeedbackData data : results) {
            String stars = "⭐".repeat(data.getRating());
            String username = data.getUsername() != null ? data.getUsername() : "Anonymous";

            Object[] row = {
                    data.getId(),
                    data.getOrganization(),
                    data.getCategory(),
                    data.getFeedbackText(),
                    data.getRating() + " " + stars,
                    username,
                    sdf.format(data.getSubmissionDate())
            };
            tableModel.addRow(row);
        }

        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No feedback found for '" + searchTerm + "'",
                    "No Results",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
