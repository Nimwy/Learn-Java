public class EnumEx {
    public static void main(String[] args) {
        System.err.println("");
        OrderStatus status = OrderStatus.SHIPPED;
        switch (status) {
            case PENDING:
                System.out.println("Order is waiting.");
                break;
            case PROCESSING:
                System.out.println("Order is processed.");
                break;
            case SHIPPED:
                System.out.println("Order is shipped.");
                break;
            case DELIVERED:
                System.out.println("Order is delivered.");
                break;
            case CANCELLED:
                System.out.println("Order is cancelled.");
                break;
        }

        OrderStatus status2 = OrderStatus.DELIVERED;
        System.out.println(status2 == OrderStatus.DELIVERED);

        System.out.println(status2);
    }
}

enum OrderStatus {
    PENDING,
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELLED
}
