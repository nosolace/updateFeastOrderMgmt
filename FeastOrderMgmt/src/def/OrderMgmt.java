/*
    Chương trình quản lý
 */
package def_package;

import core.CustomerList;
import core.MenuList;
import core.OrderList;
import tools.ConsoleInputter;

/**
 *
 * @author no-solace
 */
public class OrderMgmt {

    public static void main(String[] args) {

        String customerData = "data/customer.dat"; //File chứa khách hàng
        String menuData = "data/FeastMenu.csv"; //File chứa menu
        String orderData = "data/feast_order_service.dat"; // File chứa order
        CustomerList customerList = new CustomerList();
        customerList.loadFromFile(customerData); // Nạp thông tin khách hàng
        MenuList menuList = new MenuList();
        menuList.loadFromFile(menuData); // Nạp thông tin menu
        OrderList orderList = new OrderList(customerList, menuList);
        orderList.loadFromFile(orderData);

        int choice; //biến menu
        boolean isChanged = false; //biến "isChange" kiểm tra thông tin đăng ký có bị thay đổi hay không
        do {
            choice = ConsoleInputter.intMenu("Register customers", "Update customer information",
                    "Search for customer information by name", "Display feast menus", "Place a feast order",
                    "Update order information", "Save data to file",
                    "Display Customer or Order lists", "Exit the Program");

            switch (choice) {
                case 1:
                    do {
                        customerList.addRegist();
                    } while (ConsoleInputter.getBoolean("Enter new customer"));
                    isChanged = true;
                    break;
                case 2:
                    do {
                        customerList.update();
                    } while (ConsoleInputter.getBoolean("Update another customer"));
                    isChanged = true;
                    break;
                case 3:
                    customerList.printByName();
                    break;
                case 4:
                    menuList.displayMenu();
                    break;
                case 5:
                    do {
                        orderList.addOrder();
                    } while (ConsoleInputter.getBoolean("Enter new order"));
                    isChanged = true;
                    break;
                case 6:
                    do {
                        orderList.update();
                    } while (ConsoleInputter.getBoolean("Update another order"));
                    isChanged = true;
                    break;
                case 7:
                    customerList.saveFile(customerData);
                    orderList.saveFile(orderData);
                    isChanged = false;
                    break;
                case 8:
                    System.out.println("Show list of Customer or Order?");
                    int show = ConsoleInputter.intMenu("Customer", "Order");
                    switch (show) {
                        case 1:
                            customerList.printList();
                            break;
                        case 2:
                            orderList.displayOrderDetail();
                            break;
                    }
                    break;
                default:
                    if (isChanged == true) {
                        if (ConsoleInputter.getBoolean("Data changed. Save data to file.") == true) {
                            orderList.saveFile(orderData);
                            customerList.saveFile(customerData);
                        }
                        System.out.println("Good bye!");
                    }
            }
        } while (choice < 9);
    }
}
