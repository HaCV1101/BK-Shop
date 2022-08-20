package model;


import sql.Database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class User implements ExecuteSQLAble {

    protected boolean isManager;
    protected String userID;
    protected String userName;
    protected String accountName;
    protected String password;
    protected Date dateOfBirth;
    protected String phoneNumber;
    protected String address;
    protected String avatar;

    public User() {
        super();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            dateOfBirth = sdf.parse("19000101");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public abstract void setAvatar();

    @Override
    public abstract String autoIncreaseID();

    @Override
    public abstract String getMaxID();

    @Override
    public boolean updateSQL() {
        Database db = new Database();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String executeUpdate = "UPDATE NguoiDung SET TenNguoiDung = '" +
                userName + "',TenDangNhap='" + accountName + "',MatKhau = '" +
                password + "', NgaySinh = '" + sdf.format(dateOfBirth) + "',SDT = '" +
                phoneNumber + "', DiaChi = '" + address + "' where IDNguoiDung = '" + userID + "'";
        System.out.println(executeUpdate);
        return db.executeUpdate(executeUpdate) > 0;
    }

    @Override
    public boolean insertSQL() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String executeInsert =
                "INSERT IGNORE INTO NguoiDung VALUE (" + String.format(
                        "'%s','%s', '%s', '%s', '%s', '%s', '%s')",
                        userID, userName, accountName, password, sdf.format(dateOfBirth), phoneNumber, address);
        System.out.println(executeInsert);
        Database db = new Database();
        return db.executeUpdate(executeInsert) > 0;
    }

    @Override
    public boolean deleteSQL() {
        String executeDelete =
                "DELETE from NguoiDung WHERE IDNguoiDung = " + String.format("'%s'",
                        userID);
        Database db = new Database();
        System.out.println(executeDelete);
        return db.executeUpdate(executeDelete) > 0;
    }

    @Override
    public String toString() {
        return userID;
    }

    @Override
    public boolean querySQL(String s) {
        Database db = new Database();
        if (db.executeQuery(s) && db.resulSetNext()) {
            userID = db.getResulString("IDNguoiDung");
            userName = db.getResulString("TenNguoiDung");
            accountName = db.getResulString("TenDangNhap");
            password = db.getResulString("MatKhau");
            String date = db.getResulString("NgaySinh");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                dateOfBirth = sdf.parse(date);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            phoneNumber = db.getResulString("SDT");
            address = db.getResulString("DiaChi");
            return userName.length() != 0;
        }
        return false;
    }
}
