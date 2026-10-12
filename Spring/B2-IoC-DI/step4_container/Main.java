import java.util.HashMap;
import java.util.Map;

class MiniContainer {

    private Map<String, Object> beans = new HashMap<>();

    public void addBean(String name, Object bean) {
        beans.put(name, bean);
    }

    public Object getBean(String name) {
        return beans.get(name);
    }
}

interface MessageSender {
    void send(String message);
}

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

class TelegramSender implement MessageSender {
    public void send(String message) {
        System.out.println("[Telegram] " + message);
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
    public static void main(String[] args) {
        MiniContainer container = new MiniContainer();

        container.addBean("sender", new ZaloSender());

        container.addBean(
            "jobService",
            new JobService((MessageSender) container.getBean("sender"))
        );

        JobService jobService =
            (JobService) container.getBean("jobService");

        jobService.approveJob();

        JobService again =
            (JobService) container.getBean("jobService");

        System.out.println("Cùng một đối tượng? " + (jobService == again));
    }
}