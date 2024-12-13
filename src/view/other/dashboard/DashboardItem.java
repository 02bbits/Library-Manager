package view.other.dashboard;

import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class DashboardItem extends JButton {
    private boolean isOpen = false;
    private final ImageIcon icon;
    private final ImageIcon highlightedIcon;
    private final String buttonName;
    private final Color defaultColor = new Color(0, 0, 60);

    public DashboardItem(String name, ImageIcon icon, ImageIcon highlightedIcon) {
        super("    " + name);
        buttonName = name;
        this.highlightedIcon = highlightedIcon;
        this.icon = icon;
        setIcon(icon);
        setForeground(Color.WHITE);
        setBackground(defaultColor);
        setOpaque(false);
        putClientProperty(FlatClientProperties.STYLE, "arc:35");
        putClientProperty(FlatClientProperties.STYLE, "font:$dashboard-button.font");
        setMaximumSize(new Dimension(300, 50));
        setHorizontalAlignment(SwingConstants.LEFT);
        setMargin(new Insets(0,15,0,0));
        setFocusPainted(false);

        addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isOpen) {
                    Dashboard.updateAllItemStatus();
                    changeState();
                    Dashboard.navigateTo(DashboardItem.this);
                    Dashboard.setCurrentItem(DashboardItem.this);
                }
            }
        });
    }

    public boolean isOpen() {
        return isOpen;
    }

    public String getButtonName() {
        return buttonName;
    }

    public void changeState() {
        isOpen = !isOpen;
        setBackground(isOpen ? new Color(227, 142, 73) : defaultColor);
        setForeground(isOpen ? Color.BLACK : Color.WHITE);
        setIcon(isOpen ? highlightedIcon : icon);
    }
}
