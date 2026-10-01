package ProductManagement.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import ProductManagement.model.Product;
import ProductManagement.util.ProductValidator;

public class ProductService {
    private ArrayList<Product> products;

    public ProductService() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        if (!ProductValidator.isValidId(product.getId()) ||
            !ProductValidator.isValidName(product.getName()) ||
            !ProductValidator.isValidPrice(product.getPrice())) {
            return;
        }

        if (findProductById(product.getId()) != null) {
            return;
        }

        products.add(product);
    }

    public Product findProductById(int id) {
        return products.stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean deleteProduct(int id) {
        return products.removeIf(product -> product.getId() == id);
    }

    public ArrayList<Product> getAllProducts() {
        return products;
    }

    public List<Product> findProductsByMinPrice(double minPrice) {
        return products.stream()
                .filter(product -> product.getPrice() >= minPrice)
                .toList();
    }

    public List<String> getProductNamesByCategory(String category) {
        return products.stream()
                .filter(product -> product.getCategory().equalsIgnoreCase(category))
                .map(Product::getName)
                .toList();
    }

    public Product findMostExpensiveProduct() {
        return products.stream()
                .max(Comparator.comparingDouble(Product::getPrice))
                .orElse(null);
    }

    public boolean areAllPricesValid() {
        return products.stream()
                .allMatch(product -> product.getPrice() >= 0);
    }

    public long countProductsByCategory(String category) {
        return products.stream()
                .filter(product -> product.getCategory().equalsIgnoreCase(category))
                .count();
    }

    public List<String> getExpensiveProductNames(double minPrice) {
        return products.stream()
                .filter(product -> product.getPrice() >= minPrice)
                .map(Product::getName)
                .sorted()
                .toList();
    }
}