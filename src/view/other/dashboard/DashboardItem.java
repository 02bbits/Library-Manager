package view.other.dashboard;

import com.formdev.flatlaf.FlatClientProperties;
import util.AnimationUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class DashboardItem extends JButton {
    private boolean isOpen = false;
    private final ImageIcon icon;
    private final ImageIcon highlightedIcon;
    private final String buttonName;
    private final Color defaultColor = new Color(0, 0, 60);
    private final String defaultText;

    public DashboardItem(String name, ImageIcon icon, ImageIcon highlightedIcon) {
        super("    " + name);
        buttonName = name;
        this.highlightedIcon = highlightedIcon;
        this.icon = icon;
        defaultText = getText();
        setIcon(icon);
        setForeground(Color.WHITE);
        setBackground(defaultColor);
        setOpaque(false);
        putClientProperty(FlatClientProperties.STYLE, "arc:35");
        putClientProperty(FlatClientProperties.STYLE, "font:$dashboard-button.font");
        setMaximumSize(new Dimension(300, 50));
        setHorizontalAlignment(JLabel.LEFT);
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

    private void startAnimation() {
        Color startColor = isOpen ? defaultColor : new Color(227, 142, 73);
        Color endColor = isOpen ? new Color(227, 142, 73) : defaultColor;
        AnimationUtil.changeColor(startColor, endColor, this);

        Color startTextColor = isOpen ? Color.WHITE : Color.BLACK;
        Color endTextColor = isOpen ? Color.black : Color.WHITE;
        AnimationUtil.changeForegroundColor(startTextColor, endTextColor, this);

        int Alignment = isOpen ? JLabel.CENTER : JLabel.LEFT;
        setHorizontalAlignment(Alignment);
        setText(isOpen ? getText() + "          ▶" : defaultText);
    }

    public boolean isOpen() {
        return isOpen;
    }

    public String getButtonName() {
        return buttonName;
    }

    public void changeState() {
        isOpen = !isOpen;
        startAnimation();
        setIcon(isOpen ? highlightedIcon : icon);
    }
}
