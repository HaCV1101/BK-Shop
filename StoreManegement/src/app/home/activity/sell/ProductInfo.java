package app.home.activity.sell;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import app.home.activity.dialogEvent.ButtonActionListener;
import model.Product;

public class ProductInfo extends JDialog {

  private JPanel contentPane;
  private JButton buttonOK;
  private JButton buttonCancel;
  private JButton deleteButton;
  private JComboBox comboBox1;
  private JLabel pName;
  private JLabel pID;
  private JLabel pCost;
  private JLabel pQuantity;
  private JLabel pInfo;
  private Product p;
  private int oQuantity;
  private boolean mode;//true: choose ---- false: custom

  private ButtonActionListener action;

  public void setAction(ButtonActionListener action) {
    this.action = action;
  }

  public ProductInfo(ButtonActionListener action, Product p, int quantity, boolean mode) {
    this.p = p;
    this.action = action;
    setContentPane(contentPane);
    setModal(true);
    pack();
    getRootPane().setDefaultButton(buttonOK);
    setLocationRelativeTo(null);
    setTitle("Thông tin sản phẩm");
    this.mode = mode;
    if (mode) {
      deleteButton.setVisible(false);
    }
    buttonOK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        onOK();
      }
    });

    buttonCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        onCancel();
      }
    });
    deleteButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.out.println(action);
        onDelete();
      }
    });
    // call onCancel() when cross is clicked
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        onCancel();
      }
    });

    // call onCancel() on ESCAPE
    contentPane.registerKeyboardAction(new ActionListener() {
                                         public void actionPerformed(ActionEvent e) {
                                           onCancel();
                                         }
                                       }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
        JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    pID.setText(p.getProductID());
    pName.setText(p.getProductName());
    pCost.setText(p.getCost() + " Đồng");
    pQuantity.setText(p.getQuantity() + "");
    pInfo.setText(p.getProductDetail());
    for (int i = 1; i <= p.getQuantity(); i++) {
      comboBox1.addItem(i);
    }
    comboBox1.setSelectedIndex(quantity - 1);
    setVisible(true);
  }

  private void onDelete() {
    action.actionPerformed(-1, p, comboBox1.getSelectedIndex() + 1);
    dispose();
  }

  private void onOK() {
    // add your code here
    action.actionPerformed(mode ? 1 : 0, p, comboBox1.getSelectedIndex() + 1);
    dispose();
  }

  private void onCancel() {
    // add your code here if necessary
    dispose();
  }

//  public static void main(String[] args) {
//    ProductInfo dialog = new ProductInfo(true);
//    System.exit(0);
//  }
}
