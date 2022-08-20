package app.home.activity.sell;

import java.util.Scanner;
import model.Customer;
import model.Employee;
import model.Order;
import model.Product;
import model.User;
import sql.Database;

public class SellTest {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Order order = new Order(new Employee());
    Database.getConnect();
    order.autoIncreaseID();
    while (true) {
      showMenu();
      int choose = scanner.nextInt();
      scanner.nextLine();
      switch (choose) {
        case 1:
          System.out.println("id:");
          String id = scanner.nextLine();
          System.out.println("sl:");
          int sl = scanner.nextInt();
          scanner.nextLine();
          order.addProduct(id, sl);
          break;
        case 2:
          System.out.println("% giam gia:");
          int gg = scanner.nextInt();
          scanner.nextLine();
          order.setDiscount(gg);
          break;
        case 3:
          order.setHaveCard(true);
          System.out.println("customer:");
          String idc = scanner.nextLine();
          Customer cus = new Customer();
          cus.querySQL("SELECT * FROM KhachHang where IDKhachHang = \"" + idc + "\"");
          order.setCustomer(cus);
          System.out.println("points: "+cus.getPoints());
          System.out.println("use points: ");
          int point = scanner.nextInt();
          order.setUsedPoints(point);
          scanner.nextLine();
          break;
        case 4:
          System.out.println(order);
          break;
        case 5:
          System.exit(0);
          break;
        default:
          break;
      }
    }
  }

  private static void showMenu() {
    System.out.println("""
        1: Them sp
        2: Giam gia
        3: su dung the tich diem
        4: Xuat hoa don
        5: exit""");
  }
}
