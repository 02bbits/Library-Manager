package view.TablePanel;

import com.formdev.flatlaf.FlatClientProperties;
import view.other.Graph.GraphBar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.RoundRectangle2D;

public class DashboardPage extends JPanel {
    private final JPanel totalInfoPanel;
    private final JPanel graphPanel;
    private final String PATH = "LibraryManagement/assets/icons/";

    public DashboardPage() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        totalInfoPanel = new JPanel();
        graphPanel = new JPanel();
        initTotalInfoPanel();
        initGraphPanel();

        add(totalInfoPanel, BorderLayout.NORTH);
        add(graphPanel, BorderLayout.CENTER);
    }

    public void initTotalInfoPanel() {
        totalInfoPanel.setLayout(new GridLayout(1,3, 15, 0));
        totalInfoPanel.setPreferredSize(new Dimension(1120, 170));
        totalInfoPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        int fakeValue = 2121;
        TotalInfo panel1 = new TotalInfo("Book Total", fakeValue, new ImageIcon(PATH + "bookInfo.png"), new Color(255, 214, 10), new Color(235, 175, 0));
        TotalInfo panel2 = new TotalInfo("Rental Total", fakeValue, new ImageIcon(PATH + "rentInfo.png"), new Color(99, 242, 63), new Color(55, 222, 43));
        TotalInfo panel3 = new TotalInfo("Registered Readers", fakeValue, new ImageIcon(PATH + "userInfo.png"), new Color(233, 63, 242), new Color(192, 43, 222));

        totalInfoPanel.add(panel1);
        totalInfoPanel.add(panel2);
        totalInfoPanel.add(panel3);
    }

    public void initGraphPanel() {
        // Header Panel
        JPanel headPanel = new JPanel(new BorderLayout());
        headPanel.setOpaque(false);
        headPanel.setMaximumSize(new Dimension(1120, 40));

        JButton reloadButton = new JButton(new ImageIcon(PATH + "reload.png"));
        reloadButton.setMaximumSize(new Dimension(30, 30));
        reloadButton.setFocusPainted(false);
        reloadButton.setBackground(Color.WHITE);
        reloadButton.setMargin(new Insets(15, 15, 0, 0));
        reloadButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println();
            }
        });

        headPanel.add(reloadButton, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("This Week Analysis");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(Color.BLACK);

        headPanel.add(titleLabel, BorderLayout.CENTER);

        // Graphs
        int WIDTH = 25;
        JPanel graphs = new JPanel();
        graphs.setLayout(new BoxLayout(graphs, BoxLayout.X_AXIS));

        graphs.setOpaque(false);
        graphs.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Add 7 sample bar
        graphs.add(new GraphBar(300,WIDTH,"Monday"));
        graphs.add(new GraphBar(200,WIDTH,"Tuesday"));
        graphs.add(new GraphBar(100,WIDTH,"Wednesday"));
        graphs.add(new GraphBar(200,WIDTH,"Thursday"));
        graphs.add(new GraphBar(300,WIDTH,"Friday"));
        graphs.add(new GraphBar(200,WIDTH,"Saturday"));
        graphs.add(new GraphBar(100,WIDTH,"Sunday"));

        // Add components
        graphPanel.setLayout(new BoxLayout(graphPanel, BoxLayout.Y_AXIS));
        graphPanel.setBackground(Color.WHITE);
        graphPanel.putClientProperty(FlatClientProperties.STYLE, "arc:20");
        graphPanel.add(headPanel);
        graphPanel.add(graphs);
    }

    private static class TotalInfo extends JPanel {
        private final Color color1;
        private final Color color2;
        private int value;

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

            JLabel valueLabel = new JLabel();
            valueLabel.setText(String.valueOf(value));
            valueLabel.setHorizontalAlignment(SwingConstants.LEFT);
            valueLabel.putClientProperty(FlatClientProperties.STYLE, "font:$value.font");
            valueLabel.setForeground(Color.BLACK);
            add(valueLabel);

            setBorder(BorderFactory.createEmptyBorder(23, 30, 30, 30));
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
}
