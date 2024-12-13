package view.TablePanel;

import com.formdev.flatlaf.FlatClientProperties;
import view.other.CustomComponent.CustomTable;
import view.other.CustomComponent.CustomTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BookPage extends JPanel {
    private JPanel searchPanel;
    private JPanel tablePanel;
    private final String PATH = "LibraryManagement/assets/icons/";

    public BookPage() {
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
        String[] choices = { "  Search by Title", "  Search by Author", "  Search by Publisher", "  Search by Genre"};
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
                g2.drawString(getSelectedItem().toString().substring(0, 17) + "..", 20, 27);
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

        // Sample data
        String[] headers = {"ID", "Title", "Author", "Publisher", "Publication Date", "Genre"};
        String[][] data = {
                {"1", "The Great Gatsby", "F. Scott Fitzgerald", "Scribner", "1925-04-10", "Novel"},
                {"2", "To Kill a Mockingbird", "Harper Lee", "J.B. Lippincott & Co.", "1960-07-11", "Fiction"},
                {"3", "1984", "George Orwell", "Secker & Warburg", "1949-06-08", "Dystopian"},
                {"4", "Pride and Prejudice", "Jane Austen", "T. Egerton", "1813-01-28", "Romance"},
                {"5", "The Catcher in the Rye", "J.D. Salinger", "Little, Brown and Company", "1951-07-16", "Fiction"},
                {"6", "Moby-Dick", "Herman Melville", "Harper & Brothers", "1851-10-18", "Adventure"},
                {"7", "The Hobbit", "J.R.R. Tolkien", "George Allen & Unwin", "1937-09-21", "Fantasy"},
                {"8", "Fahrenheit 451", "Ray Bradbury", "Ballantine Books", "1953-10-19", "Dystopian"},
                {"9", "Jane Eyre", "Charlotte Brontë", "Smith, Elder & Co.", "1847-10-16", "Gothic Fiction"},
                {"10", "The Lord of the Rings", "J.R.R. Tolkien", "George Allen & Unwin", "1954-07-29", "Fantasy"},
                {"11", "Animal Farm", "George Orwell", "Secker & Warburg", "1945-08-17", "Satire"},
                {"12", "Brave New World", "Aldous Huxley", "Chatto & Windus", "1932-08-18", "Dystopian"},
                {"13", "Wuthering Heights", "Emily Brontë", "Thomas Cautley Newby", "1847-12-01", "Gothic Fiction"},
                {"14", "Crime and Punishment", "Fyodor Dostoevsky", "The Russian Messenger", "1866-01-01", "Psychological Fiction"},
                {"15", "Great Expectations", "Charles Dickens", "Chapman & Hall", "1861-08-01", "Novel"},
                {"16", "The Alchemist", "Paulo Coelho", "HarperTorch", "1988-04-01", "Adventure"},
                {"17", "Frankenstein", "Mary Shelley", "Lackington, Hughes, Harding, Mavor & Jones", "1818-01-01", "Horror"},
                {"18", "Les Misérables", "Victor Hugo", "A. Lacroix, Verboeckhoven & Cie", "1862-04-03", "Historical Fiction"},
                {"19", "Dracula", "Bram Stoker", "Archibald Constable and Company", "1897-05-26", "Horror"},
                {"20", "War and Peace", "Leo Tolstoy", "The Russian Messenger", "1869-01-01", "Historical Fiction"},
        };
        DefaultTableModel model = new DefaultTableModel(data, headers);

        CustomTable table = new CustomTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        marginPanel.add(scrollPane, BorderLayout.CENTER);

        tablePanel.add(marginPanel, BorderLayout.CENTER);
    }
}
