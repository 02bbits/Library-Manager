package view.other.dashboard;

import com.formdev.flatlaf.FlatClientProperties;
import view.Application;
import view.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Dashboard extends JLayeredPane {
    private JPanel header;
    private JPanel sidebar;
    private static ArrayList<DashboardItem> items = new ArrayList<>();
    private final String PATH = "LibraryManagement/assets/icons/";
    private final String username;
    private final String role;
    private static DashboardItem currentItem;      // pointer to the current focused item

    public Dashboard(String username, String role) {
        this.username = username;
        this.role = role;

        initHeader();
        initSidebar();
        currentItem = items.getFirst();     // default current item

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(280, 800));
        setOpaque(false);
        setBackground(new Color(0, 0, 60));

        add(header, BorderLayout.NORTH);
        add(sidebar, BorderLayout.CENTER);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFocusPainted(false);
        logoutButton.setBackground(new Color(227, 142, 73));
        logoutButton.setForeground(Color.BLACK);
        logoutButton.setPreferredSize(new Dimension(300, 50));
        logoutButton.setMargin(new Insets(0,15,0,0));
        logoutButton.putClientProperty(FlatClientProperties.STYLE, "arc:35");
        logoutButton.setHorizontalAlignment(SwingConstants.CENTER);
        logoutButton.putClientProperty(FlatClientProperties.STYLE, "font:$dashboard-button.font");

        logoutButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(null, "Logging out of your account","Logout?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                Application.toLoginPanel();
            }
        });

        add(logoutButton, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
    }

    private void initHeader() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setPreferredSize(new Dimension(170, 120));
        infoPanel.setOpaque(false);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JLabel username = new JLabel(this.username);
        username.setForeground(Color.WHITE);
        username.setFont(new Font("Inter", Font.BOLD, 15));
        username.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

        JLabel role = new JLabel(this.role);
        role.setForeground(Color.WHITE);
        role.setFont(new Font("Inter", Font.PLAIN, 12));

        username.setAlignmentX(Component.LEFT_ALIGNMENT);
        role.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoPanel.add(username);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(role);

        header = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(50, 50, 110));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                super.paintComponent(g);
            }
        };
        header.setPreferredSize(new Dimension(300, 120));
        header.add(infoPanel, BorderLayout.EAST);

        JLabel avatar = new JLabel(new ImageIcon(PATH + "avatar.png"));
        avatar.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        header.add(avatar, BorderLayout.WEST);
        header.setOpaque(false);
    }

    private void initSidebar() {
        sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        sidebar.setOpaque(false);
        sidebar.setBorder(BorderFactory.createEmptyBorder(10,15,10,10));
        JLabel barTitle = new JLabel("    TABLES");
        barTitle.putClientProperty(FlatClientProperties.STYLE, "font:$Menu.label.font;");
        barTitle.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
        barTitle.setForeground(Color.WHITE);

        JLabel barTitle2 = new JLabel("    OTHER");
        barTitle2.putClientProperty(FlatClientProperties.STYLE, "font:$Menu.label.font;");
        barTitle2.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
        barTitle2.setForeground(Color.WHITE);

        JLabel barTitle3 = new JLabel("    MAIN");
        barTitle3.putClientProperty(FlatClientProperties.STYLE, "font:$Menu.label.font;");
        barTitle3.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
        barTitle3.setForeground(Color.WHITE);

        addMargin(10);
        sidebar.add(barTitle3);
        addButton("Dashboard", new ImageIcon(PATH + "dashboard.png"), new ImageIcon(PATH + "highlighted-dashboard.png"));
        addMargin(35);

        sidebar.add(barTitle);
        addButton("Books", new ImageIcon(PATH + "book.png"), new ImageIcon(PATH + "highlighted-book.png"));
        addMargin(25);
        addButton("Readers", new ImageIcon(PATH + "users.png"), new ImageIcon(PATH + "highlighted-users.png"));
        addMargin(25);
        addButton("Rents", new ImageIcon(PATH + "highlighted-rent.png"), new ImageIcon(PATH + "rent.png"));
//        sidebar.add(barTitle2);
//        addButton("Messages", new ImageIcon(PATH + "mail.png"), new ImageIcon(PATH + "highlighted-mail.png"));
//        addMargin(25);

        items.getFirst().changeState();     // default focused item
    }

    public static ArrayList<DashboardItem> getItems() {
        return items;
    }

    private void addButton(String name, ImageIcon icon, ImageIcon highlightedIcon) {
        DashboardItem dashboardItem = new DashboardItem(name, icon, highlightedIcon);
        sidebar.add(dashboardItem);
        items.add(dashboardItem);
    }

    private void addMargin(int height) {
        JLabel margin = new JLabel();
        margin.setOpaque(false);
        margin.setMaximumSize(new Dimension(25, height));
        sidebar.add(margin);
    }

    public static void updateAllItemStatus() {
        for (DashboardItem item : items) {
            if (item.isOpen()) {
                item.changeState();
            }
        }
    }

    public static void navigateTo(DashboardItem item) {
        MainPanel.toPage(item.getButtonName());
    }

    public static DashboardItem getCurrentItem() {
        return currentItem;
    }

    public static void setCurrentItem(DashboardItem item) {
        Dashboard.currentItem = item;
    }
}
