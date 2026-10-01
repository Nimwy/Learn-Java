import java.util.ArrayList;

public class WildcardEx {
    public static void main(String[] args) {
        ArrayList<String> names = new ArrayList<>();
        names.add("An");
        names.add("Binh");
        names.add("Nam");

        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(10);
        numbers.add(20);
        numbers.add(30);

        ArrayList<Double> prices = new ArrayList<>();
        prices.add(10.5);
        prices.add(20.5);
        prices.add(30.5);

        printList(names);
        printList(numbers);
        printList(prices);

        // list.add("Hello");
    }

    public static void printList(ArrayList<?> list) {
        for (Object value : list) {
            System.out.println(value);
        }
    }
}
