public class GenericBoundEx {
    public static void main(String[] args) {
        System.out.println(sum(10, 20));
        System.out.println(sum(10.5, 20.5));
        System.out.println(sum(10.5f, 20.5f));
        System.out.println(multiply(10, 20));
        System.out.println(multiply(10.5, 20.5));
        System.out.println(multiply(10.5f, 20.5f));

        // System.out.println(sum("10", "20"));
    }

    public static <T extends Number> double sum(T a, T b) {
        return a.doubleValue() + b.doubleValue();
    }

    public static <T extends Number> double multiply(T a, T b) {
        return a.doubleValue() * b.doubleValue();
    }
}
