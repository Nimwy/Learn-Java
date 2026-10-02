package Annotation;

public class Main {
    public static void main(String[] args) {
        // 1. Tạo 3 Product
        Product product1 = new Product(1, "Laptop", "Electronics", 1500);
        Product product2 = new Product(2, "Mouse", "Computer", 25);     
        Product product3 = new Product(3, "Keyboard", "Computer", 70);  

        // 2. Tạo 3 Employee
        Employee employee1 = new Employee(101, "An", "IT", 1500);
        Employee employee2 = new Employee(102, "Binh", "HR", 1200);
        Employee employee3 = new Employee(103, "Cuong", "IT", 1800);
    
        System.out.println("Tat ca cac Product: ");
        System.out.println(product1);
        System.out.println(product2);
        System.out.println(product3);

        System.out.println("-------------------------------");

        System.out.println("\nTat ca cac Employee: ");
        System.out.println(employee1);
        System.out.println(employee2);
        System.out.println(employee3);

        try {
            product3.setPrice(100);
        } catch (IllegalArgumentException e) {
            System.out.println("Loi: " + e.getMessage());
        } finally {
            System.out.println("Gia tri sau khi thay doi: " + product3.getPrice());
        }

        try {
            employee2.setSalary(-1300);
        } catch (IllegalArgumentException e) {
            System.out.println("Loi: " + e.getMessage());
        } finally {
            System.out.println("Luong sau khi thay doi: " + employee2.getSalary());
        }
    }
}
