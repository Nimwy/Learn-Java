public class Interface {
    public static void main(String[] args) {

        // Polymorphism qua abstract class Device
        Device[] devices = {
            new SmartPhone(1, "iPhone", 80),
            new Laptop(2, "MacBook", 60),
            new TV(3, "Samsung", 55)
        };

        for (Device device : devices) {
            device.showInfo();
            System.out.println();
        }


        // Polymorphism qua interface Connectable
        Connectable[] connectableDevices = {
            new SmartPhone(1, "iPhone", 80),
            new Laptop(2, "MacBook", 60),
            new TV(3, "Samsung", 55)
        };

        for (Connectable device : connectableDevices) {
            device.connect();
            device.disconnect();
        }


        // Polymorphism qua interface Chargeable
        Chargeable[] chargeableDevices = {
            new SmartPhone(1, "iPhone", 80),
            new Laptop(2, "MacBook", 60)
        };

        for (Chargeable device : chargeableDevices) {
            device.charge();
        }
    }
}


// ==================== INTERFACES ====================

interface Connectable {
    void connect();
    void disconnect();
}

interface Chargeable {
    void charge();
}


// ==================== ABSTRACT CLASS ====================

abstract class Device {
    private int id;
    private String name;

    public Device(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public abstract void showInfo();

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}


// ==================== SMARTPHONE ====================

class SmartPhone extends Device implements Connectable, Chargeable {

    private int battery;

    public SmartPhone(int id, String name, int battery) {
        super(id, name);
        this.battery = battery;
    }

    @Override
    public void showInfo() {
        System.out.println("Smart Phone: " + getName());
        System.out.println("Battery: " + battery + "%");
    }

    @Override
    public void connect() {
        System.out.println(getName() + " is connected.");
    }

    @Override
    public void disconnect() {
        System.out.println(getName() + " is disconnected.");
    }

    @Override
    public void charge() {
        battery = Math.min(100, battery + 10);

        System.out.println(
            getName() + " is charging. Battery: " + battery + "%"
        );
    }
}


// ==================== LAPTOP ====================

class Laptop extends Device implements Connectable, Chargeable {

    private int battery;

    public Laptop(int id, String name, int battery) {
        super(id, name);
        this.battery = battery;
    }

    @Override
    public void showInfo() {
        System.out.println("Laptop: " + getName());
        System.out.println("Battery: " + battery + "%");
    }

    @Override
    public void connect() {
        System.out.println(getName() + " is connected.");
    }

    @Override
    public void disconnect() {
        System.out.println(getName() + " is disconnected.");
    }

    @Override
    public void charge() {
        battery = Math.min(100, battery + 20);

        System.out.println(
            getName() + " is charging. Battery: " + battery + "%"
        );
    }
}


// ==================== TV ====================

class TV extends Device implements Connectable {

    private int screenSize;

    public TV(int id, String name, int screenSize) {
        super(id, name);
        this.screenSize = screenSize;
    }

    @Override
    public void showInfo() {
        System.out.println("TV: " + getName());
        System.out.println("Screen Size: " + screenSize + " inch");
    }

    @Override
    public void connect() {
        System.out.println(getName() + " is connected.");
    }

    @Override
    public void disconnect() {
        System.out.println(getName() + " is disconnected.");
    }
}
