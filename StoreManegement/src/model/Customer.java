package model;

import sql.Database;

public class Customer implements ExecuteSQLAble {

  private String customerID;
  private String customerName;
  private String phoneNumber;
  private int points = 0;


  public String getCustomerID() {
    return customerID;
  }

  public void setCustomerID(String customerID) {
    this.customerID = customerID;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public boolean setPhoneNumber(String phoneNumber) {
    String pattern = "0\\d{9,10}";
    if (phoneNumber.matches(pattern)){
      this.phoneNumber = phoneNumber;
      return true;
    }
    return false;
  }

  public int getPoints() {
    return points;
  }

  public void setPoints(int points) {
    this.points = points;
  }

  public Customer() {
  }

  @Override
  public String autoIncreaseID() {
    String maxID = getMaxID();
    if (!maxID.equals("")) {
      customerID = "KH" + String.format("%02d", Integer.parseInt(maxID.substring(2)) + 1);
    }
    return customerID;
  }

  @Override
  public String getMaxID() {
    Database db = new Database();
    if (db.executeQuery("SELECT IDKhachHang FROM KhachHang ORDER BY IDKhachHang desc limit 1")
        && db.resulSetNext()) {
      return db.getResulString("IDKhachHang");
    }
    return "";
  }

  @Override
  public boolean updateSQL() {
    String execute =
        "UPDATE KhachHang SET DiemTichLuy = " + points + " where IDKhachHang = '" + customerID
            + "'";
    System.out.println(execute);
    Database db = new Database();
    if (db.executeUpdate(execute) > 0) {
      return true;
    }
    return false;
  }

  @Override
  public boolean insertSQL() {
    Database db = new Database();
    String executeInsert =
        "INSERT IGNORE INTO KhachHang VALUE (" + String.format("'%s','%s', '%s', %d)",
            customerID, customerName, phoneNumber, points);
    return db.executeUpdate(executeInsert) > 0;
  }

  @Override
  public boolean deleteSQL() {
    return false;
  }

  @Override
  public boolean querySQL(String s) {
    Database db = new Database();
    if (db.executeQuery(s) && db.resulSetNext()) {
      customerID = db.getResulString("IDKhachHang");
      customerName = db.getResulString("TenKhachHang");
      phoneNumber = db.getResulString("SDT");
      points = Integer.parseInt(db.getResulString("DiemTichLuy"));
      if (customerName.length() != 0) {
        return true;
      } else {
        return false;
      }
    }
    return false;
  }
}
