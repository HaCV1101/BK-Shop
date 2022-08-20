package model;


import sql.Database;

public class Product implements Comparable<Product>, ExecuteSQLAble {

    private String productID;
    private String productName;
    private int cost;
    private int quantity;
    private String productDetail;

    public Product() {
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return productID;
    }

    @Override
    public String autoIncreaseID() {
        String maxID = getMaxID();
        if (!maxID.equals("")) {
            productID = "SP" + String.format("%03d", Integer.parseInt(maxID.substring(2)) + 1);
        }
        return productID;
    }

    @Override
    public String getMaxID() {
        Database db = new Database();
        if (db.executeQuery("SELECT IDSanPham FROM SanPham ORDER BY IDSanPham desc limit 1")) {
            if (db.resulSetNext()) {
                return db.getResulString("IDSanPham");
            } else {
                return "SP0";
            }
        }
        return "";
    }

    @Override
    public boolean updateSQL() {
        String execute =
                "UPDATE SanPham SET SoLuong = " + quantity + ",GiaBan = " + cost + " where IDSanPham = '" + productID + "'";
        System.out.println(execute);
        Database db = new Database();
        return db.executeUpdate(execute) > 0;
    }

    @Override
    public boolean insertSQL() {
        Database db = new Database();
        String executeInsert =
                "INSERT IGNORE INTO SanPham VALUE (" + String.format("'%s','%s', %d, %d, %s)",
                        productID, productName, cost, quantity, productDetail.equals("") ? null : "'" + productDetail + "'");
        System.out.println(executeInsert);
        return db.executeUpdate(executeInsert) > 0;
    }

    @Override
    public boolean deleteSQL() {
        Database db = new Database();
        String executeDelete = "DELETE FROM SanPham where IDSanPham = '" + productID + "'";
        System.out.println(executeDelete);
        return db.executeUpdate(executeDelete) > 0;
    }

    @Override
    public boolean querySQL(String s) {
        Database db = new Database();
//        System.out.println(s);
        if (db.executeQuery(s) && db.resulSetNext()) {
            productID = db.getResulString("IDSanPham");
            productName = db.getResulString("TenSanPham");
            cost = Integer.parseInt(db.getResulString("GiaBan"));
            quantity = Integer.parseInt(db.getResulString("SoLuong"));
            productDetail = db.getResulString("ChiTietSP");
            return productID.length() != 0;
        }
        return false;
    }

    @Override
    public int compareTo(Product product) {
        return this.productID.compareTo(product.productID);
    }

    public boolean search(String keyWord) {
        return productID.toLowerCase().contains(keyWord.toLowerCase()) || productName.toLowerCase().contains(keyWord.toLowerCase());
    }
}
