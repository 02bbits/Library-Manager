package view;

import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.FlatClientProperties;
import controller.UserController;
import util.FormatUtil;
import view.other.CustomComponent.CustomPasswordField;
import view.other.CustomComponent.CustomTextField;

public class RegistrationPanel extends JPanel {

    public RegistrationPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);

        // ============= Login form panel
        JPanel registrationForm = new JPanel();
        registrationForm.setLayout(new BoxLayout(registrationForm, BoxLayout.Y_AXIS));
        registrationForm.setOpaque(false);

        JPanel centeringPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centeringPanel.setOpaque(false);
        centeringPanel.add(registrationForm);
        add(Box.createVerticalGlue());
        add(centeringPanel);
        add(Box.createVerticalGlue());

        // ============= Title labels
        JLabel titleLabel1 = new JLabel("Register", SwingConstants.CENTER);
        titleLabel1.putClientProperty(FlatClientProperties.STYLE, "font:$h1.font");
        titleLabel1.setForeground(Color.WHITE);
        titleLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        registrationForm.add(Box.createRigidArea(new Dimension(0, 80)));
        registrationForm.add(titleLabel1);

        JLabel titleLabel2 = new JLabel("Create a new account", SwingConstants.CENTER);
        titleLabel2.putClientProperty(FlatClientProperties.STYLE, "font:$h2.font");
        titleLabel2.setForeground(Color.WHITE);
        titleLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        registrationForm.add(Box.createRigidArea(new Dimension(0, 10)));
        registrationForm.add(titleLabel2);
        registrationForm.add(Box.createRigidArea(new Dimension(0, 60)));

        // ============= Username field
        CustomTextField usernameField = new CustomTextField();
        usernameField.setPrefixIcon(new ImageIcon("LibraryManagement/assets/icons/userIcon.png"));
        usernameField.setHint("Enter your username");
        usernameField.setPreferredSize(new Dimension(420, 45));
        usernameField.setMaximumSize(usernameField.getPreferredSize());
        registrationForm.add(usernameField);
        registrationForm.add(Box.createRigidArea(new Dimension(0, 35)));

        // ============= Email field
        CustomTextField emailField = new CustomTextField();
        emailField.setPrefixIcon(new ImageIcon("LibraryManagement/assets/icons/mail.png"));
        emailField.setHint("Enter your email address");
        emailField.setPreferredSize(new Dimension(420, 45));
        emailField.setMaximumSize(emailField.getPreferredSize());
        registrationForm.add(emailField);
        registrationForm.add(Box.createRigidArea(new Dimension(0, 35)));

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
        registrationForm.add(passwordField);
        registrationForm.add(Box.createRigidArea(new Dimension(0, 35)));

        // ============= Confirm Password field
        CustomPasswordField confirmPasswordField = new CustomPasswordField();
        confirmPasswordField.setPrefixIcon(new ImageIcon("LibraryManagement/assets/icons/passwordIcon.png"));
        confirmPasswordField.setHint("Re-enter your password");
        confirmPasswordField.putClientProperty(FlatClientProperties.STYLE, ""
                + "showRevealButton:true;"
                + "showCapsLock:true;"
                + "placeholderForeground:#a1a1a1");
        confirmPasswordField.setPreferredSize(new Dimension(420, 45));
        confirmPasswordField.setMaximumSize(confirmPasswordField.getPreferredSize());
        registrationForm.add(confirmPasswordField);
        registrationForm.add(Box.createRigidArea(new Dimension(0, 30)));

        // ============= Button Panel
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setOpaque(false);
        registrationForm.add(buttonPanel);

        // ============= Register button
        JButton toLoginPanelButton = new JButton("Already have an account? Login");
        toLoginPanelButton.setForeground(new Color(110, 167, 209));
        toLoginPanelButton.setOpaque(false);
        toLoginPanelButton.setContentAreaFilled(false);
        toLoginPanelButton.setBorderPainted(false);
        toLoginPanelButton.addActionListener(e -> {
            Application.toLoginPanel();
        });

        buttonPanel.add(toLoginPanelButton, BorderLayout.EAST);

        // ============= Login button
        JButton btnRegister = new JButton("Register");
        btnRegister.putClientProperty(FlatClientProperties.STYLE,
                "borderWidth:0; focusWidth:0");
        btnRegister.setPreferredSize(new Dimension(80, 40));
        btnRegister.setBackground(new Color(227, 142, 73));
        btnRegister.setForeground(Color.WHITE);
        buttonPanel.add(btnRegister, BorderLayout.SOUTH);

        // ============= Button click action
        btnRegister.addActionListener(e -> {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());
            String confirmPassword = String.valueOf(confirmPasswordField.getPassword());
            UserController userController = new UserController();
            boolean fieldsAreBlank = usernameField.getText().isEmpty() || emailField.getText().isEmpty() || passwordField.getText().isEmpty() || confirmPasswordField.getText().isEmpty();

            if (userController.exist(usernameField.getText())) {
                JOptionPane.showMessageDialog(null, "Username exists");
            }

            else if (!FormatUtil.isValidEmail(emailField.getText())) {
                JOptionPane.showMessageDialog(null, "Invalid email address");
            }

            else if (password.length() < 6) {
                JOptionPane.showMessageDialog(null, "Password too short (minimum 6 characters)");
            }

            else if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(null, "Passwords do not match");
            }

            else if (fieldsAreBlank) {
                JOptionPane.showMessageDialog(null, "Please fill all the fields");
            }

            else {
                userController.register(usernameField.getText(), passwordField.getText(), emailField.getText());
                JOptionPane.showMessageDialog(null, "Account created successfully");
                Application.toMainPanel(username, "Staff");
                usernameField.setText("");
                passwordField.setText("");
                confirmPasswordField.setText("");
                emailField.setText("");
            }
        });
    }
}