import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class CollectionsEx {

    public static void main(String[] args) {

        ArrayList<Product> products = new ArrayList<>();

        products.add(new Product(1, "Keyboard", "Computer", 500000));
        products.add(new Product(2, "Mouse", "Computer", 300000));
        products.add(new Product(3, "iPhone 15", "Phone", 20000000));
        products.add(new Product(4, "Galaxy S24", "Phone", 18000000));
        products.add(new Product(5, "Monitor", "Computer", 5000000));


        // 1. Show all products
        for (Product product : products) {
            product.showInfo();
        }


        // 2. Find product by ID
        Product product = findProductById(products, 3);

        if (product != null) {
            product.showInfo();
        } else {
            System.out.println("Product not found.");
        }


        // 3. Find products by category
        findProductsByCategory(products, "Phone");


        // 4. Calculate average price
        double averagePrice = calculateAveragePrice(products);

        System.out.println("Average price: " + averagePrice);


        // 5. HashSet
        HashSet<String> categories = new HashSet<>();

        for (Product p : products) {
            categories.add(p.getCategory());
        }

        System.out.println("Categories: " + categories);


        // 6. HashMap
        HashMap<Integer, Product> productMap = new HashMap<>();

        for (Product p : products) {
            productMap.put(p.getId(), p);
        }

        Product productFromMap = productMap.get(4);

        if (productFromMap != null) {
            productFromMap.showInfo();
        } else {
            System.out.println("Product not found.");
        }
    }


    public static Product findProductById(
            ArrayList<Product> products,
            int id
    ) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }

        return null;
    }


    public static void findProductsByCategory(
            ArrayList<Product> products,
            String category
    ) {
        for (Product product : products) {
            if (product.getCategory().equalsIgnoreCase(category)) {
                product.showInfo();
            }
        }
    }


    public static double calculateAveragePrice(
            ArrayList<Product> products
    ) {
        if (products.isEmpty()) {
            return 0;
        }

        double totalPrice = 0;

        for (Product product : products) {
            totalPrice += product.getPrice();
        }

        return totalPrice / products.size();
    }
}

class Product {
    private int id;
    private String name;
    private String category;
    private double price;

    public Product(int id, String name, String category, double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public void showInfo() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Category: " + category);
        System.out.println("Price: " + price);
        System.out.println("-------------------------");
    }

}
