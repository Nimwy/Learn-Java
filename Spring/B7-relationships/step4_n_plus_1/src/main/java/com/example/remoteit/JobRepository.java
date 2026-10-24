package com.example.remoteit;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JobRepository extends JpaRepository<Job, Long> {

    // JOIN FETCH: lấy job KÈM category và tags trong MỘT câu SQL (tránh N+1).
    // Đây là JPQL (viết theo TÊN ENTITY: Job, j.category, j.tags), không phải SQL.
    //   - JOIN FETCH j.category   : tải sẵn category (nếu không có, mỗi job là 1 câu SELECT category)
    //   - LEFT JOIN FETCH j.tags  : tải sẵn tags; LEFT để job không có tag vẫn hiện ra
    //   - DISTINCT                : vì join với tags làm job bị lặp dòng, DISTINCT gộp lại
    // Hibernate dịch câu này thành SQL:
    //   SELECT DISTINCT j.*, c.*, t.*
    //   FROM job j
    //   JOIN      category c  ON c.id = j.category_id
    //   LEFT JOIN job_tags jt ON jt.job_id = j.id
    //   LEFT JOIN tag      t  ON t.id = jt.tag_id
    //   ORDER BY j.id
    @Query("SELECT DISTINCT j FROM Job j JOIN FETCH j.category LEFT JOIN FETCH j.tags ORDER BY j.id")
    List<Job> findAllWithCategoryAndTags();
}