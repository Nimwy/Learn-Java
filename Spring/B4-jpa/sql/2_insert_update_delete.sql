-- Phần 2 — Thêm, sửa, xóa. Cuối file xóa lại những gì đã thêm, dữ liệu trở về như seed.sql.

-- Thêm 1 dòng, RETURNING trả về id DB vừa tạo
INSERT INTO category (name, slug) VALUES ('Data / AI', 'data-ai') RETURNING id;

-- Thêm job vào danh mục vừa tạo (tìm id theo slug, không cần nhớ số)
INSERT INTO job (title, category_id, salary_max)
VALUES ('Data Engineer', (SELECT id FROM category WHERE slug = 'data-ai'), 2600)
RETURNING id, title, status;          -- status tự lấy DEFAULT 'pending'

-- Sửa: Admin duyệt tin. LUÔN có WHERE, nếu không sẽ sửa CẢ BẢNG
UPDATE job SET status = 'approved' WHERE title = 'Data Engineer';

-- Vi phạm ràng buộc: DB từ chối
INSERT INTO category (name, slug) VALUES ('Backend 2', 'backend');            -- trùng slug (UNIQUE)
INSERT INTO job (title, category_id) VALUES ('Job lạc', 99);                  -- category 99 không tồn tại (khóa ngoại)
INSERT INTO job (category_id) VALUES (1);                                      -- thiếu title (NOT NULL)

-- Xóa: xóa job trước, rồi mới xóa được category (vì job đang trỏ tới category)
DELETE FROM job WHERE title = 'Data Engineer';
DELETE FROM category WHERE slug = 'data-ai';
