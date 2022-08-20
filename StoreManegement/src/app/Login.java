package app;

import app.home.Home;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Objects;
import javax.swing.*;

import app.home.activity.MenuOption;
import model.Employee;
import model.Manager;
import model.User;
import sql.Database;
import swing.button.Button;
import swing.textfield.*;
import swing.checkbox.JCheckBoxCustom;
import swing.textfield.TextField;

public class Login extends JFrame {

    private JPanel mainPanel;
    private JPanel panel1;
    private JLabel loginLable2;
    private JLabel loginLabel;
    private JLabel logo;
    private JPanel loginPanel;
    private JPanel buttonPanel;
    private final Button loginButton = new Button();
    private final Button cancelButton = new Button();

    private TextField userTextField;
    private PasswordField passWordTextField;
    private JCheckBoxCustom rememberCheckBox;
    private boolean isRemember;

    private User user;

    public User getUser() {
        return user;
    }

    public Login(String title) throws HeadlessException {
        setTitle(title);
        setContentPane(mainPanel);
        mainPanel.setBackground(new Color(255, 255, 255));
        customControl();
        getUserRemembered();
        addAllListener();
        pack();
        setMinimumSize(new Dimension(800, 700));
        setMaximumSize(new Dimension(1200, 960));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void getUserRemembered() {
        try {
            FileInputStream fis = new FileInputStream("userRemember.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String user = br.readLine();
            userTextField.setText(user != null ? user : "");
            String pass = br.readLine();
            passWordTextField.setText(pass != null ? pass : "");
            br.close();
            isr.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addAllListener() {
        rememberCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                isRemember = rememberCheckBox.isSelected();
                System.out.println("remember is " + isRemember);
            }
        });
        JFrame parent = this;
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                File f = new File("userRemember.txt");
                if (!f.exists()) {
                    try {
                        f.createNewFile();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                if (checkUser()) {
                    System.out.println("Login");
                    setVisible(false);
                    new Home("Application", parent);
                    if (isRemember) {
                        try {
                            FileOutputStream fos = new FileOutputStream(f);
                            OutputStreamWriter osw = new OutputStreamWriter(fos);
                            BufferedWriter bw = new BufferedWriter(osw);
                            bw.write(userTextField.getText());
                            bw.newLine();
                            bw.write(passWordTextField.getText());
                            bw.newLine();
                            bw.close();
                            osw.close();
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "Username or password is incorrect", "Incorrect information", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        userTextField.addActionListener(loginButton.getActionListeners()[loginButton.getActionListeners().length - 1]);
        passWordTextField.addActionListener(loginButton.getActionListeners()[loginButton.getActionListeners().length - 1]);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
                System.exit(0);
            }
        });
    }

    private boolean checkUser() {
        Database db = new Database();
        db.executeQuery("SELECT MatKhau, IDNguoiDung FROM NguoiDung WHERE TenDangNhap = '" + userTextField.getText() + "'");
        if (db.resulSetNext()) {
            String pass = db.getResulString("MatKhau");
            if (pass.equals(passWordTextField.getText())) {
                String id = db.getResulString("IDNguoiDung");
                if (id.startsWith("QL")) {
                    user = new Manager();
                } else {
                    user = new Employee();
                }
                if (user.querySQL("SELECT * FROM NguoiDung WHERE IDNguoiDung = '" + id + "'")) {
                    System.out.println(user);
                }
                return true;
            }
        } else {
            return false;
        }
        return false;
    }

    private void customControl() {
        userTextField = new TextField();
        userTextField.setLabelText("Username");
        userTextField.setPreferredSize(new Dimension(420, 60));
        passWordTextField = new PasswordField();
        passWordTextField.setLabelText("Password");
        passWordTextField.setShowAndHide(true);
        passWordTextField.setPreferredSize(new Dimension(420, 60));
        loginPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 0);
        loginPanel.add(userTextField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        loginPanel.add(passWordTextField, gbc);
        rememberCheckBox = new JCheckBoxCustom();
        rememberCheckBox.setText("Remember me on this device");
        rememberCheckBox.setPreferredSize(new Dimension(420, 60));
        gbc.gridx = 0;
        gbc.gridy = 3;
        loginPanel.add(rememberCheckBox, gbc);
        loginButton.setText("LOGIN");
        loginButton.setIcon(getInstanceIcon("/icon/enter.png"));
        loginButton.setIconTextGap(10);
        loginButton.setForeground(new Color(0, 0, 0, 255));
        loginButton.setFont(new Font("Press Start 2P", Font.BOLD, 15));
        loginButton.setRadius(30);
        loginButton.setPreferredSize(new Dimension(150, 50));
        loginButton.setColor(new Color(76, 204, 255, 100));
        loginButton.setColorClick(new Color(76, 204, 255));
        loginButton.setBorderColor(new Color(7, 93, 192));
        loginButton.setColorOver(new Color(76, 204, 255, 174));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setFocusable(false);
        buttonPanel.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(loginButton, gbc);
        cancelButton.setPreferredSize(new Dimension(150, 50));
        cancelButton.setColor(new Color(199, 234, 234, 202));
        cancelButton.setColorClick(new Color(224, 227, 238, 228));
        cancelButton.setText("Cancel");
        cancelButton.setIcon(getInstanceIcon("/icon/cancel.png"));
        cancelButton.setRadius(30);
        buttonPanel.setLayout(new GridBagLayout());
        gbc.gridx = 1;
        gbc.insets = new Insets(0, 20, 0, 0);
        buttonPanel.add(cancelButton, gbc);
        buttonPanel.setPreferredSize(new Dimension(1000, 150));
    }

    public ImageIcon getInstanceIcon(String iconPath) {
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(Login.class.getResource(iconPath)));
        icon = new ImageIcon(icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        return icon;
    }
}
