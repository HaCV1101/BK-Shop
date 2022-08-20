package model;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Consumer;

import billprint.BillPrinter;
import sql.Database;

public class Order implements ExecuteSQLAble {

    private String orderID;
    private User user;
    private boolean haveCard = false;
    private Customer customer;

    private int discount = 0;
    private int usedPoints;
    private List<OrderDetail> orderDetails = new ArrayList<>();
    private String date;

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = Math.max(discount, 0);
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Order(User user) {
        super();
        this.user = user;
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
        this.date = sdf.format(date);
    }

    @Override
    public String autoIncreaseID() {
        String maxID = getMaxID();
        if (!maxID.equals("")) {
            orderID = "HD" + String.format("%03d", Integer.parseInt(maxID.substring(2)) + 1);
        }
        return orderID;
    }

    public void addProduct(Product product, int quantity) {
        for (OrderDetail od : orderDetails) {
            if (od.getProduct().equals(product)) {
                od.setQuantity(quantity);
                return;
            }
        }
        orderDetails.add(new OrderDetail(this, product, quantity));
    }

    public boolean isHaveCard() {
        return haveCard;
    }

    public void setHaveCard(boolean haveCard) {
        this.haveCard = haveCard;
    }

    public boolean addProduct(String productID, int quantity) {
        Product product = new Product();
        if (product.querySQL("select * from SanPham where IDSanPham = \"" + productID + "\"")) {
            addProduct(product, quantity);
            return true;
        }
        return false;
    }

    public boolean removeProduct(Product product) {
        for (OrderDetail od : orderDetails) {
            if (od.getProduct().equals(product)) {
                orderDetails.remove(od);
                return true;
            }
        }
        return false;
    }

    public int getTotalCost() {
        int totalCost = 0;
        for (OrderDetail od : orderDetails) {
            totalCost += od.getProduct().getCost() * od.getQuantity();
        }
        return totalCost;
    }

    public int getTotal() {
        int totalCost = getTotalCost();
        return totalCost - totalCost * discount / 100 - usedPoints;
    }

    public User getUser() {
        return user;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getUsedPoints() {
        return usedPoints;
    }

    public boolean setUsedPoints(int usedPoints) {
        if (usedPoints > customer.getPoints()) {
            return false;
        }
        this.usedPoints = usedPoints;
        return true;
    }

    @Override
    public String toString() {
//        StringBuilder sb = new StringBuilder("\t\t\t\t\tCTHD-" + orderID + "\n");
//        Set<Entry<Product, Integer>> set = orderDetails.entrySet();
//        sb.append("\tProduct").append(String.format("%35s\n", "Quantity"));
//        for (Entry<Product, Integer> orderDetail : set) {
//            sb.append(String.format("%-40s", orderDetail.getKey().getProductName())).append(orderDetail.getValue())
//                    .append("\n");
//        }
//        int total = getTotalCost();
//        sb.append("Total cost: ").append(total).append("\n");
//        sb.append("Discount: ").append((this.discount * total) / 100).append("\n");
//        sb.append("Use point: ").append(usedPoints).append("\n");
//        sb.append(("Total: ")).append(getTotal()).append("\n");
//        Calendar calendar = Calendar.getInstance();
//        Date date = calendar.getTime();
//        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd-HH:mm:ss");
//        sb.append(sdf.format(date)).append("\n");
//        return sb.toString();
        return "";
    }

    @Override
    public String getMaxID() {
        Database db = new Database();
        if (db.executeQuery("SELECT IDHoaDon FROM HoaDon ORDER BY IDHoaDon desc limit 1")) {
            if (db.resulSetNext()) {
                return db.getResulString("IDHoaDon");
            } else {
                return "HD000";
            }
        }
        return "";
    }

    @Override
    public boolean updateSQL() {
        return false;
    }

    @Override
    public boolean insertSQL() {
        StringBuilder execute =
                new StringBuilder(
                        "INSERT INTO HoaDon value ('" + autoIncreaseID() + "','" + user.getUserID() + "',");
        if (haveCard) {
            execute.append("'").append(customer.getCustomerID()).append("',");
        } else {
            execute.append("null,");
        }
        execute.append(discount).append(",").append(haveCard ? usedPoints : 0).append(",'").append(date)
                .append("')");
        Database db = new Database();
        System.out.println(execute);
        if (db.executeUpdate(execute.toString()) >= 0) {
            boolean rt = true;
            for (OrderDetail od : orderDetails) {
                rt = od.insertSQL() && rt;
            }
            return rt;
        }
        return false;
    }

    @Override
    public boolean deleteSQL() {
        return false;
    }

    @Override
    public boolean querySQL(String s) {
        return false;
    }

    public void print() {
        BillPrinter.billPrint(this, "src/billprint/" + orderID + ".pdf");
    }
}
