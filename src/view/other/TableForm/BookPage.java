package view.other.TableForm;

import com.formdev.flatlaf.FlatClientProperties;
import view.other.CustomComponent.CustomTable;
import view.other.CustomComponent.CustomTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BookPage extends JPanel {
    private JPanel searchPanel;
    private JPanel tablePanel;
    private final String PATH = "LibraryManagement/assets/icons/";

    public BookPage() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(30, 30, 30, 30));

        initSearchPanel();
        initTablePanel();
        add(searchPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
    }

    private void initSearchPanel() {
        searchPanel = new JPanel(new BorderLayout(10,0));
        searchPanel.setOpaque(false);
        searchPanel.setPreferredSize(new Dimension(1000,45));

        CustomTextField searchField = new CustomTextField();

        searchField.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        searchField.setSuffixIcon(new ImageIcon(PATH + "findIcon.png"));
        searchField.setHint("Search...");

        String[] choices = { "  Search by Title", "  Search by Author", "  Search by Publisher", "  Search by Genre"};
        final JComboBox<String> cb = new JComboBox<>(choices);
        cb.putClientProperty(FlatClientProperties.STYLE, "arc:25;" + "font:bold -1");
        cb.setPreferredSize(new Dimension(180,20));
        cb.setVisible(true);

        searchPanel.add(cb, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
    }

    void initTablePanel() {
        tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(new EmptyBorder(30,0,15,0));
        setOpaque(false);

        JLabel title = new JLabel("Books Information");
        tablePanel.add(title, BorderLayout.NORTH);

        CustomTable table = new CustomTable(4,4);
        tablePanel.add(table, BorderLayout.CENTER);
    }
}
