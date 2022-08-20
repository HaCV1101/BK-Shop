import app.Login;

import javax.swing.SwingUtilities;

import sql.Database;

public class Application {

    public static void main(String[] args) {
        Database.getConnect();
        SwingUtilities.invokeLater(() -> new Login("Login"));
    }
}
