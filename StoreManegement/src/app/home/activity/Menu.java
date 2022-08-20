package app.home.activity;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class Menu extends JPanel {

    private MenuSelectedEvent event;
    private List<MenuOption> menuOptions = new ArrayList<>();

    public Menu() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setVisible(true);

        init();
        addAllListener();
    }

    private void init() {
        MenuOption.setCountIndex(0);
        menuOptions.add((MenuOption) add(new MenuOption("Menu", "/icon/icons8-menu-60.png")));
        menuOptions.add((MenuOption) add(new MenuOption("Trang chủ", "/icon/icons8-home-page-60.png")));
    }

    public void addMenuOption(MenuOption option) {
        menuOptions.add((MenuOption) add(option));
    }

    public void addMenuOption(Component component) {
        add(component);
    }

    private void addAllListener() {
    }

    @Override
    protected void paintChildren(Graphics g) {
        super.paintChildren(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gp = new GradientPaint(0, 0, Color.decode("#1CB5E0"), 0, getHeight(),
                Color.decode("#0078AA"));
        g2.setPaint(gp);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        g2.fillRect(getWidth() - 20, 0, getWidth(), getHeight());
        super.paintChildren(g);
    }

    public void addMenuSelectedEvent(MenuSelectedEvent event) {
        this.event = event;
        for (int i = 1; i < menuOptions.size(); i++) {
            MenuOption mo = menuOptions.get(i);
            mo.addMenuSelectedEvent(event);
        }
    }

    public interface MenuSelectedEvent {
        void selected(int i);
    }
}

