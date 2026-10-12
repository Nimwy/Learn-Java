// BƯỚC 3: TỰ VIẾT MINI ORM — ĐỌC ANNOTATION + REFLECTION ĐỂ TỰ SINH SQL
// Chạy (Linux/Mac): javac Main.java && java -cp .:../lib/postgresql.jar Main
// Chạy (Windows):   javac Main.java && java -cp .;../lib/postgresql.jar Main

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

// ===== 1. Tự tạo 2 nhãn =====
@Retention(RetentionPolicy.RUNTIME)
@interface Table {
    String value(); // tên bảng, ví dụ @Table("category")
}

@Retention(RetentionPolicy.RUNTIME)
@interface Id {} // đánh dấu field khóa chính

// ===== 2. Category: chỉ gắn nhãn, KHÔNG viết SQL =====
@Table("category")
class Category {
    @Id
    private Long id;
    private String name;
    private String slug;

    public Category() {} // ORM cần constructor rỗng để tự tạo object

    public Category(String name, String slug) {
        this.name = name;
        this.slug = slug;
    }

    @Override
    public String toString() {
        return "Category{id=" + id + ", name=" + name + ", slug=" + slug + "}";
    }
}

// ===== 3. Tag: class MỚI, cũng chỉ gắn nhãn =====
@Table("tag")
class Tag {
    @Id
    private Long id;
    private String name;
    private String slug;

    public Tag() {}

    public Tag(String name, String slug) {
        this.name = name;
        this.slug = slug;
    }

    @Override
    public String toString() {
        return "Tag{id=" + id + ", name=" + name + ", slug=" + slug + "}";
    }
}

// ===== 4. MiniOrm: một class dùng cho MỌI entity =====
class MiniOrm {
    private Connection conn;

    public MiniOrm(Connection conn) {
        this.conn = conn;
    }

    // Lưu bất kỳ object nào có @Table
    public void save(Object entity) throws Exception {
        Class<?> c = entity.getClass();
        String table = c.getAnnotation(Table.class).value(); // đọc tên bảng từ nhãn

        // Đọc tên field và giá trị (bỏ qua @Id vì DB tự sinh)
        List<String> columns = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        for (Field f : c.getDeclaredFields()) {
            if (f.isAnnotationPresent(Id.class)) {
                continue;
            }
            f.setAccessible(true); // cho phép đọc field private
            columns.add(f.getName());
            values.add(f.get(entity));
        }

        // Tự ghép câu SQL: INSERT INTO category (name, slug) VALUES (?, ?)
        String sql = "INSERT INTO " + table + " (" + String.join(", ", columns) + ")"
                + " VALUES (" + "?, ".repeat(columns.size() - 1) + "?)";
        System.out.println("SQL tự sinh: " + sql);

        PreparedStatement ps = conn.prepareStatement(sql);
        for (int i = 0; i < values.size(); i++) {
            ps.setObject(i + 1, values.get(i));
        }
        ps.executeUpdate();
    }

    // Đọc tất cả dòng của bảng, tự chuyển thành object
    public List<Object> findAll(Class<?> c) throws Exception {
        String table = c.getAnnotation(Table.class).value();
        String sql = "SELECT * FROM " + table;
        System.out.println("SQL tự sinh: " + sql);

        ResultSet rs = conn.createStatement().executeQuery(sql);
        List<Object> result = new ArrayList<>();
        while (rs.next()) {
            Object obj = c.getDeclaredConstructor().newInstance(); // gọi constructor rỗng
            for (Field f : c.getDeclaredFields()) {
                f.setAccessible(true);
                f.set(obj, rs.getObject(f.getName())); // cột cùng tên với field
            }
            result.add(obj);
        }
        return result;
    }
}

// ===== 5. Main =====
public class Main {
    static final String URL = "jdbc:postgresql://localhost:5433/remoteit";
    static final String USER = "postgres";
    static final String PASSWORD = "postgres";

    public static void main(String[] args) throws Exception {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // Chuẩn bị bảng (bước 4 sẽ thấy Hibernate tự làm việc này)
            conn.createStatement().execute("DROP TABLE IF EXISTS category CASCADE");
            conn.createStatement().execute("CREATE TABLE category (id BIGSERIAL PRIMARY KEY, name VARCHAR(100), slug VARCHAR(100))");
            conn.createStatement().execute("DROP TABLE IF EXISTS tag CASCADE");
            conn.createStatement().execute("CREATE TABLE tag (id BIGSERIAL PRIMARY KEY, name VARCHAR(100), slug VARCHAR(100))");

            MiniOrm orm = new MiniOrm(conn);

            // Cùng một MiniOrm, dùng cho 2 class khác nhau
            orm.save(new Category("Backend", "backend"));
            orm.save(new Category("Frontend", "frontend"));
            orm.save(new Tag("Java", "java"));

            for (Object o : orm.findAll(Category.class)) {
                System.out.println(o);
            }
            for (Object o : orm.findAll(Tag.class)) {
                System.out.println(o);
            }
        }
    }
}

// Kết quả:
// SQL tự sinh: INSERT INTO category (name, slug) VALUES (?, ?)
// SQL tự sinh: INSERT INTO category (name, slug) VALUES (?, ?)
// SQL tự sinh: INSERT INTO tag (name, slug) VALUES (?, ?)
// SQL tự sinh: SELECT * FROM category
// Category{id=1, name=Backend, slug=backend}
// Category{id=2, name=Frontend, slug=frontend}
// SQL tự sinh: SELECT * FROM tag
// Tag{id=1, name=Java, slug=java}
//
// Thêm Tag mà KHÔNG viết thêm dòng SQL nào. Đây chính là ý tưởng của ORM.
// Vấn đề còn lại: MiniOrm còn thiếu rất nhiều (update, delete, tìm theo điều kiện,
// tự tạo bảng, quan hệ giữa các bảng...). Không nên tự viết → dùng JPA.
