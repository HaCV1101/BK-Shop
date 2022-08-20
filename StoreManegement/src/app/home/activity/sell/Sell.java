package app.home.activity.sell;

import app.home.activity.dialogEvent.ButtonActionListener;
import app.home.activity.dialogEvent.ButtonListener;
import model.*;
import sql.Database;
import swing.button.Button;
import swing.checkbox.JCheckBoxCustom;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.*;

public class Sell extends JPanel {

    private JPanel sellPanel;
    private JPanel productPreview;
    private JTable table1;
    private JPanel panelOrder;
    private JCheckBoxCustom usePointsCheckBox = new JCheckBoxCustom();
    private JTextField useDTL;
    private JComboBox<String> saleComboBox;
    private final Button continueButton = new Button();
    private JTextField searchTextField;
    private JLabel totalCost;
    private JTextField cusSearch;
    private JLabel cusName;
    private JLabel phoneNum;
    private JLabel dTL;
    private JLabel useDTLLable;
    private JPanel cusPanel;
    private JPanel btnPanel;
    private JPanel ckBoxPanel;
    private JLabel totalCost2;
    private List<ProductBox> productBoxes = new ArrayList<>();
    private Order order;
    private User user;

    public Sell(User user) {
        super();
        this.user = user;
        order = new Order(user);
        add(sellPanel);
        customControl();
        addAllListener();
    }

    private void addAllListener() {
        searchTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                for (ProductBox prdB : productBoxes) {
                    prdB.setVisible(true);
                    if (prdB.search(searchTextField.getText())) {
                        for (OrderDetail orderDetail : order.getOrderDetails()) {
                            if (orderDetail.getProduct().getProductID().equals(prdB.getId())) {
                                prdB.setVisible(false);
                                break;
                            } else {
                                prdB.setVisible(true);
                            }
                        }
                    } else {
                        prdB.setVisible(false);
                    }
                }
            }
        });
    }

    private void customControl() {
        String[] col = {"STT", "Mã sản phẩm", "Tên sản phẩm", "Đơn giá", "Số lượng", "Thành tiền"};
        DefaultTableModel dmt = new DefaultTableModel(null, col);
        table1.setModel(dmt);
        table1.getTableHeader().setFont(new Font("sansserif", Font.BOLD, 20));
        table1.getColumnModel().getColumn(0).setMaxWidth(120);
        table1.getColumnModel().getColumn(0).setMinWidth(120);
        table1.getColumnModel().getColumn(1).setMaxWidth(180);
        table1.getColumnModel().getColumn(1).setMinWidth(180);
        table1.getColumnModel().getColumn(2).setMinWidth(300);
        table1.getColumnModel().getColumn(4).setMinWidth(120);
        table1.getColumnModel().getColumn(4).setMaxWidth(120);
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        Database db = new Database();
        if (db.executeQuery("SELECT IDSanPham, TenSanPham, GiaBan FROM SanPham")) {
            while (db.resulSetNext()) {
                String idSP = db.getResulString("IDSanPham");
                String tenSP = db.getResulString("TenSanPham");
                String giaSP = db.getResulString("GiaBan");
                productBoxes.add(new ProductBox(idSP, tenSP, giaSP + " Đồng"));
            }
        }
        ButtonActionListener btL = new ButtonListener() {
            @Override
            public void actionPerformed(int choose, Product product, int quantity) {
                if (choose == 1) {
                    Object[] row = {
                            (dmt.getRowCount() + 1) + "",
                            product,
                            product.getProductName(),
                            product.getCost() + "",
                            quantity + "",
                            quantity * product.getCost() + ""};
                    dmt.addRow(row);
                    order.addProduct(product, quantity);
                    for (ProductBox c : productBoxes) {
                        if (c.getId().equals(product.getProductID())) {
                            productPreview.getComponent(Integer.parseInt(c.getId().substring(2)) - 1)
                                    .setVisible(false);
                        }
                    }
                    reCalculateTotalCost();
                } else if (choose == -1) {
                    dmt.removeRow(table1.getSelectedRow());
                    order.removeProduct(product);
                    productPreview.getComponent(Integer.parseInt(product.getProductID().substring(2)) - 1)
                            .setVisible(true);
                    reCalculateTotalCost();
                } else {
                    order.addProduct(product, quantity);
                    table1.setValueAt(quantity, table1.getSelectedRow(), 4);
                    table1.setValueAt(quantity * product.getCost(), table1.getSelectedRow(), 5);
                    reCalculateTotalCost();
                }
            }
        };
        table1.setAutoCreateRowSorter(true);
        table1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if (listSelectionEvent.getValueIsAdjusting()) {
                    int selected = table1.getSelectedRow();
                    if (selected >= 0) {
                        System.out.println("selected " + selected);
                        int i = 0;
                        Product p = (Product) dmt.getValueAt(selected, 1);
                        for (OrderDetail od : order.getOrderDetails()) {
                            if (od.getProduct().equals(p)) {
                                i = od.getQuantity();
                                break;
                            }
                        }
                        new ProductInfo(btL, (Product) dmt.getValueAt(selected, 1), i, false);
                    }
                }
            }
        });

        for (ProductBox productBox : productBoxes) {
            productPreview.add(productBox);
            productBox.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    ProductBox source = ((ProductBox) e.getSource());
                    System.out.println(source.getName());
                    Product p = new Product();
                    if (p.querySQL("SELECT * FROM SanPham where IDSanPham = '" + source.getId() + "'")) {
                        new ProductInfo(btL, p, 1, true);
                    }
                }
            });
        }
        for (int i = 0; i < 20; i++) {
            saleComboBox.addItem((i * 5) + "");
        }
        saleComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                order.setDiscount(Integer.parseInt((String) Objects.requireNonNull(saleComboBox.getSelectedItem())));
                reCalculateTotalCost();
            }
        });
        cusPanel.setVisible(false);
        usePointsCheckBox.setText("Sử dụng thẻ tích điểm");
        ckBoxPanel.add(usePointsCheckBox);
        usePointsCheckBox.addActionListener(actionEvent -> {
            if (order.getTotalCost() == 0) {
                usePointsCheckBox.setSelected(false);
                order.setHaveCard(false);
            } else {
                cusPanel.setVisible(usePointsCheckBox.isSelected());
                order.setHaveCard(useDTL.isVisible());
            }
        });
        cusName.setVisible(false);
        phoneNum.setVisible(false);
        dTL.setVisible(false);
        useDTLLable.setVisible(false);
        useDTL.setVisible(false);
        cusSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Customer customer = new Customer();
                if (customer.querySQL(
                        "SELECT * FROM KhachHang where IDKhachHang = '" + cusSearch.getText() + "'")) {
                    cusName.setText(customer.getCustomerName());
                    phoneNum.setText(customer.getPhoneNumber());
                    dTL.setText("DTL: " + customer.getPoints() + "");
                    cusName.setVisible(true);
                    phoneNum.setVisible(true);
                    dTL.setVisible(true);
                    useDTLLable.setVisible(true);
                    useDTL.setVisible(true);
                    order.setCustomer(customer);
                } else {
                    JOptionPane.showMessageDialog(cusSearch, "Không tìm thấy id của khách hàng này",
                            "Không tìm thấy", JOptionPane.WARNING_MESSAGE);
                    cusName.setVisible(false);
                    phoneNum.setVisible(false);
                    dTL.setVisible(false);
                    useDTLLable.setVisible(false);
                    useDTL.setVisible(false);
                }
            }
        });
        useDTL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    int usePoints = Integer.parseInt(useDTL.getText());
                    if (usePoints < 0 || usePoints > Integer.parseInt(dTL.getText().substring(5))) {
                        JOptionPane.showMessageDialog(panelOrder,
                                "Điểm sử dụng không thể âm hoặc lớn hơn điểm hiện có",
                                "", JOptionPane.WARNING_MESSAGE);
                        order.setHaveCard(false);
                    } else if (usePoints > order.getTotal()) {
                        JOptionPane.showMessageDialog(panelOrder,
                                "Điểm sử dụng không được lớn hơn tổng tiền",
                                "", JOptionPane.WARNING_MESSAGE);
                        order.setHaveCard(false);
                    } else {
                        order.setUsedPoints(usePoints);
                        order.setHaveCard(true);
                        reCalculateTotalCost();
                    }
                } catch (NumberFormatException e) {
                    useDTL.setText("");
                    order.setHaveCard(false);
                    e.printStackTrace();
                }
            }
        });
        btnPanel.add(continueButton);
        continueButton.setText("HOÀN THÀNH");
        continueButton.setIcon(getInstanceIcon("/icon/order.png", 40));
        continueButton.setIconTextGap(15);
        continueButton.setForeground(new Color(4, 8, 12, 255));
        continueButton.setFont(new Font("Noto Sans CJK HK", Font.BOLD, 20));
        continueButton.setRadius(40);
        continueButton.setPreferredSize(new Dimension(150, 50));
        continueButton.setColor(new Color(76, 204, 255,100));
        continueButton.setColorClick(new Color(76, 204, 255));
        continueButton.setBorderColor(new Color(7, 93, 192));
        continueButton.setColorOver(new Color(76, 204, 255, 174));
        continueButton.setFocusable(false);
        continueButton.setAlignmentY(0);
        continueButton.setPreferredSize(new Dimension(300, 70));
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                while (dmt.getRowCount() > 0) {
                    Product product = (Product) dmt.getValueAt(0, 1);
                    productPreview.getComponent(Integer.parseInt(product.getProductID().substring(2)) - 1)
                            .setVisible(true);
                    dmt.removeRow(0);
                }
                if (order.getTotal() > 0) {
                    System.out.println("print");
                    JOptionPane.showMessageDialog(null, "Xuất hóa đơn thành công");
                    billPrint();
                }
            }
        });
    }

    private void billPrint() {
        if (usePointsCheckBox.isSelected() && useDTL.isVisible()) {
            order.setHaveCard(true);
            Customer customer = new Customer();
            if (customer.querySQL(
                    "SELECT * FROM KhachHang where IDKhachHang = '" + cusSearch.getText() + "'")) {
                System.out.println(customer.getCustomerName());
                if (!useDTL.getText().equals("")) {
                    order.setUsedPoints(Integer.parseInt(useDTL.getText()));
                }
            }
        }
        if(order.insertSQL()){
            order.print();
        }

        order = new Order(user);
        resetOrderPanel();
    }

    private void resetOrderPanel() {
        saleComboBox.setSelectedIndex(0);
        usePointsCheckBox.setSelected(false);
        useDTL.setText("0");
        reCalculateTotalCost();
        cusPanel.setVisible(false);
    }

    private void reCalculateTotalCost() {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        totalCost.setText(nf.format(order.getTotalCost()));
        saleComboBox.getSelectedIndex();
        totalCost2.setText(nf.format(order.getTotal()));
    }

    public ImageIcon getInstanceIcon(String iconPath, int size) {
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(Sell.class.getResource(iconPath)));
        icon = new ImageIcon(
                icon.getImage().getScaledInstance(size, size,
                        Image.SCALE_SMOOTH));
        return icon;
    }
}
