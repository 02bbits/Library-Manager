package view.TablePanel;

import com.formdev.flatlaf.FlatClientProperties;
import controller.BookController;
import controller.UserController;
import util.DateUtil;
import util.FormatUtil;
import util.TableUtil;
import view.other.CustomComponent.CustomTable;
import view.other.CustomComponent.CustomTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Objects;

public class BookPage extends JPanel {
    private JPanel searchPanel;
    private JPanel tablePanel;
    private JPanel buttonPanel;
    private CardLayout cardLayout = new CardLayout();
    private boolean isSearchPanel = false;
    private JTable normalTable;
    private JTable searchTable;
    private JPanel normalTablePanel;
    private JPanel searchTablePanel;
    private DefaultTableModel model;
    private final BookController bookController = new BookController();
    private final UserController userController = new UserController();
    private final String PATH = "LibraryManagement/assets/icons/";

    public BookPage() {
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
        String[] choices = {"Filter by Title", "Filter by Author", "Filter by Publisher"};
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
                    case "Filter by Title":
                        search(searchField.getText(), "Title");
                        break;
                    case "Filter by Author":
                        search(searchField.getText(), "Author");
                        break;
                    case "Filter by Publisher":
                        search(searchField.getText(), "Publisher");
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

        BookController bookController = new BookController();
        model = TableUtil.booksToTableModel(bookController.getBooks());

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
            int selectedRow = isSearchPanel ? searchTable.getSelectedRow() : normalTable.getSelectedRow();

            if (selectedRow != -1) {
                Object rawId = normalTable.getValueAt(selectedRow, 0);
                Integer id = Integer.valueOf(rawId.toString());
                Object title = normalTable.getValueAt(selectedRow, 1);
                Object author = normalTable.getValueAt(selectedRow, 2);
                Object publisher = normalTable.getValueAt(selectedRow, 3);
                Object genres = normalTable.getValueAt(selectedRow, 4);
                Object publicationDate = normalTable.getValueAt(selectedRow, 5);
                Object status = normalTable.getValueAt(selectedRow, 6);

                CustomTextField titleField = new CustomTextField();
                titleField.setText(title.toString());
                CustomTextField authorField = new CustomTextField();
                authorField.setText(author.toString());
                CustomTextField publisherField = new CustomTextField();
                publisherField.setText(publisher.toString());
                CustomTextField genresField = new CustomTextField();
                genresField.setText(genres.toString());
                CustomTextField publicationDateField = new CustomTextField();
                publicationDateField.setText(publicationDate.toString());
                JComboBox<String> statusField = new JComboBox<>(new String[]{"AVAILABLE", "UNAVAILABLE"});
                statusField.setSelectedItem(status);
                statusField.setBackground(Color.WHITE);
                statusField.setForeground(Color.BLACK);

                JPanel editPanel = new JPanel(new GridLayout(0, 1, 10, 0));
                editPanel.add(new JLabel("Title:"));
                editPanel.add(titleField);
                editPanel.add(new JLabel("Author:"));
                editPanel.add(authorField);
                editPanel.add(new JLabel("Publisher:"));
                editPanel.add(publisherField);
                editPanel.add(new JLabel("Genres:"));
                editPanel.add(genresField);
                editPanel.add(new JLabel("Publication Date:"));
                editPanel.add(publicationDateField);
                editPanel.add(new JLabel("Status"));
                editPanel.add(statusField);

                int result = JOptionPane.showConfirmDialog(null, editPanel, "Edit Row",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                boolean fieldsAreBlank = titleField.getText().isEmpty() || authorField.getText().isEmpty() || publisherField.getText().isEmpty() || genresField.getText().isEmpty() || publicationDateField.getText().isEmpty();

                if (result == JOptionPane.OK_OPTION) {
                    if (fieldsAreBlank) {
                        JOptionPane.showMessageDialog(null, "No row selected!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else if (!FormatUtil.isValidDate(publicationDateField.getText())) {
                        JOptionPane.showMessageDialog(null, "Invalid date format!\nDates must follow this format: YYYY-DD-MM", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        normalTable.setValueAt(titleField.getText(), selectedRow, 1);
                        normalTable.setValueAt(authorField.getText(), selectedRow, 2);
                        normalTable.setValueAt(publisherField.getText(), selectedRow, 3);
                        normalTable.setValueAt(genresField.getText(), selectedRow, 4);
                        normalTable.setValueAt(publicationDateField.getText(), selectedRow, 5);
                        normalTable.setValueAt(statusField.getSelectedItem(), selectedRow, 6);
                        bookController.updateBook(id, titleField.getText(), authorField.getText(), publisherField.getText(), genresField.getText(), publicationDateField.getText(), Objects.requireNonNull(statusField.getSelectedItem()).toString());
                        JOptionPane.showMessageDialog(null, "Row updated successfully!");
                    }
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "No row selected!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Add Button
        JButton addButton = new JButton("Add Row");
        addButton.addActionListener(e -> {
            CustomTextField titleField = new CustomTextField();
            CustomTextField authorField = new CustomTextField();
            CustomTextField publisherField = new CustomTextField();
            CustomTextField genresField = new CustomTextField();
            CustomTextField publicationDateField = new CustomTextField();
            JComboBox<String> statusField = new JComboBox<>(new String[]{"AVAILABLE", "UNAVAILABLE"});
            statusField.setBackground(Color.WHITE);
            statusField.setForeground(Color.BLACK);

            JPanel editPanel = new JPanel(new GridLayout(0, 1, 0, 0));
            editPanel.add(new JLabel("Title:"));
            editPanel.add(titleField);
            editPanel.add(new JLabel("Author:"));
            editPanel.add(authorField);
            editPanel.add(new JLabel("Publisher:"));
            editPanel.add(publisherField);
            editPanel.add(new JLabel("Genres:"));
            editPanel.add(genresField);
            editPanel.add(new JLabel("Publication Date:"));
            editPanel.add(publicationDateField);
            editPanel.add(new JLabel("Status"));
            editPanel.add(statusField);

            int result = JOptionPane.showConfirmDialog(null, editPanel, "Add Row",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            boolean fieldsAreBlank = titleField.getText().isEmpty() || authorField.getText().isEmpty() || publisherField.getText().isEmpty() || genresField.getText().isEmpty() || publicationDateField.getText().isEmpty();

            if (result == JOptionPane.OK_OPTION) {
                if (fieldsAreBlank) {
                    JOptionPane.showMessageDialog(null, "Fields cannot be left empty");
                }

                else if (!FormatUtil.isValidDate(publicationDateField.getText())) {
                    JOptionPane.showMessageDialog(null, "Invalid date format!\nDates must follow this format: YYYY-DD-MM", "Error", JOptionPane.ERROR_MESSAGE);
                }

                else if (!FormatUtil.isValidGenresField(genresField.getText())) {
                    JOptionPane.showMessageDialog(null, "Invalid genres field!\nInput must follow this format: <genre>, <genre>,..", "Error", JOptionPane.ERROR_MESSAGE);
                }

                else {
                    model.addRow(new Object[]{bookController.getLatestID() + 1, titleField.getText(), authorField.getText(), publisherField.getText(), genresField.getText(), publicationDateField.getText(), Objects.requireNonNull(statusField.getSelectedItem()).toString()});
                    JOptionPane.showMessageDialog(null, "Row added successfully!");
                    bookController.addBook(titleField.getText(), authorField.getText(), publisherField.getText(), genresField.getText(), publicationDateField.getText(), Objects.requireNonNull(statusField.getSelectedItem()).toString());
                    titleField.setText("");
                    authorField.setText("");
                    publisherField.setText("");
                    genresField.setText("");
                    publicationDateField.setText("");
                    statusField.setSelectedItem("");
                }
            }
        });

        // Delete Button
        JButton deleteButton = new JButton("Delete Row");
        deleteButton.addActionListener(e -> {
            int selectedRow = isSearchPanel ? searchTable.getSelectedRow() : normalTable.getSelectedRow();
            if (selectedRow != -1) {
                Object tableValue = normalTable.getValueAt(selectedRow, 0);
                int id = Integer.parseInt(tableValue.toString());

                int result = JOptionPane.showConfirmDialog(null, "\tThis action cannot be undone","Delete row?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    model.removeRow(selectedRow);
                    bookController.deleteBook(id);
                    JOptionPane.showMessageDialog(null, "Row removed successfully!");
                }
            }

            else {
                JOptionPane.showMessageDialog(null, "No row selected!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Rent Button
        JButton rentButton = new JButton("Rent Book");
        rentButton.addActionListener(e -> {
            int selectedRow = isSearchPanel ? searchTable.getSelectedRow() : normalTable.getSelectedRow();
            String status = normalTable.getValueAt(selectedRow,6).toString();

            if (status.trim().equals("UNAVAILABLE")) {
                JOptionPane.showMessageDialog(null, "Selected book is not available!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (selectedRow != -1) {
                int bookID = Integer.parseInt(normalTable.getValueAt(selectedRow,0).toString());

                CustomTextField userIDField = new CustomTextField();
                CustomTextField rentDate = new CustomTextField();
                CustomTextField dueDate = new CustomTextField();

                JPanel rentPanel = new JPanel(new GridLayout(0, 1, 0, 0));
                rentPanel.add(new JLabel("UserID:"));
                rentPanel.add(userIDField);
                rentPanel.add(new JLabel("Rent Date:"));
                rentPanel.add(rentDate);
                rentPanel.add(new JLabel("Due Date:"));
                rentPanel.add(dueDate);

                int result = JOptionPane.showConfirmDialog(null, rentPanel, "Rent Selected Book",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                boolean fieldsAreBlank = userIDField.getText().isEmpty() || rentDate.getText().isEmpty() || dueDate.getText().isEmpty();

                if (result == JOptionPane.OK_OPTION) {
                    int userID = Integer.parseInt(userIDField.getText());

                    if (fieldsAreBlank) {
                        JOptionPane.showMessageDialog(null, "Fields cannot be left empty");
                    }

                    else if (!FormatUtil.isValidDate(rentDate.getText())) {
                        JOptionPane.showMessageDialog(null, "Invalid date format!\nDates must follow this format: YYYY-DD-MM", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    else if (!FormatUtil.isValidDate(dueDate.getText())) {
                        JOptionPane.showMessageDialog(null, "Invalid date format!\nDates must follow this format: YYYY-DD-MM", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    else if (userController.getUser(userID) == null) {
                        JOptionPane.showMessageDialog(null, "User does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    else if (!DateUtil.isDateBefore(rentDate.getText(), dueDate.getText())) {
                        JOptionPane.showMessageDialog(null, "Rent date cannot be before rent date!", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    else {
                        bookController.rentBook(bookID, userID, rentDate.getText(), dueDate.getText());
                        model.setValueAt("UNAVAILABLE", selectedRow, 6);
                        JOptionPane.showMessageDialog(null, "Rent book successfully!");
                        userIDField.setText("");
                        rentDate.setText("");
                        dueDate.setText("");
                    }
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

        rentButton.setBackground(new Color(10, 57, 129));
        rentButton.setForeground(Color.WHITE);
        rentButton.putClientProperty(FlatClientProperties.STYLE, "font:bold -1");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(rentButton);
    }

    private void toNormalTable() {
        cardLayout.show(tablePanel, "Normal Table");
        isSearchPanel = false;
    }

    private void search(String searchQuery, String columnName) {
        cardLayout.show(tablePanel, "Search Table");
        DefaultTableModel newModel = TableUtil.booksToTableModel(bookController.searchBook(searchQuery, columnName));
        searchTable.setModel(newModel);
        isSearchPanel = true;
    }
}
