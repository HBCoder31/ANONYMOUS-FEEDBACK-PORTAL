import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegistrationFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton registerButton;
    private JButton backButton;
    private JFrame parentFrame;

    public RegistrationFrame(JFrame parent) {
        this.parentFrame = parent;
        setTitle("Create New Account");
        setSize(450, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Gradient Background
        GradientPanel mainPanel = new GradientPanel(
                new Color(250, 82, 82),
                new Color(255, 159, 67)
        );
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

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
        cardGbc.fill = GridBagConstraints.HORIZONTAL;
        cardGbc.insets = new Insets(10, 0, 10, 0);

        // Title - EMOJI FIXED
        cardGbc.gridy = 0;
        JLabel titleLabel = new JLabel("<html><span style='font-family: Segoe UI Emoji;'>✨</span> JOIN US TODAY</html>", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(new Color(52, 73, 94));
        cardPanel.add(titleLabel, cardGbc);

        // Subtitle
        cardGbc.gridy = 1;
        JLabel subtitleLabel = new JLabel("Create your account", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(127, 140, 141));
        cardPanel.add(subtitleLabel, cardGbc);

        // Username - EMOJI FIXED
        cardGbc.gridy = 2;
        cardGbc.insets = new Insets(20, 0, 5, 0);
        JLabel userLabel = new JLabel("<html><span style='font-family: Segoe UI Emoji;'>👤</span> Username</html>");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        userLabel.setForeground(new Color(52, 73, 94));
        cardPanel.add(userLabel, cardGbc);

        cardGbc.gridy = 3;
        cardGbc.insets = new Insets(0, 0, 10, 0);
        usernameField = createStyledTextField();
        cardPanel.add(usernameField, cardGbc);

        // Password - EMOJI FIXED
        cardGbc.gridy = 4;
        cardGbc.insets = new Insets(15, 0, 5, 0);
        JLabel passLabel = new JLabel("<html><span style='font-family: Segoe UI Emoji;'>🔐</span> Password</html>");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        passLabel.setForeground(new Color(52, 73, 94));
        cardPanel.add(passLabel, cardGbc);

        cardGbc.gridy = 5;
        cardGbc.insets = new Insets(0, 0, 10, 0);
        passwordField = createStyledPasswordField();
        cardPanel.add(passwordField, cardGbc);

        // Confirm Password - EMOJI FIXED
        cardGbc.gridy = 6;
        cardGbc.insets = new Insets(10, 0, 5, 0);
        JLabel confirmLabel = new JLabel("<html><span style='font-family: Segoe UI Emoji;'>🔒</span> Confirm Password</html>");
        confirmLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        confirmLabel.setForeground(new Color(52, 73, 94));
        cardPanel.add(confirmLabel, cardGbc);

        cardGbc.gridy = 7;
        cardGbc.insets = new Insets(0, 0, 10, 0);
        confirmPasswordField = createStyledPasswordField();
        cardPanel.add(confirmPasswordField, cardGbc);

        // Register Button
        cardGbc.gridy = 8;
        cardGbc.insets = new Insets(25, 0, 10, 0);
        registerButton = new JButton("CREATE ACCOUNT");
        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        registerButton.setForeground(Color.WHITE);
        registerButton.setBackground(new Color(46, 204, 113));
        registerButton.setFocusPainted(false);
        registerButton.setBorderPainted(false);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerButton.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));
        cardPanel.add(registerButton, cardGbc);

        // Back Button
        cardGbc.gridy = 9;
        cardGbc.insets = new Insets(5, 0, 10, 0);
        backButton = new JButton("← BACK TO LOGIN");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        backButton.setForeground(new Color(52, 73, 94));
        backButton.setBackground(new Color(236, 240, 241));
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        cardPanel.add(backButton, cardGbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(cardPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Action Listeners
        registerButton.addActionListener(e -> performRegistration());
        backButton.addActionListener(e -> {
            parentFrame.setVisible(true);
            dispose();
        });

        addHoverEffect(registerButton, new Color(39, 174, 96), new Color(46, 204, 113));
        addHoverEffect(backButton, new Color(220, 225, 230), new Color(236, 240, 241));
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 2, true),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 2, true),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        return field;
    }

    private void performRegistration() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill all fields!",
                    "Incomplete Form",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (username.length() < 3) {
            JOptionPane.showMessageDialog(this,
                    "Username must be at least 3 characters!",
                    "Invalid Username",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (password.length() < 4) {
            JOptionPane.showMessageDialog(this,
                    "Password must be at least 4 characters!",
                    "Weak Password",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this,
                    "Passwords do not match!",
                    "Password Mismatch",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (UserOperations.usernameExists(username)) {
            JOptionPane.showMessageDialog(this,
                    "Username already taken! Please choose another.",
                    "Username Exists",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean success = UserOperations.registerUser(username, password);

        if (success) {
            JOptionPane.showMessageDialog(this,
                    "<html><span style='font-family: Segoe UI Emoji;'>✓</span> Registration successful! <span style='font-family: Segoe UI Emoji;'>🎉</span><br><br>You can now login with your credentials.</html>",
                    "Welcome!",
                    JOptionPane.INFORMATION_MESSAGE);

            parentFrame.setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Registration failed! Please try again.",
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
