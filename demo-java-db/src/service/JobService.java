package service;

import dao.CategoryDao;
import dao.JobDao;
import model.Category;
import model.Job;

import java.math.BigDecimal;
import java.util.ArrayList;

public class JobService {

    private final JobDao jobDao = new JobDao();
    private final CategoryDao categoryDao = new CategoryDao();

    // 1. Lấy tất cả công việc
    public ArrayList<Job> getAllJobs() {
        return jobDao.findAll();
    }

    // 2. Tìm công việc theo ID
    public Job getJobById(long id) {
        if (id <= 0) {
            System.out.println("ID job khong hop le");
            return null;
        }

        return jobDao.findById(id);
    }

    // 3. Lấy công việc theo danh mục
    public ArrayList<Job> getJobsByCategory(long categoryId) {
        if (categoryId <= 0) {
            System.out.println("ID category khong hop le");
            return new ArrayList<>();
        }

        return jobDao.findByCategory(categoryId);
    }

    // 4. Tạo công việc
    public boolean createJob(
            long categoryId,
            String title,
            BigDecimal salary,
            String status) {

        if (categoryId <= 0) {
            System.out.println("ID category khong hop le");
            return false;
        }

        if (title == null || title.isBlank()) {
            System.out.println("Tieu de job khong duoc de trong");
            return false;
        }

        if (salary != null && salary.compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("Luong khong duoc am");
            return false;
        }

        if (status == null || status.isBlank()) {
            System.out.println("Status khong duoc de trong");
            return false;
        }

        // Kiểm tra danh mục có tồn tại không
        Category category = categoryDao.findById(categoryId);

        if (category == null) {
            System.out.println("Category khong ton tai");
            return false;
        }

        Job job = new Job(
            0L,
            categoryId,
            title.trim(),
            salary,
            status.trim()
        );

        return jobDao.insert(job);
    }

    // 5. Cập nhật trạng thái
    public boolean updateJobStatus(long id, String status) {
        if (id <= 0) {
            System.out.println("ID job khong hop le");
            return false;
        }

        if (status == null || status.isBlank()) {
            System.out.println("Status khong duoc de trong");
            return false;
        }

        // Kiểm tra job có tồn tại không
        Job job = jobDao.findById(id);

        if (job == null) {
            System.out.println("Job khong ton tai");
            return false;
        }

        return jobDao.updateStatus(id, status.trim());
    }

    // 6. Xóa công việc
    public boolean deleteJob(long id) {
        if (id <= 0) {
            System.out.println("ID job khong hop le");
            return false;
        }

        return jobDao.delete(id);
    }

    // 7. Lấy danh sách job kèm tên danh mục
    public ArrayList<String> getAllJobsWithCategoryName() {
        return jobDao.findAllWithCategoryName();
    }
}