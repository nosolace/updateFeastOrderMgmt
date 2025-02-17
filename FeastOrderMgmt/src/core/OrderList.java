/*
    Lớp mô tả cho một List đơn đặt hàng
 */
package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import tools.ConsoleInputter;

/**
 *
 * @author no-solace
 */
public class OrderList extends ArrayList<Order> {

    private static int numberOfTable;
    private static String code, mCode, date; // mã khách hàng, mã thực đơn

    // Thông tin khách hàng và menu, chỉ đọc
    private final CustomerList customers;
    private final MenuList menuList;

    // Constructor 2 tham số để đọc
    public OrderList(CustomerList customers, MenuList menuList) {
        this.customers = customers;
        this.menuList = menuList;
    }

    private int inputID() {
        return ConsoleInputter.getInt("Input order ID ", 1, this.size());
    }

    private String inputMenu() {
        Object objChoice = ConsoleInputter.objFeastMenu(menuList.toArray());
        return ((Menu) objChoice).getCode();
    }

    private String inputEventDate() {
        Date date;
        do {
            date = ConsoleInputter.getDate("Input date", "dd/MM/yyyy");
            if (date.before(new java.util.Date())) {
                System.out.println("Must be a valid date in the future");
            }
        } while (date.before(new java.util.Date()));
        return ConsoleInputter.dateStr(date, "dd/MM/yyyy");
    }

    private int inputTable() {
        return ConsoleInputter.getInt("Input Number of Tables", 1, 100);
    }

    private boolean isDupplicate(String code, String mCode, String date) {
        return this.indexOf(new Order(code, mCode, date)) >= 0;
    }

    private Order getOrder(int id) {
        return this.get(this.indexOf(new Order(id)));
    }

    private void printOrderDetail(int id) {
        Order o = getOrder(id);
        Menu m = menuList.getMenu(o.getMenuCode());
        String seperator = "---------------------------------------------------------------------------";
        String header = String.format("Customer order information [Order ID: %d]", o.getOrderID());
        String orderInformation = String.format("%-16s: %s\n", "Code of Set Menu", o.getMenuCode())
                + String.format("%-16s: %s\n", "Set menu name", m.name)
                + String.format("%-16s: %s\n", "Event date", o.getDate())
                + String.format("%-16s: %d\n", "Number of tables", o.getNumOfTables())
                + String.format("%-16s: %,d\n", "Price", m.price)
                + String.format("%s:\n%s", "Ingredients", m.ingredients.replace("#", "\n"));
        String totalCost = String.format("%-16s: %,d", "Total cost", m.price * o.getNumOfTables()); // Tổng tiền
        System.out.println(seperator + "\n" + header + "\n" + seperator);
        System.out.println(customers.getCustomerDetail(o.getCustomerCode()));
        System.out.println(seperator);
        System.out.println(orderInformation);
        System.out.println(seperator);
        System.out.println(totalCost);
        System.out.println(seperator);
    }

    public void addOrder() {
        code = customers.inputCode();
        if (customers.isCustomerInList(code)) {
            mCode = inputMenu();
            numberOfTable = inputTable();
            date = inputEventDate();
            if (isDupplicate(code, mCode, date)) {
                System.out.println("Dupplicate data!");
            } else {
                this.add(new Order(code, mCode, numberOfTable, date));
                printOrderDetail(Order.count); // Biến count chính là id của Order
            }
        } else {
            System.out.println("This customer does not exist");
        }
    }

    public void update() {
        Order req = getOrder(inputID());
        do {
            mCode = inputMenu();
            numberOfTable = inputTable();
            date = inputEventDate();
            code = req.getCustomerCode();
            if (isDupplicate(code, mCode, date)) {
                System.out.println("Dupplicate data!");
            }
        } while (isDupplicate(code, mCode, date));
        req.setMenuCode(mCode);
        req.setNumOfTables(numberOfTable);
        req.setDate(date);
    }

    public void displayOrderDetail() {
        if (this.isEmpty()) {
            System.out.println("No data in the system.");
        } else {
            String seperator = "---------------------------------------------------------------------------";
            String header = seperator + "\n" + String.format("%-4s| %-11s| %-12s| %-9s| %-9s| %-7s|%12s",
                    "ID", "Event date", "Customer ID", "Set Menu", "Price", "Tables", "Cost") + "\n" + seperator;
            System.out.println(header);
            for (Order o : this) {
                int price = menuList.getMenu(o.getMenuCode()).price;
                int cost = price * o.getNumOfTables();
                String detail = String.format("%-4d| %-11s| %-12s| %-9s| %,9d| %7d|%,12d",
                        o.getOrderID(), o.getDate(), o.getCustomerCode(), o.getMenuCode(), price, o.getNumOfTables(), cost);
                System.out.println(detail);
            }
            System.out.println(seperator);
        }
    }

    public void loadFromFile(String fname) {
        //Lấy file theo tên file
        File f = new File(fname);
        //Kiểm tra file có tồn tại
        if (!f.exists()) {
            System.out.println("File does not exist"); // Thông báo không có file
        } else {
            try (FileInputStream fis = new FileInputStream(fname);
                    ObjectInputStream ois = new ObjectInputStream(fis)) {
                // Biến lưu số lượng Obj của list trong saveFile sẽ được đọc đầu tiên
                int size = ois.readInt();
                Order.count = size;
                // Load danh sách vào list này
                for (int i = 0; i < size; i++) {
                    Order order = (Order) ois.readObject();
                    this.add(order);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void saveFile(String fname) {
        try (FileOutputStream fos = new FileOutputStream(fname);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            // Ghi vào số lượng khách hàng
            oos.writeInt(this.size());
            // Vòng lặp duyệt từng list
            for (Order order : this) {
                oos.writeObject(order); // Ghi vào file
            }
        } catch (IOException e) { // Báo lỗi nếu có
            System.out.println(e);
        }
    }
}
