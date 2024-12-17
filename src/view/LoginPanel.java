package view;

import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.FlatClientProperties;
import controller.UserController;
import view.other.CustomComponent.CustomPasswordField;
import view.other.CustomComponent.CustomTextField;

public class LoginPanel extends JPanel {

    public LoginPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);

        // ============= Login form panel
        JPanel loginForm = new JPanel();
        loginForm.setLayout(new BoxLayout(loginForm, BoxLayout.Y_AXIS));
        loginForm.setOpaque(false);

        JPanel centeringPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centeringPanel.setOpaque(false);
        centeringPanel.add(loginForm);
        add(Box.createVerticalGlue());
        add(centeringPanel);
        add(Box.createVerticalGlue());

        // ============= Title labels
        JLabel titleLabel1 = new JLabel("Login", SwingConstants.CENTER);
        titleLabel1.putClientProperty(FlatClientProperties.STYLE, "font:$h1.font");
        titleLabel1.setForeground(Color.WHITE);
        titleLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginForm.add(Box.createRigidArea(new Dimension(0, 80)));
        loginForm.add(titleLabel1);

        JLabel titleLabel2 = new JLabel("Login to your account", SwingConstants.CENTER);
        titleLabel2.putClientProperty(FlatClientProperties.STYLE, "font:$h2.font");
        titleLabel2.setForeground(Color.WHITE);
        titleLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginForm.add(Box.createRigidArea(new Dimension(0, 10)));
        loginForm.add(titleLabel2);
        loginForm.add(Box.createRigidArea(new Dimension(0, 60)));

        // ============= Username field
        CustomTextField usernameField = new CustomTextField();
        usernameField.setPrefixIcon(new ImageIcon("LibraryManagement/assets/icons/userIcon.png"));
        usernameField.setHint("Enter your username");
        usernameField.setPreferredSize(new Dimension(420, 45));
        usernameField.setMaximumSize(usernameField.getPreferredSize());
        loginForm.add(usernameField);
        loginForm.add(Box.createRigidArea(new Dimension(0, 35)));

        // ============= Password field
        CustomPasswordField passwordField = new CustomPasswordField();
        passwordField.setPrefixIcon(new ImageIcon("LibraryManagement/assets/icons/passwordIcon.png"));
        passwordField.setHint("Enter your password");
        passwordField.putClientProperty(FlatClientProperties.STYLE, ""
                + "showRevealButton:true;"
                + "showCapsLock:true;"
                + "placeholderForeground:#a1a1a1");
        passwordField.setPreferredSize(new Dimension(420, 45));
        passwordField.setMaximumSize(passwordField.getPreferredSize());
        loginForm.add(passwordField);
        loginForm.add(Box.createRigidArea(new Dimension(0, 30)));

        // ============= Button Panel
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setOpaque(false);
        loginForm.add(buttonPanel);

        // ============= Register button
        JButton toRegistrationPanelButton = new JButton("Create a new account");
        toRegistrationPanelButton.setForeground(new Color(110, 167, 209));
        toRegistrationPanelButton.setFont(toRegistrationPanelButton.getFont().deriveFont(Font.PLAIN, 13));
        toRegistrationPanelButton.setOpaque(false);
        toRegistrationPanelButton.setContentAreaFilled(false);
        toRegistrationPanelButton.setBorderPainted(false);
        toRegistrationPanelButton.addActionListener(e -> {
            Application.toRegisterPanel();
        });

        buttonPanel.add(toRegistrationPanelButton, BorderLayout.EAST);

        // ============= Login button
        JButton btnLogin = new JButton("Login");
        btnLogin.putClientProperty(FlatClientProperties.STYLE,
                "borderWidth:0; focusWidth:0");
        btnLogin.setPreferredSize(new Dimension(80, 40));
        btnLogin.setBackground(new Color(227, 142, 73));
        btnLogin.setForeground(Color.WHITE);
        buttonPanel.add(btnLogin, BorderLayout.SOUTH);

        // ============= Button click action
        btnLogin.addActionListener(e -> {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());
            UserController userController = new UserController();
            int userID = userController.login(username, password);

            if (userID != 0 && !userController.getUser(userID).isReader()) {
                Application.toMainPanel(username, userController.getUser(userID).getRole());
                usernameField.setText("");
                passwordField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Login failed\nInvalid username or password");
            }
        });
    }
}