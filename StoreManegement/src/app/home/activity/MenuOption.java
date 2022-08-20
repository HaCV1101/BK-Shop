package app.home.activity;

import app.home.activity.Menu.MenuSelectedEvent;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuOption extends JPanel {

  private JPanel panel1;
  private JLabel icon;
  private JLabel text;
  private static int countIndex = 0;
  private int index;
  private MenuSelectedEvent event;

  public static void setCountIndex(int countIndex) {
    MenuOption.countIndex = countIndex;
  }

  public MenuOption() {
    super();
  }

  public MenuOption(String text, String iconPath) {
    this();
    setOpaque(false);
    setMaximumSize(new Dimension(350, 100));
    setIcon(iconPath);
    setText(text);
    add(panel1);
    panel1.setOpaque(false);

    index = countIndex++;
    if (index != 0) {
      setCursor(new Cursor(Cursor.HAND_CURSOR));
      addMouseListener(new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
          event.selected(index);
          panel1.setOpaque(true);
          panel1.setBackground(new Color(255, 255, 255, 80));
        }

        @Override
        public void mouseReleased(MouseEvent e) {
          super.mouseReleased(e);
          panel1.setOpaque(false);
          panel1.setBackground(new Color(255, 255, 255, 0));
        }

        @Override
        public void mouseEntered(MouseEvent e) {
          super.mouseEntered(e);
          panel1.setOpaque(true);
          panel1.setBackground(new Color(255, 255, 255, 40));
        }

        @Override
        public void mouseExited(MouseEvent e) {
          super.mouseExited(e);
          panel1.setOpaque(false);
          panel1.setBackground(new Color(255, 255, 255, 0));
        }
      });
    }
  }

  public void setText(String text) {
    this.text.setText(text);
    this.text.setForeground(Color.decode("#65686D"));
    this.text.setFont(new Font("sansserif", Font.BOLD, 19));
    this.text.setSize(new Dimension(250, 100));
    this.text.setOpaque(false);
  }

  public void setIcon(String iconPath) {
    ImageIcon icon = new ImageIcon(
        Objects.requireNonNull(MenuOption.class.getResource(iconPath)));
    icon = new ImageIcon(
        icon.getImage().getScaledInstance(60, 60,
            Image.SCALE_SMOOTH));
    this.icon.setIcon(icon);
    this.icon.setOpaque(false);
  }

  public void addMenuSelectedEvent(MenuSelectedEvent event) {
    this.event = event;
  }
}
