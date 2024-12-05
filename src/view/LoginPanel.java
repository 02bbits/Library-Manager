package view;

import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.FlatClientProperties;
import view.other.CustomPasswordField;
import view.other.CustomTextField;

public class LoginPanel extends JPanel {

    public LoginPanel() {
        // Set main panel layout to GridBagLayout for centering
        setLayout(new GridBagLayout());
        setOpaque(false);

        // ============= Login form panel
        JPanel loginForm = new JPanel(new GridBagLayout());
        loginForm.setPreferredSize(new Dimension(300, 300));
        loginForm.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 45, 20, 10);

        // ============= Title label
        JLabel titleLabel1 = new JLabel("Login", SwingConstants.CENTER);
        titleLabel1.putClientProperty(FlatClientProperties.STYLE, "font:$h1.font");
        titleLabel1.setForeground(Color.WHITE);

        JLabel titleLabel2 = new JLabel("Login to your account", SwingConstants.CENTER);
        titleLabel2.putClientProperty(FlatClientProperties.STYLE, "font:$h2.font");
        titleLabel2.setForeground(Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginForm.add(titleLabel1, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 35, 40, 0);
        loginForm.add(titleLabel2, gbc);
        gbc.insets = new Insets(10, 10, 20, 10);

        // ============= Username label
//        JLabel usernameLabel = new JLabel(new ImageIcon("LibraryManagement/assets/icons/userIcon.png"));
//        gbc.gridx = 0;
//        gbc.gridy = 2;
//        gbc.gridwidth = 1;
//        gbc.anchor = GridBagConstraints.EAST;
//        loginForm.add(usernameLabel, gbc);

        // ============= Password label
//        JLabel passwordLabel = new JLabel(new ImageIcon("LibraryManagement/assets/icons/passwordIcon.png"));
//        gbc.gridx = 0;
//        gbc.gridy = 3;
//        gbc.anchor = GridBagConstraints.EAST;
//        loginForm.add(passwordLabel, gbc);

        // ============= Username field
        CustomTextField usernameField = new CustomTextField();
        usernameField.setPrefixIcon(new ImageIcon("LibraryManagement/assets/icons/userIcon.png"));
        usernameField.setHint("Username");
//        usernameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "User Name");
//        usernameField.setBackground(Color.WHITE);
//        usernameField.putClientProperty(
//                FlatClientProperties.STYLE, "placeholderForeground:#a1a1a1"
//        );

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        loginForm.add(usernameField, gbc);

        // ============= Password field
        CustomPasswordField passwordField = new CustomPasswordField();
        passwordField.setPrefixIcon(new ImageIcon("LibraryManagement/assets/icons/passwordIcon.png"));
        passwordField.setHint("Password");
        passwordField.putClientProperty(FlatClientProperties.STYLE, ""
                + "showRevealButton:true;"
                + "showCapsLock:true;"
                + "placeholderForeground:#a1a1a1");
//        passwordField.setBackground(Color.WHITE);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        loginForm.add(passwordField, gbc);

        // ============= Login button
        JButton btnLogin = new JButton("Login");
        btnLogin.putClientProperty(FlatClientProperties.STYLE,
                "borderWidth:0; focusWidth:0");
        btnLogin.setPreferredSize(new Dimension(50, 27));
        btnLogin.setBackground(new Color(227, 142, 73));
        btnLogin.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        loginForm.add(btnLogin, gbc);

        // Add login form to main panel
        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.gridx = 0;
        mainGbc.gridy = 0;
        mainGbc.anchor = GridBagConstraints.CENTER;
        add(loginForm, mainGbc);

        // ============= Button click action
        btnLogin.addActionListener(e -> {
            String username = usernameField.getText();
            char[] password = passwordField.getPassword();
            Application.toMainPanel();
        });
    }
}
