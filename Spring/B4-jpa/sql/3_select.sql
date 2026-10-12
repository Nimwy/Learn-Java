-- Phần 3 — Truy vấn

-- Lọc nhiều điều kiện, sắp xếp
SELECT title, salary_max
FROM job
WHERE status = 'approved' AND salary_max >= 2000
ORDER BY salary_max DESC;

-- Tìm theo chuỗi, không phân biệt hoa thường
SELECT title FROM job WHERE title ILIKE '%java%';

-- Job không công khai lương. Dùng IS NULL, KHÔNG dùng = NULL
SELECT title FROM job WHERE salary_max IS NULL;

-- Thử = NULL: không ra dòng nào (NULL nghĩa là "không biết", không bằng gì cả)
SELECT title FROM job WHERE salary_max = NULL;

-- Phân trang: mỗi trang 3 job, mới nhất trước
SELECT id, title, created_at FROM job ORDER BY created_at DESC LIMIT 3 OFFSET 0;  -- trang 1
SELECT id, title, created_at FROM job ORDER BY created_at DESC LIMIT 3 OFFSET 3;  -- trang 2
