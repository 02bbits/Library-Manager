package view.other.Graph;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GraphBar extends JPanel {
    private final int height;
    private final int width;

    public GraphBar(int height, int width, String text) {
        this.height = height;
        this.width = width;

        JLabel bar = new JLabel(" ") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setAlignmentX(Component.CENTER_ALIGNMENT);

                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(253, 175, 74),
                        width, height, new Color(247, 165, 64)
                );

                g2d.setPaint(gradient);
                int arc = 10;
                g2d.fillRoundRect(60, getHeight() - height, width, height, arc, arc);
                g2d.dispose();
            }
        };
        bar.setPreferredSize(new Dimension(width, height));
        bar.setHorizontalAlignment(JLabel.CENTER);

        JLabel title = new JLabel(text);
        title.setForeground(Color.BLACK);
        title.putClientProperty(FlatClientProperties.STYLE, "font:$graph-title.font");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));

        setLayout(new BorderLayout());
        setOpaque(false);
        add(bar, BorderLayout.CENTER);
        add(title, BorderLayout.SOUTH);

        setPreferredSize(new Dimension(width, height + 10));
    }
}
