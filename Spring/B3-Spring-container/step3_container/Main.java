import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

// ===== 1. Tự tạo nhãn @Component =====
// RUNTIME = giữ nhãn lại lúc chạy để container đọc được
@Retention(RetentionPolicy.RUNTIME)
@interface Component {}

// ===== 2. Interface =====
interface MessageSender {
    void send(String message);
}

// ===== 3. Các cách gửi =====
class EmailSender implements MessageSender {
    public void send(String message) {
        System.out.println("[EMAIL]" + message);
    }
}

@Component
class ZaloSender implements MessageSender {
    public void send(String message) {
        System.out.println("[ZALO] " + message);
    }
}

// ===== 4. JobService (có nhãn) =====
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

// ===== 5. MiniContainer: tự đọc nhãn và tự new =====
class MiniContainer {
    private List<Object> beans = new ArrayList<>();

    public MiniContainer(Class<?>... classes) throws Exception {
        for (Class<?> c : classes) {
            // B1. Không có nhãn @Component → bỏ qua                (Bước 1)
            if (!c.isAnnotationPresent(Component.class)) {
                continue;
            }

            // B2. Xem constructor cần những tham số kiểu gì         (Bước 2)
            Constructor<?> constructor = c.getDeclaredConstructors()[0];
            Class<?>[] paramTypes = constructor.getParameterTypes();
            
            // B3. Lấy bean phù hợp cho từng tham số
            Object[] params = new Object[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i++) {
                params[i] = getBean(paramTypes[i]);
            }

            // B4. Gọi constructor (tương đương new ...(params))    (Bước 2)
            Object bean = constructor.newInstance(params);
            beans.add(bean);
            System.out.println("Tạo bean: " + c.getSimpleName());
        }
    }

    // Tìm bean theo kiểu (kiểu có thể là interface)
    public Object getBean(Class<?> type) {
        for (Object bean : beans) {
            if (type.isInstance(bean)) {
                return bean;
            }
        }
        throw new RuntimeException("Không có bean kiểu " + type.getSimpleName());
    }
}

// ===== 6. Main: không còn chữ new nào cho JobService hay ZaloSender =====
public class Main {
    public static void main(String[] args) throws Exception {
        // Đưa danh sách class cho container (Spring thì tự tìm trong package)
        // Lưu ý: class được dùng làm phụ thuộc phải đứng trước
        MiniContainer container = new MiniContainer(EmailSender.class, ZaloSender.class, JobService.class);

        JobService jobService = (JobService) container.getBean(JobService.class);
        jobService.approveJob();
    }
}