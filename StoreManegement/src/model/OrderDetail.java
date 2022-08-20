package model;

import sql.Database;

public class OrderDetail implements ExecuteSQLAble {
    private Order owner;
    private Product product;
    private int quantity;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public OrderDetail(Order owner, Product product, int quantity) {
        this.owner = owner;
        this.product = product;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OrderDetail() {
        super();
    }

    public OrderDetail(Order owner) {
        this();
        this.owner = owner;
    }

    public Order getOwner() {
        return owner;
    }

    public void setOwner(Order owner) {
        this.owner = owner;
    }
    public int getCost(){
        return product.getCost()*quantity;
    }

    @Override
    public String autoIncreaseID() {
        return null;
    }

    @Override
    public String getMaxID() {
        return null;
    }

    @Override
    public boolean updateSQL() {
        return false;
    }

    @Override
    public boolean insertSQL() {
        String execute = "INSERT INTO ChiTietHD VALUE('" + owner.getOrderID() + "','" + product.getProductID() + "'," + quantity + ")";
        System.out.println(execute);
        Database db = new Database();
        product.setQuantity(product.getQuantity() - quantity);
        return db.executeUpdate(execute) > 0 && product.updateSQL();
    }

    @Override
    public boolean deleteSQL() {
        return false;
    }

    @Override
    public boolean querySQL(String s) {
        return false;
    }
}
