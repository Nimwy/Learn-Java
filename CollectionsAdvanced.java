import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class CollectionsAdvanced {
    public static void main(String[] args) {

        HashMap<Integer, Product> products = new HashMap<>();

        products.put(1, new Product(1, "Product A", "Category 1", 10.0));
        products.put(2, new Product(2, "Product B", "Category 2", 20.0));
        products.put(3, new Product(3, "Product C", "Category 1", 15.0));
        
        addProduct(products, new Product(2, "Product D", "Category 3", 30.0));
        
        System.out.println("Find Product ID 2:");
        Product product = findProductById(products, 2);
        if (product != null) {
            product.showInfo();
        } else {
            System.out.println("Product not found.");
        }

        

        updateProduct(products, 2, "Updated Product B", "Updated Category", 25.0);
        updateProduct(products, 99, "Updated Product B", "Updated Category", 25.0);

        findProductById(products, 2).showInfo();
        findProductById(products, 99);

        deleteProduct(products, 3);

        addProduct(products, new Product(4, "Product D", "Category 3", 5.0));
        addProduct(products, new Product(5, "Product E", "Category 4", 40.0));

        ArrayList<Product> sortedByPrice = sortByPriceAscending(products);
        System.out.println("Products sorted by price (ascending):");
        for (Product p : sortedByPrice) {
            p.showInfo();
        }

        ArrayList<Product> sortedByName = sortByName(products);
        System.out.println("Products sorted by name:");
        for (Product p : sortedByName) {
            p.showInfo();
        }
        
    }

    public static boolean addProduct(
        HashMap<Integer, Product> products,
        Product product
    ) {
        if (products.containsKey(product.getId())) {
            return false;
        }

        products.put(product.getId(), product);
        return true;
    }

    public static Product findProductById(
        HashMap<Integer, Product> products,
        int id
    ) {
        return products.get(id);
    }

    public static boolean updateProduct(
        HashMap<Integer, Product> products,
        int id,
        String newName,
        String newCategory,
        double newPrice
    ) {
        if (products.containsKey(id)) {
            Product product = products.get(id);
            product.setName(newName);
            product.setCategory(newCategory);
            product.setPrice(newPrice);
            return true;
        } else {
            return false;
        }
    }

    public static boolean deleteProduct(
        HashMap<Integer, Product> products,
        int id
    ) {
        return products.remove(id) != null;
    }

    public static void displayAllProducts(HashMap<Integer, Product> products) {
        Iterator<Product> iterator = products.values().iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            product.showInfo();
        }
    }

    public static ArrayList<Product> sortByPriceAscending(
        HashMap<Integer, Product> products
    ) {
        ArrayList<Product> productList =
        new ArrayList<>(products.values());
        productList.sort((p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice()));
        return productList;
    }

    public static ArrayList<Product> sortByName(
        HashMap<Integer, Product> products
    ) {
        ArrayList<Product> productList =
        new ArrayList<>(products.values());
        productList.sort((p1, p2) -> p1.getName().compareTo(p2.getName()));
        return productList;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void showInfo() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Category: " + category);
        System.out.println("Price: " + price);
        System.out.println("-------------------------");
    }

}
