import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// ===== 1. Class Category =====
class Category {
    private Long id;
    private String name;
    private String slug;

    public Category() {}

    public Category(String name, String slug) {
        this.name = name;
        this.slug = slug;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    @Override
    public String toString() {
        return "Category{id=" + id + ", name=" + name
                + ", slug=" + slug + "}";
    }
}

// ===== 2. CategoryDao: MỌI câu SQL của Category nằm ở đây =====
class CategoryDao {
    private Connection conn;

    public CategoryDao(Connection conn) {
        this.conn = conn;
    }

    public void createTable() throws SQLException {
        conn.createStatement().execute(
            "DROP TABLE IF EXISTS category CASCADE"
        );
        conn.createStatement().execute(
            "CREATE TABLE category (id BIGSERIAL PRIMARY KEY, "
            + "name VARCHAR(100), slug VARCHAR(100))"
        );
    }

    public void save(Category c) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
            "INSERT INTO category (name, slug) VALUES (?, ?)"
        );
        ps.setString(1, c.getName());
        ps.setString(2, c.getSlug());
        ps.executeUpdate();
    }

    public List<Category> findAll() throws SQLException {
        ResultSet rs = conn.createStatement().executeQuery(
            "SELECT id, name, slug FROM category"
        );

        List<Category> categories = new ArrayList<>();

        while (rs.next()) {
            categories.add(mapRow(rs));
        }

        return categories;
    }

    public Category findById(long id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
            "SELECT id, name, slug FROM category WHERE id = ?"
        );
        ps.setLong(1, id);

        ResultSet rs = ps.executeQuery();

        return rs.next() ? mapRow(rs) : null;
    }

    // Chuyển 1 dòng thành 1 Category — viết MỘT lần, dùng chung
    private Category mapRow(ResultSet rs) throws SQLException {
        Category c = new Category();
        c.setId(rs.getLong("id"));
        c.setName(rs.getString("name"));
        c.setSlug(rs.getString("slug"));
        return c;
    }
}

// ===== 3. Main: chỉ gọi hàm, không còn thấy SQL =====
public class Main {
    static final String URL =
        "jdbc:postgresql://localhost:5433/remoteit";
    static final String USER = "postgres";
    static final String PASSWORD = "postgres";

    public static void main(String[] args) throws Exception {
        try (Connection conn =
                 DriverManager.getConnection(URL, USER, PASSWORD)) {

            CategoryDao dao = new CategoryDao(conn);
            dao.createTable();

            dao.save(new Category("Backend", "backend"));
            dao.save(new Category("Frontend", "frontend"));

            System.out.println("--- findAll ---");
            for (Category c : dao.findAll()) {
                System.out.println(c);
            }

            System.out.println("--- findById(2) ---");
            System.out.println(dao.findById(2));
        }
    }
}