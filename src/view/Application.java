package view;

import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import java.awt.Dimension;
import java.io.File;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;


public class Application extends JFrame {
    private RegistrationPanel registrationPanel;
    private final LoginPanel loginPanel;
    private MainPanel mainPanel;
    private static Application app;
    Image img = Toolkit.getDefaultToolkit().getImage("LibraryManagement" + File.separator + "assets" + File.separator + "banner.png");
    public static final String ICON_PATH = "LibraryManagement" + File.separator + "assets" + File.separator + "icons" + File.separator;

    public Application() {
        setLayout(new CardLayout());

        loginPanel = new LoginPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img, 0, 0, this);
            }
        };
        registrationPanel = new RegistrationPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img, 0, 0, this);
            }
        };

        setContentPane(loginPanel);
        getRootPane().putClientProperty(FlatClientProperties.FULL_WINDOW_CONTENT, true);
    }

    public static void toMainPanel(String username, String role) {
        app.mainPanel = new MainPanel(username, role);

        FlatAnimatedLafChange.showSnapshot();
        app.setContentPane(app.mainPanel);
        app.mainPanel.applyComponentOrientation(app.getComponentOrientation());
        SwingUtilities.updateComponentTreeUI(app.mainPanel);
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }

    public static void toLoginPanel() {
        FlatAnimatedLafChange.showSnapshot();
        app.setContentPane(app.loginPanel);
        app.loginPanel.applyComponentOrientation(app.getComponentOrientation());
        SwingUtilities.updateComponentTreeUI(app.loginPanel);
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }

    public static void toRegisterPanel() {
        FlatAnimatedLafChange.showSnapshot();
        app.setContentPane(app.registrationPanel);
        app.registrationPanel.applyComponentOrientation(app.getComponentOrientation());
        SwingUtilities.updateComponentTreeUI(app.registrationPanel);
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }

    public void run() {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch(Exception ignored){}

        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("theme");
        FlatMacDarkLaf.setup();

        java.awt.EventQueue.invokeLater(() -> {
            app = new Application();
            app.setTitle("Library Management System");
            app.setVisible(true);
            app.setSize(new Dimension(1400, 800));
            app.setMaximumSize(new Dimension(1920, 1080));
            app.setResizable(false);
            app.setLocationRelativeTo(null);
            app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
}
