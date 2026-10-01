public class ExceptionEx1 {
    public static void main(String[] args) {
        try {
            ExceptionProduct product = new ExceptionProduct(1, "Laptop", "Electronics", 100.0);
            System.out.println("Product created successfully!");
        } catch (ProductException e) {
            System.out.println("Error creating product: " + e.getMessage());
        }
        
        try {
            ExceptionProduct product2 = new ExceptionProduct(2, "Smartphone", "Electronics", -50.0);
            System.out.println("Product created successfully!");
        } catch (ProductException e) {
            System.out.println("Error creating product: " + e.getMessage());
        }
    }
}

class ExceptionProduct {
    private int id;
    public String name;
    private String category;
    private double price;

    public ExceptionProduct(int id, String name, String category, double price) throws ProductException {

        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        validate();
    }

    public void validate() throws ProductException {
        
        if (id <= 0) {
            throw new ProductException("Invalid product ID.");
        }

        if (name == null || name.trim().isEmpty()) {
            throw new ProductException("Product name cannot be empty.");
        }

        if (category == null || category.trim().isEmpty()) {
            throw new ProductException("Product category cannot be empty.");
        }

        if (price < 0) {
            throw new ProductException("Price cannot be negative.");
        }
    }
    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

}

class ProductException extends Exception {
    public ProductException(String message) {
        super(message);
    }

}
