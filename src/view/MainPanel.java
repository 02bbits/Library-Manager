package view;

import view.MessagePanel.MainMessagePage;
import view.TablePanel.BookPage;
import view.TablePanel.DashboardPage;
import view.TablePanel.ReaderPage;
import view.TablePanel.RentalPage;
import view.other.dashboard.Dashboard;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    private static ContentPanel contentPanel;

    public MainPanel() {
        Dashboard dashboard = new Dashboard("Username", "Admin");
        JPanel dashboardPanel = new JPanel(new BorderLayout());
        dashboardPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        dashboardPanel.add(dashboard, BorderLayout.CENTER);
        contentPanel = new ContentPanel();

        // Add dashboard and content panellll
        setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);
        add(dashboardPanel, BorderLayout.WEST);
    }

    public static void toPage(String pageName) {
        // Check the current opened item
        if (! (Dashboard.getCurrentItem().getButtonName().equals(pageName))) {
            contentPanel.getCardLayout().show(contentPanel, pageName);
        }
    }

    private static class ContentPanel extends JPanel {
        private final CardLayout cardLayout;

        public ContentPanel() {
            cardLayout = new CardLayout();
            setLayout(cardLayout);
            cardLayout.show(this, "Book Page");     // default page

            // Pages must have the same name as the associated buttons
            DashboardPage dashboardPage = new DashboardPage();
            add(dashboardPage, "Dashboard");
            BookPage bookPage = new BookPage();
            add(bookPage, "Books");
            ReaderPage readerPage = new ReaderPage();
            add(readerPage, "Readers");
            RentalPage rentalPage = new RentalPage();
            add(rentalPage, "Rents");
            MainMessagePage messagePage = new MainMessagePage();
            add(messagePage, "Messages");
        }

        public CardLayout getCardLayout() {
            return cardLayout;
        }
    }


}
