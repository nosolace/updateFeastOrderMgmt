/*
    Tạo công cụ nhập dữ liệu
 */
package tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author no-solace, "I took it from sensei, and made it mine"
 */
public class ConsoleInputter {

    public static Scanner sc = new Scanner(System.in);

    public static boolean getBoolean(String prompt) {
        System.out.print(prompt + " (Y/N, T/F, 1/0)?: ");
        String data = sc.nextLine().trim().toUpperCase();
        char c = data.charAt(0);
        return c == 'T' || c == 'Y' || c == '1';
    }

    public static int getInt(String prompt, int min, int max) {
        int result = 0;
        do {
            System.out.print(prompt + "[" + min + ", " + max + "]: ");
            result = Integer.parseInt(sc.nextLine().trim());
            if (result < min || result > max) {
                System.out.println("Value range: " + "[" + min + ", " + max + "]");
            }
        } while (result < min || result > max);
        return result;
    }

    public static double getDouble(String prompt, double min, double max) {
        double result = 0;
        do {
            System.out.print(prompt + "[" + min + ", " + max + "]: ");
            result = Double.parseDouble(sc.nextLine().trim());
            if (result < min || result > max) {
                System.out.println("Value range: [" + min + ", " + max + "]");
            }
        } while (result < min || result > max);
        return result;
    }

    public static String getStr(String prompt) {
        System.out.print(prompt + ": ");
        return sc.nextLine().trim();
    }

    public static String getStr(String prompt, String pattern, String errorMsg) {
        String data;
        boolean valid;
        do {
            System.out.print(prompt + ": ");
            data = sc.nextLine().trim();
            valid = data.matches(pattern);
            if (!valid) {
                System.out.println(errorMsg);
            }
        } while (!valid);
        return data;
    }

    public static String updateStr(String prompt, String pattern, String errorMsg) {
        String data;
        boolean valid;
        do {
            System.out.print(prompt + ": ");
            data = sc.nextLine().trim();
            valid = data.matches(pattern) || data.isEmpty();
            if (!valid) {
                System.out.println(errorMsg);
            }
        } while (!valid);
        return data;
    }

    public static int intMenu(Object... options) {
        int n = options.length;
        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + " - " + options[i]);
        }
        return getInt("Choice ", 1, n);
    }

    public static int intFeastMenu(Object... options) {
        int n = options.length;
        for (int i = 0; i < n; i++) {
            System.out.println("----------" + " Option " + (i + 1) + " ----------\n" + options[i]);
        }
        return getInt("Choice ", 1, n);
    }

    public static Object objFeastMenu(Object... options) {
        int choice = intFeastMenu(options);
        return options[choice - 1];
    }

    public static Object objMenu(Object... options) {
        int choice = intMenu(options);
        return options[choice - 1];
    }

    public static Date getDate(String prompt, String dateFormat) {
        String dateStr;
        Date d;
        DateFormat formatter = new SimpleDateFormat(dateFormat);

        do {
            System.out.print(prompt + ": ");
            dateStr = sc.nextLine().trim();
            try {
                d = formatter.parse(dateStr);
            } catch (ParseException e) {
                System.out.println("Date format should be " + dateFormat + ".");
                d = null;
            }
        } while (d == null);
        return d;
    }

    public static String dateStr(Date date, String dateFormat) {
        DateFormat formatter = new SimpleDateFormat(dateFormat);
        return (date == null) ? null : formatter.format(date);
    }
}
