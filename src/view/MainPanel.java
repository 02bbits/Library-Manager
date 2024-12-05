package view;

import view.other.TableForm.BookPage;
import view.other.TableForm.DashboardPage;
import view.other.TableForm.ReaderPage;
import view.other.TableForm.RentalPage;
import view.other.dashboard.Dashboard;
import view.other.dashboard.MessagePage;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    private static ContentPanel contentPanel;

    public MainPanel() {
        Dashboard dashboard = new Dashboard("Username", "Admin");
        contentPanel = new ContentPanel();

        // Add dashboard and content panellll
        setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);
        add(dashboard, BorderLayout.WEST);
    }

    public static void toPage(String pageName) {
        // Check the current opened item
        if (! (Dashboard.getCurrentItem().getButtonName().equals(pageName))) {
            contentPanel.getCardLayout().show(contentPanel, pageName);
        }
    }

    private static class ContentPanel extends JPanel {
        private CardLayout cardLayout;

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
            MessagePage messagePage = new MessagePage();
            add(messagePage, "Messages");
        }

        public CardLayout getCardLayout() {
            return cardLayout;
        }
    }


}
