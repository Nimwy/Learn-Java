// ===== 1. Interface =====
interface MessageSender {
    void send(String message);
}

// ===== 2. Các cách gửi =====
class EmailSender implements MessageSender {
    public void send(String message) {
        System.out.println("[EMAIL] " + message);
    }
}

class ZaloSender implements MessageSender {
    public void send(String message) {
        System.out.println("[ZALO] " + message);
    }
}

class TelegramSender implements MessageSender {
    public void send(String message) {
        System.out.println("[Telegram] " + message);
    }
}

class FakeSender implements MessageSender {
    private String lastMessage;

    public void send(String message) {
        lastMessage = message;
    }

    public String getLastMessage() {
        return lastMessage;
    }
}

// ===== 3. JobService NHẬN sender qua constructor (DI) =====
class JobService {
    private MessageSender sender;

    public JobService(MessageSender sender) { // không tự new nữa
        this.sender = sender;
    }

    public void approveJob() {
        sender.send("Tin tuyển dụng đã được duyệt");
    }
}

// ===== 4. Main: tạo sender rồi truyền vào JobService =====
public class Main {
    public static void main(String[] args) {
        FakeSender fakeSender = new FakeSender();

        JobService jobService = new JobService(fakeSender);

        jobService.approveJob();

        System.out.println(fakeSender.getLastMessage());
    }
}