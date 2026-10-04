
package dao;

import config.Database;
import model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CategoryDao {

    private Category mapRow(ResultSet rs) throws SQLException {
        return new Category(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("slug")
        );
    }

    public ArrayList<Category> findAll() {
        ArrayList<Category> list = new ArrayList<>();
        String sql = "SELECT id, name, slug FROM category ORDER BY id";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (SQLException e) {
            System.out.println("Lỗi findAll categories: " + e.getMessage());
        }

        return list;
    }

    public Category findById(long id) {
        String sql = "SELECT id, name, slug FROM category WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Lỗi findById category: " + e.getMessage());
        }

        return null;
    }

    public boolean insert(Category category) {
        String sql = "INSERT INTO category (name, slug) VALUES (?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 sql, Statement.RETURN_GENERATED_KEYS
             )) {

            ps.setString(1, category.getName());
            ps.setString(2, category.getSlug());

            int rows = ps.executeUpdate();

            if (rows == 0) {
                return false;
            }

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    category.setId(keys.getLong("id"));
                    return true;
                }
            }

            return false;

        } catch (SQLException e) {
            System.out.println("Lỗi insert category: " + e.getMessage());
            return false;
        }
    }

    public boolean update(Category category) {
        String sql = "UPDATE category SET name = ?, slug = ? WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, category.getName());
            ps.setString(2, category.getSlug());
            ps.setLong(3, category.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Lỗi update category: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(long id) {
        String sql = "DELETE FROM category WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Lỗi delete category: " + e.getMessage());
            return false;
        }
    }
}
