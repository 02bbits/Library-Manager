package view.MessagePanel;

import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainMessagePage extends JPanel {
    private JPanel mainPanel;
    private JLabel searchPanel;

    public MainMessagePage() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(30, 30, 30, 30));

        initMainPanel();
        add(mainPanel, BorderLayout.CENTER);
    }

    private void initMainPanel() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.putClientProperty(FlatClientProperties.STYLE, "arc:25");

        JList<String> messageList = new JList<>();
        JScrollPane scrollPane = new JScrollPane(messageList);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }
}
