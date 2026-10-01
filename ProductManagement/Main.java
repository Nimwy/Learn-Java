package ProductManagement;


import ProductManagement.model.Product;
import ProductManagement.service.ProductService;


public class Main {
    public static void main(String[] args) {
        //1. Tạo ProductService
        ProductService productService = new ProductService();
        //2. Thêm 4 Product
        productService.addProduct(new Product(1, "Laptop", "Electronics", 1000));
        productService.addProduct(new Product(2, "Mouse", "Computer", 20));
        productService.addProduct(new Product(3, "Keyboard", "Computer", 50));
        productService.addProduct(new Product(4, "Monitor", "Electronics", 300));
        //3. Thử thêm Product có ID trùng
        productService.addProduct(new Product(1, "Laptop", "Electronics", 1000));
        //4. Thử thêm Product có price âm
        productService.addProduct(new Product(5, "Keyboard", "Computer", -50));
        //5. Tìm Product ID = 2
        System.out.println(productService.findProductById(2));
        //6. Xóa Product ID = 3
        productService.deleteProduct(3);
        //7. In toàn bộ Product còn lại
        System.out.println(productService.getAllProducts());
    }
}