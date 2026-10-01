
import java.util.ArrayList;

public class GenericEx {
    public static void main(String[] args) {

        Box<String> s = new Box<>("Hello Java");
        Box<Integer> i = new Box<>(100);
        Box<Double> d = new Box<>(99.5);

        System.out.println("String: ");
        Box.printValue(s.getValue());
        System.out.println("Integer: ");
        Box.printValue(i.getValue());
        System.out.println("Double: ");
        Box.printValue(d.getValue());

        ArrayList<String> names = new ArrayList<>();
        names.add("An");
        names.add("Binh");
        names.add("Nam");

        String firstName = Box.getFirstElement(names);
        System.out.println("First element: " + firstName);

    }
}

class Box<T> {
    private T value;

    public Box(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public static <T> void printValue(T value) {
        System.out.println(value); 
    }

    public static <T> T getFirstElement(ArrayList<T> list) {
        return list.get(0);
    }
}