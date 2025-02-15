/*
    Lớp mô tả cho một đơn đặt hàng
 */
package core;

import java.io.Serializable;

/**
 *
 * @author no-solace
 */
public class Order implements Serializable {

    protected static int count = 0;
    private int orderID;
    private String customerCode, menuCode;
    private int numOfTables;
    private String date;

    public Order(String customerCode, String menuCode, int numOfTables, String date) {
        Order.count = 1 + Order.count;
        this.orderID = count;
        this.customerCode = customerCode;
        this.menuCode = menuCode;
        this.numOfTables = numOfTables;
        this.date = date;
    }

    public Order(int orderID) {
        this.orderID = orderID;
        this.customerCode = "unknown";
        this.menuCode = "unknown";
        this.date = "unknown";
    }

    public Order(String customerCode, String menuCode, String date) {
        this.orderID = 0;
        this.customerCode = customerCode;
        this.menuCode = menuCode;
        this.date = date;
    }

    @Override
    public boolean equals(Object obj) {
        Order o = (Order) obj;
        return this.orderID == o.orderID
                || this.customerCode.equals(o.customerCode) && this.menuCode.equals(o.menuCode) && this.date.equals(o.date);
    }

    public int getOrderID() {
        return orderID;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public int getNumOfTables() {
        return numOfTables;
    }

    public String getDate() {
        return date;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public void setNumOfTables(int numOfTables) {
        this.numOfTables = numOfTables;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
