-- Phần 5 — Gom nhóm

-- Đếm job theo trạng thái
SELECT status, COUNT(*) AS so_job
FROM job
GROUP BY status
ORDER BY status;

-- Số job và lương trung bình theo danh mục (LEFT JOIN để giữ DevOps)
SELECT c.name AS danh_muc, COUNT(j.id) AS so_job, ROUND(AVG(j.salary_max)) AS luong_tb
FROM category c
LEFT JOIN job j ON j.category_id = c.id
GROUP BY c.name
ORDER BY so_job DESC, c.name;

-- HAVING: lọc NHÓM sau khi gom (WHERE thì lọc DÒNG trước khi gom)
SELECT c.name AS danh_muc, COUNT(j.id) AS so_job
FROM category c
JOIN job j ON j.category_id = c.id
WHERE j.status = 'approved'
GROUP BY c.name
HAVING COUNT(j.id) >= 3;
