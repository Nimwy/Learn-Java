-- Phần 4 — JOIN

-- INNER JOIN: job kèm tên danh mục (chỉ lấy dòng khớp ở CẢ HAI bảng)
SELECT j.title, c.name AS danh_muc
FROM job j
JOIN category c ON c.id = j.category_id
WHERE j.status = 'approved'
ORDER BY c.name, j.title;

-- LEFT JOIN: giữ MỌI dòng bảng bên trái (category), kể cả danh mục chưa có job
SELECT c.name AS danh_muc, j.title
FROM category c
LEFT JOIN job j ON j.category_id = c.id
WHERE c.slug IN ('mobile', 'devops')
ORDER BY c.name, j.title;

-- Nối 3 bảng qua bảng trung gian job_tags: job và các tag của nó
SELECT j.title, t.name AS tag
FROM job j
JOIN job_tags jt ON jt.job_id = j.id
JOIN tag t       ON t.id = jt.tag_id
WHERE j.id IN (1, 11)
ORDER BY j.id, t.name;
