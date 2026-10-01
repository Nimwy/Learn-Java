import java.util.HashSet;

public class EqualsHashCodeEx {
    public static void main(String[] args) {

        ProductEH p1 = new ProductEH(1, "Laptop", 1000);
        ProductEH p2 = new ProductEH(1, "Gaming Laptop", 1500);
        ProductEH p3 = new ProductEH(2, "Mouse", 20);

        System.out.println(p1.equals(p2));
        System.out.println(p1.equals(p3));

        HashSet<ProductEH> products = new HashSet<>();
        products.add(p1);
        products.add(p2);
        products.add(p3);

        System.out.println(products.size());
    }


}

class ProductEH {
    private int id;
    public String name;
    private double price;

    public ProductEH(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof ProductEH)) {
            return false;
        }

        ProductEH other = (ProductEH) obj;

        return this.id == other.id;

    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

}