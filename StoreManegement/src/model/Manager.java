package model;

import sql.Database;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Manager extends User {

    public Manager() {
        super();
        isManager = true;
    }

    @Override
    public void setAvatar() {
        avatar = String.format("%s.png", userID);
    }

    @Override
    public String autoIncreaseID() {
        String maxID = getMaxID();
        int id = Integer.parseInt(maxID.substring(2)) + 1;
        userID = "QL" + id;
        return userID;
    }

    @Override
    public String getMaxID() {
        Database db = new Database();
        if (db.executeQuery(
                "SELECT IDNguoiDung FROM NguoiDung WHERE IDNguoiDung LIKE \"QL%%\" ORDER BY IDNguoiDung desc limit 1")
                && db.resulSetNext()) {
            return db.getResulString("IDNguoiDung");
        }
        return "";
    }

    @Override
    public boolean querySQL(String s) {
        if (super.querySQL(s)) {
            isManager = userName.contains("QL");
            return true;
        }
        return false;
    }
}
