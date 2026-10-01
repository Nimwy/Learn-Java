import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class FileProductEx {
    public static void main(String[] args) {

        ArrayList<ProductFP> products = new ArrayList<>();

        products.add(new ProductFP(1, "Laptop", "Electronics", 1000));
        products.add(new ProductFP(2, "Mouse", "Computer", 20));
        products.add(new ProductFP(3, "Keyboard", "Computer", 50));
        products.add(new ProductFP(4, "Monitor", "Electronics", 300));

        // Ghi file
        try (FileWriter writer = new FileWriter("product.txt")) {

            for (ProductFP p : products) {
                writer.write(p.getId() + ",");
                writer.write(p.getName() + ",");
                writer.write(p.getCategory() + ",");
                writer.write(p.getPrice() + "\n");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Đọc file
        ArrayList<ProductFP> loadedProducts = new ArrayList<>();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader("product.txt"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                try {

                    String[] parts = line.split(",");
                    if (parts.length != 4) {
                        throw new IllegalArgumentException("Invalid product format");
                    }
                    int id = Integer.parseInt(parts[0].trim());
                    if (id <= 0) {
                        throw new IllegalArgumentException("ID must be positive");
                    }
                    double price = Double.parseDouble(parts[3].trim());
                    if (price < 0) {
                        throw new IllegalArgumentException("Price cannot be negative");
                    }

                    ProductFP product = new ProductFP(
                        id,
                        parts[1].trim(),
                        parts[2].trim(),
                        price
                    );
                    loadedProducts.add(product);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
                
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("Loaded Products:");

        for (ProductFP p : loadedProducts) {
            System.out.println(
                p.getId() + " | " +
                p.getName() + " | " +
                p.getCategory() + " | " +
                p.getPrice()
            );
        }
    }
}

class ProductFP {
    private int id;
    private String name;
    private String category;
    private double price;

    public ProductFP(int id, String name, String category, double price) {
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
}