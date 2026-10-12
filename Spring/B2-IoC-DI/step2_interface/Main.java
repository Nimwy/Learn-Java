// ===== 1. Interface =====
interface MessageSender {
    void send(String message);
}

// ===== 2. Các cách gửi =====
class EmailSender implements MessageSender {
    public void send(String message) {
        System.out.println("[Email] " + message);
    }
}

class ZaloSender implements MessageSender {
    public void send(String message) {
        System.out.println("[Zalo] " + message);
    }
}

// ===== 3. JobService vẫn TỰ NEW =====
class JobService {
    private MessageSender sender = new ZaloSender();

    public void approveJob() {
        sender.send("Tin tuyển dụng đã được duyệt");
    }
}

// ===== 4. Main: chạy chương trình =====
public class Main {
    public static void main(String[] args) {
        JobService jobService = new JobService();
        jobService.approveJob();
    }
}