public class StaticEx {
    public static void main(String[] args) {
        Student s1 = new Student(1, "An", 3.5);
        Student s2 = new Student(2, "Binh", 3.8);
        Student s3 = new Student(3, "Nam", 5.0);

        Student.showStudentCount();
    }
}

class Student {
    private int id;
    private String name;
    private double gpa;
    private static int studentCount = 0;
    static void showStudentCount() {
        System.out.println("Total students: " + studentCount);
    }

    static boolean isValidGpa(double gpa) {
        if (gpa >= 0.0 && gpa <= 4.0) {
            return true;
        } else {
            return false;
        }
    }
    public Student(int id, String name, double gpa) {
        if (isValidGpa(gpa)) {
            this.id = id;
            this.name = name;
            this.gpa = gpa;
            studentCount++;
        } else {
            System.out.println("Invalid GPA. Student not created.");
        }
    }
}