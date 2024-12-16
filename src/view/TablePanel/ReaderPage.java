package view.TablePanel;

import com.formdev.flatlaf.FlatClientProperties;
import controller.UserController;
import util.FormatUtil;
import util.TableUtil;
import view.other.CustomComponent.CustomTable;
import view.other.CustomComponent.CustomTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Objects;

public class ReaderPage extends JPanel {
    private JPanel searchPanel;
    private JPanel tablePanel;
    private JPanel normalTablePanel;
    private JPanel searchTablePanel;
    private JPanel buttonPanel;

    private CustomTable normalTable;
    private CustomTable searchTable;

    private DefaultTableModel model;
    private UserController userController = new UserController();
    private CardLayout cardLayout = new CardLayout();

    private final String PATH = "LibraryManagement/assets/icons/";

    public ReaderPage() {
        setLayout(new BorderLayout(0,20));
        setBorder(new EmptyBorder(30, 30, 30, 30));

        initSearchPanel();
        initTablePanel();
        initButtonPanel();
        add(searchPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
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
        String[] choices = { "Filter by Username", "Filter by Email"};
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
                g2.drawString(Objects.requireNonNull(getSelectedItem()).toString(), 20, 27);
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
                switch (Objects.requireNonNull(cb.getSelectedItem()).toString()) {
                    case "Filter by Username":
                        search(searchField.getText(), "Username");
                        break;
                    case "Filter by Email":
                        search(searchField.getText(), "Email");
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

        // Normal Table Init
        normalTablePanel = new JPanel(new BorderLayout(0,15));
        normalTablePanel.setBorder(new EmptyBorder(5,15,10,15));
        normalTablePanel.setOpaque(false);

        UserController userController = new UserController();
        model = TableUtil.readersToTableModel(userController.getReaders());

        normalTable = new CustomTable(model);
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

    private void initButtonPanel() {
        buttonPanel = new JPanel(new GridLayout(1,0));
        buttonPanel.setPreferredSize(new Dimension(1000,50));

        // Edit Button
        JButton editButton = new JButton("Edit Row");
        editButton.addActionListener(e -> {
            int selectedRow = normalTable.getSelectedRow();

            if (selectedRow != -1) {
                Object email = normalTable.getValueAt(selectedRow, 2);
                Object name = normalTable.getValueAt(selectedRow, 1);
                Object idValue = normalTable.getValueAt(selectedRow, 0);
                int id = Integer.parseInt(idValue.toString());

                CustomTextField nameField = new CustomTextField();
                nameField.setText(name.toString());
                CustomTextField emailField = new CustomTextField();
                emailField.setText(email.toString());

                UserController userController = new UserController();

                JPanel editPanel = new JPanel(new GridLayout(0, 1, 10, 0));
                editPanel.add(new JLabel("Username:"));
                editPanel.add(nameField);
                editPanel.add(new JLabel("Email:"));
                editPanel.add(emailField);

                int result = JOptionPane.showConfirmDialog(null, editPanel, "Edit Row",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                boolean fieldsAreBlank = nameField.getText().isEmpty() || emailField.getText().isEmpty();

                if (result == JOptionPane.OK_OPTION) {
                    if (!FormatUtil.isValidEmail(emailField.getText())) {
                        JOptionPane.showMessageDialog(null, "Invalid email address");
                    }

                    else if (userController.exist(nameField.getText())) {
                        JOptionPane.showMessageDialog(null, "Username exists");
                    }

                    else if (fieldsAreBlank) {
                        JOptionPane.showMessageDialog(null, "Fields cannot be left empty");
                    }

                    else {
                        normalTable.setValueAt(emailField.getText(), selectedRow, 2);
                        normalTable.setValueAt(nameField.getText(), selectedRow, 1);
                        userController.updateUser(id, nameField.getText(), emailField.getText(), "Reader");
                        JOptionPane.showMessageDialog(null, "Row updated successfully!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "No row selected!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Add Button
        JButton addButton = new JButton("Add Row");
        addButton.addActionListener(e -> {
            CustomTextField nameField = new CustomTextField();
            CustomTextField emailField = new CustomTextField();
            UserController userController = new UserController();

            JPanel editPanel = new JPanel(new GridLayout(0, 1, 0, 0));
            editPanel.add(new JLabel("Username:"));
            editPanel.add(nameField);
            editPanel.add(new JLabel("Email:"));
            editPanel.add(emailField);

            int result = JOptionPane.showConfirmDialog(null, editPanel, "Add Row",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            boolean fieldsAreBlank = nameField.getText().isEmpty() || emailField.getText().isEmpty();

            if (result == JOptionPane.OK_OPTION) {
                if (!FormatUtil.isValidEmail(emailField.getText())) {
                    JOptionPane.showMessageDialog(null, "Invalid email address");
                }

                else if (userController.exist(nameField.getText())) {
                    JOptionPane.showMessageDialog(null, "Username exists");
                }

                else if (fieldsAreBlank) {
                    JOptionPane.showMessageDialog(null, "Fields cannot be left empty");
                }

                else {
                    model.addRow(new Object[]{userController.getLatestID() + 1, nameField.getText(), emailField.getText()});
                    userController.addUser(nameField.getText(), emailField.getText());
                    JOptionPane.showMessageDialog(null, "Row added successfully!");
                }
            }
        });

        // Delete Button
        JButton deleteButton = new JButton("Delete Row");
        deleteButton.addActionListener(e -> {
            int selectedRow = normalTable.getSelectedRow();
            if (selectedRow != -1) {
                int id = Integer.parseInt(normalTable.getValueAt(selectedRow,0).toString());

                int result = JOptionPane.showConfirmDialog(null, "\tThis action cannot be undone","Delete row?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    model.removeRow(selectedRow);
                    userController.deleteUser(id);
                    JOptionPane.showMessageDialog(null, "Row removed successfully!");
                }
            }

            else {
                JOptionPane.showMessageDialog(null, "No row selected!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Button styles
        addButton.setBackground(new Color(227, 142, 73));
        addButton.setForeground(Color.WHITE);
        addButton.putClientProperty(FlatClientProperties.STYLE, "font:bold -1");

        editButton.setBackground(new Color(227, 142, 73));
        editButton.setForeground(Color.WHITE);
        editButton.putClientProperty(FlatClientProperties.STYLE, "font:bold -1");

        deleteButton.setBackground(new Color(227, 142, 73));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.putClientProperty(FlatClientProperties.STYLE, "font:bold -1");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
    }

    private void toNormalTable() {
        cardLayout.show(tablePanel, "Normal Table");
    }

    private void search(String searchQuery, String columnName) {
        cardLayout.show(tablePanel, "Search Table");
        DefaultTableModel newModel = TableUtil.usersToTableModel(userController.searchReaders(searchQuery, columnName));
        searchTable.setModel(newModel);
    }
}
