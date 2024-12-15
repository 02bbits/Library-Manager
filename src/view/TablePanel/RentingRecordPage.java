package view.TablePanel;

import com.formdev.flatlaf.FlatClientProperties;
import controller.RentingRecordController;
import controller.UserController;
import util.TableUtil;
import view.other.CustomComponent.CustomTable;
import view.other.CustomComponent.CustomTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Objects;

public class RentingRecordPage extends JPanel {
    private JPanel searchPanel;
    private JPanel tablePanel;
    private final String PATH = "LibraryManagement/assets/icons/";

    public RentingRecordPage() {
        setLayout(new BorderLayout(0,20));
        setBorder(new EmptyBorder(30, 30, 30, 30));

        initSearchPanel();
        initTablePanel();
        add(searchPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
    }

    private void initSearchPanel() {
        searchPanel = new JPanel(new BorderLayout());
        searchPanel.setOpaque(false);
        searchPanel.setPreferredSize(new Dimension(1000,45));

        CustomTextField searchField = new CustomTextField();

        searchField.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        searchField.setSuffixIcon(new ImageIcon(PATH + "findIcon.png"));
        searchField.setHint("Search...");

        // Workaround for half-rounded JComboBox
        String[] choices = { "  Search by Username", "  Search by Email"};
        JComboBox<String> cb = new JComboBox<>(choices) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(10, 57, 129));
                g2.fillRoundRect(0, 0, getWidth() / 2 + 10, getHeight(), 20, 20);
                g2.fillRect(getWidth() / 2 - 10, 0, getWidth(), getHeight());

                g2.setColor(Color.WHITE);
                g2.drawString(Objects.requireNonNull(getSelectedItem()).toString().substring(0, 17) + "..", 20, 27);
            }
        };

        cb.setOpaque(false);
        cb.putClientProperty(FlatClientProperties.STYLE, "font:bold -1");
        cb.setBorder(BorderFactory.createEmptyBorder());
        cb.setPreferredSize(new Dimension(180,20));

        searchPanel.add(cb, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
    }

    void initTablePanel() {
        tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(new EmptyBorder(15,0,15,0));
        tablePanel.putClientProperty(FlatClientProperties.STYLE, "arc:25");

        JPanel marginPanel = new JPanel(new BorderLayout(0,15));   // Nested Panel for element margin
        marginPanel.setBorder(new EmptyBorder(5,15,10,15));
        marginPanel.setOpaque(false);

        RentingRecordController rentingRecordController = new RentingRecordController();
        DefaultTableModel model = TableUtil.rentingRecordsToTableModel(rentingRecordController.getRecords());

        CustomTable table = new CustomTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        marginPanel.add(scrollPane, BorderLayout.CENTER);

        tablePanel.add(marginPanel, BorderLayout.CENTER);
    }
}
