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
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Colorful Gradient Header
        GradientPanel headerPanel = new GradientPanel(
                new Color(255, 71, 87),  // Pink-Red
                new Color(255, 126, 95)  // Orange-Pink
        );
        headerPanel.setPreferredSize(new Dimension(1200, 80));
        JLabel titleLabel = new JLabel("SEARCH RESULTS: " + searchTerm.toUpperCase());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Table
        String[] columnNames = {"ID", "Organization", "Category", "Feedback", "Rating", "Date"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        resultsTable = new JTable(tableModel);
        resultsTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        resultsTable.setRowHeight(50);

        // BLACK HEADERS
        resultsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        resultsTable.getTableHeader().setBackground(Color.WHITE);
        resultsTable.getTableHeader().setForeground(Color.BLACK);
        resultsTable.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));

        resultsTable.setSelectionBackground(new Color(52, 152, 219));
        resultsTable.setSelectionForeground(Color.WHITE);
        resultsTable.setGridColor(new Color(189, 195, 199));

        // Set column widths
        resultsTable.getColumnModel().getColumn(0).setPreferredWidth(40);   // ID
        resultsTable.getColumnModel().getColumn(1).setPreferredWidth(120);  // Organization
        resultsTable.getColumnModel().getColumn(2).setPreferredWidth(120);  // Category
        resultsTable.getColumnModel().getColumn(3).setPreferredWidth(500);  // Feedback
        resultsTable.getColumnModel().getColumn(4).setPreferredWidth(80);   // Rating - just number
        resultsTable.getColumnModel().getColumn(5).setPreferredWidth(140);  // Date

        // Enable text wrapping
        resultsTable.setDefaultRenderer(Object.class, new MultiLineTableCellRenderer());

        JScrollPane scrollPane = new JScrollPane(resultsTable);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
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
            Object[] row = {
                    data.getId(),
                    data.getOrganization(),
                    data.getCategory(),
                    data.getFeedbackText(),
                    data.getRating(),  // Just the number
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

    // Custom cell renderer for multi-line text
    class MultiLineTableCellRenderer extends JLabel implements TableCellRenderer {
        public MultiLineTableCellRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {

            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(table.getBackground());
            }

            setFont(table.getFont());

            // Handle long feedback text
            if (column == 3) { // Feedback column
                String text = (value == null) ? "" : value.toString();
                setText("<html><div style='width:480px;'>" + text + "</div></html>");
            } else {
                setText((value == null) ? "" : value.toString());
            }

            return this;
        }
    }
}
