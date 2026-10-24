import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// 1. Class Category đại diện cho dữ liệu danh mục
class Category {
    private Long id;
    private String name;
    private String slug;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    @Override
    public String toString() {
        return "Category{id=" + id + ", name=" + name + ", slug=" + slug + "}";
    }
}

// ===== 2. Main: kết nối DB, thêm và đọc dữ liệu =====
public class Main {
    static final String URL = "jdbc:postgresql://localhost:5433/remoteit";
    static final String USER = "postgres";
    static final String PASSWORD = "postgres";

    public static void main(String[] args) throws Exception {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            // Chuẩn bị bảng cho bài học
            Statement statement = conn.createStatement();

            statement.execute("DROP TABLE IF EXISTS category CASCADE");
            statement.execute("CREATE TABLE category ("
                                + "id BIGSERIAL PRIMARY KEY, "
                                + "name VARCHAR(100), "
                                + "slug VARCHAR(100))");

            // Thêm dữ liệu
            PreparedStatement insert = conn.prepareStatement(
                "INSERT INTO category (name, slug) VALUES (?, ?)"
            );

            insert.setString(1, "Backend");
            insert.setString(2, "backend");
            insert.executeUpdate();

            insert.setString(1, "Frontend");
            insert.setString(2, "frontend");
            insert.executeUpdate();

            // Đọc dữ liệu
            ResultSet rs = statement.executeQuery(
                    "SELECT id, name, slug FROM category");

            List<Category> categories = new ArrayList<>();

            while (rs.next()) {
                Category c = new Category();

                c.setId(rs.getLong("id"));
                c.setName(rs.getString("name"));
                c.setSlug(rs.getString("slug"));

                categories.add(c);
            }

            for (Category c : categories) {
                System.out.println(c);
            }
        }
    }
}