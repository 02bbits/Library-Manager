package view;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    public MainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Dashboard dashboard = new Dashboard();
        JPanel contentPanel = new JPanel();

        mainPanel.add(dashboard, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.EAST);
        JLabel titleLabel = new JLabel("Library Management");
        add(titleLabel, BorderLayout.NORTH);
        add(mainPanel);
    }
}
