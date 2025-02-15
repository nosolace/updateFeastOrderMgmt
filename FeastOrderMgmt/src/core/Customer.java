/*
    Lớp mô tả cho đối tượng "đơn đăng ký của khách hàng"
 */
package core;

import java.io.Serializable;

/**
 *
 * @author no-solace
 */
public class Customer implements Comparable<Customer>, Serializable {

    public static final String CODE_FORMAT = "[CGK]{1}[\\d]{4}";
    public static final String NAME_FORMAT = "[a-zA-z ]{2,25}";
    public static final String PHONE_FORMAT = "0[1-9][0-9]{8}";
    public static final String EMAIL_FORMAT = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    private String code, name, phone, email;

    public Customer(String code, String name, String phone, String email) {
        this.code = code;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public Customer(String code) {
        this.code = code;
    }

    // Getter ID
    public String getCode() {
        return code;
    }

    // Getter name
    public String getName() {
        return name;
    }

    // Getter phone
    public String getPhone() {
        return phone;
    }

    // Getter email
    public String getEmail() {
        return email;
    }

    // Setter Name
    public void setName(String name) {
        name = name.trim();
        if (name.matches(NAME_FORMAT)) {
            this.name = name;
        }
    }

    // Setter Phone
    public void setPhone(String phone) {
        phone = phone.trim();
        if (phone.matches(PHONE_FORMAT)) {
            this.phone = phone;
        }
    }

    // Setter Email
    public void setEmail(String email) {
        email = email.trim();
        if (email.matches(EMAIL_FORMAT)) {
            this.email = email;
        }
    }

    public String formatName() {
        // Cắt tên thành từng chuỗi theo " "
        String[] tokens = name.split(" ");
        // Kiểm tra họ tên có nhiều
        if (tokens.length <= 1) {
            return name;
        } else {
            // firstName
            String firstName = tokens[tokens.length - 1];
            // last và middle name
            String lastMidName = tokens[0];
            for (int i = 1; i <= tokens.length - 2; i++) {
                lastMidName = lastMidName + " " + tokens[i];
            }
            return firstName + ", " + lastMidName;
        }
    }

    @Override
    public boolean equals(Object obj) {
        Customer customer = (Customer) obj;
        return this.code.equals(customer.code);
    }

    @Override
    public int compareTo(Customer o) {
        return this.formatName().compareTo(o.formatName());
    }

    @Override
    public String toString() {
        return String.format("%-8s| %-30s| %-11s| %-30s", code, this.formatName(), phone, email);
    }
}
