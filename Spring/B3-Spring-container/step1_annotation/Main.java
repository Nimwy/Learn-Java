import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// 1. Tự tạo nhãn @Component
@Retention(RetentionPolicy.RUNTIME)
@interface Component {}

// 2. Interface
interface MessageSender {
    void send(String message);
}

// 3. Gắn nhãn lên một số class
class EmailSender implements MessageSender {
    public void send(String message) {
        System.out.println("[EMAIL]" + message);
    }
}

@Component
class ZaloSender implements MessageSender {
    public void send(String message) {
        System.out.println("[ZALO]" + message);
    }
}

@Component
class JobService {
    private MessageSender sender;
    
    public JobService(MessageSender sender) {
        this.sender = sender;
    }

    public void approveJob() {
        sender.send("Tin tuyển dụng đã được duyệt");
    }
}

public class Main {
    public static void main(String[] args) {
        Class<?>[] classes = {
            EmailSender.class,
            ZaloSender.class,
            JobService.class
        };

        for (Class<?> c : classes) {
            boolean hasLabel =
                c.isAnnotationPresent(Component.class);
            
            System.out.println(
                c.getSimpleName() 
                + " có @Component?"
                + hasLabel
            );
        }
    }
}