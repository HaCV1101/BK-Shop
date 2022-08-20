package app.home;

import app.Login;
import app.home.activity.Activity;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Home extends JFrame {

  private JPanel mainPanel;
  private JPanel activityPanel;
  private JFrame parent;

  public Home(String title, JFrame parent) throws HeadlessException {
    super(title);
    this.parent = parent;
    setContentPane(mainPanel);
    customControl();
    addAllListener();
//    setResizable(false);
  }

  private void addAllListener() {
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        super.windowClosing(e);
        dispose();
        parent.setVisible(true);
      }
    });
  }

  private void customControl() {
    mainPanel.setBackground(new Color(255, 255, 255));
    setSize(new Dimension(2400, 1500));
    setMinimumSize(new Dimension(800, 700));
    setMaximumSize(new Dimension(1200, 960));
    setLocationRelativeTo(null);
    setVisible(true);
    Activity activity = new Activity(((Login) parent).getUser());
    activityPanel.add(activity, BorderLayout.CENTER);
  }
}
