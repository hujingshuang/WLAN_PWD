package com.swjtu;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class mainFrame extends JFrame {
    private JTable table;

    public mainFrame() {
        initComponent();
    }

    private void initComponent() {
        String[][] wlanInfo = new WLAN().getInfo();
        if (wlanInfo == null) {
            return;
        }
        String[] columnNames = {"网络名称", "密码"};

        table = new JTable(wlanInfo, columnNames);

        int rows = table.getRowCount();
        int cols = table.getColumnCount();

        int rowHight = 40;
        int colWidth = 300;

        table.getTableHeader().setFont(new Font("Dialog", Font.TRUETYPE_FONT | Font.BOLD, 20));
        table.setFont(new Font("Menu.font", Font.TRUETYPE_FONT, 20));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setRowHeight(rowHight);

        DefaultTableCellRenderer dct = new DefaultTableCellRenderer();
        dct.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, dct);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setSize(colWidth * cols, rowHight * rows);

        this.add(scroll);
        this.setVisible(true);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        if (rows > 15) {
            rows = 15;
        }
        this.setSize(colWidth * cols, rowHight * (rows + 1));
        this.setTitle("WIFI PASSWORD FINDER");
    }
}
