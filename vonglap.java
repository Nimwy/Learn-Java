import java.util.Scanner;

public class vonglap {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Nhap n: ");
        int n = scanner.nextInt();

        // for (int i = 1; i <= n; i++) {
        //     System.out.println(i);
        // }

        int sum = 0;

        for (int i = 1; i <= n; i++) {
            sum += i;
        }

        System.out.println("Tong: " + sum

        );
    }
}