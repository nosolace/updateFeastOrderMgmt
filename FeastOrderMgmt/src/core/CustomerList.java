/*
    Lớp mô tả cho đối tượng List Đăng Ký Khách Hàng
 */
package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import tools.ConsoleInputter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author no-solace
 */
public class CustomerList extends ArrayList<Customer> {

    private static String code, name, phone, email;

    protected String inputCode() {
        return ConsoleInputter.getStr("Input code", Customer.CODE_FORMAT, "Code must be unique and follow given format").trim();
    }

    private String inputName() {
        return ConsoleInputter.getStr("Input name", Customer.NAME_FORMAT, "Length must be between 2 and 25 characters");
    }

    private String inputPhone() {
        return ConsoleInputter.getStr("Input phone", Customer.PHONE_FORMAT, "Must contain exactly 10 digits and belong to a valid Vietnamese network operator");
    }

    private String inputEmail() {
        return ConsoleInputter.getStr("Input email", Customer.EMAIL_FORMAT, "Email format: example@domain.com").trim();
    }

    protected boolean isCustomerInList(String code) {
        return this.indexOf(new Customer(code)) >= 0;
    }

    protected Customer getCustomer(String code) {
        return this.get(this.indexOf(new Customer(code)));
    }

    protected String getCustomerDetail(String code) {
        Customer c = getCustomer(code);
        return String.format("%-16s: %s\n%-16s: %s\n%-16s: %s\n%-16s: %s",
                "Code", c.getCode(), "Customer name", c.formatName(), "Phone number", c.getPhone(), "Email", c.getEmail());
    }

    public void addRegist() {
        do {
            code = inputCode();
            if (isCustomerInList(code)) {
                System.out.println("This code already exists.");
            }
        } while (isCustomerInList(code));
        name = inputName();
        phone = inputPhone();
        email = inputEmail();
        this.add(new Customer(code, name, phone, email));
        System.out.println("The new customer has been successfully added.");
    }

    public void update() {
        String code = inputCode();
        if (isCustomerInList(code)) {
            Customer c = getCustomer(code);
            name = ConsoleInputter.updateStr("Input name", Customer.NAME_FORMAT, "Length must be between 2 and 25 characters");
            c.setName(name);
            phone = phone = ConsoleInputter.updateStr("Input phone", Customer.PHONE_FORMAT, "Must contain exactly 10 digits and belong to a valid Vietnamese network operator");
            c.setPhone(phone);
            email = ConsoleInputter.updateStr("Input email", Customer.EMAIL_FORMAT, "Email format: example@domain.com");
            c.setEmail(email);
            System.out.println("The customer information has been successfully updated.");
        } else {
            System.out.println("This customer does not exist.");
        }
    }

    public void printList() {
        if (this.isEmpty()) {
            System.out.println("No one matches the search criteria!");
        } else {
            String seperator = "---------------------------------------------------------------------------";
            // biến tiêu đề (header)
            String header = seperator + "\n" + String.format("%-8s| %-30s| %-11s| %-30s\n", "Code", "Customer Name", "Phone", "Email") + seperator;
            System.out.println(header);
            for (Customer c : this) {
                System.out.println(c);
            }
            System.out.println(seperator);
        }
    }

    public void printByName() {
        String key = inputName().toUpperCase();
        CustomerList customers = new CustomerList(); // Biến customers lưu tên khách hàng cần tìm
        for (Customer c : this) {
            // Kiểm tra tên có chứa tên hay không
            if (c.getName().toUpperCase().contains(key)) {
                customers.add(c);
            }
        }
        Collections.sort(customers); // Sort theo yêu cầu
        if (customers.isEmpty()) {
            System.out.println("No one matches the search criteria!");
        } else {
            customers.printList();
        }
    }

    // Đọc file
    public void loadFromFile(String fname) {
        //Lấy file theo tên file
        File f = new File(fname);
        //Kiểm tra file có tồn tại
        if (f.exists()) {
            try (FileInputStream fis = new FileInputStream(fname);
                    ObjectInputStream ois = new ObjectInputStream(fis)) {
                int size = ois.readInt(); // Đọc số lượng đã lưu trước đó
                for (int i = 0; i < size; i++) {
                    Customer customer = (Customer) ois.readObject();
                    this.add(customer);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            System.out.println("This file does not exist.");
        }
    }

    // Ghi file
    public void saveFile(String fname) {
        try (FileOutputStream fos = new FileOutputStream(fname);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeInt(this.size());
            for (Customer customer : this) {
                oos.writeObject(customer);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
/* no longer human - Dazai */
