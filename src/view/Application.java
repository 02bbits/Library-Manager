package view;

import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;


public class Application extends JFrame {
    private final LoginPanel loginPanel;
    private final MainPanel mainPanel;
    private static Application app;
    Image img = Toolkit.getDefaultToolkit().getImage("LibraryManagement/assets/banner.png");

    public Application() {
        setLayout(new CardLayout());

        mainPanel = new MainPanel();
        loginPanel = new LoginPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img, 0, 0, this);
            }
        };
        setContentPane(loginPanel);
        getRootPane().putClientProperty(FlatClientProperties.FULL_WINDOW_CONTENT, true);
    }

    public static void toMainPanel() {
        FlatAnimatedLafChange.showSnapshot();
        app.setContentPane(app.mainPanel);
        app.mainPanel.applyComponentOrientation(app.getComponentOrientation());
        SwingUtilities.updateComponentTreeUI(app.mainPanel);
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }

    public static void main(String[] args) {
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
