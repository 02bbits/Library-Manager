package view.other.CustomComponent;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;

public class CustomTable extends JTable {
    public CustomTable(TableModel model) {
        super(model);
        setForeground(Color.black);
        setBackground(Color.white);
        setShowHorizontalLines(true);
        setRowHeight(40);
        setFocusable(false);
        getColumnModel().getColumn(0).setPreferredWidth(5);

        setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, false, row, column);
                label.setFont(label.getFont().deriveFont(Font.PLAIN, 15));

                // Set styles on selection
                if (isSelected) {
                    label.setBackground(new Color(227, 142, 73));
                    label.setForeground(Color.white);
                } else {
                    label.setBackground(Color.white);
                    label.setForeground(new Color(20, 20, 20));

                    // Set styles for specific values, why do I keep commenting even tho I am the only one who view these codes
                    if ("AVAILABLE".equals(value)) {
                        label.setForeground(Color.GREEN);
                        label.setFont(label.getFont().deriveFont(Font.BOLD, 16));
                    }

                    else if ("UNAVAILABLE".equals(value)) {
                        label.setForeground(Color.RED);
                        label.setFont(label.getFont().deriveFont(Font.BOLD, 16));
                    }
                }

                return label;
            }
        });

        JTableHeader header = getTableHeader();
        header.setReorderingAllowed(false);

        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setFocusable(false);
                label.setFont(header.getFont().deriveFont(Font.BOLD, 15));
                label.setBorder(null);
                label.setBackground(Color.white);
                label.setForeground(Color.black);
                label.setHorizontalAlignment(SwingConstants.LEFT);
                return label;
            }
        });
    }

    public boolean isCellEditable(int row, int column) {
        return false;
    };
}
