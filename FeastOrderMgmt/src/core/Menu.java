/*
    lớp mô tả cho đối tượng "thực đơn?"
 */
package core;

/**
 *
 * @author no-solace
 */
public class Menu implements Comparable<Menu> {

    //Thuộc tính thực đơn
    String code, name, ingredients;
    int price;

    //Contructor 4 tham số để tạo một set menu
    public Menu(String code, String name, int price, String ingredients) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.ingredients = ingredients;
    }

    //Constructor 1 tham số để tìm menu
    public Menu(String code) {
        this.code = code;
    }

    //Hành vi equals
    public boolean equals(Object obj) {
        Menu m = (Menu) obj;
        return this.code.equals(m.code);
    }

    // Getter code
    public String getCode() {
        return code;
    }

    //toString
    @Override
    public String toString() {
        return String.format("%-12s: %s\n", "Code", code)
                + String.format("%-12s: %s\n", "Name", name)
                + String.format("%-12s: %,d\n", "Price", price)
                + String.format("%-12s:\n%s", "Ingredients", ingredients.replace("#", "\n"));
    }

    //So sánh thực đơn
    @Override
    public int compareTo(Menu o) {
        return Integer.compare(this.price, o.price);
    }
}//100% Thực đơn 
