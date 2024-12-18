package view.TablePanel;

import com.formdev.flatlaf.FlatClientProperties;
import controller.BookController;
import controller.RentingRecordController;
import controller.UserController;
import util.DateUtil;
import view.Application;
import view.other.Graph.GraphBar;
import view.other.Graph.GraphPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.RoundRectangle2D;
import java.time.LocalDate;
import java.util.ArrayList;

public class DashboardPage extends JPanel {
    private final JPanel totalInfoPanel;
    private final JPanel analysisPanel;
    private final String PATH = Application.ICON_PATH;
    private GraphPanel graphs;

    UserController userController = new UserController();
    BookController bookController = new BookController();
    RentingRecordController rentingRecordController = new RentingRecordController();

    private TotalInfo panel1;
    private TotalInfo panel2;
    private TotalInfo panel3;

    public DashboardPage() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        totalInfoPanel = new JPanel();
        analysisPanel = new JPanel();
        initTotalInfoPanel();
        initGraphPanel();

        add(totalInfoPanel, BorderLayout.NORTH);
        add(analysisPanel, BorderLayout.CENTER);
    }

    public void initTotalInfoPanel() {
        totalInfoPanel.setLayout(new GridLayout(1,3, 15, 0));
        totalInfoPanel.setPreferredSize(new Dimension(1120, 170));
        totalInfoPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        panel1 = new TotalInfo("Book Total", bookController.getBooks().size(), new ImageIcon(PATH + "bookInfo.png"), new Color(255, 214, 10), new Color(235, 175, 0));
        panel2 = new TotalInfo("Rental Total", rentingRecordController.getRecords().size(), new ImageIcon(PATH + "rentInfo.png"), new Color(99, 242, 63), new Color(55, 222, 43));
        panel3 = new TotalInfo("Registered Readers", userController.getReaders().size(), new ImageIcon(PATH + "userInfo.png"), new Color(233, 63, 242), new Color(192, 43, 222));

        totalInfoPanel.add(panel1);
        totalInfoPanel.add(panel2);
        totalInfoPanel.add(panel3);
    }

    public void initGraphPanel() {
        // Header Panel
        JPanel headPanel = new JPanel(new BorderLayout());
        headPanel.setOpaque(false);
        headPanel.setMaximumSize(new Dimension(1920, 40));

        JButton reloadButton = new JButton(new ImageIcon(PATH + "reload.png"));
        reloadButton.setMaximumSize(new Dimension(30, 30));
        reloadButton.setFocusPainted(false);
        reloadButton.setBackground(Color.WHITE);
        reloadButton.setMargin(new Insets(15, 15, 0, 0));
        reloadButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateInfo();
                updateGraph();
            }
        });

        headPanel.add(reloadButton, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("This Week's Rental Analysis");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(Color.BLACK);
        headPanel.add(titleLabel, BorderLayout.CENTER);

        // Graph
        int WIDTH = 25;
        graphs = new GraphPanel(WIDTH);

        // Init graph bars height
        updateGraph();

        // Add components
        analysisPanel.setLayout(new BoxLayout(analysisPanel, BoxLayout.Y_AXIS));
        analysisPanel.setBackground(Color.WHITE);
        analysisPanel.putClientProperty(FlatClientProperties.STYLE, "arc:20");
        analysisPanel.add(headPanel);
        analysisPanel.add(graphs);
    }

    private void updateInfo() {
        panel1.update(bookController.getBooks().size());
        panel2.update(rentingRecordController.getRecords().size());
        panel3.update(userController.getReaders().size());
    }

    private static class TotalInfo extends JPanel {
        private final Color color1;
        private final Color color2;
        private int value;
        JLabel valueLabel;

        public TotalInfo(String title, int value, ImageIcon icon, Color color1, Color color2) {
            this.color1 = color1;
            this.color2 = color2;

            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            JLabel iconLabel = new JLabel();
            iconLabel.setIcon(icon);
            add(iconLabel);

            JLabel titleLabel = new JLabel(title);
            titleLabel.setBorder(new EmptyBorder(11, 0, 2, 0));
            titleLabel.setForeground(Color.BLACK);
            add(titleLabel);

            valueLabel = new JLabel();
            valueLabel.setText(String.valueOf(value));
            valueLabel.setHorizontalAlignment(SwingConstants.LEFT);
            valueLabel.putClientProperty(FlatClientProperties.STYLE, "font:$value.font");
            valueLabel.setForeground(Color.BLACK);
            add(valueLabel);

            setBorder(BorderFactory.createEmptyBorder(23, 30, 30, 30));
        }

        public void update(int newValue) {
            valueLabel.setText(String.valueOf(newValue));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g.create();
            int width = getWidth();
            int height = getHeight();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            GradientPaint gradient = new GradientPaint(
                    0, height, color1,
                    width, 0, color2
            );
            g2d.setPaint(gradient);

            int arc = 25;
            Shape roundedRect = new RoundRectangle2D.Float(0, 0, width, height, arc, arc);
            g2d.fill(roundedRect);
            g2d.dispose();
        }
    }

    private void updateGraph() {
        ArrayList<LocalDate> datesOfWeek = DateUtil.getDatesOfWeek(DateUtil.getCurrentWeek());
        ArrayList<String> rentDates = rentingRecordController.getRentDates();

        for (int i = 0; i < 7; i++) {
            int height = 10;

            if (rentDates.contains(datesOfWeek.get(i).toString())) {
                for (String rentDate : rentDates) {
                    if (rentDate.equals(datesOfWeek.get(i).toString())) {
                        height += 20;
                    }
                }

                graphs.setBarHeight(i, height);
            }
        }
    }
}
