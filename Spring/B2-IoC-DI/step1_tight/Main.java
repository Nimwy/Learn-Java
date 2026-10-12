// 1. Class gửi email.
class EmailSender {
    public void send(String message) {
        System.out.println("[Email] " + message);
    }
}

class JobService {
    private EmailSender sender = new EmailSender();

    public void approveJob() {
        sender.send("Tin tuyển dụng đã được duyệt");
    }
}

public class Main {
    public static void main(String[] args) {
        JobService jobService = new JobService();
        jobService.approveJob();
    }
}