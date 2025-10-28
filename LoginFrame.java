import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JPanel buttonPanel;

    public LoginFrame() {
        setTitle("Feedback Portal - Login");
        setSize(450, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Main Gradient Panel
        GradientPanel mainPanel = new GradientPanel(
                new Color(67, 233, 123),
                new Color(56, 249, 215)
        );
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // White Card Panel
        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setLayout(new GridBagLayout());
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));

        GridBagConstraints cardGbc = new GridBagConstraints();
        cardGbc.gridx = 0;
        cardGbc.insets = new Insets(10, 0, 10, 0);
        cardGbc.fill = GridBagConstraints.HORIZONTAL;

        // Logo/Title - EMOJI FIXED
        cardGbc.gridy = 0;
        JLabel titleLabel = new JLabel("<html><span style='font-family: Segoe UI Emoji;'>🎯</span> FEEDBACK PORTAL</html>", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(52, 73, 94));
        cardPanel.add(titleLabel, cardGbc);

        // Subtitle
        cardGbc.gridy = 1;
        JLabel subtitleLabel = new JLabel("Sign in to continue", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(127, 140, 141));
        cardPanel.add(subtitleLabel, cardGbc);

        // Username Label - EMOJI FIXED
        cardGbc.gridy = 2;
        cardGbc.insets = new Insets(20, 0, 5, 0);
        JLabel userLabel = new JLabel("<html><span style='font-family: Segoe UI Emoji;'>👤</span> Username</html>");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        userLabel.setForeground(new Color(52, 73, 94));
        cardPanel.add(userLabel, cardGbc);

        // Username Field
        cardGbc.gridy = 3;
        cardGbc.insets = new Insets(0, 0, 10, 0);
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 2, true),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        cardPanel.add(usernameField, cardGbc);

        // Password Label - EMOJI FIXED
        cardGbc.gridy = 4;
        cardGbc.insets = new Insets(10, 0, 5, 0);
        JLabel passLabel = new JLabel("<html><span style='font-family: Segoe UI Emoji;'>🔒</span> Password</html>");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        passLabel.setForeground(new Color(52, 73, 94));
        cardPanel.add(passLabel, cardGbc);

        // Password Field
        cardGbc.gridy = 5;
        cardGbc.insets = new Insets(0, 0, 10, 0);
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 2, true),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        cardPanel.add(passwordField, cardGbc);

        // Button Panel
        cardGbc.gridy = 6;
        cardGbc.insets = new Insets(20, 0, 10, 0);
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 1, 0, 10));
        buttonPanel.setBackground(Color.WHITE);

        loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(52, 152, 219));
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));
        buttonPanel.add(loginButton);

        cardPanel.add(buttonPanel, cardGbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(cardPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Action Listeners
        loginButton.addActionListener(e -> performLogin());
        passwordField.addActionListener(e -> performLogin());

        addHoverEffect(loginButton, new Color(41, 128, 185), new Color(52, 152, 219));
    }

    private void performLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter both username and password!",
                    "Input Required",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        User user = UserOperations.loginUser(username, password);

        if (user != null) {
            JOptionPane.showMessageDialog(this,
                    "<html>Welcome back, " + user.getUsername() + "! <span style='font-family: Segoe UI Emoji;'>🎉</span></html>",
                    "Login Successful",
                    JOptionPane.INFORMATION_MESSAGE);

            new HomeFrame(user).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "<html><span style='font-family: Segoe UI Emoji;'>❌</span> Invalid username or password!<br><br>If you don't have an account, click 'REGISTER NOW' below.</html>",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);

            passwordField.setText("");
            showRegisterButton();
        }
    }

    private void showRegisterButton() {
        if (registerButton == null) {
            buttonPanel.setLayout(new GridLayout(1, 2, 10, 0));

            registerButton = new JButton("REGISTER NOW");
            registerButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
            registerButton.setForeground(Color.WHITE);
            registerButton.setBackground(new Color(46, 204, 113));
            registerButton.setFocusPainted(false);
            registerButton.setBorderPainted(false);
            registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            registerButton.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));

            registerButton.addActionListener(e -> openRegistrationFrame());
            addHoverEffect(registerButton, new Color(39, 174, 96), new Color(46, 204, 113));

            buttonPanel.add(registerButton);
            buttonPanel.revalidate();
            buttonPanel.repaint();
        }
    }

    private void openRegistrationFrame() {
        new RegistrationFrame(this).setVisible(true);
        this.setVisible(false);
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

    public static void main(String[] args) {
        DatabaseConnection.createTables();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
