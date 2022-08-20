package app.home.activity.statistics;

import app.home.activity.usermanagement.UserManagement;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import sql.Database;
import swing.button.Button;
import swing.combobox.Combobox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class Statistic extends JPanel {

    private JPanel mainPanel;
    private JPanel showPanel;
    private JPanel btnPanel;
    private JPanel topPanel;

    public Statistic() {
        add(mainPanel);
        customs();
        addAllListener();
    }

    private void addAllListener() {
        quantityS.addActionListener(this::onQuantityS);
        revenueS.addActionListener(this::onRevenueS);
        statisticsBy.addActionListener(this::onStatisticsBy);
        chooseM.addActionListener(this::onChooseM);
        chooseY.addActionListener(this::onChooseY);
    }

    private void onChooseY(ActionEvent actionEvent) {
        if (!chooseM.isVisible()) {
            if (statisticsOption) {
                int year = Integer.parseInt(Objects.requireNonNull(chooseY.getSelectedItem()).toString());
                int[] a = new int[12];
                for (int i = 0; i < 12; i++) {
                    Database db = new Database();
                    if (db.executeQuery("SELECT SUM(SoLuong) as DEM FROM ChiTietHD" +
                            " where IDHoaDon in (select IDHoaDon from HoaDon where Month(NgayBan) = " +
                            (i + 1) + " AND year(NgayBan) = " + year + ")")) {
                        if (db.resulSetNext()) {
                            String set = db.getResulString("DEM");
                            if (set != null) {
                                a[i] = Integer.parseInt(set);
                            }
                        }
                    }
                }
                DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                for (int i = 0; i < a.length; i++) {
                    dcd.setValue(a[i], "Số lượng sản phẩm", "" + (i + 1));
                }
                JFreeChart jchart = ChartFactory.createBarChart("Thống kê số lượng sản phẩm bán được trong năm " + year, "Tháng", "Số lượng", dcd, PlotOrientation.VERTICAL, true, true, false);
                CategoryPlot cp = jchart.getCategoryPlot();
                cp.setRangeGridlinePaint(Color.BLACK);
                if (chartPanel != null)
                    showPanel.remove(chartPanel);
                chartPanel = new ChartPanel(jchart);
                showPanel.add(chartPanel, BorderLayout.CENTER);
            } else {
                int year = Integer.parseInt(Objects.requireNonNull(chooseY.getSelectedItem()).toString());
                int[] a = new int[12];
                for (int i = 0; i < 12; i++) {
                    Database db = new Database();
                    if (db.executeQuery("select SUM(GiaBan * c.SoLuong) DoanhThu from ChiTietHD c,SanPham s, HoaDon h where c.IDSanPham = s.IDSanPham and c.`IDHoaDon` = h.`IDHoaDon` and month(NgayBan) =" + (i + 1) + " and year(NgayBan) = " + year)) {
                        if (db.resulSetNext()) {
                            String set = db.getResulString("DoanhThu");
                            if (set != null) {
                                a[i] = Integer.parseInt(set);
                            }
                        }
                    }
                }
                DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                for (int i = 0; i < a.length; i++) {
                    dcd.setValue(a[i], "Doanh thu", "" + (i + 1));
                }
                JFreeChart jchart = ChartFactory.createBarChart("Thống kê doanh thu trong năm " + year, "Tháng", "Doanh thu", dcd, PlotOrientation.VERTICAL, true, true, false);
                CategoryPlot cp = jchart.getCategoryPlot();
                cp.setRangeGridlinePaint(Color.BLACK);
                if (chartPanel != null)
                    showPanel.remove(chartPanel);
                chartPanel = new ChartPanel(jchart);
                showPanel.add(chartPanel, BorderLayout.CENTER);
            }
        }
    }

    private void onChooseM(ActionEvent actionEvent) {
        if (statisticsOption) {
            int month = Integer.parseInt(Objects.requireNonNull(chooseM.getSelectedItem()).toString());
            int year = Integer.parseInt(Objects.requireNonNull(chooseY.getSelectedItem()).toString());
            int numDate = 0;
            int[] a = new int[31];
            for (int i = 0; i < 31; i++) {
                Database db = new Database();
                if (db.executeQuery("SELECT SUM(SoLuong) as DEM FROM ChiTietHD" +
                        " where IDHoaDon in (select IDHoaDon from HoaDon where NgayBan = '" + year + "-" + month + "-" + (i + 1) + "')")) {
                    if (db.resulSetNext()) {
                        String set = db.getResulString("DEM");
                        if (set != null) {
                            a[i] = Integer.parseInt(set);
                        }
                        numDate++;
                    }
                }
            }
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            for (int i = 0; i < numDate; i++) {
                dcd.setValue(a[i], "Số lượng sản phẩm", "" + (i + 1));
            }
            JFreeChart jchart = ChartFactory.createBarChart("Thống kê số lượng sản phẩm bán được trong tháng" + month, "Ngày", "Số lượng", dcd, PlotOrientation.VERTICAL, true, true, false);
            jchart.setAntiAlias(false);
            CategoryPlot cp = jchart.getCategoryPlot();
            cp.setRangeGridlinePaint(Color.BLACK);
            if (chartPanel != null)
                showPanel.remove(chartPanel);
            chartPanel = new ChartPanel(jchart);
            showPanel.add(chartPanel, BorderLayout.CENTER);
        } else {
            int month = Integer.parseInt(Objects.requireNonNull(chooseM.getSelectedItem()).toString());
            int year = Integer.parseInt(Objects.requireNonNull(chooseY.getSelectedItem()).toString());
            int numDate = 0;
            int[] a = new int[31];
            for (int i = 0; i < 31; i++) {
                Database db = new Database();
                if (db.executeQuery("select SUM(GiaBan * c.SoLuong) DoanhThu from ChiTietHD c,SanPham s, HoaDon h where c.IDSanPham = s.IDSanPham and c.`IDHoaDon` = h.`IDHoaDon` and NgayBan = '" + year + "-" + month + "-" + (i + 1) + "'")) {
                    if (db.resulSetNext()) {
                        String set = db.getResulString("DoanhThu");
                        if (set != null) {
                            a[i] = Integer.parseInt(set);
                        }
                        numDate++;
                    }
                }
            }
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            for (int i = 0; i < numDate; i++) {
                dcd.setValue(a[i], "Số lượng sản phẩm", "" + (i + 1));
            }
            JFreeChart jchart = ChartFactory.createBarChart("Thống kê doanh thu tháng " + month, "Ngày", "Doanh thu", dcd, PlotOrientation.VERTICAL, true, true, false);
            jchart.setAntiAlias(false);
            CategoryPlot cp = jchart.getCategoryPlot();
            cp.setRangeGridlinePaint(Color.BLACK);
            if (chartPanel != null)
                showPanel.remove(chartPanel);
            chartPanel = new ChartPanel(jchart);
            showPanel.add(chartPanel, BorderLayout.CENTER);
        }
    }

    private void onStatisticsBy(ActionEvent actionEvent) {
        boolean mode = statisticsBy.getSelectedIndex() == 0;
        chooseM.setVisible(mode);
        if (mode) {
            onChooseM(actionEvent);
        } else onChooseY(actionEvent);
    }

    private void onRevenueS(ActionEvent actionEvent) {
        statisticsOption = false;
        switch (statisticsBy.getSelectedIndex()) {
            case 0 -> onChooseM(actionEvent);
            case 1 -> onChooseY(actionEvent);
        }
    }

    private void onQuantityS(ActionEvent actionEvent) {
        statisticsOption = true;
        switch (statisticsBy.getSelectedIndex()) {
            case 0 -> onChooseM(actionEvent);
            case 1 -> onChooseY(actionEvent);
        }
    }

    private void customs() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        quantityS.setText("Thống kê theo số lượng");
        quantityS.setFont(new Font("SansSerif", Font.BOLD, 25));
        quantityS.setColor(new Color(76, 204, 255, 100));
        quantityS.setColorClick(new Color(76, 204, 255));
        quantityS.setBorderColor(new Color(7, 93, 192));
        quantityS.setColorOver(new Color(76, 204, 255, 174));
        quantityS.setRadius(50);
        quantityS.setPreferredSize(new Dimension(450, 100));
        quantityS.setIcon(getInstanceIcon("/icon/packages.png", 60));
        btnPanel.add(quantityS, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 0);
        revenueS.setText("Thống kê theo doanh thu");
        revenueS.setFont(new Font("SansSerif", Font.BOLD, 25));
        revenueS.setIcon(getInstanceIcon("/icon/arrows.png", 60));
        revenueS.setColor(new Color(76, 204, 255, 100));
        revenueS.setColorClick(new Color(76, 204, 255));
        revenueS.setBorderColor(new Color(7, 93, 192));
        revenueS.setColorOver(new Color(76, 204, 255, 174));
        revenueS.setRadius(50);
        revenueS.setPreferredSize(new Dimension(450, 100));
        btnPanel.add(revenueS, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 10);
        JLabel label = new JLabel("Thống kê theo: ");
        label.setPreferredSize(new Dimension(200, 60));
        label.setFont(new Font("SansSerif", Font.PLAIN, 20));
        topPanel.add(label, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 10);
        statisticsBy.setPreferredSize(new Dimension(200, 60));
        statisticsBy.addItem("Theo ngày");
        statisticsBy.addItem("Theo tháng");
        statisticsBy.setLabeText("");
        topPanel.add(statisticsBy, gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 10);
        chooseM.setPreferredSize(new Dimension(200, 60));
        for (int i = 1; i <= 12; i++) {
            chooseM.addItem(i);
        }
        chooseM.setLabeText("");
        topPanel.add(chooseM, gbc);
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 10);
        chooseY.setPreferredSize(new Dimension(200, 60));
        chooseY.addItem(2022);
        chooseY.setLabeText("");
        topPanel.add(chooseY, gbc);
    }

    public ImageIcon getInstanceIcon(String iconPath, int size) {
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(Statistic.class.getResource(iconPath)));
        icon = new ImageIcon(
                icon.getImage().getScaledInstance(size, size,
                        Image.SCALE_SMOOTH));
        return icon;
    }

    private final Button quantityS = new Button();
    private final Button revenueS = new Button();
    private final Combobox<String> statisticsBy = new Combobox<>();
    private final Combobox<Integer> chooseM = new Combobox<>();
    private final Combobox<Integer> chooseY = new Combobox<>();
    private ChartPanel chartPanel;
    private boolean statisticsOption = true;
}
