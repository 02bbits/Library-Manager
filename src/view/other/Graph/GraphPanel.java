package view.other.Graph;

import javax.swing.*;
import java.util.ArrayList;

public class GraphPanel extends JPanel {
    private final ArrayList<GraphBar> graphs;
    public static final int DEFAULT_HEIGHT = 10;

    public GraphPanel(int WIDTH) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // init graph bars
        graphs = new ArrayList<>();
        graphs.add(new GraphBar(DEFAULT_HEIGHT,WIDTH,"Monday"));
        graphs.add(new GraphBar(DEFAULT_HEIGHT,WIDTH,"Tuesday"));
        graphs.add(new GraphBar(DEFAULT_HEIGHT,WIDTH,"Wednesday"));
        graphs.add(new GraphBar(DEFAULT_HEIGHT,WIDTH,"Thursday"));
        graphs.add(new GraphBar(DEFAULT_HEIGHT,WIDTH,"Friday"));
        graphs.add(new GraphBar(DEFAULT_HEIGHT,WIDTH,"Saturday"));
        graphs.add(new GraphBar(DEFAULT_HEIGHT,WIDTH,"Sunday"));

        for (GraphBar graph : graphs) {
            add(graph);
        }
    }

    public void setBarHeight(int barIndex, int barHeight) {
        graphs.get(barIndex).setHeight(barHeight);
    }
}
