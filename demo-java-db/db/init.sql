CREATE TABLE category (
    id      BIGSERIAL    PRIMARY KEY,
    name    VARCHAR(100) NOT NULL,
    slug    VARCHAR(120) NOT NULL UNIQUE
);

CREATE TABLE jobs (
    id          BIGSERIAL PRIMARY KEY,
    category_id BIGINT NOT NULL REFERENCES category(id),
    title       VARCHAR(200) NOT NULL,
    salary      NUMERIC(12,2),
    status      VARCHAR(20) NOT NULL DEFAULT 'draft'
);

INSERT INTO category (name, slug) VALUES
    ('Backend', 'backend'), ('Frontend', 'frontend'), ('Mobile', 'mobile');

INSERT INTO jobs (category_id, title, salary, status) VALUES
    (1, 'Senior Java Developer', 3000, 'approved'),
    (2, 'Python Backend Engineer', 2500, 'approved'),
    (3, 'React Developer', NULL, 'pending');