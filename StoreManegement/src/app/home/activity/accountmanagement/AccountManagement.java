package app.home.activity.accountmanagement;

import app.home.activity.statistics.Statistic;
import model.User;
import swing.button.Button;
import swing.combobox.Combobox;
import swing.textfield.PasswordField;
import swing.textfield.TextField;
import swing.textfield_suggestion.TextFieldSuggestion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.Objects;

public class AccountManagement extends JPanel {
    private JPanel mainPanel;
    private JPanel btnPanel;
    private JPanel showPanel;
    private JPanel changePassPanel;
    private JPanel changeInfoPanel;

    public AccountManagement(User user) {
        this.user = user;
        add(mainPanel);
        customs();
        addAllListener();
    }

    private void addAllListener() {
        changePass.addActionListener(this::showChangePass);
        changeInfo.addActionListener(this::showChangeInfo);
        pass1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!pass1.getText().equals(user.getPassword()) && pass1.getText().length() != 0) {
                    mesP1.setText("Sai mật khẩu");
                    mesP1.setVisible(true);
                    okChangePass.setEnabled(false);
                } else {
                    mesP1.setVisible(false);
                    if (!mesP3.isVisible() && pass3.getText().length() != 0) {
                        okChangePass.setEnabled(true);
                    }
                }
            }
        });
        pass3.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!pass3.getText().equals(pass2.getText())) {
                    mesP3.setText("Mật khẩu mới không khớp");
                    mesP3.setVisible(true);
                    okChangePass.setEnabled(false);
                } else {
                    mesP3.setVisible(false);
                    if (!mesP1.isVisible() && pass1.getText().length() != 0) {
                        okChangePass.setEnabled(true);
                    }
                }
            }
        });
        okChangePass.addActionListener(this::onOKChangePass);
        okChangeInfo.addActionListener(this::onOKChangeInfo);
    }

    private void onOKChangeInfo(ActionEvent actionEvent) {
        user.setUserName(name.getText());
        user.setPhoneNumber(phone.getText());
        user.setAddress(adr.getText());
        user.updateSQL();
        JOptionPane.showMessageDialog(this, "Cập nhật thông tin thành công");
    }

    private void onOKChangePass(ActionEvent actionEvent) {
        user.setPassword(pass2.getText());
        user.updateSQL();
        JOptionPane.showMessageDialog(this, "Thay đổi mật khẩu thành công");
        pass1.setText("");
        pass2.setText("");
        pass3.setText("");
    }

    private void showChangeInfo(ActionEvent actionEvent) {
        CardLayout layout = (CardLayout) showPanel.getLayout();
        layout.first(showPanel);
        layout.next(showPanel);
    }

    private void showChangePass(ActionEvent actionEvent) {
        CardLayout layout = (CardLayout) showPanel.getLayout();
        layout.first(showPanel);
    }

    private void customs() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        changePass.setText("Thay đổi mật khẩu             ");
        changePass.setIcon(getInstanceIcon("/icon/password.png", 60));
        changePass.setColor(new Color(76, 204, 255, 100));
        changePass.setColorClick(new Color(76, 204, 255));
        changePass.setBorderColor(new Color(7, 93, 192));
        changePass.setColorOver(new Color(76, 204, 255, 174));
        changePass.setRadius(50);
        changePass.setIconTextGap(10);
        changePass.setFont(new Font("SansSerif", Font.BOLD, 25));
        changePass.setPreferredSize(new Dimension(470, 100));
        btnPanel.add(changePass, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 0);
        changeInfo.setText("Thay đổi thông tin cá nhân");
        changeInfo.setIcon(getInstanceIcon("/icon/info.png", 60));
        changeInfo.setIconTextGap(10);
        changeInfo.setColor(new Color(76, 204, 255, 100));
        changeInfo.setColorClick(new Color(76, 204, 255));
        changeInfo.setBorderColor(new Color(7, 93, 192));
        changeInfo.setColorOver(new Color(76, 204, 255, 174));
        changeInfo.setRadius(50);
        changeInfo.setFont(new Font("SansSerif", Font.BOLD, 25));
        changeInfo.setPreferredSize(new Dimension(470, 100));
        btnPanel.add(changeInfo, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        achievements.setText("Thành tích");
        achievements.setFont(new Font("SansSerif", Font.BOLD, 25));
        achievements.setPreferredSize(new Dimension(400, 80));
//        btnPanel.add(achievements, gbc);
        //
        Dimension dms = new Dimension(450, 65);
        Font font = new Font("SansSerif", Font.PLAIN, 15);
        pass1.setShowAndHide(true);
        pass1.setLabelText("Mật khẩu cũ");
        pass1.setPreferredSize(dms);
        pass1.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        changePassPanel.add(pass1, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 0);
        mesP1.setForeground(Color.RED);
        mesP1.setVisible(false);
        changePassPanel.add(mesP1, gbc);
        pass2.setShowAndHide(true);
        pass2.setLabelText("Nhập mật khẩu mới");
        pass2.setPreferredSize(dms);
        pass2.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        changePassPanel.add(pass2, gbc);
        pass3.setShowAndHide(true);
        pass3.setLabelText("Nhập lại mật khẩu mới");
        pass3.setPreferredSize(dms);
        pass3.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 20, 0);
        changePassPanel.add(pass3, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 20, 0);
        mesP3.setForeground(Color.RED);
        mesP3.setVisible(false);
        changePassPanel.add(mesP3, gbc);
        okChangePass.setText("OK");
        okChangePass.setColor(new Color(76, 204, 255, 100));
        okChangePass.setColorClick(new Color(76, 204, 255));
        okChangePass.setBorderColor(new Color(7, 93, 192));
        okChangePass.setColorOver(new Color(76, 204, 255, 174));
        okChangePass.setRadius(30);
        okChangePass.setPreferredSize(new Dimension(200, 50));
        okChangePass.setFont(new Font("SansSerif", Font.BOLD, 20));
        okChangePass.setEnabled(false);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(40, 0, 0, 0);
        changePassPanel.add(okChangePass, gbc);
        //
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        name.setLabelText("Tên người dùng");
        name.setText(user.getUserName());
        name.setPreferredSize(dms);
        name.setFont(font);
        changeInfoPanel.add(name, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 0);
        phone.setLabelText("Số điện thoại");
        phone.setText(user.getPhoneNumber());
        phone.setPreferredSize(dms);
        phone.setFont(font);
        changeInfoPanel.add(phone, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        adr.setLabelText("Địa chỉ");
        adr.setText(user.getAddress());
        adr.setPreferredSize(dms);
        adr.setFont(font);
        changeInfoPanel.add(adr, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(40, 0, 0, 0);
        okChangeInfo.setText("OK");
        okChangeInfo.setPreferredSize(new Dimension(200, 50));
        okChangeInfo.setFont(new Font("SansSerif", Font.BOLD, 20));
        okChangeInfo.setColor(new Color(76, 204, 255, 100));
        okChangeInfo.setColorClick(new Color(76, 204, 255));
        okChangeInfo.setBorderColor(new Color(7, 93, 192));
        okChangeInfo.setColorOver(new Color(76, 204, 255, 174));
        okChangeInfo.setRadius(30);
        changeInfoPanel.add(okChangeInfo, gbc);

        CardLayout layout = (CardLayout) showPanel.getLayout();
        layout.last(showPanel);
    }

    public ImageIcon getInstanceIcon(String iconPath, int size) {
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(AccountManagement.class.getResource(iconPath)));
        icon = new ImageIcon(
                icon.getImage().getScaledInstance(size, size,
                        Image.SCALE_SMOOTH));
        return icon;
    }

    private User user;
    private final Button changePass = new Button();
    private final Button changeInfo = new Button();
    private final Button achievements = new Button();
    private final PasswordField pass1 = new PasswordField();
    private final PasswordField pass2 = new PasswordField();
    private final PasswordField pass3 = new PasswordField();
    private final Button okChangePass = new Button();
    private final TextField name = new TextField();
    private final TextField phone = new TextField();
    private final TextField adr = new TextField();
    private final Button okChangeInfo = new Button();
    private JLabel mesP1 = new JLabel();
    private JLabel mesP3 = new JLabel();


}
