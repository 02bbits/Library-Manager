package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginPanel extends JFrame {

    public LoginPanel() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        } catch(Exception ignored){}

        setTitle("Login Page");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);


        GradientPanel loginPanel = new GradientPanel();
        loginPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(new EmptyBorder(70,0,0,0));
        loginPanel.add(titleLabel, BorderLayout.NORTH);
        loginPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel loginForm = new JPanel();
        loginForm.setLayout(new GridBagLayout());
        loginForm.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 10, 10, 10);

        // Add username and password fields with labels
        JLabel usernameLabel = new JLabel();
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 12));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        loginForm.add(usernameLabel, gbc);
        usernameLabel.setIcon(new ImageIcon("LibraryManagement/assets/userIcon.png"));

        JTextField usernameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        loginForm.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel();
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 12));
        passwordLabel.setIcon(new ImageIcon("LibraryManagement/assets/passwordIcon.png"));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        loginForm.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        loginForm.add(passwordField, gbc);

        JButton btnLogin = new JButton("Login");
        JButton btnCancel = new JButton("Cancel");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 12));
        btnCancel.setFont(new Font("Arial", Font.BOLD, 12));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnCancel);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginForm.add(buttonPanel, gbc);

        loginPanel.add(loginForm, BorderLayout.CENTER);

        add(loginPanel);

        btnLogin.addActionListener(e -> {
            String username = usernameField.getText();
            char[] password = passwordField.getPassword();
            JOptionPane.showMessageDialog(this, "Login Attempted for User: " + username);
        });

        btnCancel.addActionListener(e -> {
            usernameField.setText("");
            passwordField.setText("");
        });
    }

    // Gradient panel for custom background
    private static class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;
            int width = getWidth();
            int height = getHeight();

            GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(203, 166, 247),
                    0, height, new Color(0, 206, 209)
            );

            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, width, height);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginPanel loginPage = new LoginPanel();
            loginPage.setVisible(true);
        });
    }
}
