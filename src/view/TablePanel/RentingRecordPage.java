package view.TablePanel;

import com.formdev.flatlaf.FlatClientProperties;
import controller.RentingRecordController;
import util.FormatUtil;
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
    private JPanel normalTablePanel;
    private JPanel searchTablePanel;

    private CustomTable normalTable;
    private CustomTable searchTable;

    private DefaultTableModel model;
    private final RentingRecordController rentingRecordController = new RentingRecordController();
    private final CardLayout cardLayout = new CardLayout();

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
        String[] choices = { "Filter by Rent date (Before)", "Filter by Rent date (After)", "Filter by Due date (Before)", "Filter by Due date (After)"};
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

        searchField.addActionListener(e -> {
            if (searchField.getText().isEmpty()) {
                toNormalTable();
            }

            else {
                if (!FormatUtil.isValidDate(searchField.getText())) {
                    return;
                }

                switch (Objects.requireNonNull(cb.getSelectedItem()).toString()) {
                    case "Filter by Rent date (Before)":
                        search(searchField.getText(), "RentDate", true);
                        break;
                    case "Filter by Rent date (After)":
                        search(searchField.getText(), "RentDate", false);
                        break;
                    case "Filter by Due date (Before)":
                        search(searchField.getText(), "DueDate", true);
                        break;
                    case "Filter by Due date (After)":
                        search(searchField.getText(), "DueDate", false);
                        break;
                }
            }
        });

        searchPanel.add(cb, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
    }

    void initTablePanel() {
        tablePanel = new JPanel(cardLayout);
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(new EmptyBorder(15,0,15,0));
        tablePanel.putClientProperty(FlatClientProperties.STYLE, "arc:25");

        normalTablePanel = new JPanel(new BorderLayout(0,15));
        normalTablePanel.setBorder(new EmptyBorder(5,15,10,15));
        normalTablePanel.setOpaque(false);

        RentingRecordController rentingRecordController = new RentingRecordController();
        DefaultTableModel model = TableUtil.rentingRecordsToTableModel(rentingRecordController.getRecords());

        CustomTable normalTable = new CustomTable(model);
        JScrollPane scrollPane = new JScrollPane(normalTable);
        normalTablePanel.add(scrollPane, BorderLayout.CENTER);

        // Search Tabel Init
        searchTablePanel = new JPanel(new BorderLayout(0,15));
        searchTablePanel.setBorder(new EmptyBorder(5,15,10,15));
        searchTablePanel.setOpaque(false);

        searchTable = new CustomTable(model);
        JScrollPane scrollPane2 = new JScrollPane(searchTable);
        searchTablePanel.add(scrollPane2, BorderLayout.CENTER);


        tablePanel.add(normalTablePanel, "Normal Table");
        tablePanel.add(searchTablePanel, "Search Table");
    }

    private void toNormalTable() {
        cardLayout.show(tablePanel, "Normal Table");
    }

    private void search(String searchQuery, String columnName, boolean beforeMode) {
        cardLayout.show(tablePanel, "Search Table");
        DefaultTableModel newModel = TableUtil.rentingRecordsToTableModel(rentingRecordController.searchRecords(searchQuery, columnName, beforeMode));
        searchTable.setModel(newModel);
    }
}
