INSERT INTO category (idx, category_type)
VALUES
    (1, '채소'),
    (2, '견과류'),
    (3, '곡류'),
    (4, '버섯류'),
    (5, '과일'),
    (6, '해산물'),
    (7, '건어물'),
    (8, '정육'),
    (9, '유제품'),
    (10, '주류')
    ON DUPLICATE KEY UPDATE idx = idx;