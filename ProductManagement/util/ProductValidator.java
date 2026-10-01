package ProductManagement.util;

public class ProductValidator {
    public static  boolean isValidId(int id) {
        return id > 0;
    }

    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    public static boolean isValidPrice(double price) {
        return price >= 0;
    }
}

