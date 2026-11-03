-- V2: Thêm cột created_at vào bảng sessions (phiên được tạo lúc nào).
-- Đây là ví dụ schema TIẾN HÓA: bảng đã có từ V1, giờ bổ sung cột mới.
--
-- Lưu ý quan trọng khi thêm cột NOT NULL vào bảng ĐÃ CÓ DỮ LIỆU:
-- các dòng cũ chưa có giá trị cho cột mới → phải cho DEFAULT, nếu không sẽ lỗi.
-- Ở đây dùng DEFAULT now() để dòng cũ nhận thời điểm chạy migration.

ALTER TABLE sessions
    ADD COLUMN created_at TIMESTAMP NOT NULL DEFAULT now();
