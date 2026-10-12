-- Bài 4.0 — Tạo bảng (rút gọn từ Remote IT Job)
-- Chạy lại file này bất cứ lúc nào để làm mới từ đầu.

DROP TABLE IF EXISTS job_tags, job, tag, category;

-- Danh mục việc làm: Backend, Frontend...
CREATE TABLE category (
    id   BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,  -- khóa chính, DB tự tăng 1, 2, 3...
    name VARCHAR(100) NOT NULL,                             -- bắt buộc có giá trị
    slug VARCHAR(100) NOT NULL UNIQUE                       -- không được trùng
);

-- Tag kỹ năng: Java, Spring, React...
CREATE TABLE tag (
    id   BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    slug VARCHAR(100) NOT NULL UNIQUE
);

-- Tin tuyển dụng
CREATE TABLE job (
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title       VARCHAR(200) NOT NULL,
    category_id BIGINT NOT NULL REFERENCES category(id),  -- khóa ngoại: phải là id có thật trong category
    salary_max  INTEGER,                                   -- lương tối đa (USD); NULL = không công khai
    status      VARCHAR(20) NOT NULL DEFAULT 'pending',    -- pending / approved / expired
    created_at  TIMESTAMP NOT NULL DEFAULT NOW()
);

-- Bảng trung gian: một job có nhiều tag, một tag thuộc nhiều job
CREATE TABLE job_tags (
    job_id BIGINT NOT NULL REFERENCES job(id),
    tag_id BIGINT NOT NULL REFERENCES tag(id),
    PRIMARY KEY (job_id, tag_id)                           -- khóa chính kép: không gắn 1 tag 2 lần cho 1 job
);
