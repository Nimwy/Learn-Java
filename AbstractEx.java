public class AbstractEx {
    public static void main(String[] args) {
        Employee[] employees = {
            new FullTimeEmployee(1, "John Doe", 5000, 1000),
            new PartTimeEmployee(2, "Jane Smith", 2000, 20, 25),
            new FullTimeEmployee(3, "Bob Johnson", 6000, 1500),
            new PartTimeEmployee(4, "Alice Williams", 2500, 25, 20)
        };
        
        double totalSalary = 0;
        double maxSalary = 0;
        Employee highestPaidEmployee = null;
        for (Employee employee : employees) {
            employee.showInfo();
            employee.work();

            double salary = employee.calculateSalary();

            totalSalary += salary;

            if (salary > maxSalary) {
                maxSalary = salary;
                highestPaidEmployee = employee;
            }
        }
        System.out.println("Total Salary: " + totalSalary);
        System.out.println("Highest Paid Employee: " + highestPaidEmployee.getName() + " with salary: " + maxSalary);

    }
}

abstract class Employee {
    protected int id;
    private String name;
    protected double baseSalary;

    public Employee(int id, String name, double baseSalary) {
        this.id = id;
        this.name = name;
        this.baseSalary = baseSalary;
    }

    public abstract double calculateSalary();

    public abstract void work();

    public void showInfo() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + getName());
        System.out.println("Base Salary: " + baseSalary);
        System.out.println("Salary: " + calculateSalary());
    }

    public String getName() {
        return name;
    }
}

class FullTimeEmployee extends Employee {
    private double bonus;

    public FullTimeEmployee(int id, String name, double baseSalary, double bonus) {
        super(id, name, baseSalary);
        this.bonus = bonus;
    }

    @Override
    public double calculateSalary() {
        return baseSalary + bonus;
    }
    @Override
    public void work() {
        System.out.println(getName() + " is working full-time.");
    }
}

class PartTimeEmployee extends Employee {
    private int hoursWorked;
    private double hourlyRate;

    public PartTimeEmployee(int id, String name, double baseSalary, int hoursWorked, double hourlyRate) {
        super(id, name, baseSalary);
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    @Override
    public double calculateSalary() {
        return hoursWorked * hourlyRate;
    }
    @Override
    public void work() {
        System.out.println(getName() + " is working part-time.");
    }
}