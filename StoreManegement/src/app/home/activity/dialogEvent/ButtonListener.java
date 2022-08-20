package app.home.activity.dialogEvent;

import model.Product;

import java.awt.event.MouseAdapter;

public abstract class ButtonListener implements ButtonActionListener {
    public void actionPerformed(int choose, Product product, int quantity) {
    }//

    public void actionPerformed(int choose, Product product, boolean mode) {
    }//
}
