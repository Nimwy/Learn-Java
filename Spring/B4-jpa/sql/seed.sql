-- Bài 4.0 — Dữ liệu mẫu. Chạy SAU schema.sql.

INSERT INTO category (name, slug) VALUES
    ('Backend',  'backend'),    -- id 1
    ('Frontend', 'frontend'),   -- id 2
    ('Mobile',   'mobile'),     -- id 3
    ('DevOps',   'devops');     -- id 4 (chưa có job nào)

INSERT INTO tag (name, slug) VALUES
    ('Java',   'java'),     -- id 1
    ('Spring', 'spring'),   -- id 2
    ('React',  'react'),    -- id 3
    ('Kotlin', 'kotlin'),   -- id 4
    ('Docker', 'docker'),   -- id 5
    ('Python', 'python');   -- id 6 (chưa job nào dùng)

INSERT INTO job (title, category_id, salary_max, status, created_at) VALUES
    ('Java Developer',           1, 2000, 'approved', '2026-09-01 09:00'),  -- id 1
    ('Senior Java Developer',    1, 3000, 'approved', '2026-09-05 09:00'),  -- id 2
    ('Golang Developer',         1, 2500, 'approved', '2026-09-08 09:00'),  -- id 3
    ('Kotlin Backend Developer', 1, NULL, 'pending',  '2026-09-20 09:00'),  -- id 4
    ('ReactJS Developer',        2, 1500, 'approved', '2026-09-03 09:00'),  -- id 5
    ('Frontend Intern',          2,  400, 'approved', '2026-09-12 09:00'),  -- id 6
    ('VueJS Developer',          2, NULL, 'approved', '2026-09-18 09:00'),  -- id 7
    ('React Native Developer',   3, 1800, 'approved', '2026-09-10 09:00'),  -- id 8
    ('Android Developer',        3, 2200, 'pending',  '2026-09-22 09:00'),  -- id 9
    ('iOS Developer',            3, 2400, 'expired',  '2026-08-01 09:00'),  -- id 10
    ('Spring Boot Developer',    1, 1200, 'approved', '2026-09-25 09:00');  -- id 11

INSERT INTO job_tags (job_id, tag_id) VALUES
    (1, 1), (1, 2),           -- Java Developer: Java, Spring
    (2, 1), (2, 2),           -- Senior Java Developer: Java, Spring
    (3, 5),                   -- Golang Developer: Docker
    (4, 4),                   -- Kotlin Backend Developer: Kotlin
    (5, 3),                   -- ReactJS Developer: React
    (8, 3),                   -- React Native Developer: React
    (9, 4),                   -- Android Developer: Kotlin
    (11, 1), (11, 2), (11, 5);-- Spring Boot Developer: Java, Spring, Docker
