import model.Category;
import model.Job;
import service.CategoryService;
import service.JobService;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {
        CategoryService categoryService = new CategoryService();
        JobService jobService = new JobService();

        // 1. Lấy tất cả category
        System.out.println("===== TAT CA CATEGORY =====");

        for (Category category : categoryService.getAllCategories()) {
            System.out.println(
                category.getId() + " | " +
                category.getName() + " | " +
                category.getSlug()
            );
        }

        // 2. Tìm category theo ID
        System.out.println("\n===== TIM CATEGORY ID = 1 =====");

        Category category = categoryService.getCategoryById(1L);

        if (category != null) {
            System.out.println(
                category.getId() + " | " +
                category.getName() + " | " +
                category.getSlug()
            );
        } else {
            System.out.println("Khong tim thay category");
        }

        // 3. Lấy tất cả job
        System.out.println("\n===== TAT CA JOBS =====");

        for (Job job : jobService.getAllJobs()) {
            printJob(job);
        }

        // 4. Tìm job theo ID
        System.out.println("\n===== TIM JOB ID = 1 =====");

        Job foundJob = jobService.getJobById(1L);

        if (foundJob != null) {
            printJob(foundJob);
        } else {
            System.out.println("Khong tim thay job");
        }

        // 5. Lấy job theo category
        System.out.println("\n===== JOBS CUA CATEGORY ID = 1 =====");

        for (Job job : jobService.getJobsByCategory(1L)) {
            printJob(job);
        }

        // 6. JOIN job với tên category
        System.out.println("\n===== JOBS JOIN CATEGORY =====");

        for (String item : jobService.getAllJobsWithCategoryName()) {
            System.out.println(item);
        }

        // 7. Thử tạo job mới thông qua Service
        System.out.println("\n===== TAO JOB TEST =====");

        String testTitle = "Service Test Job "
                + System.currentTimeMillis();

        boolean created = jobService.createJob(
            1L,
            testTitle,
            new BigDecimal("1200.00"),
            "draft"
        );

        System.out.println("Tao job thanh cong: " + created);

        // Tìm job vừa tạo để lấy ID
        long testJobId = -1L;

        if (created) {
            for (Job job : jobService.getAllJobs()) {
                if (testTitle.equals(job.getTitle())) {
                    testJobId = job.getId();
                    printJob(job);
                    break;
                }
            }
        }

        // 8. Cập nhật trạng thái job thử nghiệm
        if (testJobId > 0) {
            System.out.println("\n===== CAP NHAT STATUS =====");

            boolean updated = jobService.updateJobStatus(
                testJobId,
                "pending"
            );

            System.out.println("Cap nhat thanh cong: " + updated);

            Job updatedJob = jobService.getJobById(testJobId);

            if (updatedJob != null) {
                printJob(updatedJob);
            }

            // 9. Xóa job thử nghiệm
            System.out.println("\n===== XOA JOB TEST =====");

            boolean deleted = jobService.deleteJob(testJobId);

            System.out.println("Xoa job thanh cong: " + deleted);
        }

        // 10. Kiểm tra Service từ chối dữ liệu không hợp lệ
        System.out.println("\n===== KIEM TRA DU LIEU KHONG HOP LE =====");

        boolean invalidJobCreated = jobService.createJob(
            -1L,
            "Invalid Test Job",
            new BigDecimal("1000.00"),
            "draft"
        );

        System.out.println(
            "Tao job voi category ID = -1: " + invalidJobCreated
        );

        System.out.println("\n===== KET THUC KIEM THU =====");
    }

    private static void printJob(Job job) {
        System.out.println(
            job.getId() + " | " +
            job.getCategoryId() + " | " +
            job.getTitle() + " | " +
            job.getSalary() + " | " +
            job.getStatus()
        );
    }
}
