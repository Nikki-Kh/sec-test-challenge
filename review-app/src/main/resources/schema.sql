CREATE TABLE users (
    user_id INTEGER NOT NULL AUTO_INCREMENT,
    username VARCHAR(32) NOT NULL,
    password VARCHAR(32) NOT NULL,
    PRIMARY KEY (user_id),
    UNIQUE (username)
);

CREATE TABLE reviews (
    product_id VARCHAR(32) NOT NULL,
    avg_score NUMERIC NOT NULL,
    review_num INTEGER NOT NULL,
    PRIMARY KEY (product_id)
);