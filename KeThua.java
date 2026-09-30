public class KeThua {
    public static void main(String[] args) {
        Student student = new Student("An", 20, 3.5);
        Teacher teacher = new Teacher("Binh", 30, "Java");

        student.introduce();
        // student.study();

        teacher.introduce();
        // teacher.teach();
    }
}

class Person {
    protected String name;
    protected int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void introduce() {
        System.out.println("My name is " + name + ". I am " + age + " years old.");
    }
}

class Student extends Person {
    private double gpa;

    public Student(String name, int age, double gpa) {
        super(name, age);
        this.gpa = gpa;
    }

    // public void study() {
    //     System.out.println(name + " is studying" + ".");
    // }

    @Override 
    public void introduce() {
        System.out.println("My name is " + name + ". I am a student.");
    }
}

class Teacher extends Person {
    private String subject;

    public Teacher(String name, int age, String subject) {
        super(name, age);
        this.subject = subject;
    }

    // public void teach() {
    //     System.out.println(name + " is teaching " + subject + ".");
    // }

    @Override
    public void introduce() {
        System.out.println("My name is " + name + ". I am a teacher.");
    }
}


