
package dao;

import config.Database;
import model.Job;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class JobDao {

    private Job mapRow(ResultSet rs) throws SQLException {
        return new Job(
            rs.getLong("id"),
            rs.getLong("category_id"),
            rs.getString("title"),
            rs.getBigDecimal("salary"),
            rs.getString("status")
        );
    }

    // 1. Lấy tất cả jobs
    public ArrayList<Job> findAll() {
        ArrayList<Job> list = new ArrayList<>();

        String sql = """
            SELECT id, category_id, title, salary, status
            FROM jobs
            ORDER BY id
            """;

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (SQLException e) {
            System.out.println("Lỗi findAll jobs: " + e.getMessage());
        }

        return list;
    }

    // 2. Tìm job theo ID
    public Job findById(long id) {
        String sql = """
            SELECT id, category_id, title, salary, status
            FROM jobs
            WHERE id = ?
            """;

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Lỗi findById job: " + e.getMessage());
        }

        return null;
    }

    // 3. Tìm jobs theo category ID
    public ArrayList<Job> findByCategory(long categoryId) {
        ArrayList<Job> list = new ArrayList<>();

        String sql = """
            SELECT id, category_id, title, salary, status
            FROM jobs
            WHERE category_id = ?
            ORDER BY id
            """;

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, categoryId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Lỗi findByCategory: " + e.getMessage());
        }

        return list;
    }

    // 4. Thêm job
    public boolean insert(Job job) {
        String sql = """
            INSERT INTO jobs (category_id, title, salary, status)
            VALUES (?, ?, ?, ?)
            """;

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, job.getCategoryId());
            ps.setString(2, job.getTitle());
            ps.setBigDecimal(3, job.getSalary());
            ps.setString(4, job.getStatus());

            int rows = ps.executeUpdate();

            if (rows == 0) {
                return false;
            }

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    job.setId(keys.getLong("id"));
                }
            }

            return true;

        } catch (SQLException e) {
            System.out.println("Lỗi insert job: " + e.getMessage());
            return false;
        }
    }

    // 5. Cập nhật trạng thái job
    public boolean updateStatus(long id, String status) {
        String sql = """
            UPDATE jobs
            SET status = ?
            WHERE id = ?
            """;

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setLong(2, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Lỗi updateStatus job: " + e.getMessage());
            return false;
        }
    }

    // 6. Xóa job
    public boolean delete(long id) {
        String sql = "DELETE FROM jobs WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Lỗi delete job: " + e.getMessage());
            return false;
        }
    }

    // 7. JOIN jobs với category, trả về danh sách chuỗi
    public ArrayList<String> findAllWithCategoryName() {
        ArrayList<String> list = new ArrayList<>();

        String sql = """
            SELECT c.name AS category_name, j.title, j.status
            FROM jobs j
            JOIN category c ON c.id = j.category_id
            ORDER BY c.name, j.title
            """;

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String categoryName = rs.getString("category_name");
                String title = rs.getString("title");
                String status = rs.getString("status");

                list.add(categoryName + " | " + title + " | " + status);
            }

        } catch (SQLException e) {
            System.out.println(
                "Lỗi findAllWithCategoryName: " + e.getMessage()
            );
        }

        return list;
    }
}
