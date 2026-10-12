-- Phần 6 — Transaction và Index

-- ===== Transaction: thành công hết, hoặc không làm gì =====
SELECT COUNT(*) AS so_job_truoc FROM job;

BEGIN;
INSERT INTO job (title, category_id, salary_max) VALUES ('Spring Developer', 1, 1800);
INSERT INTO job_tags (job_id, tag_id) VALUES (lastval(), 999);   -- tag 999 không tồn tại → LỖI
SELECT COUNT(*) FROM job;                                         -- transaction đã hỏng, lệnh nào cũng bị từ chối
ROLLBACK;                                                         -- hủy cả lệnh INSERT job ở trên

SELECT COUNT(*) AS so_job_sau FROM job;                           -- vẫn như cũ

-- ===== Index: tìm nhanh hơn trên bảng lớn =====
-- Tạo bảng tạm 100.000 dòng (generate_series sinh dãy số 1..100000)
CREATE TABLE job_lon AS
SELECT g AS id, 'Job ' || g AS title
FROM generate_series(1, 100000) AS g;
ANALYZE job_lon;                                                  -- cập nhật thống kê cho DB

EXPLAIN SELECT * FROM job_lon WHERE title = 'Job 50000';          -- chưa có index: Seq Scan (quét cả bảng)

CREATE INDEX idx_job_lon_title ON job_lon (title);

EXPLAIN SELECT * FROM job_lon WHERE title = 'Job 50000';          -- có index: Index Scan (đi thẳng tới dòng cần)

DROP TABLE job_lon;
