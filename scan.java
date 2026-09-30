import java.util.Scanner;

public class scan {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Nhap ten: ");
        String name = scanner.nextLine();

        System.out.print("Nhap tuoi: ");
        int age = scanner.nextInt();

        System.out.println("Xin chao " + name);
        
        if (age < 18) {
            System.out.println("Ban chua du tuoi de lai xe");
        } else {
            System.out.println("Ban da du tuoi de lai xe");
        }
    }
}
