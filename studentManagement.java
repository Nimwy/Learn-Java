import java.util.ArrayList;
import java.util.Scanner;

public class studentManagement {
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);

        class Student {
            int id;
            String name;
            int age;
            double gpa;

            void input() {
                System.out.print("Nhap id: ");
                id = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                System.out.print("Nhap ten: ");
                name = scanner.nextLine();

                System.out.print("Nhap tuoi: ");
                age = scanner.nextInt();

                System.out.print("Nhap GPA: ");
                gpa = scanner.nextDouble();
            }
        }
    
        ArrayList<Student> students = new ArrayList<>();
        
        System.out.println("===== STUDENT MANAGEMENT =====\n" + //
                        "\n" + //
                        "1. Add student\n" + //
                        "2. Show students\n" + //
                        "3. Find student\n" + //
                        "4. Average GPA\n" + //
                        "5. Exit");

        while (true) {

            int choose;
            System.out.print("Choose: ");
            choose = scanner.nextInt();

            if (choose == 1) {
                System.out.println("Add student:");
                Student student = new Student();
                student.input();
                students.add(student);
            } else if (choose == 2) {
                int choose_number;
                System.out.println("Show students(number): ");
                choose_number = scanner.nextInt();
                System.out.println("Show students:");
                
                int index = choose_number - 1;
                Student student = students.get(index);
                System.out.println("ID: " + student.id);
                System.out.println("Name: " + student.name);
                System.out.println("Age: " + student.age);
                System.out.println("GPA: " + student.gpa);
                    
                

            } else if (choose == 3) {
                System.out.println("Find by:\n" + //
                                        "1. ID\n" + //
                                        "2. Name");
                int find_choice;
                find_choice = scanner.nextInt();
                if (find_choice == 1) {
                    System.out.print("Nhap id: ");
                    int find_id = scanner.nextInt();
                    for (Student student : students) {
                        if (student.id == find_id) {
                            System.out.println("ID: " + student.id);
                            System.out.println("Name: " + student.name);
                            System.out.println("Age: " + student.age);
                            System.out.println("GPA: " + student.gpa);
                        }
                    }
                } else if (find_choice == 2) {
                    System.out.print("Nhap name: ");
                    scanner.nextLine(); // Consume the newline character
                    String find_name = scanner.nextLine();
                    for (Student student : students) {
                        if (student.name.equalsIgnoreCase(find_name)) {
                            System.out.println("ID: " + student.id);
                            System.out.println("Name: " + student.name);
                            System.out.println("Age: " + student.age);
                            System.out.println("GPA: " + student.gpa);
                        }
                    }
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            } else if (choose == 4) {
                if (students.isEmpty()) {
                    System.out.println("No students available to calculate average GPA.");
                } else {
                    double totalGPA = 0;
                    for (Student student : students) {
                        totalGPA += student.gpa;
                    }
                    double averageGPA = totalGPA / students.size();
                    System.out.println("Average GPA: " + averageGPA);
                }
            } else if (choose == 5) {
                System.out.println("Exit");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
