package app.home.activity.cardregister;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import model.Customer;
import swing.button.Button;
import swing.textfield.TextField;

public class CardRegister extends JPanel {

  private JPanel mainPanel;
  private JPanel textForm;

  public CardRegister() {
    add(mainPanel);
    setPreferredSize(new Dimension(2050, 1500));
    form();
    addListener();
  }

  private void addListener() {
    ActionListener enter = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        OnConfirmButton();
      }
    };
    customerName.addActionListener(enter);
    customerNumber.addActionListener(enter);
    confirmButton.addActionListener(enter);
  }

  private void OnConfirmButton() {
    Customer customer = new Customer();
    customer.autoIncreaseID();
    if (customerName.getText().equals("") || customerNumber.getText().equals("")) {
      JOptionPane.showMessageDialog(this, "Tên khách hàng hoặc số điện thoại không được để trống",
          "Error",
          JOptionPane.ERROR_MESSAGE);
    } else {
      customer.setCustomerName(customerName.getText());
      if (!customer.setPhoneNumber(customerNumber.getText())) {
        JOptionPane.showMessageDialog(this, "Số điện thoại phải có 10 chữ số và bắt đầu bằng 0",
            "Error",
            JOptionPane.ERROR_MESSAGE);
      } else {
        customerName.setText("");
        customerNumber.setText("");
        customer.setPoints(0);
        if (customer.insertSQL()) {
          JOptionPane.showMessageDialog(this, "Tạo khách hàng thành viên mới thành công", "Success",
              JOptionPane.INFORMATION_MESSAGE);
        }
      }
    }
  }

  private void form() {
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.insets = new Insets(0, 0, 10, 0);
    customerName.setPreferredSize(new Dimension(420, 60));
    customerName.setLabelText("Full Name");
    textForm.add(customerName, gbc);
    gbc.gridx = 0;
    gbc.gridy = 1;
    customerNumber.setPreferredSize(new Dimension(420, 60));
    customerNumber.setLabelText("Phone Number");
    textForm.add(customerNumber, gbc);
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.insets = new Insets(80, 0, 0, 0);
    confirmButton.setPreferredSize(new Dimension(150, 50));
    confirmButton.setFont(new Font("SanSerif", Font.BOLD, 20));
    confirmButton.setRadius(20);
    confirmButton.setFocusable(false);
    confirmButton.setText("Resister");
    confirmButton.setColor(new Color(76, 204, 255, 100));
    confirmButton.setColorClick(new Color(76, 204, 255));
    confirmButton.setBorderColor(new Color(7, 93, 192));
    confirmButton.setColorOver(new Color(76, 204, 255, 174));
    textForm.add(confirmButton, gbc);
  }

//  public static void main(String[] args) {
//    JFrame frame = new JFrame();
//    frame.setSize(new Dimension(2050, 1500));
//    frame.setContentPane(new CardRegister());
//    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//    frame.setLocationRelativeTo(null);
//    frame.setVisible(true);
//  }

  private final TextField customerName = new TextField();
  private final TextField customerNumber = new TextField();
  private final Button confirmButton = new Button();
}
