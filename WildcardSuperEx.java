import java.util.ArrayList;

public class WildcardSuperEx {
    public static void main(String[] args) {

        ArrayList<Integer> integers = new ArrayList<>();
        ArrayList<Number> numbers = new ArrayList<>();
        ArrayList<Object> objects = new ArrayList<>();

        addNumbers(integers);
        addNumbers(numbers);
        addNumbers(objects);

        System.out.println("Integers: " + integers);
        System.out.println("Numbers: " + numbers);
        System.out.println("Objects: " + objects);

        // ArrayList<Double> doubles = new ArrayList<>();
        // addNumbers(doubles); // ❌
    }

    public static void addNumbers(ArrayList<? super Integer> list) {
        list.add(10);
        list.add(20);
        list.add(30);
    }
}