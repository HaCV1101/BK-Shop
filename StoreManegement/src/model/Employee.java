package model;

import sql.Database;

public class Employee extends User {

    public Employee() {
        super();
        isManager = false;
    }

    @Override
    public void setAvatar() {
        avatar = "NV" + String.format("%s.png", userID);
    }

    @Override
    public String autoIncreaseID() {
        String maxID = getMaxID();
        int id = Integer.parseInt(maxID.substring(2)) + 1;
        userID = "NV" + id;
        return userID;
    }

    @Override
    public String getMaxID() {
        Database db = new Database();
        if (db.executeQuery(
                "SELECT IDNguoiDung FROM NguoiDung WHERE IDNguoiDung LIKE \"NV%%\" ORDER BY IDNguoiDung desc limit 1")
                && db.resulSetNext()) {
            return db.getResulString("IDNguoiDung");
        }
        return "";
    }

    @Override
    public boolean querySQL(String s) {
        if (super.querySQL(s)) {
            isManager = userName.contains("NV");
            return true;
        }
        return false;
    }
}
