import java.lang.reflect.Constructor;

interface MessageSender {
    void send(String message);
}

class ZaloSender implements MessageSender {
    public void send(String message) {
        System.out.println("[ZALO] " + message);
    }
}

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
    public static void main(String[] args) throws Exception {
        // Cách quen thuộc: new
        MessageSender sender1 = new ZaloSender();
        sender1.send("Tạo bằng new");

        // Cách reflection: lấy constructor từ class, rồi gọi nó
        Constructor<?> zaloConstructor = ZaloSender.class.getDeclaredConstructors()[0];
        MessageSender sender2 = (MessageSender) zaloConstructor.newInstance();
        sender2.send("Tạo bằng reflection");

        // Constructor có tham số: hỏi xem nó cần kiểu gì
        Constructor<?> jobConstructor = JobService.class.getDeclaredConstructors()[0];
        Class<?>[] paramTypes = jobConstructor.getParameterTypes();
        System.out.println("JobService cần: " + paramTypes[0].getSimpleName());

        // Truyền tham số vào khi gọi constructor (giống new JobService(sender2))
        JobService jobService = (JobService) jobConstructor.newInstance(sender2);
        jobService.approveJob();
    }
}