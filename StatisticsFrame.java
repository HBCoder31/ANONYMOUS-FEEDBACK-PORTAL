import javax.swing.*;
import java.awt.*;

public class StatisticsFrame extends JFrame {
    private String organization;

    public StatisticsFrame(String organization) {
        this.organization = organization;

        setTitle("Statistics - " + organization);
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Colorful Gradient Header - EMOJI FIXED
        GradientPanel headerPanel = new GradientPanel(
                new Color(255, 184, 0),    // Gold
                new Color(255, 140, 0)     // Dark Orange
        );
        headerPanel.setPreferredSize(new Dimension(600, 80));
        JLabel titleLabel = new JLabel("<html><span style='font-family: Segoe UI Emoji;'>📊</span> STATISTICS: " + organization.toUpperCase() + "</html>");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Stats Panel
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(3, 1, 15, 15));
        statsPanel.setBackground(new Color(236, 240, 241));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        int[] stats = FeedbackOperations.getStatistics(organization);

        // Total Feedback Card - EMOJI FIXED
        statsPanel.add(createStatCard("<html><span style='font-family: Segoe UI Emoji;'>📝</span> Total Feedbacks</html>",
                String.valueOf(stats[0]),
                new Color(52, 152, 219),
                new Color(41, 128, 185)));

        // Average Rating Card - EMOJI FIXED
        String starEmoji = "<span style='font-family: Segoe UI Emoji;'>⭐</span>";
        String avgRating = stats[1] + " " + starEmoji.repeat(stats[1]);
        statsPanel.add(createStatCard("<html><span style='font-family: Segoe UI Emoji;'>⭐</span> Average Rating</html>",
                "<html>" + avgRating + "</html>",
                new Color(46, 204, 113),
                new Color(39, 174, 96)));

        // Total Users Card - EMOJI FIXED
        statsPanel.add(createStatCard("<html><span style='font-family: Segoe UI Emoji;'>👥</span> Total Contributors</html>",
                String.valueOf(stats[2]),
                new Color(155, 89, 182),
                new Color(142, 68, 173)));

        add(statsPanel, BorderLayout.CENTER);

        // Footer - EMOJI FIXED
        GradientPanel footerPanel = new GradientPanel(
                new Color(52, 73, 94),
                new Color(44, 62, 80)
        );
        footerPanel.setPreferredSize(new Dimension(600, 50));
        JLabel footerLabel = new JLabel("<html><span style='font-family: Segoe UI Emoji;'>📈</span> Data updated in real-time</html>", SwingConstants.CENTER);
        footerLabel.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        footerLabel.setForeground(Color.WHITE);
        footerPanel.add(footerLabel);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private JPanel createStatCard(String label, String value, Color color1, Color color2) {
        GradientPanel card = new GradientPanel(color1, color2);
        card.setLayout(new GridBagLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 3, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy = 0;
        JLabel labelComp = new JLabel(label);
        labelComp.setFont(new Font("Segoe UI", Font.BOLD, 18));
        labelComp.setForeground(Color.WHITE);
        card.add(labelComp, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 0, 0);
        JLabel valueComp = new JLabel(value);
        valueComp.setFont(new Font("Segoe UI", Font.BOLD, 32));
        valueComp.setForeground(Color.WHITE);
        card.add(valueComp, gbc);

        return card;
    }
}
