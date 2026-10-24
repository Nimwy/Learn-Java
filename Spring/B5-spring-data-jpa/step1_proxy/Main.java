import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

// 1. Interface: khong viet class implements
interface CategoryRepository {
    String findBySlug(String slug);

    String findByNameContaining(String text);

    String findByName(String name);
}

// 2. Handler: noi nhan moi loi goi phuong thuc
class PrintHandler implements InvocationHandler {

    @Override
    public Object invoke(
            Object proxy,
            Method method,
            Object[] args
    ) {
        System.out.println(
            "-> invoke() nhan: ham"
            + method.getName()
            + ", tham so"
            + Arrays.toString(args)
        );

        return "ket qua gia cua " + method.getName();
    }
}

// 3. Tao proxy va thu goi cac phuong thuc
public class Main {

    public static void main(String[] args) {

        CategoryRepository repository =
            (CategoryRepository) Proxy.newProxyInstance(
                CategoryRepository.class.getClassLoader(),
                new Class<?>[] { CategoryRepository.class },
                new PrintHandler()
            );
        
        System.out.println(repository.findBySlug("backend"));

        System.out.println(
            repository.findByNameContaining("end")
        );

        System.out.println(repository.findByName("Backend"));

        System.out.println(
            "Class thật của repository: "
            + repository.getClass().getName()
        );
    }        
}
