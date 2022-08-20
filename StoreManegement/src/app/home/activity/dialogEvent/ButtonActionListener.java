package app.home.activity.dialogEvent;

import model.Product;

public interface ButtonActionListener {
    void actionPerformed(int choose, Product product, int quantity);//

    void actionPerformed(int choose, Product product, boolean mode);//

}
