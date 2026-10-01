import java.util.ArrayList;

public class WildcardExtendsEx {
    public static void main(String[] args) {
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(10);
        numbers.add(20);
        numbers.add(30);

        ArrayList<Double> prices = new ArrayList<>();
        prices.add(10.5);
        prices.add(20.5);
        prices.add(30.5);

        System.out.println("Sum: " + sumNumbers(numbers));
        System.out.println("Sum: " + sumNumbers(prices));
    }

    public static double sumNumbers(ArrayList<? extends Number> list) {
        double sum = 0;
        for (Number n : list) {
            sum += n.doubleValue();
        }
        return sum;
    }
}
