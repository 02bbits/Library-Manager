package view;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JPanel {
    private final String[] items = {
            "Library Overview",
            "Book Table",
            "Rental Table",
            "Reader Table",
            "Logout"
    };

    public Dashboard() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Optional padding

        JButton btnLibrary = new JButton("Library Overview");
        JButton btnBookTable = new JButton("Book Table");
        JButton btnRentalTable = new JButton("Rental Table");
        JButton btnReaderTable = new JButton("Reader Table");
        JButton btnLogout = new JButton("Logout");

        Dimension BUTTON_SIZE = new Dimension(200, 50); // Width: 200, Height: 50
        int BUTTON_SPACING = 20;

        btnLibrary.setMaximumSize(BUTTON_SIZE);
        btnLibrary.setPreferredSize(BUTTON_SIZE);
        btnLibrary.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button horizontally
        btnBookTable.setMaximumSize(BUTTON_SIZE);
        btnBookTable.setPreferredSize(BUTTON_SIZE);
        btnBookTable.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRentalTable.setMaximumSize(BUTTON_SIZE);
        btnRentalTable.setPreferredSize(BUTTON_SIZE);
        btnRentalTable.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnReaderTable.setMaximumSize(BUTTON_SIZE);
        btnReaderTable.setPreferredSize(BUTTON_SIZE);
        btnReaderTable.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogout.setMaximumSize(BUTTON_SIZE);
        btnLogout.setPreferredSize(BUTTON_SIZE);
        btnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add buttons to the panel
        add(btnLibrary);
        add(Box.createVerticalStrut(BUTTON_SPACING)); // Add space between buttons
        add(btnBookTable);
        add(Box.createVerticalStrut(BUTTON_SPACING)); // Add space between buttons
        add(btnRentalTable);
        add(Box.createVerticalStrut(BUTTON_SPACING)); // Add space between buttons
        add(btnReaderTable);
        add(Box.createVerticalStrut(400)); // Add space between buttons
        add(btnLogout);

        setBackground(new Color(49, 50, 68));
    }

}
