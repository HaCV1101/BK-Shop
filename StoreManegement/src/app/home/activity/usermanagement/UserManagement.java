package app.home.activity.usermanagement;

import app.home.activity.productmanagement.ProductManagement;
import model.Employee;
import model.Manager;
import model.Product;
import model.User;
import sql.Database;
import swing.button.Button;
import swing.combobox.Combobox;
import swing.textfield_suggestion.TextFieldSuggestion;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class UserManagement extends JPanel {

    private JPanel mainPanel;
    private JPanel btnPanel1;
    private JPanel btnPanel2;
    private JPanel namePanel;
    private JPanel dateOfBirthPanel;
    private JTable userTable;
    private JPanel logNamePanel;
    private JPanel passPanel;
    private JPanel phonePanel;
    private JPanel adrPanel;
    private JLabel idLabel;
    private final Button okButton = new Button();
    private final Button deleteButton = new Button();
    private final Button addButton = new Button();
    private final Button addManButton = new Button();
    private final Button addEmpButton = new Button();
    private final TextFieldSuggestion nameTextField = new TextFieldSuggestion();
    private final TextFieldSuggestion accountNameTextField = new TextFieldSuggestion();
    private final TextFieldSuggestion passTextField = new TextFieldSuggestion();
    private final TextFieldSuggestion phoneNumTextField = new TextFieldSuggestion();
    private final TextFieldSuggestion adrTextField = new TextFieldSuggestion();

    private final Combobox<Integer> dayCombobox = new Combobox<>();
    private final Combobox<Integer> monthCombobox = new Combobox<>();
    private final Combobox<Integer> yearCombobox = new Combobox<>();

    public UserManagement() {
        add(mainPanel);
        customs();
    }

    private void customs() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        okButton.setText("OK");
        okButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        okButton.setPreferredSize(new Dimension(150, 50));
        okButton.setColor(new Color(76, 204, 255, 100));
        okButton.setColorClick(new Color(76, 204, 255));
        okButton.setBorderColor(new Color(0, 120, 170));
        okButton.setColorOver(new Color(76, 204, 255, 174));
        okButton.setRadius(30);
        okButton.addActionListener(this::onOK);
        btnPanel1.add(okButton, gbc);
        okButton.setVisible(false);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 20, 0, 0);
        deleteButton.setText("Delete");
        deleteButton.setPreferredSize(new Dimension(150, 50));
        deleteButton.setVisible(false);
        deleteButton.setColor(new Color(246, 246, 246, 255));
        deleteButton.setColorClick(new Color(58, 180, 242));
        deleteButton.setBorderColor(new Color(0, 120, 170));
        deleteButton.setColorOver(new Color(76, 204, 255, 174));
        deleteButton.setRadius(30);
        deleteButton.addActionListener(this::onDelete);
        btnPanel1.add(deleteButton, gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 20, 0, 0);
        addButton.setText("Add");
        addButton.setPreferredSize(new Dimension(150, 50));
        addButton.setColor(new Color(76, 204, 255, 100));
        addButton.setColorClick(new Color(76, 204, 255));
        addButton.setBorderColor(new Color(0, 120, 170));
        addButton.setColorOver(new Color(76, 204, 255, 174));
        addButton.setRadius(30);
        addButton.addActionListener(this::onAdd);
        btnPanel1.add(addButton, gbc);
        addButton.setVisible(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        addManButton.setText("Thêm quản lý    ");
        addManButton.setIcon(getInstanceIcon("/icon/manager.png", 60));
        addManButton.setColor(new Color(76, 204, 255,100));
        addManButton.setColorClick(new Color(76, 204, 255));
        addManButton.setBorderColor(new Color(7, 93, 192));
        addManButton.setColorOver(new Color(76, 204, 255, 174));
        addManButton.setRadius(50);
        addManButton.setFont(new Font("SansSerif", Font.BOLD, 30));
        addManButton.setPreferredSize(new Dimension(450, 100));
        addManButton.addActionListener(this::onAddUser);
        btnPanel2.add(addManButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(20, 0, 0, 0);
        addEmpButton.setText("Thêm nhân viên");
        addEmpButton.setIcon(getInstanceIcon("/icon/employee.png", 60));
        addEmpButton.setColor(new Color(76, 204, 255,100));
        addEmpButton.setColorClick(new Color(76, 204, 255));
        addEmpButton.setBorderColor(new Color(7, 93, 192));
        addEmpButton.setColorOver(new Color(76, 204, 255, 174));
        addEmpButton.setRadius(50);
        addEmpButton.setFont(new Font("SansSerif", Font.BOLD, 30));
        addEmpButton.setPreferredSize(new Dimension(450, 100));
        addEmpButton.addActionListener(this::onAddUser);
        btnPanel2.add(addEmpButton, gbc);

        nameTextField.setPreferredSize(new Dimension(300, 50));
        nameTextField.setOpaque(true);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        namePanel.add(nameTextField, gbc);

        accountNameTextField.setPreferredSize(new Dimension(300, 50));
        accountNameTextField.setOpaque(true);
        gbc.gridx = 1;
        gbc.gridy = 0;
        logNamePanel.add(accountNameTextField, gbc);

        passTextField.setPreferredSize(new Dimension(300, 50));
        passTextField.setOpaque(true);
        gbc.gridx = 1;
        gbc.gridy = 0;
        passPanel.add(passTextField, gbc);

        phoneNumTextField.setPreferredSize(new Dimension(300, 50));
        phoneNumTextField.setOpaque(true);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 250);
        phonePanel.add(phoneNumTextField, gbc);

        adrTextField.setPreferredSize(new Dimension(300, 50));
        adrTextField.setOpaque(true);
        gbc.gridx = 1;
        gbc.gridy = 0;
        adrPanel.add(adrTextField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 20, 0, 0);
        DefaultComboBoxModel<Integer> dcm = new DefaultComboBoxModel<>();
        for (int i = 1900; i <= 2010; i++) {
            dcm.addElement(i);
        }
        yearCombobox.setModel(dcm);
        yearCombobox.setPreferredSize(new Dimension(180, 60));
        yearCombobox.setSelectedIndex(-1);
        yearCombobox.setLabeText("Year");
        dateOfBirthPanel.add(yearCombobox, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 20, 0, 0);
        DefaultComboBoxModel<Integer> dcm1 = new DefaultComboBoxModel<>();
        for (int i = 1; i <= 12; i++) {
            dcm1.addElement(i);
        }
        monthCombobox.setModel(dcm1);
        monthCombobox.setPreferredSize(new Dimension(180, 60));
        monthCombobox.setSelectedIndex(-1);
        monthCombobox.setLabeText("Month");
        dateOfBirthPanel.add(monthCombobox, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 20, 0, 0);
        DefaultComboBoxModel<Integer> dcm2 = new DefaultComboBoxModel<>();
        for (int i = 1; i <= 31; i++) {
            dcm2.addElement(i);
        }
        dayCombobox.setModel(dcm2);
        dayCombobox.setPreferredSize(new Dimension(180, 60));
        dayCombobox.setSelectedIndex(-1);
        dayCombobox.setLabeText("Day");
        dateOfBirthPanel.add(dayCombobox, gbc);

        String[] col = {"STT", "ID", "Tên người dùng", "Tên đăng nhập", "Mật khẩu", "Ngày sinh", "Số điện thoại", "Địa chỉ"};
        DefaultTableModel dmt = new DefaultTableModel(null, col);
        userTable.setModel(dmt);
        userTable.getSelectionModel().addListSelectionListener(this::onSelectRow);
        Database db = new Database();
        if (db.executeQuery("SELECT IDNguoiDung FROM NguoiDung")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            while (db.resulSetNext()) {
                User user = db.getResulString("IDNguoiDung").contains("QL") ? new Manager() : new Employee();
                user.querySQL("SELECT * FROM NguoiDung where IDNguoiDung = '" + db.getResulString("IDNguoiDung") + "'");
                dmt.addRow(new Object[]{userTable.getRowCount() + 1, user, user.getUserName(),
                        user.getAccountName(), user.getPassword(), sdf.format(user.getDateOfBirth()),
                        user.getPhoneNumber(), user.getAddress()});
            }
        }

    }

    private void onDelete(ActionEvent actionEvent) {
        int selected = userTable.getSelectedRow();
        User user = (User) userTable.getModel().getValueAt(selected, 1);
        user.deleteSQL();
        ((DefaultTableModel) userTable.getModel()).removeRow(selected);
        System.out.println("for " + selected + " to " + userTable.getRowCount());
        for (int i = selected; i < userTable.getRowCount(); i++) {
            userTable.getModel().setValueAt(i + 1, i, 0);
        }
        updateProductInfoPanel(new Manager());
        deleteButton.setVisible(false);
        okButton.setVisible(false);
    }

    private void onAdd(ActionEvent actionEvent) {
        if (nameTextField.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Tên người dùng không được để trống");
        } else if (accountNameTextField.getText().equals("") || passTextField.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Tài khoản mật khẩu không được để trống");
        } else {
            User user;
            if (idLabel.getText().contains("QL")) {
                user = new Manager();
            } else {
                user = new Employee();
            }
            user.setUserID(idLabel.getText());
            user.setUserName(nameTextField.getText());
            user.setAccountName(accountNameTextField.getText());
            user.setPassword(passTextField.getText());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                user.setDateOfBirth(sdf.parse(yearCombobox.getSelectedItem() + "-" + monthCombobox.getSelectedItem() + "-" + dayCombobox.getSelectedItem()));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            user.setPhoneNumber(phoneNumTextField.getText());
            user.setAddress(adrTextField.getText());
            ((DefaultTableModel) userTable.getModel()).addRow(new Object[]{userTable.getRowCount() + 1, user, user.getUserName(),
                    user.getAccountName(), user.getPassword(), sdf.format(user.getDateOfBirth()),
                    user.getPhoneNumber(), user.getAddress()});
            user.insertSQL();
            JOptionPane.showMessageDialog(this, "Thêm người dùng thành công");
            addButton.setVisible(false);
        }
    }

    private void onAddUser(ActionEvent actionEvent) {
        okButton.setVisible(false);
        deleteButton.setVisible(false);
        addButton.setVisible(true);
        nameTextField.setEnabled(true);
        User user;
        if (actionEvent.getSource().equals(addEmpButton)) {
            user = new Employee();
        } else {
            user = new Manager();
        }
        user.autoIncreaseID();
        updateProductInfoPanel(user);
    }


    private void onOK(ActionEvent actionEvent) {
        if (nameTextField.getText().equals("") || accountNameTextField.getText().equals("") || passTextField.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Tên người dùng, tên đăng nhập và mật khẩu không thể để trống");
        } else {
            int selected = userTable.getSelectedRow();
            User user = (User) userTable.getModel().getValueAt(selected, 1);
            user.setUserID(idLabel.getText());
            user.setUserName(nameTextField.getText());
            user.setAccountName(accountNameTextField.getText());
            user.setPassword(passTextField.getText());
            user.setPhoneNumber(phoneNumTextField.getText());
            user.setAddress(adrTextField.getText());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                user.setDateOfBirth(sdf.parse(yearCombobox.getSelectedItem() + "-" + monthCombobox.getSelectedItem() + "-" + dayCombobox.getSelectedItem()));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            user.updateSQL();
            sdf = new SimpleDateFormat("yyyy/MM/dd");
            userTable.getModel().setValueAt(user.getAccountName(), selected, 3);
            userTable.getModel().setValueAt(user.getPassword(), selected, 4);
            userTable.getModel().setValueAt(sdf.format(user.getDateOfBirth()), selected, 5);
            userTable.getModel().setValueAt(user.getPhoneNumber(), selected, 6);
            userTable.getModel().setValueAt(user.getAddress(), selected, 7);
            JOptionPane.showMessageDialog(this, "Cập nhật thông tin người dùng thành công");
            okButton.setVisible(false);
            deleteButton.setVisible(false);
        }
    }

    private void onSelectRow(ListSelectionEvent event) {
        if (event.getValueIsAdjusting()) {
            okButton.setVisible(true);
            deleteButton.setVisible(true);
            addButton.setVisible(false);
            nameTextField.setEnabled(false);
            int selected = userTable.getSelectedRow();
            User user = (User) userTable.getModel().getValueAt(selected, 1);
            updateProductInfoPanel(user);
        }
    }

    private void updateProductInfoPanel(User user) {
        idLabel.setText(user.getUserID());
        nameTextField.setText(user.getUserName());
        accountNameTextField.setText(user.getAccountName());
        passTextField.setText(user.getPassword());
        Date date = user.getDateOfBirth();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        int year = Integer.parseInt(sdf.format(date));
        yearCombobox.setSelectedIndex(year >= 1900 ? year - 1900 : -1);
        sdf = new SimpleDateFormat("MM");
        int month = Integer.parseInt(sdf.format(date));
        monthCombobox.setSelectedIndex(month - 1);
        sdf = new SimpleDateFormat("dd");
        int day = Integer.parseInt(sdf.format(date));
        dayCombobox.setSelectedIndex(day - 1);
        phoneNumTextField.setText(user.getPhoneNumber());
        adrTextField.setText(user.getAddress());
    }

    public ImageIcon getInstanceIcon(String iconPath, int size) {
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(UserManagement.class.getResource(iconPath)));
        icon = new ImageIcon(
                icon.getImage().getScaledInstance(size, size,
                        Image.SCALE_SMOOTH));
        return icon;
    }
}
