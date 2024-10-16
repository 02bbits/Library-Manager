package view;

import javax.swing.*;
import java.awt.CardLayout;

public class MainFrame extends JFrame {
    private final CardLayout cl = new CardLayout();
    private JButton bookButton;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel contentPanel = new JPanel(cl);
    private JButton readerButton;
    private JButton exitButton;

    public MainFrame() {

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("mainFrame");
        frame.setContentPane(new MainFrame().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
