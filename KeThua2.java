public class KeThua2 {
    public static void main(String[] args) {
        Payment[] payments = {
        new CashPayment(100),
        new CardPayment(200),
        new MomoPayment(150)
        };
        
        for (Payment payment : payments) {
            payment.pay();
        }
        
        for (Payment payment : payments) {
            payment.pay();
            System.out.println("Fee: " + payment.getFee());
        }
    }
}

class Payment {
    protected double amount;

    public Payment(double amount) {
        this.amount = amount;
    }

    public void pay() {
        System.out.println("Processing payment...");
    }

    public double getFee() {
        return 0;
    }
}

class CashPayment extends Payment {

    public CashPayment(double amount) {
        super(amount);
    }
    @Override 
    public void pay() {
        System.out.println("Paid " + amount + " by cash.");
    }
    
    
}

class CardPayment extends Payment {

    public CardPayment(double amount) {
        super(amount);
    }
    @Override 
    public void pay() {
        System.out.println("Paid " + amount + " by credit card.");
    }
    @Override
    public double getFee() {
        return amount * 0.02;
    }
}

class MomoPayment extends Payment {

    public MomoPayment(double amount) {
        super(amount);
    }
    @Override 
    public void pay() {
        System.out.println("Paid " + amount + " by Momo.");
    }
    @Override
    public double getFee() {
        return amount * 0.01;
    }
}