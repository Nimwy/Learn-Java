import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


// ===== 1. Tự tạo 2 annotation =====
@Retention(RetentionPolicy.RUNTIME)
@interface Table {
    String value();
}

@Retention(RetentionPolicy.RUNTIME)
@interface Id {}

// ===== 2. Category =====
@Table("category")
class Category {
    @Id
    private Long id;
    private String name;
    private String slug;

    public Category() {}

    public Category(String name, String slug) {
        this.name = name;
        this.slug = slug;
    }

    @Override
    public String toString() {
        return "Category{id=" + id + ", name=" + name
                + ", slug=" + slug + "}";
    }
}

// ===== 3. Tag =====
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
        return "Tag{id=" + id + ", name=" + name
                + ", slug=" + slug + "}";
    }
}

// ===== 4. MiniOrm: dùng chung cho nhiều class =====
class MiniOrm {
    private Connection conn;

    public MiniOrm(Connection conn) {
        this.conn = conn;
    }

    // Lưu bất kỳ object nào có @Table
    public void save(Object entity) throws Exception {
        Class<?> c = entity.getClass();

        String table = c.getAnnotation(Table.class).value();

        List<String> columns = new ArrayList<>();
        List<Object> values = new ArrayList<>();

        for (Field f : c.getDeclaredFields()) {
            if (f.isAnnotationPresent(Id.class)) {
                continue;
            }

            f.setAccessible(true);
            columns.add(f.getName());
            values.add(f.get(entity));
        }

        String sql = "INSERT INTO " + table + " ("
                + String.join(", ", columns) + ")"
                + " VALUES ("
                + "?, ".repeat(columns.size() - 1) + "?)";

        System.out.println("SQL tự sinh: " + sql);

        PreparedStatement ps = conn.prepareStatement(sql);

        for (int i = 0; i < values.size(); i++) {
            ps.setObject(i + 1, values.get(i));
        }

        ps.executeUpdate();
    }

    // Đọc tất cả dòng và chuyển thành object
    public List<Object> findAll(Class<?> c) throws Exception {
        String table = c.getAnnotation(Table.class).value();

        String sql = "SELECT * FROM " + table;
        System.out.println("SQL tự sinh: " + sql);

        ResultSet rs = conn.createStatement().executeQuery(sql);

        List<Object> result = new ArrayList<>();

        while (rs.next()) {
            Object obj = c.getDeclaredConstructor().newInstance();

            for (Field f : c.getDeclaredFields()) {
                f.setAccessible(true);
                f.set(obj, rs.getObject(f.getName()));
            }

            result.add(obj);
        }

        return result;
    }
}


// ===== 5. Main =====
public class Main {
    static final String URL =
        "jdbc:postgresql://localhost:5433/remoteit";
    static final String USER = "postgres";
    static final String PASSWORD = "postgres";

    public static void main(String[] args) throws Exception {
        try (Connection conn =
                 DriverManager.getConnection(URL, USER, PASSWORD)) {

            // Chuẩn bị bảng cho bài học
            conn.createStatement().execute(
                "DROP TABLE IF EXISTS category CASCADE"
            );
            conn.createStatement().execute(
                "CREATE TABLE category (id BIGSERIAL PRIMARY KEY, "
                + "name VARCHAR(100), slug VARCHAR(100))"
            );

            conn.createStatement().execute(
                "DROP TABLE IF EXISTS tag CASCADE"
            );
            conn.createStatement().execute(
                "CREATE TABLE tag (id BIGSERIAL PRIMARY KEY, "
                + "name VARCHAR(100), slug VARCHAR(100))"
            );

            MiniOrm orm = new MiniOrm(conn);

            // Dùng cùng một MiniOrm cho hai class
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