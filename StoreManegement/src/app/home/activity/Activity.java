package app.home.activity;

import app.home.activity.Menu.MenuSelectedEvent;
import app.home.activity.accountmanagement.AccountManagement;
import app.home.activity.cardregister.CardRegister;
import app.home.activity.productmanagement.ProductManagement;
import app.home.activity.sell.Sell;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import javax.swing.*;

import app.home.activity.statistics.Statistic;
import app.home.activity.usermanagement.UserManagement;
import model.Employee;
import model.Manager;
import model.User;

public class Activity extends JPanel {

    private JPanel activityPanel;
    private JPanel menuPanel;
    private JPanel workingPanel;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private User user;

    public Activity(User user) {
        super();
        this.user = user;
        customControl();
        setVisible(true);
    }

    private void customControl() {
        setSize(new Dimension(2400, 1400));
        activityPanel.setBackground(new Color(255, 255, 255));
        setMinimumSize(new Dimension(800, 700));
        setMaximumSize(new Dimension(1200, 960));
        add(activityPanel);
        Menu menu = new Menu();

        JPanel option1 = new JPanel(new BorderLayout());
        JLabel homePage = new JLabel();
        homePage.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon icon = new ImageIcon(
                Objects.requireNonNull(MenuOption.class.getResource("/icon/Logo.png")));
        icon = new ImageIcon(
                icon.getImage().getScaledInstance(1000, 1000,
                        Image.SCALE_SMOOTH));
        homePage.setIcon(icon);
        homePage.setPreferredSize(new Dimension(2050, 1400));
        option1.add(homePage, BorderLayout.CENTER);
        workingPanel.add(option1);

        JLabel helloLabel = new JLabel("Xin chào " + user.getUserName());
        helloLabel.setAlignmentX(0.5f);
        helloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        helloLabel.setMaximumSize(new Dimension(300, 100));
        helloLabel.setFont(new Font("sanserif", Font.ITALIC, 20));
        helloLabel.setForeground(new Color(197, 61, 61, 200));

        JLabel time = new JLabel();
        time.setAlignmentX(0.5f);
        time.setHorizontalAlignment(SwingConstants.CENTER);
        time.setMaximumSize(new Dimension(300, 100));
        time.setFont(new Font("sanserif", Font.BOLD | Font.ITALIC, 30));
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Calendar calendar = Calendar.getInstance();
                Date t = calendar.getTime();
                SimpleDateFormat dfm = new SimpleDateFormat("HH:mm:ss");
                time.setText(dfm.format(t));
            }
        });
        timer.start();
        if (user instanceof Employee) {
            menu.addMenuOption(new MenuOption("Bán hàng", "/icon/icons8-sell-64.png"));
            menu.addMenuOption(new MenuOption("Đăng ký thẻ tích điểm", "/icon/icons8-sign-up-64.png"));
            menu.addMenuOption(helloLabel);
            menu.addMenuOption(time);
            menu.addMenuOption(new MenuOption("Quản lý tài khoản", "/icon/icons8-male-user-50.png"));
            JPanel option2 = new JPanel(new BorderLayout());
            option2.add(new Sell(user), BorderLayout.CENTER);
            workingPanel.add(option2);
            JPanel option3 = new JPanel(new BorderLayout());
            option3.add(new CardRegister(), BorderLayout.CENTER);
            workingPanel.add(option3);
            JPanel option4 = new JPanel(new BorderLayout());
            option4.add(new AccountManagement(user), BorderLayout.CENTER);
            workingPanel.add(option4);
        } else {
            menu.addMenuOption(new MenuOption("Quản lý sản phẩm", "/icon/icons8-product-64.png"));
            menu.addMenuOption(new MenuOption("Quản lý người dùng", "/icon/icons8-conference-50.png"));
            menu.addMenuOption(new MenuOption("Thống kê", "/icon/icons8-statistics-58.png"));
            menu.addMenuOption(helloLabel);
            menu.addMenuOption(time);
            menu.addMenuOption(new MenuOption("Quản lý tài khoản", "/icon/icons8-male-user-50.png"));
            JPanel option4 = new JPanel(new BorderLayout());
            option4.add(new ProductManagement(), BorderLayout.CENTER);
            workingPanel.add(option4);
            JPanel option5 = new JPanel(new BorderLayout());
            option5.add(new UserManagement(), BorderLayout.CENTER);
            workingPanel.add(option5);
            JPanel option6 = new JPanel(new BorderLayout());
            option6.add(new Statistic(), BorderLayout.CENTER);
            workingPanel.add(option6);
            JPanel option7 = new JPanel(new BorderLayout());
            option7.add(new AccountManagement(user), BorderLayout.CENTER);
            workingPanel.add(option7);
        }
        menu.addMenuSelectedEvent(new MenuSelectedEvent() {
            @Override
            public void selected(int i) {
                System.out.println(i);
                CardLayout cardLayout = (CardLayout) workingPanel.getLayout();
                cardLayout.first(workingPanel);
                for (int j = 0; j < i - 1; j++) {
                    cardLayout.next(workingPanel);
                }
            }
        });
        menuPanel.add(menu, BorderLayout.CENTER);

        CardLayout layout = (CardLayout) workingPanel.getLayout();
        layout.first(workingPanel);
    }
}
