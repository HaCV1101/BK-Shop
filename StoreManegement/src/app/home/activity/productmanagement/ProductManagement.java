package app.home.activity.productmanagement;

import app.home.activity.sell.Sell;
import model.Product;
import sql.Database;
import swing.button.Button;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import swing.textfield_suggestion.TextFieldSuggestion;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductManagement extends JPanel {
    private JPanel mainPanel;
    private JPanel namePanel;
    private JPanel costPanel;
    private JPanel quantityPanel;
    private JPanel detailPanel;
    private JPanel btnPanel1;
    private JPanel btnPanel2;
    private JTable productTable;
    private JPanel searchPanel;
    private JLabel idLabel;
    private final Button okButton = new Button();
    private final Button addButton = new Button();
    private final Button deleteButton = new Button();

    private final Button addProductButton = new Button();

    private final TextFieldSuggestion nameTextField = new TextFieldSuggestion();
    private final TextFieldSuggestion costTextField = new TextFieldSuggestion();
    private final TextFieldSuggestion quantityTextField = new TextFieldSuggestion();
    private final TextFieldSuggestion detailTextField = new TextFieldSuggestion();
    private final TextFieldSuggestion searchTextField = new TextFieldSuggestion();
    private final List<Product> products = new ArrayList<>();


    public ProductManagement() {
        add(mainPanel);
        setPreferredSize(new Dimension(2050, 1500));
        custom();
    }

    private void custom() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 20);
        okButton.setText("OK");
        okButton.setRadius(30);
        okButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        okButton.setPreferredSize(new Dimension(150, 50));
        okButton.setColor(new Color(76, 204, 255,100));
        okButton.setColorClick(new Color(76, 204, 255));
        okButton.setBorderColor(new Color(0, 120, 170));
        okButton.setColorOver(new Color(76, 204, 255, 174));
        okButton.addActionListener(this::onOK);
        btnPanel1.add(okButton, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 20);
        addButton.setText("Add");
        addButton.setRadius(30);
        addButton.setPreferredSize(new Dimension(150, 50));
        addButton.setColor(new Color(76, 204, 255,100));
        addButton.setColorClick(new Color(76, 204, 255));
        addButton.setBorderColor(new Color(0, 120, 170));
        addButton.setColorOver(new Color(76, 204, 255, 174));
        addButton.addActionListener(this::onAdd);
        btnPanel1.add(addButton, gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 20);
        deleteButton.setText("Delete");
        deleteButton.setRadius(30);
        deleteButton.setPreferredSize(new Dimension(150, 50));
        deleteButton.setColor(new Color(246, 246, 246, 255));
        deleteButton.setColorClick(new Color(58, 180, 242));
        deleteButton.setBorderColor(new Color(0, 120, 170));
        deleteButton.setColorOver(new Color(76, 204, 255, 174));
        deleteButton.addActionListener(this::onDelete);
        btnPanel1.add(deleteButton, gbc);
        okButton.setVisible(false);
        deleteButton.setVisible(false);
        addButton.setVisible(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        addProductButton.setText("Thêm sản phẩm mới");
        addProductButton.setFont(new Font("SansSerif", Font.BOLD, 30));
        addProductButton.setPreferredSize(new Dimension(450, 100));
        addProductButton.setColor(new Color(76, 204, 255,100));
        addProductButton.setColorClick(new Color(76, 204, 255));
        addProductButton.setBorderColor(new Color(7, 93, 192));
        addProductButton.setColorOver(new Color(76, 204, 255, 174));
        addProductButton.setRadius(50);
        addProductButton.setIcon(getInstanceIcon("/icon/new.png", 60));
        addProductButton.setIconTextGap(10);
        addProductButton.addActionListener(this::onAddProduct);

        btnPanel2.add(addProductButton, gbc);

        nameTextField.setPreferredSize(new Dimension(300, 50));
        nameTextField.setOpaque(true);
        nameTextField.setRound(25);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        namePanel.add(nameTextField, gbc);

        costTextField.setPreferredSize(new Dimension(300, 50));
        costTextField.setOpaque(true);
        costTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                onKeyReleased(e);
            }
        });
        costTextField.setRound(25);
        gbc.gridx = 1;
        gbc.gridy = 0;
        costPanel.add(costTextField, gbc);

        quantityTextField.setPreferredSize(new Dimension(300, 50));
        quantityTextField.setOpaque(true);
        quantityTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                onKeyReleased(e);
            }
        });
        quantityTextField.setRound(25);
        gbc.gridx = 1;
        gbc.gridy = 0;
        quantityPanel.add(quantityTextField, gbc);

        detailTextField.setPreferredSize(new Dimension(300, 50));
        detailTextField.setOpaque(true);
        detailTextField.setRound(25);
        gbc.gridx = 1;
        gbc.gridy = 0;
        detailPanel.add(detailTextField, gbc);

        searchTextField.setPreferredSize(new Dimension(300, 50));
        searchTextField.setOpaque(true);
        searchTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                searchP();
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 0;
        searchPanel.add(searchTextField, gbc);

        String[] col = {"STT", "Mã sản phẩm", "Tên sản phẩm", "Giá bán", "Số lượng", "Chi tiết sản phẩm"};
        DefaultTableModel dmt = new DefaultTableModel(null, col);
        productTable.setModel(dmt);
        productTable.getSelectionModel().addListSelectionListener(this::onSelectRow);
        Database db = new Database();
        if (db.executeQuery("SELECT IDSanPham FROM SanPham")) {
            while (db.resulSetNext()) {
                Product p = new Product();
                p.querySQL("SELECT * FROM SanPham where IDSanPham = '" + db.getResulString("IDSanPham") + "'");
                products.add(p);
                dmt.addRow(new Object[]{products.size(), p, p.getProductName(), p.getCost(), p.getQuantity(), p.getProductDetail()});
            }
        }
    }

    private void searchP() {
        String key = searchTextField.getText();
        String[] col = {"STT", "Mã sản phẩm", "Tên sản phẩm", "Giá bán", "Số lượng", "Chi tiết sản phẩm"};
        DefaultTableModel dmt = new DefaultTableModel(null, col);
        int count = 0;
        for (Product p : products) {
            if (p.search(key)) {
                count++;
                dmt.addRow(new Object[]{count, p, p.getProductName(), p.getCost(), p.getQuantity(), p.getProductDetail()});
            }
        }
        productTable.setModel(dmt);
    }

    private void onDelete(ActionEvent actionEvent) {
        int selected = productTable.getSelectedRow();
        Product product = (Product) productTable.getModel().getValueAt(selected, 1);
        if (products.remove(product) && product.deleteSQL()) {
            ((DefaultTableModel) productTable.getModel()).removeRow(selected);
            for (int i = selected; i < productTable.getRowCount(); i++) {
                productTable.getModel().setValueAt(i + 1, i, 0);
            }
            updateProductInfoPanel(new Product());
            deleteButton.setVisible(false);
            okButton.setVisible(false);
            JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công");
        }
    }

    private void onAdd(ActionEvent actionEvent) {
        if (nameTextField.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Tên sản phẩm không được để trống");
        } else if (costTextField.getText().equals("0") || quantityTextField.getText().equals("0")) {
            JOptionPane.showMessageDialog(this, "Giá bán hoặc số lượng không thể bằng 0");
        } else {
            Product product = new Product();
            product.setProductID(idLabel.getText());
            product.setProductName(nameTextField.getText());
            product.setProductDetail(detailTextField.getText());
            product.setCost(Integer.parseInt(costTextField.getText()));
            product.setQuantity(Integer.parseInt(quantityTextField.getText()));
            if (product.insertSQL()) {
                products.add(product);
                ((DefaultTableModel) productTable.getModel()).addRow(new Object[]{products.size(), product, product.getProductName(), product.getCost(), product.getQuantity(), product.getProductDetail()});
                addButton.setVisible(false);
                JOptionPane.showMessageDialog(this, "Thêm sản phẩm mới thành công");
            }
        }
    }

    private void onKeyReleased(KeyEvent event) {
        TextFieldSuggestion txt = (TextFieldSuggestion) event.getSource();
        txt.setText(txt.getText().replaceAll("[ -/:-~]", ""));
    }

    private void onOK(ActionEvent actionEvent) {
        if (costTextField.getText().equals("0") || quantityTextField.getText().equals("0")) {
            JOptionPane.showMessageDialog(this, "Giá bán hoặc số lượng không thể bằng 0");
        } else {
            int selected = productTable.getSelectedRow();
            Product product = (Product) productTable.getModel().getValueAt(selected, 1);
            productTable.getSelectionModel().clearSelection();
            product.setCost(Integer.parseInt(costTextField.getText()));
            product.setQuantity(Integer.parseInt(quantityTextField.getText()));
            product.setProductDetail(detailTextField.getText());
            if(product.updateSQL()) {
                productTable.getModel().setValueAt(product.getCost(), selected, 3);
                productTable.getModel().setValueAt(product.getQuantity(), selected, 4);
                productTable.getModel().setValueAt(product.getProductDetail(), selected, 5);
                JOptionPane.showMessageDialog(this, "Cập nhật thông tin sản phẩm thành công");
            }
        }
    }

    private void onSelectRow(ListSelectionEvent event) {
        if (event.getValueIsAdjusting()) {
            okButton.setVisible(true);
            deleteButton.setVisible(true);
            addButton.setVisible(false);
            nameTextField.setEnabled(false);
            int selected = productTable.getSelectedRow();
            Product product = (Product) productTable.getModel().getValueAt(selected, 1);
            updateProductInfoPanel(product);
        }
    }

    private void updateProductInfoPanel(Product product) {
        idLabel.setText(product.getProductID());
        nameTextField.setText(product.getProductName());
        costTextField.setText(product.getCost() + "");
        quantityTextField.setText(product.getQuantity() + "");
        detailTextField.setText(product.getProductDetail());
    }

    private void onAddProduct(ActionEvent e) {
        okButton.setVisible(false);
        deleteButton.setVisible(false);
        addButton.setVisible(true);
        nameTextField.setEnabled(true);
        Product p = new Product();
        p.autoIncreaseID();
        updateProductInfoPanel(p);
    }
    public ImageIcon getInstanceIcon(String iconPath, int size) {
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(ProductManagement.class.getResource(iconPath)));
        icon = new ImageIcon(
                icon.getImage().getScaledInstance(size, size,
                        Image.SCALE_SMOOTH));
        return icon;
    }
}
